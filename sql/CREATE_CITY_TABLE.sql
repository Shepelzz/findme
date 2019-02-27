CREATE TABLE CITY (
[ID] [bigint] IDENTITY(1,1) NOT NULL,
[NAME] VARCHAR(50) NOT NULL,
[COUNTRY_ID] [bigint]
);

ALTER TABLE findme.dbo.CITY ADD CONSTRAINT CITY_ID_pk PRIMARY KEY (ID)
ALTER TABLE findme.dbo.CITY ADD CONSTRAINT CITY_COUNTRY_ID_fk FOREIGN KEY (COUNTRY_ID) REFERENCES COUNTRY (ID)