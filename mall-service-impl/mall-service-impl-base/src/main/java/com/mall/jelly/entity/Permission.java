package com.mall.jelly.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 权限表
 */
@Data
public class Permission {
	private Integer id;
	@ApiModelProperty("权限名称  查询订单")
	private String permName;
	@ApiModelProperty("权限标识  showOrder")
	private String permTag;
	@ApiModelProperty("请求url  /showOrder")
	private String url;
}
