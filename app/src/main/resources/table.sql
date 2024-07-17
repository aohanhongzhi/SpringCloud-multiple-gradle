create table if not exists user_model
(
    id          int unsigned auto_increment
        primary key,
    name        varchar(20)       not null,
    phone_number       char(11)          null,
    address     varchar(256)       null,
    age         int               null comment '年龄',
    gender      int           null comment '性别',
    password    varchar(256)      null,
    create_time datetime          null,
    update_time datetime          null,
    other_info  json              null,
    basic_infos json              null,
    deleted     tinyint default 0 not null
);


