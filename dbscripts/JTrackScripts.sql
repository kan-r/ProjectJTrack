--Script for Oracle DB

--CREATE USER JTRACK IDENTIFIED BY JTRACK;
--GRANT CONNECT, RESOURCE, DBA TO JTRACK;

CREATE TABLE JOB_TYPE(
  JOB_TYPE        	VARCHAR2(20),
  JOB_TYPE_DESC		VARCHAR2(250),
  ACTIVE          	NUMBER(1),
  DATE_CRT	      	DATE DEFAULT SYSDATE,
  USER_CRT	      	VARCHAR2(20),
  DATE_MOD			DATE,
  USER_MOD			VARCHAR2(20)
) TABLESPACE USERS;

ALTER TABLE JOB_TYPE ADD (
  CONSTRAINT JOB_TYPE_PK
  PRIMARY KEY (JOB_TYPE) USING INDEX TABLESPACE USERS);
  
  
CREATE TABLE JOB_STATUS(
  JOB_STATUS        VARCHAR2(20),
  JOB_STATUS_DESC	VARCHAR2(250),
  ACTIVE          	NUMBER(1),
  DATE_CRT	      	DATE DEFAULT SYSDATE,
  USER_CRT	      	VARCHAR2(20),
  DATE_MOD			DATE,
  USER_MOD			VARCHAR2(20)
) TABLESPACE USERS;

ALTER TABLE JOB_STATUS ADD (
  CONSTRAINT JOB_STATUS_PK
  PRIMARY KEY (JOB_STATUS) USING INDEX TABLESPACE USERS);
  
CREATE TABLE JOB_PRIORITY(
  JOB_PRIORITY      VARCHAR2(10),
  JOB_PRIORITY_DESC	VARCHAR2(250),
  ACTIVE          	NUMBER(1),
  DATE_CRT	      	DATE DEFAULT SYSDATE,
  USER_CRT	      	VARCHAR2(20),
  DATE_MOD			DATE,
  USER_MOD			VARCHAR2(20)
) TABLESPACE USERS;

ALTER TABLE JOB_PRIORITY ADD (
  CONSTRAINT JOB_PRIORITY_PK
  PRIMARY KEY (JOB_PRIORITY) USING INDEX TABLESPACE USERS);
  
CREATE TABLE JOB_RESOLUTION(
  JOB_RESOLUTION      	VARCHAR2(20),
  JOB_RESOLUTION_DESC 	VARCHAR2(250),
  ACTIVE          	NUMBER(1),
  DATE_CRT	      	DATE DEFAULT SYSDATE,
  USER_CRT	      	VARCHAR2(20),
  DATE_MOD			DATE,
  USER_MOD			VARCHAR2(20)
) TABLESPACE USERS;

ALTER TABLE JOB_RESOLUTION ADD (
  CONSTRAINT JOB_RESOLUTION_PK
  PRIMARY KEY (JOB_RESOLUTION) USING INDEX TABLESPACE USERS);
  
CREATE TABLE JOB_STAGE(
  JOB_STAGE         VARCHAR2(20),
  JOB_STAGE_DESC	VARCHAR2(250),
  ACTIVE          	NUMBER(1),
  DATE_CRT	      	DATE DEFAULT SYSDATE,
  USER_CRT	      	VARCHAR2(20),
  DATE_MOD			DATE,
  USER_MOD			VARCHAR2(20)
) TABLESPACE USERS;


ALTER TABLE JOB_STAGE ADD (
  CONSTRAINT JOB_STAGE_PK
  PRIMARY KEY (JOB_STAGE) USING INDEX TABLESPACE USERS);
  
  
CREATE TABLE TIMESHEET_CODE(
  TIMESHEET_CODE        VARCHAR2(20),
  TIMESHEET_CODE_DESC	VARCHAR2(250),
  ACTIVE          	NUMBER(1),
  DATE_CRT			DATE DEFAULT SYSDATE,
  USER_CRT	      	VARCHAR2(20),
  DATE_MOD			DATE,
  USER_MOD			VARCHAR2(20)
) TABLESPACE USERS;


ALTER TABLE TIMESHEET_CODE ADD (
  CONSTRAINT TIMESHEET_CODE_PK
  PRIMARY KEY (TIMESHEET_CODE) USING INDEX TABLESPACE USERS);
  
  
CREATE TABLE USERS(
  USER_ID         	VARCHAR2(20),
  FIRST_NAME		VARCHAR2(50),
  LAST_NAME       	VARCHAR2(50),
  ACTIVE          	NUMBER(1),
  DATE_CRT	      	DATE DEFAULT SYSDATE,
  USER_CRT	      	VARCHAR2(20),
  DATE_MOD			DATE,
  USER_MOD			VARCHAR2(20)
) TABLESPACE USERS;


ALTER TABLE USERS ADD (
  CONSTRAINT USERS_PK
  PRIMARY KEY (USER_ID) USING INDEX TABLESPACE USERS);
  

CREATE TABLE JOBS(
  JOB_NO          		NUMBER,
  JOB_NAME		  		VARCHAR2(100),
  JOB_DESC		  		VARCHAR2(4000),
  JOB_TYPE        		VARCHAR2(20),
  JOB_PRIORITY        	VARCHAR2(20),
  JOB_STATUS        	VARCHAR2(20),
  JOB_RESOLUTION        VARCHAR2(20),
  JOB_STAGE        		VARCHAR2(20),
  JOB_ORDER				NUMBER,
  ASSIGNED_TO      		VARCHAR2(20),
  TIMESHEET_CODE  		VARCHAR2(20),
  ESTIMATED_HRS   		NUMBER,
  COMPLETED_HRS 		NUMBER,
  ESTIMATED_START_DATE 	DATE,
  ACTUAL_START_DATE 	DATE,
  ESTIMATED_END_DATE 	DATE,
  ACTUAL_END_DATE 		DATE,
  PARENT_JOB_NO 		NUMBER,
  ACTIVE          		NUMBER(1),
  DATE_CRT	      		DATE DEFAULT SYSDATE,
  USER_CRT	      		VARCHAR2(20),
  DATE_MOD				DATE,
  USER_MOD				VARCHAR2(20)
) TABLESPACE USERS;

ALTER TABLE JOBS ADD JOB_REF VARCHAR2(100);

ALTER TABLE JOBS ADD (
  CONSTRAINT JOBS_PK
  PRIMARY KEY (JOB_NO) USING INDEX TABLESPACE USERS);
  
CREATE SEQUENCE JOB_NO_SEQ START WITH 1 NOCACHE;
  
  
CREATE TABLE TIMESHEET(
  TIMESHEET_ID		VARCHAR2(100),
  USER_ID          	VARCHAR2(20),
  JOB_NO          	NUMBER,
  WORKED_DATE		DATE,
  WORKED_HRS     	NUMBER,
  TIMESHEET_CODE	VARCHAR2(250),
  ACTIVE          	NUMBER(1),
  DATE_CRT	      	DATE DEFAULT SYSDATE,
  USER_CRT	      	VARCHAR2(20),
  DATE_MOD			DATE,
  USER_MOD			VARCHAR2(20)
) TABLESPACE USERS;

ALTER TABLE TIMESHEET ADD (
  CONSTRAINT TIMESHEET_PK
  PRIMARY KEY (TIMESHEET_ID) USING INDEX TABLESPACE USERS);
  
ALTER TABLE TIMESHEET ADD (
  CONSTRAINT TIMESHEET_USER_ID_FK
  FOREIGN KEY (USER_ID) REFERENCES USERS (USER_ID));
  
ALTER TABLE TIMESHEET ADD (
  CONSTRAINT TIMESHEET_JOB_NO_FK
  FOREIGN KEY (JOB_NO) REFERENCES JOBS (JOB_NO));
  
CREATE UNIQUE INDEX TIMESHEET_UIDX ON TIMESHEET (USER_ID, JOB_NO, WORKED_DATE) TABLESPACE USERS;


create or replace
PACKAGE DB_UTILS AS

  g_USER VARCHAR2(30);
  
  
  PROCEDURE UPDATE_JOB_TYPE(  
                p_JOB_TYPE      IN VARCHAR2,
                p_JOB_TYPE_DESC IN VARCHAR2,
                p_ACTIVE        IN NUMBER);
                        
  PROCEDURE DELETE_JOB_TYPE(  
                p_JOB_TYPE  IN VARCHAR2);
  
  
  PROCEDURE UPDATE_JOB_STATUS(  
                p_JOB_STATUS      IN VARCHAR2,
                p_JOB_STATUS_DESC IN VARCHAR2,
                p_ACTIVE          IN NUMBER);
                        
  PROCEDURE DELETE_JOB_STATUS(  
                p_JOB_STATUS  IN VARCHAR2);
  
  
  PROCEDURE UPDATE_JOB_PRIORITY(  
                p_JOB_PRIORITY      IN VARCHAR2,
                p_JOB_PRIORITY_DESC IN VARCHAR2,
                p_ACTIVE            IN NUMBER);
                        
  PROCEDURE DELETE_JOB_PRIORITY(  
                p_JOB_PRIORITY  IN VARCHAR2);
  
  
  PROCEDURE UPDATE_JOB_RESOLUTION(  
                p_JOB_RESOLUTION      IN VARCHAR2,
                p_JOB_RESOLUTION_DESC IN VARCHAR2,
                p_ACTIVE              IN NUMBER);
                        
  PROCEDURE DELETE_JOB_RESOLUTION(  
                p_JOB_RESOLUTION  IN VARCHAR2);
  
  
  PROCEDURE UPDATE_JOB_STAGE(   
                p_JOB_STAGE      IN VARCHAR2,
                p_JOB_STAGE_DESC IN VARCHAR2,
                p_ACTIVE         IN NUMBER);
                        
  PROCEDURE DELETE_JOB_STAGE(  
                p_JOB_STAGE  IN VARCHAR2);
  
  
  PROCEDURE UPDATE_TIMESHEET_CODE(  
                p_TIMESHEET_CODE      IN VARCHAR2,
                p_TIMESHEET_CODE_DESC IN VARCHAR2,
                p_ACTIVE              IN NUMBER);
                        
  PROCEDURE DELETE_TIMESHEET_CODE(  
                p_TIMESHEET_CODE  IN VARCHAR2);
  
  
  PROCEDURE UPDATE_USERS( 
                p_USER_ID     IN VARCHAR2,
                p_FIRST_NAME  IN VARCHAR2,
                p_LAST_NAME   IN VARCHAR2,
                p_ACTIVE      IN NUMBER);
                        
  PROCEDURE DELETE_USERS(  
                p_USER_ID IN VARCHAR2);
                
  FUNCTION IS_VALID_USER(              
                p_USERNAME    IN VARCHAR2,
                p_PASSWORD    IN VARCHAR2) RETURN BOOLEAN;
  
  PROCEDURE UPDATE_JOBS(  
                p_JOB_NO IN NUMBER,
                p_JOB_NAME IN VARCHAR2,
                p_JOB_DESC IN VARCHAR2,
                p_JOB_REF IN VARCHAR2,
                p_JOB_TYPE IN VARCHAR2,
                p_JOB_PRIORITY IN VARCHAR2,
                p_JOB_STATUS IN VARCHAR2,
                p_JOB_RESOLUTION IN VARCHAR2,
                p_JOB_STAGE IN VARCHAR2,
                p_JOB_ORDER IN NUMBER,
                p_ASSIGNED_TO IN VARCHAR2,
                p_TIMESHEET_CODE IN VARCHAR2,
                p_ESTIMATED_HRS IN NUMBER,
                p_COMPLETED_HRS IN NUMBER,
                p_ESTIMATED_START_DATE IN DATE,
                p_ACTUAL_START_DATE IN DATE,
                p_ESTIMATED_END_DATE IN DATE,
                p_ACTUAL_END_DATE IN DATE,
                p_PARENT_JOB_NO IN NUMBER,
                p_ACTIVE IN NUMBER);
                        
  PROCEDURE DELETE_JOBS(  
                p_JOB_NO IN NUMBER);
  
  
  PROCEDURE UPDATE_TIMESHEET(
                p_TIMESHEET_ID IN VARCHAR2,
                p_USER_ID IN VARCHAR2,
                p_JOB_NO IN NUMBER,
                p_WORKED_DATE IN DATE,
                p_WORKED_HRS IN NUMBER,
                p_TIMESHEET_CODE IN VARCHAR2,
                p_ACTIVE IN NUMBER);
                        
  PROCEDURE DELETE_TIMESHEET( 
                p_TIMESHEET_ID IN VARCHAR2);
                
  FUNCTION GET_TIMESHEET_ID(
                p_USER_ID IN VARCHAR2,
                P_JOB_NO IN NUMBER,
                p_WORKED_DATE IN DATE DEFAULT TRUNC(SYSDATE)) RETURN VARCHAR2;
              
  PROCEDURE DISPLAY_MONTHLY_REPORT(
                p_USER_ID IN VARCHAR2,
                p_DATE_IN_A_MONTH IN DATE);

END DB_UTILS;
/

create or replace
PACKAGE BODY DB_UTILS AS

  PROCEDURE UPDATE_JOB_TYPE(  
                p_JOB_TYPE      IN VARCHAR2,
                p_JOB_TYPE_DESC IN VARCHAR2,
                p_ACTIVE        IN NUMBER) IS
                
  BEGIN
    UPDATE JOB_TYPE
    SET JOB_TYPE_DESC = p_JOB_TYPE_DESC,
        ACTIVE = p_ACTIVE,
        DATE_MOD = SYSDATE,
        USER_MOD = g_User
    WHERE JOB_TYPE = p_JOB_TYPE;
    
    IF SQL%NOTFOUND THEN
      INSERT INTO JOB_TYPE(
            JOB_TYPE, 
            JOB_TYPE_DESC, 
            ACTIVE, 
            DATE_CRT, 
            USER_CRT)
      VALUES(
            p_JOB_TYPE, 
            p_JOB_TYPE_DESC, 
            p_ACTIVE, 
            SYSDATE, 
            g_USER);
    END IF;
  END;
  
  PROCEDURE DELETE_JOB_TYPE(  
                p_JOB_TYPE  IN VARCHAR2) IS
  
  BEGIN
    DELETE JOB_TYPE 
    WHERE JOB_TYPE = p_JOB_TYPE;
  END;
  
  
  PROCEDURE UPDATE_JOB_STATUS(  
                p_JOB_STATUS      IN VARCHAR2,
                p_JOB_STATUS_DESC IN VARCHAR2,
                p_ACTIVE          IN NUMBER) IS
                                
  BEGIN
    UPDATE JOB_STATUS
    SET JOB_STATUS_DESC = p_JOB_STATUS_DESC,
        ACTIVE = p_ACTIVE,
        DATE_MOD = SYSDATE,
        USER_MOD = g_User
    WHERE JOB_STATUS = p_JOB_STATUS;
    
    IF SQL%NOTFOUND THEN
      INSERT INTO JOB_STATUS(
            JOB_STATUS, 
            JOB_STATUS_DESC, 
            ACTIVE, 
            DATE_CRT, 
            USER_CRT)
      VALUES(
            p_JOB_STATUS, 
            p_JOB_STATUS_DESC, 
            p_ACTIVE, 
            SYSDATE, 
            g_USER);
    END IF;  
  END;
  
  PROCEDURE DELETE_JOB_STATUS(  
                p_JOB_STATUS  IN VARCHAR2) IS
  
  BEGIN
    DELETE JOB_STATUS 
    WHERE JOB_STATUS = p_JOB_STATUS;
  END;
  
  
  PROCEDURE UPDATE_JOB_PRIORITY(  
                p_JOB_PRIORITY      IN VARCHAR2,
                p_JOB_PRIORITY_DESC IN VARCHAR2,
                p_ACTIVE            IN NUMBER) IS
                                
  BEGIN
    UPDATE JOB_PRIORITY
    SET JOB_PRIORITY_DESC = p_JOB_PRIORITY_DESC,
        ACTIVE = p_ACTIVE,
        DATE_MOD = SYSDATE,
        USER_MOD = g_User
    WHERE JOB_PRIORITY = p_JOB_PRIORITY;
    
    IF SQL%NOTFOUND THEN
      INSERT INTO JOB_PRIORITY(
            JOB_PRIORITY, 
            JOB_PRIORITY_DESC, 
            ACTIVE, 
            DATE_CRT, 
            USER_CRT)
      VALUES(
            p_JOB_PRIORITY, 
            p_JOB_PRIORITY_DESC, 
            p_ACTIVE, 
            SYSDATE, 
            g_USER);
    END IF;  
  END;
  
  PROCEDURE DELETE_JOB_PRIORITY(  
                p_JOB_PRIORITY  IN VARCHAR2) IS
  
  BEGIN
    DELETE JOB_PRIORITY 
    WHERE JOB_PRIORITY = p_JOB_PRIORITY;
  END;
  
  
  PROCEDURE UPDATE_JOB_RESOLUTION(  
                p_JOB_RESOLUTION      IN VARCHAR2,
                p_JOB_RESOLUTION_DESC IN VARCHAR2,
                p_ACTIVE              IN NUMBER) IS
                                
  BEGIN
    UPDATE JOB_RESOLUTION
    SET JOB_RESOLUTION_DESC = p_JOB_RESOLUTION_DESC,
        ACTIVE = p_ACTIVE,
        DATE_MOD = SYSDATE,
        USER_MOD = g_User
    WHERE JOB_RESOLUTION = p_JOB_RESOLUTION;
    
    IF SQL%NOTFOUND THEN
      INSERT INTO JOB_RESOLUTION(
            JOB_RESOLUTION, 
            JOB_RESOLUTION_DESC, 
            ACTIVE, 
            DATE_CRT, 
            USER_CRT)
      VALUES(
            p_JOB_RESOLUTION, 
            p_JOB_RESOLUTION_DESC, 
            p_ACTIVE, 
            SYSDATE, 
            g_USER);
    END IF;  
  END;
  
  PROCEDURE DELETE_JOB_RESOLUTION(  
                p_JOB_RESOLUTION  IN VARCHAR2) IS
  
  BEGIN
    DELETE JOB_RESOLUTION 
    WHERE JOB_RESOLUTION = p_JOB_RESOLUTION;
  END;
  
  
  PROCEDURE UPDATE_JOB_STAGE(   
                p_JOB_STAGE      IN VARCHAR2,
                p_JOB_STAGE_DESC IN VARCHAR2,
                p_ACTIVE         IN NUMBER) IS
                                
  BEGIN
    UPDATE JOB_STAGE
    SET JOB_STAGE_DESC = p_JOB_STAGE_DESC,
        ACTIVE = p_ACTIVE,
        DATE_MOD = SYSDATE,
        USER_MOD = g_User
    WHERE JOB_STAGE = p_JOB_STAGE;
    
    IF SQL%NOTFOUND THEN
      INSERT INTO JOB_STAGE(
            JOB_STAGE, 
            JOB_STAGE_DESC, 
            ACTIVE, 
            DATE_CRT, 
            USER_CRT)
      VALUES(
            p_JOB_STAGE, 
            p_JOB_STAGE_DESC, 
            p_ACTIVE, 
            SYSDATE, 
            g_USER);
    END IF;  
  END;
  
  PROCEDURE DELETE_JOB_STAGE(  
                p_JOB_STAGE  IN VARCHAR2) IS
  
  BEGIN
    DELETE JOB_STAGE 
    WHERE JOB_STAGE = p_JOB_STAGE;
  END;
  
  
  PROCEDURE UPDATE_TIMESHEET_CODE(  
                p_TIMESHEET_CODE      IN VARCHAR2,
                p_TIMESHEET_CODE_DESC IN VARCHAR2,
                p_ACTIVE              IN NUMBER) IS
                                
  BEGIN
    UPDATE TIMESHEET_CODE
    SET TIMESHEET_CODE_DESC = p_TIMESHEET_CODE_DESC,
        ACTIVE = p_ACTIVE,
        DATE_MOD = SYSDATE,
        USER_MOD = g_User
    WHERE TIMESHEET_CODE = p_TIMESHEET_CODE;
    
    IF SQL%NOTFOUND THEN
      INSERT INTO TIMESHEET_CODE(
            TIMESHEET_CODE, 
            TIMESHEET_CODE_DESC, 
            ACTIVE, 
            DATE_CRT, 
            USER_CRT)
      VALUES(
            p_TIMESHEET_CODE, 
            p_TIMESHEET_CODE_DESC, 
            p_ACTIVE, 
            SYSDATE, 
            g_USER);
    END IF;  
  END;
  
  PROCEDURE DELETE_TIMESHEET_CODE(  
                p_TIMESHEET_CODE  IN VARCHAR2) IS
  
  BEGIN
    DELETE TIMESHEET_CODE 
    WHERE TIMESHEET_CODE = p_TIMESHEET_CODE;
  END;
  
  
  PROCEDURE UPDATE_USERS( 
                p_USER_ID     IN VARCHAR2,
                p_FIRST_NAME  IN VARCHAR2,
                p_LAST_NAME   IN VARCHAR2,
                p_ACTIVE      IN NUMBER) IS
                                
  BEGIN
    UPDATE USERS
    SET FIRST_NAME = p_FIRST_NAME,
        LAST_NAME = p_LAST_NAME,
        ACTIVE = p_ACTIVE,
        DATE_MOD = SYSDATE,
        USER_MOD = g_User
    WHERE USER_ID = p_USER_ID;
    
    IF SQL%NOTFOUND THEN
      INSERT INTO USERS(
            USER_ID, 
            FIRST_NAME, 
            LAST_NAME, 
            ACTIVE, 
            DATE_CRT, 
            USER_CRT)
      VALUES(
            UPPER(p_USER_ID), 
            p_FIRST_NAME, 
            p_LAST_NAME, 
            p_ACTIVE, 
            SYSDATE, 
            g_USER);
    END IF;  
  END;
  
  PROCEDURE DELETE_USERS(  
                p_USER_ID IN VARCHAR2) IS
  
  BEGIN
    DELETE USERS 
    WHERE USER_ID = p_USER_ID;
  END;
  
  FUNCTION IS_VALID_USER(              
                p_USERNAME    IN VARCHAR2,
                p_PASSWORD    IN VARCHAR2) RETURN BOOLEAN IS
          
     l_Cnt NUMBER;
     
  BEGIN
    SELECT COUNT(*)
    INTO l_Cnt
    FROM USERS
    WHERE UPPER(USER_ID) = UPPER(p_USERNAME);
    
    IF l_Cnt > 0 THEN
      RETURN TRUE;
    ELSE
      RETURN FALSE;
    END IF;
  END;
  
  
  PROCEDURE REFRESH_PARENT_JOB(
                p_PARENT_JOB_NO IN NUMBER) IS
                
  BEGIN
    IF p_PARENT_JOB_NO IS NULL THEN
      RETURN;
    END IF;
    
    UPDATE JOBS
    SET (ESTIMATED_HRS, COMPLETED_HRS) = 
        (SELECT SUM(ESTIMATED_HRS), SUM(COMPLETED_HRS) 
         FROM JOBS 
         WHERE PARENT_JOB_NO = p_PARENT_JOB_NO AND
              ACTIVE = 1)
    WHERE JOB_NO = p_PARENT_JOB_NO;
    
    UPDATE JOBS
    SET JOB_STATUS = 'In Progress', 
        ACTUAL_START_DATE = 
            ( SELECT MIN(ACTUAL_START_DATE)
              FROM JOBS
              WHERE PARENT_JOB_NO = p_PARENT_JOB_NO AND
                ACTIVE = 1)
    WHERE JOB_NO = p_PARENT_JOB_NO AND
          JOB_STATUS IS NULL AND 
          ACTUAL_START_DATE IS NULL;
  END;
  
  
  PROCEDURE UPDATE_JOBS(  
                p_JOB_NO IN NUMBER,
                p_JOB_NAME IN VARCHAR2,
                p_JOB_DESC IN VARCHAR2,
                p_JOB_REF IN VARCHAR2,
                p_JOB_TYPE IN VARCHAR2,
                p_JOB_PRIORITY IN VARCHAR2,
                p_JOB_STATUS IN VARCHAR2,
                p_JOB_RESOLUTION IN VARCHAR2,
                p_JOB_STAGE IN VARCHAR2,
                p_JOB_ORDER IN NUMBER,
                p_ASSIGNED_TO IN VARCHAR2,
                p_TIMESHEET_CODE IN VARCHAR2,
                p_ESTIMATED_HRS IN NUMBER,
                p_COMPLETED_HRS IN NUMBER,
                p_ESTIMATED_START_DATE IN DATE,
                p_ACTUAL_START_DATE IN DATE,
                p_ESTIMATED_END_DATE IN DATE,
                p_ACTUAL_END_DATE IN DATE,
                p_PARENT_JOB_NO IN NUMBER,
                p_ACTIVE IN NUMBER) IS
                
    l_TIMESHEET_CODE JOBS.TIMESHEET_CODE%TYPE;
    l_ACTUAL_START_DATE DATE;
    l_ACTUAL_END_DATE DATE;
                          
  BEGIN
    l_TIMESHEET_CODE := p_TIMESHEET_CODE;
    
    IF p_TIMESHEET_CODE IS NULL AND p_PARENT_JOB_NO IS NOT NULL THEN
      BEGIN
        SELECT TIMESHEET_CODE
        INTO l_TIMESHEET_CODE
        FROM JOBS
        WHERE JOB_NO = p_PARENT_JOB_NO;
      EXCEPTION WHEN NO_DATA_FOUND THEN
        l_TIMESHEET_CODE := NULL;
      END;
    END IF;
    
    l_ACTUAL_START_DATE := p_ACTUAL_START_DATE;
    l_ACTUAL_END_DATE := p_ACTUAL_END_DATE;
    
    IF p_ACTUAL_END_DATE IS NULL AND p_JOB_STATUS = 'Completed' THEN
      l_ACTUAL_END_DATE := SYSDATE;
    END IF;
    
    IF p_ACTUAL_START_DATE IS NOT NULL AND p_ACTUAL_START_DATE > l_ACTUAL_END_DATE THEN
      l_ACTUAL_START_DATE := l_ACTUAL_END_DATE - NVL(p_COMPLETED_HRS,0)/24;
    END IF;
    
    IF NVL(p_JOB_NO,0) = 0 THEN
      INSERT INTO JOBS(
        JOB_NO,
        JOB_NAME,
        JOB_DESC,
        JOB_REF,
        JOB_TYPE,
        JOB_PRIORITY,
        JOB_STATUS,
        JOB_STAGE,
        JOB_ORDER,
        ASSIGNED_TO,
        TIMESHEET_CODE,
        ESTIMATED_HRS,
        ESTIMATED_START_DATE,
        ESTIMATED_END_DATE,
        PARENT_JOB_NO,
        ACTIVE,
        DATE_CRT,
        USER_CRT)
      VALUES(
        JOB_NO_SEQ.NEXTVAL,
        p_JOB_NAME,
        p_JOB_DESC,
        p_JOB_REF,
        p_JOB_TYPE,
        p_JOB_PRIORITY,
        p_JOB_STATUS,
        p_JOB_STAGE,
        p_JOB_ORDER,
        p_ASSIGNED_TO,
        l_TIMESHEET_CODE,
        p_ESTIMATED_HRS,
        p_ESTIMATED_START_DATE,
        p_ESTIMATED_END_DATE,
        p_PARENT_JOB_NO,
        p_ACTIVE,
        SYSDATE,
        g_USER);
    ELSE
      UPDATE JOBS SET
        JOB_NAME = p_JOB_NAME,
        JOB_DESC = p_JOB_DESC,
        JOB_REF = p_JOB_REF,
        JOB_TYPE = p_JOB_TYPE,
        JOB_PRIORITY = p_JOB_PRIORITY,
        JOB_STATUS = p_JOB_STATUS,
        JOB_RESOLUTION = p_JOB_RESOLUTION,
        JOB_STAGE = p_JOB_STAGE,
        JOB_ORDER = p_JOB_ORDER,
        ASSIGNED_TO = p_ASSIGNED_TO,
        TIMESHEET_CODE = l_TIMESHEET_CODE,
        ESTIMATED_HRS = p_ESTIMATED_HRS,
        COMPLETED_HRS = p_COMPLETED_HRS,
        ESTIMATED_START_DATE = p_ESTIMATED_START_DATE,
        ACTUAL_START_DATE = l_ACTUAL_START_DATE,
        ESTIMATED_END_DATE = p_ESTIMATED_END_DATE,
        ACTUAL_END_DATE = l_ACTUAL_END_DATE,
        PARENT_JOB_NO = p_PARENT_JOB_NO,
        ACTIVE = p_ACTIVE,
        DATE_MOD = SYSDATE,
        USER_MOD = g_USER
      WHERE JOB_NO = p_JOB_NO;
    END IF;
    
    REFRESH_PARENT_JOB(p_PARENT_JOB_NO);
  END;
                        
  PROCEDURE DELETE_JOBS(  
                p_JOB_NO IN NUMBER) IS
                
    v_PARENT_JOB_NO NUMBER;
    
  BEGIN
    SELECT PARENT_JOB_NO
    INTO v_PARENT_JOB_NO
    FROM JOBS
    WHERE JOB_NO = p_JOB_NO;
    
    DELETE JOBS
    WHERE JOB_NO = p_JOB_NO;
    
    REFRESH_PARENT_JOB(v_PARENT_JOB_NO);
  END;
  
  
  PROCEDURE REFRESH_JOB(
                p_JOB_NO IN NUMBER) IS
              
    v_PARENT_JOB_NO NUMBER;
    
  BEGIN
    -- update JOBS with completed hrs
    UPDATE JOBS 
    SET COMPLETED_HRS = 
        (SELECT SUM(WORKED_HRS) 
         FROM TIMESHEET 
         WHERE JOB_NO = p_JOB_NO AND 
               ACTIVE = 1)
    WHERE JOB_NO = p_JOB_NO;
    
    -- update JOBS with status and actual start date
    UPDATE JOBS
    SET JOB_STATUS = 'In Progress', 
        ACTUAL_START_DATE = 
          ( SELECT MIN(WORKED_DATE)
            FROM TIMESHEET
            WHERE JOB_NO = p_JOB_NO AND 
                  ACTIVE = 1)
    WHERE JOB_NO = p_JOB_NO AND
          JOB_STATUS IS NULL AND 
          ACTUAL_START_DATE IS NULL;
  
    SELECT PARENT_JOB_NO
    INTO v_PARENT_JOB_NO
    FROM JOBS
    WHERE JOB_NO = p_JOB_NO;
  
    REFRESH_PARENT_JOB(v_PARENT_JOB_NO);
  END;
  
  PROCEDURE UPDATE_TIMESHEET( 
                p_TIMESHEET_ID IN VARCHAR2,
                p_USER_ID IN VARCHAR2,
                p_JOB_NO IN NUMBER,
                p_WORKED_DATE IN DATE,
                p_WORKED_HRS IN NUMBER,
                p_TIMESHEET_CODE IN VARCHAR2,
                p_ACTIVE IN NUMBER) IS
                
    l_TIMESHEET_ID TIMESHEET.TIMESHEET_ID%TYPE;
                            
  BEGIN      
    IF p_TIMESHEET_ID IS NULL THEN
      l_TIMESHEET_ID := TO_CHAR(p_USER_ID) || '-' || TO_CHAR(p_JOB_NO) || '-' || TO_CHAR(p_WORKED_DATE, 'YYYYMMDD');
    
      INSERT INTO TIMESHEET(
        TIMESHEET_ID,
        USER_ID,
        JOB_NO,
        WORKED_DATE,
        WORKED_HRS,
        TIMESHEET_CODE,
        ACTIVE,
        DATE_CRT,
        USER_CRT)
      VALUES(
        l_TIMESHEET_ID,
        p_USER_ID,
        p_JOB_NO,
        p_WORKED_DATE,
        p_WORKED_HRS,
        p_TIMESHEET_CODE,
        p_ACTIVE,
        SYSDATE,
        g_USER);
    ELSE
      UPDATE TIMESHEET SET
        WORKED_HRS = p_WORKED_HRS,
        TIMESHEET_CODE = p_TIMESHEET_CODE,
        ACTIVE = p_ACTIVE,
        DATE_MOD = SYSDATE,
        USER_MOD = g_USER
      WHERE TIMESHEET_ID = p_TIMESHEET_ID;
    END IF;
    
    REFRESH_JOB(p_JOB_NO);
  END;
                        
  PROCEDURE DELETE_TIMESHEET( 
                p_TIMESHEET_ID IN VARCHAR2) IS
               
    v_JOB_NO NUMBER;
    
  BEGIN
    SELECT JOB_NO
    INTO v_JOB_NO
    FROM TIMESHEET
    WHERE TIMESHEET_ID = p_TIMESHEET_ID;
    
    DELETE TIMESHEET 
    WHERE TIMESHEET_ID = P_TIMESHEET_ID;
    
    REFRESH_JOB(v_JOB_NO);
  END;
  
  FUNCTION GET_TIMESHEET_ID(
                p_USER_ID IN VARCHAR2,
                p_JOB_NO IN NUMBER,
                p_WORKED_DATE IN DATE) RETURN VARCHAR2 IS
                
    l_TIMESHEET_ID TIMESHEET.TIMESHEET_ID%TYPE;
                
  BEGIN
    SELECT TIMESHEET_ID
    INTO l_TIMESHEET_ID
    FROM TIMESHEET
    WHERE USER_ID = p_USER_ID AND
          JOB_NO = p_JOB_NO AND
          WORKED_DATE = p_WORKED_DATE;
  
    RETURN l_TIMESHEET_ID;
    
  EXCEPTION
  WHEN NO_DATA_FOUND THEN
    RETURN NULL;
  END;
  
  
  PROCEDURE PRINT(p_Txt IN VARCHAR2, p_Debug IN BOOLEAN) IS
  
  BEGIN
  	IF p_Debug THEN
	   DBMS_OUTPUT.PUT_LINE(p_Txt);
	ELSE
	   HTP.P(p_Txt);
	END IF;
  END;
  
  
  PROCEDURE DISPLAY_MONTHLY_REPORT(
                p_USER_ID IN VARCHAR2,
                p_DATE_IN_A_MONTH IN DATE) IS
                
    bDebug 	BOOLEAN := FALSE;
    vTitle  VARCHAR2(100);
    nJobNo  NUMBER;
     
    CURSOR cUser IS
    SELECT FIRST_NAME, LAST_NAME
    FROM USERS
    WHERE USER_ID = p_USER_ID;
    
    CURSOR cProj IS
    SELECT *
    FROM JOBS
    WHERE JOB_TYPE = 'Project' AND
	  (NVL(JOB_STATUS, 'N') != 'Completed' OR (JOB_STATUS = 'Completed' AND TO_CHAR(ACTUAL_END_DATE, 'MM-YYYY') = TO_CHAR(p_DATE_IN_A_MONTH, 'MM-YYYY'))) AND
      ACTIVE = 1 AND
      ASSIGNED_TO = p_USER_ID;
      
    CURSOR cTaskC IS
    SELECT *
    FROM JOBS
    WHERE JOB_TYPE != 'Project' AND
      JOB_STATUS = 'Completed' AND
      PARENT_JOB_NO = nJobNo AND
      ACTIVE = 1 AND
      ASSIGNED_TO = p_USER_ID AND
      TO_CHAR(ACTUAL_END_DATE, 'MM-YYYY') = TO_CHAR(p_DATE_IN_A_MONTH, 'MM-YYYY')
    ORDER BY ACTUAL_END_DATE;
    
    CURSOR cTaskR IS
    SELECT *
    FROM JOBS
    WHERE JOB_TYPE != 'Project' AND
      NVL(JOB_STATUS, 'N') NOT IN ('Completed', 'Cancelled') AND
      PARENT_JOB_NO = nJobNo AND
      ACTIVE = 1 AND
      ASSIGNED_TO = p_USER_ID
    ORDER BY ACTUAL_START_DATE, ESTIMATED_START_DATE;
      
    CURSOR cOtherC IS
    SELECT *
    FROM JOBS
    WHERE JOB_TYPE NOT IN ('Project', 'Support') AND
      JOB_STATUS = 'Completed' AND
      PARENT_JOB_NO IS NULL AND
      ACTIVE = 1 AND
      ASSIGNED_TO = p_USER_ID AND
      TO_CHAR(ACTUAL_END_DATE, 'MM-YYYY') = TO_CHAR(p_DATE_IN_A_MONTH, 'MM-YYYY')
    ORDER BY ACTUAL_END_DATE;
    
    CURSOR cOtherR IS
    SELECT *
    FROM JOBS
    WHERE JOB_TYPE != 'Project' AND
      NVL(JOB_STATUS, 'N') NOT IN ('Completed', 'Cancelled') AND
      PARENT_JOB_NO IS NULL AND
      ACTIVE = 1 AND
      ASSIGNED_TO = p_USER_ID
    ORDER BY ACTUAL_START_DATE, ESTIMATED_START_DATE;
    
    CURSOR cSupport IS
    SELECT *
    FROM JOBS
    WHERE JOB_TYPE = 'Support' AND
      JOB_STATUS = 'Completed' AND
      PARENT_JOB_NO IS NULL AND
      ACTIVE = 1 AND
      ASSIGNED_TO = p_USER_ID AND
      TO_CHAR(ACTUAL_END_DATE, 'MM-YYYY') = TO_CHAR(p_DATE_IN_A_MONTH, 'MM-YYYY')
    ORDER BY ACTUAL_END_DATE;
    
    CURSOR cLeave IS
    SELECT J.JOB_NAME, SUM(WORKED_HRS)/8 "DAYS"
    FROM JOBS J, TIMESHEET T
    WHERE J.JOB_TYPE = 'Leave' AND
      J.JOB_NO = T.JOB_NO AND
      T.ACTIVE = 1 AND
      T.USER_ID = p_USER_ID AND
      TO_CHAR(T.WORKED_DATE, 'MM-YYYY') = TO_CHAR(p_DATE_IN_A_MONTH, 'MM-YYYY')
    GROUP BY J.JOB_NAME;

  BEGIN
    FOR rUser IN cUser LOOP
      vTitle := TO_CHAR(p_DATE_IN_A_MONTH, 'Month- YYYY') || ' Report for ' || rUser.FIRST_NAME || ' ' || rUser.LAST_NAME;
    END LOOP;
    
    -- Title
    PRINT('<table cellpadding="0" border="0" cellspacing="1" summary="">', bDebug);
    PRINT('<tr>', bDebug);
    PRINT('<td><h2>' || vTitle || '</h2></td>', bDebug);
    PRINT('</tr>', bDebug);
    PRINT('</table>', bDebug);
    
    -- Project
    FOR rProj IN cProj LOOP
      nJobNo := rProj.JOB_NO;
      
      PRINT('<table cellpadding="5" border="0" cellspacing="0" summary="">', bDebug);
      PRINT('<tr>', bDebug);
      PRINT('<th align="left">Project:</th>', bDebug);
      PRINT('<th>' || rProj.JOB_NAME || '</th>', bDebug);
      PRINT('</tr>', bDebug);
      PRINT('<tr>', bDebug);
      PRINT('<td>SOWD:</td>', bDebug);
      PRINT('<td>' || rProj.TIMESHEET_CODE || '</td>', bDebug);
      PRINT('</tr>', bDebug);
      PRINT('</table>', bDebug); 
      
      -- Tasks completed
      PRINT('<table cellpadding="10" border="0" cellspacing="0" summary="">', bDebug);
      PRINT('<tr>', bDebug);
      PRINT('<th  align="left">Tasks completed:</th>', bDebug);
      PRINT('</tr>', bDebug);
      --PRINT('</table>', bDebug);
      
      --PRINT('<table cellpadding="15" border="0" cellspacing="1" summary="">', bDebug);
      PRINT('<tr>', bDebug);
      PRINT('<td>', bDebug);
      PRINT('<ul>', bDebug);
      FOR rTaskC IN cTaskC LOOP
        PRINT('<li>' || rTaskC.JOB_NAME || '</li>', bDebug);
      END LOOP;
      PRINT('</ul>', bDebug);
      PRINT('</td>', bDebug);
      PRINT('</tr>', bDebug);
      PRINT('</table>', bDebug);
      
      -- Tasks remaining
      PRINT('<table cellpadding="10" border="0" cellspacing="1" summary="">', bDebug);
      PRINT('<tr>', bDebug);
      PRINT('<th align="left">Tasks remaining:</th>', bDebug);
      PRINT('</tr>', bDebug);
      --PRINT('</table>', bDebug);
      
      --PRINT('<table cellpadding="20" border="0" cellspacing="1" summary="">', bDebug);
      PRINT('<tr>', bDebug);
      PRINT('<td>', bDebug);
      PRINT('<ul>', bDebug);
      FOR rTaskR IN cTaskR LOOP
        PRINT('<li>' || rTaskR.JOB_NAME || '</li>', bDebug);
      END LOOP;
      PRINT('</ul>', bDebug);
      PRINT('</td>', bDebug);
      PRINT('</tr>', bDebug);
      PRINT('</table>', bDebug);
      
      PRINT('<table cellpadding="5" border="0" cellspacing="0" summary="">', bDebug);
      PRINT('<tr>', bDebug);
      PRINT('<td  align="left">------------------------------------------------------------------------------------------------------</td>', bDebug);
      PRINT('</tr>', bDebug);
      PRINT('</table>', bDebug);
    END LOOP;
    
    -- Other
    PRINT('<table cellpadding="5" border="0" cellspacing="1" summary="">', bDebug);
    PRINT('<tr>', bDebug);
    PRINT('<th align="left">Other:</th>', bDebug);
    PRINT('</tr>', bDebug);
    PRINT('</table>', bDebug);
      
    -- Tasks completed
    PRINT('<table cellpadding="10" border="0" cellspacing="1" summary="">', bDebug);
    PRINT('<tr>', bDebug);
    PRINT('<th  align="left">Tasks completed:</th>', bDebug);
    PRINT('</tr>', bDebug);
      
    PRINT('<tr>', bDebug);
    PRINT('<td>', bDebug);
    PRINT('<ul>', bDebug);
    FOR rOtherC IN cOtherC LOOP
      PRINT('<li>' || rOtherC.JOB_NAME || '</li>', bDebug);
    END LOOP;
    PRINT('</ul>', bDebug);
    PRINT('</td>', bDebug);
    PRINT('</tr>', bDebug);
    PRINT('</table>', bDebug);
    
    PRINT('<table cellpadding="5" border="0" cellspacing="0" summary="">', bDebug);
    PRINT('<tr>', bDebug);
    PRINT('<td  align="left">------------------------------------------------------------------------------------------------------</td>', bDebug);
    PRINT('</tr>', bDebug);
    PRINT('</table>', bDebug);
    
    -- Support Activities
    PRINT('<table cellpadding="5" border="0" cellspacing="1" summary="">', bDebug);
    PRINT('<tr>', bDebug);
    PRINT('<th align="left">Support Activities:</th>', bDebug);
    PRINT('</tr>', bDebug);
    PRINT('</table>', bDebug);
    
    FOR rSupport IN cSupport LOOP
      PRINT('<table cellpadding="10" border="0" cellspacing="1" summary="">', bDebug);
      PRINT('<tr>', bDebug);
      PRINT('<td>', bDebug);
      
      PRINT('<table cellpadding="2" border="0" cellspacing="1" summary="">', bDebug);
      PRINT('<tr>', bDebug);
      PRINT('<th  align="left">Task completed:</th>', bDebug);
      PRINT('<th>' || rSupport.JOB_NAME || '</th>', bDebug);
      PRINT('</tr>', bDebug);
      PRINT('<tr>', bDebug);
      PRINT('<td>Incident ID:</td>', bDebug);
      PRINT('<td>' || rSupport.JOB_REF || '</td>', bDebug);
      PRINT('</tr>', bDebug);
      PRINT('<tr>', bDebug);
      PRINT('<td>Effort:</td>', bDebug);
      PRINT('<td>' || rSupport.COMPLETED_HRS || ' hrs</td>', bDebug);
      PRINT('</tr>', bDebug);
      PRINT('<tr>', bDebug);
      PRINT('<td>Date Resolved:</td>', bDebug);
      PRINT('<td>' || TO_CHAR(rSupport.ACTUAL_END_DATE, 'DD/MM/YYYY') || '</td>', bDebug);
      PRINT('</tr>', bDebug);
      PRINT('</table>', bDebug);
      
      PRINT('</td>', bDebug);
      PRINT('</tr>', bDebug);
      PRINT('</table>', bDebug);
    END LOOP;
    
    PRINT('<table cellpadding="5" border="0" cellspacing="0" summary="">', bDebug);
    PRINT('<tr>', bDebug);
    PRINT('<td  align="left">------------------------------------------------------------------------------------------------------</td>', bDebug);
    PRINT('</tr>', bDebug);
    PRINT('</table>', bDebug);
    
    -- Leaves Taken
    PRINT('<table cellpadding="5" border="0" cellspacing="1" summary="">', bDebug);
    PRINT('<tr>', bDebug);
    PRINT('<th align="left">Leaves Taken During Month:</th>', bDebug);
    PRINT('</tr>', bDebug);
    
    PRINT('<tr>', bDebug);
    PRINT('<td>', bDebug);
    PRINT('<ul>', bDebug);
    FOR rLeave IN cLeave LOOP
      PRINT('<li>' || rLeave.DAYS || ' - ' || rLeave.JOB_NAME || '</li>', bDebug);
    END LOOP;
    PRINT('</ul>', bDebug);
    PRINT('</td>', bDebug);
    PRINT('</tr>', bDebug);
    PRINT('</table>', bDebug);
    
    PRINT('<table cellpadding="5" border="0" cellspacing="0" summary="">', bDebug);
    PRINT('<tr>', bDebug);
    PRINT('<td  align="left">------------------------------------------------------------------------------------------------------</td>', bDebug);
    PRINT('</tr>', bDebug);
    PRINT('</table>', bDebug);
  END;

BEGIN
  g_User := UPPER(v('APP_USER'));
END DB_UTILS;
/


INSERT INTO USERS(USER_ID, FIRST_NAME, LAST_NAME, ACTIVE, DATE_CRT)
VALUES('ADMIN', 'Admin', NULL, 1, SYSDATE);


INSERT INTO JOB_PRIORITY(JOB_PRIORITY, JOB_PRIORITY_DESC, ACTIVE, DATE_CRT, USER_CRT)
VALUES('High', 'High Priority', 1, SYSDATE, 'ADMIN');

INSERT INTO JOB_PRIORITY(JOB_PRIORITY, JOB_PRIORITY_DESC, ACTIVE, DATE_CRT, USER_CRT)
VALUES('Medium', 'Medium Priority', 1, SYSDATE, 'ADMIN');

INSERT INTO JOB_PRIORITY(JOB_PRIORITY, JOB_PRIORITY_DESC, ACTIVE, DATE_CRT, USER_CRT)
VALUES('Low', 'Low Priority', 1, SYSDATE, 'ADMIN');


INSERT INTO JOB_RESOLUTION(JOB_RESOLUTION, JOB_RESOLUTION_DESC, ACTIVE, DATE_CRT, USER_CRT)
VALUES('Done', 'Completed or fixed', 1, SYSDATE, 'ADMIN');

INSERT INTO JOB_RESOLUTION(JOB_RESOLUTION, JOB_RESOLUTION_DESC, ACTIVE, DATE_CRT, USER_CRT)
VALUES('Duplicate', 'Same as another job', 1, SYSDATE, 'ADMIN');

INSERT INTO JOB_RESOLUTION(JOB_RESOLUTION, JOB_RESOLUTION_DESC, ACTIVE, DATE_CRT, USER_CRT)
VALUES('Not Clear', 'Description is not clear', 1, SYSDATE, 'ADMIN');

INSERT INTO JOB_RESOLUTION(JOB_RESOLUTION, JOB_RESOLUTION_DESC, ACTIVE, DATE_CRT, USER_CRT)
VALUES('Not Replicated', 'Issue cannot be replicated', 1, SYSDATE, 'ADMIN');


INSERT INTO JOB_STAGE(JOB_STAGE, JOB_STAGE_DESC, ACTIVE, DATE_CRT, USER_CRT)
VALUES('Design', 'Requirements gathering and high level design', 1, SYSDATE, 'ADMIN');

INSERT INTO JOB_STAGE(JOB_STAGE, JOB_STAGE_DESC, ACTIVE, DATE_CRT, USER_CRT)
VALUES('Development', 'Detailed level design, coding and unit testing', 1, SYSDATE, 'ADMIN');

INSERT INTO JOB_STAGE(JOB_STAGE, JOB_STAGE_DESC, ACTIVE, DATE_CRT, USER_CRT)
VALUES('Testing', 'Integrated testing', 1, SYSDATE, 'ADMIN');

INSERT INTO JOB_STAGE(JOB_STAGE, JOB_STAGE_DESC, ACTIVE, DATE_CRT, USER_CRT)
VALUES('Deployment', 'Deploy to other environments (Test, Prod)', 1, SYSDATE, 'ADMIN');


INSERT INTO JOB_STATUS(JOB_STATUS, JOB_STATUS_DESC, ACTIVE, DATE_CRT, USER_CRT)
VALUES('Scheduled', 'Scheduled to be commenced', 1, SYSDATE, 'ADMIN');

INSERT INTO JOB_STATUS(JOB_STATUS, JOB_STATUS_DESC, ACTIVE, DATE_CRT, USER_CRT)
VALUES('In Progress', 'Work in progress', 1, SYSDATE, 'ADMIN');

INSERT INTO JOB_STATUS(JOB_STATUS, JOB_STATUS_DESC, ACTIVE, DATE_CRT, USER_CRT)
VALUES('On Hold', 'On hold for some reason', 1, SYSDATE, 'ADMIN');

INSERT INTO JOB_STATUS(JOB_STATUS, JOB_STATUS_DESC, ACTIVE, DATE_CRT, USER_CRT)
VALUES('Completed', 'Completed with some resolution', 1, SYSDATE, 'ADMIN');

INSERT INTO JOB_STATUS(JOB_STATUS, JOB_STATUS_DESC, ACTIVE, DATE_CRT, USER_CRT)
VALUES('Pending', 'Pending for someone or something', 1, SYSDATE, 'ADMIN');

INSERT INTO JOB_STATUS(JOB_STATUS, JOB_STATUS_DESC, ACTIVE, DATE_CRT, USER_CRT)
VALUES('Cancelled', 'Cancelled, not required any more', 1, SYSDATE, 'ADMIN');


INSERT INTO JOB_TYPE(JOB_TYPE, JOB_TYPE_DESC, ACTIVE, DATE_CRT, USER_CRT)
VALUES('Project', 'Job to group some tasks as a Project', 1, SYSDATE, 'ADMIN');

INSERT INTO JOB_TYPE(JOB_TYPE, JOB_TYPE_DESC, ACTIVE, DATE_CRT, USER_CRT)
VALUES('Task', 'Task is part of a project', 1, SYSDATE, 'ADMIN');

INSERT INTO JOB_TYPE(JOB_TYPE, JOB_TYPE_DESC, ACTIVE, DATE_CRT, USER_CRT)
VALUES('Bug', 'Standalone bug or issue, not part of a project', 1, SYSDATE, 'ADMIN');

INSERT INTO JOB_TYPE(JOB_TYPE, JOB_TYPE_DESC, ACTIVE, DATE_CRT, USER_CRT)
VALUES('Enhancement', 'Standalone enhancement, not part of a project', 1, SYSDATE, 'ADMIN');

INSERT INTO JOB_TYPE(JOB_TYPE, JOB_TYPE_DESC, ACTIVE, DATE_CRT, USER_CRT)
VALUES('Support', 'Investigating and issue or assisting someone, not part of a project', 1, SYSDATE, 'ADMIN');

INSERT INTO JOB_TYPE(JOB_TYPE, JOB_TYPE_DESC, ACTIVE, DATE_CRT, USER_CRT)
VALUES('Meeting', 'Meeting, not part of any job', 1, SYSDATE, 'ADMIN');

INSERT INTO JOB_TYPE(JOB_TYPE, JOB_TYPE_DESC, ACTIVE, DATE_CRT, USER_CRT)
VALUES('Leave', 'Annaul, Sick leaves', 1, SYSDATE, 'ADMIN');

INSERT INTO JOB_TYPE(JOB_TYPE, JOB_TYPE_DESC, ACTIVE, DATE_CRT, USER_CRT)
VALUES('Adhoc', 'Ad hoc job', 1, SYSDATE, 'ADMIN');


INSERT INTO TIMESHEET_CODE(TIMESHEET_CODE, TIMESHEET_CODE_DESC, ACTIVE, DATE_CRT, USER_CRT)
VALUES('ZZ01', 'Reviews/Meetings', 1, SYSDATE, 'ADMIN');

INSERT INTO TIMESHEET_CODE(TIMESHEET_CODE, TIMESHEET_CODE_DESC, ACTIVE, DATE_CRT, USER_CRT)
VALUES('ZZ02', 'Sick Leave', 1, SYSDATE, 'ADMIN');

INSERT INTO TIMESHEET_CODE(TIMESHEET_CODE, TIMESHEET_CODE_DESC, ACTIVE, DATE_CRT, USER_CRT)
VALUES('ZZ03', 'Annual Leve', 1, SYSDATE, 'ADMIN');

INSERT INTO TIMESHEET_CODE(TIMESHEET_CODE, TIMESHEET_CODE_DESC, ACTIVE, DATE_CRT, USER_CRT)
VALUES('ZZ04', 'Public Holiday', 1, SYSDATE, 'ADMIN');

INSERT INTO TIMESHEET_CODE(TIMESHEET_CODE, TIMESHEET_CODE_DESC, ACTIVE, DATE_CRT, USER_CRT)
VALUES('ZZ05', 'Staff Training', 1, SYSDATE, 'ADMIN');

INSERT INTO TIMESHEET_CODE(TIMESHEET_CODE, TIMESHEET_CODE_DESC, ACTIVE, DATE_CRT, USER_CRT)
VALUES('ZZ11', 'Sales Support', 1, SYSDATE, 'ADMIN');