package com.mall.jelly.handler.mapper.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * GatewayHandler
 *
 */
@Data
public class GatewayHandlerEntity implements Serializable, Cloneable {
	/** 主键ID */
	@Id
	private Integer id;
	/** handler名称 */
	private String handlerName;
	/** handler主键id */
	private String handlerId;
	/** 上一个handler */
	private String prevHandlerId;

	/** 下一个handler */
	private String nextHandlerId;
	/** 是否打开 */
	private Integer isOpen;
	/** 乐观锁 */
	private Integer version;
	/** 创建人 */
	private String createdBy;
	/** 创建时间 */
	private Date createTime;
	/** 更新人 */
	private String updatedBy;
	/** 更新时间 */
	private Date updatTime;

}

 /*
		 *SET FOREIGN_KEY_CHECKS=0;
		 *
		 *------------------------------
		 *--Table structure for`gateway_handler`
		 *------------------------------
		 *DROP TABLE IF EXISTS`gateway_handler`;
		 *CREATE TABLE`gateway_handler`(
		 *`id`int(20)NOT NULL AUTO_INCREMENT,
		 *`handler_name`varchar(50)CHARACTER SET utf8mb4 DEFAULT NULL,
		 *`handler_id`varchar(20)CHARACTER SET utf8mb4 DEFAULT NULL,
		 *`prev_handler_id`varchar(20)CHARACTER SET utf8mb4 DEFAULT NULL,
		 *`next_handler_id`varchar(20)CHARACTER SET utf8mb4 DEFAULT NULL,
		 *`is_open`int(10)DEFAULT NULL,
		 *`version`int(10)DEFAULT NULL,
		 *`created_by`varchar(20)DEFAULT NULL,
		 *`updated_by`varchar(20)DEFAULT NULL,
		 *`create_time`datetime DEFAULT NULL,
		 *`updat_time`datetime DEFAULT NULL,
		 *PRIMARY KEY(`id`)
		 *)ENGINE=InnoDB AUTO_INCREMENT=5DEFAULT CHARSET=latin1 COMMENT='网关责任链表';
		 *
		 *------------------------------
		 *--Records of gateway_handler
		 *------------------------------
		 *INSERT INTO`gateway_handler`VALUES('1','API接口限流','currentLimitHandler',null,'blacklistHandler','1',null,null,null,null,null);
		 *INSERT INTO`gateway_handler`VALUES('2','黑名单拦截','blacklistHandler','currentLimitHandler','toVerifyMapHandler','1',null,null,null,null,null);
		 *INSERT INTO`gateway_handler`VALUES('3','API接口参数验证签名','toVerifyMapHandler','blacklistHandler','apiAuthorityHandler','1',null,null,null,null,null);
		 *INSERT INTO`gateway_handler`VALUES('4','API接口验证token','apiAuthorityHandler','toVerifyMapHandler',null,'1',null,null,null,null,null);
 */
