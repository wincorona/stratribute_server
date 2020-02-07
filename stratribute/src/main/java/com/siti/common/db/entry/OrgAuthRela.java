package com.siti.common.db.entry;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author zhangt
 */

@Data
@Entity
@Table(name = "sys_org_auth_rela", catalog = "pipepile")
public class OrgAuthRela implements Serializable   {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id; //ID 自增
	
	@Column(name = "org_id")
	private Integer orgId; //组织ID
	
	@Column(name = "auth_id")
	private Integer authId; //权限ID
	
	@Column(name = "create_auth")
	private Integer createAuth = 0; //添加权限 0:无权 1：有权，默认无权
	
	@Column(name = "read_auth")
	private Integer readAuth = 0; //查看权限 0:无权 1：有权,默认无权
	
	@Column(name = "update_auth")
	private Integer updateAuth = 0; //修改权限 0:无权 1：有权,默认无权
	
	@Column(name = "delete_auth")
	private Integer deleteAuth = 0; //删除权限 0:无权 1：有权,默认无权
	
}
