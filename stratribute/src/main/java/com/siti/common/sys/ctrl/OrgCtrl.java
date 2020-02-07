package com.siti.common.sys.ctrl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.siti.JacksonJson;
import com.siti.common.db.entry.Org;
import com.siti.common.sys.biz.OrgBiz;
import com.siti.tool.CommonConstant;
import com.siti.tool.CommonI18Constant;
import com.siti.tool.MessageUtils;
import com.siti.tool.MyException;
import com.siti.tool.ReturnResult;

/**
 * @author zhangt
 */

@Controller
@RequestMapping("org")
public class OrgCtrl {
	@Autowired
	private OrgBiz orgBiz;
	
	/**
	 * 新建
	 * @param org
	 * @return
	 */
	@RequestMapping(value="/save",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String save(@RequestBody Org org){
		ReturnResult rr = new ReturnResult();
		try{
			orgBiz.save(org);
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
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delete",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String delete(Integer id){
		ReturnResult rr = new ReturnResult();
		try{
			orgBiz.delete(id);
			rr.setCode(CommonConstant.CODE_OK);
			rr.setSuccess(true);
		} catch(MyException mye){
			rr.setCode(CommonConstant.CODE_FAIL);
			rr.setSuccess(false);
			rr.setMessage(mye.getMessage());
		} catch (Exception e) {
			rr.setCode(CommonConstant.CODE_FAIL);
			rr.setSuccess(false);
			rr.setMessage(MessageUtils.get(CommonI18Constant.com_siti_delete_failed));
		}
		Gson gson = new Gson();
		String json = gson.toJson(rr);
		return json;
	}
	
	/**
	 * 修改，不能修改自己的父节点
	 * @param org
	 * @return
	 */
	@RequestMapping(value="/update",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String update(@RequestBody Org org){
		ReturnResult rr = new ReturnResult();
		try{
			orgBiz.update(org);
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
	
	/**
	 * 查询
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/search",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String search(Integer userId){
		ReturnResult rr = new ReturnResult();
		try{
			List<Map> map = orgBiz.search(userId);
			rr.setData(map);
			rr.setCode(CommonConstant.CODE_OK);
			rr.setSuccess(true);
		}catch(Exception e){
			rr.setCode(CommonConstant.CODE_FAIL);
			rr.setSuccess(false);
			rr.setMessage(MessageUtils.get(CommonI18Constant.com_siti_search_failed));
		}
		Gson gson = new Gson();
		String json = gson.toJson(rr);
		return json;
	}
	
	
	/**
	 * 查询所有，返回不嵌套
	 * @return
	 */
	@RequestMapping(value="/searchAll",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String searchAll(){
		ReturnResult rr = new ReturnResult();
		try{
			List<Org> map = orgBiz.searchAll();
			rr.setData(map);
			rr.setCode(CommonConstant.CODE_OK);
			rr.setSuccess(true);
		}catch(Exception e){
			rr.setCode(CommonConstant.CODE_FAIL);
			rr.setSuccess(false);
			rr.setMessage(MessageUtils.get(CommonI18Constant.com_siti_search_failed));
		}
		Gson gson = new Gson();
		String json = gson.toJson(rr);
		return json;
	}
	
	/**
	 * 按组织名查询组织
	 * @param orgName
	 * @return
	 */
	@RequestMapping(value="/searchByOrgName",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String searchByOrgName(String orgName){
		ReturnResult rr = new ReturnResult();
		try{
			List<Org> map = orgBiz.searchByOrgName(orgName);
			rr.setData(map);
			rr.setCode(CommonConstant.CODE_OK);
			rr.setSuccess(true);
		}catch(Exception e){
			rr.setCode(CommonConstant.CODE_FAIL);
			rr.setSuccess(false);
			rr.setMessage(MessageUtils.get(CommonI18Constant.com_siti_search_failed));
		}
		Gson gson = new Gson();
		String json = gson.toJson(rr);
		return json;
	}
	
	/**
	 * 根据组织id查询该组织及其子组织
	 * @param orgId 组织id
	 * @return 不嵌套
	 */
	@RequestMapping(value="/searchOrgIdsByOrgId",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String searchOrgIdsByOrgId(Integer orgId){
		ReturnResult rr = new ReturnResult();
		try{
			List<Org> map = orgBiz.searchOrgIdsByOrgId(orgId);
			rr.setData(map);
			rr.setCode(CommonConstant.CODE_OK);
			rr.setSuccess(true);
		}catch(Exception e){
			rr.setCode(CommonConstant.CODE_FAIL);
			rr.setSuccess(false);
			rr.setMessage(MessageUtils.get(CommonI18Constant.com_siti_search_failed));
		}
		return JacksonJson.packageJsonClass(rr);
	}
	
	/**
	 * 根据用户id查询所有组织（用户所属组织以及子组织）
	 * @param userId 用户id
	 * @return
	 */
	@RequestMapping(value="/searchOrgsByUserId",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String searchOrgsByUserId(Integer userId){
		ReturnResult rr = new ReturnResult();
		try{
			List<Org> map = orgBiz.searchOrgsByUserId(userId);
			rr.setData(map);
			rr.setCode(CommonConstant.CODE_OK);
			rr.setSuccess(true);
		}catch(Exception e){
			rr.setCode(CommonConstant.CODE_FAIL);
			rr.setSuccess(false);
			rr.setMessage(MessageUtils.get(CommonI18Constant.com_siti_search_failed));
		}
		return JacksonJson.packageJsonClass(rr);
	}
}
