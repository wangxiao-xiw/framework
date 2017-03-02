--create role ${projectName} login  nosuperuser inherit createdb createrole replication ;
create role ${projectName} with nosuperuser login unencrypted password '${projectName}';
create ${projectName} ${projectName}_user with owner=${projectName} encoding='utf-8';

create sequence seq_${projectName}_users
  increment 1
  minvalue 1
  maxvalue 9223372036854775807
  start 900000001
  cache 1;
alter table seq_${projectName}_users
  owner to ${projectName};
  
create table t_${projectName}_users
(
  id bigint not null default nextval('seq_${projectName}_users'::regclass),
  login_account character varying,
  password character varying,
  real_name character varying,
  nick_name character varying,
  sex character varying,
  qq character varying,
  wei_xin character varying,
  phone_number character varying,
  sign character varying,
  avatar_url character varying,
  last_update_time timestamp without time zone,
  create_time timestamp without time zone,
  is_deleted boolean,
  constraint t_${projectName}_users_pkey primary key (id)
)
with (
  oids=false
);
alter table t_${projectName}_users
  owner to ${projectName}; 
  
  
------------------------type---------------------
create sequence seq_${projectName}_dictionary_type
  increment 1
  minvalue 1
  maxvalue 9223372036854775807
  start 10000003
  cache 1;
alter table seq_${projectName}_dictionary_type
  owner to ${projectName};

create table t_${projectName}_dictionary_type
(
  id bigint not null default nextval('seq_${projectName}_dictionary_type'::regclass),
  type_name character varying,
  type_alias character varying,
  create_time timestamp without time zone,
  modify_time timestamp without time zone,
  ref_code character varying,
  is_leaf boolean default false,
  is_deleted boolean default false,
  parent_code bigint,
  version_no bigint,
  constraint t_${projectName}_dictionary_type_pkey primary key (id)
)
with (
  oids=false
);
alter table t_${projectName}_dictionary_type
  owner to ${projectName};

------------------------data-----------------------------

create sequence seq_${projectName}_dictionary_data
  increment 1
  minvalue 1
  maxvalue 9223372036854775807
  start 1000006
  cache 1;
alter table seq_${projectName}_dictionary_data
  owner to ${projectName};
  
create table t_${projectName}_dictionary_data
(
  id bigint not null default nextval('seq_${projectName}_dictionary_data'::regclass),
  dict_type character varying,
  value_order character varying,
  value_name character varying,
  value_code character varying,
  language character varying ,--可能用的字段
  note_info character varying,
  is_deleted boolean default false,
  ref_code character varying,
  create_time timestamp without time zone,
  modify_time timestamp without time zone,
  constraint t_${projectName}_dictionary_data_pkey primary key (id)
)
with (
  oids=false
);
alter table t_${projectName}_dictionary_data
  owner to ${projectName};
 
create sequence seq_${projectName}_function
  increment 1
  minvalue 1
  maxvalue 9223372036854775807
  start 100010
  cache 1;
alter table seq_${projectName}_function
  owner to ${projectName};

-- 权限表 t_${projectName}_function
create table t_${projectName}_function
(
  id             	bigint not null default nextval('seq_${projectName}_function'::regclass),
  parent_id    	 	bigint,
  function_name  	character varying,
  uri            	character varying,
  function_level 	integer,
  valid_flag     	boolean default false,
  display_order  	integer,
  leaf           	boolean default false,
  function_type  	integer,
  function_desc  	character varying,
  create_time    	DATE,
  last_update_time	DATE,
  is_deleted     	boolean default false,
  constraint t_${projectName}_function_pkey primary key (id)
)
with (
  oids=false
);
alter table t_${projectName}_function
  owner to ${projectName};
 
create sequence seq_${projectName}_functionrole
  increment 1
  minvalue 1
  maxvalue 9223372036854775807
  start 100010
  cache 1;
alter table seq_${projectName}_functionrole
  owner to ${projectName};
-- 角色权限 t_${projectName}_functionrole
create table t_${projectName}_functionrole
(
  id          bigint not null default nextval('seq_${projectName}_functionrole'::regclass),
  role_id     bigint not null,
  function_id bigint not null
)
with (
  oids=false
);
alter table t_${projectName}_functionrole
  owner to ${projectName};

create sequence seq_${projectName}_role
  increment 1
  minvalue 1
  maxvalue 9223372036854775807
  start 100001
  cache 1;
alter table seq_${projectName}_role
  owner to ${projectName};
-- 角色 t_${projectName}_role
create table t_${projectName}_role
(
  id          bigint not null default nextval('seq_${projectName}_role'::regclass),
  role_name   character varying,
  role_desc   character varying,
  create_time    	DATE,
  last_update_time	DATE,
  is_deleted     	boolean default false,
  constraint t_${projectName}_role_pkey primary key (id)
)
with (
  oids=false
);
alter table t_${projectName}_role
  owner to ${projectName};
  
create sequence seq_${projectName}_userrole
  increment 1
  minvalue 1
  maxvalue 9223372036854775807
  start 100001
  cache 1;
alter table seq_${projectName}_userrole
  owner to ${projectName};

-- 用户角色 t_${projectName}_userrole
create table t_${projectName}_userrole
(
  id          bigint not null default nextval('seq_${projectName}_userrole'::regclass),
  role_id     bigint not null,
  user_id bigint not null
)
with (
  oids=false
);
alter table t_${projectName}_userrole
  owner to ${projectName};

create sequence seq_${projectName}_useraccount
  increment 1
  minvalue 1
  maxvalue 9223372036854775807
  start 1000001
  cache 1;
alter table seq_${projectName}_useraccount owner to ${projectName};

create table t_${projectName}_useraccount(
  id bigint not null default nextval('seq_${projectName}_useraccount'::regclass),
  login_account character varying,
  password character varying,
  real_name character varying,
  nick_name character varying,
  mobile_phone character varying,
  identity_card character varying,
  sex character varying,
  qq character varying,
  wei_xin character varying,
  avatar_url character varying,
  create_time timestamp without time zone,
  last_update_time timestamp without time zone,
  is_deleted boolean,
  constraint t_${projectName}_useraccount_pkey primary key(id)
)with (oids=false);
alter table t_${projectName}_useraccount owner to ${projectName};
