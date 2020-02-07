package com.siti.common.sys.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.siti.common.db.entry.OrgAuthRela;

/**
 * @author zhangt
 */

@Repository
public class OrgAuthRelaDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	//根据组织ID查找权限，返回authId
	@SuppressWarnings("unchecked")
	public List<OrgAuthRela> getAuthByOrgId(Integer orgId){
		String hql = "from OrgAuthRela where orgId = :orgId ";
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery(hql);
		q.setParameter("orgId", orgId);
		List<OrgAuthRela> orgAuthRelaList = q.list();
		return orgAuthRelaList.isEmpty()?null:orgAuthRelaList;
	}
	
	//根据组织ID查找权限，返回menUrl及各项权限
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getRealAuthByOrgId(Integer orgId){
		String hql = " select new map ( b.menuUrl as menuUrl, a.createAuth as createAuth, " 
				+ " a.readAuth as readAuth, a.updateAuth as updateAuth, a.deleteAuth as deleteAuth ) "
				+ " from OrgAuthRela a, Auth b "
				+ " where a.authId = b.id and a.orgId =:orgId ";
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery(hql);
		q.setParameter("orgId", orgId);
		List<Map> realOrgAuthRelaList = q.list();
		return realOrgAuthRelaList.isEmpty()?null:realOrgAuthRelaList;
	}
	
	//根据用户ID查找权限   该方法放到AuthDao中
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getAuthByUserId(Integer userId){
		String hql = " select new map ( c.menuUrl as menuUrl, a.createAuth as createAuth," 
		+ " a.readAuth as readAuth, a.updateAuth as updateAuth, a.deleteAuth as deleteAuth ) "
		+ " from OrgAuthRela a, UserOrgRela b, Auth c "
		+ " where b.userId =:userId and b.orgId = a.orgId and a.authId = c.id ";
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery(hql);
		q.setParameter("userId", userId);
		List<Map> list = q.list();
		return list.isEmpty() ? null : list;
	}
	
	/**根据权限ID删除组织权限关系
	 * @param authId
	 * @return
	 */
	public void deleteByAuthId(Integer authId){
		Session session = sessionFactory.getCurrentSession();
		String sql = "delete from OrgAuthRela where authId=:authId ";
		Query q = session.createQuery(sql);
		q.setParameter("authId", authId);
		q.executeUpdate();
	}
	
	/**根据权限ID的list删除组织权限关系
	 * @param authId
	 * @return
	 */
	public void batchDeleteByAuthIdList(List<Integer> authIdList){
		Session session = sessionFactory.getCurrentSession();
		String sql = "delete from OrgAuthRela where authId in (:authIdList) ";
		Query q = session.createQuery(sql);
		q.setParameterList("authIdList", authIdList);
		q.executeUpdate();
	}
	
	/**根据组织ID删除组织权限关系
	 * @param orgId 组织ID
	 * @return
	 */
	public void deleteByOrgId(Integer orgId){
		Session session = sessionFactory.getCurrentSession();
		String sql = "delete from OrgAuthRela where orgId=:orgId ";
		Query q = session.createQuery(sql);
		q.setParameter("orgId", orgId);
		q.executeUpdate();
	}
	
	/**
	 * 批量插入
	 * @param orgAuthRelaList 组织权限关系
	 */
	public void batchInsert(List<OrgAuthRela> orgAuthRelaList){
		Session session = sessionFactory.getCurrentSession();
		for(int i=0;i<orgAuthRelaList.size();i++){
			OrgAuthRela orgAuthRela = orgAuthRelaList.get(i);
			session.save(orgAuthRela);
			if(i%10 == 0){//单次批量操作的数目
				session.flush();
				session.clear();
			}
		}
	}

	/**
	 * 查找根节点
	 * @param orgId
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getRootAuthByOrgIdDao(Integer orgId, String lang) {
		String hql = " select new map ( c.id as id, ";
		if("en".equals(lang)){
			hql = hql + " c.labelEn as label, ";
		}else{
			hql = hql + " c.label as label, ";
		};
		hql = hql + " c.labelEn as labelEn, c.menuUrl as menuUrl, c.sort as sort, "
				+ " a.id as orgAuthRelaId, "
				+ " a.createAuth as createAuth, a.readAuth as readAuth, "
				+ " a.updateAuth as updateAuth, a.deleteAuth as deleteAuth, "
				+ " c.parentAuthId as parentAuthId, c.icon as icon ) "
				+ " from OrgAuthRela a, Auth c "
				+ " where a.authId = c.id and c.parentAuthId = 0 and a.orgId=:orgId ";
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery(hql);
		q.setParameter("orgId", orgId);
		List<Map> list = q.list();
		return list;
	}
	
	/**
	 * 通过组织ID和权限ID获取OrgAuthRela
	 * @param orgId 组织ID
	 * @param authId 权限ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public OrgAuthRela getOrgAuthRelaByOrgIdAndAuthId(Integer orgId, Integer authId){
		String hql = " from OrgAuthRela a "
				+ " where a.orgId=:orgId and a.authId =:authId ";
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery(hql);
		q.setParameter("orgId", orgId);
		q.setParameter("authId", authId);
		List<OrgAuthRela> list = q.list();
		return list.isEmpty()?null:list.get(0);
	}
	
	/**
	 * 设置某组织的某权限为有权或者无权
	 * @param orgId  组织ID
	 * @param authId 权限ID
	 * @param authType 增删改查类型 createAuth,readAuth,updateAuth,deleteAuth
	 * @param authValue 值 0:无权  1：有权
	 */
	public void setAuthByOrgId(Integer orgId, 
			Integer authId, String authType, Integer authValue){
		String hql = " update OrgAuthRela set " + authType +"=:authValue "
				+ " where orgId =:orgId and authId =:authId ";
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery(hql);
		q.setParameter("authValue", authValue);
		q.setParameter("orgId", orgId);
		q.setParameter("authId", authId);
		q.executeUpdate();
	}
	
	/**
	 * 批量设置组织的某权限为有权或者无权
	 * @param orgId  组织ID
	 * @param authId 权限ID
	 * @param authType 增删改查类型 createAuth,readAuth,updateAuth,deleteAuth
	 * @param authValue 值 0:无权  1：有权
	 */
	public void batchSetAuthByOrgIdList(List<Integer> orgIdList, 
			Integer authId, String authType, Integer authValue){
		String hql = " update OrgAuthRela set " + authType +"=:authValue "
				+ " where orgId in(:orgIdList) and authId =:authId ";
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery(hql);
		q.setParameter("authValue", authValue);
		q.setParameterList("orgIdList", orgIdList);
		q.setParameter("authId", authId);
		q.executeUpdate();
	}
	
}
