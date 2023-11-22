-- --------------------------------------------------------
-- 호스트:                          127.0.0.1
-- 서버 버전:                        8.0.34 - MySQL Community Server - GPL
-- 서버 OS:                        Win64
-- HeidiSQL 버전:                  12.4.0.6659
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- hobby_bungae 데이터베이스 구조 내보내기
CREATE DATABASE IF NOT EXISTS `hobby_bungae` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `hobby_bungae`;

-- 테이블 hobby_bungae.state 구조 내보내기
CREATE TABLE IF NOT EXISTS `state` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `si` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 테이블 데이터 hobby_bungae.state:~45 rows (대략적) 내보내기
INSERT INTO `state` (`id`, `si`) VALUES
	(1, '서울특별시'),
	(2, '부산광역시'),
	(3, '대구광역시'),
	(4, '인천광역시'),
	(5, '대전광역시'),
	(6, '광주광역시'),
	(7, '울산광역시'),
	(8, '세종특별자치시'),
	(9, '수원시'),
	(10, '성남시'),
	(11, '의정부시'),
	(12, '안양시'),
	(13, '부천시'),
	(14, '광명시'),
	(15, '평택시'),
	(16, '동두천시'),
	(17, '안산시'),
	(18, '고양시'),
	(19, '과천시'),
	(20, '구리시'),
	(21, '남양주시'),
	(22, '오산시'),
	(23, '시흥시'),
	(24, '군포시'),
	(25, '의왕시'),
	(26, '하남시'),
	(27, '용인시'),
	(28, '파주시'),
	(29, '이천시'),
	(30, '안성시'),
	(31, '김포시'),
	(32, '화성시'),
	(33, '광주시'),
	(34, '양주시'),
	(35, '포천시'),
	(36, '여주시'),
	(37, '춘천시'),
	(38, '원주시'),
	(39, '강릉시'),
	(40, '동해시'),
	(41, '태백시'),
	(42, '속초시'),
	(43, '삼척시'),
	(44, '청주시'),
	(45, '충주시');

-- 테이블 hobby_bungae.user 구조 내보내기
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `id_name` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 테이블 데이터 hobby_bungae.user:~0 rows (대략적) 내보내기

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
