-- MariaDB dump 10.19  Distrib 10.11.6-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: transport
-- ------------------------------------------------------
-- Server version	10.11.6-MariaDB-0+deb12u1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `delivery`
--

DROP TABLE IF EXISTS `delivery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `delivery` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `recipientName` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `deliveryDate` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `driver` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `delivery`
--

LOCK TABLES `delivery` WRITE;
/*!40000 ALTER TABLE `delivery` DISABLE KEYS */;
INSERT INTO `delivery` VALUES
(1,'夕阳','address1','2024-10-15 18:40:33',1,'若风'),
(2,'夕阳','address3','2024-10-15 18:48:17',0,'化雨'),
(3,'夕阳','address1','2024-10-15 21:48:18',0,'往昔');
/*!40000 ALTER TABLE `delivery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `driver`
--

DROP TABLE IF EXISTS `driver`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `driver` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `licenseNumber` varchar(100) NOT NULL,
  `phoneNumber` varchar(20) NOT NULL,
  `licenseExpiryDate` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `licenseNumber` (`licenseNumber`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `driver`
--

LOCK TABLES `driver` WRITE;
/*!40000 ALTER TABLE `driver` DISABLE KEYS */;
INSERT INTO `driver` VALUES
(1,'若风','144','15872461997','2024-10-29 16:00:00'),
(2,'化雨','135','15872461997','2024-10-30 16:00:00'),
(3,'往昔','136','15872461997','2024-10-30 16:00:00'),
(4,'浮年','138','15872461997','2024-10-30 16:00:00'),
(8,'仙逆','139','15872461997','2024-10-30 16:00:00');
/*!40000 ALTER TABLE `driver` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `level`
--

DROP TABLE IF EXISTS `level`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `level` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) NOT NULL COMMENT '运输等级',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `level`
--

LOCK TABLES `level` WRITE;
/*!40000 ALTER TABLE `level` DISABLE KEYS */;
INSERT INTO `level` VALUES
(1,'急速'),
(2,'平衡'),
(3,'稳重');
/*!40000 ALTER TABLE `level` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menu` (
  `id` int(11) NOT NULL,
  `menuCode` varchar(8) DEFAULT NULL COMMENT '菜单编码',
  `menuName` varchar(16) DEFAULT NULL COMMENT '菜单名字',
  `menuLevel` varchar(2) DEFAULT NULL COMMENT '菜单级别',
  `menuParentCode` varchar(8) DEFAULT NULL COMMENT '菜单的父code',
  `menuClick` varchar(16) DEFAULT NULL COMMENT '点击触发的函数',
  `menuRight` varchar(8) DEFAULT NULL COMMENT '权限 0超级管理员，1管理员，2普通用户 可以组合使用',
  `menuComponent` varchar(200) DEFAULT NULL COMMENT '路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES
(1,'001','管理员管理','1',NULL,'Admin','0','admin/AdminManage.vue'),
(2,'002','用户管理','1',NULL,'User','0,1','user/UserManage.vue'),
(3,'003','运输公司管理','1',NULL,'Priority','0,1','priority/PriorityManage.vue'),
(4,'004','运输方式管理','1',NULL,'Trainingtype','0,1','trainingtype/TrainingtypeManage.vue'),
(5,'005','司机管理','1',NULL,'Driver','0,1','delivery/DriverManage.vue'),
(6,'006','订单管理','1',NULL,'Trainingorder','0,1,2','trainingorder/TrainingorderManage.vue'),
(7,'007','配送管理','1',NULL,'Delivery','0,1,2','delivery/DeliveryManage.vue'),
(8,'008','记录管理','1',NULL,'Record','0,1,2','record/RecordManage.vue');
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `priority`
--

DROP TABLE IF EXISTS `priority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `priority` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) NOT NULL COMMENT '运输等级',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `createTime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `priority`
--

LOCK TABLES `priority` WRITE;
/*!40000 ALTER TABLE `priority` DISABLE KEYS */;
INSERT INTO `priority` VALUES
(1,'兔兔运输','如兔兔般速度','2024-10-15 10:26:40'),
(2,'大罗法咒','般若般若哄','2024-10-15 10:27:39'),
(3,'胡哥运输','AAAAA胡哥运输','2024-10-02 16:00:00');
/*!40000 ALTER TABLE `priority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `record`
--

DROP TABLE IF EXISTS `record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `goods` int(11) NOT NULL COMMENT '货品id',
  `userId` int(11) DEFAULT NULL COMMENT '用户',
  `adminId` int(11) DEFAULT NULL COMMENT '操作人id',
  `weight` int(11) DEFAULT NULL COMMENT '重量',
  `createtime` datetime DEFAULT NULL COMMENT '订单创建时间',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `record`
--

LOCK TABLES `record` WRITE;
/*!40000 ALTER TABLE `record` DISABLE KEYS */;
INSERT INTO `record` VALUES
(1,1,4,1,1,'2024-10-15 18:40:33','第一次测试'),
(2,2,4,1,1,'2024-10-15 18:48:17','测试'),
(3,3,4,3,1,'2024-10-15 21:48:18','测试12');
/*!40000 ALTER TABLE `record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trainingorder`
--

DROP TABLE IF EXISTS `trainingorder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trainingorder` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) NOT NULL COMMENT '货名',
  `priority` int(11) NOT NULL COMMENT '公司',
  `trainingType` int(11) NOT NULL COMMENT '运输方式',
  `weight` int(11) DEFAULT NULL COMMENT '重量',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trainingorder`
--

LOCK TABLES `trainingorder` WRITE;
/*!40000 ALTER TABLE `trainingorder` DISABLE KEYS */;
INSERT INTO `trainingorder` VALUES
(1,'1号物品',2,3,1,'1'),
(2,'2号物品',1,1,1,'1'),
(3,'赤赤1号',3,3,1,'1');
/*!40000 ALTER TABLE `trainingorder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trainingtype`
--

DROP TABLE IF EXISTS `trainingtype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trainingtype` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) NOT NULL COMMENT '运输方式',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trainingtype`
--

LOCK TABLES `trainingtype` WRITE;
/*!40000 ALTER TABLE `trainingtype` DISABLE KEYS */;
INSERT INTO `trainingtype` VALUES
(1,'10km小货车','乡内运输'),
(2,'50km小货车','县内运输'),
(3,'100km小货车','市内运输');
/*!40000 ALTER TABLE `trainingtype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `no` varchar(20) DEFAULT NULL COMMENT '账号',
  `name` varchar(100) DEFAULT NULL COMMENT '名字',
  `password` varchar(20) NOT NULL COMMENT '密码',
  `age` int(11) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL COMMENT '性别',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话',
  `role_id` int(11) DEFAULT NULL COMMENT '角色 0超级管理员，1管理员，2普通账号',
  `sValid` varchar(4) DEFAULT 'Y' COMMENT '是否有效，Y有效，其他无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES
(1,'admin','mengsheng','123',11,0,'11111',0,'Y'),
(2,'caiyang','蔡阳','123',20,0,'15872461997',1,'Y'),
(3,'chichi','赤赤','123',30,1,'15872461997',1,'Y'),
(4,'xiyang','夕阳','123',20,0,'15872461997',2,'Y'),
(5,'zuocheng','卓陈','123',20,0,'15872461997',1,'Y'),
(6,'mengdie','梦蝶','123',20,1,'15872461997',1,'Y'),
(7,'huihui','辉辉','123',20,0,'15872461997',1,'Y'),
(8,'shirui','石瑞是吾儿','123',20,0,'15872461997',1,'Y');
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

-- Dump completed on 2024-10-19 16:20:19
