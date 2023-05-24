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
dbms_output.put_line('eeee');
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

select * from employees;