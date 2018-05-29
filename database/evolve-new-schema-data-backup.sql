-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: evolve
-- ------------------------------------------------------
-- Server version	5.7.17-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `account`
--
DROP SCHEMA IF EXISTS `evolve` ;

-- -----------------------------------------------------
-- Schema evolve
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `evolve` DEFAULT CHARACTER SET utf8 ;
USE `evolve` ;

CREATE TABLE IF NOT EXISTS `evolve`.`account` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `evolve`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `evolve`.`role` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `role` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `evolve`.`account_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `evolve`.`account_role` (
  `fk_account` INT(11) NOT NULL,
  `fk_role` INT(11) NOT NULL,
  PRIMARY KEY (`fk_account`, `fk_role`),
  INDEX `fk_account_has_role_role1_idx` (`fk_role` ASC),
  INDEX `fk_account_has_role_account1_idx` (`fk_account` ASC),
  CONSTRAINT `fk_account_has_role_account1`
    FOREIGN KEY (`fk_account`)
    REFERENCES `evolve`.`account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_account_has_role_role1`
    FOREIGN KEY (`fk_role`)
    REFERENCES `evolve`.`role` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
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
    ON DELETE CASCADE
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
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 3
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
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 36
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
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 75
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
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_people_company`
    FOREIGN KEY (`fk_company`)
    REFERENCES `evolve`.`company` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_people_people_type1`
    FOREIGN KEY (`fk_type`)
    REFERENCES `evolve`.`people_type` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 2
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
AUTO_INCREMENT = 49
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
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 107
DEFAULT CHARACTER SET = utf8;

USE `evolve` ;


LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'assessor@gmail.com','123'),(2,'company@gmail.com','123'),(3,'customer@gmail.com','123'),(17,'asda@gmail.com','$2a$10$MBwWUPVebkWdjxgv4z/FLeO329wpKf1Qy1XBUAbc8GjEAKephtS0O'),(18,'supra@gmail.com','$2a$10$D52IRX9Abv/gcG0x8BN7X.YJYlKlRt/nZ6uWFlG/SWeUmX8jR6jmy'),(19,'supra1@gmail.com','$2a$10$72kFXB5aC.QJWDgXOU6UjO/NZJFi9UYBmkrqXclAF7vGv8sSbcILm'),(83,'oliversimon98@gmail.com','$2a$10$5QE3.TQ08/Xe1LIWdsoBTeHQiCddszyp0qLGLBk/95gMq6JqjR8me'),(84,'oliversimon99@gmail.com','$2a$10$wLIR22ZVCHw2Nmkfoll4BuGsv8iZ96dDtC4fzerYq1GfAJ6M/ifuq'),(85,'joshteague20@icloud.com','$2a$10$T8eUbivgQ.iHtL83VnwODOuoHnvFNEzIejQGNtsYE965rIuQ8qBay'),(86,'pol@pol.com','$2a$10$VnGzRMEuv18D3xXkIdWTpOWv3yiMXVesCLh80eRTkS.6fT3uRppEG');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `account_role`
--

LOCK TABLES `account_role` WRITE;
/*!40000 ALTER TABLE `account_role` DISABLE KEYS */;
INSERT INTO `account_role` VALUES (1,1),(2,1),(3,1),(2,3),(17,3),(18,3),(19,3),(86,3),(3,4),(84,4),(85,4),(1,5);
/*!40000 ALTER TABLE `account_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `assessment`
--

/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `evolve`.`CreateResultsForAssessment`
AFTER INSERT ON `evolve`.`assessment`
FOR EACH ROW
BEGIN

#Settings variables
DECLARE module1 INT;
DECLARE module2 INT;
DECLARE module3 INT;
DECLARE module4 INT;
DECLARE module5 INT;

DECLARE avg1 INT;
DECLARE avg2 INT;
DECLARE avg3 INT;
DECLARE avg4 INT;
DECLARE avg5 INT;

#Settings variables with functions
SET module1 = GetModuleByIdAndCompany(1, new.fk_company);
SET module2 = GetModuleByIdAndCompany(2, new.fk_company);
SET module3 = GetModuleByIdAndCompany(3, new.fk_company);
SET module4 = GetModuleByIdAndCompany(4, new.fk_company);
SET module5 = GetModuleByIdAndCompany(5, new.fk_company);

SET avg1 = GetAverageOfScores(NEW.id, module1);
SET avg2 = GetAverageOfScores(NEW.id, module2);
SET avg3 = GetAverageOfScores(NEW.id, module3);
SET avg4 = GetAverageOfScores(NEW.id, module4);
SET avg5 = GetAverageOfScores(NEW.id, module5);

#Applying the data to the results table
INSERT INTO result (fk_assessment, fk_module, score, fk_company) VALUES (NEW.id, module1, avg1, NEW.fk_company);
INSERT INTO result (fk_assessment, fk_module, score, fk_company) VALUES (NEW.id, module2, avg2, NEW.fk_company);
INSERT INTO result (fk_assessment, fk_module, score, fk_company) VALUES (NEW.id, module3, avg3, NEW.fk_company);
INSERT INTO result (fk_assessment, fk_module, score, fk_company) VALUES (NEW.id, module4, avg4, NEW.fk_company);
INSERT INTO result (fk_assessment, fk_module, score, fk_company) VALUES (NEW.id, module5, avg5, NEW.fk_company);

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Dumping data for table `assessor`
--

LOCK TABLES `assessor` WRITE;
/*!40000 ALTER TABLE `assessor` DISABLE KEYS */;
INSERT INTO `assessor` VALUES (1,'Bob Ross',1);
/*!40000 ALTER TABLE `assessor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES (1,'Halifax',1,2),(3,'Asda',1,17),(4,'Supra',1,18),(5,'Supra',1,19),(6,'pol',1,86);
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `evolve`.`insertModulesForCompany`
AFTER INSERT ON `evolve`.`company`
FOR EACH ROW
BEGIN
INSERT INTO module (fk_company, fk_module) VALUES (NEW.id, 1);
INSERT INTO module (fk_company, fk_module) VALUES (NEW.id, 2);
INSERT INTO module (fk_company, fk_module) VALUES (NEW.id, 3);
INSERT INTO module (fk_company, fk_module) VALUES (NEW.id, 4);
INSERT INTO module (fk_company, fk_module) VALUES (NEW.id, 5);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Dumping data for table `module`
--

LOCK TABLES `module` WRITE;
/*!40000 ALTER TABLE `module` DISABLE KEYS */;
INSERT INTO `module` VALUES (75,1,1,0),(76,1,2,0),(77,1,3,0),(78,1,4,1),(79,1,5,0),(80,3,1,0),(81,3,2,0),(82,3,3,0),(83,3,4,0),(84,3,5,0),(85,4,1,0),(86,4,2,0),(87,4,3,0),(88,4,4,0),(89,4,5,0),(90,5,1,0),(91,5,2,0),(92,5,3,0),(93,5,4,0),(94,5,5,0),(95,6,1,0),(96,6,2,1),(97,6,3,0),(98,6,4,0),(99,6,5,0);
/*!40000 ALTER TABLE `module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `moduletype`
--

LOCK TABLES `moduletype` WRITE;
/*!40000 ALTER TABLE `moduletype` DISABLE KEYS */;
INSERT INTO `moduletype` VALUES (1,'Clients'),(2,'People'),(3,'Value'),(4,'FM Excellence'),(5,'FM Standards');
/*!40000 ALTER TABLE `moduletype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `people`
--

LOCK TABLES `people` WRITE;
/*!40000 ALTER TABLE `people` DISABLE KEYS */;
INSERT INTO `people` VALUES (1,'Oliver','Simon',1,3,1),(66,'Oliver','Simon',1,84,2),(67,'Oliver','Simon',1,85,1);
/*!40000 ALTER TABLE `people` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `people_type`
--

LOCK TABLES `people_type` WRITE;
/*!40000 ALTER TABLE `people_type` DISABLE KEYS */;
INSERT INTO `people_type` VALUES (1,'Client'),(2,'Employee'),(3,'Manager');
/*!40000 ALTER TABLE `people_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `questionnaire`
--

LOCK TABLES `questionnaire` WRITE;
/*!40000 ALTER TABLE `questionnaire` DISABLE KEYS */;
INSERT INTO `questionnaire` VALUES (1,'Clients',1),(2,'People',2),(3,'Value',3),(4,'FM Excellence',4),(5,'FM Standards',5);
/*!40000 ALTER TABLE `questionnaire` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES (1,'Accessibility 1.0 lol',1),(2,'Accessibility: Easily Contactable',1),(3,'Responsiveness: Telephone answered promptly',1),(4,'Responsiveness: Prompt response to correspondence',1),(5,'Responsiveness: Attending to the issue',1),(6,'Reliability: Communicate timescales',1),(7,'Reliability: Meet Agreed timescales',1),(8,'Reliability: Provision of right service',1),(9,'Competence: Demonstrable processes',1),(10,'Competence: Demonstrable well trained staff',1),(11,'Credibility: General confidence in behaviour and approach',1),(12,'Credibility: Honesty',1),(13,'Understanding: Demonstrate clear understanding of requirements',1),(14,'Understanding: Understanding needs and requirements',1),(15,'Understanding: Sympathetic to customer problems and contraints',1),(16,'Communication: Recieve regular progress reports',1),(17,'Communication: Explain the impact of works',1),(18,'Communication: Communicate in an understandable way',1),(19,'Communication: Listen to customer',1),(20,'Communication: Communicate on-going progress of works/jobs',1),(21,'Communication: Communicate job closure',1),(22,'Courtesy: Courteous staff',1),(23,'Courtesy: Helpful staff',1),(24,'Courtesy: Presentable staff',1),(25,'Facilities: Hygiene (Cleaning)',1),(26,'Facilities: Waste',1),(27,'Facilities: Security',1),(28,'Facilities: Catering and vending',1),(29,'Facilities: Maintenance',1),(30,'Facilities: Mailroom',1),(31,'Facilities: Help desk',1),(32,'I am familiar with the Job Description for the service that I am expected to deliver and my actual roles and responsibilities closely match this',2),(33,'A Personal Development Review process (staff appraisal) exists and occurs on a regular basis (12-monthly minimum)',2),(34,'My own PDR is fair and reasonable as are the objectives set',2),(35,'Training plans exist and these are updated on a regular basis. I am familiar with the TP and I am content with the level of training that I receive.',2),(36,'There is strong leadership and management of the service (including company briefings). It works well at all levels',2),(37,'I am aware of my Health & Safety and Environmental obligations',2),(38,'Clear work instructions and procedures exist and I can refer to them to carry out my work',2),(39,'In my opinion, the service(s) being delivered are to a high standard and the client is content with the level of service delivered',2),(40,'I receive regular feedback on my performance. Good performance is recognised',2),(41,'I am able to feedback my views and comments to the management team and these are considered accordingly',2),(42,'Innovation is encouraged from above and I am encouraged to propose innovative ideas. I am incentivised to work efficiently and offer innovative ideas',2),(43,'I am given sufficient time to carry out my role and perform my tasks as per my job specification',2),(44,'I am motivated, empowered and feel part of the team',2),(45,'I receive job satisfaction',2),(46,'Do you have a clear FM strategy for the FM services being delivered and how does this strategy ensure value?',4),(47,'Are all stakeholders aware of your Service delivery strategy? How is this communicated to all stakeholders?',4),(48,'How do you revise your standard policies to meet the requirements of the Clients policies concerning the FM services?',4),(49,'This is a new question',1),(50,'Hello12345',1);
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `result`
--

LOCK TABLES `result` WRITE;
/*!40000 ALTER TABLE `result` DISABLE KEYS */;
INSERT INTO `result` VALUES (36,75,73,1),(36,76,0,1),(36,77,0,1),(36,78,0,1),(36,79,0,1),(37,75,0,1),(37,76,0,1),(37,77,0,1),(37,78,0,1),(37,79,0,1),(38,75,0,1),(38,76,0,1),(38,77,0,1),(38,78,0,1),(38,79,0,1);
/*!40000 ALTER TABLE `result` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_USER'),(3,'ROLE_COMPANY'),(4,'ROLE_CUSTOMER'),(5,'ROLE_ASSESSOR');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `scores`
--

LOCK TABLES `scores` WRITE;
/*!40000 ALTER TABLE `scores` DISABLE KEYS */;
INSERT INTO `scores` VALUES (70,'3',NULL,1,70,NULL),(71,'4',NULL,2,70,NULL),(72,'5','ckasdmckas',3,70,NULL),(73,'4',NULL,1,70,NULL),(74,'4',NULL,2,70,NULL),(75,'4',NULL,3,70,NULL),(76,'4',NULL,4,70,NULL),(77,'4',NULL,5,70,NULL),(78,'4',NULL,6,70,NULL),(79,'4',NULL,7,70,NULL),(80,'4',NULL,8,70,NULL),(81,'4',NULL,9,70,NULL),(82,'4',NULL,10,70,NULL),(83,'4',NULL,11,70,NULL),(84,'4',NULL,12,70,NULL),(85,'4',NULL,13,70,NULL),(86,'4',NULL,14,70,NULL),(87,'4',NULL,15,70,NULL),(88,'4',NULL,16,70,NULL),(89,'4',NULL,17,70,NULL),(90,'4',NULL,18,70,NULL),(91,'4',NULL,19,70,NULL),(92,'4',NULL,20,70,NULL),(93,'4',NULL,21,70,NULL),(94,'4',NULL,22,70,NULL),(95,'4',NULL,23,70,NULL),(96,'4',NULL,24,70,NULL),(97,'4',NULL,25,70,NULL),(98,'4',NULL,26,70,NULL),(99,'4',NULL,27,70,NULL),(100,'4',NULL,28,70,NULL),(101,'4',NULL,29,70,NULL),(102,'4',NULL,30,70,NULL),(103,'4',NULL,31,70,NULL),(104,'4',NULL,1,70,NULL),(105,'4',NULL,30,70,NULL),(106,'4',NULL,31,70,NULL),(107,'3',NULL,1,75,36),(108,'4',NULL,1,75,36),(109,'4',NULL,2,75,36);
/*!40000 ALTER TABLE `scores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'evolve'
--

--
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-02 17:44:12
