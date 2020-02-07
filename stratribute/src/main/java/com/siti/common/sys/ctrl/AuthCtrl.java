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
import com.siti.common.db.entry.Auth;
import com.siti.common.sys.biz.AuthBiz;
import com.siti.tool.CommonConstant;
import com.siti.tool.CommonI18Constant;
import com.siti.tool.MessageUtils;
import com.siti.tool.ReturnResult;

/**
 * @author zhangt
 */
@Controller
@RequestMapping("auth")
public class AuthCtrl {
	@Autowired
	private AuthBiz authBiz;
	
	private static final Logger logger = Logger.getLogger(AuthCtrl.class);
	
	/**
	 * 根据用户ID获取合法的各项权限，给前端渲染界面用
	 * @param userId 用户ID
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getValidAuthByUserId", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getValidAuthByUserIdCtrl(Integer userId){
		ReturnResult rr = new ReturnResult();
		try{
			List<Map> allAuthList = authBiz.getValidAuthByUserIdBiz(userId);
			rr.setCode(CommonConstant.CODE_OK);
			rr.setSuccess(true);
			rr.setData(allAuthList);
		}catch(Exception e){
			e.printStackTrace();
			rr.setCode(CommonConstant.CODE_FAIL);
			rr.setSuccess(false);
			rr.setMessage(MessageUtils.get(CommonI18Constant.com_siti_search_failed));
		}
		Gson gson = new Gson();
		String json = gson.toJson(rr);
		return json;
	}
	
	/**
	 * 根据用户ID获取各项权限，给前端渲染界面用
	 * @param userId 用户ID
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getAllAuthByUserId", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getAllAuthByUserIdCtrl(Integer userId){
		ReturnResult rr = new ReturnResult();
		try{
			List<Map> allAuthList = authBiz.getAllAuthByUserIdBiz(userId);
			rr.setCode(CommonConstant.CODE_OK);
			rr.setSuccess(true);
			rr.setData(allAuthList);
		}catch(Exception e){
			e.printStackTrace();
			rr.setCode(CommonConstant.CODE_FAIL);
			rr.setSuccess(false);
			rr.setMessage(MessageUtils.get(CommonI18Constant.com_siti_search_failed));
		}
		Gson gson = new Gson();
		String json = gson.toJson(rr);
		return json;
	}
	
	/**
	 * 删除
	 * @param userId 用户ID
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String delete(Integer id){
		ReturnResult rr = new ReturnResult();
		try{
			authBiz.delete(id);
			rr.setCode(CommonConstant.CODE_OK);
			rr.setSuccess(true);
		}catch(Exception e){
			rr.setCode(CommonConstant.CODE_FAIL);
			rr.setSuccess(false);
			rr.setMessage(MessageUtils.get(CommonI18Constant.com_siti_delete_failed));
			//logger.error("删除失败： " + e);
		}
		Gson gson = new Gson();
		String json = gson.toJson(rr);
		return json;
	}
	
	/**
	 * 新建
	 * @param Auth auth
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String save(@RequestBody Auth auth){
		ReturnResult rr = new ReturnResult();
		try{
			authBiz.save(auth);
			rr.setCode(CommonConstant.CODE_OK);
			rr.setSuccess(true);
		}catch(Exception e){
			rr.setCode(CommonConstant.CODE_FAIL);
			rr.setSuccess(false);
			rr.setMessage(MessageUtils.get(CommonI18Constant.com_siti_add_failed));
		}
		Gson gson = new Gson();
		String json = gson.toJson(rr);
		return json;
	}
	
	/**
	 * 修改，不会对parentAuthId字段做修改
	 * @param Auth auth
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String update(@RequestBody Auth auth){
		ReturnResult rr = new ReturnResult();
		try{
			authBiz.update(auth);
			rr.setCode(CommonConstant.CODE_OK);
			rr.setSuccess(true);
		}catch(Exception e){
			rr.setCode(CommonConstant.CODE_FAIL);
			rr.setSuccess(false);
			rr.setMessage(MessageUtils.get(CommonI18Constant.com_siti_update_failed));
		}
		Gson gson = new Gson();
		String json = gson.toJson(rr);
		return json;
	}
	
	
}
