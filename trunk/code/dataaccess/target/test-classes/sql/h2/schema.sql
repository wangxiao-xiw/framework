DROP TABLE if EXISTS TBL_EGM_USER_TEST;

/*==============================================================*/
/* Table: TBL_EGM_USER_TEST                                          */
/*==============================================================*/
CREATE TABLE TBL_EGM_USER_TEST (
   ID                   INT8  IDENTITY(1,1)  NOT NULL,
   LOGIN_NAME           VARCHAR(128)         NOT NULL,
   REAL_NAME            VARCHAR(32)          NOT NULL,
   EMAIL                VARCHAR(128)         NOT NULL,
   TEL                  VARCHAR(16)          NOT NULL,
   CRTED_DATETIME       TIMESTAMP            NOT NULL DEFAULT CURRENT_TIMESTAMP,
   IS_DELETED           BOOL                 NOT NULL DEFAULT 0,
   CONSTRAINT PK_TBL_EGM_USER PRIMARY KEY (ID)
);