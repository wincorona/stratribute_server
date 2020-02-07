package com.siti.common.sys.biz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.siti.common.db.entry.Org;
import com.siti.common.db.entry.User;
import com.siti.common.db.entry.UserOrgRela;
import com.siti.common.sys.dao.OrgDao;
import com.siti.common.sys.dao.UserDao;
import com.siti.common.sys.dao.UserOrgRelaDao;
import com.siti.tool.CommonI18Constant;
import com.siti.tool.MessageUtils;
import com.siti.tool.MyException;

/**
 * @author zhangt
 */

@Service
@Transactional
public class UserBiz {
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserOrgRelaDao userOrgRelaDao;
	
	@Autowired
	private OrgDao orgDao;
	
	@Autowired
	private OrgBiz orgBiz;
	
	private final String defaultPassword = "123456";

	/**
	 * 新建
	 * @param json
	 * @throws Exception
	 */
	public void save(String json) throws Exception{
		Gson gson = new Gson();
		JsonObject obj = gson.fromJson(json, JsonObject.class);
		//解析用户
		JsonElement userEle = obj.get("user");
		User user = gson.fromJson(userEle, User.class);
		//解析组织
		JsonArray jsonArray = obj.getAsJsonArray("orgIds");
		List<Integer> orgIdList = gson.fromJson(jsonArray, new TypeToken<List<Integer>>() {}.getType()); 
		
		user.setPassword(DigestUtils.md5Hex(defaultPassword));//设置默认密码
		List<User> sameNameUsersList = userDao.getUsersByUserName(user.getUserName());
		if(sameNameUsersList.isEmpty()){
			userDao.save(user);
			userOrgRelaDao.save(user.getId(), orgIdList);
		}else{
			throw new MyException(MessageUtils.get(CommonI18Constant.com_siti_sys_user_add_repeat));
		}
	}

	/**
	 * 删除
	 * @param userId
	 */
	public void delete(Integer userId, Integer id) {
		//用户不能删除自己
		if(userId.equals(id)){
			throw new MyException(MessageUtils.get(CommonI18Constant.com_siti_sys_user_forbid_delete_self));
		}
		//先删除用户与组织的关系
		userOrgRelaDao.deleteByUserId(id);
		//再删除用户表中数据
		userDao.deleteById(id);
	}

	/**
	 * 查询
	 * @param userId 用户ID
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> search(Integer userId, String userName) {
		//只能查找自己所属的组织，以及该组织下的子组织的用户
		//1）查询自己所属的组织(一般用户只属于一个组织，此处为了以后拓展，组织是个List)
		List<UserOrgRela> userOrgRelaList =  userOrgRelaDao.getUserOrgRelaByUserId(userId);
		//自己所属组织中的所有用户先加入到userList中
		List<Integer> allOrgIdList = new ArrayList<Integer>();//含自己所属组织以及该组织的子组织，以及子组织的子组织。。。
		for(UserOrgRela userOrgRela:userOrgRelaList){
			allOrgIdList.add(userOrgRela.getOrgId());
		}
		
		//2）查询该组织以及所有的子组织，返回一个List，不嵌含List
		List<Org> allChildrenOrgList = new ArrayList<Org>();
		for(UserOrgRela userOrgRela:userOrgRelaList){
			orgBiz.recurseSearchOrgListByPid(allChildrenOrgList, userOrgRela.getOrgId());
		}
		for(Org org:allChildrenOrgList){
			allOrgIdList.add(org.getId());
		}
		
		//3) 查询所属2）中组织的所有用户
		List<Map> userMap = userDao.getUserByOrgIds(allOrgIdList,userName);
		//再把每个用户的组织以list的形式放进去
		for(Map map:userMap){
			Integer id = (Integer)map.get("id");
			List<Map> orgList = userOrgRelaDao.getOrgByUserId(id);
			map.put("org", orgList);
		}
		return userMap;
	}

	/**
	 * 修改（密码除外）
	 * @param json
	 */
	public void update(String json) {
		Gson gson = new Gson();
		JsonObject obj = gson.fromJson(json, JsonObject.class);
		//解析用户
		JsonElement userEle = obj.get("user");
		User user = gson.fromJson(userEle, User.class);
		//解析组织
		JsonArray jsonArray = obj.getAsJsonArray("orgIds");
		List<Integer> orgIdList = gson.fromJson(jsonArray, new TypeToken<List<Integer>>() {}.getType()); 
		
		//判断用户名是否重复
		List<User> usersWithSameNameDiffIdList = userDao.getUsersBySameNameDiffId(user);
		if(!usersWithSameNameDiffIdList.isEmpty()){
			throw new MyException(MessageUtils.get(CommonI18Constant.com_siti_sys_user_add_repeat));
		}
		
		//用户密码不变
		User oriUser = userDao.getUserByUserId(user.getId());//原来的用户对象
		user.setPassword(oriUser.getPassword());
		
		//更新本用户
		userDao.update(user);
		
		//删除用户原来的组织关系
		userOrgRelaDao.deleteByUserId(user.getId());
		
		//创建用户新的组织关系
		userOrgRelaDao.save(user.getId(),orgIdList);
	}

	/**
	 * 修改密码
	 * @param id 用户ID
	 * @param oldPwd 旧密码
	 * @param newPwd 新密码
	 */
	public void updatePwd(Integer id, String oldPwd, String newPwd) {
		User user = userDao.getUserByUserId(id);
		String encodeOldPwd=DigestUtils.md5Hex(oldPwd);
		if(encodeOldPwd.equals(user.getPassword())){
			userDao.updatePwd(id, DigestUtils.md5Hex(newPwd));
		}else{
			throw new MyException(MessageUtils.get(CommonI18Constant.com_siti_sys_user_old_password_error));
		}
	}


}
