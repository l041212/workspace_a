create table t_holiday(
  `id` int(11) auto_increment primary key comment 'id',
  `name` varchar(64) not null default '' comment '名称',
  `date` date not null comment '日期',
  `count` int(2) not null default 1 comment '天数',
  `status` int(1) not null default '0' comment '状态{0:''可用'',1:''删除''}',
  `description` varchar(256) not null default '' comment '描述'
)engine=innodb charset=utf8;