
CREATE TABLE countries (
id INT NOT NULL PRIMARY KEY,
name varchar2(255) NOT NULL UNIQUE,
created_at DATE,
updated_at DATE
)
/
CREATE TABLE CITIES (
id INT NOT NULL PRIMARY KEY,
name varchar2(255) NOT NULL,
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

create table airplanes (
    id int not null primary key,
    num_passengers int not null
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

drop table flights;
CREATE TABLE flights (
    id INT NOT NULL PRIMARY KEY,
    departure_city_id INTEGER NOT NULL,
    arrival_city_id INTEGER NOT NULL,
    departure_day VARCHAR2(20),
    departure_hour VARCHAR2(5),
    arrival_hour VARCHAR2(5),
    airplane_id INTEGER,
    employees_no INTEGER,
    aprox_passengers INTEGER,
    CONSTRAINT fk_departure_city_id FOREIGN KEY (departure_city_id) REFERENCES cities(id),
    CONSTRAINT fk_arrival_city_id FOREIGN KEY (arrival_city_id) REFERENCES cities(id)
);



/
-- create table flights (
--     id int not null primary key,
--     destination varchar2(100),
--     departure_city_id integer not null,
--     arrival_city_id integer not null,
--     departure_date date,
--     arrival_date date,
--     airplane_id integer not null,
--     employees_no integer not null,
--     constraint fk_departure_city_id foreign key (departure_city_id) references cities(id),
--     constraint fk_arrival_city_id foreign key (arrival_city_id) references cities(id)
-- )

/
create table users
(
    id          int           not null primary key,
    password    varchar2(100) not null,
    employee_id integer       not null,
    constraint fk_employee foreign key (employee_id) references employees (id)
)

/
create table passengers (
    id int not null primary key,
    flight_id integer not null,
    passengers_number integer not null,
    constraint fk_flight foreign key (flight_id) references flights (id)
)
/

CREATE TYPE crew_assignment_type AS OBJECT (
    crew_role INT,
    employee_id INT
);
/
CREATE TYPE crew_assignment_table_type AS TABLE OF crew_assignment_type;

/
drop table flight_assignments;
/

CREATE TABLE flight_assignments (
    id INT NOT NULL PRIMARY KEY,
    flight_id INT NOT NULL,
    crew_assignments crew_assignment_table_type,
    CONSTRAINT fk_flight_assig_flight_id FOREIGN KEY (flight_id) REFERENCES flights(id)
) NESTED TABLE crew_assignments STORE AS lista;
/
drop table pilot;
/
CREATE TABLE Pilot (
    id INT PRIMARY KEY,
    employeeId INT,
    assignments INT
);
/
drop table copilot cascade constraints;
/
CREATE TABLE Copilot (
    id INT PRIMARY KEY,
    employeeId INT,
    assignments INT
);
/
drop table flight_attendant cascade constraints;
/
CREATE TABLE flight_attendant (
    id INT PRIMARY KEY,
    employeeId INT,
    assignments INT
);
/
drop table flight_engineer cascade constraints;
/
CREATE TABLE flight_engineer (
    id INT PRIMARY KEY,
    employeeId INT,
    assignments INT
);
/

CREATE OR REPLACE TRIGGER trg_flight_assignments_fk
BEFORE INSERT OR UPDATE ON flight_assignments
FOR EACH ROW
    DECLARE
    dummy NUMBER;
BEGIN
    FOR i IN 1..:new.crew_assignments.COUNT LOOP
        BEGIN
            SELECT 1
            INTO dummy
            FROM crew
            WHERE id = :new.crew_assignments(i).crew_role;
        EXCEPTION
            WHEN NO_DATA_FOUND THEN
                RAISE_APPLICATION_ERROR(-20001, 'Invalid crew role: ' || :new.crew_assignments(i).crew_role);
        END;

        BEGIN
            SELECT 1
            INTO dummy
            FROM employees
            WHERE id = :new.crew_assignments(i).employee_id;
        EXCEPTION
            WHEN NO_DATA_FOUND THEN
                RAISE_APPLICATION_ERROR(-20002, 'Invalid employee ID: ' || :new.crew_assignments(i).employee_id);
        END;
    END LOOP;
END;
/


/
/*declare
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
end;*/


/
-------------------------------------
CREATE OR REPLACE PACKAGE my_package AS
    TYPE varr IS VARRAY(4) OF VARCHAR2(50);
    TYPE varr_1 IS VARRAY(4) OF INTEGER;
    TYPE varr_2 IS VARRAY(4) OF INTEGER;

    crew_insertion_error EXCEPTION;
    table_creation_error EXCEPTION;

    PRAGMA EXCEPTION_INIT(crew_insertion_error, -20001);
    PRAGMA EXCEPTION_INIT(table_creation_error, -20002);

    PROCEDURE insert_crew_and_tables;
END my_package;
/

CREATE OR REPLACE PACKAGE BODY my_package AS
    v_crew_list varr := varr('Pilot', 'Copilot', 'Flight Engineer', 'Flight Attendant');
    v_hours varr_1 := varr_1(75, 75, 40, 85);
    v_ids varr_2 := varr_2(1, 2, 3, 4);

    PROCEDURE insert_crew_and_tables IS
        v_nume VARCHAR2(50);
        v_id INTEGER;
        v_hour INTEGER;
        v_statement VARCHAR2(1000);
        v_cursor1 INTEGER;
        v_cursor2 INTEGER;
        v_result1 INTEGER;
        v_result2 INTEGER;
    BEGIN
        v_cursor1 := dbms_sql.open_cursor();
        v_cursor2 := dbms_sql.open_cursor();

        FOR v_i IN 1..4 LOOP
            v_nume := v_crew_list(v_i);
            v_hour := v_hours(v_i);
            v_id := v_ids(v_i);

            -- Insert into crew table
            v_statement := 'INSERT INTO crew (id, name, max_hours) VALUES (:1, :2, :3)';
            dbms_output.put_line(v_statement);
            dbms_sql.parse(v_cursor1, v_statement, dbms_sql.native);
            dbms_sql.bind_variable(v_cursor1, ':1', v_id);
            dbms_sql.bind_variable(v_cursor1, ':2', v_nume);
            dbms_sql.bind_variable(v_cursor1, ':3', v_hour);

            v_result1 := dbms_sql.execute(v_cursor1);

            -- Check for errors and raise custom exception if necessary
            IF dbms_sql.last_row_count <> 1 THEN
                RAISE crew_insertion_error;
            END IF;

            -- Create table for each crew member
            v_nume := REPLACE(TRIM(v_nume), ' ', '_');
            v_statement := 'CREATE TABLE '|| v_nume || ' (id INT NOT NULL, ' ||
                            'employee_id INT NOT NULL, ' ||
                            'CONSTRAINT fk_crew_employee_id'||v_i || ' FOREIGN KEY (employee_id) REFERENCES employees(id) )';
            dbms_output.put_line(v_statement);
            dbms_sql.parse(v_cursor2, v_statement, dbms_sql.native);

            v_result2 := dbms_sql.execute(v_cursor2);

            -- Check for errors and raise custom exception if necessary
            IF v_result2 <> 0 THEN
                RAISE table_creation_error;
            END IF;
        END LOOP;

        dbms_sql.close_cursor(v_cursor1);
        dbms_sql.close_cursor(v_cursor2);
    /*EXCEPTION
        WHEN crew_insertion_error THEN
            dbms_output.put_line('Error inserting crew member');
            -- Handle the exception as desired

        WHEN table_creation_error THEN
            dbms_output.put_line('Error creating table');
            -- Handle the exception as desired*/
    END insert_crew_and_tables;
    -- Implement additional procedures, functions, or declarations as needed
END my_package;
/
BEGIN
    BEGIN
        my_package.insert_crew_and_tables;
    EXCEPTION
        WHEN my_package.crew_insertion_error THEN
            dbms_output.put_line('Error inserting crew member');
            -- Handle the crew insertion error as desired

        WHEN my_package.table_creation_error THEN
            dbms_output.put_line('Error creating table');
            -- Handle the table creation error as desired

        WHEN OTHERS THEN
            dbms_output.put_line('An error occurred');
            -- Handle other exceptions if necessary
    END;
END;
/


-------------------------------------

/*
drop table flight_attendant;
/
drop table flight_engineer;
/
drop table pilot;
/
drop table copilot;
/
delete from crew where id in (1, 2, 3, 4);
/
select * from crew;
/
select * from pilot;
*/


/
CREATE OR REPLACE TRIGGER delete_employees_trigger
BEFORE DELETE ON crew
FOR EACH ROW
BEGIN
    DELETE FROM employees WHERE crew_id = :OLD.id;
END;
/

CREATE OR REPLACE TRIGGER delete_user_trigger
BEFORE DELETE ON employees
FOR EACH ROW
BEGIN
    DELETE FROM users WHERE employee_id = :OLD.id;
END;
/



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
select * from flights;
select * from cities;
insert into countries(id,name) values (1,'lalal');
 insert into cities (id,name,country_id) values (1,'d',1);
 insert into cities (id,name,country_id) values (2,'dii',1);
 insert into cities (id,name,country_id) values (3,'od',1);
 insert into cities (id,name,country_id) values (4,'fv',1);
CREATE SEQUENCE SEQUENCE1 START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEQUENCE5 START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEQUENCE6 START WITH 1 INCREMENT BY 1;
delete from flights where id=1;
INSERT INTO flights (id, departure_city_id, arrival_city_id, departure_day, departure_hour, arrival_hour,aprox_passengers)
VALUES (1, 1, 2, 'Tuesday', '15:30','16:30',100);

INSERT INTO flights (id, departure_city_id, arrival_city_id, departure_day, departure_hour, arrival_hour,aprox_passengers)
VALUES (2, 1, 3, 'Tuesday', '15:30','16:30',120);


INSERT INTO flights (id, departure_city_id, arrival_city_id, departure_day, departure_hour, arrival_hour,aprox_passengers)
VALUES (3, 2, 4, 'Monday', '15:30','16:30',120);

INSERT INTO flights (id, departure_city_id, arrival_city_id, departure_day, departure_hour, arrival_hour,aprox_passengers)
VALUES (4, 1, 2, 'Monday', '14:30','15:20',80);

select * from airplanes;
select * from flights;
select * from countries;
select * from cities;
delete from countries where id=1;
CREATE SEQUENCE SEQUENCE2 START WITH 966 INCREMENT BY 1;
DROP SEQUENCE SEQUENCE2;
select * from cities;
SELECT sequence_name
FROM user_sequences
WHERE sequence_name = 'SEQUENCE2';

SELECT grantee, privilege FROM all_tab_privs WHERE table_name = 'SEQUENCE2';

CREATE SEQUENCE SEQUENCE2 START WITH 966 INCREMENT BY 1 NOCACHE;

SELECT sequence_name, min_value
FROM user_sequences
WHERE sequence_name = 'SEQUENCE2';

SELECT SEQUENCE2.NEXTVAL FROM DUAL;
SELECT SEQUENCE2.CURRVAL FROM DUAL;

select * from airplanes;

select * from flights;
delete from airplanes where id=1;

-- select * from countries where name like 'Italy';
-- select count(*) from CITIES where id > 965;
-- select * from CITIES where country_id = 179;
-- select * from CITIES where name like '%New York%';
-- delete from countries where id in (1, 2, 3, 4, 5, 6, 7);
-- drop table countries cascade constraints;
-- drop table CITIES cascade constraints;
-- alter table countries drop column name;
-- alter table countries add name varchar2(255);
-- alter table cities drop column name;
-- alter table cities add name varchar2(255);
-- select * from CITIES;
-- select count(*) from CITIES;
-- delete from CITIES where id > 965;
-- select * from CITIES order by id asc;
-- select * from PILOT;
select count(*) from pilot;
select count(*) from copilot;
select count(*) from flight_engineer;
select count(*) from flight_attendant;
select * from flight_attendant;
delete from flight_attendant where id > = 1;
select * from employees where id = 305;

ALTER TABLE flights
ADD pilot_id int;




