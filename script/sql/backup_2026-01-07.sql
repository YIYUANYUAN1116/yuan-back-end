-- MySQL dump 10.13  Distrib 8.0.44, for Linux (x86_64)
--
-- Host: localhost    Database: yuan
-- ------------------------------------------------------
-- Server version	8.0.44

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `yuan`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `yuan` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `yuan`;

--
-- Table structure for table `aihuman_config`
--

DROP TABLE IF EXISTS `aihuman_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `aihuman_config` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `model_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `model_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `model_params` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `agent_params` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `status` int DEFAULT NULL,
  `publish` int DEFAULT NULL,
  `create_dept` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aihuman_config`
--

LOCK TABLES `aihuman_config` WRITE;
/*!40000 ALTER TABLE `aihuman_config` DISABLE KEYS */;
INSERT INTO `aihuman_config` VALUES (9,'关爱老婆数字人（梅朵）','梅朵吉祥物','/Live2D/models/梅朵吉祥物/梅朵吉祥物.model3.json','{\n	\"Version\": 3,\n	\"FileReferences\": {\n		\"Moc\": \"梅朵吉祥物.moc3\",\n		\"Textures\": [\n			\"梅朵吉祥物.4096/texture_00.png\",\n			\"梅朵吉祥物.4096/texture_01.png\"\n		],\n		\"Physics\": \"梅朵吉祥物.physics3.json\",\n		\"DisplayInfo\": \"梅朵吉祥物.cdi3.json\",\n		\"MotionSync\": \"梅朵吉祥物.motionsync3.json\",\n		\"Expressions\": [\n			{\n				\"Name\": \"kaixin\",\n				\"File\": \"kaixin.exp3.json\"\n			},\n			{\n				\"Name\": \"maozi\",\n				\"File\": \"maozi.exp3.json\"\n			},\n			{\n				\"Name\": \"mouth open\",\n				\"File\": \"mouth open.exp3.json\"\n			},\n			{\n				\"Name\": \"shibai\",\n				\"File\": \"shibai.exp3.json\"\n			},\n			{\n				\"Name\": \"yinchen\",\n				\"File\": \"yinchen.exp3.json\"\n			}\n		],\n		\"Motions\": {\n			\"\": [\n				{\n					\"File\": \"mouth.motion3.json\"\n				}\n			]\n		}\n	},\n	\"Groups\": [\n		{\n			\"Target\": \"Parameter\",\n			\"Name\": \"LipSync\",\n			\"Ids\": [\n				\"ParamMouthForm\",\n				\"ParamMouthOpenY\"\n			]\n		},\n		{\n			\"Target\": \"Parameter\",\n			\"Name\": \"EyeBlink\",\n			\"Ids\": [\n				\"ParamEyeLOpen\",\n				\"ParamEyeROpen\"\n			]\n		}\n	],\n	\"HitAreas\": []\n}','{\n    \"bot_id\": \"7504596188201746470\",\n    \"user_id\": \"7376476310010937396\",\n    \"stream\": true,\n    \"auto_save_history\": true\n}','2025-09-29 16:36:46','2025-09-29 16:36:46',0,1,NULL,NULL,'1'),(10,'关爱老婆数字人（K）','kei_vowels_pro','/Live2D/models/kei_vowels_pro/kei_vowels_pro.model3.json','{\n	\"Version\": 3,\n	\"FileReferences\": {\n		\"Moc\": \"kei_vowels_pro.moc3\",\n		\"Textures\": [\n			\"kei_vowels_pro.2048/texture_00.png\"\n		],\n		\"Physics\": \"kei_vowels_pro.physics3.json\",\n		\"DisplayInfo\": \"kei_vowels_pro.cdi3.json\",\n		\"MotionSync\": \"kei_vowels_pro.motionsync3.json\",\n		\"Motions\": {\n			\"\": [\n				{\n					\"File\": \"motions/01_kei_en.motion3.json\",\n					\"Sound\": \"sounds/01_kei_en.wav\",\n					\"MotionSync\": \"Vowels_CRI\"\n				},\n				{\n					\"File\": \"motions/01_kei_jp.motion3.json\",\n					\"Sound\": \"sounds/01_kei_jp.wav\",\n					\"MotionSync\": \"Vowels_CRI\"\n				},\n				{\n					\"File\": \"motions/01_kei_ko.motion3.json\",\n					\"Sound\": \"sounds/01_kei_ko.wav\",\n					\"MotionSync\": \"Vowels_CRI\"\n				},\n				{\n					\"File\": \"motions/01_kei_zh.motion3.json\",\n					\"Sound\": \"sounds/01_kei_zh.wav\",\n					\"MotionSync\": \"Vowels_CRI\"\n				}\n			]\n		}\n	},\n	\"Groups\": [\n		{\n			\"Target\": \"Parameter\",\n			\"Name\": \"LipSync\",\n			\"Ids\": []\n		},\n		{\n			\"Target\": \"Parameter\",\n			\"Name\": \"EyeBlink\",\n			\"Ids\": [\n				\"ParamEyeLOpen\",\n				\"ParamEyeROpen\"\n			]\n		}\n	],\n	\"HitAreas\": [\n		{\n			\"Id\": \"HitAreaHead\",\n			\"Name\": \"Head\"\n		}\n	]\n}','3','2025-09-29 16:35:27','2025-09-29 16:35:27',0,1,NULL,NULL,'1'),(11,'关爱老婆数字人（March 7th）','March 7th','/Live2D/models/March 7th/March 7th.model3.json','{\n	\"Version\": 3,\n	\"FileReferences\": {\n		\"Moc\": \"March 7th.moc3\",\n		\"Textures\": [\n			\"March 7th.4096/texture_00.png\",\n			\"March 7th.4096/texture_01.png\"\n		],\n		\"Physics\": \"March 7th.physics3.json\",\n		\"DisplayInfo\": \"March 7th.cdi3.json\",\n		\"Expressions\": [\n			{\n				\"Name\": \"捂脸\",\n				\"File\": \"1.exp3.json\"\n			},\n			{\n				\"Name\": \"比耶\",\n				\"File\": \"2.exp3.json\"\n			},\n			{\n				\"Name\": \"照相\",\n				\"File\": \"3.exp3.json\"\n			},\n			{\n				\"Name\": \"脸红\",\n				\"File\": \"4.exp3.json\"\n			},\n			{\n				\"Name\": \"黑脸\",\n				\"File\": \"5.exp3.json\"\n			},\n			{\n				\"Name\": \"哭\",\n				\"File\": \"6.exp3.json\"\n			},\n			{\n				\"Name\": \"流汗\",\n				\"File\": \"7.exp3.json\"\n			},\n			{\n				\"Name\": \"星星\",\n				\"File\": \"8.exp3.json\"\n			}\n		]\n	},\n	\"Groups\": [\n		{\n			\"Target\": \"Parameter\",\n			\"Name\": \"EyeBlink\",\n			\"Ids\": [\n				\"ParamEyeLOpen\",\n				\"ParamEyeROpen\"\n			]\n		},\n		{\n			\"Target\": \"Parameter\",\n			\"Name\": \"LipSync\",\n			\"Ids\": [\n				\"ParamMouthOpenY\"\n			]\n		}\n	],\n	\"HitAreas\": []\n}','3','2025-09-29 21:09:26','2025-09-29 21:09:28',0,1,NULL,NULL,NULL),(12,'关爱老婆数字人（pachan）','pachan','/Live2D/models/pachan/pachan.model3.json','{\n	\"Version\": 3,\n	\"FileReferences\": {\n		\"Moc\": \"pachirisu anime girl - top half.moc3\",\n		\"Textures\": [\n			\"pachirisu anime girl - top half.4096/texture_00.png\"\n		],\n		\"Physics\": \"pachirisu anime girl - top half.physics3.json\",\n		\"DisplayInfo\": \"pachirisu anime girl - top half.cdi3.json\"\n	},\n	\"Groups\": [\n		{\n			\"Target\": \"Parameter\",\n			\"Name\": \"EyeBlink\",\n			\"Ids\": []\n		},\n		{\n			\"Target\": \"Parameter\",\n			\"Name\": \"LipSync\",\n			\"Ids\": []\n		}\n	]\n}',NULL,'2025-10-05 19:49:56','2025-10-05 19:49:56',0,1,NULL,NULL,NULL),(13,'关爱老婆数字人（230108）','230108','/Live2D/models/230108/230108.model3.json','{\n	\"Version\": 3,\n	\"FileReferences\": {\n		\"Moc\": \"230108.moc3\",\n		\"Textures\": [\n			\"230108.4096/texture_00.png\"\n		],\n		\"Physics\": \"230108.physics3.json\",\n		\"DisplayInfo\": \"230108.cdi3.json\"\n	},\n	\"Groups\": [\n		{\n			\"Target\": \"Parameter\",\n			\"Name\": \"LipSync\",\n			\"Ids\": [\n				\"ParamMouthOpenY\"\n			]\n		},\n		{\n			\"Target\": \"Parameter\",\n			\"Name\": \"EyeBlink\",\n			\"Ids\": [\n				\"ParamEyeLOpen\",\n				\"ParamEyeROpen\"\n			]\n		}\n	]\n}',NULL,'2025-10-06 19:28:20','2025-10-06 19:28:23',0,1,NULL,NULL,NULL);
/*!40000 ALTER TABLE `aihuman_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `aihuman_info`
--

DROP TABLE IF EXISTS `aihuman_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `aihuman_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '交互名称',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '交互内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='AI人类交互信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aihuman_info`
--

LOCK TABLES `aihuman_info` WRITE;
/*!40000 ALTER TABLE `aihuman_info` DISABLE KEYS */;
INSERT INTO `aihuman_info` VALUES (1,'1','1','2025-09-26 18:02:00','2025-09-26 18:02:02','0');
/*!40000 ALTER TABLE `aihuman_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat_config`
--

DROP TABLE IF EXISTS `chat_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat_config` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `category` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '配置类型',
  `config_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '配置名称',
  `config_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '配置值',
  `config_dict` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '说明',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `version` int DEFAULT NULL COMMENT '版本',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `update_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新IP',
  `tenant_id` bigint NOT NULL DEFAULT '0' COMMENT '租户Id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `unique_category_key` (`category`,`config_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1904862904897019906 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='配置信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_config`
--

LOCK TABLES `chat_config` WRITE;
/*!40000 ALTER TABLE `chat_config` DISABLE KEYS */;
INSERT INTO `chat_config` VALUES (1779450794448789505,'chat','apiKey','sk-xx','API 密钥',103,'2024-04-14 18:05:05','1','1','2025-03-31 19:54:16',NULL,NULL,'0',NULL,0),(1779450794872414210,'chat','apiHost','https://api.pandarobot.chat/','API 地址',103,'2024-04-14 18:05:05','1','1','2025-03-31 19:54:16',NULL,NULL,'0',NULL,0),(1779497340548784129,'pay','pid','1000','商户PID',103,'2024-04-14 21:10:02','1','1','2025-05-23 15:11:40',NULL,NULL,'0',NULL,0),(1779497340938854401,'pay','key','xx','商户密钥',103,'2024-04-14 21:10:02','1','1','2025-05-23 15:11:40',NULL,NULL,'0',NULL,0),(1779497341135986690,'pay','payUrl','https://pay.pandarobot.chat/mapi.php','支付地址',103,'2024-04-14 21:10:02','1','1','2025-05-23 15:11:40',NULL,NULL,'0',NULL,0),(1779497341400227842,'pay','notify_url','https://www.pandarobot.chat/pay/notifyUrl','回调地址',103,'2024-04-14 21:10:02','1','1','2025-05-23 15:11:40',NULL,NULL,'0',NULL,0),(1779497341588971522,'pay','return_url','https://www.pandarobot.chat/pay/returnUrl','跳转通知',103,'2024-04-14 21:10:02','1','1','2024-04-28 17:46:31',NULL,NULL,'0',NULL,0),(1779513580331835394,'mail','host','smtp.163.com','主机地址',103,'2024-04-14 22:14:34','1','1','2025-05-23 15:12:45',NULL,NULL,'0',NULL,0),(1779513580658991106,'mail','port','465','主机端口',103,'2024-04-14 22:14:34','1','1','2025-05-23 15:12:46',NULL,NULL,'0',NULL,0),(1779513580919037953,'mail','from','ageerle@163.com','发送方',103,'2024-04-14 22:14:34','1','1','2025-05-23 15:12:46',NULL,NULL,'0',NULL,0),(1779513581107781634,'mail','user','ageerle@163.com','用户名',103,'2024-04-14 22:14:34','1','1','2025-05-23 15:12:46',NULL,NULL,'0',NULL,0),(1779513581309108225,'mail','pass','xxxx','授权码',103,'2024-04-14 22:14:34','1','1','2025-05-23 15:12:46',NULL,NULL,'0',NULL,0),(1786058372188569602,'review','enabled','false','文本审核',103,'2024-05-02 23:41:14','1','1','2025-03-30 22:45:29',NULL,NULL,'0',NULL,0),(1786058372637360129,'review','apiKey','xx','apiKey',103,'2024-05-02 23:41:14','1','1','2025-03-30 22:45:29',NULL,NULL,'0',NULL,0),(1786058372897406977,'review','secretKey','xx','secretKey',103,'2024-05-02 23:41:14','1','1','2025-03-30 22:45:29',NULL,NULL,'0',NULL,0),(1792207511704100866,'sys','name','熊猫助手','网站名称',103,'2024-05-19 22:55:43','1','1','2025-03-26 19:48:33',NULL,NULL,'0',NULL,0),(1792207512089976834,'sys','logoImage','http://panda-1253683406.cos.ap-guangzhou.myqcloud.com/panda/2024/05/19/4c106628754b4bd882a4c002eaa317f5.jpg','网站logo',103,'2024-05-19 22:55:43','1','1','2025-03-26 19:48:33',NULL,NULL,'0',NULL,0),(1792207512412938241,'sys','copyright','ageerle','版权信息',103,'2024-05-19 22:55:43','1','1','2025-03-26 19:48:33',NULL,NULL,'0',NULL,0),(1792207512740093954,'sys','customImage','','客服二维码',103,'2024-05-19 22:55:43','1','1','2025-03-26 19:48:33',NULL,NULL,'0',NULL,0),(1811317732300914689,'mail','mailModel','<p>您此次的验证码为：{code}，有效期为30分钟，请尽快填写!</p><p><br></p>','邮箱模板',103,'2024-07-11 16:32:55','1','1','2024-07-17 17:28:52',NULL,NULL,'0',NULL,0),(1813506141979254785,'mail','mailTitle','【熊猫助手】验证码','邮箱标题',103,'2024-07-17 17:28:52','1','1','2024-07-17 17:28:52',NULL,NULL,'0',NULL,0),(1897610056458412050,'weaviate','protocol','http','协议',103,'2025-03-06 21:10:02','1','1','2025-03-06 21:10:31',NULL,NULL,'0',NULL,0),(1897610056458412051,'weaviate','host','127.0.0.1:6038','地址',103,'2025-03-06 21:10:02','1','1','2025-03-06 21:10:31',NULL,NULL,'0',NULL,0),(1897610056458412052,'weaviate','classname','LocalKnowledge','分类名称',103,'2025-03-06 21:10:02','1','1','2025-03-06 21:10:31',NULL,NULL,'0',NULL,0);
/*!40000 ALTER TABLE `chat_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat_message`
--

DROP TABLE IF EXISTS `chat_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat_message` (
  `id` bigint NOT NULL COMMENT '主键',
  `session_id` bigint DEFAULT NULL COMMENT '会话id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '消息内容',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '对话角色',
  `deduct_cost` double(20,2) DEFAULT '0.00' COMMENT '扣除金额',
  `total_tokens` int DEFAULT '0' COMMENT '累计 Tokens',
  `model_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '模型名称',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `billing_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '计费类型（1-token计费，2-次数计费，null-普通消息）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='聊天消息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_message`
--

LOCK TABLES `chat_message` WRITE;
/*!40000 ALTER TABLE `chat_message` DISABLE KEYS */;
/*!40000 ALTER TABLE `chat_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat_model`
--

DROP TABLE IF EXISTS `chat_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat_model` (
  `id` bigint NOT NULL COMMENT '主键',
  `tenant_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '000000' COMMENT '租户编号',
  `category` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '模型分类',
  `model_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '模型名称',
  `provider_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '模型供应商',
  `model_describe` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '模型描述',
  `model_price` double DEFAULT NULL COMMENT '模型价格',
  `model_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '计费类型',
  `model_show` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否显示',
  `system_prompt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '系统提示词',
  `api_host` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '请求地址',
  `api_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '密钥',
  `api_url` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '请求后缀',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `priority` int DEFAULT '1' COMMENT '模型优先级(值越大优先级越高)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='聊天模型';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_model`
--

LOCK TABLES `chat_model` WRITE;
/*!40000 ALTER TABLE `chat_model` DISABLE KEYS */;
/*!40000 ALTER TABLE `chat_model` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat_pay_order`
--

DROP TABLE IF EXISTS `chat_pay_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat_pay_order` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单编号',
  `order_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单名称',
  `amount` decimal(10,2) NOT NULL COMMENT '金额',
  `payment_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '支付状态',
  `payment_method` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '支付方式',
  `user_id` bigint DEFAULT NULL COMMENT '用户ID',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='支付订单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_pay_order`
--

LOCK TABLES `chat_pay_order` WRITE;
/*!40000 ALTER TABLE `chat_pay_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `chat_pay_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat_session`
--

DROP TABLE IF EXISTS `chat_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat_session` (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  `session_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '会话标题',
  `session_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '会话内容',
  `create_dept` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '部门',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `conversation_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '会话ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='会话管理';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_session`
--

LOCK TABLES `chat_session` WRITE;
/*!40000 ALTER TABLE `chat_session` DISABLE KEYS */;
/*!40000 ALTER TABLE `chat_session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat_usage_token`
--

DROP TABLE IF EXISTS `chat_usage_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat_usage_token` (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户',
  `token` int DEFAULT NULL COMMENT '待结算token',
  `model_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '模型名称',
  `total_token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '累计使用token',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户token使用详情';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_usage_token`
--

LOCK TABLES `chat_usage_token` WRITE;
/*!40000 ALTER TABLE `chat_usage_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `chat_usage_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dev_schema`
--

DROP TABLE IF EXISTS `dev_schema`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dev_schema` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `schema_group_id` bigint DEFAULT NULL COMMENT '分组ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '模型名称',
  `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '模型编码',
  `table_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '表名',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2001288354171138051 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='数据模型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dev_schema`
--

LOCK TABLES `dev_schema` WRITE;
/*!40000 ALTER TABLE `dev_schema` DISABLE KEYS */;
/*!40000 ALTER TABLE `dev_schema` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dev_schema_field`
--

DROP TABLE IF EXISTS `dev_schema_field`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dev_schema_field` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `schema_id` bigint DEFAULT NULL COMMENT '模型ID',
  `schema_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '模型名称',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '字段名称',
  `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '字段编码',
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '字段类型',
  `comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '字段注释',
  `is_pk` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '是否主键（0否 1是）',
  `is_required` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '是否必填（0否 1是）',
  `is_unique` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '是否唯一（0否 1是）',
  `default_value` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '默认值',
  `length` int DEFAULT NULL COMMENT '字段长度',
  `scale` int DEFAULT NULL COMMENT '小数位数',
  `is_list` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '1' COMMENT '是否列表显示（0否 1是）',
  `is_query` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '1' COMMENT '是否查询字段（0否 1是）',
  `is_insert` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '1' COMMENT '是否插入字段（0否 1是）',
  `is_edit` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '1' COMMENT '是否编辑字段（0否 1是）',
  `query_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '查询方式（EQ等于、NE不等于、GT大于、LT小于、LIKE模糊、BETWEEN范围）',
  `html_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'input' COMMENT '显示类型（input输入框、textarea文本域、select下拉框、checkbox复选框、radio单选框、datetime日期控件、image图片上传、upload文件上传、editor富文本编辑器）',
  `dict_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典类型',
  `sort` int DEFAULT NULL COMMENT '排序',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2001288354368270341 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='数据模型字段表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dev_schema_field`
--

LOCK TABLES `dev_schema_field` WRITE;
/*!40000 ALTER TABLE `dev_schema_field` DISABLE KEYS */;
INSERT INTO `dev_schema_field` VALUES (2001288287192297474,2001288287074856961,'sys_oper_log','日志主键','operId','BIGINT','日志主键','1','1','0',NULL,19,NULL,'1','1','0','0','EQ','input','',1,'0',NULL,NULL,'2025-12-17 21:48:04',NULL,'2025-12-17 21:48:04',NULL),(2001288287192297475,2001288287074856961,'sys_oper_log','租户编号','tenantId','VARCHAR','租户编号','0','0','0','000000',20,NULL,'1','1','1','1','EQ','input','',2,'0',NULL,NULL,'2025-12-17 21:48:04',NULL,'2025-12-17 21:48:04',NULL),(2001288287255212034,2001288287074856961,'sys_oper_log','模块标题','title','VARCHAR','模块标题','0','0','0','',50,NULL,'1','1','1','1','EQ','input','',3,'0',NULL,NULL,'2025-12-17 21:48:04',NULL,'2025-12-17 21:48:04',NULL),(2001288287255212035,2001288287074856961,'sys_oper_log','业务类型（0其它 1新增 2修改 3删除）','businessType','INT','业务类型（0其它 1新增 2修改 3删除）','0','0','0','0',10,NULL,'1','1','1','1','EQ','input','',4,'0',NULL,NULL,'2025-12-17 21:48:04',NULL,'2025-12-17 21:48:04',NULL),(2001288287318126594,2001288287074856961,'sys_oper_log','方法名称','method','VARCHAR','方法名称','0','0','0','',100,NULL,'1','1','1','1','EQ','input','',5,'0',NULL,NULL,'2025-12-17 21:48:04',NULL,'2025-12-17 21:48:04',NULL),(2001288287318126595,2001288287074856961,'sys_oper_log','请求方式','requestMethod','VARCHAR','请求方式','0','0','0','',10,NULL,'1','1','1','1','EQ','input','',6,'0',NULL,NULL,'2025-12-17 21:48:05',NULL,'2025-12-17 21:48:05',NULL),(2001288287385235457,2001288287074856961,'sys_oper_log','操作类别（0其它 1后台用户 2手机端用户）','operatorType','INT','操作类别（0其它 1后台用户 2手机端用户）','0','0','0','0',10,NULL,'1','1','1','1','EQ','input','',7,'0',NULL,NULL,'2025-12-17 21:48:05',NULL,'2025-12-17 21:48:05',NULL),(2001288287385235458,2001288287074856961,'sys_oper_log','操作人员','operName','VARCHAR','操作人员','0','0','0','',50,NULL,'1','1','1','1','EQ','input','',8,'0',NULL,NULL,'2025-12-17 21:48:05',NULL,'2025-12-17 21:48:05',NULL),(2001288287385235459,2001288287074856961,'sys_oper_log','部门名称','deptName','VARCHAR','部门名称','0','0','0','',50,NULL,'1','1','1','1','EQ','input','',9,'0',NULL,NULL,'2025-12-17 21:48:05',NULL,'2025-12-17 21:48:05',NULL),(2001288287452344321,2001288287074856961,'sys_oper_log','请求URL','operUrl','VARCHAR','请求URL','0','0','0','',255,NULL,'1','1','1','1','EQ','input','',10,'0',NULL,NULL,'2025-12-17 21:48:05',NULL,'2025-12-17 21:48:05',NULL),(2001288287452344322,2001288287074856961,'sys_oper_log','主机地址','operIp','VARCHAR','主机地址','0','0','0','',128,NULL,'1','1','1','1','EQ','input','',11,'0',NULL,NULL,'2025-12-17 21:48:05',NULL,'2025-12-17 21:48:05',NULL),(2001288287452344323,2001288287074856961,'sys_oper_log','操作地点','operLocation','VARCHAR','操作地点','0','0','0','',255,NULL,'1','1','1','1','EQ','input','',12,'0',NULL,NULL,'2025-12-17 21:48:05',NULL,'2025-12-17 21:48:05',NULL),(2001288287515258882,2001288287074856961,'sys_oper_log','请求参数','operParam','VARCHAR','请求参数','0','0','0','',2000,NULL,'1','1','1','1','EQ','input','',13,'0',NULL,NULL,'2025-12-17 21:48:05',NULL,'2025-12-17 21:48:05',NULL),(2001288287515258883,2001288287074856961,'sys_oper_log','返回参数','jsonResult','VARCHAR','返回参数','0','0','0','',2000,NULL,'1','1','1','1','EQ','input','',14,'0',NULL,NULL,'2025-12-17 21:48:05',NULL,'2025-12-17 21:48:05',NULL),(2001288287515258884,2001288287074856961,'sys_oper_log','操作状态（0正常 1异常）','status','INT','操作状态（0正常 1异常）','0','0','0','0',10,NULL,'1','1','1','1','EQ','input','',15,'0',NULL,NULL,'2025-12-17 21:48:05',NULL,'2025-12-17 21:48:05',NULL),(2001288287582367745,2001288287074856961,'sys_oper_log','错误消息','errorMsg','VARCHAR','错误消息','0','0','0','',2000,NULL,'1','1','1','1','EQ','input','',16,'0',NULL,NULL,'2025-12-17 21:48:05',NULL,'2025-12-17 21:48:05',NULL),(2001288287582367746,2001288287074856961,'sys_oper_log','操作时间','operTime','DATETIME','操作时间','0','0','0',NULL,19,NULL,'1','1','1','1','EQ','datetime','',17,'0',NULL,NULL,'2025-12-17 21:48:05',NULL,'2025-12-17 21:48:05',NULL),(2001288287582367747,2001288287074856961,'sys_oper_log','消耗时间','costTime','BIGINT','消耗时间','0','0','0','0',19,NULL,'1','1','1','1','EQ','input','',18,'0',NULL,NULL,'2025-12-17 21:48:05',NULL,'2025-12-17 21:48:05',NULL),(2001288354238246914,2001288354171138050,'sys_logininfor','访问ID','infoId','BIGINT','访问ID','1','1','0',NULL,19,NULL,'1','1','0','0','EQ','input','',1,'0',NULL,NULL,'2025-12-17 21:48:20',NULL,'2025-12-17 21:48:20',NULL),(2001288354238246915,2001288354171138050,'sys_logininfor','租户编号','tenantId','VARCHAR','租户编号','0','0','0','000000',20,NULL,'1','1','1','1','EQ','input','',2,'0',NULL,NULL,'2025-12-17 21:48:20',NULL,'2025-12-17 21:48:20',NULL),(2001288354238246916,2001288354171138050,'sys_logininfor','用户账号','userName','VARCHAR','用户账号','0','0','0','',50,NULL,'1','1','1','1','EQ','input','',3,'0',NULL,NULL,'2025-12-17 21:48:20',NULL,'2025-12-17 21:48:20',NULL),(2001288354301161474,2001288354171138050,'sys_logininfor','登录IP地址','ipaddr','VARCHAR','登录IP地址','0','0','0','',128,NULL,'1','1','1','1','EQ','input','',4,'0',NULL,NULL,'2025-12-17 21:48:20',NULL,'2025-12-17 21:48:20',NULL),(2001288354301161475,2001288354171138050,'sys_logininfor','登录地点','loginLocation','VARCHAR','登录地点','0','0','0','',255,NULL,'1','1','1','1','EQ','input','',5,'0',NULL,NULL,'2025-12-17 21:48:20',NULL,'2025-12-17 21:48:20',NULL),(2001288354301161476,2001288354171138050,'sys_logininfor','浏览器类型','browser','VARCHAR','浏览器类型','0','0','0','',50,NULL,'1','1','1','1','EQ','input','',6,'0',NULL,NULL,'2025-12-17 21:48:20',NULL,'2025-12-17 21:48:20',NULL),(2001288354368270337,2001288354171138050,'sys_logininfor','操作系统','os','VARCHAR','操作系统','0','0','0','',50,NULL,'1','1','1','1','EQ','input','',7,'0',NULL,NULL,'2025-12-17 21:48:20',NULL,'2025-12-17 21:48:20',NULL),(2001288354368270338,2001288354171138050,'sys_logininfor','登录状态（0成功 1失败）','status','CHAR','登录状态（0成功 1失败）','0','0','0','0',1,NULL,'1','1','1','1','EQ','input','',8,'0',NULL,NULL,'2025-12-17 21:48:20',NULL,'2025-12-17 21:48:20',NULL),(2001288354368270339,2001288354171138050,'sys_logininfor','提示消息','msg','VARCHAR','提示消息','0','0','0','',255,NULL,'1','1','1','1','EQ','input','',9,'0',NULL,NULL,'2025-12-17 21:48:20',NULL,'2025-12-17 21:48:20',NULL),(2001288354368270340,2001288354171138050,'sys_logininfor','访问时间','loginTime','DATETIME','访问时间','0','0','0',NULL,19,NULL,'1','1','1','1','EQ','datetime','',10,'0',NULL,NULL,'2025-12-17 21:48:20',NULL,'2025-12-17 21:48:20',NULL);
/*!40000 ALTER TABLE `dev_schema_field` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dev_schema_group`
--

DROP TABLE IF EXISTS `dev_schema_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dev_schema_group` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分组名称',
  `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分组编码',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图标',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1944346023254429698 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='数据模型分组表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dev_schema_group`
--

LOCK TABLES `dev_schema_group` WRITE;
/*!40000 ALTER TABLE `dev_schema_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `dev_schema_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `graph_build_task`
--

DROP TABLE IF EXISTS `graph_build_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `graph_build_task` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `task_uuid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务UUID',
  `graph_uuid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '图谱UUID',
  `knowledge_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '知识库ID',
  `doc_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文档ID（可选，null表示全量构建）',
  `task_type` tinyint DEFAULT '1' COMMENT '任务类型：1全量构建、2增量更新、3重建',
  `task_status` tinyint DEFAULT '1' COMMENT '任务状态：1待执行、2执行中、3成功、4失败',
  `progress` int DEFAULT '0' COMMENT '进度百分比（0-100）',
  `total_docs` int DEFAULT '0' COMMENT '总文档数',
  `processed_docs` int DEFAULT '0' COMMENT '已处理文档数',
  `extracted_entities` int DEFAULT '0' COMMENT '提取的实体数',
  `extracted_relations` int DEFAULT '0' COMMENT '提取的关系数',
  `error_message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '错误信息',
  `result_summary` json DEFAULT NULL COMMENT '结果摘要(JSON格式)',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_task_uuid` (`task_uuid`) USING BTREE,
  KEY `idx_graph_uuid` (`graph_uuid`) USING BTREE,
  KEY `idx_knowledge_id` (`knowledge_id`) USING BTREE,
  KEY `idx_task_status` (`task_status`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='图谱构建任务表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `graph_build_task`
--

LOCK TABLES `graph_build_task` WRITE;
/*!40000 ALTER TABLE `graph_build_task` DISABLE KEYS */;
/*!40000 ALTER TABLE `graph_build_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `graph_entity_type`
--

DROP TABLE IF EXISTS `graph_entity_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `graph_entity_type` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '实体类型名称',
  `type_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型编码',
  `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '描述',
  `color` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '#1890ff' COMMENT '可视化颜色',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图标',
  `sort` int DEFAULT '0' COMMENT '显示顺序',
  `is_enable` tinyint(1) DEFAULT '1' COMMENT '是否启用（0否 1是）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_type_code` (`type_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='图谱实体类型定义表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `graph_entity_type`
--

LOCK TABLES `graph_entity_type` WRITE;
/*!40000 ALTER TABLE `graph_entity_type` DISABLE KEYS */;
INSERT INTO `graph_entity_type` VALUES (1,'人物','PERSON','人物实体，包括真实人物和虚拟角色','#1890ff','user',1,1,'','2025-11-07 16:33:37','','2025-11-07 16:33:37',NULL),(2,'机构','ORGANIZATION','组织机构，包括公司、政府机构等','#52c41a','bank',2,1,'','2025-11-07 16:33:37','','2025-11-07 16:33:37',NULL),(3,'地点','LOCATION','地理位置，包括国家、城市、地址等','#fa8c16','environment',3,1,'','2025-11-07 16:33:37','','2025-11-07 16:33:37',NULL),(4,'概念','CONCEPT','抽象概念，包括理论、方法等','#722ed1','bulb',4,1,'','2025-11-07 16:33:37','','2025-11-07 16:33:37',NULL),(5,'事件','EVENT','事件记录，包括历史事件、活动等','#eb2f96','calendar',5,1,'','2025-11-07 16:33:37','','2025-11-07 16:33:37',NULL),(6,'产品','PRODUCT','产品或服务','#13c2c2','shopping',6,1,'','2025-11-07 16:33:37','','2025-11-07 16:33:37',NULL),(7,'技术','TECHNOLOGY','技术或工具','#2f54eb','tool',7,1,'','2025-11-07 16:33:37','','2025-11-07 16:33:37',NULL),(8,'文档','DOCUMENT','文档或资料','#faad14','file-text',8,1,'','2025-11-07 16:33:37','','2025-11-07 16:33:37',NULL);
/*!40000 ALTER TABLE `graph_entity_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `graph_query_history`
--

DROP TABLE IF EXISTS `graph_query_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `graph_query_history` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `query_uuid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '查询UUID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `knowledge_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '知识库ID',
  `graph_uuid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图谱UUID',
  `query_text` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '查询文本',
  `query_type` tinyint DEFAULT '1' COMMENT '查询类型：1实体查询、2关系查询、3路径查询、4混合查询',
  `cypher_query` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '生成的Cypher查询',
  `result_count` int DEFAULT '0' COMMENT '结果数量',
  `response_time` int DEFAULT '0' COMMENT '响应时间(ms)',
  `is_success` tinyint(1) DEFAULT '1' COMMENT '是否成功（0否 1是）',
  `error_message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '错误信息',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_query_uuid` (`query_uuid`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE,
  KEY `idx_knowledge_id` (`knowledge_id`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='图谱查询历史表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `graph_query_history`
--

LOCK TABLES `graph_query_history` WRITE;
/*!40000 ALTER TABLE `graph_query_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `graph_query_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `graph_relation_type`
--

DROP TABLE IF EXISTS `graph_relation_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `graph_relation_type` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `relation_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '关系名称',
  `relation_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '关系编码',
  `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '描述',
  `direction` tinyint(1) DEFAULT '1' COMMENT '关系方向：0双向、1单向',
  `style` json DEFAULT NULL COMMENT '可视化样式(JSON格式)',
  `sort` int DEFAULT '0' COMMENT '显示顺序',
  `is_enable` tinyint(1) DEFAULT '1' COMMENT '是否启用（0否 1是）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_relation_code` (`relation_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='图谱关系类型定义表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `graph_relation_type`
--

LOCK TABLES `graph_relation_type` WRITE;
/*!40000 ALTER TABLE `graph_relation_type` DISABLE KEYS */;
INSERT INTO `graph_relation_type` VALUES (1,'属于','BELONGS_TO','隶属关系，表示从属或归属',1,NULL,1,1,'','2025-11-07 16:33:37','','2025-11-07 16:33:37',NULL),(2,'位于','LOCATED_IN','地理位置关系',1,NULL,2,1,'','2025-11-07 16:33:37','','2025-11-07 16:33:37',NULL),(3,'相关','RELATED_TO','一般关联关系',0,NULL,3,1,'','2025-11-07 16:33:37','','2025-11-07 16:33:37',NULL),(4,'导致','CAUSES','因果关系',1,NULL,4,1,'','2025-11-07 16:33:37','','2025-11-07 16:33:37',NULL),(5,'包含','CONTAINS','包含关系',1,NULL,5,1,'','2025-11-07 16:33:37','','2025-11-07 16:33:37',NULL),(6,'提及','MENTIONS','文档提及实体的关系',1,NULL,6,1,'','2025-11-07 16:33:37','','2025-11-07 16:33:37',NULL),(7,'部分','PART_OF','部分关系',1,NULL,7,1,'','2025-11-07 16:33:37','','2025-11-07 16:33:37',NULL),(8,'实例','INSTANCE_OF','实例关系',1,NULL,8,1,'','2025-11-07 16:33:37','','2025-11-07 16:33:37',NULL),(9,'相似','SIMILAR_TO','相似关系',0,NULL,9,1,'','2025-11-07 16:33:37','','2025-11-07 16:33:37',NULL),(10,'前序','PRECEDES','时序关系',1,NULL,10,1,'','2025-11-07 16:33:37','','2025-11-07 16:33:37',NULL),(11,'工作于','WORKS_AT','人物与机构的工作关系',1,NULL,11,1,'','2025-11-07 16:33:37','','2025-11-07 16:33:37',NULL),(12,'创建','CREATED_BY','创建关系',1,NULL,12,1,'','2025-11-07 16:33:37','','2025-11-07 16:33:37',NULL),(13,'使用','USES','使用关系',1,NULL,13,1,'','2025-11-07 16:33:37','','2025-11-07 16:33:37',NULL);
/*!40000 ALTER TABLE `graph_relation_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `graph_statistics`
--

DROP TABLE IF EXISTS `graph_statistics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `graph_statistics` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `graph_uuid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '图谱UUID',
  `stat_date` date NOT NULL COMMENT '统计日期',
  `total_nodes` int DEFAULT '0' COMMENT '总节点数',
  `total_relationships` int DEFAULT '0' COMMENT '总关系数',
  `node_type_distribution` json DEFAULT NULL COMMENT '节点类型分布(JSON格式)',
  `relation_type_distribution` json DEFAULT NULL COMMENT '关系类型分布(JSON格式)',
  `query_count` int DEFAULT '0' COMMENT '查询次数',
  `avg_query_time` int DEFAULT '0' COMMENT '平均查询时间(ms)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_graph_date` (`graph_uuid`,`stat_date`) USING BTREE,
  KEY `idx_stat_date` (`stat_date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='图谱统计信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `graph_statistics`
--

LOCK TABLES `graph_statistics` WRITE;
/*!40000 ALTER TABLE `graph_statistics` DISABLE KEYS */;
/*!40000 ALTER TABLE `graph_statistics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `knowledge_attach`
--

DROP TABLE IF EXISTS `knowledge_attach`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `knowledge_attach` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `kid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '知识库ID',
  `doc_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文档ID',
  `doc_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文档名称',
  `doc_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文档类型',
  `oss_id` int DEFAULT NULL COMMENT '对象存储ID',
  `pic_status` tinyint(1) NOT NULL DEFAULT '10' COMMENT '拆解图片状态10未开始，20进行中，30已完成',
  `pic_anys_status` tinyint(1) NOT NULL DEFAULT '10' COMMENT '分析图片状态10未开始，20进行中，30已完成',
  `vector_status` tinyint(1) NOT NULL DEFAULT '10' COMMENT '写入向量数据库状态10未开始，20进行中，30已完成',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '文档内容',
  `create_dept` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '部门',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_kname` (`kid`,`doc_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1926124407095468035 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='知识库附件';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `knowledge_attach`
--

LOCK TABLES `knowledge_attach` WRITE;
/*!40000 ALTER TABLE `knowledge_attach` DISABLE KEYS */;
/*!40000 ALTER TABLE `knowledge_attach` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `knowledge_base_graph_segment`
--

DROP TABLE IF EXISTS `knowledge_base_graph_segment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `knowledge_base_graph_segment` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `uuid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '片段UUID',
  `kb_uuid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '知识库UUID',
  `kb_item_uuid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '知识库条目UUID',
  `doc_uuid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文档UUID',
  `segment_text` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '片段文本内容',
  `chunk_index` int DEFAULT '0' COMMENT '片段索引（第几个片段）',
  `total_chunks` int DEFAULT '1' COMMENT '总片段数',
  `extraction_status` tinyint DEFAULT '0' COMMENT '抽取状态：0-待处理 1-处理中 2-已完成 3-失败',
  `entity_count` int DEFAULT '0' COMMENT '抽取的实体数量',
  `relation_count` int DEFAULT '0' COMMENT '抽取的关系数量',
  `token_used` int DEFAULT '0' COMMENT '消耗的token数',
  `error_message` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '错误信息',
  `user_id` bigint DEFAULT NULL COMMENT '用户ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_uuid` (`uuid`) USING BTREE,
  KEY `idx_kb_uuid` (`kb_uuid`) USING BTREE,
  KEY `idx_kb_item_uuid` (`kb_item_uuid`) USING BTREE,
  KEY `idx_doc_uuid` (`doc_uuid`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='知识图谱片段表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `knowledge_base_graph_segment`
--

LOCK TABLES `knowledge_base_graph_segment` WRITE;
/*!40000 ALTER TABLE `knowledge_base_graph_segment` DISABLE KEYS */;
/*!40000 ALTER TABLE `knowledge_base_graph_segment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `knowledge_fragment`
--

DROP TABLE IF EXISTS `knowledge_fragment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `knowledge_fragment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `kid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '知识库ID',
  `doc_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文档ID',
  `fid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '知识片段ID',
  `idx` int NOT NULL COMMENT '片段索引下标',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文档内容',
  `create_dept` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '部门',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1926124406994804743 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='知识片段';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `knowledge_fragment`
--

LOCK TABLES `knowledge_fragment` WRITE;
/*!40000 ALTER TABLE `knowledge_fragment` DISABLE KEYS */;
/*!40000 ALTER TABLE `knowledge_fragment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `knowledge_graph_instance`
--

DROP TABLE IF EXISTS `knowledge_graph_instance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `knowledge_graph_instance` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `graph_uuid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '图谱UUID',
  `knowledge_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '关联knowledge_info.kid',
  `graph_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '图谱名称',
  `graph_status` tinyint DEFAULT '10' COMMENT '构建状态：10构建中、20已完成、30失败',
  `node_count` int DEFAULT '0' COMMENT '节点数量',
  `relationship_count` int DEFAULT '0' COMMENT '关系数量',
  `config` json DEFAULT NULL COMMENT '图谱配置(JSON格式)',
  `model_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'LLM模型名称',
  `entity_types` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '实体类型（逗号分隔）',
  `relation_types` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '关系类型（逗号分隔）',
  `error_message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '错误信息',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_graph_uuid` (`graph_uuid`) USING BTREE,
  KEY `idx_knowledge_id` (`knowledge_id`) USING BTREE,
  KEY `idx_graph_status` (`graph_status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='知识图谱实例表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `knowledge_graph_instance`
--

LOCK TABLES `knowledge_graph_instance` WRITE;
/*!40000 ALTER TABLE `knowledge_graph_instance` DISABLE KEYS */;
/*!40000 ALTER TABLE `knowledge_graph_instance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `knowledge_info`
--

DROP TABLE IF EXISTS `knowledge_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `knowledge_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `kid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '知识库ID',
  `uid` bigint NOT NULL DEFAULT '0' COMMENT '用户ID',
  `kname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '知识库名称',
  `share` tinyint DEFAULT NULL COMMENT '是否公开知识库（0 否 1是）',
  `description` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '描述',
  `knowledge_separator` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '知识分隔符',
  `question_separator` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '提问分隔符',
  `overlap_char` int DEFAULT NULL COMMENT '重叠字符数',
  `retrieve_limit` int DEFAULT NULL COMMENT '知识库中检索的条数',
  `text_block_size` int DEFAULT NULL COMMENT '文本块大小',
  `vector_model_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '向量库',
  `embedding_model_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '向量模型',
  `embedding_model_id` bigint DEFAULT NULL COMMENT '模型id',
  `system_prompt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '系统提示词',
  `create_dept` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '部门',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_kid` (`kid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1926124373012553730 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='知识库';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `knowledge_info`
--

LOCK TABLES `knowledge_info` WRITE;
/*!40000 ALTER TABLE `knowledge_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `knowledge_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `knowledge_role`
--

DROP TABLE IF EXISTS `knowledge_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `knowledge_role` (
  `id` bigint NOT NULL COMMENT '知识库角色id',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '知识库角色name',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `group_id` bigint DEFAULT NULL COMMENT '知识库角色组id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='知识库角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `knowledge_role`
--

LOCK TABLES `knowledge_role` WRITE;
/*!40000 ALTER TABLE `knowledge_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `knowledge_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `knowledge_role_group`
--

DROP TABLE IF EXISTS `knowledge_role_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `knowledge_role_group` (
  `id` bigint NOT NULL COMMENT '知识库角色组id',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '知识库角色组name',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='知识库角色组表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `knowledge_role_group`
--

LOCK TABLES `knowledge_role_group` WRITE;
/*!40000 ALTER TABLE `knowledge_role_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `knowledge_role_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `knowledge_role_relation`
--

DROP TABLE IF EXISTS `knowledge_role_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `knowledge_role_relation` (
  `id` bigint NOT NULL COMMENT 'id',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `knowledge_role_id` bigint DEFAULT NULL COMMENT '知识库角色id',
  `knowledge_id` bigint DEFAULT NULL COMMENT '知识库id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='知识库角色与知识库关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `knowledge_role_relation`
--

LOCK TABLES `knowledge_role_relation` WRITE;
/*!40000 ALTER TABLE `knowledge_role_relation` DISABLE KEYS */;
/*!40000 ALTER TABLE `knowledge_role_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mcp_info`
--

DROP TABLE IF EXISTS `mcp_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mcp_info` (
  `mcp_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `server_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '服务器名称',
  `transport_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '链接方式',
  `command` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '命令',
  `arguments` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '参数',
  `env` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '环境',
  `status` tinyint(1) DEFAULT NULL COMMENT '是否启用',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '工具描述',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`mcp_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='mcp工具管理';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mcp_info`
--

LOCK TABLES `mcp_info` WRITE;
/*!40000 ALTER TABLE `mcp_info` DISABLE KEYS */;
INSERT INTO `mcp_info` VALUES (1,'howtocook-mcp','STDIO','npx','[\"-y\", \"howtocook-mcp\"]',NULL,1,NULL,NULL,NULL,'2025-08-11 17:19:25',1,'2025-08-11 18:24:22',NULL);
/*!40000 ALTER TABLE `mcp_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prompt_template`
--

DROP TABLE IF EXISTS `prompt_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prompt_template` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `template_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '提示词模板名称',
  `template_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '提示词模板内容',
  `category` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '提示词分类，knowledge 知识库类型，chat 对话类型，draw绘画类型 ...',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='提示词模板表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prompt_template`
--

LOCK TABLES `prompt_template` WRITE;
/*!40000 ALTER TABLE `prompt_template` DISABLE KEYS */;
/*!40000 ALTER TABLE `prompt_template` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_config`
--

DROP TABLE IF EXISTS `sys_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_config` (
  `config_id` bigint NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `tenant_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '000000' COMMENT '租户编号',
  `config_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='参数配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_config`
--

LOCK TABLES `sys_config` WRITE;
/*!40000 ALTER TABLE `sys_config` DISABLE KEYS */;
INSERT INTO `sys_config` VALUES (1,'000000','主框架页-默认皮肤样式名称','skin-purple','skin-blue','N',103,1,'2023-05-14 15:19:42',1,'2025-03-28 22:30:49','蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow'),(2,'000000','用户管理-账号初始密码','sys.user.initPassword','123456','N',103,1,'2023-05-14 15:19:42',1,'2025-03-28 10:40:44','初始化密码 123456'),(3,'000000','主框架页-侧边栏主题','sys.index.sideTheme','theme-dark','Y',103,1,'2023-05-14 15:19:42',1,'2025-03-28 10:40:50','深色主题theme-dark，浅色主题theme-light'),(5,'000000','账号自助-是否开启用户注册功能','sys.account.registerUser','false','Y',103,1,'2023-05-14 15:19:42',1,'2025-03-28 10:40:57','是否开启注册用户功能（true开启，false关闭）'),(11,'000000','OSS预览列表资源开关','sys.oss.previewListResource','true','Y',103,1,'2023-05-14 15:19:42',NULL,NULL,'true:开启, false:关闭');
/*!40000 ALTER TABLE `sys_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dept`
--

DROP TABLE IF EXISTS `sys_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dept` (
  `dept_id` bigint NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `tenant_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '000000' COMMENT '租户编号',
  `parent_id` bigint DEFAULT '0' COMMENT '父部门id',
  `ancestors` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '部门名称',
  `order_num` int DEFAULT '0' COMMENT '显示顺序',
  `leader_id` bigint DEFAULT NULL,
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=131 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='部门表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dept`
--

LOCK TABLES `sys_dept` WRITE;
/*!40000 ALTER TABLE `sys_dept` DISABLE KEYS */;
INSERT INTO `sys_dept` VALUES (1,'000000',1,'','多租户系统管理',0,1,NULL,NULL,'0','0',NULL,NULL,NULL,1,'2026-01-06 21:28:14'),(124,'2008535393950965760',1,'','一语科技有限公司',0,1780264338471858188,'18782123331','571145417@qq.com','0','0',NULL,1,'2026-01-06 21:45:29',1,'2026-01-07 21:29:11'),(125,'2008535393950965760',124,'','IT开发部',1,NULL,'18728371293','67123923@qq.com','0','2',124,1780264338471858188,'2026-01-07 21:22:59',1,'2026-01-07 21:23:34'),(126,'2008535393950965760',124,'','上海一语科技有限公司',1,NULL,'18238491281','123123@qq.com','0','0',124,1780264338471858188,'2026-01-07 21:36:01',1780264338471858188,'2026-01-07 21:36:01'),(127,'2008535393950965760',126,'','IT开发部',1,1780264338471858190,NULL,NULL,'0','0',124,1780264338471858188,'2026-01-07 21:36:28',1780264338471858188,'2026-01-07 21:36:28'),(128,'2008535393950965760',126,'','IT运维部',2,NULL,NULL,NULL,'0','0',124,1780264338471858188,'2026-01-07 21:36:40',1780264338471858188,'2026-01-07 21:36:40'),(129,'2008535393950965760',126,'','IT测试部',3,NULL,NULL,NULL,'0','0',124,1780264338471858188,'2026-01-07 21:36:56',1780264338471858188,'2026-01-07 21:36:56'),(130,'2008535393950965760',126,'','人事部',4,NULL,NULL,NULL,'0','0',124,1780264338471858188,'2026-01-07 21:37:09',1780264338471858188,'2026-01-07 21:37:09');
/*!40000 ALTER TABLE `sys_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_data`
--

DROP TABLE IF EXISTS `sys_dict_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dict_data` (
  `dict_code` bigint NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `tenant_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '000000' COMMENT '租户编号',
  `dict_sort` int DEFAULT '0' COMMENT '字典排序',
  `dict_label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1971580286589534226 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='字典数据表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_data`
--

LOCK TABLES `sys_dict_data` WRITE;
/*!40000 ALTER TABLE `sys_dict_data` DISABLE KEYS */;
INSERT INTO `sys_dict_data` VALUES (1,'000000',1,'男','0','sys_user_sex','','','Y','0',103,1,'2023-05-14 15:19:41',NULL,NULL,'性别男'),(2,'000000',2,'女','1','sys_user_sex','','','N','0',103,1,'2023-05-14 15:19:41',NULL,NULL,'性别女'),(3,'000000',3,'未知','2','sys_user_sex','',NULL,'N','0',103,1,'2023-05-14 15:19:41',1,'2025-12-21 15:54:25','性别未知'),(4,'000000',1,'显示','0','sys_show_hide','','success','Y','0',103,1,'2023-05-14 15:19:41',1,'2025-12-21 13:47:51','显示菜单'),(5,'000000',2,'隐藏','1','sys_show_hide','','error','N','0',103,1,'2023-05-14 15:19:41',1,'2025-12-21 13:47:58','隐藏菜单'),(12,'000000',1,'是','Y','sys_yes_no','','primary','Y','0',103,1,'2023-05-14 15:19:41',NULL,NULL,'系统默认是'),(13,'000000',2,'否','N','sys_yes_no','','danger','N','0',103,1,'2023-05-14 15:19:41',NULL,NULL,'系统默认否'),(14,'000000',1,'通知','1','sys_notice_type','','warning','Y','0',103,1,'2023-05-14 15:19:41',NULL,NULL,'通知'),(15,'000000',2,'公告','2','sys_notice_type','','success','N','0',103,1,'2023-05-14 15:19:41',NULL,NULL,'公告'),(16,'000000',1,'正常','0','sys_notice_status','','primary','Y','0',103,1,'2023-05-14 15:19:41',NULL,NULL,'正常状态'),(17,'000000',2,'关闭','1','sys_notice_status','','danger','N','0',103,1,'2023-05-14 15:19:41',NULL,NULL,'关闭状态'),(18,'000000',1,'新增','1','sys_oper_type','','default','N','0',103,1,'2023-05-14 15:19:41',1,'2025-12-17 22:38:59','新增操作'),(19,'000000',2,'修改','2','sys_oper_type','','default','N','0',103,1,'2023-05-14 15:19:41',NULL,NULL,'修改操作'),(20,'000000',3,'删除','3','sys_oper_type','','Error','N','0',103,1,'2023-05-14 15:19:41',1,'2025-12-17 22:39:46','删除操作'),(21,'000000',4,'授权','4','sys_oper_type','','Processing','N','0',103,1,'2023-05-14 15:19:41',1,'2025-12-17 22:39:59','授权操作'),(22,'000000',5,'导出','5','sys_oper_type','','Warning','N','0',103,1,'2023-05-14 15:19:41',1,'2025-12-17 22:40:06','导出操作'),(23,'000000',6,'导入','6','sys_oper_type','','warning','N','0',103,1,'2023-05-14 15:19:41',NULL,NULL,'导入操作'),(24,'000000',7,'强退','7','sys_oper_type','','Error','N','0',103,1,'2023-05-14 15:19:41',1,'2025-12-17 22:40:16','强退操作'),(25,'000000',8,'生成代码','8','sys_oper_type','','Warning','N','0',103,1,'2023-05-14 15:19:41',1,'2025-12-17 22:40:21','生成操作'),(26,'000000',9,'清空数据','9','sys_oper_type','','Error','N','0',103,1,'2023-05-14 15:19:41',1,'2025-12-17 22:40:25','清空操作'),(27,'000000',1,'成功','0','sys_common_status','','primary','N','0',103,1,'2023-05-14 15:19:41',NULL,NULL,'正常状态'),(28,'000000',2,'失败','1','sys_common_status','','danger','N','0',103,1,'2023-05-14 15:19:41',NULL,NULL,'停用状态'),(29,'000000',99,'其他','0','sys_oper_type','','default','N','0',103,1,'2023-05-14 15:19:41',1,'2025-12-17 22:40:28','其他操作'),(33,'000000',1,'成功','0','sys_opre_status',NULL,'Success','Y','0',NULL,NULL,'2025-12-17 22:09:09',NULL,'2025-12-17 22:09:09','成功'),(34,'000000',2,'失败','1','sys_opre_status',NULL,'Error','N','0',NULL,NULL,'2025-12-17 22:09:31',NULL,'2025-12-17 22:09:31','失败'),(35,'000000',1,'免费用户','0','sys_user_grade','','info','N','0',103,1,'2024-04-04 13:27:15',1,'2024-04-04 13:30:09',''),(36,'000000',0,'token计费','1','sys_model_billing','','primary','N','0',103,1,'2024-04-05 12:49:03',1,'2024-04-21 00:05:41',''),(37,'000000',0,'次数计费','2','sys_model_billing','','success','N','0',103,1,'2024-04-05 12:49:22',1,'2024-04-05 12:49:22',''),(38,'000000',0,'未支付','1','pay_state','','info','N','0',103,1,'2024-04-16 23:57:49',1,'2024-04-16 23:58:29',''),(39,'000000',2,'已支付','2','pay_state','','success','N','0',103,1,'2024-04-16 23:58:11',1,'2024-04-16 23:58:21',''),(40,'000000',0,'知识库','vector','prompt_template_type',NULL,'','N','0',103,1,'2025-06-12 17:29:05',1,'2025-06-12 17:29:05',NULL),(41,'000000',1,'中转模型-chat','chat','chat_model_category',NULL,'','N','0',103,1,'2025-06-26 21:21:28',1,'2025-06-26 21:22:26',NULL),(42,'000000',1,'本地部署模型-ollama','ollama','chat_model_category',NULL,'','N','0',103,1,'2025-06-26 21:24:22',1,'2025-06-26 21:24:22',NULL),(43,'000000',1,'DIFY-dify','dify','chat_model_category',NULL,'','N','0',103,1,'2025-06-26 21:24:43',1,'2025-06-26 21:24:43',NULL),(44,'000000',1,'扣子-coze','coze','chat_model_category',NULL,'','N','0',103,1,'2025-06-26 21:24:58',1,'2025-06-26 21:24:58',NULL),(45,'000000',1,'智谱清言-zhipu','zhipu','chat_model_category',NULL,'','N','0',103,1,'2025-06-26 21:25:10',1,'2025-06-26 21:25:10',NULL),(46,'000000',1,'深度求索-deepseek','deepseek','chat_model_category',NULL,'','N','0',103,1,'2025-06-26 21:25:23',1,'2025-06-26 21:25:23',NULL),(47,'000000',1,'通义千问-qianwen','qianwen','chat_model_category',NULL,'','N','0',103,1,'2025-06-26 21:25:36',1,'2025-06-26 21:25:36',NULL),(48,'000000',1,'知识库向量模型-vector','vector','chat_model_category',NULL,'','N','0',103,1,'2025-06-26 21:25:48',1,'2025-06-26 21:25:48',NULL),(49,'000000',1,'图片识别模型-image','image','chat_model_category',NULL,'','N','0',103,1,'2025-06-26 21:26:01',1,'2025-06-26 21:26:01',NULL),(50,'000000',1,'FASTGPT-fastgpt','fastgpt','chat_model_category',NULL,'','N','0',103,1,'2025-07-03 10:13:46',1,'2025-07-03 10:13:46',NULL),(51,'000000',0,'STDIO','STDIO','mcp_transport_type',NULL,'','N','0',NULL,NULL,'2025-08-09 16:33:56',1,'2025-08-09 16:34:19',NULL),(52,'000000',1,'SSE','SSE','mcp_transport_type',NULL,'','N','0',NULL,NULL,'2025-08-09 16:34:32',NULL,'2025-08-09 16:34:32',NULL),(53,'000000',2,'HTTP','HTTP','mcp_transport_type',NULL,'','N','0',NULL,NULL,'2025-08-09 16:36:22',NULL,'2025-08-09 16:36:22',NULL),(54,'000000',0,'未发布','0','aihuman_is_publish',NULL,'#949494','N','0',NULL,NULL,'2025-09-26 22:18:46',NULL,'2025-09-26 22:18:46',NULL),(55,'000000',1,'已发布','1','aihuman_is_publish',NULL,'#00a89d','N','0',NULL,NULL,'2025-09-26 22:19:05',1,'2025-09-26 22:19:25',NULL),(56,'000000',0,'土豆','3','',NULL,NULL,'N','0',NULL,NULL,'2025-12-15 21:53:48',NULL,'2025-12-15 21:53:48','test'),(57,'000000',0,'test','3','',NULL,NULL,'N','0',NULL,NULL,'2025-12-15 21:55:02',NULL,'2025-12-15 21:55:02','123'),(58,'000000',0,'test','3','',NULL,NULL,'N','0',NULL,NULL,'2025-12-15 21:56:58',NULL,'2025-12-15 21:56:58',NULL),(59,'000000',0,'123','123','',NULL,NULL,'N','0',NULL,NULL,'2025-12-15 22:19:26',NULL,'2025-12-15 22:19:26',NULL),(60,'000000',0,'123','123','',NULL,NULL,'N','0',NULL,NULL,'2025-12-15 22:20:15',NULL,'2025-12-15 22:20:15',NULL),(61,'000000',1,'启用','0','sys_normal_disable',NULL,'success','Y','0',NULL,NULL,'2025-12-16 21:02:05',NULL,'2025-12-16 21:02:05','启用状态'),(62,'000000',2,'禁用','1','sys_normal_disable',NULL,'error','N','0',NULL,NULL,'2025-12-16 21:02:40',NULL,'2025-12-16 21:02:40','禁用状态'),(63,'000000',1,'草稿','DRAFT','wf_definition_status',NULL,'processing','Y','0',NULL,NULL,'2026-01-01 20:42:15',1,'2026-01-01 20:43:28','草稿'),(64,'000000',2,'已发布','PUBLISHED','wf_definition_status',NULL,'success','N','0',NULL,NULL,'2026-01-01 20:42:39',NULL,'2026-01-01 20:42:39','已发布'),(65,'000000',3,'已停用','DISABLED','wf_definition_status',NULL,'error','N','0',NULL,NULL,'2026-01-01 20:43:22',NULL,'2026-01-01 20:43:22','已停用');
/*!40000 ALTER TABLE `sys_dict_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_type`
--

DROP TABLE IF EXISTS `sys_dict_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dict_type` (
  `dict_id` bigint NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `tenant_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '000000' COMMENT '租户编号',
  `dict_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典类型',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`) USING BTREE,
  UNIQUE KEY `tenant_id` (`tenant_id`,`dict_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1971579935501123592 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='字典类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_type`
--

LOCK TABLES `sys_dict_type` WRITE;
/*!40000 ALTER TABLE `sys_dict_type` DISABLE KEYS */;
INSERT INTO `sys_dict_type` VALUES (1,'000000','用户性别','sys_user_sex','0',103,1,'2023-05-14 15:19:41',NULL,NULL,'用户性别列表'),(2,'000000','菜单状态','sys_show_hide','0',103,1,'2023-05-14 15:19:41',NULL,NULL,'菜单状态列表'),(3,'000000','系统开关','sys_normal_disable','0',103,1,'2023-05-14 15:19:41',NULL,NULL,'系统开关列表'),(6,'000000','系统是否','sys_yes_no','0',103,1,'2023-05-14 15:19:41',NULL,NULL,'系统是否列表'),(7,'000000','通知类型','sys_notice_type','0',103,1,'2023-05-14 15:19:41',NULL,NULL,'通知类型列表'),(8,'000000','通知状态','sys_notice_status','0',103,1,'2023-05-14 15:19:41',NULL,NULL,'通知状态列表'),(9,'000000','操作类型','sys_oper_type','0',103,1,'2023-05-14 15:19:41',NULL,NULL,'操作类型列表'),(10,'000000','系统状态','sys_common_status','0',103,1,'2023-05-14 15:19:41',NULL,NULL,'登录状态列表'),(11,'000000','操作状态','sys_opre_status','0',NULL,NULL,'2025-12-17 22:08:50',NULL,'2025-12-17 22:08:50','操作状态'),(12,'000000','流程定义状态','wf_definition_status','0',NULL,NULL,'2026-01-01 20:38:48',NULL,'2026-01-01 20:38:48','流程定义状态'),(13,'000000','用户等级','sys_user_grade','0',103,1,'2024-04-04 13:26:13',1,'2024-04-04 13:26:13',''),(14,'000000','模型计费方式','sys_model_billing','0',103,1,'2024-04-05 12:48:37',1,'2024-04-08 11:22:18','模型计费方式'),(15,'000000','支付状态','pay_state','0',103,1,'2024-04-16 23:56:00',1,'2025-03-29 15:21:57','支付状态'),(16,'000000','状态类型','status_type','0',103,1,'2025-03-26 00:06:31',1,'2025-03-26 00:06:31',NULL),(17,'000000','提示词模板分类','prompt_template_type','0',103,1,'2025-06-12 17:28:07',1,'2025-06-12 17:28:07','提示词模板类型'),(18,'000000','模型分类','chat_model_category','0',103,1,'2025-06-26 21:20:39',1,'2025-06-26 21:20:39','模型分类'),(19,'000000','mcp链接方式','mcp_transport_type','0',NULL,NULL,'2025-08-09 16:33:16',NULL,'2025-08-09 16:33:16',NULL),(20,'000000','发布状态','aihuman_is_publish','0',NULL,NULL,'2025-09-26 22:17:41',NULL,'2025-09-26 22:17:41','0 代表未发布，1代表发布');
/*!40000 ALTER TABLE `sys_dict_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_file_info`
--

DROP TABLE IF EXISTS `sys_file_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_file_info` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '文件id',
  `url` varchar(512) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '文件访问地址',
  `size` bigint DEFAULT NULL COMMENT '文件大小，单位字节',
  `filename` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '文件名称',
  `original_filename` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '原始文件名',
  `base_path` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '基础存储路径',
  `path` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '存储路径',
  `ext` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '文件扩展名',
  `user_id` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '文件所属用户',
  `file_type` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '文件类型',
  `attr` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci COMMENT '附加属性',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `version` int DEFAULT NULL COMMENT '版本',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `update_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新IP',
  `tenant_id` bigint NOT NULL DEFAULT '0' COMMENT '租户Id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='文件记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_file_info`
--

LOCK TABLES `sys_file_info` WRITE;
/*!40000 ALTER TABLE `sys_file_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_file_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_logininfor`
--

DROP TABLE IF EXISTS `sys_logininfor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_logininfor` (
  `info_id` bigint NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `tenant_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '000000' COMMENT '租户编号',
  `user_id` bigint DEFAULT NULL COMMENT '用户Id',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '用户账号',
  `ipaddr` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '操作系统',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '提示消息',
  `login_time` datetime DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`) USING BTREE,
  KEY `idx_sys_logininfor_s` (`status`) USING BTREE,
  KEY `idx_sys_logininfor_lt` (`login_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2002277166233899298 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='系统访问记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_logininfor`
--

LOCK TABLES `sys_logininfor` WRITE;
/*!40000 ALTER TABLE `sys_logininfor` DISABLE KEYS */;
INSERT INTO `sys_logininfor` VALUES (2002277166233899261,'00000',1,'admin','0:0:0:0:0:0:0:1','内网IP','MSEdge','Windows 10 or Windows Server 2016','0','退出成功','2026-01-06 21:45:52'),(2002277166233899262,'2008535393950965760',1780264338471858188,'U732864','0:0:0:0:0:0:0:1','内网IP','MSEdge','Windows 10 or Windows Server 2016','1','密码输入错误1次','2026-01-06 21:45:58'),(2002277166233899263,'2008535393950965760',1780264338471858188,'U732864','0:0:0:0:0:0:0:1','内网IP','MSEdge','Windows 10 or Windows Server 2016','0','登录成功','2026-01-06 21:46:03'),(2002277166233899264,'2008535393950965760',1780264338471858188,'U732864','0:0:0:0:0:0:0:1','内网IP','MSEdge','Windows 10 or Windows Server 2016','0','退出成功','2026-01-06 21:52:56'),(2002277166233899265,'00000',1,'admin','0:0:0:0:0:0:0:1','内网IP','MSEdge','Windows 10 or Windows Server 2016','0','登录成功','2026-01-06 21:52:59'),(2002277166233899266,'00000',1,'admin','0:0:0:0:0:0:0:1','内网IP','MSEdge','Windows 10 or Windows Server 2016','0','退出成功','2026-01-06 21:53:38'),(2002277166233899267,'2008535393950965760',1780264338471858188,'U732864','0:0:0:0:0:0:0:1','内网IP','MSEdge','Windows 10 or Windows Server 2016','0','登录成功','2026-01-06 21:53:40'),(2002277166233899268,'2008535393950965760',1780264338471858188,'U732864','0:0:0:0:0:0:0:1','内网IP','MSEdge','Windows 10 or Windows Server 2016','0','退出成功','2026-01-06 22:09:51'),(2002277166233899269,'2008535393950965760',1780264338471858188,'U732864','0:0:0:0:0:0:0:1','内网IP','MSEdge','Windows 10 or Windows Server 2016','0','登录成功','2026-01-06 22:09:53'),(2002277166233899270,'2008535393950965760',1780264338471858188,'U732864','0:0:0:0:0:0:0:1','内网IP','MSEdge','Windows 10 or Windows Server 2016','0','退出成功','2026-01-07 20:42:11'),(2002277166233899271,'2008535393950965760',1780264338471858188,'U732864','0:0:0:0:0:0:0:1','内网IP','MSEdge','Windows 10 or Windows Server 2016','0','登录成功','2026-01-07 20:42:12'),(2002277166233899272,'2008535393950965760',1780264338471858188,'U732864','0:0:0:0:0:0:0:1','内网IP','MSEdge','Windows 10 or Windows Server 2016','0','退出成功','2026-01-07 20:43:05'),(2002277166233899273,'00000',1,'admin','0:0:0:0:0:0:0:1','内网IP','MSEdge','Windows 10 or Windows Server 2016','0','登录成功','2026-01-07 20:43:07'),(2002277166233899274,'00000',1,'admin','0:0:0:0:0:0:0:1','内网IP','MSEdge','Windows 10 or Windows Server 2016','0','退出成功','2026-01-07 20:43:27'),(2002277166233899275,'2008535393950965760',1780264338471858188,'U732864','0:0:0:0:0:0:0:1','内网IP','MSEdge','Windows 10 or Windows Server 2016','0','登录成功','2026-01-07 20:43:30'),(2002277166233899276,'2008535393950965760',1780264338471858188,'U732864','0:0:0:0:0:0:0:1','内网IP','MSEdge','Windows 10 or Windows Server 2016','0','退出成功','2026-01-07 20:44:08'),(2002277166233899277,'00000',1,'admin','0:0:0:0:0:0:0:1','内网IP','MSEdge','Windows 10 or Windows Server 2016','0','登录成功','2026-01-07 20:44:13'),(2002277166233899278,'00000',1,'admin','0:0:0:0:0:0:0:1','内网IP','MSEdge','Windows 10 or Windows Server 2016','0','退出成功','2026-01-07 20:46:13'),(2002277166233899279,'2008535393950965760',1780264338471858188,'U732864','0:0:0:0:0:0:0:1','内网IP','MSEdge','Windows 10 or Windows Server 2016','0','登录成功','2026-01-07 20:46:15'),(2002277166233899280,'2008535393950965760',1780264338471858188,'U732864','0:0:0:0:0:0:0:1','内网IP','MSEdge','Windows 10 or Windows Server 2016','0','退出成功','2026-01-07 20:47:01'),(2002277166233899281,'00000',1,'admin','0:0:0:0:0:0:0:1','内网IP','MSEdge','Windows 10 or Windows Server 2016','0','登录成功','2026-01-07 20:47:03'),(2002277166233899282,'00000',1,'admin','0:0:0:0:0:0:0:1','内网IP','MSEdge','Windows 10 or Windows Server 2016','0','退出成功','2026-01-07 20:47:30'),(2002277166233899283,'2008535393950965760',1780264338471858188,'U732864','0:0:0:0:0:0:0:1','内网IP','MSEdge','Windows 10 or Windows Server 2016','0','登录成功','2026-01-07 20:47:35'),(2002277166233899284,'2008535393950965760',1780264338471858188,'U732864','0:0:0:0:0:0:0:1','内网IP','MSEdge','Windows 10 or Windows Server 2016','0','退出成功','2026-01-07 21:07:53'),(2002277166233899285,'2008535393950965760',1780264338471858188,'U732864','0:0:0:0:0:0:0:1','内网IP','MSEdge','Windows 10 or Windows Server 2016','0','登录成功','2026-01-07 21:07:58'),(2002277166233899286,'2008535393950965760',1780264338471858188,'U732864','0:0:0:0:0:0:0:1','内网IP','MSEdge','Windows 10 or Windows Server 2016','0','退出成功','2026-01-07 21:19:51'),(2002277166233899287,'2008535393950965760',1780264338471858188,'U732864','0:0:0:0:0:0:0:1','内网IP','MSEdge','Windows 10 or Windows Server 2016','0','登录成功','2026-01-07 21:21:45'),(2002277166233899288,'2008535393950965760',1780264338471858188,'U732864','0:0:0:0:0:0:0:1','内网IP','MSEdge','Windows 10 or Windows Server 2016','0','退出成功','2026-01-07 21:44:23'),(2002277166233899289,'2008535393950965760',1780264338471858190,'U495360','0:0:0:0:0:0:0:1','内网IP','MSEdge','Windows 10 or Windows Server 2016','0','登录成功','2026-01-07 21:44:26'),(2002277166233899290,'2008535393950965760',1780264338471858190,'U495360','0:0:0:0:0:0:0:1','内网IP','MSEdge','Windows 10 or Windows Server 2016','0','退出成功','2026-01-07 21:44:35'),(2002277166233899291,'2008535393950965760',1780264338471858188,'U732864','0:0:0:0:0:0:0:1','内网IP','MSEdge','Windows 10 or Windows Server 2016','0','登录成功','2026-01-07 21:44:38'),(2002277166233899292,'2008535393950965760',1780264338471858188,'U732864','0:0:0:0:0:0:0:1','内网IP','MSEdge','Windows 10 or Windows Server 2016','0','退出成功','2026-01-07 22:17:27'),(2002277166233899293,'2008535393950965760',1780264338471858188,'U732864','0:0:0:0:0:0:0:1','内网IP','MSEdge','Windows 10 or Windows Server 2016','0','登录成功','2026-01-07 22:17:33'),(2002277166233899294,'2008535393950965760',1780264338471858188,'U732864','0:0:0:0:0:0:0:1','内网IP','MSEdge','Windows 10 or Windows Server 2016','0','退出成功','2026-01-07 22:19:44'),(2002277166233899295,'2008535393950965760',1780264338471858188,'U732864','0:0:0:0:0:0:0:1','内网IP','MSEdge','Windows 10 or Windows Server 2016','0','登录成功','2026-01-07 22:19:50'),(2002277166233899296,'2008535393950965760',1780264338471858188,'U732864','0:0:0:0:0:0:0:1','内网IP','MSEdge','Windows 10 or Windows Server 2016','0','退出成功','2026-01-07 22:24:00'),(2002277166233899297,'00000',1,'admin','0:0:0:0:0:0:0:1','内网IP','MSEdge','Windows 10 or Windows Server 2016','0','登录成功','2026-01-07 22:24:03');
/*!40000 ALTER TABLE `sys_logininfor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_menu` (
  `menu_id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `route_name` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `parent_id` bigint DEFAULT '0' COMMENT '父菜单ID',
  `order_num` int DEFAULT '0' COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '组件路径',
  `query_param` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '路由参数',
  `is_frame` int DEFAULT '1' COMMENT '是否为外链（0是 1否）',
  `is_cache` int DEFAULT '0' COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '显示状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '#' COMMENT '菜单图标',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '备注',
  `scope` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2005891256378392583 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='菜单权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES (1,'系统管理','system',0,5,'/system','','',1,0,'M','0','0','','SettingOutlined',103,1,'2023-05-14 15:19:39',1,'2025-09-26 20:19:31','系统管理目录','BOTH'),(101,'角色管理','role',1,2,'/system/role','./system/role','',1,0,'C','0','0','system:role:list','',103,1,'2023-05-14 15:19:39',1,'2024-10-07 21:04:59','角色管理菜单','BOTH'),(102,'菜单管理','menu',1,7,'/system/menu','./system/menu','',1,0,'C','0','0','system:menu:list','',103,1,'2023-05-14 15:19:39',1,'2025-12-22 20:42:20','菜单管理菜单','BOTH'),(103,'部门管理','dept',1,4,'/system/dept','./system/dept','',1,0,'C','0','0','system:dept:list','mdi:company',103,1,'2023-05-14 15:19:39',1,'2024-10-07 21:07:38','部门管理菜单','BOTH'),(104,'岗位管理','post',1,3,'/system/post','./system/post','',1,0,'C','0','0','system:post:list','post',103,1,'2023-05-14 15:19:39',1,'2025-12-22 20:41:54','岗位管理菜单','BOTH'),(105,'字典管理','dict',1,6,'/system/dict','./system/dict','',1,0,'C','0','0','system:dict:list','',103,1,'2023-05-14 15:19:40',1,'2025-12-20 18:35:56','字典管理菜单','BOTH'),(108,'日志管理','log',1,9,'/system/log','','',1,0,'M','0','0','','',103,1,'2023-05-14 15:19:40',1,'2024-10-07 21:10:41','日志管理菜单','BOTH'),(200,'租户管理','tenant',1,5,'/system/tenant','./system/tenant','',1,0,'C','0','0','system:tenant:list','#',103,1,'2023-05-14 15:19:39',1,'2025-12-22 20:42:05','租户管理菜单','PLATFORM'),(500,'操作日志','oprelog',108,1,'/system/log/oprelog','./system/log/oprelog','',1,0,'C','0','0','system:oprelog:list','',103,1,'2023-05-14 15:19:40',1,'2025-12-20 18:43:50','操作日志菜单','BOTH'),(501,'登录日志','loginfor',108,2,'/system/log/loginfor','./system/log/loginfor','',1,0,'C','0','0','system:loginfor:list','',103,1,'2023-05-14 15:19:40',1,'2025-12-20 18:35:30','登录日志菜单','BOTH'),(503,'工作流管理','workflow',0,6,'/workflow',NULL,NULL,1,0,'M','0','0','','MergeOutlined',NULL,NULL,'2025-12-30 14:42:08',1,'2025-12-30 19:08:25','工作流管理','BOTH'),(1001,'用户查询','',2000,1,'','','',1,0,'F','0','0','system:user:query','#',103,1,'2023-05-14 15:19:40',NULL,NULL,'','BOTH'),(1002,'用户新增','',2000,2,'','','',1,0,'F','0','0','system:user:add','#',103,1,'2023-05-14 15:19:40',NULL,NULL,'','BOTH'),(1003,'用户修改','',2000,3,'','','',1,0,'F','0','0','system:user:edit','#',103,1,'2023-05-14 15:19:40',NULL,NULL,'','BOTH'),(1004,'用户删除','',2000,4,'','','',1,0,'F','0','0','system:user:remove','#',103,1,'2023-05-14 15:19:40',NULL,NULL,'','BOTH'),(1005,'用户导出','',2000,5,'','','',1,0,'F','0','0','system:user:export','#',103,1,'2023-05-14 15:19:40',NULL,NULL,'','BOTH'),(1006,'用户导入','',2000,6,'','','',1,0,'F','0','0','system:user:import','#',103,1,'2023-05-14 15:19:40',NULL,NULL,'','BOTH'),(1007,'重置密码','',2000,7,'','','',1,0,'F','0','0','system:user:resetPwd','#',103,1,'2023-05-14 15:19:40',NULL,NULL,'','BOTH'),(1008,'角色查询',NULL,101,1,'','','',1,0,'F','0','0','system:role:query','',103,1,'2023-05-14 15:19:40',NULL,NULL,'','BOTH'),(1009,'角色新增',NULL,101,2,'','','',1,0,'F','0','0','system:role:add','',103,1,'2023-05-14 15:19:40',NULL,NULL,'','BOTH'),(1010,'角色修改',NULL,101,3,'','','',1,0,'F','0','0','system:role:edit','',103,1,'2023-05-14 15:19:40',NULL,NULL,'','BOTH'),(1011,'角色删除',NULL,101,4,'','','',1,0,'F','0','0','system:role:remove','',103,1,'2023-05-14 15:19:40',NULL,NULL,'','BOTH'),(1012,'角色导出',NULL,101,5,'','','',1,0,'F','0','0','system:role:export','',103,1,'2023-05-14 15:19:40',NULL,NULL,'','BOTH'),(1013,'菜单查询',NULL,102,1,'','','',1,0,'F','0','0','system:menu:list','',103,1,'2023-05-14 15:19:40',1,'2025-12-21 17:53:02','','BOTH'),(1014,'菜单新增',NULL,102,2,'','','',1,0,'F','0','0','system:menu:add','',103,1,'2023-05-14 15:19:40',NULL,NULL,'','BOTH'),(1015,'菜单修改',NULL,102,3,'','','',1,0,'F','0','0','system:menu:edit','',103,1,'2023-05-14 15:19:40',NULL,NULL,'','BOTH'),(1016,'菜单删除',NULL,102,4,'','','',1,0,'F','0','0','system:menu:remove','',103,1,'2023-05-14 15:19:40',NULL,NULL,'','BOTH'),(1017,'部门查询',NULL,103,1,'','','',1,0,'F','0','0','system:dept:query','#',103,1,'2023-05-14 15:19:40',NULL,NULL,'','BOTH'),(1018,'部门新增',NULL,103,2,'','','',1,0,'F','0','0','system:dept:add','#',103,1,'2023-05-14 15:19:40',NULL,NULL,'','BOTH'),(1019,'部门修改',NULL,103,3,'','','',1,0,'F','0','0','system:dept:edit','#',103,1,'2023-05-14 15:19:40',NULL,NULL,'','BOTH'),(1020,'部门删除',NULL,103,4,'','','',1,0,'F','0','0','system:dept:remove','#',103,1,'2023-05-14 15:19:40',NULL,NULL,'','BOTH'),(1021,'岗位查询',NULL,104,1,'','','',1,0,'F','0','0','system:post:query','#',103,1,'2023-05-14 15:19:40',NULL,NULL,'','BOTH'),(1022,'岗位新增',NULL,104,2,'','','',1,0,'F','0','0','system:post:add','#',103,1,'2023-05-14 15:19:40',NULL,NULL,'','BOTH'),(1023,'岗位修改',NULL,104,3,'','','',1,0,'F','0','0','system:post:edit','#',103,1,'2023-05-14 15:19:40',NULL,NULL,'','BOTH'),(1024,'岗位删除',NULL,104,4,'','','',1,0,'F','0','0','system:post:remove','#',103,1,'2023-05-14 15:19:40',NULL,NULL,'','BOTH'),(1026,'字典查询',NULL,105,1,'#','','',1,0,'F','0','0','system:dict:query','',103,1,'2023-05-14 15:19:40',NULL,NULL,'','BOTH'),(1027,'字典新增',NULL,105,2,'#','','',1,0,'F','0','0','system:dict:add','',103,1,'2023-05-14 15:19:40',NULL,NULL,'','BOTH'),(1028,'字典修改',NULL,105,3,'#','','',1,0,'F','0','0','system:dict:edit','',103,1,'2023-05-14 15:19:40',NULL,NULL,'','BOTH'),(1029,'字典删除',NULL,105,4,'#','','',1,0,'F','0','0','system:dict:remove','',103,1,'2023-05-14 15:19:40',NULL,NULL,'','BOTH'),(1030,'字典导出',NULL,105,5,'#','','',1,0,'F','0','0','system:dict:export','',103,1,'2023-05-14 15:19:40',NULL,NULL,'','BOTH'),(1040,'操作查询',NULL,500,1,'#','','',1,0,'F','0','0','system:operlog:query','',103,1,'2023-05-14 15:19:40',NULL,NULL,'','BOTH'),(1041,'操作删除',NULL,500,2,'#','','',1,0,'F','0','0','system:operlog:remove','',103,1,'2023-05-14 15:19:40',NULL,NULL,'','BOTH'),(1042,'日志导出',NULL,500,4,'#','','',1,0,'F','0','0','system:operlog:export','',103,1,'2023-05-14 15:19:40',NULL,NULL,'','BOTH'),(1043,'登录查询',NULL,501,1,'#','','',1,0,'F','0','0','system:loginfor:query','',103,1,'2023-05-14 15:19:40',NULL,NULL,'','BOTH'),(1044,'登录删除',NULL,501,2,'#','','',1,0,'F','0','0','system:loginfor:remove','',103,1,'2023-05-14 15:19:40',NULL,NULL,'','BOTH'),(1045,'日志导出',NULL,501,3,'#','','',1,0,'F','0','0','system:loginfor:export','',103,1,'2023-05-14 15:19:40',NULL,NULL,'','BOTH'),(1900,'租户查询',NULL,200,1,'','','',1,0,'F','0','0','system:tenant:query','#',103,1,'2023-05-14 15:19:40',NULL,NULL,'','PLATFORM'),(1901,'租户新增',NULL,200,2,'','','',1,0,'F','0','0','system:tenant:add','#',103,1,'2023-05-14 15:19:40',NULL,NULL,'','PLATFORM'),(1902,'租户修改',NULL,200,3,'','','',1,0,'F','0','0','system:tenant:edit','#',103,1,'2023-05-14 15:19:40',NULL,NULL,'','PLATFORM'),(1903,'租户删除',NULL,200,4,'','','',1,0,'F','0','0','system:tenant:remove','#',103,1,'2023-05-14 15:19:40',NULL,NULL,'','PLATFORM'),(2000,'用户管理','user',1,1,'/system/user','./system/user',NULL,1,0,'C','0','0','system:user:list','#',NULL,NULL,'2025-12-20 17:35:02',NULL,'2025-12-20 17:35:02','','BOTH'),(2001,'欢迎','welcome',0,1,'/welcome','./welcome',NULL,1,0,'M','0','0',NULL,'SmileOutlined',NULL,NULL,'2025-12-20 17:38:16',NULL,'2025-12-20 17:38:16','','BOTH'),(2002,'Dashboard','dashboard',0,2,'/dashboard','./dashboard',NULL,1,0,'M','0','0',NULL,'DashboardOutlined',NULL,NULL,'2025-12-20 17:39:10',1,'2025-12-20 17:50:35','','BOTH'),(2003,'工作台','workplace',2002,1,'/dashboard/workplace','./dashboard/workplace',NULL,1,0,'C','0','0','dashboard:workplace','#',NULL,NULL,'2025-12-20 17:39:58',1,'2025-12-20 19:50:37','','BOTH'),(2004,'分析页','analysis',2002,2,'/dashboard/analysis','./dashboard/analysis',NULL,1,0,'C','0','0','dashboard:analysis','#',NULL,NULL,'2025-12-20 17:40:33',1,'2025-12-20 19:50:47','','BOTH'),(2005,'用户授权',NULL,2000,0,'',NULL,NULL,1,0,'F','0','0','system:user:assign','#',NULL,NULL,'2025-12-21 17:44:56',NULL,'2025-12-21 17:44:56','用户授权角色','BOTH'),(2006,'权限分配',NULL,101,0,'',NULL,NULL,1,0,'F','0','0','system:role:assigne','#',NULL,NULL,'2025-12-21 17:49:39',1,'2025-12-21 17:49:53','更多按钮','BOTH'),(2007,'个人中心','account',0,7,'/account',NULL,NULL,1,0,'M','0','0',NULL,'UserOutlined',NULL,NULL,'2025-12-26 20:45:03',1,'2025-12-30 14:42:24','个人中心','BOTH'),(2008,'个人设置','settings',2007,1,'/account/settings','./account/settings',NULL,1,0,'C','0','0','account:settings:list','#',NULL,NULL,'2025-12-26 20:47:23',NULL,'2025-12-26 20:47:23','个人设置','BOTH'),(2009,'流程定义','definition',503,1,'/workflow/definition','./workflow/definition',NULL,1,0,'C','0','0','workflow:wfDefinition:list','#',103,1,'2025-12-30 14:39:55',1,'2025-12-30 14:48:10','流程定义菜单','BOTH'),(2010,'流程实例','instance',503,1,'/workflow/instance','./workflow/instance',NULL,1,0,'C','0','0','workflow:wfInstance:list','#',103,1,'2025-12-30 14:39:55',1,'2025-12-30 14:57:37','流程实例菜单','BOTH'),(2100,'wfd查询',NULL,2009,1,'#','',NULL,1,0,'F','0','0','workflow:wfDefinition:query','#',103,1,'2025-12-30 14:39:55',NULL,NULL,'','BOTH'),(2101,'wfd新增',NULL,2009,2,'#','',NULL,1,0,'F','0','0','workflow:wfDefinition:add','#',103,1,'2025-12-30 14:39:55',NULL,NULL,'','BOTH'),(2102,'wfd修改',NULL,2009,3,'#','',NULL,1,0,'F','0','0','workflow:wfDefinition:edit','#',103,1,'2025-12-30 14:39:55',NULL,NULL,'','BOTH'),(2103,'wfd删除',NULL,2009,4,'#','',NULL,1,0,'F','0','0','workflow:wfDefinition:remove','#',103,1,'2025-12-30 14:39:55',NULL,NULL,'','BOTH'),(2104,'wfd导出',NULL,2009,5,'#','',NULL,1,0,'F','0','0','workflow:wfDefinition:export','#',103,1,'2025-12-30 14:39:55',NULL,NULL,'','BOTH'),(2105,'wfi查询',NULL,2010,1,'#','',NULL,1,0,'F','0','0','workflow:wfInstance:query','#',103,1,'2025-12-30 14:39:55',NULL,NULL,'','BOTH'),(2106,'wfi新增',NULL,2010,2,'#','',NULL,1,0,'F','0','0','workflow:wfInstance:add','#',103,1,'2025-12-30 14:39:55',NULL,NULL,'','BOTH'),(2107,'wfi修改',NULL,2010,3,'#','',NULL,1,0,'F','0','0','workflow:wfInstance:edit','#',103,1,'2025-12-30 14:39:55',NULL,NULL,'','BOTH'),(2108,'wfi删除',NULL,2010,4,'#','',NULL,1,0,'F','0','0','workflow:wfInstance:remove','#',103,1,'2025-12-30 14:39:55',NULL,NULL,'','BOTH'),(2109,'wfi导出',NULL,2010,5,'#','',NULL,1,0,'F','0','0','workflow:wfInstance:export','#',103,1,'2025-12-30 14:39:55',NULL,NULL,'','BOTH');
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_notice`
--

DROP TABLE IF EXISTS `sys_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_notice` (
  `notice_id` bigint NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `tenant_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '000000' COMMENT '租户编号',
  `notice_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公告标题',
  `notice_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` longblob COMMENT '公告内容',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1789324923280932866 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='通知公告表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_notice`
--

LOCK TABLES `sys_notice` WRITE;
/*!40000 ALTER TABLE `sys_notice` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_notice_state`
--

DROP TABLE IF EXISTS `sys_notice_state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_notice_state` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `notice_id` bigint NOT NULL COMMENT '公告ID',
  `read_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '阅读状态（0未读 1已读）',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户阅读状态表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_notice_state`
--

LOCK TABLES `sys_notice_state` WRITE;
/*!40000 ALTER TABLE `sys_notice_state` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_notice_state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_oper_log`
--

DROP TABLE IF EXISTS `sys_oper_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_oper_log` (
  `oper_id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `tenant_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '000000' COMMENT '租户编号',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '模块标题',
  `business_type` int DEFAULT '0' COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '请求方式',
  `operator_type` int DEFAULT '0' COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `operator_id` bigint DEFAULT NULL,
  `oper_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '返回参数',
  `status` int DEFAULT '0' COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
  `cost_time` bigint DEFAULT '0' COMMENT '消耗时间',
  PRIMARY KEY (`oper_id`) USING BTREE,
  KEY `idx_sys_oper_log_bt` (`business_type`) USING BTREE,
  KEY `idx_sys_oper_log_s` (`status`) USING BTREE,
  KEY `idx_sys_oper_log_ot` (`oper_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2001302410110120363 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='操作日志记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_oper_log`
--

LOCK TABLES `sys_oper_log` WRITE;
/*!40000 ALTER TABLE `sys_oper_log` DISABLE KEYS */;
INSERT INTO `sys_oper_log` VALUES (2001302410110120319,'00000','部门管理',2,'com.yuan.system.controller.SysDeptController.edit()','PUT',1,1,'admin','','/system/sysDept','0:0:0:0:0:0:0:1','内网IP','{\"deptId\":1,\"tenantId\":null,\"parentId\":null,\"ancestors\":null,\"deptName\":\"多租户系统管理\",\"orderNum\":0,\"leader\":\"admin\",\"phone\":null,\"email\":null,\"status\":\"0\",\"delFlag\":null,\"createDept\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null}','{\"code\":200,\"msg\":\"操作成功\",\"data\":null}',0,'','2026-01-06 21:28:14',43),(2001302410110120320,'00000','多租户',1,'com.yuan.system.controller.SysTenantController.add()','POST',1,1,'admin','','/system/sysTenant','0:0:0:0:0:0:0:1','内网IP','{\"id\":13,\"tenantId\":\"2008531291594510336\",\"contactUserName\":\"YUAN\",\"contactPhone\":\"18781111111\",\"companyName\":\"一语暗远科技有限公司\",\"licenseNumber\":null,\"address\":\"上海市西藏中路268号来福士广场办公大楼501室\",\"intro\":\"简介\",\"domain\":\"www.yuan.com\",\"remark\":\"备注\",\"packageId\":null,\"expireTime\":null,\"accountCount\":100,\"status\":\"0\",\"delFlag\":null,\"createDept\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null}','{\"code\":200,\"msg\":\"操作成功\",\"data\":null}',0,'','2026-01-06 21:29:11',215),(2001302410110120321,'00000','角色',2,'com.yuan.system.controller.SysRoleController.edit()','PUT',1,1,'admin','','/system/sysRole','0:0:0:0:0:0:0:1','内网IP','{\"roleId\":\"1780264338471858193\",\"tenantId\":null,\"roleName\":\"租户管理员\",\"roleKey\":\"tenantAdmin\",\"roleSort\":3,\"dataScope\":\"1\",\"menuCheckStrictly\":null,\"deptCheckStrictly\":null,\"status\":\"0\",\"delFlag\":null,\"createDept\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null,\"remark\":\"租户管理员 2008531291594510336\",\"menuIds\":[],\"menuIdList\":null,\"menuNames\":null,\"amount\":null,\"superAdmin\":false}','{\"code\":200,\"msg\":\"操作成功\",\"data\":null}',0,'','2026-01-06 21:39:35',72),(2001302410110120322,'00000','角色',2,'com.yuan.system.controller.SysRoleController.edit()','PUT',1,1,'admin','','/system/sysRole','0:0:0:0:0:0:0:1','内网IP','{\"roleId\":\"1780264338471858179\",\"tenantId\":null,\"roleName\":\"平台管理员\",\"roleKey\":\"platadmin\",\"roleSort\":2,\"dataScope\":\"1\",\"menuCheckStrictly\":null,\"deptCheckStrictly\":null,\"status\":\"0\",\"delFlag\":null,\"createDept\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null,\"remark\":\"平台管理员\",\"menuIds\":[2001,2002,2003,2004,1,2000,101,104,103,200,105,102,108,2006,1008,1009,1010,1011,1012,1013,1014,1015,1016,1017,1018,1019,1020,1021,1022,1023,1024,1026,1027,1028,1029,1030,500,501,1900,1901,1902,1903,2005,1001,1002,1003,1004,1005,1006,1007,1040,1041,1042,1043,1044,1045,503,2009,2010,2100,2101,2102,2103,2104,2105,2106,2107,2108,2109,2007,2008],\"menuIdList\":null,\"menuNames\":null,\"amount\":null,\"superAdmin\":false}','',1,'包含无权分配的菜单','2026-01-06 21:40:18',74),(2001302410110120323,'00000','角色',2,'com.yuan.system.controller.SysRoleController.edit()','PUT',1,1,'admin','','/system/sysRole','0:0:0:0:0:0:0:1','内网IP','{\"roleId\":\"1780264338471858179\",\"tenantId\":null,\"roleName\":\"平台管理员\",\"roleKey\":\"platadmin\",\"roleSort\":2,\"dataScope\":\"1\",\"menuCheckStrictly\":null,\"deptCheckStrictly\":null,\"status\":\"0\",\"delFlag\":null,\"createDept\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null,\"remark\":\"平台管理员\",\"menuIds\":[2001,2002,2003,2004,200,105,102,108,1013,1014,1015,1016,1026,1027,1028,1029,1030,500,501,1900,1901,1902,1903,1040,1041,1042,1043,1044,1045,101,2006,1008,1009,1010,1011,1012,503,2009,2010,2100,2101,2102,2103,2104,2105,2106,2107,2108,2109,2007,2008],\"menuIdList\":null,\"menuNames\":null,\"amount\":null,\"superAdmin\":false}','',1,'包含无权分配的菜单','2026-01-06 21:40:46',55),(2001302410110120324,'00000','角色',2,'com.yuan.system.controller.SysRoleController.edit()','PUT',1,1,'admin','','/system/sysRole','0:0:0:0:0:0:0:1','内网IP','{\"roleId\":\"1780264338471858179\",\"tenantId\":null,\"roleName\":\"平台管理员\",\"roleKey\":\"platadmin\",\"roleSort\":2,\"dataScope\":\"1\",\"menuCheckStrictly\":null,\"deptCheckStrictly\":null,\"status\":\"0\",\"delFlag\":null,\"createDept\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null,\"remark\":\"平台管理员\",\"menuIds\":[2001,2002,2003,2004,1,2000,101,104,103,200,105,102,108,2006,1008,1009,1010,1011,1012,1013,1014,1015,1016,1017,1018,1019,1020,1021,1022,1023,1024,1026,1027,1028,1029,1030,500,501,1900,1901,1902,1903,2005,1001,1002,1003,1004,1005,1006,1007,1040,1041,1042,1043,1044,1045,503,2009,2010,2100,2101,2102,2103,2104,2105,2106,2107,2108,2109,2007,2008],\"menuIdList\":null,\"menuNames\":null,\"amount\":null,\"superAdmin\":false}','{\"code\":200,\"msg\":\"操作成功\",\"data\":null}',0,'','2026-01-06 21:41:44',93),(2001302410110120325,'00000','角色',2,'com.yuan.system.controller.SysRoleController.edit()','PUT',1,1,'admin','','/system/sysRole','0:0:0:0:0:0:0:1','内网IP','{\"roleId\":\"1780264338471858193\",\"tenantId\":null,\"roleName\":\"租户管理员\",\"roleKey\":\"tenantAdmin\",\"roleSort\":3,\"dataScope\":\"1\",\"menuCheckStrictly\":null,\"deptCheckStrictly\":null,\"status\":\"0\",\"delFlag\":null,\"createDept\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null,\"remark\":\"租户管理员 2008531291594510336\",\"menuIds\":[2001,2002,2003,2004,103,1017,1018,1019,1020,104,1021,1022,1023,1024,101,2006,1008,1009,1010,1011,1012,2000,2005,1001,1002,1003,1004,1005,1006,1007,108,500,501,1040,1041,1042,1043,1044,1045,503,2009,2010,2100,2101,2102,2103,2104,2105,2106,2107,2108,2109,2007,2008],\"menuIdList\":null,\"menuNames\":null,\"amount\":null,\"superAdmin\":false}','{\"code\":200,\"msg\":\"操作成功\",\"data\":null}',0,'','2026-01-06 21:42:04',67),(2001302410110120326,'00000','多租户',1,'com.yuan.system.controller.SysTenantController.add()','POST',1,1,'admin','','/system/sysTenant','0:0:0:0:0:0:0:1','内网IP','{\"id\":14,\"tenantId\":\"2008535393950965760\",\"contactUserName\":\"袁振栋\",\"contactPhone\":\"18781111111\",\"companyName\":\"一语暗远科技有限公司\",\"licenseNumber\":null,\"address\":\"上海市西藏中路268号来福士广场办公大楼501室\",\"intro\":\"简介\",\"domain\":\"www.yuan.com\",\"remark\":\"备注\",\"packageId\":null,\"expireTime\":null,\"accountCount\":100,\"status\":\"0\",\"delFlag\":null,\"createDept\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null}','{\"code\":200,\"msg\":\"操作成功\",\"data\":null}',0,'','2026-01-06 21:45:30',230),(2001302410110120327,'00000','角色',2,'com.yuan.system.controller.SysRoleController.edit()','PUT',1,1,'admin','','/system/sysRole','0:0:0:0:0:0:0:1','内网IP','{\"roleId\":\"1780264338471858194\",\"tenantId\":null,\"roleName\":\"租户管理员\",\"roleKey\":\"tenantAdmin\",\"roleSort\":0,\"dataScope\":\"1\",\"menuCheckStrictly\":null,\"deptCheckStrictly\":null,\"status\":\"0\",\"delFlag\":null,\"createDept\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null,\"remark\":\"租户管理员 2008535393950965760\",\"menuIds\":[2001,2002,2003,2004,503,2009,2010,2100,2101,2102,2103,2104,2105,2106,2107,2108,2109,2007,2008,2000,2005,1001,1002,1003,1004,1005,1006,1007,101,2006,1008,1009,1010,1011,1012,104,1021,1022,1023,1024,103,1017,1018,1019,1020,108,500,501,1040,1041,1042,1043,1044,1045,102,1013,1014,1015,1016],\"menuIdList\":null,\"menuNames\":null,\"amount\":null,\"superAdmin\":false}','{\"code\":200,\"msg\":\"操作成功\",\"data\":null}',0,'','2026-01-06 21:53:36',109),(2001302410110120328,'2008535393950965760','部门管理',2,'com.yuan.system.controller.SysDeptController.edit()','PUT',1,1780264338471858188,'U732864','','/system/sysDept','0:0:0:0:0:0:0:1','内网IP','{\"deptId\":124,\"tenantId\":null,\"parentId\":null,\"ancestors\":null,\"deptName\":\"总部\",\"orderNum\":0,\"leader\":\"袁振栋\",\"phone\":\"18782123331\",\"email\":\"571145417@qq.com\",\"status\":\"0\",\"delFlag\":null,\"createDept\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null}','{\"code\":200,\"msg\":\"操作成功\",\"data\":null}',0,'','2026-01-06 22:12:06',34),(2001302410110120329,'2008535393950965760','用户',1,'com.yuan.system.controller.SysUserController.add()','POST',1,1780264338471858188,'U732864','','/system/sysUser','0:0:0:0:0:0:0:1','内网IP','{\"userId\":\"1780264338471858189\",\"openId\":null,\"userGrade\":null,\"userBalance\":null,\"tenantId\":null,\"deptId\":124,\"userName\":\"U066688\",\"nickName\":\"袁2号\",\"userType\":null,\"userPlan\":null,\"email\":\"123123@qq.com\",\"phonenumber\":\"18192831241\",\"sex\":\"0\",\"avatar\":null,\"wxAvatar\":null,\"status\":\"0\",\"delFlag\":null,\"loginIp\":null,\"loginDate\":null,\"domainName\":null,\"createDept\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null,\"remark\":\"12312\",\"kroleGroupType\":null,\"kroleGroupIds\":null,\"roleId\":null,\"postId\":null,\"postName\":null,\"deptName\":null,\"roleName\":null,\"primaryPostId\":null}','{\"code\":200,\"msg\":\"操作成功\",\"data\":null}',0,'','2026-01-06 23:09:51',178),(2001302410110120330,'2008535393950965760','post',1,'com.yuan.system.controller.SysPostController.add()','POST',1,1780264338471858188,'U732864','','/system/sysPost','0:0:0:0:0:0:0:1','内网IP','{\"postId\":15,\"tenantId\":null,\"postCode\":\"ceo\",\"postName\":\"ceo\",\"postSort\":1,\"status\":\"0\",\"deptId\":124,\"createDept\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null,\"remark\":\"1\",\"delFlag\":null}','{\"code\":200,\"msg\":\"操作成功\",\"data\":null}',0,'','2026-01-06 23:15:08',81),(2001302410110120331,'2008535393950965760','用户',2,'com.yuan.system.controller.SysUserController.edit()','PUT',1,1780264338471858188,'U732864','','/system/sysUser','0:0:0:0:0:0:0:1','内网IP','{\"userId\":\"1780264338471858189\",\"openId\":null,\"userGrade\":null,\"userBalance\":null,\"tenantId\":null,\"deptId\":124,\"userName\":null,\"nickName\":\"袁2号\",\"userType\":null,\"userPlan\":null,\"email\":\"123123@qq.com\",\"phonenumber\":\"18192831241\",\"sex\":\"0\",\"avatar\":null,\"wxAvatar\":null,\"status\":\"0\",\"delFlag\":null,\"loginIp\":null,\"loginDate\":null,\"domainName\":null,\"createDept\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null,\"remark\":null,\"kroleGroupType\":null,\"kroleGroupIds\":null,\"roleId\":null,\"postId\":null,\"postName\":null,\"deptName\":null,\"roleName\":null,\"primaryPostId\":15}','{\"code\":200,\"msg\":\"操作成功\",\"data\":null}',0,'','2026-01-06 23:15:24',74),(2001302410110120332,'2008535393950965760','个人信息',2,'com.yuan.system.controller.SysProfileController.updateProfile()','PUT',1,1780264338471858188,'U732864','','/system/user/profile','0:0:0:0:0:0:0:1','内网IP','{\"createDept\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null,\"userId\":\"1780264338471858188\",\"nickName\":\"袁振栋\",\"email\":\"571145417@qq.com\",\"phonenumber\":\"18781111111\",\"sex\":\"1\"}','{\"code\":200,\"msg\":\"操作成功\",\"data\":null}',0,'','2026-01-07 21:05:54',63),(2001302410110120333,'2008535393950965760','部门管理',2,'com.yuan.system.controller.SysDeptController.edit()','PUT',1,1780264338471858188,'','总部','/system/sysDept','0:0:0:0:0:0:0:1','内网IP','{\"deptId\":124,\"tenantId\":null,\"parentId\":null,\"ancestors\":null,\"deptName\":\"一语暗远科技有限公司\",\"orderNum\":0,\"leaderId\":null,\"phone\":\"18782123331\",\"email\":\"571145417@qq.com\",\"status\":\"0\",\"delFlag\":null,\"createDept\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null,\"leader\":null}','{\"code\":200,\"msg\":\"操作成功\",\"data\":null}',0,'','2026-01-07 21:22:26',41),(2001302410110120334,'2008535393950965760','部门管理',1,'com.yuan.system.controller.SysDeptController.add()','POST',1,1780264338471858188,'','总部','/system/sysDept','0:0:0:0:0:0:0:1','内网IP','{\"deptId\":125,\"tenantId\":null,\"parentId\":124,\"ancestors\":null,\"deptName\":\"IT开发部\",\"orderNum\":1,\"leaderId\":null,\"phone\":\"18728371293\",\"email\":\"67123923@qq.com\",\"status\":\"0\",\"delFlag\":null,\"createDept\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null,\"leader\":null}','{\"code\":200,\"msg\":\"操作成功\",\"data\":null}',0,'','2026-01-07 21:22:59',25),(2001302410110120335,'2008535393950965760','部门管理',3,'com.yuan.system.controller.SysDeptController.remove()','DELETE',1,1780264338471858188,'','总部','/system/sysDept/125','0:0:0:0:0:0:0:1','内网IP','{}','{\"code\":200,\"msg\":\"操作成功\",\"data\":null}',0,'','2026-01-07 21:23:34',34),(2001302410110120336,'2008535393950965760','部门管理',3,'com.yuan.system.controller.SysDeptController.remove()','DELETE',1,1780264338471858188,'','总部','/system/sysDept/125','0:0:0:0:0:0:0:1','内网IP','{}','{\"code\":500,\"msg\":\"操作失败\",\"data\":null}',0,'','2026-01-07 21:23:46',16),(2001302410110120337,'2008535393950965760','部门管理',3,'com.yuan.system.controller.SysDeptController.remove()','DELETE',1,1780264338471858188,'','总部','/system/sysDept/125','0:0:0:0:0:0:0:1','内网IP','{}','{\"code\":500,\"msg\":\"操作失败\",\"data\":null}',0,'','2026-01-07 21:24:07',14),(2001302410110120338,'2008535393950965760','部门管理',3,'com.yuan.system.controller.SysDeptController.remove()','DELETE',1,1780264338471858188,'','总部','/system/sysDept/125','0:0:0:0:0:0:0:1','内网IP','{}','{\"code\":500,\"msg\":\"操作失败\",\"data\":null}',0,'','2026-01-07 21:25:14',28332),(2001302410110120339,'2008535393950965760','部门管理',2,'com.yuan.system.controller.SysDeptController.edit()','PUT',1,1780264338471858188,'','总部','/system/sysDept','0:0:0:0:0:0:0:1','内网IP','{\"deptId\":124,\"tenantId\":null,\"parentId\":null,\"ancestors\":null,\"deptName\":\"一语科技有限公司\",\"orderNum\":0,\"leaderId\":null,\"phone\":\"18782123331\",\"email\":\"571145417@qq.com\",\"status\":\"0\",\"delFlag\":null,\"createDept\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null,\"leader\":null}','{\"code\":200,\"msg\":\"操作成功\",\"data\":null}',0,'','2026-01-07 21:29:11',18),(2001302410110120340,'2008535393950965760','部门管理',1,'com.yuan.system.controller.SysDeptController.add()','POST',1,1780264338471858188,'','总部','/system/sysDept','0:0:0:0:0:0:0:1','内网IP','{\"deptId\":null,\"tenantId\":null,\"parentId\":124,\"ancestors\":null,\"deptName\":\"上海一语科技有限公司\",\"orderNum\":1,\"leaderId\":null,\"phone\":\"187855729234\",\"email\":\"123123@qq.com\",\"status\":\"0\",\"delFlag\":null,\"createDept\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null,\"leader\":null}','',1,'\r\n### Error updating database.  Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Data too long for column \'phone\' at row 1\r\n### The error may exist in com/yuan/system/mapper/SysDeptMapper.java (best guess)\r\n### The error may involve com.yuan.system.mapper.SysDeptMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO sys_dept (parent_id, dept_name, order_num, phone, email, status, create_dept, create_by, create_time, update_by, update_time, tenant_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, \'2008535393950965760\')\r\n### Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Data too long for column \'phone\' at row 1\n; Data truncation: Data too long for column \'phone\' at row 1','2026-01-07 21:29:42',138),(2001302410110120341,'2008535393950965760','部门管理',1,'com.yuan.system.controller.SysDeptController.add()','POST',1,1780264338471858188,'','总部','/system/sysDept','0:0:0:0:0:0:0:1','内网IP','{\"deptId\":126,\"tenantId\":null,\"parentId\":124,\"ancestors\":null,\"deptName\":\"上海一语科技有限公司\",\"orderNum\":1,\"leaderId\":null,\"phone\":\"18238491281\",\"email\":\"123123@qq.com\",\"status\":\"0\",\"delFlag\":null,\"createDept\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null,\"leader\":null}','{\"code\":200,\"msg\":\"操作成功\",\"data\":null}',0,'','2026-01-07 21:36:01',39),(2001302410110120342,'2008535393950965760','部门管理',1,'com.yuan.system.controller.SysDeptController.add()','POST',1,1780264338471858188,'','总部','/system/sysDept','0:0:0:0:0:0:0:1','内网IP','{\"deptId\":127,\"tenantId\":null,\"parentId\":126,\"ancestors\":null,\"deptName\":\"IT开发部\",\"orderNum\":1,\"leaderId\":null,\"phone\":null,\"email\":null,\"status\":\"0\",\"delFlag\":null,\"createDept\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null,\"leader\":null}','{\"code\":200,\"msg\":\"操作成功\",\"data\":null}',0,'','2026-01-07 21:36:28',19),(2001302410110120343,'2008535393950965760','部门管理',1,'com.yuan.system.controller.SysDeptController.add()','POST',1,1780264338471858188,'','总部','/system/sysDept','0:0:0:0:0:0:0:1','内网IP','{\"deptId\":128,\"tenantId\":null,\"parentId\":126,\"ancestors\":null,\"deptName\":\"IT运维部\",\"orderNum\":2,\"leaderId\":null,\"phone\":null,\"email\":null,\"status\":\"0\",\"delFlag\":null,\"createDept\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null,\"leader\":null}','{\"code\":200,\"msg\":\"操作成功\",\"data\":null}',0,'','2026-01-07 21:36:40',18),(2001302410110120344,'2008535393950965760','部门管理',1,'com.yuan.system.controller.SysDeptController.add()','POST',1,1780264338471858188,'','总部','/system/sysDept','0:0:0:0:0:0:0:1','内网IP','{\"deptId\":129,\"tenantId\":null,\"parentId\":126,\"ancestors\":null,\"deptName\":\"IT测试部\",\"orderNum\":3,\"leaderId\":null,\"phone\":null,\"email\":null,\"status\":\"0\",\"delFlag\":null,\"createDept\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null,\"leader\":null}','{\"code\":200,\"msg\":\"操作成功\",\"data\":null}',0,'','2026-01-07 21:36:56',18),(2001302410110120345,'2008535393950965760','部门管理',1,'com.yuan.system.controller.SysDeptController.add()','POST',1,1780264338471858188,'','总部','/system/sysDept','0:0:0:0:0:0:0:1','内网IP','{\"deptId\":130,\"tenantId\":null,\"parentId\":126,\"ancestors\":null,\"deptName\":\"人事部\",\"orderNum\":4,\"leaderId\":null,\"phone\":null,\"email\":null,\"status\":\"0\",\"delFlag\":null,\"createDept\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null,\"leader\":null}','{\"code\":200,\"msg\":\"操作成功\",\"data\":null}',0,'','2026-01-07 21:37:09',17),(2001302410110120346,'2008535393950965760','post',1,'com.yuan.system.controller.SysPostController.add()','POST',1,1780264338471858188,'','总部','/system/sysPost','0:0:0:0:0:0:0:1','内网IP','{\"postId\":16,\"tenantId\":null,\"postCode\":\"JAVA_BACK\",\"postName\":\"JAVA后端开发\",\"postSort\":1,\"status\":\"0\",\"deptId\":127,\"createDept\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null,\"remark\":\"JAVA后端开发\",\"delFlag\":null}','{\"code\":200,\"msg\":\"操作成功\",\"data\":null}',0,'','2026-01-07 21:37:55',34),(2001302410110120347,'2008535393950965760','post',1,'com.yuan.system.controller.SysPostController.add()','POST',1,1780264338471858188,'','总部','/system/sysPost','0:0:0:0:0:0:0:1','内网IP','{\"postId\":17,\"tenantId\":null,\"postCode\":\"VUE-FRONT\",\"postName\":\"VUE前端开发\",\"postSort\":2,\"status\":\"0\",\"deptId\":127,\"createDept\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null,\"remark\":\"VUE前端开发\",\"delFlag\":null}','{\"code\":200,\"msg\":\"操作成功\",\"data\":null}',0,'','2026-01-07 21:38:32',20),(2001302410110120348,'2008535393950965760','用户',1,'com.yuan.system.controller.SysUserController.add()','POST',1,1780264338471858188,'','总部','/system/sysUser','0:0:0:0:0:0:0:1','内网IP','{\"userId\":\"1780264338471858190\",\"openId\":null,\"userGrade\":null,\"userBalance\":null,\"tenantId\":null,\"deptId\":127,\"userName\":\"U495360\",\"nickName\":\"袁JAVA1号\",\"userType\":null,\"userPlan\":null,\"email\":\"yuanjava1@qq.com\",\"phonenumber\":null,\"sex\":\"0\",\"avatar\":null,\"wxAvatar\":null,\"status\":\"0\",\"delFlag\":null,\"loginIp\":null,\"loginDate\":null,\"domainName\":null,\"createDept\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null,\"remark\":\"后端开发一号\",\"kroleGroupType\":null,\"kroleGroupIds\":null,\"roleId\":null,\"postId\":null,\"postName\":null,\"deptName\":null,\"roleName\":null,\"primaryPostId\":16}','{\"code\":200,\"msg\":\"操作成功\",\"data\":null}',0,'','2026-01-07 21:39:36',150),(2001302410110120349,'2008535393950965760','用户',1,'com.yuan.system.controller.SysUserController.add()','POST',1,1780264338471858188,'','总部','/system/sysUser','0:0:0:0:0:0:0:1','内网IP','{\"userId\":\"1780264338471858191\",\"openId\":null,\"userGrade\":null,\"userBalance\":null,\"tenantId\":null,\"deptId\":127,\"userName\":\"U169664\",\"nickName\":\"袁VUE1号\",\"userType\":null,\"userPlan\":null,\"email\":\"yuanVUE1@qq.com\",\"phonenumber\":null,\"sex\":\"0\",\"avatar\":null,\"wxAvatar\":null,\"status\":\"0\",\"delFlag\":null,\"loginIp\":null,\"loginDate\":null,\"domainName\":null,\"createDept\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null,\"remark\":\"VUE前端开发一号\",\"kroleGroupType\":null,\"kroleGroupIds\":null,\"roleId\":null,\"postId\":null,\"postName\":null,\"deptName\":null,\"roleName\":null,\"primaryPostId\":17}','{\"code\":200,\"msg\":\"操作成功\",\"data\":null}',0,'','2026-01-07 21:40:02',99),(2001302410110120350,'2008535393950965760','用户',2,'com.yuan.system.controller.SysUserController.edit()','PUT',1,1780264338471858188,'','总部','/system/sysUser','0:0:0:0:0:0:0:1','内网IP','{\"userId\":\"1780264338471858191\",\"openId\":null,\"userGrade\":null,\"userBalance\":null,\"tenantId\":null,\"deptId\":127,\"userName\":null,\"nickName\":\"袁VUE1号\",\"userType\":null,\"userPlan\":null,\"email\":\"yuanvue1@qq.com\",\"phonenumber\":\"\",\"sex\":\"0\",\"avatar\":null,\"wxAvatar\":null,\"status\":\"0\",\"delFlag\":null,\"loginIp\":null,\"loginDate\":null,\"domainName\":null,\"createDept\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null,\"remark\":null,\"kroleGroupType\":null,\"kroleGroupIds\":null,\"roleId\":null,\"postId\":null,\"postName\":null,\"deptName\":null,\"roleName\":null,\"primaryPostId\":17}','{\"code\":200,\"msg\":\"操作成功\",\"data\":null}',0,'','2026-01-07 21:40:18',37),(2001302410110120351,'2008535393950965760','角色',1,'com.yuan.system.controller.SysRoleController.add()','POST',1,1780264338471858188,'','总部','/system/sysRole','0:0:0:0:0:0:0:1','内网IP','{\"roleId\":\"1780264338471858195\",\"tenantId\":null,\"roleName\":\"开发人员\",\"roleKey\":\"Developer\",\"roleSort\":2,\"dataScope\":\"4\",\"menuCheckStrictly\":null,\"deptCheckStrictly\":null,\"status\":\"0\",\"delFlag\":null,\"createDept\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null,\"remark\":\"开发部人员基本角色\",\"menuIds\":[2007,2008,503,2009,2010,2100,2101,2102,2103,2104,2105,2106,2107,2108,2109,108,500,501,1040,1041,1042,1043,1044,1045,1017,1021,1008,1001,2002,2003,2004,2001,1013],\"menuIdList\":null,\"menuNames\":null,\"amount\":null,\"superAdmin\":false}','{\"code\":200,\"msg\":\"操作成功\",\"data\":null}',0,'','2026-01-07 21:44:15',104),(2001302410110120352,'2008535393950965760','岗位管理',4,'com.yuan.system.controller.SysPostController.insertPostRole()','PUT',1,1780264338471858188,'','一语科技有限公司','/system/sysPost/insertPostRole','0:0:0:0:0:0:0:1','内网IP','{\"postId\":\"16\",\"roleIds[]\":\"1780264338471858195\"}','{\"code\":200,\"msg\":\"操作成功\",\"data\":null}',0,'','2026-01-07 22:00:04',112),(2001302410110120353,'2008535393950965760','岗位管理',4,'com.yuan.system.controller.SysPostController.insertPostRole()','PUT',1,1780264338471858188,'','一语科技有限公司','/system/sysPost/insertPostRole','0:0:0:0:0:0:0:1','内网IP','{\"postId\":\"17\",\"roleIds[]\":\"1780264338471858195\"}','{\"code\":200,\"msg\":\"操作成功\",\"data\":null}',0,'','2026-01-07 22:00:07',28),(2001302410110120354,'2008535393950965760','用户',2,'com.yuan.system.controller.SysUserController.edit()','PUT',1,1780264338471858188,'','一语科技有限公司','/system/sysUser','0:0:0:0:0:0:0:1','内网IP','{\"userId\":\"1780264338471858189\",\"openId\":null,\"userGrade\":null,\"userBalance\":null,\"tenantId\":null,\"deptId\":124,\"userName\":null,\"nickName\":\"袁1号\",\"userType\":null,\"userPlan\":null,\"email\":\"123123@qq.com\",\"phonenumber\":\"18192831241\",\"sex\":\"0\",\"avatar\":null,\"wxAvatar\":null,\"status\":\"0\",\"delFlag\":null,\"loginIp\":null,\"loginDate\":null,\"domainName\":null,\"createDept\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null,\"remark\":null,\"kroleGroupType\":null,\"kroleGroupIds\":null,\"roleId\":null,\"postId\":null,\"postName\":null,\"deptName\":null,\"roleName\":null,\"primaryPostId\":15}','{\"code\":200,\"msg\":\"操作成功\",\"data\":null}',0,'','2026-01-07 22:05:02',79),(2001302410110120355,'2008535393950965760','用户',2,'com.yuan.system.controller.SysUserController.edit()','PUT',1,1780264338471858188,'','一语科技有限公司','/system/sysUser','0:0:0:0:0:0:0:1','内网IP','{\"userId\":\"1780264338471858188\",\"openId\":null,\"userGrade\":null,\"userBalance\":null,\"tenantId\":null,\"deptId\":124,\"userName\":null,\"nickName\":\"袁振栋\",\"userType\":null,\"userPlan\":null,\"email\":\"571145417@qq.com\",\"phonenumber\":\"18781111111\",\"sex\":\"0\",\"avatar\":null,\"wxAvatar\":null,\"status\":\"0\",\"delFlag\":null,\"loginIp\":null,\"loginDate\":null,\"domainName\":null,\"createDept\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null,\"remark\":null,\"kroleGroupType\":null,\"kroleGroupIds\":null,\"roleId\":null,\"postId\":null,\"postName\":null,\"deptName\":null,\"roleName\":null,\"primaryPostId\":14}','{\"code\":200,\"msg\":\"操作成功\",\"data\":null}',0,'','2026-01-07 22:05:08',33),(2001302410110120356,'2008535393950965760','用户',2,'com.yuan.system.controller.SysUserController.edit()','PUT',1,1780264338471858188,'','一语科技有限公司','/system/sysUser','0:0:0:0:0:0:0:1','内网IP','{\"userId\":\"1780264338471858191\",\"openId\":null,\"userGrade\":null,\"userBalance\":null,\"tenantId\":null,\"deptId\":127,\"userName\":null,\"nickName\":\"袁VUE1号\",\"userType\":null,\"userPlan\":null,\"email\":\"yuanvue1@qq.com\",\"phonenumber\":\"\",\"sex\":\"1\",\"avatar\":null,\"wxAvatar\":null,\"status\":\"0\",\"delFlag\":null,\"loginIp\":null,\"loginDate\":null,\"domainName\":null,\"createDept\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null,\"remark\":null,\"kroleGroupType\":null,\"kroleGroupIds\":null,\"roleId\":null,\"postId\":null,\"postName\":null,\"deptName\":null,\"roleName\":null,\"primaryPostId\":17}','{\"code\":200,\"msg\":\"操作成功\",\"data\":null}',0,'','2026-01-07 22:05:14',27),(2001302410110120357,'2008535393950965760','角色',2,'com.yuan.system.controller.SysRoleController.edit()','PUT',1,1780264338471858188,'','一语科技有限公司','/system/sysRole','0:0:0:0:0:0:0:1','内网IP','{\"roleId\":\"1780264338471858195\",\"tenantId\":null,\"roleName\":\"开发人员\",\"roleKey\":\"Developer\",\"roleSort\":1,\"dataScope\":\"4\",\"menuCheckStrictly\":null,\"deptCheckStrictly\":null,\"status\":\"0\",\"delFlag\":null,\"createDept\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null,\"remark\":\"开发部人员基本角色\",\"menuIds\":[1001,1008,1013,1017,1021,1040,1041,1042,1043,1044,1045,2001,2003,2004,2008,2100,2101,2102,2103,2104,2105,2106,2107,2108,2109],\"menuIdList\":null,\"menuNames\":null,\"amount\":null,\"superAdmin\":false}','{\"code\":200,\"msg\":\"操作成功\",\"data\":null}',0,'','2026-01-07 22:07:11',75),(2001302410110120358,'2008535393950965760','角色',2,'com.yuan.system.controller.SysRoleController.edit()','PUT',1,1780264338471858188,'','一语科技有限公司','/system/sysRole','0:0:0:0:0:0:0:1','内网IP','{\"roleId\":\"1780264338471858195\",\"tenantId\":null,\"roleName\":\"开发人员\",\"roleKey\":\"developer\",\"roleSort\":1,\"dataScope\":\"4\",\"menuCheckStrictly\":null,\"deptCheckStrictly\":null,\"status\":\"0\",\"delFlag\":null,\"createDept\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null,\"remark\":\"开发部人员基本角色\",\"menuIds\":[1001,1008,1013,1017,1021,1040,1041,1042,1043,1044,1045,2001,2003,2004,2008,2100,2101,2102,2103,2104,2105,2106,2107,2108,2109],\"menuIdList\":null,\"menuNames\":null,\"amount\":null,\"superAdmin\":false}','{\"code\":200,\"msg\":\"操作成功\",\"data\":null}',0,'','2026-01-07 22:08:22',75),(2001302410110120359,'2008535393950965760','角色',2,'com.yuan.system.controller.SysRoleController.edit()','PUT',1,1780264338471858188,'','一语科技有限公司','/system/sysRole','0:0:0:0:0:0:0:1','内网IP','{\"roleId\":\"1780264338471858195\",\"tenantId\":null,\"roleName\":\"开发人员\",\"roleKey\":\"developer\",\"roleSort\":1,\"dataScope\":\"4\",\"menuCheckStrictly\":null,\"deptCheckStrictly\":null,\"status\":\"0\",\"delFlag\":null,\"createDept\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null,\"remark\":\"开发部人员基本角色1\",\"menuIds\":[1001,1008,1013,1017,1021,1040,1041,1042,1043,1044,1045,2001,2003,2004,2008,2100,2101,2102,2103,2104,2105,2106,2107,2108,2109],\"menuIdList\":null,\"menuNames\":null,\"amount\":null,\"superAdmin\":false}','{\"code\":200,\"msg\":\"操作成功\",\"data\":null}',0,'','2026-01-07 22:17:11',68066),(2001302410110120360,'2008535393950965760','角色',2,'com.yuan.system.controller.SysRoleController.edit()','PUT',1,1780264338471858188,'','一语科技有限公司','/system/sysRole','0:0:0:0:0:0:0:1','内网IP','{\"roleId\":\"1780264338471858195\",\"tenantId\":null,\"roleName\":\"开发人员\",\"roleKey\":\"developer\",\"roleSort\":1,\"dataScope\":\"4\",\"menuCheckStrictly\":null,\"deptCheckStrictly\":null,\"status\":\"0\",\"delFlag\":null,\"createDept\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null,\"remark\":\"开发部人员基本角色\",\"menuIds\":[1001,1008,1013,1017,1021,1040,1041,1042,1043,1044,1045,2001,2003,2004,2008,2100,2101,2102,2103,2104,2105,2106,2107,2108,2109],\"menuIdList\":null,\"menuNames\":null,\"amount\":null,\"superAdmin\":false}','{\"code\":200,\"msg\":\"操作成功\",\"data\":null}',0,'','2026-01-07 22:18:00',83),(2001302410110120361,'2008535393950965760','角色',2,'com.yuan.system.controller.SysRoleController.edit()','PUT',1,1780264338471858188,'','一语科技有限公司','/system/sysRole','0:0:0:0:0:0:0:1','内网IP','{\"roleId\":\"1780264338471858195\",\"tenantId\":null,\"roleName\":\"开发人员\",\"roleKey\":\"developer\",\"roleSort\":1,\"dataScope\":\"4\",\"menuCheckStrictly\":null,\"deptCheckStrictly\":null,\"status\":\"0\",\"delFlag\":null,\"createDept\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null,\"remark\":\"开发部人员基本角色1\",\"menuIds\":[1001,1008,1013,1017,1021,1040,1041,1042,1043,1044,1045,2001,2003,2004,2008,2100,2101,2102,2103,2104,2105,2106,2107,2108,2109],\"menuIdList\":null,\"menuNames\":null,\"amount\":null,\"superAdmin\":false}','{\"code\":200,\"msg\":\"操作成功\",\"data\":null}',0,'','2026-01-07 22:19:17',65),(2001302410110120362,'2008535393950965760','角色',2,'com.yuan.system.controller.SysRoleController.edit()','PUT',1,1780264338471858188,'袁振栋','一语科技有限公司','/system/sysRole','0:0:0:0:0:0:0:1','内网IP','{\"roleId\":\"1780264338471858195\",\"tenantId\":null,\"roleName\":\"开发人员\",\"roleKey\":\"developer\",\"roleSort\":1,\"dataScope\":\"4\",\"menuCheckStrictly\":null,\"deptCheckStrictly\":null,\"status\":\"0\",\"delFlag\":null,\"createDept\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null,\"remark\":\"开发部人员基本角色\",\"menuIds\":[1001,1008,1013,1017,1021,1040,1041,1042,1043,1044,1045,2001,2003,2004,2008,2100,2101,2102,2103,2104,2105,2106,2107,2108,2109],\"menuIdList\":null,\"menuNames\":null,\"amount\":null,\"superAdmin\":false}','{\"code\":200,\"msg\":\"操作成功\",\"data\":null}',0,'','2026-01-07 22:20:00',60);
/*!40000 ALTER TABLE `sys_oper_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_oss`
--

DROP TABLE IF EXISTS `sys_oss`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_oss` (
  `oss_id` bigint NOT NULL AUTO_INCREMENT COMMENT '对象存储主键',
  `tenant_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '000000' COMMENT '租户编号',
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '文件名',
  `original_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '原名',
  `file_suffix` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '文件后缀名',
  `url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'URL地址',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint DEFAULT NULL COMMENT '上传人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新人',
  `service` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'minio' COMMENT '服务商',
  PRIMARY KEY (`oss_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1925801916342902787 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='OSS对象存储表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_oss`
--

LOCK TABLES `sys_oss` WRITE;
/*!40000 ALTER TABLE `sys_oss` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_oss` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_oss_config`
--

DROP TABLE IF EXISTS `sys_oss_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_oss_config` (
  `oss_config_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主建',
  `tenant_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '000000' COMMENT '租户编号',
  `config_key` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '配置key',
  `access_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT 'accessKey',
  `secret_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '秘钥',
  `bucket_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '桶名称',
  `prefix` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '前缀',
  `endpoint` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '访问站点',
  `domain` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '自定义域名',
  `is_https` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'N' COMMENT '是否https（Y=是,N=否）',
  `region` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '域',
  `access_policy` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '桶权限类型(0=private 1=public 2=custom)',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '1' COMMENT '是否默认（0=是,1=否）',
  `ext1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '扩展字段',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`oss_config_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='对象存储配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_oss_config`
--

LOCK TABLES `sys_oss_config` WRITE;
/*!40000 ALTER TABLE `sys_oss_config` DISABLE KEYS */;
INSERT INTO `sys_oss_config` VALUES (1,'000000','minio','ruoyi','ruoyi123','ruoyi','','127.0.0.1:9000','','N','','1','1','',103,1,'2023-05-14 15:19:42',1,'2025-03-26 16:25:55',NULL),(2,'000000','qiniu','ruoyi','ruoyi123','ruoyi','','s3-cn-north-1.qiniucs.com','','N','','1','1','',103,1,'2023-05-14 15:19:42',1,'2025-05-23 14:31:40',NULL),(3,'000000','aliyun','ruoyi','ruoyi123','ruoyi','','oss-cn-beijing.aliyuncs.com','','N','','1','1','',103,1,'2023-05-14 15:19:42',1,'2025-03-13 13:13:04',NULL),(4,'000000','qcloud','ruoyi','ruoyi123','ruoyi','','cos.ap-guangzhou.myqcloud.com','','Y','ap-guangzhou','1','0','',103,1,'2023-05-14 15:19:42',1,'2025-05-23 14:31:41','');
/*!40000 ALTER TABLE `sys_oss_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_post`
--

DROP TABLE IF EXISTS `sys_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_post` (
  `post_id` bigint NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `tenant_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '000000' COMMENT '租户编号',
  `post_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位名称',
  `dept_id` bigint DEFAULT NULL COMMENT '部门Id',
  `post_sort` int NOT NULL COMMENT '显示顺序',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态（0正常 1停用）',
  `del_Flag` char(1) COLLATE utf8mb4_general_ci DEFAULT '0',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`post_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='岗位信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_post`
--

LOCK TABLES `sys_post` WRITE;
/*!40000 ALTER TABLE `sys_post` DISABLE KEYS */;
INSERT INTO `sys_post` VALUES (14,'2008535393950965760','tenantAdmin','租户管理员',124,0,'0','0',NULL,1,'2026-01-06 21:45:29',1,'2026-01-06 21:45:29',NULL),(15,'2008535393950965760','ceo','ceo',124,1,'0','0',NULL,1780264338471858188,'2026-01-06 23:15:08',1780264338471858188,'2026-01-06 23:15:08','1'),(16,'2008535393950965760','JAVA_BACK','JAVA后端开发',127,1,'0','0',124,1780264338471858188,'2026-01-07 21:37:55',1780264338471858188,'2026-01-07 21:37:55','JAVA后端开发'),(17,'2008535393950965760','VUE-FRONT','VUE前端开发',127,2,'0','0',124,1780264338471858188,'2026-01-07 21:38:32',1780264338471858188,'2026-01-07 21:38:32','VUE前端开发');
/*!40000 ALTER TABLE `sys_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role` (
  `role_id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `tenant_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '000000' COMMENT '租户编号',
  `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色权限字符串',
  `role_sort` int NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `menu_check_strictly` tinyint(1) DEFAULT '1' COMMENT '菜单树选择项是否关联显示',
  `dept_check_strictly` tinyint(1) DEFAULT '1' COMMENT '部门树选择项是否关联显示',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1780264338471858196 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='角色信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'000000','超级管理员','superadmin',1,'1',1,1,'0','0',103,1,'2023-05-14 15:19:39',NULL,NULL,'超级管理员'),(1780264338471858179,'000000','平台管理员','platadmin',2,'1',1,1,'0','0',NULL,NULL,'2025-12-25 16:14:08',1,'2026-01-06 21:41:44','平台管理员'),(1780264338471858194,'2008535393950965760','租户管理员','tenantAdmin',0,'1',1,1,'0','0',NULL,1,'2026-01-06 21:45:29',1,'2026-01-06 21:53:36','租户管理员 2008535393950965760'),(1780264338471858195,'2008535393950965760','开发人员','developer',1,'4',1,1,'0','0',124,1780264338471858188,'2026-01-07 21:44:15',1,'2026-01-07 22:20:00','开发部人员基本角色');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_dept`
--

DROP TABLE IF EXISTS `sys_role_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_dept` (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `dept_id` bigint NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`role_id`,`dept_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='角色和部门关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_dept`
--

LOCK TABLES `sys_role_dept` WRITE;
/*!40000 ALTER TABLE `sys_role_dept` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_role_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_menu` (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='角色和菜单关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` VALUES (1780264338471858179,1),(1780264338471858179,101),(1780264338471858179,102),(1780264338471858179,103),(1780264338471858179,104),(1780264338471858179,105),(1780264338471858179,108),(1780264338471858179,200),(1780264338471858179,500),(1780264338471858179,501),(1780264338471858179,503),(1780264338471858179,1001),(1780264338471858179,1002),(1780264338471858179,1003),(1780264338471858179,1004),(1780264338471858179,1005),(1780264338471858179,1006),(1780264338471858179,1007),(1780264338471858179,1008),(1780264338471858179,1009),(1780264338471858179,1010),(1780264338471858179,1011),(1780264338471858179,1012),(1780264338471858179,1013),(1780264338471858179,1014),(1780264338471858179,1015),(1780264338471858179,1016),(1780264338471858179,1017),(1780264338471858179,1018),(1780264338471858179,1019),(1780264338471858179,1020),(1780264338471858179,1021),(1780264338471858179,1022),(1780264338471858179,1023),(1780264338471858179,1024),(1780264338471858179,1026),(1780264338471858179,1027),(1780264338471858179,1028),(1780264338471858179,1029),(1780264338471858179,1030),(1780264338471858179,1040),(1780264338471858179,1041),(1780264338471858179,1042),(1780264338471858179,1043),(1780264338471858179,1044),(1780264338471858179,1045),(1780264338471858179,1900),(1780264338471858179,1901),(1780264338471858179,1902),(1780264338471858179,1903),(1780264338471858179,2000),(1780264338471858179,2001),(1780264338471858179,2002),(1780264338471858179,2003),(1780264338471858179,2004),(1780264338471858179,2005),(1780264338471858179,2006),(1780264338471858179,2007),(1780264338471858179,2008),(1780264338471858179,2009),(1780264338471858179,2010),(1780264338471858179,2100),(1780264338471858179,2101),(1780264338471858179,2102),(1780264338471858179,2103),(1780264338471858179,2104),(1780264338471858179,2105),(1780264338471858179,2106),(1780264338471858179,2107),(1780264338471858179,2108),(1780264338471858179,2109),(1780264338471858193,1),(1780264338471858193,101),(1780264338471858193,103),(1780264338471858193,104),(1780264338471858193,108),(1780264338471858193,500),(1780264338471858193,501),(1780264338471858193,503),(1780264338471858193,1001),(1780264338471858193,1002),(1780264338471858193,1003),(1780264338471858193,1004),(1780264338471858193,1005),(1780264338471858193,1006),(1780264338471858193,1007),(1780264338471858193,1008),(1780264338471858193,1009),(1780264338471858193,1010),(1780264338471858193,1011),(1780264338471858193,1012),(1780264338471858193,1017),(1780264338471858193,1018),(1780264338471858193,1019),(1780264338471858193,1020),(1780264338471858193,1021),(1780264338471858193,1022),(1780264338471858193,1023),(1780264338471858193,1024),(1780264338471858193,1040),(1780264338471858193,1041),(1780264338471858193,1042),(1780264338471858193,1043),(1780264338471858193,1044),(1780264338471858193,1045),(1780264338471858193,2000),(1780264338471858193,2001),(1780264338471858193,2002),(1780264338471858193,2003),(1780264338471858193,2004),(1780264338471858193,2005),(1780264338471858193,2006),(1780264338471858193,2007),(1780264338471858193,2008),(1780264338471858193,2009),(1780264338471858193,2010),(1780264338471858193,2100),(1780264338471858193,2101),(1780264338471858193,2102),(1780264338471858193,2103),(1780264338471858193,2104),(1780264338471858193,2105),(1780264338471858193,2106),(1780264338471858193,2107),(1780264338471858193,2108),(1780264338471858193,2109),(1780264338471858194,1),(1780264338471858194,101),(1780264338471858194,102),(1780264338471858194,103),(1780264338471858194,104),(1780264338471858194,108),(1780264338471858194,500),(1780264338471858194,501),(1780264338471858194,503),(1780264338471858194,1001),(1780264338471858194,1002),(1780264338471858194,1003),(1780264338471858194,1004),(1780264338471858194,1005),(1780264338471858194,1006),(1780264338471858194,1007),(1780264338471858194,1008),(1780264338471858194,1009),(1780264338471858194,1010),(1780264338471858194,1011),(1780264338471858194,1012),(1780264338471858194,1013),(1780264338471858194,1014),(1780264338471858194,1015),(1780264338471858194,1016),(1780264338471858194,1017),(1780264338471858194,1018),(1780264338471858194,1019),(1780264338471858194,1020),(1780264338471858194,1021),(1780264338471858194,1022),(1780264338471858194,1023),(1780264338471858194,1024),(1780264338471858194,1040),(1780264338471858194,1041),(1780264338471858194,1042),(1780264338471858194,1043),(1780264338471858194,1044),(1780264338471858194,1045),(1780264338471858194,2000),(1780264338471858194,2001),(1780264338471858194,2002),(1780264338471858194,2003),(1780264338471858194,2004),(1780264338471858194,2005),(1780264338471858194,2006),(1780264338471858194,2007),(1780264338471858194,2008),(1780264338471858194,2009),(1780264338471858194,2010),(1780264338471858194,2100),(1780264338471858194,2101),(1780264338471858194,2102),(1780264338471858194,2103),(1780264338471858194,2104),(1780264338471858194,2105),(1780264338471858194,2106),(1780264338471858194,2107),(1780264338471858194,2108),(1780264338471858194,2109),(1780264338471858195,101),(1780264338471858195,102),(1780264338471858195,103),(1780264338471858195,104),(1780264338471858195,500),(1780264338471858195,501),(1780264338471858195,1001),(1780264338471858195,1008),(1780264338471858195,1013),(1780264338471858195,1017),(1780264338471858195,1021),(1780264338471858195,1040),(1780264338471858195,1041),(1780264338471858195,1042),(1780264338471858195,1043),(1780264338471858195,1044),(1780264338471858195,1045),(1780264338471858195,2000),(1780264338471858195,2001),(1780264338471858195,2002),(1780264338471858195,2003),(1780264338471858195,2004),(1780264338471858195,2007),(1780264338471858195,2008),(1780264338471858195,2009),(1780264338471858195,2010),(1780264338471858195,2100),(1780264338471858195,2101),(1780264338471858195,2102),(1780264338471858195,2103),(1780264338471858195,2104),(1780264338471858195,2105),(1780264338471858195,2106),(1780264338471858195,2107),(1780264338471858195,2108),(1780264338471858195,2109);
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_post`
--

DROP TABLE IF EXISTS `sys_role_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_post` (
  `post_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`role_id`,`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='岗位角色关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_post`
--

LOCK TABLES `sys_role_post` WRITE;
/*!40000 ALTER TABLE `sys_role_post` DISABLE KEYS */;
INSERT INTO `sys_role_post` VALUES (13,1780264338471858193),(14,1780264338471858194),(16,1780264338471858195),(17,1780264338471858195);
/*!40000 ALTER TABLE `sys_role_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_tenant`
--

DROP TABLE IF EXISTS `sys_tenant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_tenant` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `tenant_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户编号',
  `contact_user_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '联系人',
  `contact_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '联系电话',
  `company_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '企业名称',
  `license_number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '统一社会信用代码',
  `address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '地址',
  `intro` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '企业简介',
  `domain` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '域名',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `package_id` bigint DEFAULT NULL COMMENT '租户套餐编号',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `account_count` int DEFAULT '-1' COMMENT '用户数量（-1不限制）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '租户状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='租户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_tenant`
--

LOCK TABLES `sys_tenant` WRITE;
/*!40000 ALTER TABLE `sys_tenant` DISABLE KEYS */;
INSERT INTO `sys_tenant` VALUES (1,'000000','管理组','15888888888','XXX有限公司',NULL,NULL,'多租户通用后台管理管理系统',NULL,NULL,NULL,NULL,-1,'0','2',103,1,'2023-05-14 15:19:39',1,'2025-12-25 15:24:58'),(14,'2008535393950965760','袁振栋','18781111111','一语暗远科技有限公司',NULL,'上海市西藏中路268号来福士广场办公大楼501室','简介','www.yuan.com','备注',NULL,NULL,100,'0','0',NULL,1,'2026-01-06 21:45:29',1,'2026-01-06 21:45:29');
/*!40000 ALTER TABLE `sys_tenant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_tenant_package`
--

DROP TABLE IF EXISTS `sys_tenant_package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_tenant_package` (
  `package_id` bigint NOT NULL AUTO_INCREMENT COMMENT '租户套餐id',
  `package_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '套餐名称',
  `menu_ids` varchar(3000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '关联菜单id',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `menu_check_strictly` tinyint(1) DEFAULT '1' COMMENT '菜单树选择项是否关联显示',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`package_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='租户套餐表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_tenant_package`
--

LOCK TABLES `sys_tenant_package` WRITE;
/*!40000 ALTER TABLE `sys_tenant_package` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_tenant_package` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `open_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '微信用户标识',
  `user_grade` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '用户等级',
  `user_balance` double(20,2) DEFAULT '0.00' COMMENT '账户余额',
  `tenant_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '000000' COMMENT '租户编号',
  `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户昵称',
  `user_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'sys_user' COMMENT '用户类型（sys_user系统用户）',
  `user_plan` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'Free' COMMENT '用户套餐',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '手机号码',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '头像地址',
  `wx_avatar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '微信头像地址',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '密码',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `domain_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注册域名',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `krole_group_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '关联知识库角色/角色组',
  `krole_group_ids` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '关联知识库角色/角色组id',
  `primary_post_id` bigint DEFAULT NULL COMMENT '主岗位',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1780264338471858192 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,NULL,'1',855.40,'00000','admin','admin','sys_user','Free','571145417@qq.com','15888888881','1','https://gw.alipayobjects.com/zos/rmsportal/KDpgvguMpGfqaHPjicRK.svg',NULL,'$2a$10$3z20wM5TAK087TruCwDCsO9guXP.ADMc7jdPN1b5QAYSms6YSsYbG','0','0','0:0:0:0:0:0:0:1','2026-01-07 22:24:03',NULL,103,1,'2023-05-14 15:19:39',1,'2026-01-07 22:24:03','平台管理员',NULL,NULL,NULL),(2,NULL,'0',0.00,'000000','plat','plat','sys_user','Free','1231123@qq.com','','0',NULL,NULL,'$2a$10$J2UkMyvqaMV9ZylSRLmSDeeiNtWUjGj9ZVtr9N4DoKoqJUVMFmDSK','0','0','0:0:0:0:0:0:0:1','2025-12-25 20:30:18',NULL,NULL,NULL,'2025-12-25 17:06:43',1,'2025-12-25 20:30:18','plat',NULL,NULL,NULL),(1780264338471858188,NULL,'0',0.00,'2008535393950965760','U732864','袁振栋','sys_user','Free','571145417@qq.com','18781111111','0',NULL,NULL,'$2a$10$eQhP3LzsROW9BGLj64/BQeU4hjm5N8mw50U31a1ia4KgIcJJISX9u','0','0','0:0:0:0:0:0:0:1','2026-01-07 22:19:50',NULL,NULL,1,'2026-01-06 21:45:30',1,'2026-01-07 22:19:50',NULL,NULL,NULL,14),(1780264338471858189,NULL,'0',0.00,'2008535393950965760','U066688','袁1号','sys_user','Free','123123@qq.com','18192831241','0',NULL,NULL,'$2a$10$X65MpfnRItCthoAOpm2VE.2twVEl4U1IbR6ayVydRceMjzKyRxDfu','0','0','',NULL,NULL,NULL,1780264338471858188,'2026-01-06 23:09:51',1,'2026-01-07 22:05:02','12312',NULL,NULL,15),(1780264338471858190,NULL,'0',0.00,'2008535393950965760','U495360','袁JAVA1号','sys_user','Free','yuanjava1@qq.com','','0',NULL,NULL,'$2a$10$kXUx7dfaRrcjgvpmRBhEUez7ZT3ynDcvpzmhLuVITUhJsrtztFEBC','0','0','0:0:0:0:0:0:0:1','2026-01-07 21:44:26',NULL,124,1780264338471858188,'2026-01-07 21:39:36',1,'2026-01-07 21:44:26','后端开发一号',NULL,NULL,16),(1780264338471858191,NULL,'0',0.00,'2008535393950965760','U169664','袁VUE1号','sys_user','Free','yuanvue1@qq.com','','1',NULL,NULL,'$2a$10$6fmzvbuGZwtat7sBzF3hx.ckaol26wPeHzKY2182YXDG4qws1lnpy','0','0','',NULL,NULL,124,1780264338471858188,'2026-01-07 21:40:02',1,'2026-01-07 22:05:14','VUE前端开发一号',NULL,NULL,17);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_post`
--

DROP TABLE IF EXISTS `sys_user_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_post` (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `post_id` bigint NOT NULL COMMENT '岗位ID',
  `is_primary` tinyint DEFAULT '0',
  PRIMARY KEY (`user_id`,`post_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户与岗位关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_post`
--

LOCK TABLES `sys_user_post` WRITE;
/*!40000 ALTER TABLE `sys_user_post` DISABLE KEYS */;
INSERT INTO `sys_user_post` VALUES (1,1,1),(1780264338471858188,14,1),(1780264338471858189,15,1),(1780264338471858190,16,1),(1780264338471858191,17,1);
/*!40000 ALTER TABLE `sys_user_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_role` (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户和角色关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` VALUES (1,1);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_workflow`
--

DROP TABLE IF EXISTS `t_workflow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_workflow` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uuid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'uuid',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '标题',
  `user_id` bigint NOT NULL DEFAULT '0' COMMENT '用户ID',
  `is_public` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否公开',
  `is_enable` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否启用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '备注',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除 默认0不删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=120 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='工作流定义（用户定义的工作流）| Workflow Definition';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_workflow`
--

LOCK TABLES `t_workflow` WRITE;
/*!40000 ALTER TABLE `t_workflow` DISABLE KEYS */;
INSERT INTO `t_workflow` VALUES (119,'7c95c7892dd544788d90e49ce2fad966','测试工作流',1,1,1,'2025-11-07 16:44:41','2025-11-07 16:44:41','测试工作流',0);
/*!40000 ALTER TABLE `t_workflow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_workflow_component`
--

DROP TABLE IF EXISTS `t_workflow_component`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_workflow_component` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `uuid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `display_order` int NOT NULL DEFAULT '0',
  `is_enable` tinyint(1) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_display_order` (`display_order`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='工作流组件库 | Workflow Component';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_workflow_component`
--

LOCK TABLES `t_workflow_component` WRITE;
/*!40000 ALTER TABLE `t_workflow_component` DISABLE KEYS */;
INSERT INTO `t_workflow_component` VALUES (17,'5cd68dccbbb411f0bb7840c2ba9a7fbc','Start','开始','流程由此开始',0,1,'2025-11-07 16:32:49','2025-11-07 16:32:49',0),(18,'5cd6ac69bbb411f0bb7840c2ba9a7fbc','End','结束','流程由此结束',0,1,'2025-11-07 16:32:49','2025-11-07 16:32:49',0),(19,'5cd6c8eabbb411f0bb7840c2ba9a7fbc','Answer','生成回答','调用大语言模型回答问题',0,1,'2025-11-07 16:32:49','2025-11-07 16:32:49',0);
/*!40000 ALTER TABLE `t_workflow_component` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_workflow_edge`
--

DROP TABLE IF EXISTS `t_workflow_edge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_workflow_edge` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uuid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '边唯一标识',
  `workflow_id` bigint NOT NULL DEFAULT '0' COMMENT '所属工作流定义 id',
  `source_node_uuid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '起始节点 uuid',
  `source_handle` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '起始锚点标识',
  `target_node_uuid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '目标节点 uuid',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除：0 正常，1 已删',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_workflow_edge_workflow_id` (`workflow_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=199 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='工作流定义的边 | Edge of Workflow Definition';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_workflow_edge`
--

LOCK TABLES `t_workflow_edge` WRITE;
/*!40000 ALTER TABLE `t_workflow_edge` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_workflow_edge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_workflow_node`
--

DROP TABLE IF EXISTS `t_workflow_node`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_workflow_node` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uuid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '节点唯一标识',
  `workflow_id` bigint NOT NULL DEFAULT '0' COMMENT '所属工作流定义 id',
  `workflow_component_id` bigint NOT NULL DEFAULT '0' COMMENT '引用的组件 id',
  `user_id` bigint NOT NULL DEFAULT '0' COMMENT '创建人',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '节点标题',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '节点备注',
  `input_config` json NOT NULL COMMENT '输入参数模板，例：{"params":[{"name":"user_define_param01","type":"string"}]}',
  `node_config` json DEFAULT NULL COMMENT '节点执行配置，例：{"params":[{"prompt":"Summarize the following content:{user_define_param01}"}]}',
  `position_x` double NOT NULL DEFAULT '0' COMMENT '画布 x 坐标',
  `position_y` double NOT NULL DEFAULT '0' COMMENT '画布 y 坐标',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除：0 正常，1 已删',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_workflow_node_workflow_id` (`workflow_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=270 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='工作流定义的节点 | Node of Workflow Definition';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_workflow_node`
--

LOCK TABLES `t_workflow_node` WRITE;
/*!40000 ALTER TABLE `t_workflow_node` DISABLE KEYS */;
INSERT INTO `t_workflow_node` VALUES (269,'f4660cebe26b439f8264ad0111b56c85',119,17,0,'开始','用户输入','{\"ref_inputs\": [], \"user_inputs\": [{\"name\": \"var_user_input\", \"type\": 1, \"uuid\": \"dc9590d781764ace943bf03b383e742b\", \"title\": \"用户输入\", \"required\": false, \"max_length\": 1000}]}','{}',0,0,'2025-11-07 16:44:41','2025-11-07 16:44:41',0);
/*!40000 ALTER TABLE `t_workflow_node` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_workflow_runtime`
--

DROP TABLE IF EXISTS `t_workflow_runtime`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_workflow_runtime` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uuid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '运行实例唯一标识',
  `user_id` bigint NOT NULL DEFAULT '0' COMMENT '启动人',
  `workflow_id` bigint NOT NULL DEFAULT '0' COMMENT '对应工作流定义 id',
  `input` json DEFAULT NULL COMMENT '运行输入，例：{"userInput01":"text01","userInput02":true,"userInput03":10,"userInput04":["selectedA","selectedB"],"userInput05":["https://a.com/a.xlsx","https://a.com/b.png"]}',
  `output` json DEFAULT NULL COMMENT '运行输出，成功或失败的结果',
  `status` smallint NOT NULL DEFAULT '1' COMMENT '执行状态：1 就绪，2 执行中，3 成功，4 失败',
  `status_remark` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '状态补充说明，如失败原因',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除：0 正常，1 已删',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_workflow_runtime_workflow_id` (`workflow_id`) USING BTREE,
  KEY `idx_workflow_runtime_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=297 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='工作流实例（运行时）| Workflow Runtime';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_workflow_runtime`
--

LOCK TABLES `t_workflow_runtime` WRITE;
/*!40000 ALTER TABLE `t_workflow_runtime` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_workflow_runtime` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_workflow_runtime_node`
--

DROP TABLE IF EXISTS `t_workflow_runtime_node`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_workflow_runtime_node` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uuid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '节点运行实例唯一标识',
  `user_id` bigint NOT NULL DEFAULT '0' COMMENT '创建人',
  `workflow_runtime_id` bigint NOT NULL DEFAULT '0' COMMENT '所属运行实例 id',
  `node_id` bigint NOT NULL DEFAULT '0' COMMENT '对应工作流定义里的节点 id',
  `input` json DEFAULT NULL COMMENT '节点本次输入数据',
  `output` json DEFAULT NULL COMMENT '节点本次输出数据',
  `status` smallint NOT NULL DEFAULT '1' COMMENT '节点执行状态：1 进行中，2 失败，3 成功',
  `status_remark` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '状态补充说明，如失败堆栈',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除：0 正常，1 已删',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_runtime_node_runtime_id` (`workflow_runtime_id`) USING BTREE,
  KEY `idx_runtime_node_node_id` (`node_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=805 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='工作流实例（运行时）- 节点 | Workflow Runtime Node';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_workflow_runtime_node`
--

LOCK TABLES `t_workflow_runtime_node` WRITE;
/*!40000 ALTER TABLE `t_workflow_runtime_node` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_workflow_runtime_node` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wf_biz_ref`
--

DROP TABLE IF EXISTS `wf_biz_ref`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wf_biz_ref` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `biz_type` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务类型，如 LEAVE/REIMBURSE',
  `biz_id` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务主键',
  `instance_id` bigint NOT NULL COMMENT '流程实例ID',
  `status` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'RUNNING/APPROVED/REJECTED/CANCELED',
  `create_dept` bigint DEFAULT NULL,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_biz_instance` (`biz_type`,`biz_id`,`instance_id`),
  KEY `idx_instance` (`instance_id`),
  KEY `idx_biz` (`biz_type`,`biz_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wf_biz_ref`
--

LOCK TABLES `wf_biz_ref` WRITE;
/*!40000 ALTER TABLE `wf_biz_ref` DISABLE KEYS */;
/*!40000 ALTER TABLE `wf_biz_ref` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wf_cc`
--

DROP TABLE IF EXISTS `wf_cc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wf_cc` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '抄送ID',
  `tenant_id` varchar(20) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '000000' COMMENT '租户ID',
  `instance_id` bigint NOT NULL COMMENT '流程实例ID',
  `user_id` bigint NOT NULL COMMENT '被抄送人',
  `read_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '0' COMMENT '是否已读(0未读 1已读)',
  `create_dept` bigint DEFAULT NULL,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_user` (`user_id`),
  KEY `idx_instance` (`instance_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='流程抄送表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wf_cc`
--

LOCK TABLES `wf_cc` WRITE;
/*!40000 ALTER TABLE `wf_cc` DISABLE KEYS */;
/*!40000 ALTER TABLE `wf_cc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wf_definition`
--

DROP TABLE IF EXISTS `wf_definition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wf_definition` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(20) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '000000' COMMENT '租户ID',
  `definition_key` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '流程KEY（业务类型标识）',
  `definition_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '流程名称',
  `version` int NOT NULL COMMENT '版本号，从1递增',
  `status` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'DRAFT/PUBLISHED/DISABLED',
  `form_schema` json DEFAULT NULL COMMENT '表单Schema(JSON)',
  `flow_json` json DEFAULT NULL COMMENT '流程定义JSON',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_dept` bigint DEFAULT NULL,
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '0存在 2删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_key_ver` (`tenant_id`,`definition_key`,`version`),
  KEY `idx_tenant_key` (`tenant_id`,`definition_key`),
  KEY `idx_tenant_status` (`tenant_id`,`status`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='流程定义';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wf_definition`
--

LOCK TABLES `wf_definition` WRITE;
/*!40000 ALTER TABLE `wf_definition` DISABLE KEYS */;
INSERT INTO `wf_definition` VALUES (1,'0','leave','请假',11,'DRAFT',NULL,'{\"edges\": [{\"id\": \"4f7b2200-ab42-4bf9-9f38-19b58119492e\", \"type\": \"polyline\", \"endPoint\": {\"x\": 298.6666717529297, \"y\": 120.66667175292967}, \"pointsList\": [{\"x\": 190, \"y\": 120}, {\"x\": 244.33333587646484, \"y\": 120}, {\"x\": 244.33333587646484, \"y\": 120.66667175292967}, {\"x\": 298.6666717529297, \"y\": 120.66667175292967}], \"properties\": {}, \"startPoint\": {\"x\": 190, \"y\": 120}, \"sourceNodeId\": \"start\", \"targetNodeId\": \"1602f8e0-2aba-4239-9b4b-5ec8049436bd\", \"sourceAnchorId\": \"start_1\", \"targetAnchorId\": \"1602f8e0-2aba-4239-9b4b-5ec8049436bd_3\"}, {\"id\": \"24b941db-ab67-4c84-bc83-8901187fe706\", \"type\": \"polyline\", \"endPoint\": {\"x\": 550.6666717529297, \"y\": 121.66667175292967}, \"pointsList\": [{\"x\": 398.6666717529297, \"y\": 120.66667175292967}, {\"x\": 474.6666717529297, \"y\": 120.66667175292967}, {\"x\": 474.6666717529297, \"y\": 121.66667175292967}, {\"x\": 550.6666717529297, \"y\": 121.66667175292967}], \"properties\": {}, \"startPoint\": {\"x\": 398.6666717529297, \"y\": 120.66667175292967}, \"sourceNodeId\": \"1602f8e0-2aba-4239-9b4b-5ec8049436bd\", \"targetNodeId\": \"049e5205-a0d4-4858-b36b-02349c845a8c\", \"sourceAnchorId\": \"1602f8e0-2aba-4239-9b4b-5ec8049436bd_1\", \"targetAnchorId\": \"049e5205-a0d4-4858-b36b-02349c845a8c_3\"}], \"nodes\": [{\"x\": 140, \"y\": 120, \"id\": \"start\", \"text\": {\"x\": 140, \"y\": 120, \"value\": \"开始\"}, \"type\": \"circle\", \"properties\": {\"width\": 100, \"height\": 100, \"status\": \"start\", \"wfType\": \"START\"}}, {\"x\": 348.6666717529297, \"y\": 120.66667175292967, \"id\": \"1602f8e0-2aba-4239-9b4b-5ec8049436bd\", \"text\": {\"x\": 348.6666717529297, \"y\": 120.66667175292967, \"value\": \"人事审批\"}, \"type\": \"rect\", \"properties\": {\"width\": 100, \"height\": 80, \"wfType\": \"USER_TASK\", \"assignee\": {\"kind\": \"FIXED\", \"users\": [{\"userId\": \"1780264338471858184\", \"deptName\": \"上海-开发部\", \"nickName\": \"袁IT\", \"postName\": \"人事\"}], \"userIds\": [\"1780264338471858184\"]}}}, {\"x\": 600.6666717529297, \"y\": 121.66667175292967, \"id\": \"049e5205-a0d4-4858-b36b-02349c845a8c\", \"text\": {\"x\": 600.6666717529297, \"y\": 121.66667175292967, \"value\": \"结束\"}, \"type\": \"circle\", \"properties\": {\"width\": 100, \"height\": 100, \"wfType\": \"END\"}}]}','1',NULL,'2025-12-30 21:25:23',1,'2026-01-03 22:33:52',NULL,'0');
/*!40000 ALTER TABLE `wf_definition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wf_instance`
--

DROP TABLE IF EXISTS `wf_instance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wf_instance` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(20) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '000000',
  `definition_id` bigint NOT NULL,
  `business_key` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务单号/业务主键',
  `status` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'RUNNING/APPROVED/REJECTED/CANCELED',
  `start_user_id` bigint NOT NULL,
  `start_dept_id` bigint DEFAULT NULL,
  `variables_json` json DEFAULT NULL COMMENT '流程变量(JSON):金额/部门/请假类型等',
  `start_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `end_time` datetime DEFAULT NULL,
  `create_dept` bigint DEFAULT NULL,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_tenant_def` (`tenant_id`,`definition_id`),
  KEY `idx_tenant_biz` (`tenant_id`,`business_key`),
  KEY `idx_tenant_status` (`tenant_id`,`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='流程实例';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wf_instance`
--

LOCK TABLES `wf_instance` WRITE;
/*!40000 ALTER TABLE `wf_instance` DISABLE KEYS */;
/*!40000 ALTER TABLE `wf_instance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wf_node_instance`
--

DROP TABLE IF EXISTS `wf_node_instance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wf_node_instance` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(20) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '000000',
  `instance_id` bigint NOT NULL,
  `node_key` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '节点ID(来自flow_json)',
  `node_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'START/APPROVAL/GATEWAY/END',
  `status` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'WAIT/DONE/CANCELED',
  `order_no` int NOT NULL COMMENT '节点序号(运行时生成)',
  `finished_time` datetime DEFAULT NULL,
  `create_dept` bigint DEFAULT NULL,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_inst_order` (`instance_id`,`order_no`),
  KEY `idx_tenant_inst` (`tenant_id`,`instance_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='节点实例';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wf_node_instance`
--

LOCK TABLES `wf_node_instance` WRITE;
/*!40000 ALTER TABLE `wf_node_instance` DISABLE KEYS */;
/*!40000 ALTER TABLE `wf_node_instance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wf_task`
--

DROP TABLE IF EXISTS `wf_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wf_task` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(20) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '000000',
  `instance_id` bigint NOT NULL,
  `node_instance_id` bigint NOT NULL,
  `assignee_id` bigint NOT NULL COMMENT '处理人',
  `status` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'TODO/DONE/TRANSFERRED/CANCELED',
  `action` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'APPROVE/REJECT/TRANSFER/ADD_SIGN/WITHDRAW',
  `comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `finished_time` datetime DEFAULT NULL,
  `create_dept` bigint DEFAULT NULL,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_assignee_status` (`tenant_id`,`assignee_id`,`status`),
  KEY `idx_inst` (`tenant_id`,`instance_id`),
  KEY `idx_node_inst` (`node_instance_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='审批任务';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wf_task`
--

LOCK TABLES `wf_task` WRITE;
/*!40000 ALTER TABLE `wf_task` DISABLE KEYS */;
/*!40000 ALTER TABLE `wf_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wf_task_log`
--

DROP TABLE IF EXISTS `wf_task_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wf_task_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(20) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '000000',
  `task_id` bigint NOT NULL,
  `instance_id` bigint NOT NULL,
  `action` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `operator_id` bigint NOT NULL,
  `comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_dept` bigint DEFAULT NULL,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_inst_time` (`tenant_id`,`instance_id`,`operate_time`),
  KEY `idx_task` (`tenant_id`,`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='审批任务日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wf_task_log`
--

LOCK TABLES `wf_task_log` WRITE;
/*!40000 ALTER TABLE `wf_task_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `wf_task_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'yuan'
--

--
-- Dumping routines for database 'yuan'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-01-07 22:54:47
