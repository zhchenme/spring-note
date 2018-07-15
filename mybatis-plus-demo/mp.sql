/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50716
Source Host           : localhost:3306
Source Database       : mp

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2018-07-15 18:05:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tbl_employee
-- ----------------------------
DROP TABLE IF EXISTS `tbl_employee`;
CREATE TABLE `tbl_employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `last_name` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `gender` char(1) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_employee
-- ----------------------------
INSERT INTO `tbl_employee` VALUES ('1', 'Tom', 'tom@qq.com', '1', '22');
INSERT INTO `tbl_employee` VALUES ('2', 'Jerry', 'jerry@qq.com', '0', '25');
INSERT INTO `tbl_employee` VALUES ('3', 'Black', 'black@qq.com', '1', '30');
INSERT INTO `tbl_employee` VALUES ('4', 'White', 'white@qq.com', '0', '35');
INSERT INTO `tbl_employee` VALUES ('5', '李四', 'zc@qq.com', '1', '22');
INSERT INTO `tbl_employee` VALUES ('6', '张三', 'jas@qq.com', '1', '20');
INSERT INTO `tbl_employee` VALUES ('7', '张三', 'jas@qq.com', '1', '20');
INSERT INTO `tbl_employee` VALUES ('8', '张三', 'jas@qq.com', '1', '20');
INSERT INTO `tbl_employee` VALUES ('9', '张三', 'jas@qq.com', '1', '20');
