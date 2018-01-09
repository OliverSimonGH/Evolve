-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema evolve
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `evolve` ;

-- -----------------------------------------------------
-- Schema evolve
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `evolve` DEFAULT CHARACTER SET utf8 ;
USE `evolve` ;

-- -----------------------------------------------------
-- Table `evolve`.`account_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `evolve`.`account_type` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `evolve`.`account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `evolve`.`account` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `fk_type` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_account_type1_idx` (`fk_type` ASC),
  CONSTRAINT `fk_account_type1`
    FOREIGN KEY (`fk_type`)
    REFERENCES `evolve`.`account_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `evolve`.`assessor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `evolve`.`assessor` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `fk_account` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_assessor_account1_idx` (`fk_account` ASC),
  CONSTRAINT `fk_assessor_account1`
    FOREIGN KEY (`fk_account`)
    REFERENCES `evolve`.`account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `evolve`.`company`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `evolve`.`company` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `fk_assessor` INT(11) NULL DEFAULT NULL,
  `fk_account` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_company_assessor1_idx` (`fk_assessor` ASC),
  INDEX `fk_company_account1_idx` (`fk_account` ASC),
  CONSTRAINT `fk_company_account1`
    FOREIGN KEY (`fk_account`)
    REFERENCES `evolve`.`account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_company_assessor1`
    FOREIGN KEY (`fk_assessor`)
    REFERENCES `evolve`.`assessor` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `evolve`.`assessment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `evolve`.`assessment` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `date` DATE NOT NULL,
  `approved` INT(11) NOT NULL DEFAULT '0',
  `qvi_score` INT(11) NULL DEFAULT NULL,
  `fk_company` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_assessment_company1_idx` (`fk_company` ASC),
  CONSTRAINT `fk_assessment_company1`
    FOREIGN KEY (`fk_company`)
    REFERENCES `evolve`.`company` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 35
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `evolve`.`moduletype`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `evolve`.`moduletype` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `evolve`.`module`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `evolve`.`module` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `fk_company` INT(11) NOT NULL,
  `fk_module` INT(11) NOT NULL,
  `deleted` INT(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  INDEX `fk_module_company1_idx` (`fk_company` ASC),
  INDEX `fk_module_moduleType1_idx` (`fk_module` ASC),
  CONSTRAINT `fk_module_company1`
    FOREIGN KEY (`fk_company`)
    REFERENCES `evolve`.`company` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_module_moduleType1`
    FOREIGN KEY (`fk_module`)
    REFERENCES `evolve`.`moduletype` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 50
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `evolve`.`people_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `evolve`.`people_type` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `evolve`.`people`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `evolve`.`people` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `fk_company` INT(11) NOT NULL,
  `fk_account` INT(11) NOT NULL,
  `fk_type` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_people_company_idx` (`fk_company` ASC),
  INDEX `fk_people_account1_idx` (`fk_account` ASC),
  INDEX `fk_people_people_type1_idx` (`fk_type` ASC),
  CONSTRAINT `fk_people_account1`
    FOREIGN KEY (`fk_account`)
    REFERENCES `evolve`.`account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_people_company`
    FOREIGN KEY (`fk_company`)
    REFERENCES `evolve`.`company` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_people_people_type1`
    FOREIGN KEY (`fk_type`)
    REFERENCES `evolve`.`people_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `evolve`.`questionnaire`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `evolve`.`questionnaire` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `fk_module` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_questionnaire_moduleType1_idx` (`fk_module` ASC),
  CONSTRAINT `fk_questionnaire_moduleType1`
    FOREIGN KEY (`fk_module`)
    REFERENCES `evolve`.`moduletype` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `evolve`.`questions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `evolve`.`questions` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `question` VARCHAR(400) NOT NULL,
  `fk_questionnaire` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_questions_questionnaire1_idx` (`fk_questionnaire` ASC),
  CONSTRAINT `fk_questions_questionnaire1`
    FOREIGN KEY (`fk_questionnaire`)
    REFERENCES `evolve`.`questionnaire` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 53
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `evolve`.`result`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `evolve`.`result` (
  `fk_assessment` INT(11) NOT NULL,
  `fk_module` INT(11) NOT NULL,
  `score` INT(11) NOT NULL,
  `fk_company` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`fk_assessment`, `fk_module`),
  INDEX `fk_assessment_has_module_module1_idx` (`fk_module` ASC),
  INDEX `fk_assessment_has_module_assessment1_idx` (`fk_assessment` ASC),
  CONSTRAINT `fk_assessment_has_module_assessment1`
    FOREIGN KEY (`fk_assessment`)
    REFERENCES `evolve`.`assessment` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_assessment_has_module_module1`
    FOREIGN KEY (`fk_module`)
    REFERENCES `evolve`.`module` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `evolve`.`scores`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `evolve`.`scores` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `score` VARCHAR(45) NOT NULL,
  `comment` VARCHAR(45) NULL DEFAULT NULL,
  `fk_question` INT(11) NOT NULL,
  `fk_module` INT(11) NOT NULL,
  `fk_result` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_scores_questions1_idx` (`fk_question` ASC),
  INDEX `fk_scores_module1_idx` (`fk_module` ASC),
  CONSTRAINT `fk_scores_module1`
    FOREIGN KEY (`fk_module`)
    REFERENCES `evolve`.`module` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_scores_questions1`
    FOREIGN KEY (`fk_question`)
    REFERENCES `evolve`.`questions` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 68
DEFAULT CHARACTER SET = utf8;

USE `evolve`;

DELIMITER $$
USE `evolve`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `evolve`.`insertModulesForCompany`
AFTER INSERT ON `evolve`.`company`
FOR EACH ROW
BEGIN
INSERT INTO module (fk_company, fk_module) VALUES (NEW.id, 1);
INSERT INTO module (fk_company, fk_module) VALUES (NEW.id, 2);
INSERT INTO module (fk_company, fk_module) VALUES (NEW.id, 3);
INSERT INTO module (fk_company, fk_module) VALUES (NEW.id, 4);
INSERT INTO module (fk_company, fk_module) VALUES (NEW.id, 5);
END$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
