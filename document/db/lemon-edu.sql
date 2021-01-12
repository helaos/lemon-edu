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
    `sort` int(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '排序',
    `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
    `create_time` datetime(0) NOT NULL COMMENT '创建时间',
    `update_time` datetime(0) NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_name`(`name`) USING BTREE
) COMMENT='讲师表';


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
    `sort` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '排序字段',
    `create_time` datetime(0) NOT NULL COMMENT '创建时间',
    `update_time` datetime(0) NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_parent_id` (`parent_id`)
) COMMENT='课程科目分类表';


DROP TABLE IF EXISTS `edu_course`;
CREATE TABLE `edu_course` (
    `id` char(19) NOT NULL COMMENT '课程ID',
    `teacher_id` char(19) NOT NULL COMMENT '课程讲师ID',
    `subject_id` char(19) NOT NULL COMMENT '课程专业ID',
    `subject_parent_id` char(19) NULL DEFAULT NULL COMMENT '课程专业父级ID',
    `title` varchar(50) NOT NULL COMMENT '课程标题',
    `price` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '课程销售价格，设置为0则可免费观看',
    `lesson_num` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '总课时',
    `cover` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '课程封面图片路径',
    `buy_count` bigint(10) unsigned NOT NULL DEFAULT '0' COMMENT '销售数量',
    `view_count` bigint(10) unsigned NOT NULL DEFAULT '0' COMMENT '浏览数量',
    `version` bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `status` varchar(10) NOT NULL DEFAULT 'Draft' COMMENT '课程状态 Draft未发布  Normal已发布',
    `is_deleted` tinyint(3) DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
    `create_time` datetime(0) NOT NULL COMMENT '创建时间',
    `update_time` datetime(0) NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_title` (`title`),
    KEY `idx_subject_id` (`subject_id`),
    KEY `idx_teacher_id` (`teacher_id`)
) COMMENT='课程信息表';


DROP TABLE IF EXISTS `edu_course_description`;
CREATE TABLE `edu_course_description` (
    `id` char(19) NOT NULL COMMENT '课程ID',
    `description` mediumtext COMMENT '课程简介',
    `create_time` datetime(0) NOT NULL COMMENT '创建时间',
    `update_time` datetime(0) NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) COMMENT='课程简介表';


DROP TABLE IF EXISTS `edu_chapter`;
CREATE TABLE `edu_chapter` (
    `id` char(19) NOT NULL COMMENT '章节ID',
    `course_id` char(19) NOT NULL COMMENT '课程ID',
    `title` varchar(50) NOT NULL COMMENT '章节名称',
    `sort` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '显示排序',
    `create_time` datetime(0) NOT NULL COMMENT '创建时间',
    `update_time` datetime(0) NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_course_id` (`course_id`)
) COMMENT='课程表';


DROP TABLE IF EXISTS `edu_video`;
CREATE TABLE `edu_video` (
    `id` char(19) NOT NULL COMMENT '视频ID',
    `course_id` char(19) NOT NULL COMMENT '课程ID',
    `chapter_id` char(19) NOT NULL COMMENT '章节ID',
    `title` varchar(50) NOT NULL COMMENT '节点名称',
    `video_source_id` varchar(100) DEFAULT NULL COMMENT '云端视频资源',
    `video_original_name` varchar(100) DEFAULT NULL COMMENT '原始文件名称',
    `sort` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '排序字段',
    `play_count` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '播放次数',
    `is_free` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否可以试听：0收费 1免费',
    `duration` float NOT NULL DEFAULT '0' COMMENT '视频时长（秒）',
    `status` varchar(20) NOT NULL DEFAULT 'Empty' COMMENT 'Empty未上传 Transcoding转码中  Normal正常',
    `size` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '视频源文件大小（字节）',
    `version` bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
    `create_time` datetime(0) NOT NULL COMMENT '创建时间',
    `update_time` datetime(0) NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_course_id` (`course_id`),
    KEY `idx_chapter_id` (`chapter_id`)
) COMMENT='课程视频表';


DROP TABLE IF EXISTS `crm_banner`;
CREATE TABLE `crm_banner` (
    `id` char(19) NOT NULL DEFAULT '' COMMENT 'ID',
    `title` varchar(20) DEFAULT '' COMMENT '标题',
    `image_url` varchar(500) NOT NULL DEFAULT '' COMMENT '图片地址',
    `link_url` varchar(500) DEFAULT '' COMMENT '链接地址',
    `sort` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '排序',
    `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
    `create_time` datetime(0) NOT NULL COMMENT '创建时间',
    `update_time` datetime(0) NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_name` (`title`)
) COMMENT='首页banner表';


DROP TABLE IF EXISTS `ucenter_member`;
CREATE TABLE `ucenter_member` (
    `id` char(19) NOT NULL COMMENT '会员id',
    `openid` varchar(128) DEFAULT NULL COMMENT '微信openid',
    `mobile` varchar(11) DEFAULT '' COMMENT '手机号',
    `password` varchar(255) DEFAULT NULL COMMENT '密码',
    `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
    `sex` tinyint(2) unsigned DEFAULT NULL COMMENT '性别 1 女，2 男',
    `age` tinyint(3) unsigned DEFAULT NULL COMMENT '年龄',
    `avatar` varchar(255) DEFAULT NULL COMMENT '用户头像',
    `sign` varchar(100) DEFAULT NULL COMMENT '用户签名',
    `is_disabled` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否禁用 1（true）已禁用，  0（false）未禁用',
    `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
    `create_time` datetime(0) NOT NULL COMMENT '创建时间',
    `update_time` datetime(0) NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) COMMENT='会员表';

DROP TABLE IF EXISTS `edu_comment`;
CREATE TABLE `edu_comment` (
    `id` char(19) NOT NULL COMMENT '讲师ID',
    `course_id` varchar(19) NOT NULL DEFAULT '' COMMENT '课程id',
    `teacher_id` char(19) NOT NULL DEFAULT '' COMMENT '讲师id',
    `member_id` varchar(19) NOT NULL DEFAULT '' COMMENT '会员id',
    `nickname` varchar(50) DEFAULT NULL COMMENT '会员昵称',
    `avatar` varchar(255) DEFAULT NULL COMMENT '会员头像',
    `content` varchar(500) DEFAULT NULL COMMENT '评论内容',
    `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_time` datetime NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_course_id` (`course_id`),
    KEY `idx_teacher_id` (`teacher_id`),
    KEY `idx_member_id` (`member_id`)
) COMMENT='评论';

DROP TABLE IF EXISTS `oms_order`;
CREATE TABLE `oms_order` (
    `id` char(19) NOT NULL DEFAULT '' COMMENT '订单ID',
    `order_no` varchar(20) NOT NULL DEFAULT '' COMMENT '订单号',
    `course_id` varchar(19) NOT NULL DEFAULT '' COMMENT '课程id',
    `course_title` varchar(100) DEFAULT NULL COMMENT '课程名称',
    `course_cover` varchar(255) DEFAULT NULL COMMENT '课程封面',
    `teacher_name` varchar(20) DEFAULT NULL COMMENT '讲师名称',
    `member_id` varchar(19) NOT NULL DEFAULT '' COMMENT '会员id',
    `nickname` varchar(50) DEFAULT NULL COMMENT '会员昵称',
    `mobile` varchar(11) DEFAULT NULL COMMENT '会员手机',
    `total_fee` decimal(10,2) DEFAULT '0.01' COMMENT '订单金额（分）',
    `pay_type` tinyint(3) DEFAULT NULL COMMENT '支付类型（1：微信 2：支付宝）',
    `status` tinyint(3) DEFAULT NULL COMMENT '订单状态（0：未支付 1：已支付）',
    `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_time` datetime NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_order_no` (`order_no`),
    KEY `idx_course_id` (`course_id`),
    KEY `idx_member_id` (`member_id`)
) COMMENT='订单';

DROP TABLE IF EXISTS `oms_pay_log`;
CREATE TABLE `oms_pay_log` (
    `id` char(19) NOT NULL DEFAULT '' COMMENT '订单日志ID',
    `order_no` varchar(20) NOT NULL DEFAULT '' COMMENT '订单号',
    `pay_time` datetime DEFAULT NULL COMMENT '支付完成时间',
    `total_fee` decimal(10,2) DEFAULT '0.01' COMMENT '支付金额（分）',
    `transaction_id` varchar(30) DEFAULT NULL COMMENT '交易流水号',
    `trade_state` char(20) DEFAULT NULL COMMENT '交易状态',
    `pay_type` tinyint(3) NOT NULL DEFAULT '0' COMMENT '支付类型（1：微信 2：支付宝）',
    `attribute` text COMMENT '其他属性',
    `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_time` datetime NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_order_no` (`order_no`)
) COMMENT='支付日志表';

DROP TABLE IF EXISTS `statistics_daily`;
CREATE TABLE `statistics_daily` (
    `id` char(19) NOT NULL COMMENT '主键',
    `date_calculated` varchar(20) NOT NULL COMMENT '统计日期',
    `register_num` int(11) NOT NULL DEFAULT '0' COMMENT '注册人数',
    `login_num` int(11) NOT NULL DEFAULT '0' COMMENT '登录人数',
    `video_view_num` int(11) NOT NULL DEFAULT '0' COMMENT '每日播放视频数',
    `course_num` int(11) NOT NULL DEFAULT '0' COMMENT '每日新增课程数',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_time` datetime NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `statistics_day` (`date_calculated`)
) COMMENT='网站统计日数据';

DROP TABLE IF EXISTS `acl_permission`;
CREATE TABLE `acl_permission` (
    `id` char(19) NOT NULL DEFAULT '' COMMENT '编号',
    `pid` char(19) NOT NULL DEFAULT '' COMMENT '所属上级',
    `name` varchar(20) NOT NULL DEFAULT '' COMMENT '名称',
    `type` tinyint(3) NOT NULL DEFAULT '0' COMMENT '类型(1:菜单,2:按钮)',
    `permission_value` varchar(50) DEFAULT NULL COMMENT '权限值',
    `path` varchar(100) DEFAULT NULL COMMENT '访问路径',
    `component` varchar(100) DEFAULT NULL COMMENT '组件路径',
    `icon` varchar(50) DEFAULT NULL COMMENT '图标',
    `status` tinyint(4) DEFAULT NULL COMMENT '状态(0:禁止,1:正常)',
    `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_time` datetime NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_pid` (`pid`)
) COMMENT='权限';

DROP TABLE IF EXISTS `acl_role`;
CREATE TABLE `acl_role` (
    `id` char(19) NOT NULL DEFAULT '' COMMENT '角色id',
    `role_name` varchar(20) NOT NULL DEFAULT '' COMMENT '角色名称',
    `role_code` varchar(20) DEFAULT NULL COMMENT '角色编码',
    `remark` varchar(255) DEFAULT NULL COMMENT '备注',
    `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_time` datetime NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) COMMENT '角色';

DROP TABLE IF EXISTS `acl_role_permission`;
CREATE TABLE `acl_role_permission` (
    `id` char(19) NOT NULL DEFAULT '',
    `role_id` char(19) NOT NULL DEFAULT '',
    `permission_id` char(19) NOT NULL DEFAULT '',
    `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_time` datetime NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_role_id` (`role_id`),
    KEY `idx_permission_id` (`permission_id`)
) COMMENT='角色权限';

DROP TABLE IF EXISTS `acl_user`;
CREATE TABLE `acl_user` (
    `id` char(19) NOT NULL COMMENT '会员id',
    `username` varchar(20) NOT NULL DEFAULT '' COMMENT '微信openid',
    `password` varchar(32) NOT NULL DEFAULT '' COMMENT '密码',
    `nick_name` varchar(50) DEFAULT NULL COMMENT '昵称',
    `salt` varchar(255) DEFAULT NULL COMMENT '用户头像',
    `token` varchar(100) DEFAULT NULL COMMENT '用户签名',
    `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_time` datetime NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`)
) COMMENT='用户表';

DROP TABLE IF EXISTS `acl_user_role`;
CREATE TABLE `acl_user_role` (
    `id` char(19) NOT NULL DEFAULT '' COMMENT '主键id',
    `role_id` char(19) NOT NULL DEFAULT '0' COMMENT '角色id',
    `user_id` char(19) NOT NULL DEFAULT '0' COMMENT '用户id',
    `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_time` datetime NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_role_id` (`role_id`),
    KEY `idx_user_id` (`user_id`)
) COMMENT '用户角色';