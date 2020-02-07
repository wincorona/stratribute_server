package com.siti.tool;

/**
 * 自定义异常类
 * @author zhangt
 *
 */
public class MyException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public MyException(String message)
	     {
	         super(message);
	     }
}
