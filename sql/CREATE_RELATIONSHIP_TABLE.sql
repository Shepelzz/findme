CREATE TABLE RELATIONSHIP(
  USER_FROM_ID NUMBER,
  CONSTRAINT RELATIONSHIP_USER_FROM_FK FOREIGN KEY(USER_FROM_ID) REFERENCES USERS(USER_ID),
  USER_TO_ID NUMBER,
  CONSTRAINT RELATIONSHIP_USER_TO_FK FOREIGN KEY(USER_TO_ID) REFERENCES USERS(USER_ID),
  STATUS NVARCHAR2(50) NOT NULL
);

CREATE SEQUENCE RELATIONSHIP_ID_SEQ
  MINVALUE 1
  INCREMENT BY 1;


--mysql
-- CREATE TABLE RELATIONSHIP(
--   USER_FROM_ID INT,
--   FOREIGN KEY(USER_FROM_ID) REFERENCES USERS(USER_ID),
--   USER_TO_ID INT,
--   FOREIGN KEY(USER_TO_ID) REFERENCES USERS(USER_ID),
--   STATUS VARCHAR(50) NOT NULL
-- );