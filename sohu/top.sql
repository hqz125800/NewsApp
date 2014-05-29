/*
MySQL Data Transfer
Source Host: localhost
Source Database: cnsoftbei
Target Host: localhost
Target Database: cnsoftbei
Date: 2014/5/29 15:24:18
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for top
-- ----------------------------
CREATE TABLE `top` (
  `newspic` text,
  `newsid` text,
  `newstitle` text,
  `newsauthor` text,
  `newscontent` text,
  `newsurl` text,
  `id` int(11) NOT NULL auto_increment,
  `newsdate` text,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for top
-- ----------------------------
CREATE TABLE `finance` (
  `newspic` text,
  `newsid` text,
  `newstitle` text,
  `newsauthor` text,
  `newscontent` text,
  `newsurl` text,
  `id` int(11) NOT NULL auto_increment,
  `newsdate` text,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for top
-- ----------------------------
CREATE TABLE `military` (
  `newspic` text,
  `newsid` text,
  `newstitle` text,
  `newsauthor` text,
  `newscontent` text,
  `newsurl` text,
  `id` int(11) NOT NULL auto_increment,
  `newsdate` text,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for top
-- ----------------------------
CREATE TABLE `tiyu` (
  `newspic` text,
  `newsid` text,
  `newstitle` text,
  `newsauthor` text,
  `newscontent` text,
  `newsurl` text,
  `id` int(11) NOT NULL auto_increment,
  `newsdate` text,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for top
-- ----------------------------
CREATE TABLE `yule` (
  `newspic` text,
  `newsid` text,
  `newstitle` text,
  `newsauthor` text,
  `newscontent` text,
  `newsurl` text,
  `id` int(11) NOT NULL auto_increment,
  `newsdate` text,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for top
-- ----------------------------
CREATE TABLE `fashion` (
  `newspic` text,
  `newsid` text,
  `newstitle` text,
  `newsauthor` text,
  `newscontent` text,
  `newsurl` text,
  `id` int(11) NOT NULL auto_increment,
  `newsdate` text,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for top
-- ----------------------------
CREATE TABLE `more` (
  `newspic` text,
  `newsid` text,
  `newstitle` text,
  `newsauthor` text,
  `newscontent` text,
  `newsurl` text,
  `id` int(11) NOT NULL auto_increment,
  `newsdate` text,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;