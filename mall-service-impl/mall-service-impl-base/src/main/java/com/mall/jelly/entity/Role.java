package com.mall.jelly.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 角色信息表
 */
@Data
public class Role {
	private Integer id;
	@ApiModelProperty("角色名称  admin")
	private String roleName;
	@ApiModelProperty("角色描述  管理员")
	private String roleDesc;
}
