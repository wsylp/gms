CREATE TABLE t_user (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    real_name VARCHAR(50) NULL DEFAULT NULL,
    login_name VARCHAR(50) NULL DEFAULT NULL,
    password VARCHAR(50) NULL DEFAULT NULL,
    org_code varchar(20) ,
    phone VARCHAR(50) NULL DEFAULT NULL,
    id_card VARCHAR(50) NULL DEFAULT NULL,
    level BIGINT(20) NULL DEFAULT NULL,
    family_adress VARCHAR(50) NULL DEFAULT NULL,
    work_adress VARCHAR(50) NULL DEFAULT NULL,
    work_type VARCHAR(10) NULL DEFAULT NULL,
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    update_time TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00',
    PRIMARY KEY (id)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

INSERT INTO t_user (  real_name, login_name, password, org_code, phone, id_card, level, family_adress, work_adress, work_type, create_time, update_time) 
VALUES (  '李总', '0001', '123456', '0706678CG', '1', '2', 1, '1', '1', '1', '2018-05-13 00:18:07', '0000-00-00 00:00:00');

INSERT INTO t_user (  real_name, login_name, password, org_code, phone, id_card, level, family_adress, work_adress, work_type, create_time, update_time) 
VALUES (  '魏总', '0002', '123456', '0706678CG', '1', '2', 1, '1', '1', '1', '2018-05-13 00:18:07', '0000-00-00 00:00:00');

INSERT INTO t_user (  real_name, login_name, password, org_code, phone, id_card, level, family_adress, work_adress, work_type, create_time, update_time) 
VALUES (  '采购员小张', '0003', '123456', '0706678CG', '1', '2', 1, '1', '1', '1', '2018-05-13 00:18:07', '0000-00-00 00:00:00');



