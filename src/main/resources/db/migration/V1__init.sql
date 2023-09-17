create table users (
                       user_id                    bigserial,
                       username              varchar(30) not null unique,
                       password              varchar(80) not null,
                       email                 varchar(50) unique,
                       primary key (user_id)
);

create table roles (
                       role_id                    serial,
                       name                  varchar(50) not null,
                       primary key (role_id)
);

CREATE TABLE users_roles (
                             user_id               bigint not null,
                             role_id               int not null,
                             primary key (user_id, role_id),
                             foreign key (user_id) references users (user_id),
                             foreign key (role_id) references roles (role_id)
);

CREATE TABLE tasks (
                              task_id bigserial NOT NULL,
                              "name" varchar(255) NULL,
                              status varchar(255) NULL,
                              user_id int8 NULL,
                              CONSTRAINT tasks_pkey FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE responsible_user_tasks (
                                               responsible_user_id int8 NOT NULL,
                                               task_id int8 NOT NULL
);

insert into roles (name)
values
    ('ROLE_USER'), ('ROLE_ADMIN');

insert into users (username, password, email)
values
    ('user', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'user@gmail.com'),
    ('admin', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'admin@gmail.com');

insert into users_roles (user_id, role_id)
values
    (1, 1),
    (2, 2);