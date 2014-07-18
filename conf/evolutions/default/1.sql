# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table authorised_user (
  id                        bigint not null,
  first_name                varchar(255),
  last_name                 varchar(255),
  email                     varchar(255),
  password                  varchar(255),
  tel                       varchar(255),
  constraint pk_authorised_user primary key (id))
;

create table company (
  id                        bigint not null,
  name                      varchar(255),
  requisite                 varchar(255),
  constraint pk_company primary key (id))
;

create table file (
  id                        bigint not null,
  folder_id                 bigint not null,
  name                      varchar(255),
  path                      varchar(255),
  type                      varchar(255),
  time                      bigint,
  constraint pk_file primary key (id))
;

create table folder (
  id                        bigint not null,
  name                      varchar(255),
  user_id                   bigint,
  time                      bigint,
  constraint pk_folder primary key (id))
;

create table mail (
  id                        bigint not null,
  message                   varchar(255),
  time                      bigint,
  constraint pk_mail primary key (id))
;

create table security_role (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_security_role primary key (id))
;

create table user_permission (
  id                        bigint not null,
  permission_value          varchar(255),
  constraint pk_user_permission primary key (id))
;


create table authorised_user_security_role (
  authorised_user_id             bigint not null,
  security_role_id               bigint not null,
  constraint pk_authorised_user_security_role primary key (authorised_user_id, security_role_id))
;

create table authorised_user_user_permission (
  authorised_user_id             bigint not null,
  user_permission_id             bigint not null,
  constraint pk_authorised_user_user_permission primary key (authorised_user_id, user_permission_id))
;

create table company_authorised_user (
  company_id                     bigint not null,
  authorised_user_id             bigint not null,
  constraint pk_company_authorised_user primary key (company_id, authorised_user_id))
;

create table file_authorised_user (
  file_id                        bigint not null,
  authorised_user_id             bigint not null,
  constraint pk_file_authorised_user primary key (file_id, authorised_user_id))
;

create table file_folder (
  file_id                        bigint not null,
  folder_id                      bigint not null,
  constraint pk_file_folder primary key (file_id, folder_id))
;

create table mail_file (
  mail_id                        bigint not null,
  file_id                        bigint not null,
  constraint pk_mail_file primary key (mail_id, file_id))
;
create sequence authorised_user_seq;

create sequence company_seq;

create sequence file_seq;

create sequence folder_seq;

create sequence mail_seq;

create sequence security_role_seq;

create sequence user_permission_seq;

alter table file add constraint fk_file_folder_1 foreign key (folder_id) references folder (id);
create index ix_file_folder_1 on file (folder_id);
alter table folder add constraint fk_folder_user_2 foreign key (user_id) references authorised_user (id);
create index ix_folder_user_2 on folder (user_id);



alter table authorised_user_security_role add constraint fk_authorised_user_security_r_01 foreign key (authorised_user_id) references authorised_user (id);

alter table authorised_user_security_role add constraint fk_authorised_user_security_r_02 foreign key (security_role_id) references security_role (id);

alter table authorised_user_user_permission add constraint fk_authorised_user_user_permi_01 foreign key (authorised_user_id) references authorised_user (id);

alter table authorised_user_user_permission add constraint fk_authorised_user_user_permi_02 foreign key (user_permission_id) references user_permission (id);

alter table company_authorised_user add constraint fk_company_authorised_user_co_01 foreign key (company_id) references company (id);

alter table company_authorised_user add constraint fk_company_authorised_user_au_02 foreign key (authorised_user_id) references authorised_user (id);

alter table file_authorised_user add constraint fk_file_authorised_user_file_01 foreign key (file_id) references file (id);

alter table file_authorised_user add constraint fk_file_authorised_user_autho_02 foreign key (authorised_user_id) references authorised_user (id);

alter table file_folder add constraint fk_file_folder_file_01 foreign key (file_id) references file (id);

alter table file_folder add constraint fk_file_folder_folder_02 foreign key (folder_id) references folder (id);

alter table mail_file add constraint fk_mail_file_mail_01 foreign key (mail_id) references mail (id);

alter table mail_file add constraint fk_mail_file_file_02 foreign key (file_id) references file (id);

# --- !Downs

drop table if exists authorised_user cascade;

drop table if exists authorised_user_security_role cascade;

drop table if exists authorised_user_user_permission cascade;

drop table if exists company cascade;

drop table if exists company_authorised_user cascade;

drop table if exists file cascade;

drop table if exists file_authorised_user cascade;

drop table if exists file_folder cascade;

drop table if exists folder cascade;

drop table if exists mail cascade;

drop table if exists mail_file cascade;

drop table if exists security_role cascade;

drop table if exists user_permission cascade;

drop sequence if exists authorised_user_seq;

drop sequence if exists company_seq;

drop sequence if exists file_seq;

drop sequence if exists folder_seq;

drop sequence if exists mail_seq;

drop sequence if exists security_role_seq;

drop sequence if exists user_permission_seq;

