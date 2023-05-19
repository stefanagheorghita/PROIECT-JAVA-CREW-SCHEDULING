
CREATE TABLE countries (
id INT NOT NULL PRIMARY KEY,
name varchar2(50) NOT NULL UNIQUE,
created_at DATE,
updated_at DATE
)
/
CREATE TABLE CITIES (
id INT NOT NULL PRIMARY KEY,
name varchar2(50) NOT NULL,
country_id INT NOT NULL,
created_at DATE,
updated_at DATE,
CONSTRAINT fk_city_country_id FOREIGN KEY (country_id) REFERENCES countries(id)
)
/


create table crew (
    id int not null primary key,
    name varchar2(50) unique,
    max_hours int not null
)
/

create table employees (
    id int not null primary key,
    first_name varchar2(50) not null,
    last_name varchar2(50) not null,
    birthdate date not null,
    gender varchar2(1) not null,
    crew_id int not null,
    created_at date,
    updated_at date,
    constraint fk_employee_crew_id foreign key (crew_id) references crew(id)
)
/
