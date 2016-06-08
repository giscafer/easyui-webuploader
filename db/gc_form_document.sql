/*
Navicat MySQL Data Transfer

Source Server         : mysql_local
Source Server Version : 50621
Source Host           : localhost:3306
Source Database       : gzfzjcht

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2016-06-08 19:24:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for gc_form_document
-- ----------------------------
DROP TABLE IF EXISTS `gc_form_document`;
CREATE TABLE `gc_form_document` (
  `obejctid` int(11) NOT NULL AUTO_INCREMENT,
  `s_plpath` varchar(254) DEFAULT NULL COMMENT '父目录',
  `s_lpath` varchar(254) DEFAULT NULL COMMENT '逻辑目录',
  `d_upsize` varchar(200) DEFAULT NULL COMMENT '文档大小',
  `s_name` varchar(254) DEFAULT NULL COMMENT '文档名称',
  `s_thumbnail` varchar(254) DEFAULT NULL COMMENT '图片缩略图路径',
  `i_jb` int(11) DEFAULT NULL COMMENT '机构级别',
  `s_systemname` varchar(254) DEFAULT NULL COMMENT '系统名称',
  `s_uptime` varchar(254) DEFAULT NULL COMMENT '上传时间',
  `i_version` int(11) DEFAULT '1',
  `s_type` varchar(254) DEFAULT NULL COMMENT '文档类型',
  `s_upip` varchar(254) DEFAULT NULL COMMENT '上传IP',
  `s_upuser` varchar(254) DEFAULT NULL COMMENT '上传人',
  `s_url` varchar(254) DEFAULT NULL COMMENT '文档URL',
  PRIMARY KEY (`obejctid`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
