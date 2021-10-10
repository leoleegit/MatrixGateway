-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: 192.168.3.116    Database: matrix_admin
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
-- Table structure for table `t_access_permission`
--

DROP TABLE IF EXISTS `t_access_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_access_permission` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `type` tinyint NOT NULL,
  `code` varchar(255) NOT NULL,
  `desc` varchar(255) DEFAULT NULL,
  `root_id` int DEFAULT '0',
  `method` varchar(10) NOT NULL DEFAULT 'ALL',
  `path` varchar(500) DEFAULT NULL,
  `created_at` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `updated_at` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `created_by` varchar(45) DEFAULT NULL,
  `updated_by` varchar(45) DEFAULT NULL,
  `disable` tinyint DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `parent_id_idx` (`root_id`),
  CONSTRAINT `t_access_permission_f_r_id` FOREIGN KEY (`root_id`) REFERENCES `t_access_permission` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_access_permission`
--

LOCK TABLES `t_access_permission` WRITE;
/*!40000 ALTER TABLE `t_access_permission` DISABLE KEYS */;
INSERT INTO `t_access_permission` VALUES (1,'Matrix Admin Console',0,'matrix','Matrix Admin Console',NULL,'ALL',NULL,'2021-09-16 04:11:40.624363','2021-09-29 03:17:59.056000','leo','leo',0),(7,'Role Manage',1,'matrix:role','Role Manage',1,'ALL','/admin/role/*','2021-09-17 08:51:32.418461','2021-09-18 01:49:07.699000','leo','leo',0),(45,'Permission Manage',1,'matrix:permission','Permission Manage',1,'ALL','/admin/permission/*','2021-09-29 02:55:22.659997','2021-09-29 02:55:22.659997','leo','leo',0),(47,'User Manage',1,'matrix:user','User Manage',1,'ALL','/admin/user/*','2021-09-29 02:55:49.400393','2021-09-29 02:55:49.400393','leo','leo',0),(49,'SpotlightX',0,'spotlightx','SpotlightX',NULL,'ALL',NULL,'2021-09-29 02:56:47.403426','2021-09-29 02:57:58.079000','leo','leo',0),(59,'Case',1,'spotlightx:case','Case',49,'ALL',NULL,'2021-09-29 08:45:28.107815','2021-09-29 08:45:28.107815','leo','leo',0),(61,'Add Case',1,'spotlightx:case:add case','Add Case',59,'POST','/spotlightx/case/add','2021-09-29 08:46:07.417071','2021-09-29 08:46:07.417071','leo','leo',0),(63,'Edit Case',1,'spotlightx:case:edit case','Edit Case',59,'POST','/spotlightx/case/edit','2021-09-29 08:57:18.191619','2021-09-29 08:57:31.327000','leo','leo',0),(69,'Search Case',1,'spotlightx:case:search','Search',59,'POST','/spotlightx/case/search','2021-09-30 03:41:33.580986','2021-09-30 03:42:59.839000','leo','leo',0),(75,'message',2,'spotlightx:case:search:message','message',69,'POST','/spotlightx/case/search/message','2021-09-30 03:55:44.416564','2021-09-30 03:55:44.416564','leo','leo',0),(77,'image',2,'spotlightx:case:search:image','image',69,'PUT','/spotlightx/case/search/image','2021-09-30 06:33:31.959899','2021-09-30 06:33:55.077000','leo','leo',0),(79,'Mobile App',0,'mobile','Mobile App',NULL,'ALL','/mobile/*','2021-09-30 06:36:12.007916','2021-09-30 06:36:12.007916','leo','leo',0),(81,'User',1,'mobile:user','User',79,'ALL','/mobile/user/*','2021-09-30 06:36:42.353561','2021-09-30 06:36:42.353561','leo','leo',0);
/*!40000 ALTER TABLE `t_access_permission` ENABLE KEYS */;
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
