CREATE TABLE IF NOT EXISTS `t_menu` (
  id varchar(20) NOT NULL,
  name varchar(50) NOT NULL,
  url varchar(50) NOT NULL,
  parent_id varchar(20) NOT NULL,
  leval int(5) DEFAULT NULL,
  sort int(5) DEFAULT NULL,
  icon_cls varchar(15),
  state varchar(10),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into t_menu  values ('menu_user_001','用户管理','',-1,1,1,'','closed');
 insert into t_menu  values ('menu_user_002','用户管理','','menu_user_001',1,1,'','closed');
 insert into t_menu  values ('menu_user_003','用户查询','','menu_user_001',1,1,'','closed');
