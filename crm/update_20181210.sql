create table t_friend_member(
	`id` int primary key auto_increment comment 'id',
	`type_id` int not null default 0 comment 'type_id',
	`name` varchar(16) not null default '' comment '姓名',
	`sex` tinyint not null default 0 comment '性别{0:\'\',1:\'男\',2:\'女\'}',
	`identity` varchar(32) not null default '' comment '身份证',
	`birthday` timestamp not null default current_timestamp comment '出生日期',
	`phone` varchar(32) not null default '' comment '联络电话',
	`email` varchar(32) not null default '' comment '邮箱地址',
	`address` varchar(128) not null default '' comment '住宅地址',
	`education` tinyint not null default 0 comment '教育{0:\'\',1:\'初中或以下\',2:\'高中\',3:\'本科\',4:\'研究生或以上\'}',
	`job` tinyint not null default 0 comment '职业{0:\'\',1:\'公务员\',1:\'企业\',2:\'学生\',3:\'离退休人员\',4:\'其他\'}',
	`company` varchar(64) not null default '' comment '公司',
	`post` varchar(16) not null default '' comment '职位',
	`line` varchar(16) not null default '' comment '常用线路',
	`destination` varchar(128) not null default '' comment '申请目的',
	`apply_time` timestamp not null default current_timestamp comment '申请日期',
	`status` tinyint not null default 0 comment '状态{0:\'可用\',1:\'删除\'}'
) engine=innodb default charset=utf8;

create table t_friend_member_event(
	`id` int primary key auto_increment comment 'id',
	`event_id` int not null default 0 comment 'event_id',
	`member_id` int not null default 0 comment 'memeber_id'
) engine=innodb default charset=utf8;

/*
create table t_friend_event_photo(
	`id` int primary key auto_increment comment 'id',
	`event_id` int not null default 0 comment 'event_id',
	`index` tinyint not null default 0 comment 'photo index',
	`name` varchar(64) not null default '' comment 'file name',
	`path` varchar(128) not null default '' comment 'url path',
	`time` timestamp not null default current_timestamp comment 'modify time',
	`status` tinyint not null default 0 comment 'status{0:\'active\',1:\'delete\'}'
) engine=innodb default charset=utf8;
*/

create table t_friend_event(
	`id` int primary key auto_increment comment 'id',
	`title` varchar(16) not null default '' comment '标题',
	`time_from` timestamp not null default current_timestamp comment '开始时间',
	`time_to` timestamp not null default current_timestamp comment '结束时间',
	`location` varchar(128) not null default '' comment '地点',
	`content` varchar(128) not null default '' comment '内容文件',
	`photo` varchar(128) not null default '' comment '图片集'
	`summary` varchar(256) not null default '' comment '简介',
	`status` tinyint not null default 0 comment '状态{0:\'可用\',1:\'删除\'}'
) engine=innodb default charset=utf8;
