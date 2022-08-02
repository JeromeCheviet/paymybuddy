-- MySQL dump 10.13  Distrib 5.7.38, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: paymybuddy
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Table structure for table `contact`
--

DROP TABLE IF EXISTS `contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact` (
  `user_id` int NOT NULL,
  `friend_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`friend_id`),
  KEY `userid` (`user_id`),
  KEY `friendid` (`friend_id`),
  CONSTRAINT `friendid` FOREIGN KEY (`friend_id`) REFERENCES `user` (`id`),
  CONSTRAINT `userid` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact`
--

LOCK TABLES `contact` WRITE;
/*!40000 ALTER TABLE `contact` DISABLE KEYS */;
INSERT INTO `contact` VALUES (1,4),(1,7),(7,2),(7,3),(7,4),(7,8),(8,4);
/*!40000 ALTER TABLE `contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transaction` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `user_id` int NOT NULL,
  `beneficiary_user_id` int NOT NULL,
  `amount` float NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `fee_amount` float DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `beneficiary_user_id` (`beneficiary_user_id`),
  CONSTRAINT `transaction_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `transaction_ibfk_2` FOREIGN KEY (`beneficiary_user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES (1,'2022-06-20',7,8,10,'Your birthday',0.05),(2,'2022-06-23',7,2,8,'Piou',0.04),(3,'2022-06-23',7,8,5,'Restaurant',0.025),(4,'2022-06-24',7,3,7,'Bills',0.035),(5,'2022-06-28',7,4,10,'Bills',0.05),(6,'2022-07-11',7,2,1,'test',0.005),(7,'2022-07-11',8,4,2,'tips',0.01),(8,'2022-07-11',8,4,2,'test',0.01);
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `name` varchar(50) NOT NULL,
  `rib` varchar(100) NOT NULL,
  `bank_name` varchar(50) NOT NULL,
  `balance` float NOT NULL,
  `role` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'jerome@mail.fr','$2y$10$z8ycLx9471w0mfC0nMhYN.gcp3cVK3JsQdbgAyvgx8WmcuA3kEsz2','Jerome','FR76-000-111-4444','Banque A',3,1),(2,'hayley@mail.fr','$2y$10$z8ycLx9471w0mfC0nMhYN.gcp3cVK3JsQdbgAyvgx8WmcuA3kEsz2','Hayley','FR702134456787663332','banque B',9,0),(3,'clara@mail.fr','$2y$10$z8ycLx9471w0mfC0nMhYN.gcp3cVK3JsQdbgAyvgx8WmcuA3kEsz2','Clara','FR706545033373569645','banque A',7,0),(4,'smith@mail.fr','$2y$10$z8ycLx9471w0mfC0nMhYN.gcp3cVK3JsQdbgAyvgx8WmcuA3kEsz2','Smith','FR70765498230992134','banque B',14,0),(7,'toto@mail.fr','$2a$10$EvXCyPf0m/vRv7ml/aZTOuL3UgnmT.qqgZLt2x.lsEC0/KrPtfZeq','Toto','FR76-000-111-2222','maFrenchBank',59,1),(8,'tata@mail.fr','$2a$10$9EeEK5KTTJ8qNbbxHMN9F..oLUIhZtOB9gTsoB84HuDNdDfHwCkva','Tato','FR76-000-111-2345','maFrenchBank',1,0),(9,'test@mail.fr','$2a$10$5lEsIPfYRhZ3M3aeXYYM0eroGP5/uKozNAU9ZokyeioUwPaQpB2HS','Test','FR76-4332-1954','maFrenchBank',0,0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-07-25 14:57:37
