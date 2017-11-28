-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: localhost    Database: SuperpersonSighting_Tests
-- ------------------------------------------------------
-- Server version	5.7.19

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
-- Table structure for table `Location`
--

DROP TABLE IF EXISTS `Location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Location` (
  `LocationId` int(11) NOT NULL AUTO_INCREMENT,
  `NameOfResidence` varchar(45) DEFAULT NULL,
  `Address` varchar(60) NOT NULL,
  `Latitude` float(10,6) NOT NULL,
  `Longitude` float(10,6) NOT NULL,
  `Description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`LocationId`)
) ENGINE=InnoDB AUTO_INCREMENT=630 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Location`
--

LOCK TABLES `Location` WRITE;
/*!40000 ALTER TABLE `Location` DISABLE KEYS */;
INSERT INTO `Location` VALUES (629,'Midtown School of Science and Technology','Forest Hills',1.000000,1.000000,'I got... homework');
/*!40000 ALTER TABLE `Location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Organization`
--

DROP TABLE IF EXISTS `Organization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Organization` (
  `OrganizationId` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(30) NOT NULL,
  `Description` varchar(500) NOT NULL,
  `Address` varchar(60) NOT NULL,
  `ContactInfo` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`OrganizationId`)
) ENGINE=InnoDB AUTO_INCREMENT=942 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Organization`
--

LOCK TABLES `Organization` WRITE;
/*!40000 ALTER TABLE `Organization` DISABLE KEYS */;
INSERT INTO `Organization` VALUES (941,'Avengers','The Avengers. It\'s what we call ourselves, sort of like a team. \'Earth\'s Mightiest Heroes\' type of thing.','890 Fifth Avenue, Manhattan, New York City','212-576-4000');
/*!40000 ALTER TABLE `Organization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Sighting`
--

DROP TABLE IF EXISTS `Sighting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Sighting` (
  `SightingId` int(11) NOT NULL AUTO_INCREMENT,
  `DateSeen` date NOT NULL,
  `Description` varchar(500) NOT NULL,
  `SuperpersonId` int(11) NOT NULL,
  `LocationId` int(11) NOT NULL,
  `FileName` varchar(500) DEFAULT NULL,
  `Title` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`SightingId`),
  KEY `fk_Sighting_Superperson` (`SuperpersonId`),
  KEY `fk_Sighting_Location` (`LocationId`),
  CONSTRAINT `fk_Sighting_Location` FOREIGN KEY (`LocationId`) REFERENCES `Location` (`LocationId`),
  CONSTRAINT `fk_Sighting_Superperson` FOREIGN KEY (`SuperpersonId`) REFERENCES `Superperson` (`SuperpersonId`),
  CONSTRAINT `sighting_ibfk_1` FOREIGN KEY (`SuperpersonId`) REFERENCES `Superperson` (`SuperpersonId`),
  CONSTRAINT `sighting_ibfk_2` FOREIGN KEY (`LocationId`) REFERENCES `Location` (`LocationId`)
) ENGINE=InnoDB AUTO_INCREMENT=140 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Sighting`
--

LOCK TABLES `Sighting` WRITE;
/*!40000 ALTER TABLE `Sighting` DISABLE KEYS */;
/*!40000 ALTER TABLE `Sighting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Superperson`
--

DROP TABLE IF EXISTS `Superperson`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Superperson` (
  `SuperpersonId` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(30) NOT NULL,
  `Power` varchar(500) NOT NULL,
  `Description` varchar(500) NOT NULL,
  PRIMARY KEY (`SuperpersonId`)
) ENGINE=InnoDB AUTO_INCREMENT=601 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Superperson`
--

LOCK TABLES `Superperson` WRITE;
/*!40000 ALTER TABLE `Superperson` DISABLE KEYS */;
INSERT INTO `Superperson` VALUES (600,'Spider-Man','Superhuman strength, reflexes, and balance. Ability to cling to majority of surfaces. Subconscious ability to sense everything in his surroundings, AKA- \'spidey-sense\'.','');
/*!40000 ALTER TABLE `Superperson` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Superperson_Organization`
--

DROP TABLE IF EXISTS `Superperson_Organization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Superperson_Organization` (
  `OrganizationId` int(11) NOT NULL,
  `SuperpersonId` int(11) NOT NULL,
  PRIMARY KEY (`OrganizationId`,`SuperpersonId`),
  KEY `fk_Superperson_Organization_Superperson` (`SuperpersonId`),
  CONSTRAINT `fk_Superperson_Organization_Organization` FOREIGN KEY (`OrganizationId`) REFERENCES `Organization` (`OrganizationId`),
  CONSTRAINT `fk_Superperson_Organization_Superperson` FOREIGN KEY (`SuperpersonId`) REFERENCES `Superperson` (`SuperpersonId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Superperson_Organization`
--

LOCK TABLES `Superperson_Organization` WRITE;
/*!40000 ALTER TABLE `Superperson_Organization` DISABLE KEYS */;
INSERT INTO `Superperson_Organization` VALUES (941,600);
/*!40000 ALTER TABLE `Superperson_Organization` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-20  9:42:35
