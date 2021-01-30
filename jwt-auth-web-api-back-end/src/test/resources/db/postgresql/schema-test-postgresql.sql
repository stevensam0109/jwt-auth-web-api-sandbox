/*  ---------------------------------------
	-- DDL : Data Definition Language
	-- BASE DE DONNEES : POSTGRESQL 
	-- SCHEMA OR CATALOG :  
	---------------------------------------
*/

/* 	---------------------------
	-- Scripts de Suppressions 
	---------------------------
*/ 
-- Suppression des tables : T_CATEGORIES, T_CATEGORIES_T_PRODUCTS, T_PRODUCTS
DROP TABLE IF EXISTS T_CATEGORIES CASCADE; 
DROP TABLE IF EXISTS T_CATEGORIES_T_PRODUCTS CASCADE;
DROP TABLE IF EXISTS T_PRODUCTS CASCADE; 

 -- Suppression de la table T_USERS et éléments rattachés
DROP TABLE IF EXISTS T_USERS CASCADE; 
DROP TABLE IF EXISTS USER_ROLES CASCADE; 

-- Suprression de la séquence hibernate
DROP SEQUENCE IF EXISTS HIBERNATE_SEQUENCE;

/*  ----------------------------------------
	-- Scripts de Création des Tables
	---------------------------------------
*/

-- Création de la séquence pour incrément automatique des identifiants des tables
CREATE SEQUENCE HIBERNATE_SEQUENCE START 1 INCREMENT 1;

-- Création de la table : T_CATEGORIES
CREATE TABLE T_CATEGORIES (
	ID INT8 NOT NULL,
	CATEGORY_TYPE VARCHAR(255) NOT NULL,
	DESCRIPTION VARCHAR(255) NOT NULL,
	ENABLED BOOLEAN NOT NULL,
	NAME VARCHAR(255) NOT NULL,
	OPTLOCK INT4 NOT NULL,
	PRIMARY KEY (ID)
);

-- Création de la table d'association : T_CATEGORIES_T_PRODUCTS
create table T_CATEGORIES_T_PRODUCTS (
	CATEGORY_ID INT8 NOT NULL,
	PRODUCTS_ID INT8 NOT NULL,
	PRIMARY KEY (CATEGORY_ID, PRODUCTS_ID)
);  

-- Création de la table : T_PRODUCTS
CREATE TABLE T_PRODUCTS (
	ID INT8 NOT NULL,
	DESCRIPTION VARCHAR(255) NOT NULL,
	IS_ACTIVE BOOLEAN NOT NULL,
	NAME VARCHAR(255) NOT NULL,
	PRICE NUMERIC(19, 2) NOT NULL,
	QUANTITY int8 NOT NULL,
	UNIT_PRICE NUMERIC(19, 2) NOT NULL,
	OPTLOCK INT4 NOT NULL,
	IMAGE_URL VARCHAR(255) NOT NULL,
	PRIMARY KEY (ID)
);

-- Contrainte unicité de la colonne : PRODUCTS_ID
ALTER TABLE T_CATEGORIES_T_PRODUCTS 
	ADD CONSTRAINT UK_40p0vt4lvwv479gxy15u63x38 unique (PRODUCTS_ID);

-- Contrainte de la clé étrangère :PRODUCTS_ID dans la table d'association T_CATEGORIES_T_PRODUCTS
ALTER TABLE T_CATEGORIES_T_PRODUCTS 
	ADD CONSTRAINT FKprbvgp7usy4j4cu30t5rkjrl4 
		FOREIGN KEY (PRODUCTS_ID) 
		REFERENCES T_PRODUCTS (ID); 

-- Contrainte de la clé étrangère :CATEGORY_ID dans la table d'association T_CATEGORIES_T_PRODUCTS
ALTER TABLE T_CATEGORIES_T_PRODUCTS 
	ADD CONSTRAINT FK7oh5h90freuloi32a476qa5hk 
		FOREIGN KEY (CATEGORY_ID) 
		REFERENCES T_CATEGORIES(ID);

/*  ----------------------------------------
	-- Scripts de Création des Credentials
	---------------------------------------
*/

-- Création de la table T_USERS
CREATE TABLE T_USERS (
	ID INT8 NOT NULL,
	ACCOUNT_EXPIRED BOOLEAN NOT NULL,
	ACCOUNT_LOCKED BOOLEAN NOT NULL,
	CREATED_TIME TIMESTAMP, 
	CREDENTIALS_EXPIRED BOOLEAN NOT NULL,
	EMAIL VARCHAR(254) NOT NULL,
	ENABLED BOOLEAN NOT NULL,
	USER_PASSWORD VARCHAR(60) NOT NULL,
	UPDATED_TIME TIMESTAMP,
	USER_NAME VARCHAR(80) NOT NULL,
	OPTLOCK INTEGER NOT NULL DEFAULT '0',
	PRIMARY KEY (ID)
);

-- Création de la table USERS_ROLES
CREATE TABLE USER_ROLES (
	USER_ID INT8 NOT NULL,
	ROLES INT4
);

-- Contrainte unicité de l'adresse électronique
ALTER TABLE T_USERS 
	ADD CONSTRAINT UK_kbdgs6v1gu1pcoq5u9ohje6ep UNIQUE (EMAIL);
	
	-- Contrainte T_USERS du login
ALTER TABLE T_USERS 
	ADD CONSTRAINT UK_srr913w7behdj71hcwtde381p UNIQUE (USER_NAME);
	
-- Contrainte de la clé étrangère dans la table USERS_ROLES
ALTER TABLE USER_ROLES 
	ADD CONSTRAINT FKs6y4k5lgw4a4ei5lj2u2ibkh5 
		FOREIGN KEY (USER_ID) 
		REFERENCES T_USERS;
