package com.siti.common.sys.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.siti.common.db.entry.User;

/**
 * @author zhangt
 */

@Repository
public class UserDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * 新建用户
	 * @param user 用户对象
	 */
	public void save(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.save(user);
	}
	
	/**
	 * 删除用户
	 * @param userId 用户ID
	 */
	public void deleteById(Integer userId) {
		Session session = sessionFactory.getCurrentSession();
		String sql = "delete from User where id = :id";
		Query q = session.createQuery(sql);
		q.setParameter("id", userId);
		q.executeUpdate();
	}
	
	/**
	 * 修改
	 * @param user 用户对象
	 */
	public void update(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.merge(user);
	}
	
	
	/**
	 * 按用户名查询用户
	 * @param userName 用户名
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<User> getUsersByUserName(String userName){
		String hql = "from User where userName = :userName ";
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery(hql);
		q.setParameter("userName", userName);
		List<User> userList = q.list();
		return userList;
	}
	
	/**
	 * 查询与本用户同名但是不通ID的用户
	 * @param user 本用户对象
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<User> getUsersBySameNameDiffId(User user){
		String hql = "from User where userName = :userName and id != :id";
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery(hql);
		q.setParameter("userName", user.getUserName());
		q.setParameter("id", user.getId());
		List<User> userList = q.list();
		return userList;
	}
	
	/**
	 * 按用户ID查询用户
	 * @param userId 用户ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public User getUserByUserId(Integer userId){
		Session session = sessionFactory.getCurrentSession();
		String sql = " from User where id =:userId";
		Query q = session.createQuery(sql);
		q.setParameter("userId", userId);
		List<User> userList = q.list();
		if(userList.isEmpty()){
			return null;
		}else{
			return userList.get(0);
		}
	}
	
	/**
	 * 按用户名称查询用户
	 * @param userName 用户名
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public User getUserByUserName(String userName){
		Session session = sessionFactory.getCurrentSession();
		String sql = " from User where userName =:userName";
		Query q = session.createQuery(sql);
		q.setParameter("userName", userName);
		List<User> userList = q.list();
		if(userList.isEmpty()){
			return null;
		}else{
			return userList.get(0);
		}
	}

	/**
	 * 按组织ID的List查询用户
	 * @param orgIdList 组织ID的List
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> getUserByOrgIds(List<Integer> orgIdList, String userName){
		Session session = sessionFactory.getCurrentSession();
		String sql = " select new map (u.id as id, u.userName as userName, "
				+ " u.cellPhone as cellPhone, u.address as address, u.realName as realName ) "//o.name as orgName, o.id as orgId 不是单个，返回一个List
				+ " from User u, UserOrgRela r, Org o "
				+ " where u.id = r.userId and o.id = r.orgId and o.id in(:orgIdList) ";
		if(userName != null){
			userName = "%%" + userName + "%%";
			sql = sql + " and u.userName like:userName ";
		}
		Query q = session.createQuery(sql);
		q.setParameterList("orgIdList", orgIdList);
		if(userName != null){
			q.setParameter("userName", userName);
		}
		List<Map> userList = q.list();
		return userList;
	}

	/**
	 * 修改密码
	 * @param id 用户ID
	 * @param newPwd 新密码
	 */
	public void updatePwd(Integer id, String newPwd) {
		Session session = sessionFactory.getCurrentSession();
		String sql = "update User set password=:newPwd where id = :id";
		Query q = session.createQuery(sql);
		q.setParameter("id", id);
		q.setParameter("newPwd", newPwd);
		q.executeUpdate();
	}
	
	
}
