CREATE SCHEMA `railway`;

use railway;

create table user (
	user_id varchar(8) not null,
    username varchar(100) not null,
    name varchar(100),
    constraint user_pk primary key(username)
);

create table task (
	task_id int not null auto_increment,
    description varchar(255),
    priority int,
    due_date date,
    username varchar(100),
    constraint task_pk primary key(task_id),
    constraint task_fk foreign key(username) references user(username)
);
