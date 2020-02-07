package com.siti.tool;

import lombok.Data;

/**
 * @author zhangt
 */

@Data
public class ReturnResult {
	private int		code = CommonConstant.CODE_OK;	
	private boolean success = true; //前台业务逻辑是否执行  true:执行 false:不执行， 默认值为true
	private Object	data;
	private String	message;		//如果错误返回错误信息
	private String	token;
}
