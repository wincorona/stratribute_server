package com.siti.common.sys.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.siti.common.db.entry.Auth;

/**
 * @author zhangt
 */

@Repository
public class AuthDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * 根据用户ID查找根节点 
	 * @param userId 用户ID
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> getRootAuthByUserIdDao(Integer userId) {
		String hql = " select new map ( c.id as id, c.label as label, c.labelEn as labelEn, c.menuUrl as menuUrl, c.sort as sort, "
				+ " a.createAuth as createAuth, a.readAuth as readAuth, "
				+ " a.updateAuth as updateAuth, a.deleteAuth as deleteAuth, "
				+ " c.parentAuthId as parentAuthId, c.icon as icon, a.id as orgAuthRelaId ) "
				+ " from OrgAuthRela a, UserOrgRela b, Auth c "
				+ " where b.userId =:userId and b.orgId = a.orgId and a.authId = c.id and c.parentAuthId = 0 "
				+ " order by c.sort ";
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery(hql);
		q.setParameter("userId", userId);
		List<Map> list = q.list();
		return list;
	}
	
	/**
	 * 由父节点查找子节点
	 * @param pId 父节点ID
	 * @param orgId 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> getChildrenAuthByPIdAndOrgIdDao(Integer pId, Integer orgId, String lang) {
		String hql = " select new map ( c.id as id, ";
		if("en".equals(lang)){
			hql = hql + " c.labelEn as label, ";
		}else{
			hql = hql + " c.label as label, ";
		};
		hql = hql +  " c.labelEn as labelEn, c.menuUrl as menuUrl, c.sort as sort, "
				+ " a.id as orgAuthRelaId, "
				+ " a.createAuth as createAuth, a.readAuth as readAuth, "
				+ " a.updateAuth as updateAuth, a.deleteAuth as deleteAuth, "
				+ " c.parentAuthId as parentAuthId, c.icon as icon ) "
				+ " from OrgAuthRela a, Auth c "
				+ " where a.authId = c.id and c.parentAuthId =:pId and a.orgId =:orgId";
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery(hql);
		q.setParameter("pId", pId);
		q.setParameter("orgId", orgId);
		List<Map> list = q.list();
		return list;
	}
	
	/**
	 * 根据用户ID和父权限ID获取其子权限
	 * @param pId
	 * @param userId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> getChildrenAuthByPIdAndUserIdDao(Integer pId,
			Integer userId) {
		String hql = " select new map ( c.id as id, c.label as label, c.labelEn as labelEn, c.menuUrl as menuUrl, c.sort as sort, "
				+ " a.id as orgAuthRelaId, "
				+ " a.createAuth as createAuth, a.readAuth as readAuth, "
				+ " a.updateAuth as updateAuth, a.deleteAuth as deleteAuth, "
				+ " c.parentAuthId as parentAuthId, c.icon as icon ) "
				+ " from OrgAuthRela a, UserOrgRela b, Auth c "
				+ " where b.userId =:userId and b.orgId = a.orgId and a.authId = c.id and c.parentAuthId =:pId "
				+ " order by c.sort ";
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery(hql);
		q.setParameter("pId", pId);
		q.setParameter("userId", userId);
		List<Map> list = q.list();
		return list;
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public void delete(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		String sql = "delete from Auth where id = :id";
		Query q = session.createQuery(sql);
		q.setParameter("id", id);
		q.executeUpdate();
	}
	
	/**
	 * 批量删除
	 * @param idList
	 * @return
	 */
	public void batchDelete(List<Integer> idList) {
		Session session = sessionFactory.getCurrentSession();
		String sql = "delete from Auth where id in(:idList)";
		Query q = session.createQuery(sql);
		q.setParameterList("idList", idList);
		q.executeUpdate();
	}

	/**
	 * 获取该权限下的所有子权限
	 * @param Integer parentAuthId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Auth> getChildrenAuthByParentAuthId(Integer parentAuthId) {
		Session session = sessionFactory.getCurrentSession();
		String sql = " from Auth where parentAuthId=:parentAuthId ";
		Query q = session.createQuery(sql);
		q.setParameter("parentAuthId", parentAuthId);
		List<Auth> list = q.list();
		return list;
	}

	/**
	 * 新建
	 * @param auth
	 */
	public void save(Auth auth) {
		Session session = sessionFactory.getCurrentSession();
		session.save(auth);
	}

	/**
	 * 修改（先不做字段检查）
	 * @param auth
	 */
	public void update(Auth auth) {
		Session session = sessionFactory.getCurrentSession();
		session.merge(auth);
	}
	
	/**
	 * 获取所有的权限
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Auth> getAllAuth(){
		Session session = sessionFactory.getCurrentSession();
		String sql = " from Auth ";
		Query q = session.createQuery(sql);
		List<Auth> list = q.list();
		return list;
	}

}
