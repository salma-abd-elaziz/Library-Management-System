
-- -----------------------------------------------------
-- Schema BOOK_STORE
-- -----------------------------------------------------
Drop schema BOOK_STORE ;
CREATE SCHEMA IF NOT EXISTS BOOK_STORE ;
USE BOOK_STORE ;


-- -----------------------------------------------------
-- Table BOOK_STORE.PUBLISHER
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS BOOK_STORE.PUBLISHER (
  Publisher_name VARCHAR(45) NOT NULL,
  Address VARCHAR(100) NULL,
  Phone_num VARCHAR(45) NOT NULL,
  PRIMARY KEY (Publisher_name)
  );


-- -----------------------------------------------------
-- Table BOOK_STORE.BOOK
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS BOOK_STORE.BOOK (
  ISBN INT  NOT NULL,
  Title VARCHAR(45) NOT NULL,
  Publisher VARCHAR(45) NOT NULL,
  Prod_year YEAR(4) NULL,
  Price decimal(10,2) UNSIGNED NOT NULL,
  Category VARCHAR(45) NOT NULL,
  Copies_num INT NOT NULL,
  Min_copies INT NOT NULL,
  PRIMARY KEY (ISBN),
  UNIQUE INDEX ISBN_UNIQUE (ISBN ASC),
  INDEX Category_indexing (Category ASC),
  INDEX Title_indexing (Title ASC),
  INDEX Publisher_indexing (Publisher ASC),
 
  CONSTRAINT Book_Publisher_fk
    FOREIGN KEY (Publisher)
    REFERENCES BOOK_STORE.PUBLISHER (Publisher_name)
    ON  DELETE  CASCADE	
    ON UPDATE CASCADE
);



-- -----------------------------------------------------
-- Table BOOK_STORE.ORDER
-- -----------------------------------------------------
DROP TABLE IF exists BOOK_ORDER;
CREATE TABLE IF NOT EXISTS BOOK_STORE.BOOK_ORDER (
  Seq_num INT NOT NULL AUTO_INCREMENT,
  ISBN INT  NOT NULL,
  Order_count INT UNSIGNED NOT NULL,
  Confirmed_by_manger BOOLEAN NOT NULL DEFAULT 0,
  PRIMARY KEY (Seq_num),
  CONSTRAINT Order_Book_fk
    FOREIGN KEY (ISBN)
    REFERENCES BOOK_STORE.BOOK (ISBN)
    ON DELETE CASCADE
    ON UPDATE CASCADE
    );



-- -----------------------------------------------------
-- Table BOOK_STORE.AUTHOR
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS BOOK_STORE.AUTHOR (
  ISBN INT  NOT NULL,
  Author_name VARCHAR(45) NOT NULL,
  PRIMARY KEY (ISBN, Author_name),
  CONSTRAINT Author_book_fk
    FOREIGN KEY (ISBN)
    REFERENCES BOOK_STORE.BOOK (ISBN)
    ON DELETE CASCADE
    ON UPDATE CASCADE
    );


-- -----------------------------------------------------
-- Table BOOK_STORE.STORE_STORE_USER
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS BOOK_STORE.STORE_USER (
  User_name VARCHAR(45) NOT NULL ,
  Password VARCHAR(45) NOT NULL,
  First_name VARCHAR(45) NOT NULL,
  Last_name VARCHAR(45) NOT NULL,
  Email VARCHAR(45) NOT NULL UNIQUE,
  Phone VARCHAR(45) NULL UNIQUE,
  Shipping_address VARCHAR(100) NOT NULL,
  Is_manager TINYINT(1) NOT NULL, -- 
  PRIMARY KEY (User_name),
  UNIQUE INDEX User_name_UNIQUE (User_name ASC)
);


-- -----------------------------------------------------
-- Table BOOK_STORE.shopping_card
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS BOOK_STORE.SHOPPING_CART (
  Seq_key INT NOT NULL AUTO_INCREMENT,
  User_name VARCHAR(45) NULL,
  ISBN INT  NULL,
  Num_books INT NULL,
  Con_date DATE NULL,
  Confirmed TINYINT(1) NULL DEFAULT 0,
  Book_price decimal(10,2), -- -------------------------------------------------------------------->
  PRIMARY KEY (Seq_key),
  UNIQUE INDEX Seq_key_UNIQUE (Seq_key ASC),
  INDEX user_name_indexing (User_name ASC),
  CONSTRAINT shipping_user_fk
    FOREIGN KEY (User_name)
    REFERENCES BOOK_STORE.STORE_USER (User_name)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT shipping_book_fk
    FOREIGN KEY (ISBN)
    REFERENCES BOOK_STORE.BOOK (ISBN)
    ON DELETE SET NULL
    ON UPDATE CASCADE);



-- -----------------------------------------------------
-- Table BOOK_STORE.PURSHASE
-- -----------------------------------------------------
-- CREATE TABLE IF NOT EXISTS BOOK_STORE.PURCHASE ( -- --------------------------------------->
--   User_name_pur VARCHAR(45) NOT NULL,
--   Money decimal(10,2) UNSIGNED NOT NULL ,
--   PRIMARY KEY (User_name_pur),
--   CONSTRAINT User_name_pur_fk
--     FOREIGN KEY (User_name_pur)
--     REFERENCES BOOK_STORE.STORE_USER (User_name)
--     ON DELETE CASCADE
--     ON UPDATE CASCADE
--   );


-- -----------------------------------------
-- ----- CONSTRINTS' TRIGGERS --------------
-- ----------------------------------------- 
  

-- ------------------ BOOK CONSTRINTS -------------
DELIMITER $$
CREATE TRIGGER BEFORE_INSERT_IN_BOOK BEFORE INSERT ON BOOK
FOR EACH ROW
BEGIN
    IF new.Copies_num < 0 then
        SIGNAL SQLSTATE '12345'
            SET MESSAGE_TEXT = 'number of copies can not be negative';
    END IF;
    IF new.ISBN < 0 then
        SIGNAL SQLSTATE '12345'
            SET MESSAGE_TEXT = 'ISBN can not be negative';
    END IF;

    IF new.Min_copies < 0 then
        SIGNAL SQLSTATE '12345'
            SET MESSAGE_TEXT = 'min number of copies can not be negative';
    END IF;
	IF new.Category!= "Science" AND new.Category != "Art" AND NEW.Category != "Religion" AND NEW.Category!= "History" AND NEW.Category != "Geography"  then
        SIGNAL SQLSTATE '12345'
            SET MESSAGE_TEXT = 'Category is not in domain ';
    END IF;
    IF NEW.Prod_year > YEAR(curdate()) THEN
        SIGNAL SQLSTATE '12345'
            SET MESSAGE_TEXT = 'INVALID YEAR ';
    END IF;
END$$   
DELIMITER ; 


DELIMITER $$
CREATE TRIGGER AFTER_INSERT_IN_BOOK AFTER INSERT ON BOOK FOR EACH ROW
BEGIN
   IF NEW.Copies_num < NEW.Min_copies then 
         INSERT INTO BOOK_ORDER(ISBN,Order_count,Confirmed_by_manger) VALUES(NEW.ISBN,NEW.Min_copies,false);
   END IF;
END$$  


DELIMITER $$
CREATE TRIGGER BEFORE_UPDATE_IN_BOOK BEFORE update ON BOOK FOR EACH ROW
BEGIN
    IF new.Copies_num < 0 then
        SIGNAL SQLSTATE '12345'
            SET MESSAGE_TEXT = 'number of copies can not be negative , Update can not be done';
    END IF;
    IF new.ISBN < 0 then
        SIGNAL SQLSTATE '12345'
            SET MESSAGE_TEXT = 'ISBN can not be negative , Update can not be done';
    END IF;

    IF new.Min_copies < 0 then
        SIGNAL SQLSTATE '12345'
            SET MESSAGE_TEXT = 'min number of copies can not be negative , Update can not be done';
    END IF;
    IF new.Category!= "Science" AND new.Category != "Art" AND NEW.Category != "Religion" AND NEW.Category!= "History" AND NEW.Category != "Geography"  then
        SIGNAL SQLSTATE '12345'
            SET MESSAGE_TEXT = 'Category is not in domain  , Update can not be done';
    END IF;
    IF NEW.Prod_year > YEAR(curdate()) THEN
        SIGNAL SQLSTATE '12345'
            SET MESSAGE_TEXT = 'INVALID YEAR ';
    END IF;
END$$ 

DELIMITER $$
CREATE TRIGGER AFTER_UPDATE_IN_BOOK AFTER update ON BOOK FOR EACH ROW
BEGIN
   IF NEW.Copies_num < NEW.Min_copies AND NOT exists (SELECT * FROM BOOK_ORDER WHERE ISBN = NEW.ISBN)then 
         INSERT INTO BOOK_ORDER(ISBN,Order_count,Confirmed_by_manger) VALUES(NEW.ISBN,NEW.Min_copies,false);
   END IF;
END$$ 


-- ------------------ BOOK_ORDER CONSTRINTS -------------
DELIMITER $$
CREATE TRIGGER delete_order before delete ON BOOK_ORDER FOR EACH ROW
BEGIN
	IF old.Confirmed_by_manger = 1 then
		UPDATE BOOK AS B SET Copies_num = Copies_num + old.Order_count where B.ISBN = old.ISBN;
	end if;
END$$  
 
-- ------------------ STORE_USER CONSTRINTS -------------

DELIMITER $$
CREATE TRIGGER BEFORE_INSERT_TO_STORE_USER before INSERT ON STORE_USER FOR EACH ROW
BEGIN
   IF new.Email not like '%@%.com' THEN
		SIGNAL SQLSTATE '12345'
            SET MESSAGE_TEXT = 'invalid Email';
   END IF;
END$$ 

-- DELIMITER $$
-- CREATE TRIGGER AFTER_INSERT_TO_STORE_USER AFTER INSERT ON STORE_USER FOR EACH ROW -- ------------------------------------------->
-- BEGIN
-- 		INSERT INTO PURCHASE VALUES(NEW.User_name,0);
-- 
-- END$$ 

-- ------------------ SHOPPING_CART CONSTRINTS -------------
DELIMITER $$
CREATE TRIGGER BEFORE_INSERT_T0_SHOPPING_CART BEFORE INSERT ON SHOPPING_CART
FOR EACH ROW
BEGIN
    IF new.Num_books < 0 then
        SIGNAL SQLSTATE '12345'
            SET MESSAGE_TEXT = 'number of copies can not be negative';
    END IF;
-- 	IF  new.Num_books > (SELECT Copies_num FROM BOOK AS B where B.ISBN = new.ISBN) then
--         SIGNAL SQLSTATE '12345'
--             SET MESSAGE_TEXT = 'number of copies is not available in the store ';
--     END IF;
	IF new.Confirmed = true then
		UPDATE BOOK as B set B.Copies_num = B.Copies_num - new.Num_books where B.ISBN = new.ISBN ;
 -- ------------------------------------------------------------------------------------------------------------------->		
-- UPDATE PURCHASE AS P set P.Money = P.Money + new.Num_books*(SELECT Price FROM BOOK WHERE ISBN=NEW.ISBN) where User_name_pur = new.User_name; -- ------------------------>
    END IF;
    
END$$   
DELIMITER ;



-- ------------------ SHOPPING_CART CONSTRINTS -------------
DELIMITER $$
CREATE TRIGGER BEFORE_UPDATE_T0_SHOPPING_CART BEFORE UPDATE ON SHOPPING_CART
FOR EACH ROW
BEGIN
	IF new.Confirmed = true AND new.ISBN=null then
		SIGNAL SQLSTATE '12345'
            SET MESSAGE_TEXT = 'The book is deleted from the store';
    END IF;
    IF new.Num_books > (SELECT Copies_num FROM BOOK AS B where B.ISBN = new.ISBN) AND new.Confirmed= true and old.Confirmed= false  then
        SIGNAL SQLSTATE '12345'
            SET MESSAGE_TEXT = 'number of copies is not available';
    END IF;
	IF  new.Num_books <0 then
        SIGNAL SQLSTATE '12345'
            SET MESSAGE_TEXT = 'number of copies can not be negative ';
    END IF;
	 IF new.Confirmed= true and old.Confirmed= false  then -- ------------------------------------> 
		set NEW.Book_price= (SELECT Price FROM BOOK WHERE ISBN = NEW.ISBN);
	 END IF;
	
END$$   
DELIMITER ;

  

DELIMITER $$
CREATE TRIGGER AFTER_UPDATE_T0_SHOPPING_CART AFTER UPDATE ON SHOPPING_CART
FOR EACH ROW
BEGIN
	IF new.Confirmed= true and old.Confirmed= false AND NEW.ISBN != null then
		UPDATE BOOK AS B set B.Copies_num = B.Copies_num-new.Num_books  where B.ISBN = new.ISBN ;
		
-- --------------------------------------------------------------------------------------->
		-- UPDATE PURCHASE AS P set P.Money = P.Money + new.Num_books*(SELECT Price FROM BOOK WHERE ISBN=NEW.ISBN) where User_name_pur = new.User_name; -- ------------------------>
		-- update SHOPPING_CART AS SH_C set Con_date =curdate() where SH_C.Seq_key = NEW.Seq_key;
    END IF;
END$$   
DELIMITER ;


  
