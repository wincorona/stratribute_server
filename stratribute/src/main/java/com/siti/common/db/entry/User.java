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
@Table(name = "sys_user", catalog = "pipepile")
public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "user_name")
	private String userName;//用户名

	@Column(name = "password")
	private String password;//密码
	
	@Column(name = "real_name")
	private String realName;//真实名
	
	@Column(name = "cell_phone")
	private String cellPhone;//手机号
	
	@Column(name = "address")
	private String address;//地址
	
}
