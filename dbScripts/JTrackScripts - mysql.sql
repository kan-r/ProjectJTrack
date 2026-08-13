-- create user 'jtrack' identified by 'provide pwd here';
-- create database jtrackdb;
-- grant all on jtrackdb.* to 'jtrack';



create table job_types(
  code				varchar(20) not null primary key,
  description		varchar(255)
);


create table job_statuses(
  code        		varchar(20) not null primary key,
  description		varchar(255),
  display_order		int not null unique
);

  
create table job_priorities(
  code      		varchar(10) not null primary key,
  description		varchar(255),
  display_order		int not null unique
);


create table sprint_statuses(
  code        		varchar(20) not null primary key,
  description		varchar(255),
  display_order		int not null unique
);


create table sprints(
  id          		int not null auto_increment primary key,
  name				varchar(100) not null unique,
  status_code		varchar(20) not null,
  start_date		date,
  end_date			date,
  created_at	  	timestamp default current_timestamp,
  created_by		varchar(100),
  updated_at		timestamp default null on update current_timestamp,
  updated_by		varchar(100),
  foreign key (status_code) references sprint_statuses(code)
);
  

create table jobs(
  id          			int not null auto_increment primary key,
  sprint_id				int,
  name		  			varchar(100) not null unique,
  description		  	text(4000),
  type_code        		varchar(20) not null,
  priority_code     	varchar(20) not null,
  status_code       	varchar(20) not null,
  assigned_to      		varchar(100),
  estimated_hours   	decimal(5, 2),
  actual_hours 			decimal(5, 2),
  parent_id 			int,
  created_at	  		timestamp default current_timestamp,
  created_by			varchar(100),
  updated_at			timestamp default null on update current_timestamp,
  updated_by			varchar(100),
  foreign key (sprint_id) references sprints(id),
  foreign key (type_code) references job_types(code),
  foreign key (priority_code) references job_priorities(code),
  foreign key (status_code) references job_statuses(code),
  foreign key (parent_id) references jobs(id)
);


create table timesheets(
  id				int not null auto_increment primary key,
  user_id          	varchar(100) not null,
  job_id          	int not null,
  worked_date		date,
  worked_hours     	decimal(5, 2),
  created_at	  	timestamp default current_timestamp,
  created_by		varchar(100),
  updated_at		timestamp default null on update current_timestamp,
  updated_by		varchar(100),
  foreign key (job_id) references jobs(id) on delete cascade
);


	
-- DATA ---------------------------------------------------------------


-- job_types

insert into job_types(code, description)
values('TASK', 'Task');

insert into job_types(code, description)
values('SUB_TASK', 'Sub Task');

insert into job_types(code, description)
values('BUG', 'Bug');


-- job_statuses

insert into job_statuses(code, description, display_order)
values('BACKLOG', 'Backlog', 10);

insert into job_statuses(code, description, display_order)
values('OPEN', 'Open', 20);

insert into job_statuses(code, description, display_order)
values('IN_PROGRESS', 'In Progress', 30);

insert into job_statuses(code, description, display_order)
values('CODE_REVIEW', 'Code Review', 40);

insert into job_statuses(code, description, display_order)
values('TESTING', 'Testing', 50);

insert into job_statuses(code, description, display_order)
values('CLOSED', 'Closed', 60);


-- job_priorities

insert into job_priorities(code, description, display_order)
values('LOW', 'Low', 10);

insert into job_priorities(code, description, display_order)
values('MEDIUM', 'Medium', 20);

insert into job_priorities(code, description, display_order)
values('HIGH', 'High', 30);

insert into job_priorities(code, description, display_order)
values('CRITICAL', 'Critical', 40);


-- sprint_statuses

insert into sprint_statuses(code, description, display_order)
values('PLANNING', 'Planning', 10);

insert into sprint_statuses(code, description, display_order)
values('ACTIVE', 'Active', 20);

insert into sprint_statuses(code, description, display_order)
values('COMPLETED', 'Completed', 30);


-- END ----------------------------------------------------------------
