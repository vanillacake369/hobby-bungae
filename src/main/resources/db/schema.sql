create table if not exists hobby
(
    id         bigint auto_increment
    primary key,
    hobby_name varchar(255) null
    );

create table if not exists state
(
    id bigint auto_increment
    primary key,
    do varchar(255) not null,
    gu varchar(255) not null,
    si varchar(255) not null
    );

create table if not exists user
(
    id           bigint auto_increment
    primary key,
    created_at   datetime(6)  null,
    modified_at  datetime(6)  null,
    email        varchar(255) not null,
    id_name      varchar(255) not null,
    introduction varchar(255) null,
    name         varchar(255) not null,
    nick_name    varchar(255) not null,
    password     varchar(255) not null
    );

create table if not exists post
(
    id          bigint auto_increment
    primary key,
    created_at  datetime(6)  null,
    modified_at datetime(6)  null,
    contents    varchar(500) not null,
    title       varchar(20)  not null,
    state_id    bigint       null,
    user_id     bigint       null,
    constraint FK72mt33dhhs48hf9gcqrq4fxte
    foreign key (user_id) references user (id),
    constraint FKd166pgxvaav9ja19gf4p094jo
    foreign key (state_id) references state (id)
    );

create table if not exists comment
(
    id      bigint auto_increment
    primary key,
    text    varchar(255) not null,
    post_id bigint       null,
    user_id bigint       null,
    constraint FK8kcum44fvpupyw6f5baccx25c
    foreign key (user_id) references user (id),
    constraint FKs1slvnkuemjsq2kj4h3vhx7i1
    foreign key (post_id) references post (id)
    );

create table if not exists post_hobby
(
    id       bigint auto_increment
    primary key,
    hobby_id bigint not null,
    post_id  bigint not null,
    constraint FKfulbldwwtsrmg2fc1e6kbth1a
    foreign key (hobby_id) references hobby (id),
    constraint FKgcbmeedkjk7fp4ke0ky2xn33a
    foreign key (post_id) references post (id)
    );

create table if not exists user_hobby
(
    id       bigint auto_increment
    primary key,
    hobby_id bigint not null,
    user_id  bigint not null,
    constraint FKbb99gkcqchq4w3hmqnugmcwhc
    foreign key (user_id) references user (id),
    constraint FKg5dvw1ny1qp6j37gkueih2md0
    foreign key (hobby_id) references hobby (id)
    );

