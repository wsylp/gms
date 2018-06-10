create table t_role_function (
    id int(10) not null auto_increment primary key,
    function_id varchar(20),
    role_id int(10)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;