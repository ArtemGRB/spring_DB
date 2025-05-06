create table  cars (
    car_id int8 primary key,
    brand VARCHAR(20) NOT NULL
);

create table persons (
    id int primary key,
    name_person varchar(30) not null,
    age_person int not null,
    license boolean not null default false,
    car_id int,
    foreign key (car_id) references cars (car_id)
);