create table `camera` (
    `camera_id` int(11) unsigned not null auto_increment comment '相机ID',
    `camera_name` varchar(255) not null default '' comment '相机名称，具有唯一性',
    `rtsp_url` varchar(255) not null default '' comment 'RTSP连接地址',
    `description` VARCHAR(500) NULL COMMENT '备注描述' ,
    primary key (`camera_id`)
) engine = innodb default charset = utf8mb4;

create unique index `uk_camera_name` on `camera` (`camera_name`);

create table `device` (
    `device_id` int(11) unsigned not null auto_increment comment 'IOT设备ID',
    `device_name` varchar(255) not null default '' comment 'IOT设备名称，具有唯一性',
    `status` int(2) unsigned not null default 0 comment 'IOT设备状态：0-正常，1-断线',
    `device_scenario` varchar(50) not null default '' comment '设备使用的场景, 关联 com.hankutech.ax.message.protocal.com.hankutech.ax.message.code.ScenarioFlag',
    `description` VARCHAR(500) NULL COMMENT '备注描述' ,
    primary key (`device_id`)
) engine = innodb default charaset = utf8mb4;
ALTER TABLE `device`
ADD COLUMN `ax_plc_id` int(11) NOT NULL DEFAULT 0  COMMENT '艾信 plc的编号， 用于plc与中心服务器通讯时标识自己' AFTER `description`;
ALTER TABLE `device`
ADD COLUMN `app_id` int(11) NOT NULL  DEFAULT 0  COMMENT 'app编号， 用于app与中心服务器通讯时标识自己' AFTER `ax_plc_id`;
ALTER TABLE `device`
ADD COLUMN `device_group_id` INT(11) NOT NULL DEFAULT 0 COMMENT '设备楼栋分组编号， 相同楼栋分组编号的设备中， 只能有一个是活动状态' AFTER `app_id`;



create unique index `uk_device_name` on `device` (`device_name`);

create table `device_camera` (
    `id` char(36) not null default '' comment '设备与相机联合ID，使用36位UUID',
    `device_id` int(11) not null comment '设备表ID',
    `camera_id` int(11) not null comment '相机表ID',
    `ai_type_array` varchar(255) not null default '' comment 'AI检测算法类型数组，使用逗号分隔',
    primary key (`id`)
) engine = innodb default charset = utf8mb4;

create unique index `uk_dc_union_dev_cam` on `device_camera` (`device_id`, `camera_id`);

create table `person` (
    `person_id` int(11) unsigned not null auto_increment comment '人员ID',
    `person_name` varchar(255) not null default '' comment '人员名称',
    `image` MEDIUMTEXT not null  comment '照片base64',
    `phone_num` varchar(50) not null default '' comment '手机号码，具有唯一性',
    `face_ftr_array` MEDIUMTEXT not null  comment '人脸特征向量数组,使用逗号分隔',
    primary key (`person_id`)
) engine = innodb default charset = utf8mb4;

create unique index `uk_person_phone` on `person` (`phone_num`);

create table `device_person` (
    `id` char(36) not null default '' comment '设备与人员联合ID，使用36位UUID',
    `device_id` int(11) not null comment '设备表ID',
    `person_id` int(11) not null comment '人员表ID',
    primary key (`id`)
) engine = innodb default charset = utf8mb4;

-- 事件表
create table `event` (
    `event_id` int(11) unsigned not null auto_increment comment '事件ID',
    `device_id` int(11) not null   comment '设备ID',
    `camera_id` int(11) not null   comment '相机ID',
    `event_type` varchar(50) not null default '' comment '事件类型',
    `event_type_value` int(11) not null comment '预定义事件值，与事件类型有关',
    `event_time` datetime not null comment '事件发生的时间',
    `description` varchar(500) default null comment '事件描述',
    `event_image_path` VARCHAR(200) NULL COMMENT '事件关联的图片路径',
    primary key (`event_id`)
) engine = innodb default charset = utf8mb4;

alter table `event` add index index_event_time (`event_time`);



CREATE TABLE `user` (
  `user_name` VARCHAR(100) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `display_name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`user_name`)
  ) engine = innodb default charset = utf8mb4;

