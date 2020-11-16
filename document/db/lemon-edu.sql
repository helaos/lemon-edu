CREATE DATABASE `lemon_edu` CHARACTER SET utf8mb4;

USE `lemon_edu`;

-- 教师表
DROP TABLE IF EXISTS `edu_teacher`;

CREATE TABLE `edu_teacher`  (
    `id` char(19) NOT NULL COMMENT '讲师ID',
    `name` varchar(20) NOT NULL COMMENT '讲师姓名',
    `intro` varchar(500) NOT NULL DEFAULT '' COMMENT '讲师简介',
    `career` varchar(500) NULL DEFAULT NULL COMMENT '讲师资历,一句话说明讲师',
    `level` int(10) UNSIGNED NOT NULL COMMENT '头衔 1高级讲师 2首席讲师',
    `avatar` varchar(255) NULL DEFAULT NULL COMMENT '讲师头像',
    `sort` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '排序',
    `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
    `create_time` datetime(0) NOT NULL COMMENT '创建时间',
    `update_time` datetime(0) NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_name`(`name`) USING BTREE
) COMMENT='讲师';

-- 教师表数据
INSERT INTO `edu_teacher` VALUES ('1189389745345345977', '张三', '近年主持国家自然科学基金（6项）', '高级', 1, 'https://guli-file-190513.oss-cn-beijing.aliyuncs.com/avatar/default.jpg', 0, 0, '2019-10-30 14:18:46', '2019-11-12 13:36:36');
INSERT INTO `edu_teacher` VALUES ('1189389726308478977', '晴天', '高级讲师简介', '高级讲师资历', 2, 'https://online-teach-file.oss-cn-beijing.aliyuncs.com/teacher/2019/10/30/de47ee9b-7fec-43c5-8173-13c5f7f689b2.png', 1, 0, '2019-10-30 11:53:03', '2019-10-30 11:53:03');
INSERT INTO `edu_teacher` VALUES ('1189390295668469762', '李刚', '高级讲师简介', '高级讲师', 2, 'https://online-teach-file.oss-cn-beijing.aliyuncs.com/teacher/2019/10/30/b8aa36a2-db50-4eca-a6e3-cc6e608355e0.png', 2, 0, '2019-10-30 11:55:19', '2019-11-12 13:37:52');
INSERT INTO `edu_teacher` VALUES ('1189426437876985857', '王二', '高级讲师简介', '高级讲师', 1, 'https://online-teach-file.oss-cn-beijing.aliyuncs.com/teacher/2019/11/08/e44a2e92-2421-4ea3-bb49-46f2ec96ef88.png', 0, 0, '2019-10-30 14:18:56', '2019-11-12 13:37:35');
INSERT INTO `edu_teacher` VALUES ('1189426464967995393', '王五', '高级讲师简介', '高级讲师', 1, 'https://online-teach-file.oss-cn-beijing.aliyuncs.com/teacher/2019/10/30/65423f14-49a9-4092-baf5-6d0ef9686a85.png', 0, 0, '2019-10-30 14:19:02', '2019-11-12 13:37:18');
INSERT INTO `edu_teacher` VALUES ('1192249914833055746', '李四', '高级讲师简介', '高级讲师', 1, 'https://online-teach-file.oss-cn-beijing.aliyuncs.com/teacher/2019/11/07/91871e25-fd83-4af6-845f-ea8d471d825d.png', 0, 0, '2019-11-07 09:18:25', '2019-11-12 13:37:01');
INSERT INTO `edu_teacher` VALUES ('1328269946941079553', '可莉', '火花骑士', '火花骑士', 2, 'https://lemon-edu.oss-cn-beijing.aliyuncs.com/avatar/2020/11/16a5381233476a4ac0ad631154c91bbd1ffile.png', 0, 0, '2020-11-16 17:33:28', '2020-11-16 17:34:12');
INSERT INTO `edu_teacher` VALUES ('1328322530661953537', '祝融之眼', '会火法，岩浆炼体，滴血重生！', '上古之火神', 1, 'https://lemon-edu.oss-cn-beijing.aliyuncs.com/avatar/2020/11/164686dfcd6c9248659865b1960c54bc31file.png', 0, 0, '2020-11-16 21:02:25', '2020-11-16 21:02:25');

-- 课程科目表
DROP TABLE IF EXISTS `edu_subject`;

CREATE TABLE `edu_subject` (
    `id` char(19) NOT NULL COMMENT '课程类别ID',
    `title` varchar(10) NOT NULL COMMENT '类别名称',
    `parent_id` char(19) NOT NULL DEFAULT '0' COMMENT '父类ID',
    `sort` int(10) unsigned NOT NULL DEFAULT 0 COMMENT '排序字段',
    `create_time` datetime(0) NOT NULL COMMENT '创建时间',
    `update_time` datetime(0) NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_parent_id` (`parent_id`)
) COMMENT='课程科目';
