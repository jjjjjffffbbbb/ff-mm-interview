CREATE TABLE "METRICS"."METRICS"(
    ID VARCHAR2(60 CHAR) NOT NULL,
    NAME VARCHAR2(50 CHAR) NOT NULL,
    TIMESTAMP DATE NOT NULL,
    VALUE_METRIC FLOAT NOT NULL,


    CONSTRAINT METRICS_PK PRIMARY KEY (ID)
);