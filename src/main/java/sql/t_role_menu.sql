create table t_role_menu (
    id int(10) not null auto_increment primary key,
    menu_id varchar(20),
    role_id int(10)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;