# ************************************************************
# Sequel Pro SQL dump
# Version 3408
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 123.56.164.182 (MySQL 5.5.46)
# Database: ejavashop_promotion
# Generation Time: 2016-05-19 01:15:27 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table act_flash_sale
# ------------------------------------------------------------

DROP TABLE IF EXISTS `act_flash_sale`;

CREATE TABLE `act_flash_sale` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `act_flash_sale_name` varchar(100) NOT NULL COMMENT '活动名称',
  `act_date` datetime NOT NULL COMMENT '活动日期',
  `pc_image` varchar(100) DEFAULT NULL COMMENT 'pc端活动图片',
  `mobile_image` varchar(100) DEFAULT NULL COMMENT '移动端活动图片',
  `channel` tinyint(1) NOT NULL COMMENT '活动应用渠道1、通用；2、PC；3、Mobile',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1、新建；2、商品征集；3、征集结束；4、作废；5、上架；6、下架',
  `audit_opinion` text COMMENT '审核意见',
  `audit_rule` text COMMENT '申请规则，用于给商家申请时的须知',
  `remark` varchar(255) NOT NULL COMMENT '活动描述',
  `create_user_id` int(10) unsigned NOT NULL,
  `create_user_name` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_user_id` int(10) unsigned NOT NULL,
  `update_user_name` varchar(50) NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='限时抢购活动表';



# Dump of table act_flash_sale_log
# ------------------------------------------------------------

DROP TABLE IF EXISTS `act_flash_sale_log`;

CREATE TABLE `act_flash_sale_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `act_flash_sale_id` int(10) unsigned NOT NULL COMMENT '限时抢购活动id',
  `act_flash_sale_product_id` int(10) unsigned NOT NULL COMMENT '限时抢购活动商品id',
  `member_id` int(10) unsigned NOT NULL COMMENT '会员id',
  `seller_id` int(10) unsigned NOT NULL COMMENT '所属商家id',
  `order_id` int(10) unsigned NOT NULL COMMENT '订单id',
  `order_product_id` int(10) unsigned NOT NULL COMMENT '网单id',
  `product_id` int(10) unsigned NOT NULL COMMENT '商品id',
  `create_user_id` int(10) unsigned NOT NULL,
  `create_user_name` varchar(50) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='限时抢购活动参加日志表';



# Dump of table act_flash_sale_product
# ------------------------------------------------------------

DROP TABLE IF EXISTS `act_flash_sale_product`;

CREATE TABLE `act_flash_sale_product` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `act_flash_sale_id` int(10) unsigned NOT NULL COMMENT '限时抢购活动id',
  `act_flash_sale_stage_id` int(10) unsigned NOT NULL COMMENT '限时抢购活动阶段id',
  `seller_id` int(11) unsigned NOT NULL COMMENT '商家ID',
  `product_id` int(11) unsigned NOT NULL COMMENT '商品ID',
  `sort` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '排序',
  `price` decimal(10,2) unsigned NOT NULL COMMENT '活动价格',
  `stock` int(11) unsigned NOT NULL COMMENT '库存量',
  `actual_sales` int(11) unsigned NOT NULL COMMENT '已售出数量',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1、提交审核；2、审核通过；3、审核失败',
  `audit_opinion` text COMMENT '审核意见',
  `audit_user_id` int(10) unsigned DEFAULT NULL COMMENT '审核人ID',
  `audit_user_name` varchar(50) DEFAULT NULL COMMENT '审核人名称',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `create_user_id` int(10) unsigned NOT NULL,
  `create_user_name` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_user_id` int(10) unsigned NOT NULL,
  `update_user_name` varchar(50) NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='限时抢购活动商品表';



# Dump of table act_flash_sale_stage
# ------------------------------------------------------------

DROP TABLE IF EXISTS `act_flash_sale_stage`;

CREATE TABLE `act_flash_sale_stage` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `act_flash_sale_id` int(10) unsigned NOT NULL COMMENT '限时抢购活动id',
  `start_time` int(10) NOT NULL COMMENT '开始时间',
  `end_time` int(10) NOT NULL COMMENT '结束时间',
  `remark` varchar(255) DEFAULT '' COMMENT '阶段描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='限时抢购活动阶段表';



# Dump of table act_full
# ------------------------------------------------------------

DROP TABLE IF EXISTS `act_full`;

CREATE TABLE `act_full` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `seller_id` int(11) unsigned NOT NULL COMMENT '商家ID',
  `act_full_name` varchar(100) NOT NULL COMMENT '满减活动名称',
  `first_full` decimal(10,2) unsigned NOT NULL COMMENT '第一档满额',
  `first_discount` decimal(10,2) unsigned NOT NULL COMMENT '第一档减免额',
  `second_full` decimal(10,2) unsigned DEFAULT NULL COMMENT '第二档满额',
  `second_discount` decimal(10,2) unsigned DEFAULT NULL COMMENT '第二档减免额',
  `third_full` decimal(10,2) unsigned DEFAULT NULL COMMENT '第三档满额',
  `third_discount` decimal(10,2) unsigned DEFAULT NULL COMMENT '第三档减免额',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `channel` tinyint(1) NOT NULL COMMENT '活动应用渠道1、通用；2、PC；3、Mobile',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1、新建；2、提交审核；3、审核通过；4、审核失败；5、上架；6、下架',
  `audit_opinion` text COMMENT '审核意见',
  `remark` varchar(255) NOT NULL COMMENT '活动描述',
  `audit_user_id` int(10) unsigned DEFAULT NULL COMMENT '审核人ID',
  `audit_user_name` varchar(50) DEFAULT NULL COMMENT '审核人名称',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `create_user_id` int(10) unsigned NOT NULL,
  `create_user_name` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_user_id` int(10) unsigned NOT NULL,
  `update_user_name` varchar(50) NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='满减活动表';



# Dump of table act_full_log
# ------------------------------------------------------------

DROP TABLE IF EXISTS `act_full_log`;

CREATE TABLE `act_full_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `act_full_id` int(10) unsigned NOT NULL COMMENT '满减活动id',
  `member_id` int(10) unsigned NOT NULL COMMENT '会员id',
  `seller_id` int(10) unsigned NOT NULL COMMENT '所属商家id',
  `order_id` int(10) unsigned NOT NULL COMMENT '订单id',
  `create_user_id` int(10) unsigned NOT NULL,
  `create_user_name` varchar(50) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='满减活动参加日志表';



# Dump of table act_single
# ------------------------------------------------------------

DROP TABLE IF EXISTS `act_single`;

CREATE TABLE `act_single` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `seller_id` int(11) unsigned NOT NULL COMMENT '商家ID',
  `act_single_name` varchar(100) NOT NULL COMMENT '单品立减活动名称',
  `type` tinyint(1) NOT NULL COMMENT '活动类型1、减免金额；2、折扣',
  `discount` decimal(10,2) unsigned NOT NULL COMMENT '类型为减免金额时为金额，折扣类型时为折扣（如0.90为打九折）',
  `product_ids` mediumtext COMMENT '商品id列表，逗号隔开，前后有逗号，如果同一个商品有多个单品立减活动取有效活动中最新的一个',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `channel` tinyint(1) NOT NULL COMMENT '活动应用渠道1、通用；2、PC；3、Mobile',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1、新建；2、提交审核；3、审核通过；4、审核失败；5、上架；6、下架',
  `remark` varchar(255) NOT NULL COMMENT '活动描述',
  `audit_opinion` text COMMENT '审核意见',
  `audit_user_id` int(10) unsigned DEFAULT NULL COMMENT '审核人ID',
  `audit_user_name` varchar(50) DEFAULT NULL COMMENT '审核人名称',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `create_user_id` int(10) unsigned NOT NULL,
  `create_user_name` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_user_id` int(10) unsigned NOT NULL,
  `update_user_name` varchar(50) NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='单品立减活动表';



# Dump of table act_single_log
# ------------------------------------------------------------

DROP TABLE IF EXISTS `act_single_log`;

CREATE TABLE `act_single_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `act_single_id` int(10) unsigned NOT NULL COMMENT '单品立减活动id',
  `member_id` int(10) unsigned NOT NULL COMMENT '会员id',
  `seller_id` int(10) unsigned NOT NULL COMMENT '所属商家id',
  `order_id` int(10) unsigned NOT NULL COMMENT '订单id',
  `order_product_id` int(10) unsigned NOT NULL COMMENT '网单id',
  `product_id` int(10) unsigned NOT NULL COMMENT '商品id',
  `create_user_id` int(10) unsigned NOT NULL,
  `create_user_name` varchar(50) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='单品立减活动参加日志表';



# Dump of table coupon
# ------------------------------------------------------------

DROP TABLE IF EXISTS `coupon`;

CREATE TABLE `coupon` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `seller_id` int(11) unsigned NOT NULL COMMENT '商家ID',
  `coupon_name` varchar(100) NOT NULL COMMENT '优惠券名称',
  `prefix` varchar(10) NOT NULL COMMENT '优惠券前缀',
  `coupon_value` decimal(10,2) unsigned NOT NULL COMMENT '优惠券面值',
  `min_amount` decimal(10,2) unsigned NOT NULL COMMENT '适用的最低订单金额',
  `send_start_time` datetime NOT NULL COMMENT '发放开始时间',
  `send_end_time` datetime NOT NULL COMMENT '发放结束时间',
  `use_start_time` datetime NOT NULL COMMENT '使用起始时间',
  `use_end_time` datetime NOT NULL COMMENT '使用截止时间',
  `person_limit_num` int(10) unsigned NOT NULL COMMENT '每个会员限制领取的张数，0为不限',
  `total_limit_num` int(10) unsigned NOT NULL COMMENT '总共允许发放的总张数',
  `received_num` int(10) unsigned NOT NULL COMMENT '已发放的张数',
  `type` tinyint(1) NOT NULL COMMENT '优惠券类型1、在线领取；2、线下发放',
  `channel` tinyint(1) NOT NULL COMMENT '活动应用渠道1、通用；2、PC；3、Mobile',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1、新建；2、提交审核；3、审核通过；4、审核失败；5、上架；6、下架',
  `audit_opinion` text COMMENT '审核意见',
  `remark` varchar(255) NOT NULL COMMENT '优惠券描述',
  `audit_user_id` int(10) unsigned DEFAULT NULL COMMENT '审核人ID',
  `audit_user_name` varchar(50) DEFAULT NULL COMMENT '审核人名称',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `create_user_id` int(10) unsigned NOT NULL,
  `create_user_name` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_user_id` int(10) unsigned NOT NULL,
  `update_user_name` varchar(50) NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='优惠券表';



# Dump of table coupon_opt_log
# ------------------------------------------------------------

DROP TABLE IF EXISTS `coupon_opt_log`;

CREATE TABLE `coupon_opt_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `coupon_user_id` int(10) unsigned NOT NULL COMMENT '优惠券用户id',
  `member_id` int(10) unsigned NOT NULL COMMENT '会员id',
  `seller_id` int(10) unsigned NOT NULL COMMENT '所属商家id',
  `coupon_id` int(10) unsigned NOT NULL COMMENT '优惠券id',
  `opt_type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1、领取；2、下单消费；3、订单取消返回；4、商品退货返回',
  `order_id` int(10) unsigned NOT NULL COMMENT '订单id(无订单赋0)',
  `create_user_id` int(10) unsigned NOT NULL,
  `create_user_name` varchar(50) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='优惠券操作日志';



# Dump of table coupon_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `coupon_user`;

CREATE TABLE `coupon_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `member_id` int(10) unsigned NOT NULL COMMENT '会员id',
  `seller_id` int(10) unsigned NOT NULL COMMENT '所属商家id',
  `coupon_id` int(10) unsigned NOT NULL COMMENT '优惠券id',
  `coupon_sn` varchar(60) NOT NULL COMMENT '序列号',
  `password` varchar(60) DEFAULT NULL COMMENT '密码',
  `can_use` int(10) unsigned NOT NULL COMMENT '可使用次数（默认都是1次，不支持多次使用）',
  `receive_time` datetime DEFAULT NULL COMMENT '领取时间',
  `order_id` int(10) unsigned NOT NULL COMMENT '订单id(无订单赋0)',
  `use_time` datetime DEFAULT NULL COMMENT '使用时间',
  `use_start_time` datetime NOT NULL COMMENT '使用开始时间',
  `use_end_time` datetime NOT NULL COMMENT '使用结束时间',
  `create_user_id` int(10) unsigned NOT NULL,
  `create_user_name` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_user_id` int(10) unsigned NOT NULL,
  `update_user_name` varchar(50) NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `coupon_sn` (`coupon_sn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='优惠券用户表';



# Dump of table log_act_flash_sale
# ------------------------------------------------------------

DROP TABLE IF EXISTS `log_act_flash_sale`;

CREATE TABLE `log_act_flash_sale` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `opt_type` varchar(50) NOT NULL COMMENT '操作类型，C、新建，U、修改，D、删除',
  `opt_user_id` int(10) unsigned NOT NULL COMMENT '操作人ID',
  `opt_user_name` varchar(50) NOT NULL COMMENT '操作人名称',
  `opt_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  `act_flash_sale_id` int(10) unsigned NOT NULL,
  `act_flash_sale_name` varchar(100) NOT NULL COMMENT '活动名称',
  `act_date` datetime NOT NULL COMMENT '活动日期',
  `pc_image` varchar(100) DEFAULT NULL COMMENT 'PC端活动图片',
  `mobile_image` varchar(100) DEFAULT NULL COMMENT '移动端活动图片',
  `channel` tinyint(1) NOT NULL COMMENT '活动应用渠道1、通用；2、PC；3、Mobile',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1、新建；2、商品征集；3、征集结束；4、作废；5、上架；6、下架',
  `audit_opinion` text COMMENT '审核意见',
  `audit_rule` text COMMENT '申请规则，用于给商家申请时的须知',
  `remark` varchar(255) NOT NULL COMMENT '活动描述',
  `create_user_id` int(10) unsigned NOT NULL,
  `create_user_name` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_user_id` int(10) unsigned NOT NULL,
  `update_user_name` varchar(50) NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='限时抢购活动日志表';



# Dump of table log_act_flash_sale_product
# ------------------------------------------------------------

DROP TABLE IF EXISTS `log_act_flash_sale_product`;

CREATE TABLE `log_act_flash_sale_product` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `opt_type` varchar(50) NOT NULL COMMENT '操作类型，C、新建，U、修改，D、删除',
  `opt_user_id` int(10) unsigned NOT NULL COMMENT '操作人ID',
  `opt_user_name` varchar(50) NOT NULL COMMENT '操作人名称',
  `opt_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  `act_flash_sale_product_id` int(10) unsigned NOT NULL,
  `act_flash_sale_id` int(10) unsigned NOT NULL COMMENT '限时抢购活动id',
  `act_flash_sale_stage_id` int(10) unsigned NOT NULL COMMENT '限时抢购活动阶段id',
  `seller_id` int(11) unsigned NOT NULL COMMENT '商家ID',
  `product_id` int(11) unsigned NOT NULL COMMENT '商品ID',
  `price` decimal(10,2) unsigned NOT NULL COMMENT '活动价格',
  `stock` int(11) unsigned NOT NULL COMMENT '库存量',
  `actual_sales` int(11) unsigned NOT NULL COMMENT '已售出数量',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1、提交审核；2、审核通过；3、审核失败',
  `audit_opinion` text COMMENT '审核意见',
  `audit_user_id` int(10) unsigned DEFAULT NULL COMMENT '审核人ID',
  `audit_user_name` varchar(50) DEFAULT NULL COMMENT '审核人名称',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `create_user_id` int(10) unsigned NOT NULL,
  `create_user_name` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_user_id` int(10) unsigned NOT NULL,
  `update_user_name` varchar(50) NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='限时抢购活动商品日志表';



# Dump of table log_act_flash_sale_stage
# ------------------------------------------------------------

DROP TABLE IF EXISTS `log_act_flash_sale_stage`;

CREATE TABLE `log_act_flash_sale_stage` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `act_flash_sale_stage_id` int(10) unsigned NOT NULL,
  `act_flash_sale_id` int(10) unsigned NOT NULL COMMENT '限时抢购活动id',
  `start_time` int(10) NOT NULL COMMENT '开始时间',
  `end_time` int(10) NOT NULL COMMENT '结束时间',
  `remark` varchar(255) DEFAULT '' COMMENT '阶段描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='限时抢购活动阶段日志表';



# Dump of table log_act_full
# ------------------------------------------------------------

DROP TABLE IF EXISTS `log_act_full`;

CREATE TABLE `log_act_full` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `opt_type` varchar(50) NOT NULL COMMENT '操作类型，C、新建，U、修改，D、删除',
  `opt_user_id` int(10) unsigned NOT NULL COMMENT '操作人ID',
  `opt_user_name` varchar(50) NOT NULL COMMENT '操作人名称',
  `opt_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  `act_full_id` int(10) unsigned NOT NULL COMMENT '订单满减活动ID',
  `seller_id` int(11) unsigned NOT NULL COMMENT '商家ID',
  `act_full_name` varchar(100) NOT NULL COMMENT '满减活动名称',
  `first_full` decimal(10,2) unsigned NOT NULL COMMENT '第一档满额',
  `first_discount` decimal(10,2) unsigned NOT NULL COMMENT '第一档减免额',
  `second_full` decimal(10,2) unsigned DEFAULT NULL COMMENT '第二档满额',
  `second_discount` decimal(10,2) unsigned DEFAULT NULL COMMENT '第二档减免额',
  `third_full` decimal(10,2) unsigned DEFAULT NULL COMMENT '第三档满额',
  `third_discount` decimal(10,2) unsigned DEFAULT NULL COMMENT '第三档减免额',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `channel` tinyint(1) NOT NULL COMMENT '活动应用渠道1、通用；2、PC；3、Mobile',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1、新建；2、提交审核；3、审核通过；4、审核失败；5、上架；6、下架',
  `audit_opinion` text COMMENT '审核意见',
  `remark` varchar(255) NOT NULL COMMENT '活动描述',
  `audit_user_id` int(10) unsigned DEFAULT NULL COMMENT '审核人ID',
  `audit_user_name` varchar(50) DEFAULT NULL COMMENT '审核人名称',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `create_user_id` int(10) unsigned NOT NULL,
  `create_user_name` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_user_id` int(10) unsigned NOT NULL,
  `update_user_name` varchar(50) NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='满减活动日志表';



# Dump of table log_act_single
# ------------------------------------------------------------

DROP TABLE IF EXISTS `log_act_single`;

CREATE TABLE `log_act_single` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `opt_type` varchar(50) NOT NULL COMMENT '操作类型，C、新建，U、修改，D、删除',
  `opt_user_id` int(10) unsigned NOT NULL COMMENT '操作人ID',
  `opt_user_name` varchar(50) NOT NULL COMMENT '操作人名称',
  `opt_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  `act_single_id` int(10) unsigned NOT NULL COMMENT '单品立减活动ID',
  `seller_id` int(11) unsigned NOT NULL COMMENT '商家ID',
  `act_single_name` varchar(100) NOT NULL COMMENT '单品立减活动名称',
  `type` tinyint(1) NOT NULL COMMENT '活动类型1、减免金额；2、折扣',
  `discount` decimal(10,2) unsigned NOT NULL COMMENT '类型为减免金额时为金额，折扣类型时为折扣（如0.90为打九折）',
  `product_ids` mediumtext COMMENT '商品id列表，逗号隔开，前后有逗号，如果同一个商品有多个单品立减活动取有效活动中最新的一个',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `channel` tinyint(1) NOT NULL COMMENT '活动应用渠道1、通用；2、PC；3、Mobile',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1、新建；2、提交审核；3、审核通过；4、审核失败；5、上架；6、下架',
  `audit_opinion` text COMMENT '审核意见',
  `remark` varchar(255) NOT NULL COMMENT '活动描述',
  `audit_user_id` int(10) unsigned DEFAULT NULL COMMENT '审核人ID',
  `audit_user_name` varchar(50) DEFAULT NULL COMMENT '审核人名称',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `create_user_id` int(10) unsigned NOT NULL,
  `create_user_name` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_user_id` int(10) unsigned NOT NULL,
  `update_user_name` varchar(50) NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='单品立减活动日志表';



# Dump of table log_coupon
# ------------------------------------------------------------

DROP TABLE IF EXISTS `log_coupon`;

CREATE TABLE `log_coupon` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `opt_type` varchar(50) NOT NULL COMMENT '操作类型，C、新建，U、修改，D、删除',
  `opt_user_id` int(10) unsigned NOT NULL COMMENT '操作人ID',
  `opt_user_name` varchar(50) NOT NULL COMMENT '操作人名称',
  `opt_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  `coupon_id` int(10) unsigned NOT NULL COMMENT '优惠券ID',
  `seller_id` int(11) unsigned NOT NULL COMMENT '商家ID',
  `coupon_name` varchar(100) NOT NULL COMMENT '优惠券名称',
  `prefix` varchar(10) NOT NULL COMMENT '优惠券前缀',
  `coupon_value` decimal(10,2) unsigned NOT NULL COMMENT '优惠券面值',
  `min_amount` decimal(10,2) unsigned NOT NULL COMMENT '适用的最低订单金额',
  `send_start_time` datetime NOT NULL COMMENT '发放开始时间',
  `send_end_time` datetime NOT NULL COMMENT '发放结束时间',
  `use_start_time` datetime NOT NULL COMMENT '使用起始时间',
  `use_end_time` datetime NOT NULL COMMENT '使用截止时间',
  `person_limit_num` int(10) unsigned NOT NULL COMMENT '每个会员限制领取的张数，0为不限',
  `total_limit_num` int(10) unsigned NOT NULL COMMENT '总共允许发放的总张数',
  `received_num` int(10) unsigned NOT NULL COMMENT '已发放的张数',
  `type` tinyint(1) NOT NULL COMMENT '优惠券类型1、在线领取；2、线下发放',
  `channel` tinyint(1) NOT NULL COMMENT '活动应用渠道1、通用；2、PC；3、Mobile',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1、新建；2、提交审核；3、审核通过；4、审核失败；5、上架；6、下架',
  `audit_opinion` text COMMENT '审核意见',
  `remark` varchar(255) NOT NULL COMMENT '优惠券描述',
  `audit_user_id` int(10) unsigned DEFAULT NULL COMMENT '审核人ID',
  `audit_user_name` varchar(50) DEFAULT NULL COMMENT '审核人名称',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `create_user_id` int(10) unsigned NOT NULL,
  `create_user_name` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_user_id` int(10) unsigned NOT NULL,
  `update_user_name` varchar(50) NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='优惠券日志表';




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
