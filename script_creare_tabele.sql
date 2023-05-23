
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
create table airports (
    id int not null primary key,
    name varchar2(50) not null,
    city_id int not null,
    created_at date,
    updated_at date,
    constraint fk_airport_city_id foreign key (city_id) references cities(id)
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
create table flights (
    id int not null primary key,
    destination varchar2(100),
    departure_city_id integer not null,
    arrival_city_id integer not null,
    departure_date date,
    arrival_date date,
    airplane_id integer not null,
    employees_no integer not null,
    constraint fk_departure_city_id foreign key (departure_city_id) references cities(id),
    constraint fk_arrival_city_id foreign key (arrival_city_id) references cities(id)
)
/
create table users
(
    id          int           not null primary key,
    password    varchar2(100) not null,
    employee_id integer       not null,
    constraint fk_employee foreign key (employee_id) references employees (id)
)
/
declare
    type varr is varray(4) of varchar2(50);
    type varr_1 is varray(4) of integer;
    type varr_2 is varray(4) of integer;
    v_crew_list varr := varr('Pilot', 'Copilot', 'Flight Engineer', 'Flight Attendant');
    v_hours varr_1 := varr_1(75, 75, 40, 85);
    v_ids varr_2 := varr_2(1, 2, 3, 4);
    v_nume varchar2(50);
    v_id integer;
    v_hour integer;
    v_statement varchar2(1000);
    v_ok integer;
    v_cursor integer;
    v_cursor2 integer;
    v_ok2 integer;
    v_statement2 varchar2(1000);
begin
    v_cursor := dbms_sql.open_cursor();
     v_cursor2 := dbms_sql.open_cursor();
    for v_i in 1..4 loop
        --v_nume := lista_nume(TRUNC(DBMS_RANDOM.VALUE(0,lista_nume.count))+1);
        v_nume :=  v_crew_list(v_i);
        v_hour := v_hours(v_i);
        v_id := v_ids(v_i);
        --insert into crew (id, name, max_hours) values (v_id, v_nume, v_hour);
        v_statement := 'insert into crew (id, name, max_hours) values ('||v_id||', '''||v_nume||''', '||v_hour||')';
        dbms_output.put_line(v_statement);
        dbms_sql.parse(v_cursor, v_statement, dbms_sql.native);
        v_ok := dbms_sql.execute(v_cursor);
        v_nume := REPLACE(trim(v_nume), ' ', '_');
        v_statement2:='create table '|| v_nume || '(id int not null, ' ||
                      'employee_id int not null, ' ||
                      'constraint fk_crew_employee_id'||v_i || ' foreign key (employee_id) references employees(id) )';
         dbms_output.put_line(v_statement2);
        dbms_sql.parse(v_cursor2, v_statement2, dbms_sql.native);
        v_ok2 := dbms_sql.execute(v_cursor2);
    end loop;
    dbms_sql.close_cursor(v_cursor);
    dbms_sql.close_cursor(v_cursor2);
end;

 CREATE OR REPLACE TRIGGER delete_cities_trigger
BEFORE DELETE ON countries
FOR EACH ROW
BEGIN
    DELETE FROM cities WHERE country_id = :OLD.id;
END;
/


CREATE OR REPLACE TRIGGER delete_flights_trigger
BEFORE DELETE ON cities
FOR EACH ROW
BEGIN
    DELETE FROM flights
    WHERE departure_city_id = :OLD.id OR arrival_city_id = :OLD.id;
END;
/

