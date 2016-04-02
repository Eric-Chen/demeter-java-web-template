CREATE TABLE operation_log(
  id number(10) not null PRIMARY KEY,
  uid number(10) not null,
  username varchar2(50) not null,
  module VARCHAR2(20) not null,
  operation VARCHAR2(100) not null,
  changeData VARCHAR2(1000) not null,
  created TIMESTAMP not null,
  updated TIMESTAMP not NULL
);

comment on table operation_log is '操作日志表';
comment on column operation_log.username is '用户名';
comment on column operation_log.operation is '操作';
comment on column operation_log.change_data is '变更数据';

create SEQUENCE operation_log_seq START WITH 10000 MAXVALUE 100000000000000 INCREMENT BY 1;