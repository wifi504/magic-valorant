CREATE TABLE `t_file`  (
  `id` bigint NOT NULL COMMENT '文件ID',
  `upload_user` bigint NULL COMMENT '上传用户ID',
  `object_directory` varchar(255) NOT NULL DEFAULT '' COMMENT '对象目录',
  `object_key` varchar(255) NOT NULL DEFAULT '' COMMENT '对象键(UUID.拓展名)',
  `file_extension` varchar(255) NULL COMMENT '文件拓展名',
  `url` varchar(255) NULL COMMENT '访问URL(目录名/键名)',
  `permission_code` varchar(255) NULL COMMENT '访问权限标识符(为空不鉴权)',
  `origin_name` varchar(255) NULL COMMENT '原始文件名(含拓展名)',
  `file_byte_size` bigint NULL COMMENT '文件大小(字节)',
  `download_count` bigint NOT NULL DEFAULT 0 COMMENT '下载量统计',
  `refrence` bigint NOT NULL DEFAULT 0 COMMENT '业务引用总次数',
  `upload_timestamp` bigint NOT NULL COMMENT '上传时间戳(ms)',
  `ttl` bigint NOT NULL DEFAULT -1 COMMENT '有效期(-1=永久有效;单位ms)',
  `deleted_timestamp` bigint NULL COMMENT '逻辑删除(null=正常;时间戳=删除时间)',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uq_url`(`url`) USING BTREE,
  UNIQUE INDEX `uq_dir_key`(`object_directory`, `object_key`) USING BTREE
) COMMENT = '文件对象存储表';

CREATE TABLE `t_log_user`  (
  `id` bigint NOT NULL COMMENT '用户日志ID',
  `user_id` bigint NULL COMMENT '用户ID',
  `action` varchar(255) NULL COMMENT '操作类型',
  `status` tinyint NULL COMMENT '状态(0=成功;1=失败)',
  `method` varchar(10) NULL COMMENT '请求方法',
  `uri` varchar(255) NULL COMMENT '完整请求URI',
  `response` varchar(1000) NULL COMMENT '服务端响应',
  `duration` int NULL COMMENT '请求耗时(ms)',
  `ip` varchar(45) NULL COMMENT 'IP地址',
  `user_agent` varchar(255) NULL COMMENT '请求UA标识',
  `created_timestamp` bigint NULL COMMENT '请求创建时间戳(ms)',
  PRIMARY KEY (`id`)
) COMMENT = '用户操作日志表';

CREATE TABLE `t_permission`  (
  `id` bigint NOT NULL COMMENT '权限ID',
  `name` varchar(255) NOT NULL COMMENT '权限名称',
  `parent_id` bigint NULL COMMENT '父权限ID',
  `code` varchar(255) NULL COMMENT '权限标识符',
  `order_num` int NOT NULL DEFAULT 0 COMMENT '显示顺序',
  `type` enum('list','menu','api') NOT NULL DEFAULT 'api' COMMENT '权限类型(list=目录;menu=菜单;api=接口)',
  `method` enum('GET','POST','PUT','DELETE') NULL DEFAULT NULL COMMENT '接口方法',
  `url` varchar(255) NULL COMMENT '权限URL',
  `route` varchar(255) NULL COMMENT '前端路由',
  `icon` varchar(255) NULL COMMENT '图标名',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uq_code`(`code`) USING BTREE
) COMMENT = '权限表';

CREATE TABLE `t_role`  (
  `id` bigint NOT NULL COMMENT '角色ID',
  `order_num` int NOT NULL DEFAULT 0 COMMENT '排序顺序',
  `code` varchar(255) NOT NULL COMMENT '角色标识符',
  `name` varchar(255) NOT NULL COMMENT '角色名称',
  `remark` varchar(255) NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uq_code`(`code`) USING BTREE
) COMMENT = '角色表';

CREATE TABLE `t_role_permission`  (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `permission_id` bigint NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`role_id`, `permission_id`),
  UNIQUE INDEX `uq_role_permission`(`role_id`, `permission_id`) USING BTREE
) COMMENT = '角色-权限关联表';

CREATE TABLE `t_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `nickname` varchar(8) NOT NULL COMMENT '昵称',
  `email` varchar(255) NULL COMMENT '邮箱(可用于登录)',
  `password` varchar(128) NULL COMMENT '登录密码',
  `wx_openid` varchar(32) NULL COMMENT '微信openid',
  `avatar` varchar(255) NULL COMMENT '头像URL',
  `level` int NOT NULL DEFAULT 0 COMMENT '用户等级',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '用户状态(0=正常;1=封禁)',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除(0=正常;1=删除)',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户创建时间',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uq_email`(`email`) USING BTREE,
  UNIQUE INDEX `uq_wx_openid`(`wx_openid`) USING BTREE
) COMMENT = '用户表';

CREATE TABLE `t_user_role`  (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`),
  UNIQUE INDEX `uq_user_role`(`user_id`, `role_id`) USING BTREE
) COMMENT = '用户-角色关联表';

CREATE TABLE `t_val_agent`  (
  `id` bigint NOT NULL COMMENT '特工ID',
  `name` varchar(255) NOT NULL COMMENT '特工正式名称',
  `nickname` varchar(255) NULL COMMENT '玩家常用称呼',
  `avatar` varchar(255) NULL COMMENT '头像图URL',
  `photo` varchar(255) NULL COMMENT '写真图URL',
  PRIMARY KEY (`id`)
) COMMENT = '瓦特工表';

CREATE TABLE `t_val_agent_ability`  (
  `id` bigint NOT NULL COMMENT '技能ID',
  `agent_id` bigint NOT NULL COMMENT '特工ID',
  `name` varchar(255) NOT NULL COMMENT '技能名称',
  `nickname` varchar(255) NULL COMMENT '玩家常用称呼',
  `icon` varchar(255) NULL COMMENT '技能图标URL',
  `type` enum('passive','basic','signature','ultimate') NOT NULL DEFAULT 'basic' COMMENT '技能类型(被动/基础/招牌/终极)',
  `detail` varchar(255) NULL COMMENT '技能描述',
  PRIMARY KEY (`id`)
) COMMENT = '瓦特工技能表';

CREATE TABLE `t_val_map`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '地图ID',
  `name` varchar(255) NOT NULL COMMENT '地图名称',
  `cover` varchar(255) NULL COMMENT '封面图URL',
  `map` varchar(255) NULL COMMENT '平面图URL',
  `map_size_x` double NULL COMMENT '平面图水平尺寸(像素)',
  `map_size_y` double NULL COMMENT '平面图竖直尺寸(像素)',
  PRIMARY KEY (`id`)
) COMMENT = '瓦地图池表';

CREATE TABLE `t_val_map_area`  (
  `id` bigint NOT NULL COMMENT '地图区域ID',
  `map_id` bigint NOT NULL COMMENT '地图ID',
  `name` varchar(255) NULL COMMENT '区域名',
  `point` varchar(255) NULL COMMENT '标注坐标',
  `polygon` varchar(255) NULL COMMENT '点相对坐标数组',
  PRIMARY KEY (`id`)
) COMMENT = '瓦地图区域标注表';

ALTER TABLE `t_file` ADD CONSTRAINT `tf_fk_user` FOREIGN KEY (`upload_user`) REFERENCES `t_user` (`id`) ON DELETE NO ACTION ON UPDATE RESTRICT;
ALTER TABLE `t_file` ADD CONSTRAINT `tf_fk_code` FOREIGN KEY (`permission_code`) REFERENCES `t_permission` (`code`) ON DELETE NO ACTION ON UPDATE RESTRICT;
ALTER TABLE `t_log_user` ADD CONSTRAINT `tlu_fk_user` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE NO ACTION ON UPDATE RESTRICT;
ALTER TABLE `t_permission` ADD CONSTRAINT `tp_fk_parent_id` FOREIGN KEY (`parent_id`) REFERENCES `t_permission` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT;
ALTER TABLE `t_role_permission` ADD CONSTRAINT `trp_fk_role` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT;
ALTER TABLE `t_role_permission` ADD CONSTRAINT `trp_fk_permission` FOREIGN KEY (`permission_id`) REFERENCES `t_permission` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT;
ALTER TABLE `t_user_role` ADD CONSTRAINT `tur_fk_user` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT;
ALTER TABLE `t_user_role` ADD CONSTRAINT `tur_fk_role` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT;
ALTER TABLE `t_val_agent_ability` ADD CONSTRAINT `tvaa_fk_agent` FOREIGN KEY (`agent_id`) REFERENCES `t_val_agent` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT;
ALTER TABLE `t_val_map_area` ADD CONSTRAINT `tvma_fk_map` FOREIGN KEY (`map_id`) REFERENCES `t_val_map` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT;

