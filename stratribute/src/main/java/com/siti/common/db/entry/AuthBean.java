package com.siti.common.db.entry;

import lombok.Data;

/**
 * @author zhangt
 */

@Data
public class AuthBean {
	private Integer id;  //sys_org_auth_rela表中的id
	private Integer orgId; //组织ID
	private Integer authId; //权限ID
	private String label; //权限名
	private String labelEn; //权限英文名
	private Integer deleteAuth; //删除权限
	private Integer updateAuth; //更新权限
	private Integer readAuth; //查询权限
	private Integer createAuth; //添加权限
}
