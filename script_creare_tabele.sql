
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

CREATE TYPE crew_assignment_type AS OBJECT (
    crew_role INT,
    employee_id INT
);
/
CREATE TYPE crew_assignment_table_type AS TABLE OF crew_assignment_type;

/
CREATE TABLE flight_assignments (
    id INT NOT NULL PRIMARY KEY,
    flight_id INT NOT NULL,
    crew_assignments crew_assignment_table_type,
    CONSTRAINT fk_flight_assig_flight_id FOREIGN KEY (flight_id) REFERENCES flights(id)
) NESTED TABLE crew_assignments STORE AS lista;
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
set serveroutput on;
declare
v_fisier UTL_FILE.FILE_TYPE;
begin
    v_fisier:=UTL_FILE.FOPEN('MYDIR','note.csv','W');

for v_linie in (select id, id_student, id_curs, valoare, data_notare, created_at, updated_at from note)
    loop
        UTL_FILE.PUT_LINE(v_fisier, v_linie.id||','||v_linie.id_student||','||v_linie.id_curs||','||v_linie.valoare||','||v_linie.data_notare||','||v_linie.created_at||','||v_linie.updated_at);
end loop;

    UTL_FILE.FCLOSE(v_fisier);
    DBMS_OUTPUT.PUT_LINE('Export completat');
end;
/

CREATE TABLE nnote (
                       id INT NOT NULL PRIMARY KEY,
                       id_student INT NOT NULL,
                       id_curs INT NOT NULL,
                       valoare NUMBER(2),
                       data_notare DATE,
                       created_at DATE,
                       updated_at DATE
);
/
delete from nnote;
/
set serveroutput on;
declare
v_file  UTL_FILE.FILE_TYPE;
    v_line varchar2(100);
    v_id  note.id%type;
    v_id_student note.id_student%type;
    v_id_curs  varchar2(45);
    v_valoare  note.valoare%type;
    v_data_notare date;
    v_created_at date;
    v_updated_at date;
    v_pos1 number;
    v_pos2 number;
    v_pos3 number;
    v_pos4 number;
    v_pos5 number;
    v_pos6 number;
begin
   v_file := UTL_FILE.FOPEN('MYDIR', 'note.csv', 'R');
   loop
begin
         UTL_FILE.GET_LINE(v_file, v_line);
        v_pos1 := instr(v_line, ',', 1, 1);
        v_pos2 := instr(v_line, ',', 1, 2);
        v_pos3 := instr(v_line, ',', 1, 3);
        v_pos4 := instr(v_line, ',', 1, 4);
        v_pos5 := instr(v_line, ',', 1, 5);
        v_pos6 := instr(v_line, ',', 1, 6);
        v_id := to_number(substr(v_line,1,v_pos1-1));
        v_id_student := to_number(substr(v_line, v_pos1+ 1,v_pos2-v_pos1-1));
        v_id_curs := substr(v_line, v_pos2+ 1,v_pos3-v_pos2-1);
        v_valoare := to_number(substr(v_line, v_pos3+ 1,v_pos4-v_pos3-1));
        v_data_notare := to_date(substr(v_line,  v_pos4 + 1,v_pos5-v_pos4-1), 'DD-MON-YYYY');
        v_created_at := to_date(substr(v_line,  v_pos5 + 1,v_pos6-v_pos5-1), 'DD-MON-YYYY');
        v_updated_at := to_date(substr(v_line,  v_pos6+1), 'DD-MON-YYYY');
insert into nnote values(v_id, v_id_student, v_id_curs, v_valoare, v_data_notare, v_created_at, v_updated_at);
exception
         when NO_DATA_FOUND then
            exit;
end;
end loop;
   UTL_FILE.FCLOSE(v_file);
   DBMS_OUTPUT.PUT_LINE('Succes la import!');
exception
   when others then
      DBMS_OUTPUT.PUT_LINE('Esec la import din cauza: ' || SQLERRM);
end;


select * from users;


CREATE SEQUENCE SEQUENCE1 START WITH 1 INCREMENT BY 1;