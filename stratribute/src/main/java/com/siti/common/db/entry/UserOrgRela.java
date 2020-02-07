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
@Table(name = "sys_user_org_rela", catalog = "pipepile")
public class UserOrgRela implements Serializable  {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "user_id")
	private Integer userId; //用户ID
	
	@Column(name = "org_id")
	private Integer orgId; //组织ID
}
