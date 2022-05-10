-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: app_store
-- ------------------------------------------------------
-- Server version	8.0.26

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
-- Table structure for table `app_info`
--

DROP TABLE IF EXISTS `app_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `app_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `app_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `app_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `package_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `version_code` bigint NOT NULL,
  `version_code_string` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `version_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `icon_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `app_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `manifest_path` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `app_size` double DEFAULT NULL,
  `app_size_unit` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `delete_flg` varchar(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `create_date` datetime NOT NULL,
  `create_by_id` bigint NOT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_by_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_info`
--

LOCK TABLES `app_info` WRITE;
/*!40000 ALTER TABLE `app_info` DISABLE KEYS */;
INSERT INTO `app_info` VALUES (1,'ipa','Nogi','vn.com.nsmv.Nogi',1,'1','1.0.2','\\var\\www\\Sensor_System\\APP_RESOURCE\\IPA\\vn.com.nsmv.Nogi\\1.0.2_20220511_010814_627\\AppIcon76x76@2x~ipad.png','\\var\\www\\Sensor_System\\APP_RESOURCE\\IPA\\vn.com.nsmv.Nogi\\1.0.2_20220511_010814_627\\Nogi.ipa','\\var\\www\\Sensor_System\\APP_RESOURCE\\IPA\\vn.com.nsmv.Nogi\\1.0.2_20220511_010814_627\\manifest.plist',44.66,'MB','0','2022-05-11 01:08:15',0,NULL,NULL),(2,'apk','Meishi','com.meishi',1,'1','1.0','\\var\\www\\Sensor_System\\APP_RESOURCE\\APK\\com.meishi\\1.0_20220511_010905_545\\ic_launcher.png','\\var\\www\\Sensor_System\\APP_RESOURCE\\APK\\com.meishi\\1.0_20220511_010905_545\\Meishi.apk',NULL,24.46,'MB','0','2022-05-11 01:09:06',0,NULL,NULL),(3,'ipa','Meishi','vn.com.meishi',1,'1','2.0','\\var\\www\\Sensor_System\\APP_RESOURCE\\IPA\\vn.com.meishi\\2.0_20220511_010913_342\\AppIcon60x60@2x.png','\\var\\www\\Sensor_System\\APP_RESOURCE\\IPA\\vn.com.meishi\\2.0_20220511_010913_342\\Meishi.ipa','\\var\\www\\Sensor_System\\APP_RESOURCE\\IPA\\vn.com.meishi\\2.0_20220511_010913_342\\manifest.plist',12.56,'MB','0','2022-05-11 01:09:14',0,NULL,NULL);
/*!40000 ALTER TABLE `app_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `authority` varchar(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `first_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `mid_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `last_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `sex` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `date_of_birth` date NOT NULL,
  `department` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `delete_flg` varchar(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `create_date` datetime NOT NULL,
  `create_by_id` bigint NOT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_by_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'00001','$2a$10$fEGUXs17/nQSwUtRdMFPleOQVOZpJ/B2XzCx2/KxEUjaekNQJUQbC','A','F','M','L','M','1996-02-26','IT','vinh@nsmv.com.vn','0','2022-04-20 00:00:00',0,NULL,NULL);
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

-- Dump completed on 2022-05-11  0:05:17
