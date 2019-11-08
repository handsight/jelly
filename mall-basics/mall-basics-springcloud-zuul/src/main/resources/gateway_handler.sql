/*
Navicat MySQL Data Transfer

Source Server         : kp测试数据库
Source Server Version : 50720
Source Host           : 192.168.0.2:3306
Source Database       : tcc

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2019-11-08 15:06:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `gateway_handler`
-- ----------------------------
DROP TABLE IF EXISTS `gateway_handler`;
CREATE TABLE `gateway_handler` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `handler_name` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
  `handler_id` varchar(20) CHARACTER SET utf8mb4 DEFAULT NULL,
  `prev_handler_id` varchar(20) CHARACTER SET utf8mb4 DEFAULT NULL,
  `next_handler_id` varchar(20) CHARACTER SET utf8mb4 DEFAULT NULL,
  `is_open` int(10) DEFAULT NULL,
  `version` int(10) DEFAULT NULL,
  `created_by` varchar(20) DEFAULT NULL,
  `updated_by` varchar(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `updat_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1 COMMENT='网关责任链表';

-- ----------------------------
-- Records of gateway_handler
-- ----------------------------
INSERT INTO `gateway_handler` VALUES ('1', 'API接口限流', 'currentLimitHandler', null, 'blacklistHandler', '1', null, null, null, null, null);
INSERT INTO `gateway_handler` VALUES ('2', '黑名单拦截', 'blacklistHandler', 'currentLimitHandler', 'toVerifyMapHandler', '1', null, null, null, null, null);
INSERT INTO `gateway_handler` VALUES ('3', 'API接口参数验证签名', 'toVerifyMapHandler', 'blacklistHandler', 'apiAuthorityHandler', '1', null, null, null, null, null);
INSERT INTO `gateway_handler` VALUES ('4', 'API接口验证token', 'apiAuthorityHandler', 'toVerifyMapHandler', null, '1', null, null, null, null, null);
