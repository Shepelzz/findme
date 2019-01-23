CREATE TABLE MESSAGE(
    ID NUMBER,
        CONSTRAINT MESSAGE_PK PRIMARY KEY(ID),
    TEXT BLOB,
    DATE_SENT TIMESTAMP,
    DATE_READ TIMESTAMP,
    USER_FROM_ID NUMBER,
        CONSTRAINT USER_FROM_FK FOREIGN KEY(USER_FROM_ID) REFERENCES USERS(ID),
    USER_TO_ID NUMBER,
        CONSTRAINT USER_TO_FK FOREIGN KEY(USER_TO_ID) REFERENCES USERS(ID)
);

CREATE SEQUENCE MESSAGE_ID_SEQ
    MINVALUE 1
    INCREMENT BY 1;



--mssql
-- CREATE TABLE [dbo].[MESSAGE](
--     [ID] [bigint] IDENTITY(1,1) NOT NULL,
--     [TEXT] [varchar](max) NULL,
--     [DATE_SENT] [datetime] NOT NULL,
--     [DATE_READ] [datetime] NULL,
--     [USER_FROM_ID] [bigint] NOT NULL,
--     [USER_TO_ID] [bigint] NOT NULL,
-- CONSTRAINT [MESSAGE_PK] PRIMARY KEY CLUSTERED)