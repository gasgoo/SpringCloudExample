SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_student_00
-- ----------------------------
DROP TABLE IF EXISTS `t_student_00`;
CREATE TABLE `t_student_00` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `age` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_student_01
-- ----------------------------
DROP TABLE IF EXISTS `t_student_01`;
CREATE TABLE `t_student_01` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `age` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_user_00
-- ----------------------------
DROP TABLE IF EXISTS `t_user_00`;
CREATE TABLE `t_user_00` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `age` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_user_01
-- ----------------------------
DROP TABLE IF EXISTS `t_user_01`;
CREATE TABLE `t_user_01` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `age` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_user_02
-- ----------------------------
DROP TABLE IF EXISTS `t_user_02`;
CREATE TABLE `t_user_02` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `age` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS t_order_0;
CREATE TABLE t_order_0 (
order_id bigint(20) NOT NULL,
user_id bigint(20) NOT NULL,
user_name VARCHAR(32) NULL DEFAULT NULL COMMENT '用户名',
passwords VARCHAR(32) NULL DEFAULT NULL COMMENT '密码',
PRIMARY KEY (order_id)
);


DROP TABLE IF EXISTS t_order_1;
CREATE TABLE t_order_1 (

order_id bigint(20) NOT NULL,
user_id bigint(20) NOT NULL,
user_name VARCHAR(32) NULL DEFAULT NULL COMMENT '用户名',
passwords VARCHAR(32) NULL DEFAULT NULL COMMENT '密码',
PRIMARY KEY (order_id)
)

DROP TABLE IF EXISTS t_order_2;
CREATE TABLE t_order_2 (

order_id bigint(20) NOT NULL,
user_id bigint(20) NOT NULL,
user_name VARCHAR(32) NULL DEFAULT NULL COMMENT '用户名',
passwords VARCHAR(32) NULL DEFAULT NULL COMMENT '密码',
PRIMARY KEY (order_id)
)