CREATE TABLE POST(
    POST_ID NUMBER,
        CONSTRAINT POST_PK PRIMARY KEY(POST_ID),
    MESSAGE NVARCHAR2(500),
    DATE_POSTED TIMESTAMP,
    USER_POSTED_ID NUMBER,
        CONSTRAINT USER_POSTED_FK FOREIGN KEY(USER_POSTED_ID) REFERENCES USERS(USER_ID)
);

CREATE SEQUENCE POST_ID_SEQ
    MINVALUE 1
    INCREMENT BY 1;


--mysql
-- CREATE TABLE POST(
--     POST_ID INT AUTO_INCREMENT,
--     MESSAGE VARCHAR(500),
--     DATE_POSTED DATETIME,
--     USER_POSTED_ID INT,
--     FOREIGN KEY(USER_POSTED_ID) REFERENCES USERS(USER_ID),
--     PRIMARY KEY(POST_ID)
-- );