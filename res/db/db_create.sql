
/******************************************************************************/
/***                                Domains                                 ***/
/******************************************************************************/



/******************************************************************************/
/***                               Exceptions                               ***/
/******************************************************************************/

/******************************************************************************/
/***                               Procedures                               ***/
/******************************************************************************/

SET TERM ^ ;

SET TERM ; ^



/******************************************************************************/
/***                            Tables and Views                            ***/
/******************************************************************************/


CREATE TABLE AGENCIES (
    ID INTEGER NOT NULL,
	USER_ID INTEGER);

CREATE TABLE CLIENTS (
    ID INTEGER NOT NULL,
    USER_ID INTEGER,
    PHONE_NUMBER VARCHAR(30));

CREATE TABLE OFFERS (
    ID INTEGER NOT NULL,
	PERFORMER_ID INTEGER,
	PRICE FLOAT,
	PERIOD INTEGER,
	DECRIPTION VARCHAR(30),
    SOCIAL_MEDIA VARCHAR(30));
	
CREATE TABLE ORDERS (
    ID INTEGER NOT NULL,
    OFFER_ID INTEGER,
	STATUS INTEGER,
	CONDITIONS VARCHAR(30));

CREATE TABLE PERFORMERS (
    ID INTEGER NOT NULL,
    USER_ID INTEGER,
    PHONE_NUMBER VARCHAR(30),
	AGENCY_ID INTEGER,
	MONEY_AMOUNT FLOAT);

CREATE TABLE USERS (
    ID INTEGER NOT NULL,
    NAME VARCHAR(30),
    USERNAME VARCHAR(30),
    PASSWORD VARCHAR(30));
	

/******************************************************************************/
/***                              Primary keys                              ***/
/******************************************************************************/


ALTER TABLE AGENCIES ADD CONSTRAINT PK_AGENCIES PRIMARY KEY (ID);
ALTER TABLE CLIENTS ADD CONSTRAINT PK_CLIENTS PRIMARY KEY (ID);
ALTER TABLE OFFERS ADD CONSTRAINT PK_OFFERS PRIMARY KEY (ID);
ALTER TABLE ORDERS ADD CONSTRAINT ORDERS PRIMARY KEY (ID);
ALTER TABLE PERFORMERS ADD CONSTRAINT PK_PERFORMERS PRIMARY KEY (ID);
ALTER TABLE USERS ADD CONSTRAINT PK_USERS PRIMARY KEY (ID);


/******************************************************************************/
/***                              Foreign keys                              ***/
/******************************************************************************/

ALTER TABLE CLIENTS ADD CONSTRAINT CLIENT_USER_ID FOREIGN KEY (USER_ID) REFERENCES USERS (ID);
ALTER TABLE PERFORMERS ADD CONSTRAINT PERFORMER_USER_ID FOREIGN KEY (USER_ID) REFERENCES USERS (ID);
ALTER TABLE PERFORMERS ADD CONSTRAINT PERFORMER_AGENCY_ID FOREIGN KEY (AGENCY_ID) REFERENCES AGENCIES (ID);
ALTER TABLE AGENCIES ADD CONSTRAINT AGENCY_USER_ID FOREIGN KEY (USER_ID) REFERENCES USERS (ID);
ALTER TABLE OFFERS ADD CONSTRAINT OFFER_PERFORMER_ID FOREIGN KEY (PERFORMER_ID) REFERENCES PERFORMERS (ID);
ALTER TABLE ORDERS ADD CONSTRAINT ORDER_OFFER_ID FOREIGN KEY (OFFER_ID) REFERENCES OFFERS (ID);


/******************************************************************************/
/***                           Check constraints                            ***/
/******************************************************************************/



/******************************************************************************/
/***                                Indices                                 ***/
/******************************************************************************/



/******************************************************************************/
/***                                Triggers                                ***/
/******************************************************************************/

SET TERM ^ ;

SET TERM ; ^

/******************************************************************************/
/***                               Procedures                               ***/
/******************************************************************************/

SET TERM ^ ;

SET TERM ; ^



/******************************************************************************/
/***                          Object descriptions                           ***/
/******************************************************************************/

