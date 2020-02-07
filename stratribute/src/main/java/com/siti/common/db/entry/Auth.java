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
@Table(name = "sys_auth", catalog = "pipepile")
public class Auth implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "label")
	private String label; //权限名称
	
	@Column(name = "label_en")
	private String labelEn; //权限名称英文
	
	@Column(name = "menu_url")
	private String menuUrl; //唯一标识符
	
	@Column(name = "sort")
	private Integer sort; //排序
	
	@Column(name = "parent_auth_id")
	private Integer parentAuthId; //父节点ID
	
	@Column(name = "des")
	private String des; //描述
	
	@Column(name = "icon")
	private String icon; //图标
	
	
}
