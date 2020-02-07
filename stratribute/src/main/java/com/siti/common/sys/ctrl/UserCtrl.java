package com.siti.common.sys.ctrl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.siti.common.sys.biz.UserBiz;
import com.siti.tool.CommonConstant;
import com.siti.tool.CommonI18Constant;
import com.siti.tool.MessageUtils;
import com.siti.tool.MyException;
import com.siti.tool.ReturnResult;

/**
 * @author zhangt
 */

@Controller
@RequestMapping("user")
public class UserCtrl {
	
	@Autowired
	private UserBiz userBiz;
	
	private static final Logger logger = Logger.getLogger(UserCtrl.class);
	
	/**
	 * 新建
	 * @param json,含user和orgIds
	 * @return
	 */
	@RequestMapping(value="/save",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String save(@RequestBody String json) {
		ReturnResult rr = new ReturnResult();
		try{
			userBiz.save(json);
			rr.setCode(CommonConstant.CODE_OK);
			rr.setSuccess(true);
		}catch(MyException mye){
			rr.setCode(CommonConstant.CODE_FAIL);
			rr.setSuccess(false);
			rr.setMessage(mye.getMessage());
			//logger.warn(mye.getMessage());
		}catch(Exception e){
			rr.setCode(CommonConstant.CODE_FAIL);
			rr.setSuccess(false);
			rr.setMessage(MessageUtils.get(CommonI18Constant.com_siti_add_failed));
			//logger.error("新建失败：", e);
		}
		Gson gson = new Gson();
		return gson.toJson(rr);
	}
	
	/**
	 * 删除
	 * @param userId 用户ID
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String delete(Integer userId, Integer id){
		ReturnResult rr = new ReturnResult();
		try{
			userBiz.delete(userId, id);
			rr.setCode(CommonConstant.CODE_OK);
			rr.setSuccess(true);
		}catch(MyException mye){
			rr.setCode(CommonConstant.CODE_FAIL);
			rr.setSuccess(false);
			rr.setMessage(mye.getMessage());
		}catch(Exception e){
			rr.setCode(CommonConstant.CODE_FAIL);
			rr.setSuccess(false);
			rr.setMessage(MessageUtils.get(CommonI18Constant.com_siti_delete_failed));
		}
		Gson gson = new Gson();
		return gson.toJson(rr);
	}
	
	@RequestMapping(value="/update",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String update(@RequestBody String json){
		ReturnResult rr = new ReturnResult();
		try{
			userBiz.update(json);
			rr.setCode(CommonConstant.CODE_OK);
			rr.setSuccess(true);
		}catch(Exception e){
			rr.setCode(CommonConstant.CODE_FAIL);
			rr.setSuccess(false);
			rr.setMessage(MessageUtils.get(CommonI18Constant.com_siti_update_failed));
		}
		Gson gson = new Gson();
		return gson.toJson(rr);
	}
	
	/**
	 * 查询
	 * @param userId 用户ID
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String search(Integer userId, String userName){
		ReturnResult rr = new ReturnResult();
		try{
			List<Map>userList = userBiz.search(userId, userName);
			rr.setCode(CommonConstant.CODE_OK);
			rr.setSuccess(true);
			rr.setData(userList);
		}catch(Exception e){
			rr.setCode(CommonConstant.CODE_FAIL);
			rr.setSuccess(false);
			rr.setMessage(MessageUtils.get(CommonI18Constant.com_siti_search_failed));
		}
		Gson gson = new Gson();
		return gson.toJson(rr);
	}
	
	@RequestMapping(value = "/updatePwd", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String updatePwd(Integer id, String oldPwd, String newPwd){
		ReturnResult rr = new ReturnResult();
		try{
			userBiz.updatePwd(id, oldPwd, newPwd);
			rr.setCode(CommonConstant.CODE_OK);
			rr.setSuccess(true);
		}catch(MyException mye){
			rr.setCode(CommonConstant.CODE_FAIL);
			rr.setSuccess(false);
			rr.setMessage(mye.getMessage());
		}catch(Exception e){
			rr.setCode(CommonConstant.CODE_FAIL);
			rr.setSuccess(false);
			rr.setMessage(MessageUtils.get(CommonI18Constant.com_siti_login_password_update_failed));
		}
		Gson gson = new Gson();
		return gson.toJson(rr);
	}
	
}
