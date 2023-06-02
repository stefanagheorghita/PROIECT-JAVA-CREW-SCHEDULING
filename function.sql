CREATE OR REPLACE FUNCTION load_employees_from_csv RETURN NUMBER IS
  v_fisier UTL_FILE.FILE_TYPE;
  l_line VARCHAR2(4000);
  l_id NUMBER;
  l_first_name VARCHAR2(50);
  l_last_name VARCHAR2(50);
  l_birthdate DATE;
  l_gender VARCHAR2(1);
  l_crew_id NUMBER;
  l_created_at DATE;
  l_updated_at DATE;
BEGIN
  v_fisier := UTL_FILE.FOPEN('MYDIR', 'employees.csv', 'R');
      UTL_FILE.GET_LINE(v_fisier, l_line);
  LOOP
    BEGIN
      UTL_FILE.GET_LINE(v_fisier, l_line);

      -- Check for the end of file
      IF l_line IS NULL THEN
        EXIT;
      END IF;

      l_id := TO_NUMBER(REGEXP_SUBSTR(l_line, '[^,]+', 1, 1));
      l_first_name := REGEXP_SUBSTR(l_line, '[^,]+', 1, 2);
      l_last_name := REGEXP_SUBSTR(l_line, '[^,]+', 1, 3);
      l_birthdate := TO_DATE(REGEXP_SUBSTR(l_line, '[^,]+', 1, 4), 'YYYY-MM-DD');
      l_gender := REGEXP_SUBSTR(l_line, '[^,]+', 1, 5);
      l_crew_id := TO_NUMBER(REGEXP_SUBSTR(l_line, '[^,]+', 1, 6));
      l_created_at := SYSDATE;
      l_updated_at := SYSDATE;

      INSERT INTO employees (id, first_name, last_name, birthdate, gender, crew_id, created_at, updated_at)
      VALUES (l_id, l_first_name, l_last_name, l_birthdate, l_gender, l_crew_id, l_created_at, l_updated_at);

      COMMIT;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        EXIT;
    END;
  END LOOP;
  UTL_FILE.FCLOSE(v_fisier);
  RETURN SQL%ROWCOUNT;
EXCEPTION
  WHEN OTHERS THEN
    UTL_FILE.FCLOSE(v_fisier);
    RAISE;
END load_employees_from_csv;

/

select * from employees;

DECLARE
    v_nr integer;
BEGIN
    v_nr:=load_employees_from_csv();
    dbms_output.put_line('AM adaugat ' || v_nr);
end;

/



DECLARE
  l_created_at DATE;
  l_updated_at DATE;
BEGIN
  l_created_at := TRUNC(SYSDATE) - INTERVAL '10' YEAR;
  l_updated_at := l_created_at;

  FOR emp IN (SELECT id, birthdate, created_at, updated_at FROM employees) LOOP
      l_created_at := TRUNC(SYSDATE) - INTERVAL '10' YEAR;
     l_created_at := l_created_at + DBMS_RANDOM.VALUE(0, SYSDATE - l_created_at);
    DECLARE
      l_age_diff NUMBER;
    BEGIN
      SELECT EXTRACT(YEAR FROM l_created_at) - EXTRACT(YEAR FROM emp.birthdate)
      INTO l_age_diff
      FROM DUAL;

      IF l_age_diff >= 18 THEN
        l_updated_at := l_created_at;
        dbms_output.put_line(emp.id || l_created_at);
      ELSE
        l_created_at :=emp.CREATED_AT;
        l_updated_at := emp.created_at;
      END IF;
    END;

    UPDATE employees
    SET created_at = l_created_at,
        updated_at = l_updated_at
    WHERE id = emp.id;
  END LOOP;

  COMMIT;
END;
/



CREATE OR REPLACE FUNCTION get_employees_by_occupation(occupation_id IN NUMBER)
  RETURN SYS_REFCURSOR
IS
  v_result SYS_REFCURSOR;
BEGIN
  OPEN v_result FOR
    SELECT e.*
    FROM employees e
    WHERE e.crew_id IN (
      SELECT id
      FROM crew
      WHERE occupation_id = occupation_id
    );

  RETURN v_result;
END;
/



DECLARE
  TYPE occupation_ids_t IS VARRAY(10) OF NUMBER;
  v_occupation_ids occupation_ids_t;
  v_employees SYS_REFCURSOR;
  v_employee employees%ROWTYPE;
 v_occupation_name varchar2(100);
BEGIN
  SELECT id
  BULK COLLECT INTO v_occupation_ids
  FROM crew;

  FOR i IN 1..v_occupation_ids.COUNT LOOP
        select name into v_occupation_name from crew where id=v_occupation_ids(i);
        dbms_output.put_line(v_occupation_name ||':');
        v_employees := get_employees_by_occupation(v_occupation_ids(i));
        LOOP
            FETCH v_employees INTO v_employee;
            DBMS_OUTPUT.PUT_LINE(v_employee.FIRST_NAME|| ' '||v_employee.LAST_NAME);
            EXIT WHEN v_employees%NOTFOUND;
         END LOOP;
        CLOSE v_employees;
        DBMS_OUTPUT.NEW_LINE();
        DBMS_OUTPUT.NEW_LINE();
  END LOOP;
END;
/

CREATE OR REPLACE PROCEDURE InsertPilotFromEmployees
IS
BEGIN

  INSERT INTO Pilot (id, employeeId)
  SELECT rownum, id
  FROM employees
  WHERE crew_id = 1;


  COMMIT;


  DBMS_OUTPUT.PUT_LINE('Insertion into Pilot table completed successfully.');
EXCEPTION
  WHEN OTHERS THEN

    DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
/

BEGIN
  InsertPilotFromEmployees;
END;

/

CREATE OR REPLACE PROCEDURE InsertCopilotFromEmployees
IS
BEGIN

  INSERT INTO Copilot (id, employeeId)
  SELECT rownum, id
  FROM employees
  WHERE crew_id = 2;


  COMMIT;


  DBMS_OUTPUT.PUT_LINE('Insertion into Copilot table completed successfully.');
EXCEPTION
  WHEN OTHERS THEN

    DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
/

BEGIN
  InsertCopilotFromEmployees;
END;

/

CREATE OR REPLACE PROCEDURE InsertEngineerFromEmployees
IS
BEGIN

  INSERT INTO flight_engineer (id, employeeId)
  SELECT rownum, id
  FROM employees
  WHERE crew_id = 3;


  COMMIT;


  DBMS_OUTPUT.PUT_LINE('Insertion into Flight Engineer table completed successfully.');
EXCEPTION
  WHEN OTHERS THEN

    DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
/

BEGIN
  InsertEngineerFromEmployees;
END;

/

CREATE OR REPLACE PROCEDURE InsertAttendantFromEmployees
IS
BEGIN

  INSERT INTO flight_attendant (id, employeeId)
  SELECT rownum, id
  FROM employees
  WHERE crew_id = 4;


  COMMIT;


  DBMS_OUTPUT.PUT_LINE('Insertion into Flight Attendant table completed successfully.');
EXCEPTION
  WHEN OTHERS THEN

    DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
/

BEGIN
  InsertAttendantFromEmployees;
END;

/


insert into airplanes values (1,200);
insert into airplanes values (2,100);
insert into airplanes values (3,150);

