create table t_role (
    id int(10) not null auto_increment primary key,
    name varchar(20),
    role_code varchar(20),
    remark varchar(50),
     create_time timestamp
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into t_role(name,role_code,remark) values('采购部—总经理','dqzjl','每个地区都有一个总经理');
insert into t_role(name,role_code,remark) values('采购部—副总经理','dqfzjl','每个地区都有一个副总经理');
insert into t_role(name,role_code,remark) values('采购部—采购员','cgy','用于采购物品');