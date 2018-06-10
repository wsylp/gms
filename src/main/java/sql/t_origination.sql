CREATE TABLE t_organization (
    code VARCHAR(50) NULL DEFAULT NULL,
    name VARCHAR(50) NULL DEFAULT NULL,
    remark varchar(100),
    PRIMARY KEY (code)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;
INSERT INTO t_organization (code, name, remark) VALUES ('070667801', '总部分公司', '用于管理其他信息');
INSERT INTO t_organization (code, name, remark) VALUES ('0706678CG', '总部采购部门', '用于进行采购');
