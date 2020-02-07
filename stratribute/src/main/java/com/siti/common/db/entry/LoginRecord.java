package com.siti.common.db.entry;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @author zhangt
 */

@Data
@Entity
@Table(name = "sys_login_record", catalog = "pipepile")
public class LoginRecord implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	/**
	 * 用户id
	 */
	@Column(name = "user_id")
	private Integer userId; 
	
	/**
	 * 登录时间
	 */
	@Column(name = "login_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date loginTime;

}
