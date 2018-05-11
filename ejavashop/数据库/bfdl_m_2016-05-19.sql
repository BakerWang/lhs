# ************************************************************
# Sequel Pro SQL dump
# Version 3408
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 123.56.164.182 (MySQL 5.5.46)
# Database: bfdl_m
# Generation Time: 2016-05-19 01:16:57 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table pc_index_banner
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pc_index_banner`;

CREATE TABLE `pc_index_banner` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL COMMENT '标题',
  `link_url` varchar(200) NOT NULL DEFAULT '' COMMENT '链接地址',
  `image` varchar(200) NOT NULL COMMENT '图片',
  `order_no` int(2) unsigned NOT NULL COMMENT '排序',
  `start_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '展示开始时间',
  `end_time` datetime NOT NULL DEFAULT '9999-12-31 00:00:00' COMMENT '展示结束时间',
  `status` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '状态1使用2预使用3不使用',
  `create_user_id` int(10) unsigned NOT NULL,
  `create_user_name` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_user_id` int(10) unsigned NOT NULL,
  `update_user_name` varchar(50) NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='PC端首页轮播图';

LOCK TABLES `pc_index_banner` WRITE;
/*!40000 ALTER TABLE `pc_index_banner` DISABLE KEYS */;

INSERT INTO `pc_index_banner` (`id`, `title`, `link_url`, `image`, `order_no`, `start_time`, `end_time`, `status`, `create_user_id`, `create_user_name`, `create_time`, `update_user_id`, `update_user_name`, `update_time`)
VALUES
	(1,'台湾茂谷柑','http://123.56.164.182:8080/front/product/1.html','/images/ad/e5cc2a13-e31e-41fe-9678-0ef580228e90.jpg',1,'2016-03-01 18:19:25','2020-03-01 18:19:27',1,2,'ejavashop','2016-03-02 18:16:59',2,'ejavashop','2016-03-02 18:19:23'),
	(2,'本来草莓','http://123.56.164.182:8080/front/product/2.html','/images/ad/a99c4b43-6137-4c80-ba37-ce9d549e036b.jpg',2,'2016-03-01 18:21:55','2020-03-01 18:21:57',1,2,'ejavashop','2016-03-02 18:19:18',2,'ejavashop','2016-03-02 18:19:26');

/*!40000 ALTER TABLE `pc_index_banner` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table pc_index_floor
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pc_index_floor`;

CREATE TABLE `pc_index_floor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '楼层名称',
  `order_no` int(2) NOT NULL COMMENT '楼层排序号',
  `master_image` varchar(200) NOT NULL COMMENT '楼层主图片',
  `master_link_url` varchar(200) NOT NULL DEFAULT '' COMMENT '楼层主图片链接地址',
  `adv_image` varchar(200) DEFAULT '' COMMENT '楼层上端广告图片',
  `adv_link_url` varchar(200) DEFAULT NULL COMMENT '楼层上端广告图片链接地址',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态1使用2预使用3不使用',
  `remark` varchar(200) DEFAULT '' COMMENT '楼层备注',
  `create_user_id` int(10) unsigned NOT NULL,
  `create_user_name` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_user_id` int(10) unsigned NOT NULL,
  `update_user_name` varchar(50) NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='PC端首页楼层表';

LOCK TABLES `pc_index_floor` WRITE;
/*!40000 ALTER TABLE `pc_index_floor` DISABLE KEYS */;

INSERT INTO `pc_index_floor` (`id`, `name`, `order_no`, `master_image`, `master_link_url`, `adv_image`, `adv_link_url`, `status`, `remark`, `create_user_id`, `create_user_name`, `create_time`, `update_user_id`, `update_user_name`, `update_time`)
VALUES
	(1,'电脑数码',1,'/images/ad/5bd0b351-5881-408c-b5ab-854f3ed821f5.jpg','	 http://123.56.164.182:8080/front/product/1.html','/images/ad/ba265877-1753-4b20-b3bc-dc398114ea2f.jpg','	 http://123.56.164.182:8080/front/product/16.html',1,'',2,'ejavashop','2016-03-02 18:26:25',2,'ejavashop','2016-03-02 18:38:12'),
	(2,'服装鞋包',2,'/images/ad/bfd7c0d4-dba3-45ca-8535-c5ebdf051017.jpg','http://123.56.164.182:8080/front/0-28-0-0-0-0-0-0-0-0.html','/images/ad/cf47c1de-dc82-4cf5-a4b2-c8f6e048ef26.jpg','http://123.56.164.182:8080/front/product/21.html',1,'',1,'admin','2016-03-06 23:06:08',1,'admin','2016-03-06 23:31:44');

/*!40000 ALTER TABLE `pc_index_floor` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table pc_index_floor_class
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pc_index_floor_class`;

CREATE TABLE `pc_index_floor_class` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `floor_id` int(11) unsigned NOT NULL COMMENT '所属楼层ID',
  `name` varchar(20) NOT NULL COMMENT '楼层分类名称',
  `order_no` int(2) NOT NULL COMMENT '楼层分类排序号',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态1使用2预使用3不使用',
  `remark` varchar(200) DEFAULT '' COMMENT '楼层分类备注',
  `create_user_id` int(10) unsigned NOT NULL,
  `create_user_name` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_user_id` int(10) unsigned NOT NULL,
  `update_user_name` varchar(50) NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='PC端首页楼层分类表';

LOCK TABLES `pc_index_floor_class` WRITE;
/*!40000 ALTER TABLE `pc_index_floor_class` DISABLE KEYS */;

INSERT INTO `pc_index_floor_class` (`id`, `floor_id`, `name`, `order_no`, `status`, `remark`, `create_user_id`, `create_user_name`, `create_time`, `update_user_id`, `update_user_name`, `update_time`)
VALUES
	(1,1,'电脑/平板',1,1,'',2,'ejavashop','2016-03-02 18:27:29',2,'ejavashop','2016-03-02 18:38:06'),
	(2,1,'办公/网络',2,1,'',2,'ejavashop','2016-03-02 18:28:02',2,'ejavashop','2016-03-02 18:38:03'),
	(3,2,'新春推荐',1,1,'',1,'admin','2016-03-06 23:07:17',1,'admin','2016-03-06 23:49:04'),
	(4,2,'特价女装',2,1,'',1,'admin','2016-03-06 23:07:45',1,'admin','2016-03-06 23:49:01');

/*!40000 ALTER TABLE `pc_index_floor_class` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table pc_index_floor_data
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pc_index_floor_data`;

CREATE TABLE `pc_index_floor_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `floor_class_id` int(10) unsigned NOT NULL COMMENT '所属楼层分类ID',
  `data_type` tinyint(1) unsigned NOT NULL COMMENT '数据类型：1商品2图片链接',
  `ref_id` int(10) unsigned DEFAULT NULL COMMENT '数据ID（data_type为1表示商品ID）',
  `title` varchar(200) DEFAULT NULL COMMENT '图片链接标题',
  `image` varchar(200) DEFAULT NULL COMMENT '图片地址',
  `link_url` varchar(200) DEFAULT NULL COMMENT '图片链接地址',
  `order_no` int(2) NOT NULL COMMENT '排序号',
  `remark` varchar(255) DEFAULT NULL COMMENT '数据说明',
  `create_user_id` int(10) unsigned NOT NULL,
  `create_user_name` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_user_id` int(10) unsigned NOT NULL,
  `update_user_name` varchar(50) NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='PC端首页楼层分类数据表';

LOCK TABLES `pc_index_floor_data` WRITE;
/*!40000 ALTER TABLE `pc_index_floor_data` DISABLE KEYS */;

INSERT INTO `pc_index_floor_data` (`id`, `floor_class_id`, `data_type`, `ref_id`, `title`, `image`, `link_url`, `order_no`, `remark`, `create_user_id`, `create_user_name`, `create_time`, `update_user_id`, `update_user_name`, `update_time`)
VALUES
	(1,1,1,1,'',NULL,NULL,1,'',2,'ejavashop','2016-03-02 18:28:30',2,'ejavashop','2016-03-02 18:28:30'),
	(2,1,1,2,'',NULL,NULL,2,'',2,'ejavashop','2016-03-02 18:28:30',2,'ejavashop','2016-03-02 18:32:35'),
	(3,1,1,3,'',NULL,NULL,3,'',2,'ejavashop','2016-03-02 18:28:30',2,'ejavashop','2016-03-02 18:32:36'),
	(4,1,1,4,'',NULL,NULL,4,'',2,'ejavashop','2016-03-02 18:28:30',2,'ejavashop','2016-03-02 18:32:37'),
	(5,1,1,5,'',NULL,NULL,5,'',2,'ejavashop','2016-03-02 18:28:30',2,'ejavashop','2016-03-02 18:32:38'),
	(6,1,1,6,'',NULL,NULL,6,'',2,'ejavashop','2016-03-02 18:28:30',2,'ejavashop','2016-03-02 18:32:45'),
	(7,1,1,7,'',NULL,NULL,7,'',2,'ejavashop','2016-03-02 18:28:30',2,'ejavashop','2016-03-02 18:32:44'),
	(8,1,1,8,'',NULL,NULL,8,'',2,'ejavashop','2016-03-02 18:28:30',2,'ejavashop','2016-03-02 18:32:47'),
	(9,2,1,9,'',NULL,NULL,1,'',2,'ejavashop','2016-03-02 18:32:20',2,'ejavashop','2016-03-02 18:32:20'),
	(10,2,1,10,'',NULL,NULL,2,'',2,'ejavashop','2016-03-02 18:32:20',2,'ejavashop','2016-03-02 18:44:28'),
	(11,2,1,11,'',NULL,NULL,3,'',2,'ejavashop','2016-03-02 18:32:20',2,'ejavashop','2016-03-02 18:44:29'),
	(12,2,1,12,'',NULL,NULL,4,'',2,'ejavashop','2016-03-02 18:32:20',2,'ejavashop','2016-03-02 18:44:30'),
	(13,2,1,13,'',NULL,NULL,5,'',2,'ejavashop','2016-03-02 18:32:20',2,'ejavashop','2016-03-02 18:44:30'),
	(14,2,1,14,'',NULL,NULL,6,'',2,'ejavashop','2016-03-02 18:32:20',2,'ejavashop','2016-03-02 18:44:32'),
	(15,2,1,15,'',NULL,NULL,7,'',2,'ejavashop','2016-03-02 18:32:20',2,'ejavashop','2016-03-02 18:44:32'),
	(16,2,1,16,'',NULL,NULL,8,'',2,'ejavashop','2016-03-02 18:32:20',2,'ejavashop','2016-03-02 18:44:33'),
	(17,3,1,18,'',NULL,NULL,1,'',1,'admin','2016-03-06 23:08:49',1,'admin','2016-03-06 23:08:49'),
	(18,3,1,19,'',NULL,NULL,2,'',1,'admin','2016-03-06 23:09:02',1,'admin','2016-03-06 23:09:02'),
	(19,3,1,20,'',NULL,NULL,3,'',1,'admin','2016-03-06 23:09:20',1,'admin','2016-03-06 23:09:20'),
	(20,3,1,21,'',NULL,NULL,4,'',1,'admin','2016-03-06 23:09:35',1,'admin','2016-03-06 23:09:35'),
	(21,3,1,22,'',NULL,NULL,5,'',1,'admin','2016-03-06 23:09:50',1,'admin','2016-03-06 23:09:50'),
	(22,3,1,23,'',NULL,NULL,6,'',1,'admin','2016-03-06 23:10:02',1,'admin','2016-03-06 23:10:02'),
	(25,4,1,25,'',NULL,NULL,1,'',1,'admin','2016-03-06 23:10:49',1,'admin','2016-03-06 23:10:49'),
	(26,4,1,24,'',NULL,NULL,2,'',1,'admin','2016-03-06 23:11:01',1,'admin','2016-03-06 23:11:01'),
	(27,4,1,23,'',NULL,NULL,3,'',1,'admin','2016-03-06 23:11:11',1,'admin','2016-03-06 23:11:11'),
	(28,4,1,22,'',NULL,NULL,4,'',1,'admin','2016-03-06 23:11:20',1,'admin','2016-03-06 23:11:20'),
	(29,4,1,21,'',NULL,NULL,5,'',1,'admin','2016-03-06 23:11:31',1,'admin','2016-03-06 23:11:31'),
	(30,4,1,20,'',NULL,NULL,6,'',1,'admin','2016-03-06 23:11:44',1,'admin','2016-03-06 23:11:44'),
	(31,4,1,19,'',NULL,NULL,7,'',1,'admin','2016-03-06 23:11:56',1,'admin','2016-03-06 23:11:56'),
	(32,4,1,18,'',NULL,NULL,8,'',1,'admin','2016-03-06 23:12:12',1,'admin','2016-03-06 23:12:12'),
	(34,3,1,28,'',NULL,NULL,1,'',1,'admin','2016-03-06 23:50:28',1,'admin','2016-03-06 23:50:28'),
	(35,3,1,27,'',NULL,NULL,1,'',1,'admin','2016-03-06 23:50:49',1,'admin','2016-03-06 23:50:49');

/*!40000 ALTER TABLE `pc_index_floor_data` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table pc_index_floor_patch
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pc_index_floor_patch`;

CREATE TABLE `pc_index_floor_patch` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `floor_id` int(10) unsigned NOT NULL COMMENT '所属楼层分类ID',
  `title` varchar(200) NOT NULL DEFAULT '' COMMENT '显示文字',
  `link_url` varchar(200) DEFAULT NULL COMMENT '链接地址',
  `order_no` int(2) NOT NULL COMMENT '排序号',
  `status` tinyint(1) NOT NULL COMMENT '状态1使用2预使用3不使用',
  `remark` varchar(255) DEFAULT NULL COMMENT '数据说明',
  `create_user_id` int(10) unsigned NOT NULL,
  `create_user_name` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_user_id` int(10) unsigned NOT NULL,
  `update_user_name` varchar(50) NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='PC端首页楼层碎屑表';

LOCK TABLES `pc_index_floor_patch` WRITE;
/*!40000 ALTER TABLE `pc_index_floor_patch` DISABLE KEYS */;

INSERT INTO `pc_index_floor_patch` (`id`, `floor_id`, `title`, `link_url`, `order_no`, `status`, `remark`, `create_user_id`, `create_user_name`, `create_time`, `update_user_id`, `update_user_name`, `update_time`)
VALUES
	(1,1,'笔记本','http://123.56.164.182:8080/front/0-9-0-0-0-0-0-0-0-0.html',1,1,'',2,'ejavashop','2016-03-02 18:36:03',2,'ejavashop','2016-03-02 18:37:56'),
	(2,1,'联想','http://123.56.164.182:8080/front/0-9-0-0-0-0-1-0-0-0.html',2,1,'',2,'ejavashop','2016-03-02 18:36:45',2,'ejavashop','2016-03-02 18:37:54'),
	(3,1,'商务办公','http://123.56.164.182:8080/front/0-9-0-0-0-0-0-0-0-0-79_3.html',3,1,'',2,'ejavashop','2016-03-02 18:37:25',2,'ejavashop','2016-03-02 18:37:51'),
	(4,1,'ThinkPad','http://123.56.164.182:8080/front/0-9-0-0-0-0-2-0-0-0.html',4,1,'',2,'ejavashop','2016-03-02 18:37:25',2,'ejavashop','2016-03-02 18:44:02'),
	(5,1,'游戏达人','http://123.56.164.182:8080/front/0-9-0-0-0-0-0-0-0-0-79_0.html',5,1,'',2,'ejavashop','2016-03-02 18:37:25',2,'ejavashop','2016-03-02 18:44:02'),
	(6,1,'校园学生','http://123.56.164.182:8080/front/0-9-0-0-0-0-0-0-0-0-79_1.html',6,1,'',2,'ejavashop','2016-03-02 18:37:25',2,'ejavashop','2016-03-02 18:44:03'),
	(7,1,'时尚轻薄','http://123.56.164.182:8080/front/0-9-0-0-0-0-0-0-0-0-79_2.html',7,1,'',2,'ejavashop','2016-03-02 18:37:25',2,'ejavashop','2016-03-02 18:44:04'),
	(8,1,'家庭影音','http://123.56.164.182:8080/front/0-9-0-0-0-0-0-0-0-0-79_4.html',8,1,'',2,'ejavashop','2016-03-02 18:37:25',2,'ejavashop','2016-03-02 18:44:05'),
	(9,1,'Apple','http://123.56.164.182:8080/front/0-9-0-0-0-0-5-0-0-0.html',9,1,'',2,'ejavashop','2016-03-02 18:37:25',2,'ejavashop','2016-03-02 18:44:07'),
	(10,1,'华硕','http://123.56.164.182:8080/front/0-9-0-0-0-0-3-0-0-0.html',10,1,'',2,'ejavashop','2016-03-02 18:37:25',2,'ejavashop','2016-03-02 18:44:09'),
	(11,1,'戴尔','http://123.56.164.182:8080/front/0-9-0-0-0-0-4-0-0-0.html',11,1,'',2,'ejavashop','2016-03-02 18:37:25',2,'ejavashop','2016-03-02 18:44:10'),
	(12,1,'宏碁','http://123.56.164.182:8080/front/0-9-0-0-0-0-6-0-0-0.html',12,1,'',2,'ejavashop','2016-03-02 18:37:25',2,'ejavashop','2016-03-02 18:44:11'),
	(13,2,'连衣裙','http://123.56.164.182:8080/front/0-28-0-0-0-0-0-0-0-0.html',1,1,'',1,'admin','2016-03-06 23:17:52',1,'admin','2016-03-06 23:48:41'),
	(14,2,'女装新品','http://123.56.164.182:8080/front/0-28-0-0-0-0-0-0-0-0.html',2,1,'',1,'admin','2016-03-06 23:18:56',1,'admin','2016-03-06 23:48:39'),
	(15,2,'莉菲姿','http://123.56.164.182:8080/front/search.html?keyword=%E8%8E%89%E8%8F%B2%E5%A7%BF',3,1,'',1,'admin','2016-03-06 23:19:44',1,'admin','2016-03-06 23:48:36'),
	(16,2,'韩版女装','http://123.56.164.182:8080/front/product/25.html',4,1,'',1,'admin','2016-03-06 23:20:35',1,'admin','2016-03-06 23:48:34'),
	(17,2,'小清新','http://123.56.164.182:8080/front/product/21.html',5,1,'',1,'admin','2016-03-06 23:21:30',1,'admin','2016-03-06 23:48:30'),
	(18,2,'气质公主','http://123.56.164.182:8080/front/product/22.html',6,1,'',1,'admin','2016-03-06 23:23:23',1,'admin','2016-03-06 23:48:27'),
	(19,2,'氧气美女','http://123.56.164.182:8080/front/product/18.html',7,1,'',1,'admin','2016-03-06 23:23:54',1,'admin','2016-03-06 23:48:25'),
	(20,2,'针织面料','http://123.56.164.182:8080/front/search.html?keyword=%E9%92%88%E7%BB%87%E9%9D%A2%E6%96%99',8,1,'',1,'admin','2016-03-06 23:25:09',1,'admin','2016-03-06 23:48:22'),
	(21,2,'春装新品','http://123.56.164.182:8080/front/product/19.html',9,1,'',1,'admin','2016-03-06 23:26:09',1,'admin','2016-03-06 23:48:20'),
	(22,2,'可爱','http://123.56.164.182:8080/front/product/24.html',10,1,'',1,'admin','2016-03-06 23:26:58',1,'admin','2016-03-06 23:48:17'),
	(23,2,'优雅休闲','http://123.56.164.182:8080/front/product/23.html',11,1,'',1,'admin','2016-03-06 23:27:46',1,'admin','2016-03-06 23:48:13'),
	(24,2,'英伦','http://123.56.164.182:8080/front/product/23.html',12,1,'',1,'admin','2016-03-06 23:28:05',1,'admin','2016-03-06 23:47:51');

/*!40000 ALTER TABLE `pc_index_floor_patch` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table pc_recommend
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pc_recommend`;

CREATE TABLE `pc_recommend` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `recommend_type` tinyint(1) unsigned NOT NULL COMMENT '数据类型：1首页热销商品2首页今日推荐',
  `product_id` int(11) unsigned NOT NULL COMMENT '商品ID',
  `order_no` int(2) NOT NULL COMMENT '排序号',
  `start_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '展示开始时间',
  `end_time` datetime NOT NULL DEFAULT '9999-12-31 00:00:00' COMMENT '展示结束时间',
  `status` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '状态1使用2预使用3不使用',
  `remark` varchar(255) DEFAULT NULL COMMENT '描述',
  `create_user_id` int(10) unsigned NOT NULL,
  `create_user_name` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_user_id` int(10) unsigned NOT NULL,
  `update_user_name` varchar(50) NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='PC推荐商品表';

LOCK TABLES `pc_recommend` WRITE;
/*!40000 ALTER TABLE `pc_recommend` DISABLE KEYS */;

INSERT INTO `pc_recommend` (`id`, `recommend_type`, `product_id`, `order_no`, `start_time`, `end_time`, `status`, `remark`, `create_user_id`, `create_user_name`, `create_time`, `update_user_id`, `update_user_name`, `update_time`)
VALUES
	(1,1,16,1,'2016-03-01 18:24:47','2020-03-01 18:24:51',1,'',2,'ejavashop','2016-03-02 18:22:09',2,'ejavashop','2016-03-02 18:23:29'),
	(2,1,15,2,'2016-03-01 18:25:10','2020-03-01 18:25:12',1,'',2,'ejavashop','2016-03-02 18:22:32',2,'ejavashop','2016-03-02 18:23:30'),
	(3,1,14,2,'2016-03-01 18:25:10','2020-03-01 18:25:12',1,'',2,'ejavashop','2016-03-02 18:22:32',2,'ejavashop','2016-03-02 18:23:31'),
	(4,1,13,2,'2016-03-01 18:25:10','2020-03-01 18:25:12',1,'',2,'ejavashop','2016-03-02 18:22:32',2,'ejavashop','2016-03-02 18:23:32'),
	(5,1,12,2,'2016-03-01 18:25:10','2020-03-01 18:25:12',1,'',2,'ejavashop','2016-03-02 18:22:32',2,'ejavashop','2016-03-02 18:23:32'),
	(6,1,11,2,'2016-03-01 18:25:10','2020-03-01 18:25:12',1,'',2,'ejavashop','2016-03-02 18:22:32',2,'ejavashop','2016-03-02 18:23:33');

/*!40000 ALTER TABLE `pc_recommend` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table pc_seller_index
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pc_seller_index`;

CREATE TABLE `pc_seller_index` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `seller_id` int(10) unsigned NOT NULL COMMENT '商家ID',
  `logo` varchar(200) DEFAULT '' COMMENT 'logo图片',
  `notice` text COMMENT '公告',
  `detail` text COMMENT '详细介绍',
  `create_user_id` int(10) unsigned NOT NULL,
  `create_user_name` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_user_id` int(10) unsigned NOT NULL,
  `update_user_name` varchar(50) NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='PC端商家首页信息';

LOCK TABLES `pc_seller_index` WRITE;
/*!40000 ALTER TABLE `pc_seller_index` DISABLE KEYS */;

INSERT INTO `pc_seller_index` (`id`, `seller_id`, `logo`, `notice`, `detail`, `create_user_id`, `create_user_name`, `create_time`, `update_user_id`, `update_user_name`, `update_time`)
VALUES
	(1,1,'/images/ad/ab3dc419-c0cb-4a13-9e56-93ca8132b4da.png','ejavashop最适合二次开发，ejavashop最适合二次开发，ejavashop最适合二次开发，ejavashop最适合二次开发，ejavashop最适合二次开发，ejavashop最适合二次开发，ejavashop最适合二次开发，ejavashop最适合二次开发，ejavashop最适合二次开发。','',1,'ejavashop','2016-03-02 19:11:33',1,'ejavashop','2016-03-02 19:11:33'),
	(2,2,'/images/ad/b34fef6c-704f-418f-a6a8-6d114e60a443.png','ejavashop最专业的电子商务提供商，最适合二次开发的电子商务系统，最注重细节的电子商务系统。','<p>ejavashop最专业的电子商务提供商，最适合二次开发的电子商务系统，最注重细节的电子商务系统。</p>',2,'wangpeng','2016-03-06 22:39:41',2,'wangpeng','2016-03-06 22:39:41');

/*!40000 ALTER TABLE `pc_seller_index` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table pc_seller_index_banner
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pc_seller_index_banner`;

CREATE TABLE `pc_seller_index_banner` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `seller_id` int(10) unsigned NOT NULL COMMENT '商家ID',
  `title` varchar(100) NOT NULL COMMENT '标题',
  `link_url` varchar(200) NOT NULL DEFAULT '' COMMENT '链接地址',
  `image` varchar(200) NOT NULL COMMENT '图片',
  `order_no` int(2) unsigned NOT NULL COMMENT '排序',
  `start_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '展示开始时间',
  `end_time` datetime NOT NULL DEFAULT '9999-12-31 00:00:00' COMMENT '展示结束时间',
  `status` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '状态1使用2预使用3不使用',
  `create_user_id` int(10) unsigned NOT NULL,
  `create_user_name` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_user_id` int(10) unsigned NOT NULL,
  `update_user_name` varchar(50) NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='PC端商家首页轮播图';

LOCK TABLES `pc_seller_index_banner` WRITE;
/*!40000 ALTER TABLE `pc_seller_index_banner` DISABLE KEYS */;

INSERT INTO `pc_seller_index_banner` (`id`, `seller_id`, `title`, `link_url`, `image`, `order_no`, `start_time`, `end_time`, `status`, `create_user_id`, `create_user_name`, `create_time`, `update_user_id`, `update_user_name`, `update_time`)
VALUES
	(1,1,'台湾茂谷柑','http://123.56.164.182:8080/front/product/1.html','/images/ad/33207f5e-8a54-4b3c-882d-6ff0fc0c5bbf.jpg',1,'2016-03-01 19:14:59','2020-03-01 19:15:01',1,1,'ejavashop','2016-03-02 19:12:24',1,'ejavashop','2016-03-02 23:33:41'),
	(2,1,'本来草莓','http://123.56.164.182:8080/front/product/2.html','/images/ad/e09acc38-e78d-47c7-8f6d-859f64da905c.jpg',2,'2016-03-01 19:15:29','2020-03-01 19:15:31',1,1,'ejavashop','2016-03-02 19:12:50',1,'ejavashop','2016-03-02 23:33:31'),
	(3,2,'连衣裙1','http://123.56.164.182:8080/front/product/21.html','/images/ad/51ce1013-72f0-420f-a89d-5103a9230b35.jpg',1,'2016-03-06 22:43:43','2020-03-06 22:43:45',1,2,'wangpeng','2016-03-06 22:41:06',2,'wangpeng','2016-03-06 22:46:42'),
	(4,2,'连衣裙2','http://123.56.164.182:8080/front/product/22.html','/images/ad/1297499e-81a9-47df-83ad-bb07a75afdb7.jpg',1,'2016-03-06 22:44:39','2020-03-06 22:44:41',1,2,'wangpeng','2016-03-06 22:42:01',2,'wangpeng','2016-03-06 22:42:40');

/*!40000 ALTER TABLE `pc_seller_index_banner` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table pc_seller_recommend
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pc_seller_recommend`;

CREATE TABLE `pc_seller_recommend` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `seller_id` int(10) unsigned NOT NULL COMMENT '商家ID',
  `title` varchar(100) NOT NULL COMMENT '推荐标题',
  `order_no` int(2) NOT NULL COMMENT '排序号',
  `status` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '状态1使用2预使用3不使用',
  `remark` varchar(255) DEFAULT NULL COMMENT '描述',
  `create_user_id` int(10) unsigned NOT NULL,
  `create_user_name` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_user_id` int(10) unsigned NOT NULL,
  `update_user_name` varchar(50) NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='PC端商家推荐类型表';

LOCK TABLES `pc_seller_recommend` WRITE;
/*!40000 ALTER TABLE `pc_seller_recommend` DISABLE KEYS */;

INSERT INTO `pc_seller_recommend` (`id`, `seller_id`, `title`, `order_no`, `status`, `remark`, `create_user_id`, `create_user_name`, `create_time`, `update_user_id`, `update_user_name`, `update_time`)
VALUES
	(1,1,'热销电子',2,1,NULL,1,'ejavashop','2016-03-02 19:13:47',1,'ejavashop','2016-03-02 23:35:48'),
	(2,1,'今日推荐',1,1,NULL,1,'ejavashop','2016-03-02 19:13:57',1,'ejavashop','2016-03-02 23:35:44'),
	(3,2,'2016年春装推荐',1,1,NULL,2,'wangpeng','2016-03-06 22:47:42',2,'wangpeng','2016-03-06 22:48:34'),
	(4,2,'每日抢购',2,1,NULL,2,'wangpeng','2016-03-06 22:48:11',2,'wangpeng','2016-03-06 22:48:31');

/*!40000 ALTER TABLE `pc_seller_recommend` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table pc_seller_recommend_data
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pc_seller_recommend_data`;

CREATE TABLE `pc_seller_recommend_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `seller_id` int(10) unsigned NOT NULL COMMENT '商家ID',
  `recommend_id` int(10) unsigned NOT NULL COMMENT '推荐类型ID',
  `data_type` tinyint(1) unsigned NOT NULL COMMENT '数据类型：1商品2图片链接',
  `ref_id` int(10) unsigned DEFAULT NULL COMMENT '数据ID（data_type为1表示商品ID）',
  `title` varchar(200) DEFAULT NULL COMMENT '图片链接标题',
  `image` varchar(200) DEFAULT NULL COMMENT '图片地址',
  `link_url` varchar(200) DEFAULT NULL COMMENT '图片链接地址',
  `order_no` int(2) NOT NULL COMMENT '排序号',
  `remark` varchar(255) DEFAULT NULL COMMENT '数据说明',
  `create_user_id` int(10) unsigned NOT NULL,
  `create_user_name` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_user_id` int(10) unsigned NOT NULL,
  `update_user_name` varchar(50) NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='PC端商家推荐数据表';

LOCK TABLES `pc_seller_recommend_data` WRITE;
/*!40000 ALTER TABLE `pc_seller_recommend_data` DISABLE KEYS */;

INSERT INTO `pc_seller_recommend_data` (`id`, `seller_id`, `recommend_id`, `data_type`, `ref_id`, `title`, `image`, `link_url`, `order_no`, `remark`, `create_user_id`, `create_user_name`, `create_time`, `update_user_id`, `update_user_name`, `update_time`)
VALUES
	(1,1,2,1,16,'',NULL,NULL,1,'',1,'ejavashop','2016-03-02 19:14:13',1,'ejavashop','2016-03-02 19:14:13'),
	(2,1,2,1,15,'',NULL,NULL,2,'',1,'ejavashop','2016-03-02 19:14:13',1,'ejavashop','2016-03-02 19:15:08'),
	(3,1,2,1,14,'',NULL,NULL,3,'',1,'ejavashop','2016-03-02 19:14:13',1,'ejavashop','2016-03-02 19:15:09'),
	(4,1,2,1,13,'',NULL,NULL,4,'',1,'ejavashop','2016-03-02 19:14:13',1,'ejavashop','2016-03-02 19:15:10'),
	(5,1,1,1,1,'',NULL,NULL,1,'',1,'ejavashop','2016-03-02 19:15:42',1,'ejavashop','2016-03-02 19:15:42'),
	(6,1,1,1,2,'',NULL,NULL,2,'',1,'ejavashop','2016-03-02 19:15:42',1,'ejavashop','2016-03-02 19:16:19'),
	(7,1,1,1,3,'',NULL,NULL,3,'',1,'ejavashop','2016-03-02 19:15:42',1,'ejavashop','2016-03-02 19:16:20'),
	(8,1,1,1,4,'',NULL,NULL,4,'',1,'ejavashop','2016-03-02 19:15:42',1,'ejavashop','2016-03-02 19:16:21'),
	(9,2,3,1,25,'',NULL,NULL,1,'',2,'wangpeng','2016-03-06 22:48:56',2,'wangpeng','2016-03-06 22:48:56'),
	(10,2,4,1,24,'',NULL,NULL,2,'',2,'wangpeng','2016-03-06 22:49:11',2,'wangpeng','2016-03-06 22:49:11'),
	(11,2,4,1,23,'',NULL,NULL,3,'',2,'wangpeng','2016-03-06 22:49:33',2,'wangpeng','2016-03-06 22:49:33'),
	(12,2,4,1,22,'',NULL,NULL,4,'',2,'wangpeng','2016-03-06 22:49:48',2,'wangpeng','2016-03-06 22:49:48'),
	(13,2,4,1,21,'',NULL,NULL,4,'',2,'wangpeng','2016-03-06 22:50:22',2,'wangpeng','2016-03-06 22:50:22'),
	(14,2,3,1,20,'',NULL,NULL,2,'',2,'wangpeng','2016-03-06 22:50:39',2,'wangpeng','2016-03-06 22:50:39'),
	(15,2,3,1,19,'',NULL,NULL,1,'',2,'wangpeng','2016-03-06 22:50:54',2,'wangpeng','2016-03-06 22:50:54'),
	(16,2,3,1,18,'',NULL,NULL,1,'',2,'wangpeng','2016-03-06 22:51:09',2,'wangpeng','2016-03-06 22:51:09');

/*!40000 ALTER TABLE `pc_seller_recommend_data` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
