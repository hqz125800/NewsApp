CREATE TABLE `yule` (
  `newspic` blob,
  `newsid` int(11) NOT NULL auto_increment,
  `newstitle` blob,
  `newsauthor` blob,
  `newscontent` blob,
  `newsurl` char(130) default NULL,
  `newsdate` blob,
  PRIMARY KEY  (`newsid`)
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8;