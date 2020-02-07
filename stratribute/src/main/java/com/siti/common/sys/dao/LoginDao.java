package com.siti.common.sys.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.siti.common.db.entry.LoginRecord;

/**
 * @author zhangt
 */

@Repository
public class LoginDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * 添加
	 * @param loginRecord
	 */
	public void add(LoginRecord loginRecord) {
		Session session = sessionFactory.getCurrentSession();
		session.merge(loginRecord);
	}

}
