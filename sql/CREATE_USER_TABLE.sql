CREATE TABLE USERS(
    ID NUMBER,
        CONSTRAINT USERS_PK PRIMARY KEY(ID),
    FIRST_NAME NVARCHAR2(50),
    LAST_NAME NVARCHAR2(50),
    PHONE NVARCHAR2(15),
    COUNTRY NVARCHAR2(50),
    COUNTRY_ID NUMBER,
      CONSTRAINT COUNTRY_FK FOREIGN KEY(COUNTRY_ID) REFERENCES COUNTRY(ID),
    CITY NVARCHAR2(50),
    AGE NUMBER,
    DATE_REGISTERED TIMESTAMP,
    DATE_LAST_ACTIVE TIMESTAMP,
    RELATIONSHIP_STATUS NVARCHAR2(20),
    RELIGION NVARCHAR2(20),
    SCHOOL NVARCHAR2(100),
    UNIVERSITY NVARCHAR2(100),
    PASSWORD NVARCHAR2(50),
    EMAIL NVARCHAR2(50),
    PHOTO BLOB
);

CREATE SEQUENCE USER_ID_SEQ
    MINVALUE 1
    INCREMENT BY 1;


--mssql
CREATE TABLE USERS(
    [ID] [bigint] IDENTITY(1,1) NOT NULL,
    [FIRST_NAME] [varchar](50) NOT NULL,
    [LAST_NAME] [varchar](50) NULL,
    [PHONE] [varchar](15) NOT NULL,
    [COUNTRY_ID] [BIGINT] NULL,
    [CITY] [varchar](50) NULL,
    [AGE] [int] NULL,
    [DATE_REGISTERED] [datetime] NULL,
    [DATE_LAST_ACTIVE] [datetime] NULL,
    [RELATIONSHIP_STATUS] [varchar](20) NULL,
    [RELIGION] [varchar](20) NULL,
    [SCHOOL] [varchar](100) NULL,
    [UNIVERSITY] [varchar](100) NULL,
    [PASSWORD] [varchar](50) NOT NULL,
    [EMAIL] [varchar](50) NOT NULL,
    [PHOTO] image
CONSTRAINT [USERS_PK] PRIMARY KEY CLUSTERED )

ALTER TABLE findme.dbo.USERS ADD CONSTRAINT USERS_COUNTRY_ID_fk FOREIGN KEY (COUNTRY_ID) REFERENCES COUNTRY (ID)