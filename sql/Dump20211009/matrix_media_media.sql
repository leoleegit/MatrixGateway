-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: 192.168.3.116    Database: matrix_media
-- ------------------------------------------------------
-- Server version	8.0.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `media`
--

DROP TABLE IF EXISTS `media`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `media` (
  `id` int NOT NULL AUTO_INCREMENT,
  `media_id` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_by` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `type` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp(6) NULL DEFAULT NULL,
  `name` varchar(245) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `content_type` varchar(145) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `size` bigint DEFAULT NULL,
  `thumb_id` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `bucket` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `media_id_UNIQUE` (`media_id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `media`
--

LOCK TABLES `media` WRITE;
/*!40000 ALTER TABLE `media` DISABLE KEYS */;
INSERT INTO `media` VALUES (1,'ec0e35fe-36f0-4b76-9bff-4885bfea3301','leo','image','2021-10-09 08:29:38.641000','WeChat Screenshot_20211008151802.jpg','image/jpeg',60707,NULL,'2021-10'),(3,'ec0e35fe-36f0-4b76-9bff-4885bfea3301-min','leo','image','2021-10-09 08:29:38.832000','WeChat Screenshot_20211008151802.jpg','image/jpg',8551,NULL,'2021-10'),(5,'f228e90a-fb1a-49ca-a189-a59e1fa70a44','leo','image','2021-10-09 08:51:46.589000','WeChat Screenshot_20211008151802.jpg','image/jpeg',60707,NULL,'2021-10'),(7,'f228e90a-fb1a-49ca-a189-a59e1fa70a44-min','leo','image','2021-10-09 08:51:46.884000','WeChat Screenshot_20211008151802.jpg','image/jpg',8551,NULL,'2021-10'),(9,'4148075e-f52a-438d-8352-59a2c7b80fd5','leo','image','2021-10-09 09:03:50.579000','WeChat Screenshot_20211008151802.jpg','image/jpeg',60707,NULL,'2021-10'),(11,'4148075e-f52a-438d-8352-59a2c7b80fd5-min','leo','image','2021-10-09 09:03:50.682000','WeChat Screenshot_20211008151802.jpg','image/jpg',8551,NULL,'2021-10'),(13,'3e77a345-da52-4b31-bdd5-1e22b5ed3b8a','leo','image','2021-10-09 09:05:43.583000','WeChat Screenshot_20211008151802.jpg','image/jpeg',60707,NULL,'2021-10'),(15,'3e77a345-da52-4b31-bdd5-1e22b5ed3b8a-min','leo','image','2021-10-09 09:05:43.716000','WeChat Screenshot_20211008151802.jpg','image/jpg',8551,NULL,'2021-10'),(17,'46dec41b-f588-434a-a18c-0c27b4e129c0','leo','image','2021-10-09 09:15:19.850000','WeChat Screenshot_20211008151802.jpg','image/jpeg',60707,NULL,'2021-10'),(19,'46dec41b-f588-434a-a18c-0c27b4e129c0-min','leo','image','2021-10-09 09:15:19.969000','WeChat Screenshot_20211008151802.jpg','image/jpg',8551,NULL,'2021-10'),(21,'ff04f818-524c-4f4b-8fc7-9b9823f521a0','leo','image','2021-10-09 09:17:14.005000','WeChat Screenshot_20211008151802.jpg','image/jpeg',60707,NULL,'2021-10'),(23,'ff04f818-524c-4f4b-8fc7-9b9823f521a0-min','leo','image','2021-10-09 09:17:14.582000','WeChat Screenshot_20211008151802.jpg','image/jpg',8551,NULL,'2021-10'),(25,'3db3ae03-56e5-40e8-b490-fab168337625','leo','image','2021-10-09 09:19:47.134000','WeChat Screenshot_20211008151802.jpg','image/jpeg',60707,NULL,'2021-10'),(27,'3db3ae03-56e5-40e8-b490-fab168337625-min','leo','image','2021-10-09 09:19:47.692000','WeChat Screenshot_20211008151802.jpg','image/jpg',8551,NULL,'2021-10'),(29,'31dc932e-b3a2-4b19-a65b-cb802d2c8a86','leo','image','2021-10-09 09:37:28.293000','WeChat Screenshot_20211008151802.jpg','image/jpeg',60707,NULL,'2021-10'),(31,'31dc932e-b3a2-4b19-a65b-cb802d2c8a86-min','leo','image','2021-10-09 09:37:28.413000','WeChat Screenshot_20211008151802.jpg','image/jpg',8551,NULL,'2021-10'),(33,'e4736dc8-128e-4510-a840-9306342f6471','001','image','2021-10-09 12:47:29.520000','WeChat Screenshot_20211008151802.jpg','image/jpeg',60707,NULL,'2021-10'),(35,'e4736dc8-128e-4510-a840-9306342f6471-min','001','image','2021-10-09 12:47:29.651000','WeChat Screenshot_20211008151802.jpg','image/jpg',8551,NULL,'2021-10');
/*!40000 ALTER TABLE `media` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-10-09 23:21:10
