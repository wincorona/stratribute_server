package com.siti.common.sys.biz;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.siti.common.db.entry.AuthBean;
import com.siti.common.db.entry.Org;
import com.siti.common.db.entry.OrgAuthRela;
import com.siti.common.sys.dao.AuthDao;
import com.siti.common.sys.dao.OrgAuthRelaDao;
import com.siti.common.sys.dao.OrgDao;
import com.siti.tool.AuthhandlerUtil;
import com.siti.tool.CommonI18Constant;
import com.siti.tool.MessageUtils;
import com.siti.tool.MyException;

/**
 * @author zhangt
 */

@Service
@Transactional
public class OrgAuthRelaBiz {

	@Autowired
	private OrgAuthRelaDao orgAuthRelaDao;
	
	@Autowired
	private AuthDao authDao;
	
	@Autowired
	private OrgDao orgDao;
	
	/**查找组织的权限
	 * @param orgId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public List<Map> searchByOrgId(Integer orgId) {
		String lang = LocaleContextHolder.getLocale().getLanguage();
		List<Map> rootAuthList = orgAuthRelaDao.getRootAuthByOrgIdDao(orgId, lang);
		List<Map> totalAuthList = recurseGetChildrenAuth(rootAuthList, orgId, lang);
		return totalAuthList;
	}
	
	/**递归查找子权限
	 * @param authList 权限List
	 * @param orgId 组织ID
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> recurseGetChildrenAuth(List<Map> authList, Integer orgId, String lang){
		for(Map auth:authList){
			AuthhandlerUtil.adjustCRUDAuth(auth);
			
			//遍历查询各个父节点的子节点
			List<Map> childrenAuthList = 
					authDao.getChildrenAuthByPIdAndOrgIdDao((Integer)auth.get("id"), orgId, lang);
			
			recurseGetChildrenAuth(childrenAuthList,  orgId, lang);
			
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
	 * 修改组织的权限
	 * @param json
	 * @throws Exception
	 */
	public void upadte(String json) throws Exception{
		Gson gson = new Gson();
		List<AuthBean> authBeanlist= gson.fromJson(json, new TypeToken<List<AuthBean>>() {}.getType());
		//上层组织拥有下层组织的所有权限，并关系
		if(authBeanlist.isEmpty()){
			//没有变动，直接返回
			return;
		}else{
			for(AuthBean authBean:authBeanlist){
				Integer authId = authBean.getAuthId();
				Integer orgId = authBean.getOrgId();
				Org org = orgDao.getOrgByOrgId(orgId);
				if(org == null){
					throw ( new MyException(MessageUtils.get(CommonI18Constant.com_siti_operate_failed)) );
				}
				if(authBean.getCreateAuth() != null){//增加  需要优化，增删改查通用方法
					if(authBean.getCreateAuth() == 1){
						if(org.getParentOrgId() != 0){
							//当前组织不是最上层组织，递归父组织,查询父组织是否有create权限
							boolean flag = recurseCheckParentAuth("CreateAuth", orgId, authId);
							if(flag){
								orgAuthRelaDao.setAuthByOrgId(orgId, authId, "createAuth", 1);
							}else{
								//不能进行修改，请检查上层组织中是否含有所修改的权限！
								Locale local = LocaleContextHolder.getLocale();
								if("zh".equals(local.getLanguage())){
									throw new MyException("上层组织中没有" + authBean.getLabel() + "的新建权限" );
								}else if("en".equals(local.getLanguage())){
									throw new MyException("upper level organization has no create authority of " + authBean.getLabel());//英文  lable也需要英文版的
								}else{//默认
									throw new MyException("上层组织中没有" + authBean.getLabel() + "的新建权限" );
								}
							}
						}else{
							orgAuthRelaDao.setAuthByOrgId(orgId, authId, "createAuth", 1);
						}
					}else{
						orgAuthRelaDao.setAuthByOrgId(orgId, authId, "createAuth", 0);
						//该组织所删除的权限，需要将其所有子组织对应权限也置0
						recurseSetChildrenOrgAuthInvalid(orgId, authId, "createAuth");
					}
				}
				if(authBean.getDeleteAuth() != null){//删除
					if(authBean.getDeleteAuth() == 1){
						if(org.getParentOrgId() != 0){
							//当前组织不是最上层组织，递归父组织,查询父组织是否有delete权限
							boolean flag = recurseCheckParentAuth("DeleteAuth", orgId, authId);
							if(flag){
								orgAuthRelaDao.setAuthByOrgId(orgId, authId, "deleteAuth", 1);
							}else{
								Locale local = LocaleContextHolder.getLocale();
								if("zh".equals(local.getLanguage())){
									throw new MyException("上层组织中没有" + authBean.getLabel() + "的删除权限" );
								}else if("en".equals(local.getLanguage())){
									throw new MyException("upper level organization has no delete authority of " + authBean.getLabel());//英文  lable也需要英文版的
								}else{//默认
									throw new MyException("上层组织中没有" + authBean.getLabel() + "的删除权限" );
								}
							}
						}else{
							orgAuthRelaDao.setAuthByOrgId(orgId, authId, "deleteAuth", 1);
						}
					}else{
						orgAuthRelaDao.setAuthByOrgId(orgId, authId, "deleteAuth", 0);
						//该组织所删除的权限，需要将其所有子组织对应权限也置0
						recurseSetChildrenOrgAuthInvalid(orgId, authId, "deleteAuth");
					}
				}
				if(authBean.getUpdateAuth() != null){//修改
					if(authBean.getUpdateAuth() == 1){
						if(org.getParentOrgId() != 0){
							//当前组织不是最上层组织，递归父组织,查询父组织是否有update权限
							boolean flag = recurseCheckParentAuth("UpdateAuth", orgId, authId);
							if(flag){
								orgAuthRelaDao.setAuthByOrgId(orgId, authId, "updateAuth", 1);
							}else{
								Locale local = LocaleContextHolder.getLocale();
								if("zh".equals(local.getLanguage())){
									throw new MyException("上层组织中没有" + authBean.getLabel() + "的修改权限" );
								}else if("en".equals(local.getLanguage())){
									throw new MyException("upper level organization has no update authority of " + authBean.getLabel());//英文  lable也需要英文版的
								}else{//默认
									throw new MyException("上层组织中没有" + authBean.getLabel() + "的修改权限" );
								}
							}
						}else{
							orgAuthRelaDao.setAuthByOrgId(orgId, authId, "updateAuth", 1);
						}
					}else{
						orgAuthRelaDao.setAuthByOrgId(orgId, authId, "updateAuth", 0);
						//该组织所删除的权限，需要将其所有子组织对应权限也置0
						recurseSetChildrenOrgAuthInvalid(orgId, authId, "updateAuth");
					}
				}
				if(authBean.getReadAuth() != null){//查询
					if(authBean.getReadAuth() == 1){
						if(org.getParentOrgId() != 0){
							//当前组织不是最上层组织，递归父组织,查询父组织是否有read权限
							boolean flag = recurseCheckParentAuth("ReadAuth", orgId, authId);
							if(flag){
								orgAuthRelaDao.setAuthByOrgId(orgId, authId, "readAuth", 1);
							}else{
								Locale local = LocaleContextHolder.getLocale();
								if("zh".equals(local.getLanguage())){
									throw new MyException("上层组织中没有" + authBean.getLabel() + "的查询权限" );
								}else if("en".equals(local.getLanguage())){
									throw new MyException("upper level organization has no search authority of " + authBean.getLabel());//英文  lable也需要英文版的
								}else{//默认
									throw new MyException("上层组织中没有" + authBean.getLabel() + "的查询权限" );
								}
								
							}
						}else{
							orgAuthRelaDao.setAuthByOrgId(orgId, authId, "readAuth", 1);
						}
					}else{
						orgAuthRelaDao.setAuthByOrgId(orgId, authId, "readAuth", 0);
						//该组织所删除的权限，需要将其所有子组织对应权限也置0
						recurseSetChildrenOrgAuthInvalid(orgId, authId, "readAuth");
					}
				}
			}
		}
	}
	
	/**
	 * 递归设置权限
	 * @param orgId
	 * @param authId
	 * @param type
	 */
	private void recurseSetChildrenOrgAuthInvalid(Integer orgId, Integer authId, String type) {
		List<Org> childrenOrgList = orgDao.getChildrenOrgByOrgId(orgId);
		if(childrenOrgList.isEmpty()){
			//已经到最底层组织了，不需要再递归
			return;
		}else{
			List<Integer> childrenOrgIdList = new ArrayList<Integer>();
			for(Org childOrg:childrenOrgList){
				childrenOrgIdList.add(childOrg.getId());
			}
			orgAuthRelaDao.batchSetAuthByOrgIdList(childrenOrgIdList, authId, type, 0);
			for(Integer childOrgId:childrenOrgIdList){
				recurseSetChildrenOrgAuthInvalid(childOrgId, authId, type);
			}
		}
	}

	/**
	 * 优化：采用反射
	 * 遍历查询父组织（递归）中是否有该菜单的authType权限
	 * @param authType 权限类型： createAuth, deleteAuth, readAuth, updateAuth
	 * @param orgId 组织ID
	 * @param authId 菜单ID
	 * @return true:父组织有权，false:父组织无权
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean recurseCheckParentAuth(String authType, Integer orgId, Integer authId) throws Exception{
		Org parentOrg = orgDao.getParentOrgByOrgId(orgId);
		if(parentOrg !=null){
			OrgAuthRela orgAuthRela = orgAuthRelaDao.getOrgAuthRelaByOrgIdAndAuthId(parentOrg.getId(), authId);
			if(orgAuthRela!=null){
				try{
					Class c = orgAuthRela.getClass();
					Method method = c.getMethod("get"+authType);
					Integer auth = (Integer)method.invoke(orgAuthRela);
					if(auth == 1){//有权
						return true;
					}else if(parentOrg.getParentOrgId() == 0 ){//已经查到最上层的组织了，还是没有该权限
						return false;
					}else{//查到当前组织为止，都没有该权限，但是当前组织不是最上层的组织，继续递归
						return recurseCheckParentAuth(authType, parentOrg.getId(), authId);
					}
				}catch(Exception e){
					throw new MyException(MessageUtils.get(CommonI18Constant.com_siti_reflect_failed));//反射失败
				}
				
			}else{
				return false;
			}
		}else{//已经到最上层
			return false;
		}
	}
	
}
