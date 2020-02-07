package com.siti.common.sys.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.siti.common.db.entry.UserOrgRela;

/**
 * @author zhangt
 */

@Repository
public class UserOrgRelaDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	/**新建用户和组织的关系
	 * @param userId
	 * @param OrgIdList
	 */
	public void save(Integer userId, List<Integer> OrgIdList){
		Session session = sessionFactory.getCurrentSession();
		if(OrgIdList != null){
			for(int i = 0; i<OrgIdList.size(); i++){
				Integer orgId = OrgIdList.get(i);
				UserOrgRela userOrgRela = new UserOrgRela();
				userOrgRela.setUserId(userId);
				userOrgRela.setOrgId(orgId);
				session.save(userOrgRela);
				session.flush();
			}
		}
	}
	
	//修改
	public void update(Integer userId, List<Integer> OrgId){
		//先删除，再添加
		
	}
	
	/**
	 * 根据用户ID删除对应的用户组织关系
	 * @param userId 用户ID
	 */
	public void deleteByUserId(Integer userId){
		Session session = sessionFactory.getCurrentSession();
		String sql = "delete from UserOrgRela where userId = :userId";
		Query q = session.createQuery(sql);
		q.setParameter("userId", userId);
		q.executeUpdate();
	}
	
	/**
	 * 根据用户ID获取用户组织的关系
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<UserOrgRela> getUserOrgRelaByUserId(Integer userId){
		Session session = sessionFactory.getCurrentSession();
		String hql = "from UserOrgRela where userId = :userId ";
		Query q = session.createQuery(hql);
		q.setParameter("userId", userId);
		List<UserOrgRela> userOrgRelaList = q.list();
		return userOrgRelaList;
	}
	
	/**根据组织ID获取用户组织关系
	 * @param orgId 组织ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<UserOrgRela>getUserOrgRelaByOrgId(Integer orgId){
		Session session = sessionFactory.getCurrentSession();
		String hql = "from UserOrgRela where orgId = :orgId ";
		Query q = session.createQuery(hql);
		q.setParameter("orgId", orgId);
		List<UserOrgRela> userOrgRelaList = q.list();
		return userOrgRelaList;
	}
	
	/**根据用户ID获取其所属的组织列表
	 * userId 用户ID
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> getOrgByUserId(Integer userId){
		Session session = sessionFactory.getCurrentSession();
		String hql = "  select new map (o.id as id, o.name as name, "
				+ " o.parentOrgId as parentOrgId, o.des as des)  "
				+ " from Org o, UserOrgRela r "
				+ " where r.orgId = o.id and r.userId =:userId ";
		Query q = session.createQuery(hql);
		q.setParameter("userId", userId);
		List<Map> orgList = q.list();
		return orgList;
	}
	
}
