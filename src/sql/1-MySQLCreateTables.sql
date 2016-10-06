-- Indexes for primary keys have been explicitly created.

-- ---------- Table for validation queries from the connection pool. ----------

DROP TABLE PingTable;
CREATE TABLE PingTable (foo CHAR(1));

DROP TABLE Bid;
DROP TABLE Product;
DROP TABLE UserProfile;
DROP TABLE Category;

-- ------------------------------ UserProfile ----------------------------------

CREATE TABLE UserProfile (
    usrId BIGINT NOT NULL AUTO_INCREMENT,
    loginName VARCHAR(30) COLLATE latin1_bin NOT NULL,
    enPassword VARCHAR(13) NOT NULL, 
    firstName VARCHAR(30) NOT NULL,
    lastName VARCHAR(40) NOT NULL, 
    email VARCHAR(60) NOT NULL,
    CONSTRAINT UserProfile_PK PRIMARY KEY (usrId),
    CONSTRAINT LoginNameUniqueKey UNIQUE (loginName)) 
    ENGINE = InnoDB;

CREATE INDEX UserProfileIndexByLoginName ON UserProfile (loginName);

-- ------------------------------ Category ----------------------------------

CREATE TABLE Category (
    catId BIGINT NOT NULL AUTO_INCREMENT,
    catName VARCHAR(30) COLLATE latin1_bin NOT NULL,
    CONSTRAINT Category_PK PRIMARY KEY (catId)) 
    ENGINE = InnoDB;

CREATE INDEX CategoryIndexByCatName ON Category (catName);

-- ------------------------------ Product ----------------------------------

CREATE TABLE Product (
    proId BIGINT NOT NULL AUTO_INCREMENT,
    usrId BIGINT NOT NULL,
    catId BIGINT NOT NULL,
    proName VARCHAR(100) COLLATE latin1_spanish_ci NOT NULL,
    proDesc VARCHAR(100) NOT NULL,
    startDate TIMESTAMP DEFAULT 0 NOT NULL,
    endDate TIMESTAMP DEFAULT 0 NOT NULL, 
    strPrice NUMERIC(8,2) NOT NULL,
    shInfo VARCHAR(100) NOT NULL,
    version BIGINT NOT NULL,
    auctionVal NUMERIC(8,2) NOT NULL,
    winnerId BIGINT,
    CONSTRAINT Product_PK PRIMARY KEY (proId),
    CONSTRAINT Product_usrId_FK FOREIGN KEY (usrId) REFERENCES UserProfile(usrId),
    CONSTRAINT Product_catId_FK FOREIGN KEY (catId) REFERENCES Category(catId),
    CONSTRAINT Product_winnerId_FK FOREIGN KEY (winnerId) REFERENCES UserProfile(usrId)) 
    ENGINE = InnoDB;

CREATE INDEX ProductIndexByProName ON Product (proName);

-- ------------------------------ Bid ----------------------------------

CREATE TABLE Bid (
    bidId BIGINT NOT NULL AUTO_INCREMENT,
    usrId BIGINT NOT NULL,
    proId BIGINT NOT NULL,
    bidVal NUMERIC(8,2) NOT NULL,
    date TIMESTAMP NOT NULL,
    auctionVal NUMERIC(8,2) NOT NULL,
    winnerId BIGINT,
    CONSTRAINT Bid_PK PRIMARY KEY (bidId),
    CONSTRAINT Bid_usrId_FK FOREIGN KEY (usrId) REFERENCES UserProfile(usrId),
    CONSTRAINT Bid_proId_FK FOREIGN KEY (proId) REFERENCES Product(proId),
    CONSTRAINT Bid_winnerId_FK FOREIGN KEY (winnerId) REFERENCES UserProfile(usrId))
    ENGINE = InnoDB;
    

/*
--Categorys
DELETE FROM Category;
INSERT INTO Category (catName) VALUES ('Coches');
INSERT INTO Category (catName) VALUES ('Videoconsolas');
INSERT INTO Category (catName) VALUES ('Televisores');
*/
  
