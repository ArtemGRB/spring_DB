alter table student add constraint student_constraint check (age > 16);
alter table student alter column name set not null;
alter table student add constraint name_unique unique (name);
alter table student alter column age set default 20;