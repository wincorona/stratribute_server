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
import com.siti.common.sys.biz.OrgAuthRelaBiz;
import com.siti.tool.CommonConstant;
import com.siti.tool.CommonI18Constant;
import com.siti.tool.MessageUtils;
import com.siti.tool.MyException;
import com.siti.tool.ReturnResult;

/**
 * @author zhangt
 */

@Controller
@RequestMapping("orgAuthRela")
public class OrgAuthRelaCtrl {
	
	@Autowired
	OrgAuthRelaBiz orgAuthRelaBiz;
	
	/**
	 * 查询
	 * Integer orgId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/searchByOrgId",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String searchByOrgId(Integer orgId){
		ReturnResult rr = new ReturnResult();
		try{
			List<Map> map = orgAuthRelaBiz.searchByOrgId(orgId);
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
	 * 修改组织的权限
	 * @param json
	 * @return
	 */
	@RequestMapping(value="/update",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String update(@RequestBody String json){
		ReturnResult rr = new ReturnResult();
		try{
			orgAuthRelaBiz.upadte(json);
			rr.setCode(CommonConstant.CODE_OK);
			rr.setSuccess(true);
		}catch(MyException mye){
			rr.setCode(CommonConstant.CODE_FAIL);
			rr.setSuccess(false);
			rr.setMessage(mye.getMessage());
		}catch(Exception e){
			rr.setCode(CommonConstant.CODE_FAIL);
			rr.setSuccess(false);
			rr.setMessage(MessageUtils.get(CommonI18Constant.com_siti_update_failed));
		}
		Gson gson = new Gson();
		String retJson = gson.toJson(rr);
		return retJson;
	}
	
}
