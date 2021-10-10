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
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(80) COLLATE utf8mb4_unicode_ci NOT NULL,
  `nickname` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `telephone` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `sex` tinyint NOT NULL DEFAULT '0',
  `avatar_url` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `role` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `updated_at` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `created_by` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `updated_by` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `disable` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `type1` (`username`) /*!80000 INVISIBLE */,
  UNIQUE KEY `type2` (`telephone`) /*!80000 INVISIBLE */,
  UNIQUE KEY `type3` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user`
--

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` VALUES (1,'leo','$2a$10$KjiVbjcBkME.POQaNasM4ee0zM1w0YXrb19sNA4Hy/Ww1maHM.iFK','leo','15919065160','l@xsocket.cn',1,'/media/file/bucket/31dc932e-b3a2-4b19-a65b-cb802d2c8a86-min/WeChat Screenshot_20211008151802.jpg','sys_admin','2021-09-03 07:33:00.000000','2021-09-03 07:33:00.000000','setup','setup',0),(3,'spotlight','$2a$10$Z8xOrWjyKnyd0IIhGyunnODf7QcH8oZQ1L7ZUOioJJh2t0loKirYG','spotlight',NULL,NULL,1,'https://img2.baidu.com/it/u=3130915953,654604175&fm=15&fmt=auto&gp=0.jpg','user_admin','2021-09-24 09:36:57.628296','2021-09-24 09:36:57.628296','leo','leo',0),(5,'matrix','$2a$10$N6VzhibU8a84sqylJKUPmeYFPr8NkBPC9agarD6t4/kLKWxLH6N.e','matrix','15899562038',NULL,1,'./logo.svg','user_admin','2021-09-24 09:50:39.683319','2021-09-26 14:44:40.573000','leo','leo',0),(9,'001','$2a$10$z7VfVQLIuj3tPPhuneZbc.BpxWCjzJG9hxD6agK5Q4g.O87sLBhdW','001',NULL,NULL,1,'/media/file/bucket/e4736dc8-128e-4510-a840-9306342f6471-min/WeChat Screenshot_20211008151802.jpg','user','2021-10-04 15:28:52.596827','2021-10-04 15:28:52.596827','matrix','matrix',0);
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
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
