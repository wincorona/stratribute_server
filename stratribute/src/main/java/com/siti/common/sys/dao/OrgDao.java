package com.siti.common.sys.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.siti.common.db.entry.Org;

/**
 * @author zhangt
 */

@Repository
public class OrgDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * 新建
	 * @param org
	 */
	public void save(Org org) {
		Session session = sessionFactory.getCurrentSession();
		session.save(org);
	}

	/**
	 * 删除
	 * @param id 组织ID
	 */
	public void delete(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		String sql = "delete from Org where id = :id";
		Query q = session.createQuery(sql);
		q.setParameter("id", id);
		q.executeUpdate();
	}

	/**
	 * 修改
	 * @param org 组织对象
	 */
	public void update(Org org) {
		Session session = sessionFactory.getCurrentSession();
		session.merge(org);
	}

	/**根据上层组织查找
	 * @param pid 上层组织ID
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> searchByPid(Integer pid) {
		Session session = sessionFactory.getCurrentSession();
		String sql = " select new map ( o.id as id, o.name as name, "
				+ " o.parentOrgId as parentOrgId, o.des as des ) "
				+ " from Org o where parentOrgId =:pid ";
		Query q = session.createQuery(sql);
		q.setParameter("pid", pid);
		List<Map> list = q.list();
		return list;
	}
	
	/**
	 * 根据组织ID获取组织Map
	 * @param id
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> searchById(Integer id){
		Session session = sessionFactory.getCurrentSession();
		String sql = " select new map ( o.id as id, o.name as name, "
				+ " o.parentOrgId as parentOrgId, o.des as des ) "
				+ " from Org o where id =:id ";
		Query q = session.createQuery(sql);
		q.setParameter("id", id);
		List<Map> list = q.list();
		return list;
	}
	
	/**
	 * 获取所有组织，不分层
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Org> getAllOrgList(){
		Session session = sessionFactory.getCurrentSession();
		String sql = " from Org ";
		Query q = session.createQuery(sql);
		List<Org> orgList = q.list();
		return orgList;
	}

	/**
	 * 根据组织ID获取上一层父组织 【测试：当orgId的组织为根组织时，该方法是否出错】
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Org getParentOrgByOrgId(Integer orgId) {
		Session session = sessionFactory.getCurrentSession();
		String sql = " from Org a where a.id in "
				+ " ( select o.parentOrgId from Org o where o.id=:orgId and o.parentOrgId != 0) ";
		Query q = session.createQuery(sql);
		q.setParameter("orgId", orgId);
		List<Org> list = q.list();
		return list.isEmpty()?null:list.get(0);
	}
	
	/**
	 * 获取某组织的下一层的所有子组织
	 * @param orgId 组织ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Org> getChildrenOrgByOrgId(Integer orgId){
		Session session = sessionFactory.getCurrentSession();
		String sql = " from Org a where a.parentOrgId =:orgId";
		Query q = session.createQuery(sql);
		q.setParameter("orgId", orgId);
		List<Org> list = q.list();
		return list;
	}
	
	/**
	 * 根据组织ID获取组织
	 * @param orgId 组织ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Org getOrgByOrgId(Integer orgId){
		Session session = sessionFactory.getCurrentSession();
		String sql = " from Org a where a.id =:orgId ";
		Query q = session.createQuery(sql);
		q.setParameter("orgId", orgId);
		List<Org> list = q.list();
		return list.isEmpty()?null:list.get(0);
	}
	
	/**设置子组织的authType权限为value值，再修改
	 * @param authType 权限类型： 增删改查
	 * @param value 值  1，0
	 */
	public void setOrgAuthInvalid(String authType, Integer value){
		Session session = sessionFactory.getCurrentSession();
		String sql = " update Org set :authType =:value ";
		Query q = session.createQuery(sql);
		q.setParameter("authType", authType);
		q.setParameter("value", value);
		q.executeUpdate();
	}

	/**
	 * 根据组织名查找 模糊匹配
	 * @param orgName 组织名
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Org> getOrgsByOrgName(String orgName) {
		Session session = sessionFactory.getCurrentSession();
		orgName = "%%" + orgName + "%%";
		String sql = " from Org where name like:orgName";
		Query q = session.createQuery(sql);
		q.setParameter("orgName", orgName);
		List<Org> orgList = q.list();
		return orgList;
	}

	/**
	 * 根据组织id查询
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Org> searchByOrgId(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		String sql = " from Org a where a.id =:id";
		Query q = session.createQuery(sql);
		q.setParameter("id", id);
		List<Org> list = q.list();
		return list;
	}

}
