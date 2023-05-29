DROP database oj;
create database if not exists oj character set utf8;

use oj;

create table user
(
    id bigint not null comment '主键' primary key,

    status int default 1 not null comment '状态',

    password varchar(20) not null comment '密码',

    num_phone varchar(11) not null comment '手机号',

    create_time datetime not null comment '创建时间',

    power int default 0 not null comment '用户权限'
)comment '用户表';


create table questions(
    id bigint not null comment '主键id' primary key ,

    create_id bigint not null comment '创建者的id',

    status int default 1 not null comment '状态',

    create_time datetime not null comment '创建时间',

    power int default 0 not null comment '题目权限',

    title varchar(24) not null comment '标题',

    description tinytext not null comment '题目内容',

    foreign key(create_id) references user(id)
)comment '问题表';


create table question_redis(
    question_id bigint not null comment '问题id',

    foreign key (question_id) references questions(id),

    result tinytext not null comment '输入参数和标准结果'

)comment '问题的参数以及结果存入redis';

insert into user(id,status,password,num_phone,create_time,power) values(1,1,'admin',13297416607,'2023-05-03 16:32:18',2);

insert into questions(id,create_id,status,create_time,power,title,description) values(1000,1,1,'2023-06-09 22:09:18',1,'helloWorld','请输出helloWorld');

insert into question_redis(question_id, result) values (1000,'*;helloWorld');

