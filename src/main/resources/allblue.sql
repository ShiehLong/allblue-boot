/*
SQLyog Professional v12.08 (64 bit)
MySQL - 5.6.17 : Database - allblue
*********************************************************************
*/

#
# /*!40101 SET NAMES utf8 */;
#
# /*!40101 SET SQL_MODE=''*/;
#
# /*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
# /*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
# /*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
# /*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
# CREATE DATABASE /*!32312 IF NOT EXISTS*/`allblue` /*!40100 DEFAULT CHARACTER SET latin1 */;
#
# USE `allblue`;
#
# /*Table structure for table `blue_customer` */
#
# DROP TABLE IF EXISTS `blue_customer`;
#
# CREATE TABLE `blue_customer` (
#   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '客户id 主键自增长ID',
#   `customer_name` varchar(30) NOT NULL DEFAULT '' COMMENT '客户名字',
#   `mobile` varchar(20) NOT NULL DEFAULT '0' COMMENT '手机账号',
#   `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '用户密码',
#   `sex` tinyint(1) NOT NULL DEFAULT '0' COMMENT '性别：0：未知， 1 ：男， 2：女',
#   `effective` tinyint(1) NOT NULL DEFAULT '0' COMMENT '客户有效性：0：未验证， 1 ：验证无效， 2：验证有效',
#   `region` tinyint(1) NOT NULL DEFAULT '0' COMMENT '组区域：0：未知， 1 ：上海， 2：北京',
#   `bank_card` varchar(30) NOT NULL DEFAULT '' COMMENT '银行卡',
#   `idcard_num` varchar(30) NOT NULL DEFAULT '' COMMENT '身份证',
#   `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
#   `deleted_at` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除，0否，大于0是删除（大于0是存的删除的时间戳）',
#   `belong_user_id` int(11) DEFAULT NULL COMMENT '归属id',
#   `group_id` int(11) DEFAULT NULL COMMENT '归属组id',
#   PRIMARY KEY (`id`)
# ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='客户表';
#
# /*Data for the table `blue_customer` */
#
# insert  into `blue_customer`(`id`,`customer_name`,`mobile`,`password`,`sex`,`effective`,`region`,`bank_card`,`idcard_num`,`remark`,`deleted_at`,`belong_user_id`,`group_id`) values (1,'pcz','18221531111','111111',1,2,1,'888888','888888','',0,NULL,NULL);
#
# /*Table structure for table `blue_user` */
#
# DROP TABLE IF EXISTS `blue_user`;
#
# CREATE TABLE `blue_user` (
#   `id` int(11) NOT NULL AUTO_INCREMENT,
#   `username` varchar(255) COLLATE utf8_unicode_ci NOT NULL UNIQUE,
#   `email` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
#   `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
#   `photo` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
#   PRIMARY KEY (`id`),
# ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
#
#
# /*Data for the table `blue_user` */
#
# /*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
# /*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
# /*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
# /*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
#
# CREATE TABLE `role` (
#   `id` int(11) NOT NULL AUTO_INCREMENT,
#   `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
#   `sex` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
#   `age` int(3) NOT NULL,
#   `pic` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
#   `description` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
#   `video` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
#   PRIMARY KEY (`id`)
# ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/* =============用户角色功能表====================*/

DROP TABLE IF EXISTS blue_user;
CREATE TABLE `blue_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) NOT NULL UNIQUE COMMENT '用户名称',
  `email` varchar(64) NOT NULL COMMENT '邮箱',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `photo` varchar(64) DEFAULT NULL COMMENT '头像',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `creator` varchar(255) NOT NULL COMMENT '创建者',
  `modifier` varchar(255) NOT NULL COMMENT '修改人',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户表';


DROP TABLE IF EXISTS blue_role;
CREATE TABLE `blue_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `code` varchar(50) DEFAULT NULL COMMENT '角色编码',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `creator` varchar(255) NOT NULL COMMENT '创建者',
  `modifier` varchar(255) NOT NULL COMMENT '修改人',
  `remark` longtext COMMENT '角色描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 CHARSET=utf8 COMMENT='角色表';


DROP TABLE IF EXISTS blue_system;
CREATE TABLE `blue_system` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(200) DEFAULT NULL COMMENT '菜单编号，唯一键',
  `name` varchar(100) NOT NULL COMMENT '名字',
  `url` varchar(400) DEFAULT NULL COMMENT 'url',
  `level` smallint(6) DEFAULT NULL COMMENT '级别',
  `parent_code` varchar(200) DEFAULT NULL COMMENT '父编号',
  `sort_id` int(11) DEFAULT NULL COMMENT '排序',
  `status` tinyint(1) NOT NULL COMMENT '是否有效',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `creator` varchar(255) DEFAULT NULL COMMENT '创建者',
  `modifier` varchar(255) DEFAULT NULL COMMENT '修改人',
  `remark` varchar(500) DEFAULT NULL COMMENT '菜单描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1  CHARSET=utf8 COMMENT='菜单表';

DROP TABLE IF EXISTS blue_role_system;
CREATE TABLE `blue_role_system` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `system_code` varchar(200) DEFAULT NULL COMMENT '菜单编号',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色id',
  `creator` varchar(255) NOT NULL COMMENT '创建者',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 CHARSET=utf8 COMMENT='角色菜单关系表';


DROP TABLE IF EXISTS blue_user_role;
CREATE TABLE `blue_user_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(64) DEFAULT NULL COMMENT '用户名称',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色id',
  `creator` varchar(255) NOT NULL COMMENT '创建者',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 CHARSET=utf8 COMMENT='用户角色关系表';

DROP TABLE IF EXISTS persistent_logins;
CREATE TABLE `persistent_logins` (
  `username` varchar(64) not null,
  `series` varchar(64) primary key,
  `token` varchar(64) not null,
  `last_used` timestamp not null
  )ENGINE=InnoDB CHARSET=utf8 COMMENT='用户Token表';