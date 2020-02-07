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
@Table(name = "sys_org", catalog = "pipepile")
public class Org implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "name")
	private String name; //组织名称
	
	@Column(name = "parent_org_id")
	private Integer parentOrgId;//上一层组织ID
	
	@Column(name = "des")
	private String des; //描述
	
}
