package com.siti.common.sys.biz;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.siti.common.db.entry.LoginRecord;
import com.siti.common.db.entry.User;
import com.siti.common.sys.dao.LoginDao;
import com.siti.common.sys.dao.OrgAuthRelaDao;
import com.siti.common.sys.dao.UserDao;
import com.siti.common.sys.dao.UserOrgRelaDao;
import com.siti.tool.CommonI18Constant;
import com.siti.tool.JwtUtil;
import com.siti.tool.MessageUtils;
import com.siti.tool.MyException;

/**
 * @author zhangt
 */

@Service
@Transactional
public class LoginBiz {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private OrgAuthRelaDao orgAuthRelaDao;
	
	@Autowired
	private UserOrgRelaDao userOrgRelaDao;
	
	@Autowired
	private LoginDao loginDao;
	
	@SuppressWarnings("rawtypes")
	@Autowired
    private RedisTemplate redisTemplate;
	
	/**
	 * 生成并存储token
	 * @param user 用户对象
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings({ "unchecked" })
	public String generateToken(User user){
		String token = "";
		Long currentTimeMillis = System.currentTimeMillis(); 
		String tokenUserName = user.getUserName() + "_"+ currentTimeMillis;
		try{
			token =  JwtUtil.encrypt(tokenUserName,user.getId());
		}catch(Exception e){
			throw new MyException(MessageUtils.get(CommonI18Constant.com_siti_token_create_failed));
		}
		
		redisTemplate.opsForValue().set(tokenUserName,token);  //token存redis 键为 登录名_时间毫秒数
		
		redisTemplate.expire(tokenUserName, JwtUtil.EXPIRE_TIME, TimeUnit.MILLISECONDS);  //设置时限，过期失效
		
		return token;
	}
	
	/**
	 * 用户校验
	 * @param user 用户对象
	 * @return
	 */
	public User userCheck(User user){
		User u = userDao.getUserByUserName(user.getUserName());
		if(u == null){
			throw new MyException(MessageUtils.get(CommonI18Constant.com_siti_invalid_username_or_password));
		}
		String encodeStr=DigestUtils.md5Hex(user.getPassword());
		if(!u.getPassword().equals(encodeStr)){
			throw new MyException(MessageUtils.get(CommonI18Constant.com_siti_invalid_username_or_password));
		}
		return u;
	}
	
	/**
	 * 添加用户登录记录
	 * @param user 用户对象
	 */
	public void addLoginRecord(User user){
		LoginRecord loginRecord = new LoginRecord();
		loginRecord.setUserId(user.getId());
		loginRecord.setLoginTime(new Date());
		loginDao.add(loginRecord);
	}
	
	
}
