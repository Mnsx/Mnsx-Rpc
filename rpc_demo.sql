/*
 Navicat Premium Data Transfer

 Source Server         : vm.mnsx.top
 Source Server Type    : MySQL
 Source Server Version : 50740
 Source Host           : 192.168.32.130:3306
 Source Schema         : rpc_demo

 Target Server Type    : MySQL
 Target Server Version : 50740
 File Encoding         : 65001

 Date: 15/12/2022 22:09:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_good
-- ----------------------------
DROP TABLE IF EXISTS `tb_good`;
CREATE TABLE `tb_good`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `price` bigint(20) NOT NULL COMMENT '商品价格',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 109 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tb_good
-- ----------------------------
INSERT INTO `tb_good` VALUES (101, 'Apple 苹果 iPhone 12 ', 699900);
INSERT INTO `tb_good` VALUES (102, '雅迪 yadea 新国标电动车', 209900);
INSERT INTO `tb_good` VALUES (103, '骆驼（CAMEL）休闲运动鞋女', 43900);
INSERT INTO `tb_good` VALUES (104, '小米10 双模5G 骁龙865', 359900);
INSERT INTO `tb_good` VALUES (105, 'OPPO Reno3 Pro 双模5G 视频双防抖', 299900);
INSERT INTO `tb_good` VALUES (106, '美的（Midea) 新能效 冷静星II ', 544900);
INSERT INTO `tb_good` VALUES (107, '西昊/SIHOO 人体工学电脑椅子', 79900);
INSERT INTO `tb_good` VALUES (108, '梵班（FAMDBANN）休闲男鞋', 31900);

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收件人',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (1, '柳岩', '湖南省衡阳市');
INSERT INTO `tb_user` VALUES (2, '文二狗', '陕西省西安市');
INSERT INTO `tb_user` VALUES (3, '华沉鱼', '湖北省十堰市');
INSERT INTO `tb_user` VALUES (4, '张必沉', '天津市');
INSERT INTO `tb_user` VALUES (5, '郑爽爽', '辽宁省沈阳市大东区');
INSERT INTO `tb_user` VALUES (6, '范兵兵', '山东省青岛市');

SET FOREIGN_KEY_CHECKS = 1;
