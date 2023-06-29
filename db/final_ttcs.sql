-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema final_ttcs
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema final_ttcs
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `final_ttcs` DEFAULT CHARACTER SET utf8 ;
USE `final_ttcs` ;

-- -----------------------------------------------------
-- Table `final_ttcs`.`Account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `final_ttcs`.`Account` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  `phoneNumber` VARCHAR(11) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `final_ttcs`.`Type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `final_ttcs`.`Type` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `final_ttcs`.`Product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `final_ttcs`.`Product` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `image` TEXT NULL,
  `type` INT NULL,
  INDEX `type_idx` (`type` ASC) VISIBLE,
  PRIMARY KEY (`id`),
  CONSTRAINT `type`
    FOREIGN KEY (`type`)
    REFERENCES `final_ttcs`.`Type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `final_ttcs`.`AccountProduct`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `final_ttcs`.`AccountProduct` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `AccId` INT UNSIGNED NOT NULL,
  `ProId` INT UNSIGNED NOT NULL,
  `quantity` INT NULL,
  `inport_price` INT NULL,
  `sales_price` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `AccId_idx` (`AccId` ASC) VISIBLE,
  INDEX `ProId_idx` (`ProId` ASC) VISIBLE,
  CONSTRAINT `AccId`
    FOREIGN KEY (`AccId`)
    REFERENCES `final_ttcs`.`Account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `ProId`
    FOREIGN KEY (`ProId`)
    REFERENCES `final_ttcs`.`Product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `final_ttcs`.`Customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `final_ttcs`.`Customer` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `phoneNumber` VARCHAR(11) NULL,
  `name` VARCHAR(45) NULL,
  `sex` INT NULL,
  `dob` DATE NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `final_ttcs`.`AccountCustomer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `final_ttcs`.`AccountCustomer` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `AccountId` INT UNSIGNED NOT NULL,
  `CustomerId` INT UNSIGNED NOT NULL,
  `reward_points` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `AccountId_idx` (`AccountId` ASC) VISIBLE,
  INDEX `CustomerId_idx` (`CustomerId` ASC) VISIBLE,
  CONSTRAINT `AccountId`
    FOREIGN KEY (`AccountId`)
    REFERENCES `final_ttcs`.`Account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `CustomerId`
    FOREIGN KEY (`CustomerId`)
    REFERENCES `final_ttcs`.`Customer` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `final_ttcs`.`Bill`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `final_ttcs`.`Bill` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `saleDate` DATE NULL,
  `total` INT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `final_ttcs`.`BillDetail`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `final_ttcs`.`BillDetail` (
  `id` INT NOT NULL,
  `ACId` INT UNSIGNED NULL,
  `ProductId` INT UNSIGNED NULL,
  `BillId` INT UNSIGNED NULL,
  `quantity` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `ACid_idx` (`ACId` ASC) VISIBLE,
  INDEX `PruductId_idx` (`ProductId` ASC) VISIBLE,
  INDEX `BillId_idx` (`BillId` ASC) VISIBLE,
  CONSTRAINT `ACid`
    FOREIGN KEY (`ACId`)
    REFERENCES `final_ttcs`.`AccountCustomer` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `PruductId`
    FOREIGN KEY (`ProductId`)
    REFERENCES `final_ttcs`.`Product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `BillId`
    FOREIGN KEY (`BillId`)
    REFERENCES `final_ttcs`.`Bill` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

ALTER TABLE `final_ttcs`.`BillDetail`
DROP FOREIGN KEY `ACid`;
ALTER TABLE `final_ttcs`.`BillDetail`
DROP COLUMN `ACId`;

ALTER TABLE `final_ttcs`.`AccountProduct`
ADD COLUMN amountSold int default 0;

ALTER TABLE  `final_ttcs`.`accountcustomer`
MODIFY COLUMN reward_points INT DEFAULT 0;

ALTER TABLE `final_ttcs`.`bill` MODIFY saleDate TIMESTAMP;

ALTER TABLE  `final_ttcs`.`bill`
MODIFY COLUMN AccCusID INT NOT NULL;

ALTER TABLE  `final_ttcs`.`accountcustomer`
MODIFY COLUMN id INT NOT NULL AUTO_INCREMENT;

ALTER TABLE bill
ADD FOREIGN KEY (AccCusID) REFERENCES AccountCustomer(id);

ALTER TABLE  `final_ttcs`.`billdetail`
MODIFY COLUMN id INT NOT NULL AUTO_INCREMENT;

ALTER TABLE `final_ttcs`.`billdetail`
add column price int;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
