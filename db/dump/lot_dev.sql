-- MySQL dump 10.13  Distrib 5.6.30, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: lot_dev
-- ------------------------------------------------------
-- Server version	5.6.30-0ubuntu0.14.04.1

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
-- Table structure for table `block_amounts`
--

DROP TABLE IF EXISTS `block_amounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `block_amounts` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `order_id` bigint(20) DEFAULT NULL,
  `blocked_amount` double DEFAULT NULL,
  `actual_amount_charged` double DEFAULT NULL,
  `status` text COLLATE utf8_unicode_ci,
  `created_at` datetime(3) DEFAULT NULL,
  `updated_at` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `block_amounts`
--

LOCK TABLES `block_amounts` WRITE;
/*!40000 ALTER TABLE `block_amounts` DISABLE KEYS */;
INSERT INTO `block_amounts` VALUES (1,0,213,900,0,'Blocked','2016-02-20 11:43:13.775','2016-02-20 11:43:13.775'),(2,0,214,900,0,'Blocked','2016-02-20 11:43:36.654','2016-02-20 11:43:36.654'),(3,35,218,900,0,'Blocked','2016-02-20 11:46:45.034','2016-02-20 11:46:45.034'),(4,35,222,900,0,'Blocked','2016-02-20 11:47:55.303','2016-02-20 11:47:55.303'),(5,35,223,900,0,'Blocked','2016-02-20 11:49:56.213','2016-02-20 11:49:56.213'),(6,35,239,5000,0,'Blocked','2016-02-20 12:42:48.859','2016-02-20 12:42:48.859'),(7,35,240,5000,10954.965,'Deducted','2016-02-20 16:14:42.783','2016-02-20 16:14:44.637'),(8,35,244,10330,8763.972,'Deducted','2016-02-20 16:19:25.098','2016-02-20 16:19:25.968'),(9,35,245,10330,6572.978999999999,'Deducted','2016-02-20 16:20:30.801','2016-02-20 16:20:31.170'),(10,35,249,10330,10954.965,'Deducted','2016-02-20 16:22:35.054','2016-02-20 16:22:35.408'),(11,35,250,10330,8679.132,'Deducted','2016-02-20 16:23:08.204','2016-02-20 16:23:08.583'),(12,35,286,10230,8679.132,'Deducted','2016-02-26 08:56:34.281','2016-02-26 08:56:35.234'),(13,35,290,10230,8763.972,'Deducted','2016-02-26 09:01:51.793','2016-02-26 09:01:52.620'),(14,35,294,10330,6509.349,'Deducted','2016-02-26 09:05:46.719','2016-02-26 09:05:47.573'),(15,35,298,10230,6509.349,'Deducted','2016-02-26 09:08:50.556','2016-02-26 09:08:51.310'),(16,35,302,10230,6572.978999999999,'Deducted','2016-02-26 09:13:42.266','2016-02-26 09:13:42.794'),(17,35,303,10330,4381.986,'Deducted','2016-02-26 09:14:54.296','2016-02-26 09:14:54.717'),(18,35,331,10330,2169.783,'Deducted','2016-04-30 08:15:37.214','2016-04-30 08:15:38.631'),(19,35,332,10230,2169.783,'Deducted','2016-04-30 09:24:43.690','2016-04-30 09:24:44.545');
/*!40000 ALTER TABLE `block_amounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contests`
--

DROP TABLE IF EXISTS `contests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contests` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` text COLLATE utf8_unicode_ci,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  `auto_inc_days` tinyint(1) DEFAULT NULL,
  `state` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `current_day` int(11) DEFAULT NULL,
  `day_started_at` datetime DEFAULT NULL,
  `currentTradeDate` date DEFAULT NULL,
  `startDate` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contests`
--

LOCK TABLES `contests` WRITE;
/*!40000 ALTER TABLE `contests` DISABLE KEYS */;
/*!40000 ALTER TABLE `contests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `credit_rating` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bank` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `account_number` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (51,'Bernhard, Wuckert and Schmeler','BB','BONY','7651883','2016-02-07 06:50:10','2016-02-07 06:50:10'),(52,'Carroll, Nolan and Hackett','A','BONY','1938866','2016-02-07 06:50:10','2016-02-07 06:50:10'),(53,'Hand-Ryan','A','BONY','7541766','2016-02-07 06:50:10','2016-02-07 06:50:10'),(54,'Ruecker, Harber and Wyman','Junk','BONY','5453060','2016-02-07 06:50:10','2016-02-07 06:50:10'),(55,'Hoeger-Lemke','B','BONY','2586344','2016-02-07 06:50:10','2016-02-07 06:50:10'),(56,'Cronin Group','B','BONY','6679011','2016-02-07 06:50:10','2016-02-07 06:50:10'),(57,'Reichel, Hills and Trantow','B','BONY','4027463','2016-02-07 06:50:10','2016-02-07 06:50:10'),(58,'Sauer and Sons','AAA','BONY','7173756','2016-02-07 06:50:10','2016-02-07 06:50:10'),(59,'Ruecker, Dibbert and Mayert','BB','BONY','1857405','2016-02-07 06:50:10','2016-02-07 06:50:10'),(60,'Johns-Hermann','AA','BONY','7400910','2016-02-07 06:50:10','2016-02-07 06:50:10'),(61,'Ledner-Grimes','B','BONY','3002902','2016-02-07 06:50:10','2016-02-07 06:50:10'),(62,'Schmeler-Rohan','C','BONY','5938424','2016-02-07 06:50:10','2016-02-07 06:50:10'),(63,'Bechtelar, Simonis and Hahn','B','BONY','9281809','2016-02-07 06:50:10','2016-02-07 06:50:10'),(64,'Cremin LLC','A','BONY','5536555','2016-02-07 06:50:11','2016-02-07 06:50:11'),(65,'Swaniawski-Runolfsson','BB','BONY','3747131','2016-02-07 06:50:11','2016-02-07 06:50:11'),(66,'Sporer, Dicki and Conroy','AAA','BONY','5494955','2016-02-07 06:50:11','2016-02-07 06:50:11'),(67,'Durgan, Roob and Wintheiser','Junk','BONY','1930460','2016-02-07 06:50:11','2016-02-07 06:50:11'),(68,'Will-Casper','A','BONY','1671438','2016-02-07 06:50:11','2016-02-07 06:50:11'),(69,'Muller LLC','A','BONY','6208391','2016-02-07 06:50:11','2016-02-07 06:50:11'),(70,'Goldner and Sons','Junk','BONY','7063649','2016-02-07 06:50:11','2016-02-07 06:50:11'),(71,'Gorczany, Prosacco and Schamberger','BBB','BONY','4677349','2016-02-07 06:50:11','2016-02-07 06:50:11'),(72,'Wyman, Collins and Miller','BBB','BONY','3215640','2016-02-07 06:50:11','2016-02-07 06:50:11'),(73,'Bradtke-Smith','A','BONY','9093509','2016-02-07 06:50:11','2016-02-07 06:50:11'),(74,'Cartwright-Hoeger','A','BONY','8356141','2016-02-07 06:50:11','2016-02-07 06:50:11'),(75,'Crona Inc','BBB','BONY','9018084','2016-02-07 06:50:11','2016-02-07 06:50:11'),(76,'Crooks Inc','B','BONY','9273933','2016-02-07 06:50:11','2016-02-07 06:50:11'),(77,'Leffler and Sons','Junk','BONY','9517095','2016-02-07 06:50:11','2016-02-07 06:50:11'),(78,'Bruen, Kozey and Welch','A','BONY','8042623','2016-02-07 06:50:11','2016-02-07 06:50:11'),(79,'Bode, Watsica and Kuhn','AA','BONY','8230606','2016-02-07 06:50:11','2016-02-07 06:50:11'),(80,'Mosciski, Kassulke and Bruen','Junk','BONY','2289617','2016-02-07 06:50:11','2016-02-07 06:50:11'),(81,'Deckow, Cormier and Lindgren','AAA','BONY','8420455','2016-02-07 06:50:12','2016-02-07 06:50:12'),(82,'Glover and Sons','B','BONY','9382521','2016-02-07 06:50:12','2016-02-07 06:50:12'),(83,'Kutch-Mayer','AAA','BONY','1690614','2016-02-07 06:50:12','2016-02-07 06:50:12'),(84,'Dach-Cummerata','B','BONY','8900205','2016-02-07 06:50:12','2016-02-07 06:50:12'),(85,'Doyle-Champlin','Junk','BONY','1194286','2016-02-07 06:50:12','2016-02-07 06:50:12'),(86,'White Inc','AAA','BONY','9431600','2016-02-07 06:50:12','2016-02-07 06:50:12'),(87,'Hamill-Satterfield','BB','BONY','4355822','2016-02-07 06:50:12','2016-02-07 06:50:12'),(88,'Friesen-Yost','Junk','BONY','6963091','2016-02-07 06:50:12','2016-02-07 06:50:12'),(89,'Beahan-Bode','B','BONY','4031831','2016-02-07 06:50:12','2016-02-07 06:50:12'),(90,'Powlowski-Crist','BBB','BONY','4350569','2016-02-07 06:50:12','2016-02-07 06:50:12'),(91,'Koch, VonRueden and Marvin','AA','BONY','7618158','2016-02-07 06:50:12','2016-02-07 06:50:12'),(92,'Grady, Haag and Dibbert','C','BONY','7288282','2016-02-07 06:50:12','2016-02-07 06:50:12'),(93,'Schoen-Pfeffer','BBB','BONY','6626787','2016-02-07 06:50:12','2016-02-07 06:50:12'),(94,'Okuneva-Bahringer','C','BONY','7509863','2016-02-07 06:50:12','2016-02-07 06:50:12'),(95,'Cormier, Bogan and Orn','AA','BONY','4802879','2016-02-07 06:50:12','2016-02-07 06:50:12'),(96,'Bernhard Inc','BB','BONY','2488902','2016-02-07 06:50:12','2016-02-07 06:50:12'),(97,'Crooks-Daniel','AAA','BONY','1689338','2016-02-07 06:50:12','2016-02-07 06:50:12'),(98,'Ward, Schowalter and Effertz','BBB','BONY','3390952','2016-02-07 06:50:12','2016-02-07 06:50:12'),(99,'Ondricka, Wolff and Wunsch','AAA','BONY','1895286','2016-02-07 06:50:12','2016-02-07 06:50:12'),(100,'Fadel Inc','AA','BONY','4448990','2016-02-07 06:50:12','2016-02-07 06:50:12');
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `delayed_jobs`
--

DROP TABLE IF EXISTS `delayed_jobs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `delayed_jobs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `priority` int(11) NOT NULL DEFAULT '0',
  `attempts` int(11) NOT NULL DEFAULT '0',
  `handler` text COLLATE utf8_unicode_ci NOT NULL,
  `last_error` text COLLATE utf8_unicode_ci,
  `run_at` datetime DEFAULT NULL,
  `locked_at` datetime DEFAULT NULL,
  `failed_at` datetime DEFAULT NULL,
  `locked_by` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `queue` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `delayed_jobs_priority` (`priority`,`run_at`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `delayed_jobs`
--

LOCK TABLES `delayed_jobs` WRITE;
/*!40000 ALTER TABLE `delayed_jobs` DISABLE KEYS */;
/*!40000 ALTER TABLE `delayed_jobs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exception_logger_logged_exceptions`
--

DROP TABLE IF EXISTS `exception_logger_logged_exceptions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exception_logger_logged_exceptions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `exception_class` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `controller_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `action_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `message` text COLLATE utf8_unicode_ci,
  `backtrace` text COLLATE utf8_unicode_ci,
  `environment` text COLLATE utf8_unicode_ci,
  `request` text COLLATE utf8_unicode_ci,
  `created_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exception_logger_logged_exceptions`
--

LOCK TABLES `exception_logger_logged_exceptions` WRITE;
/*!40000 ALTER TABLE `exception_logger_logged_exceptions` DISABLE KEYS */;
/*!40000 ALTER TABLE `exception_logger_logged_exceptions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `market_events`
--

DROP TABLE IF EXISTS `market_events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `market_events` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text COLLATE utf8_unicode_ci NOT NULL,
  `event_type` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `summary` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `description` text COLLATE utf8_unicode_ci,
  `direction` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  `intensity` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `asset_class` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `region` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sector` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ticker` varchar(5) COLLATE utf8_unicode_ci DEFAULT NULL,
  `external_url` text COLLATE utf8_unicode_ci,
  `created_at` datetime(3) DEFAULT NULL,
  `updated_at` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `market_events`
--

LOCK TABLES `market_events` WRITE;
/*!40000 ALTER TABLE `market_events` DISABLE KEYS */;
INSERT INTO `market_events` VALUES (1,'Event1','Market','Trigger Test',NULL,'Down','Med','Bond','APAC','Tech','EBB',NULL,'2016-02-09 19:41:57.000','2016-02-09 19:41:57.000'),(2,'Test','Market','This is a test',NULL,'Up','Med','Derivative','EMEA','Pharma',NULL,NULL,'2016-02-13 17:16:57.854','2016-02-13 17:16:57.854'),(3,'Test','Non Market','This is a test',NULL,'Down','High','Stock','APAC','Auto',NULL,NULL,'2016-02-13 17:21:11.509','2016-02-13 17:21:11.509'),(4,'Test','Non Market','This is a test',NULL,'Down','High','Stock','EMEA','Finance',NULL,NULL,'2016-02-13 17:22:08.076','2016-02-13 17:22:08.076'),(5,'Test','Non Market','This is a test',NULL,'Down','Med','Bond','NA','Pharma',NULL,NULL,'2016-02-13 17:45:41.845','2016-02-13 17:45:41.845');
/*!40000 ALTER TABLE `market_events` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `exchange` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `buy_sell` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  `order_type` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `security_id` bigint(20) NOT NULL,
  `quantity` double NOT NULL,
  `unfilled_qty` double NOT NULL,
  `price` double NOT NULL,
  `pre_trade_check_status` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `trade_status` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `status` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `created_at` datetime(3) DEFAULT NULL,
  `updated_at` datetime(3) DEFAULT NULL,
  `ticker` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=333 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (246,'NASDAQ','SELL','LIMIT',1,100,120,0,103.3,'','Filled','','2016-02-20 16:22:18.121','2016-02-20 16:23:08.293','Tick'),(247,'NASDAQ','SELL','LIMIT',1,100,100,0,102.3,'','Filled','','2016-02-20 16:22:17.911','2016-02-26 08:56:34.677','Tick'),(248,'NYSE','BUY','LIMIT',1,100,150,150,98.3,'','','','2016-02-20 16:22:18.128','2016-02-20 16:22:18.128','Tick'),(249,'NYSE','BUY','MARKET',35,100,100,0,0,'BlockAmountSuccess','Filled','','2016-02-20 16:22:34.782','2016-02-20 16:22:35.317','EBB'),(250,'NYSE','BUY','MARKET',35,100,100,0,0,'BlockAmountSuccess','Filled','','2016-02-20 16:23:08.084','2016-02-20 16:23:08.510','EBB'),(251,'NASDAQ','SELL','LIMIT',1,100,100,0,102.3,'','Filled','','2016-02-21 08:33:42.808','2016-02-26 09:01:52.300','Tick'),(252,'NYSE','BUY','LIMIT',1,100,150,150,98.3,'','','','2016-02-21 08:33:43.168','2016-02-21 08:33:43.168','Tick'),(253,'NASDAQ','SELL','LIMIT',1,100,120,0,103.3,'','Filled','','2016-02-21 08:36:17.292','2016-02-26 09:05:47.169','Tick'),(254,'NASDAQ','SELL','LIMIT',1,100,100,0,102.3,'','Filled','','2016-02-21 08:36:16.902','2016-02-26 09:08:50.989','Tick'),(255,'NYSE','BUY','LIMIT',1,100,150,150,98.3,'','','','2016-02-21 08:36:17.293','2016-02-21 08:36:17.293','Tick'),(256,'NASDAQ','SELL','LIMIT',1,100,100,0,102.3,'','Filled','','2016-02-21 10:04:54.146','2016-02-26 09:13:42.605','Tick'),(257,'NASDAQ','SELL','LIMIT',1,100,120,0,103.3,'','Filled','','2016-02-21 10:04:54.499','2016-02-26 09:14:54.537','Tick'),(258,'NYSE','BUY','LIMIT',1,100,150,150,98.3,'','','','2016-02-21 10:04:54.500','2016-02-21 10:04:54.500','Tick'),(259,'NASDAQ','SELL','LIMIT',1,100,120,0,103.3,'','Filled','','2016-02-21 10:08:05.036','2016-04-30 08:15:37.924','Tick'),(260,'NYSE','BUY','LIMIT',1,100,150,150,98.3,'','','','2016-02-21 10:08:05.038','2016-02-21 10:08:05.038','Tick'),(261,'NASDAQ','SELL','LIMIT',1,100,100,0,102.3,'','Filled','','2016-02-21 10:08:04.789','2016-04-30 09:24:44.102','Tick'),(262,'NASDAQ','SELL','LIMIT',1,100,100,80,102.3,'','Partially Filled','','2016-02-21 10:09:27.642','2016-04-30 09:24:44.470','Tick'),(263,'NYSE','BUY','LIMIT',1,100,150,150,98.3,'','','','2016-02-21 10:09:27.935','2016-02-21 10:09:27.935','Tick'),(264,'NASDAQ','SELL','LIMIT',1,100,120,120,103.3,'','','','2016-02-21 10:09:27.933','2016-02-21 10:09:27.933','Tick'),(265,'NASDAQ','SELL','LIMIT',1,100,120,120,103.3,'','','','2016-02-21 10:11:07.469','2016-02-21 10:11:07.469','Tick'),(266,'NYSE','BUY','LIMIT',1,100,150,150,98.3,'','','','2016-02-21 10:11:07.479','2016-02-21 10:11:07.479','Tick'),(267,'NASDAQ','SELL','LIMIT',1,100,100,100,102.3,'','','','2016-02-21 10:11:07.201','2016-02-21 10:11:07.201','Tick'),(268,'NYSE','BUY','LIMIT',1,100,150,150,98.3,'','','','2016-02-21 10:46:32.705','2016-02-21 10:46:32.705','Tick'),(269,'NASDAQ','SELL','LIMIT',1,100,120,120,103.3,'','','','2016-02-21 10:46:32.701','2016-02-21 10:46:32.701','Tick'),(270,'NASDAQ','SELL','LIMIT',1,100,100,100,102.3,'','','','2016-02-21 10:46:32.457','2016-02-21 10:46:32.457','Tick'),(271,'NYSE','BUY','LIMIT',1,100,150,150,98.3,'','','','2016-02-21 11:05:15.725','2016-02-21 11:05:15.725','Tick'),(272,'NASDAQ','SELL','LIMIT',1,100,120,120,103.3,'','','','2016-02-21 11:05:15.723','2016-02-21 11:05:15.723','Tick'),(273,'NASDAQ','SELL','LIMIT',1,100,100,100,102.3,'','','','2016-02-21 11:05:15.467','2016-02-21 11:05:15.467','Tick'),(274,'NASDAQ','SELL','LIMIT',1,100,120,120,103.3,'','','','2016-02-21 11:38:39.696','2016-02-21 11:38:39.696','Tick'),(275,'NYSE','BUY','LIMIT',1,100,150,150,98.3,'','','','2016-02-21 11:38:39.697','2016-02-21 11:38:39.697','Tick'),(276,'NASDAQ','SELL','LIMIT',1,100,100,100,102.3,'','','','2016-02-21 11:38:39.475','2016-02-21 11:38:39.475','Tick'),(277,'NASDAQ','SELL','LIMIT',1,100,100,100,102.3,'','','','2016-02-21 16:27:12.807','2016-02-21 16:27:12.807','Tick'),(278,'NYSE','BUY','LIMIT',1,100,150,150,98.3,'','','','2016-02-21 16:27:13.210','2016-02-21 16:27:13.210','Tick'),(279,'NASDAQ','SELL','LIMIT',1,100,120,120,103.3,'','','','2016-02-21 16:27:13.209','2016-02-21 16:27:13.209','Tick'),(280,'NASDAQ','SELL','LIMIT',1,100,120,120,103.3,'','','','2016-02-24 09:24:04.475','2016-02-24 09:24:04.475','Tick'),(281,'NASDAQ','SELL','LIMIT',1,100,100,100,102.3,'','','','2016-02-24 09:24:04.012','2016-02-24 09:24:04.012','Tick'),(282,'NYSE','BUY','LIMIT',1,100,150,150,98.3,'','','','2016-02-24 09:24:04.476','2016-02-24 09:24:04.476','Tick'),(283,'NASDAQ','SELL','LIMIT',1,100,100,100,102.3,'','','','2016-02-26 08:55:39.307','2016-02-26 08:55:39.307','Tick'),(284,'NASDAQ','SELL','LIMIT',1,100,120,120,103.3,'','','','2016-02-26 08:55:39.637','2016-02-26 08:55:39.637','Tick'),(285,'NYSE','BUY','LIMIT',1,100,150,150,98.3,'','','','2016-02-26 08:55:39.639','2016-02-26 08:55:39.639','Tick'),(286,'NYSE','BUY','MARKET',35,100,100,0,0,'BlockAmountSuccess','Filled','','2016-02-26 08:56:33.747','2016-02-26 08:56:34.861','EBB'),(287,'NASDAQ','SELL','LIMIT',1,100,120,120,103.3,'','','','2016-02-26 09:01:32.224','2016-02-26 09:01:32.224','Tick'),(288,'NASDAQ','SELL','LIMIT',1,100,100,100,102.3,'','','','2016-02-26 09:01:31.964','2016-02-26 09:01:31.964','Tick'),(289,'NYSE','BUY','LIMIT',1,100,150,150,98.3,'','','','2016-02-26 09:01:32.227','2016-02-26 09:01:32.227','Tick'),(290,'NYSE','BUY','MARKET',35,100,100,0,0,'BlockAmountSuccess','Filled','','2016-02-26 09:01:51.456','2016-02-26 09:01:52.511','EBB'),(291,'NASDAQ','SELL','LIMIT',1,100,120,120,103.3,'','','','2016-02-26 09:05:29.109','2016-02-26 09:05:29.109','Tick'),(292,'NYSE','BUY','LIMIT',1,100,150,150,98.3,'','','','2016-02-26 09:05:29.110','2016-02-26 09:05:29.110','Tick'),(293,'NASDAQ','SELL','LIMIT',1,100,100,100,102.3,'','','','2016-02-26 09:05:28.897','2016-02-26 09:05:28.897','Tick'),(294,'NYSE','BUY','MARKET',35,100,100,0,0,'BlockAmountSuccess','Filled','','2016-02-26 09:05:46.325','2016-02-26 09:05:47.399','EBB'),(295,'NASDAQ','SELL','LIMIT',1,100,120,120,103.3,'','','','2016-02-26 09:08:33.365','2016-02-26 09:08:33.365','Tick'),(296,'NASDAQ','SELL','LIMIT',1,100,100,100,102.3,'','','','2016-02-26 09:08:33.164','2016-02-26 09:08:33.164','Tick'),(297,'NYSE','BUY','LIMIT',1,100,150,150,98.3,'','','','2016-02-26 09:08:33.366','2016-02-26 09:08:33.366','Tick'),(298,'NYSE','BUY','MARKET',35,100,100,0,0,'BlockAmountSuccess','Filled','','2016-02-26 09:08:50.259','2016-02-26 09:08:51.193','EBB'),(299,'NASDAQ','SELL','LIMIT',1,100,120,120,103.3,'','','','2016-02-26 09:13:08.612','2016-02-26 09:13:08.612','Tick'),(300,'NYSE','BUY','LIMIT',1,100,150,150,98.3,'','','','2016-02-26 09:13:08.613','2016-02-26 09:13:08.613','Tick'),(301,'NASDAQ','SELL','LIMIT',1,100,100,100,102.3,'','','','2016-02-26 09:13:08.396','2016-02-26 09:13:08.396','Tick'),(302,'NYSE','BUY','MARKET',35,100,100,0,0,'BlockAmountSuccess','Filled','','2016-02-26 09:13:42.052','2016-02-26 09:13:42.755','EBB'),(303,'NYSE','BUY','MARKET',35,100,100,0,0,'BlockAmountSuccess','Filled','','2016-02-26 09:14:54.186','2016-02-26 09:14:54.654','EBB'),(304,'NASDAQ','SELL','LIMIT',1,100,120,120,103.3,'','','','2016-02-28 17:51:52.639','2016-02-28 17:51:52.639','Tick'),(305,'NASDAQ','SELL','LIMIT',1,100,100,100,102.3,'','','','2016-02-28 17:51:52.253','2016-02-28 17:51:52.253','Tick'),(306,'NYSE','BUY','LIMIT',1,100,150,150,98.3,'','','','2016-02-28 17:51:52.640','2016-02-28 17:51:52.640','Tick'),(307,'NASDAQ','SELL','LIMIT',1,100,120,120,103.3,'','','','2016-02-28 18:10:23.008','2016-02-28 18:10:23.008','Tick'),(308,'NYSE','BUY','LIMIT',1,100,150,150,98.3,'','','','2016-02-28 18:10:23.010','2016-02-28 18:10:23.010','Tick'),(309,'NASDAQ','SELL','LIMIT',1,100,100,100,102.3,'','','','2016-02-28 18:10:22.782','2016-02-28 18:10:22.782','Tick'),(310,'NASDAQ','SELL','LIMIT',1,100,100,100,102.3,'','','','2016-02-28 18:32:56.270','2016-02-28 18:32:56.270','Tick'),(311,'NASDAQ','SELL','LIMIT',1,100,120,120,103.3,'','','','2016-02-28 18:32:56.528','2016-02-28 18:32:56.528','Tick'),(312,'NYSE','BUY','LIMIT',1,100,150,150,98.3,'','','','2016-02-28 18:32:56.532','2016-02-28 18:32:56.532','Tick'),(313,'NASDAQ','SELL','LIMIT',1,100,120,120,103.3,'','','','2016-02-28 18:36:15.622','2016-02-28 18:36:15.622','Tick'),(314,'NYSE','BUY','LIMIT',1,100,150,150,98.3,'','','','2016-02-28 18:36:15.624','2016-02-28 18:36:15.624','Tick'),(315,'NASDAQ','SELL','LIMIT',1,100,100,100,102.3,'','','','2016-02-28 18:36:15.414','2016-02-28 18:36:15.414','Tick'),(316,'NASDAQ','SELL','LIMIT',1,100,100,100,102.3,'','','','2016-03-01 08:27:50.320','2016-03-01 08:27:50.320','Tick'),(317,'NYSE','BUY','LIMIT',1,100,150,150,98.3,'','','','2016-03-01 08:27:50.760','2016-03-01 08:27:50.760','Tick'),(318,'NASDAQ','SELL','LIMIT',1,100,120,120,103.3,'','','','2016-03-01 08:27:50.758','2016-03-01 08:27:50.758','Tick'),(319,'NASDAQ','SELL','LIMIT',1,100,120,120,103.3,'','','','2016-03-01 20:59:20.877','2016-03-01 20:59:20.877','Tick'),(320,'NASDAQ','SELL','LIMIT',1,100,100,100,102.3,'','','','2016-03-01 20:59:20.671','2016-03-01 20:59:20.671','Tick'),(321,'NYSE','BUY','LIMIT',1,100,150,150,98.3,'','','','2016-03-01 20:59:20.885','2016-03-01 20:59:20.885','Tick'),(322,'NASDAQ','SELL','LIMIT',1,100,100,100,102.3,'','','','2016-03-01 21:00:47.688','2016-03-01 21:00:47.688','Tick'),(323,'NASDAQ','SELL','LIMIT',1,100,120,120,103.3,'','','','2016-03-01 21:00:47.906','2016-03-01 21:00:47.906','Tick'),(324,'NYSE','BUY','LIMIT',1,100,150,150,98.3,'','','','2016-03-01 21:00:47.908','2016-03-01 21:00:47.908','Tick'),(325,'NASDAQ','SELL','LIMIT',1,100,100,100,102.3,'','','','2016-03-01 21:02:38.262','2016-03-01 21:02:38.262','Tick'),(326,'NYSE','BUY','LIMIT',1,100,150,150,98.3,'','','','2016-03-01 21:02:38.525','2016-03-01 21:02:38.525','Tick'),(327,'NASDAQ','SELL','LIMIT',1,100,120,120,103.3,'','','','2016-03-01 21:02:38.517','2016-03-01 21:02:38.517','Tick'),(328,'NASDAQ','SELL','LIMIT',1,100,100,100,102.3,'','','','2016-04-30 08:12:11.763','2016-04-30 08:12:11.763','Tick'),(329,'NASDAQ','SELL','LIMIT',1,100,120,120,103.3,'','','','2016-04-30 08:12:12.177','2016-04-30 08:12:12.177','Tick'),(330,'NYSE','BUY','LIMIT',1,100,150,150,98.3,'','','','2016-04-30 08:12:12.178','2016-04-30 08:12:12.178','Tick'),(331,'NYSE','BUY','MARKET',35,100,100,0,0,'BlockAmountSuccess','Filled','','2016-04-30 08:15:36.628','2016-04-30 08:15:38.304','EBB'),(332,'NYSE','BUY','MARKET',35,100,100,0,0,'BlockAmountSuccess','Filled','','2016-04-30 09:24:43.601','2016-04-30 09:24:44.470','EBB');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `positions`
--

DROP TABLE IF EXISTS `positions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `positions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ticker` text COLLATE utf8_unicode_ci NOT NULL,
  `security_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `quantity` double NOT NULL,
  `average_price` double NOT NULL,
  `pnl` double NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `POS_IDX` (`security_id`,`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `positions`
--

LOCK TABLES `positions` WRITE;
/*!40000 ALTER TABLE `positions` DISABLE KEYS */;
INSERT INTO `positions` VALUES (5,'EBB',100,1,1000,102.78,-480,'2016-02-20 16:22:35','2016-04-30 09:24:45'),(6,'EBB',100,35,1000,102.78,-480,'2016-02-20 16:22:36','2016-04-30 09:24:45');
/*!40000 ALTER TABLE `positions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schema_migrations`
--

DROP TABLE IF EXISTS `schema_migrations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schema_migrations` (
  `version` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  UNIQUE KEY `unique_schema_migrations` (`version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schema_migrations`
--

LOCK TABLES `schema_migrations` WRITE;
/*!40000 ALTER TABLE `schema_migrations` DISABLE KEYS */;
INSERT INTO `schema_migrations` VALUES ('20151102024837'),('20160131040117');
/*!40000 ALTER TABLE `schema_migrations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `securities`
--

DROP TABLE IF EXISTS `securities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `securities` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ticker` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `price` float DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `asset_class` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sector` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `region` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tick_size` int(11) DEFAULT NULL,
  `liquidity` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `securities`
--

LOCK TABLES `securities` WRITE;
/*!40000 ALTER TABLE `securities` DISABLE KEYS */;
INSERT INTO `securities` VALUES (51,'Littel-Leuschke','IZI','Cloned interactive knowledge user',16,'2016-02-07 06:50:07','2016-02-07 06:50:07','Derivative','Technology','US',5,'None'),(52,'Weissnat-McLaughlin','TXG','Intuitive mobile function',25,'2016-02-07 06:50:07','2016-02-07 06:50:07','Stock','Auto','EMEA',8,'Medium'),(53,'Gibson Group','ILH','Implemented contextually-based concept',100,'2016-02-07 06:50:07','2016-02-07 06:50:07','Stock','Manufacturing','EMEA',9,'High'),(54,'Koelpin LLC','EUM','Profound asymmetric orchestration',4,'2016-02-07 06:50:07','2016-02-07 06:50:07','Bond','Auto','APAC',6,'Medium'),(55,'Little LLC','BXH','Grass-roots 24/7 policy',1,'2016-02-07 06:50:07','2016-02-07 06:50:07','Derivative','Construction','EU',1,'Very High'),(56,'Kemmer-Nitzsche','UYT','Synergized maximized focus group',81,'2016-02-07 06:50:07','2016-02-07 06:50:07','Stock','Manufacturing','EMEA',8,'Medium'),(57,'Ratke LLC','UEY','Self-enabling eco-centric info-mediaries',100,'2016-02-07 06:50:07','2016-02-07 06:50:07','Derivative','Construction','APAC',7,'High'),(58,'Dickinson-Dicki','STS','Optimized zero administration extranet',49,'2016-02-07 06:50:08','2016-02-07 06:50:08','Stock','Technology','APAC',5,'Very High'),(59,'Sanford, Kertzmann and Bartell','QIB','Vision-oriented asymmetric focus group',9,'2016-02-07 06:50:08','2016-02-07 06:50:08','Bond','Pharma','APAC',10,'None'),(60,'Schamberger Inc','BUO','Quality-focused client-driven frame',1,'2016-02-07 06:50:08','2016-02-07 06:50:08','Derivative','Pharma','EMEA',3,'Very High'),(61,'Herman, Miller and Beer','AMM','Cross-platform encompassing interface',25,'2016-02-07 06:50:08','2016-02-07 06:50:08','Stock','Construction','US',1,'High'),(62,'Ernser-Muller','SJE','Re-contextualized uniform info-mediaries',4,'2016-02-07 06:50:08','2016-02-07 06:50:08','Stock','Manufacturing','APAC',1,'Very High'),(63,'Gerlach-Pfannerstill','CFC','Customer-focused contextually-based moratorium',64,'2016-02-07 06:50:08','2016-02-07 06:50:08','Derivative','Auto','EU',6,'Medium'),(64,'Volkman Group','RCG','Streamlined tertiary time-frame',4,'2016-02-07 06:50:08','2016-02-07 06:50:08','Bond','Technology','EMEA',5,'Medium'),(65,'Turner and Sons','MXA','Versatile analyzing ability',100,'2016-02-07 06:50:08','2016-02-07 06:50:08','Stock','Construction','EMEA',5,'Very High'),(66,'Batz Inc','CKI','Function-based intangible workforce',81,'2016-02-07 06:50:08','2016-02-07 06:50:08','Stock','Construction','EMEA',7,'Low'),(67,'Gerlach, Rutherford and Rau','MWI','Down-sized context-sensitive focus group',25,'2016-02-07 06:50:08','2016-02-07 06:50:08','Stock','Pharma','EMEA',10,'None'),(68,'Sipes-Anderson','NZW','Team-oriented 24/7 project',49,'2016-02-07 06:50:08','2016-02-07 06:50:08','Bond','Auto','APAC',8,'Very High'),(69,'Gottlieb-Bahringer','PSZ','Total encompassing secured line',9,'2016-02-07 06:50:08','2016-02-07 06:50:08','Bond','Manufacturing','APAC',2,'Very High'),(70,'Zemlak-Dach','CJP','Vision-oriented bifurcated implementation',9,'2016-02-07 06:50:08','2016-02-07 06:50:08','Derivative','Manufacturing','EU',5,'Very High'),(71,'Schuppe, Lind and Gottlieb','PSZ','Profound context-sensitive challenge',36,'2016-02-07 06:50:08','2016-02-07 06:50:08','Derivative','Manufacturing','APAC',10,'None'),(72,'Harris-Bashirian','PVY','Sharable value-added time-frame',16,'2016-02-07 06:50:08','2016-02-07 06:50:08','Bond','Construction','US',8,'Very High'),(73,'Hermiston-Bashirian','ZHA','Progressive system-worthy knowledge base',9,'2016-02-07 06:50:08','2016-02-07 06:50:08','Derivative','Technology','APAC',8,'High'),(74,'Runolfsson, Hartmann and Rodriguez','JAM','Open-architected actuating local area network',100,'2016-02-07 06:50:08','2016-02-07 06:50:08','Derivative','Auto','APAC',8,'Medium'),(75,'Sanford Group','KMA','Synchronised high-level neural-net',16,'2016-02-07 06:50:08','2016-02-07 06:50:08','Derivative','Technology','EU',5,'High'),(76,'Stark-Robel','RDU','Visionary responsive analyzer',100,'2016-02-07 06:50:09','2016-02-07 06:50:09','Stock','Technology','EU',3,'Low'),(77,'Stroman LLC','CPT','Cross-platform optimal moderator',25,'2016-02-07 06:50:09','2016-02-07 06:50:09','Stock','Construction','EMEA',1,'Medium'),(78,'Carroll LLC','UIB','Inverse contextually-based open architecture',25,'2016-02-07 06:50:09','2016-02-07 06:50:09','Stock','Manufacturing','EMEA',6,'Very High'),(79,'Breitenberg-Graham','BPE','Phased attitude-oriented analyzer',9,'2016-02-07 06:50:09','2016-02-07 06:50:09','Bond','Pharma','APAC',1,'High'),(80,'Treutel Group','EPD','Organized value-added extranet',36,'2016-02-07 06:50:09','2016-02-07 06:50:09','Derivative','Auto','EMEA',5,'None'),(81,'Boyer-Padberg','UEP','Universal static initiative',9,'2016-02-07 06:50:09','2016-02-07 06:50:09','Bond','Manufacturing','EU',4,'High'),(82,'Aufderhar-Haag','QPA','Configurable actuating middleware',25,'2016-02-07 06:50:09','2016-02-07 06:50:09','Stock','Construction','US',8,'Medium'),(83,'Turcotte-Sawayn','UOE','Digitized systemic firmware',16,'2016-02-07 06:50:09','2016-02-07 06:50:09','Bond','Pharma','US',1,'Very High'),(84,'Gleichner Group','EVI','Fully-configurable mission-critical encryption',36,'2016-02-07 06:50:09','2016-02-07 06:50:09','Derivative','Pharma','APAC',4,'Very High'),(85,'Altenwerth Inc','RHQ','Visionary secondary firmware',25,'2016-02-07 06:50:09','2016-02-07 06:50:09','Derivative','Pharma','APAC',6,'None'),(86,'Connelly, Torphy and Schoen','XJY','Advanced directional framework',25,'2016-02-07 06:50:09','2016-02-07 06:50:09','Derivative','Construction','US',4,'Low'),(87,'Botsford-Beier','QSH','Team-oriented dedicated moderator',25,'2016-02-07 06:50:09','2016-02-07 06:50:09','Bond','Auto','US',6,'None'),(88,'Heidenreich, Bailey and Schiller','ZLM','Synergistic solution-oriented architecture',64,'2016-02-07 06:50:09','2016-02-07 06:50:09','Bond','Technology','EU',7,'Very High'),(89,'Zemlak and Sons','NWH','Robust client-server encoding',64,'2016-02-07 06:50:09','2016-02-07 06:50:09','Derivative','Manufacturing','EMEA',1,'None'),(90,'Goldner LLC','NIU','Exclusive zero administration extranet',1,'2016-02-07 06:50:09','2016-02-07 06:50:09','Derivative','Auto','EMEA',10,'Medium'),(91,'Dicki, Kuhlman and Gibson','QRN','Realigned high-level core',9,'2016-02-07 06:50:09','2016-02-07 06:50:09','Stock','Manufacturing','APAC',3,'Very High'),(92,'Schoen, Moore and Cremin','DLI','Configurable 6th generation middleware',81,'2016-02-07 06:50:09','2016-02-07 06:50:09','Bond','Pharma','APAC',3,'Very High'),(93,'Hagenes, Paucek and Kshlerin','PMC','Vision-oriented zero tolerance paradigm',25,'2016-02-07 06:50:09','2016-02-07 06:50:09','Bond','Technology','EMEA',1,'Very High'),(94,'Jones, Harris and Johnson','XGH','Seamless even-keeled hierarchy',25,'2016-02-07 06:50:09','2016-02-07 06:50:09','Bond','Manufacturing','APAC',10,'High'),(95,'Cremin-Tromp','OEE','Centralized exuding synergy',9,'2016-02-07 06:50:10','2016-02-07 06:50:10','Stock','Manufacturing','US',4,'Low'),(96,'Kohler, Shields and Bogan','LZC','Sharable static intranet',9,'2016-02-07 06:50:10','2016-02-07 06:50:10','Stock','Pharma','US',8,'High'),(97,'Schultz, Crist and Becker','LHJ','Business-focused zero administration software',9,'2016-02-07 06:50:10','2016-02-07 06:50:10','Stock','Construction','EMEA',3,'Low'),(98,'Bailey, Collins and Casper','HVI','Polarised responsive system engine',4,'2016-02-07 06:50:10','2016-02-07 06:50:10','Derivative','Pharma','EMEA',6,'High'),(99,'Deckow and Sons','LKQ','Programmable transitional process improvement',64,'2016-02-07 06:50:10','2016-02-07 06:50:10','Bond','Pharma','EU',4,'Medium'),(100,'Wisoky-Maggio','EBB','Seamless non-volatile encryption',102.3,'2016-02-07 06:50:10','2016-02-07 06:50:10','Stock','Construction','US',8,'High');
/*!40000 ALTER TABLE `securities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teams`
--

DROP TABLE IF EXISTS `teams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teams` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` text COLLATE utf8_unicode_ci,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `contest_id` int(11) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teams`
--

LOCK TABLES `teams` WRITE;
/*!40000 ALTER TABLE `teams` DISABLE KEYS */;
/*!40000 ALTER TABLE `teams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trades`
--

DROP TABLE IF EXISTS `trades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trades` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `trade_date` date NOT NULL,
  `settlement_date` date NOT NULL,
  `security_id` bigint(20) NOT NULL,
  `quantity` double NOT NULL,
  `price` double NOT NULL,
  `buy_sell` text COLLATE utf8_unicode_ci NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `order_id` bigint(20) NOT NULL,
  `matched_order_id` bigint(20) NOT NULL,
  `state` text COLLATE utf8_unicode_ci NOT NULL,
  `exchange` text COLLATE utf8_unicode_ci NOT NULL,
  `commissions` double NOT NULL,
  `taxes` double NOT NULL,
  `total_amount` double NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ticker` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trades`
--

LOCK TABLES `trades` WRITE;
/*!40000 ALTER TABLE `trades` DISABLE KEYS */;
INSERT INTO `trades` VALUES (11,'2016-02-20','2016-02-20',100,100,103.3,'SELL',1,246,249,'Active','NASDAQ',206.6,1053.66,11590.26,'2016-02-20 10:52:35','2016-02-20 10:52:35','EBB'),(12,'2016-02-20','2016-02-20',100,100,103.3,'BUY',35,249,246,'Active','NYSE',103.3,521.665,10954.965,'2016-02-20 10:52:35','2016-02-20 10:52:35','EBB'),(13,'2016-02-20','2016-02-20',100,20,103.3,'SELL',1,246,250,'Active','NASDAQ',41.32,210.732,2318.052,'2016-02-20 10:53:08','2016-02-20 10:53:08','EBB'),(14,'2016-02-20','2016-02-20',100,20,103.3,'BUY',35,250,246,'Active','NYSE',20.66,104.333,2190.993,'2016-02-20 10:53:08','2016-02-20 10:53:08','EBB'),(15,'2016-02-20','2016-02-20',100,80,102.3,'BUY',35,250,247,'Active','NYSE',81.84,413.292,8679.132,'2016-02-20 10:53:09','2016-02-20 10:53:09','EBB'),(16,'2016-02-20','2016-02-20',100,80,102.3,'SELL',1,247,250,'Active','NASDAQ',163.68,834.768,9182.448,'2016-02-20 10:53:09','2016-02-20 10:53:09','EBB'),(17,'2016-02-26','2016-02-26',100,20,102.3,'BUY',35,286,247,'Active','NYSE',20.46,103.323,2169.783,'2016-02-26 03:26:35','2016-02-26 03:26:35','EBB'),(18,'2016-02-26','2016-02-26',100,20,102.3,'SELL',1,247,286,'Active','NASDAQ',40.92,208.692,2295.612,'2016-02-26 03:26:35','2016-02-26 03:26:35','EBB'),(19,'2016-02-26','2016-02-26',100,80,102.3,'BUY',35,286,251,'Active','NYSE',81.84,413.292,8679.132,'2016-02-26 03:26:35','2016-02-26 03:26:35','EBB'),(20,'2016-02-26','2016-02-26',100,80,102.3,'SELL',1,251,286,'Active','NASDAQ',163.68,834.768,9182.448,'2016-02-26 03:26:35','2016-02-26 03:26:35','EBB'),(21,'2016-02-26','2016-02-26',100,20,102.3,'SELL',1,251,290,'Active','NASDAQ',40.92,208.692,2295.612,'2016-02-26 03:31:52','2016-02-26 03:31:52','EBB'),(22,'2016-02-26','2016-02-26',100,20,102.3,'BUY',35,290,251,'Active','NYSE',20.46,103.323,2169.783,'2016-02-26 03:31:52','2016-02-26 03:31:52','EBB'),(23,'2016-02-26','2016-02-26',100,80,103.3,'BUY',35,290,253,'Active','NYSE',82.64,417.332,8763.972,'2016-02-26 03:31:52','2016-02-26 03:31:52','EBB'),(24,'2016-02-26','2016-02-26',100,80,103.3,'SELL',1,253,290,'Active','NASDAQ',165.28,842.928,9272.208,'2016-02-26 03:31:53','2016-02-26 03:31:53','EBB'),(25,'2016-02-26','2016-02-26',100,40,103.3,'BUY',35,294,253,'Active','NYSE',41.32,208.666,4381.986,'2016-02-26 03:35:47','2016-02-26 03:35:47','EBB'),(26,'2016-02-26','2016-02-26',100,40,103.3,'SELL',1,253,294,'Active','NASDAQ',82.64,421.464,4636.104,'2016-02-26 03:35:47','2016-02-26 03:35:47','EBB'),(27,'2016-02-26','2016-02-26',100,60,102.3,'BUY',35,294,254,'Active','NYSE',61.38,309.969,6509.349,'2016-02-26 03:35:47','2016-02-26 03:35:47','EBB'),(28,'2016-02-26','2016-02-26',100,60,102.3,'SELL',1,254,294,'Active','NASDAQ',122.76,626.076,6886.836,'2016-02-26 03:35:47','2016-02-26 03:35:47','EBB'),(29,'2016-02-26','2016-02-26',100,40,102.3,'BUY',35,298,254,'Active','NYSE',40.92,206.646,4339.566,'2016-02-26 03:38:51','2016-02-26 03:38:51','EBB'),(30,'2016-02-26','2016-02-26',100,40,102.3,'SELL',1,254,298,'Active','NASDAQ',81.84,417.384,4591.224,'2016-02-26 03:38:51','2016-02-26 03:38:51','EBB'),(31,'2016-02-26','2016-02-26',100,60,102.3,'BUY',35,298,256,'Active','NYSE',61.38,309.969,6509.349,'2016-02-26 03:38:51','2016-02-26 03:38:51','EBB'),(32,'2016-02-26','2016-02-26',100,60,102.3,'SELL',1,256,298,'Active','NASDAQ',122.76,626.076,6886.836,'2016-02-26 03:38:51','2016-02-26 03:38:51','EBB'),(33,'2016-02-26','2016-02-26',100,40,102.3,'BUY',35,302,256,'Active','NYSE',40.92,206.646,4339.566,'2016-02-26 03:43:43','2016-02-26 03:43:43','EBB'),(34,'2016-02-26','2016-02-26',100,40,102.3,'SELL',1,256,302,'Active','NASDAQ',81.84,417.384,4591.224,'2016-02-26 03:43:43','2016-02-26 03:43:43','EBB'),(35,'2016-02-26','2016-02-26',100,60,103.3,'BUY',35,302,257,'Active','NYSE',61.980000000000004,312.99899999999997,6572.978999999999,'2016-02-26 03:43:43','2016-02-26 03:43:43','EBB'),(36,'2016-02-26','2016-02-26',100,60,103.3,'SELL',1,257,302,'Active','NASDAQ',123.96000000000001,632.196,6954.156,'2016-02-26 03:43:43','2016-02-26 03:43:43','EBB'),(37,'2016-02-26','2016-02-26',100,60,103.3,'SELL',1,257,303,'Active','NASDAQ',123.96000000000001,632.196,6954.156,'2016-02-26 03:44:55','2016-02-26 03:44:55','EBB'),(38,'2016-02-26','2016-02-26',100,60,103.3,'BUY',35,303,257,'Active','NYSE',61.980000000000004,312.99899999999997,6572.978999999999,'2016-02-26 03:44:55','2016-02-26 03:44:55','EBB'),(39,'2016-02-26','2016-02-26',100,40,103.3,'BUY',35,303,259,'Active','NYSE',41.32,208.666,4381.986,'2016-02-26 03:44:55','2016-02-26 03:44:55','EBB'),(40,'2016-02-26','2016-02-26',100,40,103.3,'SELL',1,259,303,'Active','NASDAQ',82.64,421.464,4636.104,'2016-02-26 03:44:55','2016-02-26 03:44:55','EBB'),(41,'2016-04-30','2016-04-30',100,80,103.3,'SELL',1,259,331,'Active','NASDAQ',165.28,842.928,9272.208,'2016-04-30 02:45:38','2016-04-30 02:45:38','EBB'),(42,'2016-04-30','2016-04-30',100,80,103.3,'BUY',35,331,259,'Active','NYSE',82.64,417.332,8763.972,'2016-04-30 02:45:38','2016-04-30 02:45:38','EBB'),(43,'2016-04-30','2016-04-30',100,20,102.3,'BUY',35,331,261,'Active','NYSE',20.46,103.323,2169.783,'2016-04-30 02:45:38','2016-04-30 02:45:38','EBB'),(44,'2016-04-30','2016-04-30',100,20,102.3,'SELL',1,261,331,'Active','NASDAQ',40.92,208.692,2295.612,'2016-04-30 02:45:38','2016-04-30 02:45:38','EBB'),(45,'2016-04-30','2016-04-30',100,80,102.3,'BUY',35,332,261,'Active','NYSE',81.84,413.292,8679.132,'2016-04-30 03:54:44','2016-04-30 03:54:44','EBB'),(46,'2016-04-30','2016-04-30',100,80,102.3,'SELL',1,261,332,'Active','NASDAQ',163.68,834.768,9182.448,'2016-04-30 03:54:44','2016-04-30 03:54:44','EBB'),(47,'2016-04-30','2016-04-30',100,20,102.3,'SELL',1,262,332,'Active','NASDAQ',40.92,208.692,2295.612,'2016-04-30 03:54:44','2016-04-30 03:54:44','EBB'),(48,'2016-04-30','2016-04-30',100,20,102.3,'BUY',35,332,262,'Active','NYSE',20.46,103.323,2169.783,'2016-04-30 03:54:44','2016-04-30 03:54:44','EBB');
/*!40000 ALTER TABLE `trades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `triggered_events`
--

DROP TABLE IF EXISTS `triggered_events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `triggered_events` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `market_event_id` bigint(20) NOT NULL,
  `sent_to_sim` tinyint(1) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `triggered_events`
--

LOCK TABLES `triggered_events` WRITE;
/*!40000 ALTER TABLE `triggered_events` DISABLE KEYS */;
INSERT INTO `triggered_events` VALUES (1,1,0,'2016-02-09 19:41:57','2016-02-09 19:41:57'),(2,1,1,'2016-02-28 18:16:49','2016-03-01 21:02:43');
/*!40000 ALTER TABLE `triggered_events` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `last_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `first_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `role` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `team` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  `team_id` int(11) DEFAULT NULL,
  `contest_id` int(11) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `encrypted_password` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `reset_password_token` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `reset_password_sent_at` datetime DEFAULT NULL,
  `remember_created_at` datetime DEFAULT NULL,
  `sign_in_count` int(11) NOT NULL DEFAULT '0',
  `current_sign_in_at` datetime DEFAULT NULL,
  `last_sign_in_at` datetime DEFAULT NULL,
  `current_sign_in_ip` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `last_sign_in_ip` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `confirmation_token` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `confirmed_at` datetime DEFAULT NULL,
  `confirmation_sent_at` datetime DEFAULT NULL,
  `unconfirmed_email` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `provider` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'email',
  `uid` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `image` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tokens` text COLLATE utf8_unicode_ci,
  `account_balance` double DEFAULT '0',
  `blocked_amount` double DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_users_on_confirmation_token` (`confirmation_token`) USING BTREE,
  UNIQUE KEY `index_users_on_email` (`email`) USING BTREE,
  UNIQUE KEY `index_users_on_reset_password_token` (`reset_password_token`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (35,'Morar','Kaylee','laurie@graham.info','Trader',NULL,NULL,NULL,NULL,'2016-02-07 06:50:03','2016-04-30 04:04:33','$2a$10$9hedQm039xwEEaL5KI3UQeWK/9vYdHmG2LtE0KcL2cSvjkqJIVd5O',NULL,NULL,NULL,9,'2016-04-30 04:04:33','2016-04-30 02:43:13','127.0.0.1','127.0.0.1',NULL,'2016-02-07 06:50:03','2016-02-07 06:50:03',NULL,'email','laurie@graham.info',NULL,'{\"teZXhp5VtjFMt6uw-F1aqw\":{\"token\":\"$2a$10$MMlISafEMdlvzm7OiIJAru7JN2jHw4nN8ZN95IuG/FKA88vbSUXyS\",\"expiry\":1463198673,\"last_token\":\"$2a$10$wTfZ5cWUcfBE7btR2LBnPubKmsJFWeOK.6BIpEqADVaMzf4oZn94u\",\"updated_at\":\"2016-04-30T09:34:33.348+05:30\"}}',966266.9149999997,-108130),(36,'Swift','Levi','sydnee.mcdermott@schamberger.name','Trader',NULL,NULL,NULL,NULL,'2016-02-07 06:50:03','2016-02-07 06:50:03','$2a$10$JASx4bqjVhlr0b1O0AkzFOtH/zk3ZYiviDRZJHeI5eURhh8l8ljua',NULL,NULL,NULL,5,NULL,NULL,NULL,NULL,NULL,'2016-02-07 06:50:03','2016-02-07 06:50:03',NULL,'email','sydnee.mcdermott@schamberger.name',NULL,'{}',0,0),(37,'Windler','Sadye','dylan@von.net','Trader',NULL,NULL,NULL,NULL,'2016-02-07 06:50:04','2016-02-07 06:50:04','$2a$10$SsRxixmVtzoyuaRb2lemKOPk128a2bQQkNUlAIECww7rb3F0Ps4xW',NULL,NULL,NULL,5,NULL,NULL,NULL,NULL,NULL,'2016-02-07 06:50:04','2016-02-07 06:50:04',NULL,'email','dylan@von.net',NULL,'{}',0,0),(38,'Kozey','Kacie','aubree_bergnaum@stamm.info','Trader',NULL,NULL,NULL,NULL,'2016-02-07 06:50:04','2016-02-07 06:50:04','$2a$10$l5QVTWvasKqG6fgQy9ZvVe7V1/.g.CKjb.tFADBsS1eKqksBDn2Q.',NULL,NULL,NULL,5,NULL,NULL,NULL,NULL,NULL,'2016-02-07 06:50:04','2016-02-07 06:50:04',NULL,'email','aubree_bergnaum@stamm.info',NULL,'{}',0,0),(39,'Waelchi','Leonel','eleanore.bernhard@kirlin.net','Trader',NULL,NULL,NULL,NULL,'2016-02-07 06:50:04','2016-02-07 06:50:04','$2a$10$IM2emGTLF3F2tHNekI3e1uILtYw7tVNnbLYxZxVx9HnPK0otu6ac2',NULL,NULL,NULL,5,NULL,NULL,NULL,NULL,NULL,'2016-02-07 06:50:04','2016-02-07 06:50:04',NULL,'email','eleanore.bernhard@kirlin.net',NULL,'{}',0,0),(40,'Wolff','Cade','kaelyn@wilkinson.io','Trader',NULL,NULL,NULL,NULL,'2016-02-07 06:50:04','2016-02-07 06:50:04','$2a$10$JahgvIQTxUPfy3SN1fZv3up/6Vev0G42mqtTAgzNMts7lWQsdlbTW',NULL,NULL,NULL,5,NULL,NULL,NULL,NULL,NULL,'2016-02-07 06:50:04','2016-02-07 06:50:04',NULL,'email','kaelyn@wilkinson.io',NULL,'{}',0,0),(41,'Kuhic','Pattie','cleta_koelpin@abernathy.io','Trader',NULL,NULL,NULL,NULL,'2016-02-07 06:50:04','2016-02-07 06:50:04','$2a$10$XeNHTyUDl2.R7AARDDkTkOMIBKlhpVK67DKTyTFSoU8jur3UNeunu',NULL,NULL,NULL,5,NULL,NULL,NULL,NULL,NULL,'2016-02-07 06:50:04','2016-02-07 06:50:04',NULL,'email','cleta_koelpin@abernathy.io',NULL,'{}',0,0),(42,'Hagenes','Kallie','rowland@jacobsherman.net','Trader',NULL,NULL,NULL,NULL,'2016-02-07 06:50:04','2016-02-07 06:50:04','$2a$10$rhCMtaf8qP0jsAMvGidBTO8WP9tewX1FYYsnXCzHID3dWEI1BSHeK',NULL,NULL,NULL,5,NULL,NULL,NULL,NULL,NULL,'2016-02-07 06:50:04','2016-02-07 06:50:04',NULL,'email','rowland@jacobsherman.net',NULL,'{}',0,0),(43,'Wuckert','Makayla','clementina_hagenes@breitenberg.info','Trader',NULL,NULL,NULL,NULL,'2016-02-07 06:50:04','2016-02-07 06:50:04','$2a$10$YZxv.6d1J4GNlT2bZPPJXuALuZK4fPWKkNlTanqKSw1/8cC2kU2AG',NULL,NULL,NULL,5,NULL,NULL,NULL,NULL,NULL,'2016-02-07 06:50:04','2016-02-07 06:50:04',NULL,'email','clementina_hagenes@breitenberg.info',NULL,'{}',0,0),(44,'Orn','Emory','braulio_thompson@tillman.co','Trader',NULL,NULL,NULL,NULL,'2016-02-07 06:50:05','2016-02-07 06:50:05','$2a$10$Ut4c7aX0V4.XbHl5fqfSruG0aBqvhR0Xu4fTUyk7vBuN7dlN1roS2',NULL,NULL,NULL,5,NULL,NULL,NULL,NULL,NULL,'2016-02-07 06:50:05','2016-02-07 06:50:05',NULL,'email','braulio_thompson@tillman.co',NULL,'{}',0,0),(45,'Dooley','Daphne','harold_rath@block.com','Trader',NULL,NULL,NULL,NULL,'2016-02-07 06:50:05','2016-02-07 06:50:05','$2a$10$LBGABsyxHZ0H.PB4GsEw0eeOdqh2kJQbiyYHhwJyxVuohk4/Z8au2',NULL,NULL,NULL,5,NULL,NULL,NULL,NULL,NULL,'2016-02-07 06:50:05','2016-02-07 06:50:05',NULL,'email','harold_rath@block.com',NULL,'{}',0,0),(46,'Pacocha','Tess','lea.upton@croninjakubowski.com','Trader',NULL,NULL,NULL,NULL,'2016-02-07 06:50:05','2016-02-07 06:50:05','$2a$10$CxonmKSoY0S2m472jU.jZ.lXhdT3tYrxUChEMgQLjmlsPl48X6Irm',NULL,NULL,NULL,5,NULL,NULL,NULL,NULL,NULL,'2016-02-07 06:50:05','2016-02-07 06:50:05',NULL,'email','lea.upton@croninjakubowski.com',NULL,'{}',0,0),(47,'Hegmann','Melany','telly@blanda.net','Trader',NULL,NULL,NULL,NULL,'2016-02-07 06:50:05','2016-02-07 06:50:05','$2a$10$S9eQcpY8hD1cgV7MGi3XhOOzDTedWUYJB8Q.giq/n8tSoVUX6WX5m',NULL,NULL,NULL,5,NULL,NULL,NULL,NULL,NULL,'2016-02-07 06:50:05','2016-02-07 06:50:05',NULL,'email','telly@blanda.net',NULL,'{}',0,0),(48,'Leannon','Gregory','teresa_veum@kaulke.com','Trader',NULL,NULL,NULL,NULL,'2016-02-07 06:50:05','2016-02-07 06:50:05','$2a$10$ItiNtWKXlNgL0nYihqXdAOoA9QkZ1KKBidmCamWHsU/nLihYBkTdy',NULL,NULL,NULL,5,NULL,NULL,NULL,NULL,NULL,'2016-02-07 06:50:05','2016-02-07 06:50:05',NULL,'email','teresa_veum@kaulke.com',NULL,'{}',0,0),(49,'Ortiz','Ernestina','jay_abshire@stehr.co','Trader',NULL,NULL,NULL,NULL,'2016-02-07 06:50:05','2016-02-07 06:50:05','$2a$10$ulU.74lgtf/aPvIpB7NpUe2rD5ZifvvrGQFriY/qSeRgsNqnq3kda',NULL,NULL,NULL,5,NULL,NULL,NULL,NULL,NULL,'2016-02-07 06:50:05','2016-02-07 06:50:05',NULL,'email','jay_abshire@stehr.co',NULL,'{}',0,0),(50,'Ryan','Fiona','narciso.hirthe@moen.biz','Trader',NULL,NULL,NULL,NULL,'2016-02-07 06:50:05','2016-02-07 06:50:05','$2a$10$FLdZx0yzG29hXFepCPt5GO9n0CZcnPygxTMZ828kS1fLVcFONkMxK',NULL,NULL,NULL,5,NULL,NULL,NULL,NULL,NULL,'2016-02-07 06:50:05','2016-02-07 06:50:05',NULL,'email','narciso.hirthe@moen.biz',NULL,'{}',0,0),(51,'Kirlin','Mallie','seth@pouroslemke.com','Trader',NULL,NULL,NULL,NULL,'2016-02-07 06:50:05','2016-02-07 06:50:05','$2a$10$x2Gi6avtcCR87MT6KuRZOuSPjPBi2KuIRktBLVzzLJi4J4u/yMKCq',NULL,NULL,NULL,5,NULL,NULL,NULL,NULL,NULL,'2016-02-07 06:50:05','2016-02-07 06:50:05',NULL,'email','seth@pouroslemke.com',NULL,'{}',0,0),(52,'Mitchell','Davonte','nora@feeney.io','Trader',NULL,NULL,NULL,NULL,'2016-02-07 06:50:06','2016-02-07 06:50:06','$2a$10$pCufGPzbf7VsxsoNvHP0fOTvBnsd80iIKEcxEd5GSySf/2sXj9RMe',NULL,NULL,NULL,5,NULL,NULL,NULL,NULL,NULL,'2016-02-07 06:50:06','2016-02-07 06:50:06',NULL,'email','nora@feeney.io',NULL,'{}',0,0),(53,'Lind','Zachery','florence.sauer@hellerdeckow.net','Trader',NULL,NULL,NULL,NULL,'2016-02-07 06:50:06','2016-02-07 06:50:06','$2a$10$9qXRN89Kk3D5O1DaPBOoF.jH9BrFgIfLnhDDWraDHcO7wWV/KlYPG',NULL,NULL,NULL,5,NULL,NULL,NULL,NULL,NULL,'2016-02-07 06:50:06','2016-02-07 06:50:06',NULL,'email','florence.sauer@hellerdeckow.net',NULL,'{}',0,0),(54,'Fritsch','Nyah','dawn.oconner@heller.info','Trader',NULL,NULL,NULL,NULL,'2016-02-07 06:50:06','2016-02-07 06:50:06','$2a$10$NE.B0hVmU.hQUAb5mnIrIeY5IKNQgonUkduIBN3GCTyqzQ5wCR/W.',NULL,NULL,NULL,5,NULL,NULL,NULL,NULL,NULL,'2016-02-07 06:50:06','2016-02-07 06:50:06',NULL,'email','dawn.oconner@heller.info',NULL,'{}',0,0),(55,'Koelpin','Graciela','mellie.kohler@tillman.biz','Simulation',NULL,NULL,NULL,NULL,'2016-02-07 06:50:06','2016-02-07 06:50:06','$2a$10$/jbHAJt8Z8BEtmc1Fgc94O345vCP.CcK5tC9R1cC.ptdhT6o2QrT6',NULL,NULL,NULL,5,NULL,NULL,NULL,NULL,NULL,'2016-02-07 06:50:06','2016-02-07 06:50:06',NULL,'email','mellie.kohler@tillman.biz',NULL,'{}',999828000,172000),(56,'Gislason','Esta','noe.kohler@ullrich.org','Simulation',NULL,NULL,NULL,NULL,'2016-02-07 06:50:06','2016-02-07 06:50:06','$2a$10$yUtwOKGT9Pfaax8fNqnaF.9w8/712m4gaSklzIfyT3gUVgIdt1RR6',NULL,NULL,NULL,5,NULL,NULL,NULL,NULL,NULL,'2016-02-07 06:50:06','2016-02-07 06:50:06',NULL,'email','noe.kohler@ullrich.org',NULL,'{}',999828000,172000),(57,'O\'Connell','Melvin','jody_blick@gradyparisian.info','Simulation',NULL,NULL,NULL,NULL,'2016-02-07 06:50:06','2016-02-07 06:50:06','$2a$10$cRp0vb5Ctf3NM7zEXb/rPOyIA.KPcNPSMFoPS8dXe/OLpM/10ttN2',NULL,NULL,NULL,5,NULL,NULL,NULL,NULL,NULL,'2016-02-07 06:50:06','2016-02-07 06:50:06',NULL,'email','jody_blick@gradyparisian.info',NULL,'{}',999828000,172000),(58,'Heathcote','Cordell','stephan@gaylord.net','Simulation',NULL,NULL,NULL,NULL,'2016-02-07 06:50:06','2016-02-07 06:50:06','$2a$10$3V/eCFIIsgxknhhOD1HJFeGNuAZTNlVj7IviguPouxMt/IS9cVRxu',NULL,NULL,NULL,5,NULL,NULL,NULL,NULL,NULL,'2016-02-07 06:50:06','2016-02-07 06:50:06',NULL,'email','stephan@gaylord.net',NULL,'{}',999828000,172000),(59,'Kuhn','Isac','brooke@johnshansen.info','Simulation',NULL,NULL,NULL,NULL,'2016-02-07 06:50:06','2016-02-07 06:50:06','$2a$10$j.1pvkD0yFf10u84L2tmvuwlEUmmX0RgD9IMQ1x3D7omCH8mLdSQa',NULL,NULL,NULL,5,NULL,NULL,NULL,NULL,NULL,'2016-02-07 06:50:06','2016-02-07 06:50:06',NULL,'email','brooke@johnshansen.info',NULL,'{}',999828000,172000),(60,'DuBuque','Jasper','muhammad@heaneypurdy.name','Simulation',NULL,NULL,NULL,NULL,'2016-02-07 06:50:07','2016-02-07 06:50:07','$2a$10$l/aG1GTMVfrr0CtItfn.cu45tQeYmqoh94C/PBAXnClwpo9seijG.',NULL,NULL,NULL,5,NULL,NULL,NULL,NULL,NULL,'2016-02-07 06:50:07','2016-02-07 06:50:07',NULL,'email','muhammad@heaneypurdy.name',NULL,'{}',999828000,172000),(61,'Mann','Rahul','caria@connelly.com','Simulation',NULL,NULL,NULL,NULL,'2016-02-07 06:50:07','2016-02-07 06:50:07','$2a$10$43YkkNsquCh8a9VUIbt/O.50uAPn7mL1s0ijozUTJfPI6uKA9uOWW',NULL,NULL,NULL,5,NULL,NULL,NULL,NULL,NULL,'2016-02-07 06:50:07','2016-02-07 06:50:07',NULL,'email','caria@connelly.com',NULL,'{}',999828000,172000),(62,'Koch','Agustina','lurline@treutel.biz','Simulation',NULL,NULL,NULL,NULL,'2016-02-07 06:50:07','2016-02-07 06:50:07','$2a$10$fv4GUf9BRn3ewce0T.I55u/M9DyHzlBODkKyW/xepUvNrGcmNFiGS',NULL,NULL,NULL,5,NULL,NULL,NULL,NULL,NULL,'2016-02-07 06:50:07','2016-02-07 06:50:07',NULL,'email','lurline@treutel.biz',NULL,'{}',999828000,172000),(63,'O\'Keefe','Andrew','walton_hegmann@bode.org','Simulation',NULL,NULL,NULL,NULL,'2016-02-07 06:50:07','2016-02-07 06:50:07','$2a$10$TQqR/TZtfTBA/xUyWQ8Og.tVljGs6u118iVdQ4CQb0qnNb8VZhgz6',NULL,NULL,NULL,5,NULL,NULL,NULL,NULL,NULL,'2016-02-07 06:50:07','2016-02-07 06:50:07',NULL,'email','walton_hegmann@bode.org',NULL,'{}',999828000,172000),(64,'Fisher','Stephan','dasia@schneider.org','Simulation',NULL,NULL,NULL,NULL,'2016-02-07 06:50:07','2016-02-07 06:50:07','$2a$10$0kwiO8hCvb9Dfz1ViEPHn.FKw7xcB99/D7m5kQXQylYnc5oD9ZWXK',NULL,NULL,NULL,5,NULL,NULL,NULL,NULL,NULL,'2016-02-07 06:50:07','2016-02-07 06:50:07',NULL,'email','dasia@schneider.org',NULL,'{}',999828000,172000);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-07-19 16:47:34
