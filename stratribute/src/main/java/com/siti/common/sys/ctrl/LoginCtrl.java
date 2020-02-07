package com.siti.common.sys.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.siti.common.db.entry.User;
import com.siti.common.sys.biz.LoginBiz;
import com.siti.tool.CommonConstant;
import com.siti.tool.CommonI18Constant;
import com.siti.tool.MessageUtils;
import com.siti.tool.MyException;
import com.siti.tool.ReturnResult;

/**
 * @author zhangt
 */

@RestController
@RequestMapping("login")
public class LoginCtrl {
	
	@Autowired
	private LoginBiz loginBiz;
	
	@SuppressWarnings("rawtypes")
	@Autowired
    private RedisTemplate redisTemplate;
	
	/**
	 * 登录
	 * @param user 用户对象，主要用到用户名和密码
	 * @return
	 */
	@RequestMapping(value="/loginCheck",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String login(@RequestBody User user){
		ReturnResult rr = new ReturnResult();
		try{
			User validUser = loginBiz.userCheck(user);
			String token = loginBiz.generateToken(validUser);
			loginBiz.addLoginRecord(validUser);
			rr.setCode(CommonConstant.CODE_OK);
			rr.setData(validUser);
			rr.setSuccess(true);
			rr.setToken(token);
		}catch(MyException e){
			rr.setCode(CommonConstant.CODE_FAIL);
			rr.setSuccess(false);
			rr.setMessage(e.getMessage());
		}catch(Exception e){
			rr.setCode(CommonConstant.CODE_FAIL);
			rr.setSuccess(false);
			rr.setMessage(MessageUtils.get(CommonI18Constant.com_siti_login_failed));
		}
		Gson gson = new Gson();
		return gson.toJson(rr);
	}
	
}
