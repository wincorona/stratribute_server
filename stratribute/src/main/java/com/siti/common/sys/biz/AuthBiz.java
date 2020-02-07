package com.siti.common.sys.biz;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.siti.common.db.entry.Auth;
import com.siti.common.db.entry.Org;
import com.siti.common.db.entry.OrgAuthRela;
import com.siti.common.sys.dao.AuthDao;
import com.siti.common.sys.dao.OrgAuthRelaDao;
import com.siti.common.sys.dao.OrgDao;
import com.siti.tool.AuthhandlerUtil;

/**
 * @author zhangt
 */

@Service
@Transactional
public class AuthBiz {
	
	@Autowired
	private AuthDao authDao;
	
	@Autowired
	private OrgAuthRelaDao orgAuthRelaDao;
	
	@Autowired
	private OrgDao orgDao;
	
	/**
	 * 获取合法的权限，需要去掉所有权限都没有的菜单
	 * @param userId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> getValidAuthByUserIdBiz(Integer userId) {
		List<Map> rootAuthList = authDao.getRootAuthByUserIdDao(userId);
		List<Map> totalAuthList = recurseGetChildrenAuth(rootAuthList, userId);
		//如果是叶节点，且各项权限都没有，则去掉该叶节点
		recurseDeleteChildrenAuthWithNoAuth(totalAuthList);
		//根节点遍历查询删除孩子为空，其他权限都没有的父节点
		Iterator<Map> iterator = totalAuthList.iterator();
		 while(iterator.hasNext()){
			 Map map1 = iterator.next();
			 List<Map> childrenAuthList = (List<Map>) map1.get("children");
			 if(childrenAuthList == null || childrenAuthList.isEmpty()){
				 Map authMap = (Map) map1.get("auth");
					Integer deleteAuth = (Integer) authMap.get("deleteAuth");
					Integer updateAuth = (Integer) authMap.get("updateAuth");
					Integer readAuth = (Integer) authMap.get("readAuth");
					Integer createAuth = (Integer) authMap.get("createAuth");
					if(deleteAuth.equals(0)&&updateAuth.equals(0)&&
							readAuth.equals(0)&&createAuth.equals(0)){//所有的权限都没有，则去掉该叶节点
						iterator.remove();   //注意这个地方
					}
			 	}
		    }
		return totalAuthList;
	}
	
	//递归遍历删除叶节点中所有权限都没有的叶节点
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> recurseDeleteChildrenAuthWithNoAuth(List<Map> authList){
		Iterator<Map> iterator = authList.iterator();
		 while(iterator.hasNext()){
			 Map map1 = iterator.next();
			 Integer isLeaf = (Integer) map1.get("leaf");
				if(isLeaf.equals(1)){//是叶节点
					Map authMap = (Map) map1.get("auth");
					Integer deleteAuth = (Integer) authMap.get("deleteAuth");
					Integer updateAuth = (Integer) authMap.get("updateAuth");
					Integer readAuth = (Integer) authMap.get("readAuth");
					Integer createAuth = (Integer) authMap.get("createAuth");
					if(deleteAuth.equals(0)&&updateAuth.equals(0)&&
							readAuth.equals(0)&&createAuth.equals(0)){//所有的权限都没有，则去掉该叶节点
						iterator.remove();   //注意这个地方
					}
				}else{
					List<Map> childrenAuthList = (List<Map>) map1.get("children");
					recurseDeleteChildrenAuthWithNoAuth(childrenAuthList);
				}
		    }
		return authList;
	}
	
	/**
	 * 获取所有节点
	 * @param userId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public List<Map> getAllAuthByUserIdBiz(Integer userId) {
		List<Map> rootAuthList = authDao.getRootAuthByUserIdDao(userId);
		List<Map> totalAuthList = recurseGetChildrenAuth(rootAuthList, userId);
		return totalAuthList;
	}
	
	/**
	 * 递归查找子权限
	 * @param authList 权限List
	 * @param userId 用户ID
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> recurseGetChildrenAuth(List<Map> authList, Integer userId){
		for(Map auth:authList){
			AuthhandlerUtil.adjustCRUDAuth(auth);
			
			//遍历查询各个父节点的子节点
			List<Map> childrenAuthList = 
					authDao.getChildrenAuthByPIdAndUserIdDao((Integer)auth.get("id"),userId);
			
			recurseGetChildrenAuth(childrenAuthList,userId);//子节点递归
			
			//1：是叶节点，0：不是叶节点
			if(childrenAuthList.isEmpty()){
				auth.put("leaf", 1);
			}else{
				auth.put("leaf", 0);
			}
			
			auth.put("children", childrenAuthList);
		}
		return authList;
	}

	/**
	 * 删除
	 * @param id
	 */
	public void delete(Integer id) {
		//获取该权限的所有子权限
		List<Auth> childrenAuthList = getChildrenAuthByParentAuthId(id);
		
		//递归
		recurseDeleteChildrenAuth(childrenAuthList);

		//解除组织与该权限的关系
		orgAuthRelaDao.deleteByAuthId(id);
		//删除该权限
		authDao.delete(id);
	}
	
	/**
	 * 递归删除
	 * @param authList
	 */
	public void recurseDeleteChildrenAuth(List<Auth> authList){
		//如果存在子权限，则先解除组织和子权限的关系
		List<Integer>authIdList = new ArrayList<Integer>();
		if(!authList.isEmpty()){
			for(Auth auth:authList){
				authIdList.add(auth.getId());
				List<Auth> childrenAuthList = getChildrenAuthByParentAuthId(auth.getId());
				recurseDeleteChildrenAuth(childrenAuthList);
			}
			
			orgAuthRelaDao.batchDeleteByAuthIdList(authIdList);
			//删除子权限
			authDao.batchDelete(authIdList);
		}
	}
	
	/**
	 * 新建
	 * @param auth
	 * @return
	 */
	public void save (Auth auth){
		authDao.save(auth);
		//遍历所有组织，在sys_org_auth_rela表中插入数据
		List<Org> orgList = orgDao.getAllOrgList();
		List<OrgAuthRela> orgAuthRelaList = new ArrayList<OrgAuthRela>();
		if(orgList != null){
			for(int i = 0; i<orgList.size();i++){
				OrgAuthRela orgAuthRela = new OrgAuthRela();
				Integer orgId = orgList.get(i).getId();
				Integer authId = auth.getId();
				orgAuthRela.setOrgId(orgId);
				orgAuthRela.setAuthId(authId);
				if(orgList.get(i).getParentOrgId().equals(0)){//根组织拥有所有权限
					orgAuthRela.setCreateAuth(1);
					orgAuthRela.setReadAuth(1);
					orgAuthRela.setUpdateAuth(1);
					orgAuthRela.setDeleteAuth(1);
				}
				orgAuthRelaList.add(orgAuthRela);
			}
			orgAuthRelaDao.batchInsert(orgAuthRelaList);
		}
	}
	
	/**
	 * 获取该权限下的所有子权限
	 * @param Integer parentAuthId
	 * @return
	 */
	public List<Auth> getChildrenAuthByParentAuthId(Integer parentAuthId){
		return authDao.getChildrenAuthByParentAuthId(parentAuthId);
	}

	/**
	 * 修改
	 * @param auth
	 * @return
	 */
	public void update(Auth auth) {
		authDao.update(auth);
	}

}
