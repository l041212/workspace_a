/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : crm_

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-11-14 09:32:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_schedule
-- ----------------------------
DROP TABLE IF EXISTS `t_schedule`;
CREATE TABLE `t_schedule` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `shift_id` varchar(32) NOT NULL DEFAULT '' COMMENT '班次id',
  `staff_id` varchar(32) NOT NULL DEFAULT '' COMMENT '员工id',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态{0:''可用'',1:''删除''}',
  `date` date NOT NULL COMMENT '日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : crm_

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-11-14 09:33:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_shift
-- ----------------------------
DROP TABLE IF EXISTS `t_shift`;
CREATE TABLE `t_shift` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '名称',
  `period` varchar(64) NOT NULL DEFAULT '' COMMENT '时段',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态{0:''可用'',1:''删除''}',
  `description` varchar(256) NOT NULL DEFAULT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_shift
-- ----------------------------
INSERT INTO `t_shift` VALUES ('1', '早班一', 'am 07:00 - pm 02:00', '0', '');
INSERT INTO `t_shift` VALUES ('2', '早班二', 'am 08:00 ~ pm 03:00', '0', '');
INSERT INTO `t_shift` VALUES ('3', '晚班一', 'pm 02:00 ~ pm 09:00', '0', '');
INSERT INTO `t_shift` VALUES ('4', '晚班三', 'pm 02:45 ~ pm 09:45', '0', '');
INSERT INTO `t_shift` VALUES ('5', '晚班二', 'pm 04:30 ~ pm 12:00', '0', '');
INSERT INTO `t_shift` VALUES ('6', '休息', '无', '0', '');
