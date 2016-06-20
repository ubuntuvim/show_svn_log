/*
Navicat MySQL Data Transfer

Source Server         : localhost_123123
Source Server Version : 50087
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50087
File Encoding         : 65001

Date: 2016-06-20 13:30:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for svn_log
-- ----------------------------
DROP TABLE IF EXISTS `svn_log`;
CREATE TABLE `svn_log` (
  `id` int(11) NOT NULL auto_increment,
  `changeFilePath` varchar(500) default NULL,
  `revision` varchar(100) default NULL,
  `author` varchar(200) default NULL,
  `logMsg` varchar(500) default NULL,
  `changeDate` datetime default NULL,
  `opType` varchar(100) default NULL COMMENT '类型：新增；删除；更新；提交',
  `recoredFlag` varchar(2) default NULL COMMENT '记录标记：1-最新；0-作废',
  `functionName` varchar(500) default NULL COMMENT '日志对应功能（只记录com/szkingdom/opp/atom/business包对应的功能）',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=164115 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for svn_settings
-- ----------------------------
DROP TABLE IF EXISTS `svn_settings`;
CREATE TABLE `svn_settings` (
  `id` int(11) NOT NULL auto_increment,
  `svnBasePath` varchar(500) default NULL COMMENT 'svn项目路径',
  `username` varchar(200) default NULL COMMENT '登录svn的账户名称',
  `password` varchar(200) default NULL COMMENT '登录svn的密码',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
