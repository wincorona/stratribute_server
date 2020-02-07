package com.siti.common.sys.biz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.siti.common.db.entry.Auth;
import com.siti.common.db.entry.Org;
import com.siti.common.db.entry.OrgAuthRela;
import com.siti.common.db.entry.UserOrgRela;
import com.siti.common.sys.dao.AuthDao;
import com.siti.common.sys.dao.OrgAuthRelaDao;
import com.siti.common.sys.dao.OrgDao;
import com.siti.common.sys.dao.UserOrgRelaDao;
import com.siti.tool.CommonI18Constant;
import com.siti.tool.MessageUtils;
import com.siti.tool.MyException;

/**
 * @author zhangt
 */

@Service
@Transactional
public class OrgBiz {
	
	@Autowired
	private OrgDao orgDao;
	
	@Autowired
	private UserOrgRelaDao userOrgRelaDao;
	
	@Autowired
	private OrgAuthRelaDao orgAuthRelaDao;
	
	@Autowired
	private AuthDao authDao;

	/**
	 * 新建
	 * @param org
	 */
	public void save(Org org) {
		orgDao.save(org);
		//为该组织配置所有的权限为0
		List<Auth> authList = authDao.getAllAuth();
		List<OrgAuthRela> orgAuthRelaList = new ArrayList<OrgAuthRela>();
		for(Auth auth:authList){
			OrgAuthRela orgAuthRela = new OrgAuthRela();
			orgAuthRela.setOrgId(org.getId());
			orgAuthRela.setAuthId(auth.getId());
			orgAuthRelaList.add(orgAuthRela);
		}
		
		if(!orgAuthRelaList.isEmpty()){
			orgAuthRelaDao.batchInsert(orgAuthRelaList);
		}
	}

	/**
	 * 删除
	 * @param id 组织ID
	 * @throws Exception
	 */
	public void delete(Integer id){
		//检查该组织下是否存在用户，若存在不能进行删除操作
		List<UserOrgRela> userOrgRelaList = userOrgRelaDao.getUserOrgRelaByOrgId(id);
		if(userOrgRelaList.size() > 0){
			throw new MyException(MessageUtils.get(CommonI18Constant.com_siti_sys_org_delete_reference_1));
		}
		
		//检查该组织下是否存在子组织，若存在不能进行删除操作
		List<Org> childrenOrgList = orgDao.getChildrenOrgByOrgId(id);
		if(!childrenOrgList.isEmpty()){
			throw new MyException(MessageUtils.get(CommonI18Constant.com_siti_sys_org_delete_reference_2));
		}
		
		//需要先删除组织和权限的关系
		orgAuthRelaDao.deleteByOrgId(id);
		
		//再删除该组织
		orgDao.delete(id);
	}

	/**
	 * 修改
	 * @param org 组织对象
	 */
	public void update(Org org) {
		orgDao.update(org);
	}

	/**
	 * 查询
	 * @param userId 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> search(Integer userId) {
		UserOrgRela userOrgRela = userOrgRelaDao.getUserOrgRelaByUserId(userId).get(0);//用户只属于一个组织
		List<Map> orgMapList = orgDao.searchById(userOrgRela.getOrgId());//组织ID唯一，这个List只有一个元素
		for(Map orgMap : orgMapList){
			List<Map> childOrgList = recurseSearchMapListByPid(userOrgRela.getOrgId());
			orgMap.put("children", childOrgList);
		}
		return orgMapList;
	}
	
	/**
	 * 递归查询
	 * @param pid
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> recurseSearchMapListByPid(Integer pid){
		List<Map> orgList = orgDao.searchByPid(pid);
		for(Map org:orgList){
			List<Map> childMap = recurseSearchMapListByPid((Integer)org.get("id"));
			org.put("children", childMap);
		}
		return orgList;
	}
	
	/**递归查找  结果不嵌含List
	 * @param allChildrenOrgList 
	 * @param pid 
	 * @return
	 */
	public void recurseSearchOrgListByPid(List<Org> allChildrenOrgList, Integer pid){
		List<Org> orgList = orgDao.getChildrenOrgByOrgId(pid);
		if(!orgList.isEmpty()){
			allChildrenOrgList.addAll(orgList);
		}
		for(Org org:orgList){
			recurseSearchOrgListByPid(allChildrenOrgList, org.getId());
		}
	}

	/**
	 * 查找所有
	 * @return
	 */
	public List<Org> searchAll() {
		return orgDao.getAllOrgList();
	}

	/**
	 * 根据组织名查找
	 * @param orgName 组织名
	 * @return
	 */
	public List<Org> searchByOrgName(String orgName) {
		return orgDao.getOrgsByOrgName(orgName);
	}
	
	/**
	 * 查询用户所属的组织及其子组织。其他模块中基本都会用到
	 * @param userId 用户id
	 * @return 返回List<Integer>, 不嵌套
	 */
	public List<Integer> searchOrgIdsByUserId(Integer userId){
		List<Integer> orgIds = new ArrayList<Integer>();
		UserOrgRela userOrgRela = userOrgRelaDao.getUserOrgRelaByUserId(userId).get(0);//用户只属于一个组织
		orgIds.add(userOrgRela.getOrgId());
		recurseSearchChildOrgIdsByOrgid(orgIds, userOrgRela.getOrgId());//加入到orgIds中
		return orgIds;
	}
	
	/**
	 * 查询本组织及其子组织，返回List<Integer>
	 * @param id 本组织id
	 * @return
	 */
	public List<Integer> searchOrgIdsById(Integer id){
		List<Integer> orgIds = new ArrayList<Integer>();
		orgIds.add(id);
		recurseSearchChildOrgIdsByOrgid(orgIds, id);//加入到orgIds中
		return orgIds;
	}

	/**
	 * 遍历查找子组织，将组织id放进orgIds中
	 * @param orgIds 所有组织id的容器
	 * @param orgId 当前这一层组织id
	 */
	@SuppressWarnings("rawtypes")
	private void recurseSearchChildOrgIdsByOrgid(List<Integer> orgIds, Integer orgId) {
		List<Map> orgList = orgDao.searchByPid(orgId);
		for(Map org:orgList){
			orgIds.add((Integer)org.get("id"));
			recurseSearchChildOrgIdsByOrgid(orgIds, (Integer)org.get("id"));
		}
	}

	/**
	 * 根据组织id查询当前组织及其子组织
	 * @param orgId 组织id
	 * @return
	 */
	public List<Org> searchOrgIdsByOrgId(Integer orgId) {
		List<Org> allValidOrgList = new ArrayList<Org>();
		//遍历查询子组织
		recurseSearchOrgListByPid(allValidOrgList, orgId);
		//将orgId的组织也加入进去
		List<Org> orgList = orgDao.searchByOrgId(orgId);
		allValidOrgList.add(orgList.get(0));
		return allValidOrgList;
	}

	/**
	 * 根据用户id查询所有组织（用户所属组织以及子组织）
	 * @param userId 用户id
	 * @return
	 */
	public List<Org> searchOrgsByUserId(Integer userId) {
		//TODO //这里假设用户只属于一个组织，后期再优化
		UserOrgRela userOrgRela =  userOrgRelaDao.getUserOrgRelaByUserId(userId).get(0);
		Org org = orgDao.searchByOrgId( userOrgRela.getOrgId()).get(0);
		List<Org> allOrgList = new ArrayList<Org>();
		recurseSearchOrgListByPid(allOrgList, userOrgRela.getOrgId());
		allOrgList.add(org);
		return allOrgList;
	}
}
