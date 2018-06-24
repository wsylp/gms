create table t_user_role(
    id int(10) not null auto_increment primary key,
    user_id int(10),
    role_id int(10)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

 insert into t_user_role(user_id,role_id) values(1,1);
 insert into t_user_role(user_id,role_id) values(2,2);
 insert into t_user_role(user_id,role_id) values(3,3);
 insert into t_user_role(user_id,role_id) values(4,4);