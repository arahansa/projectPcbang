-- phpMyAdmin SQL Dump
-- version 3.4.10.1deb1
-- http://www.phpmyadmin.net
--
-- 호스트: localhost
-- 처리한 시간: 14-05-14 07:41 
-- 서버 버전: 5.5.35
-- PHP 버전: 5.3.10-1ubuntu3.10

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 데이터베이스: `test`
--

-- --------------------------------------------------------

--
-- 테이블 구조 `stock`
--

CREATE TABLE IF NOT EXISTS `stock` (
  `itemtype` varchar(20) NOT NULL,
  `item` varchar(20) NOT NULL,
  `price` int(11) NOT NULL,
  `stock` int(11) NOT NULL,
  PRIMARY KEY (`item`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 테이블의 덤프 데이터 `stock`
--

INSERT INTO `stock` (`itemtype`, `item`, `price`, `stock`) VALUES
('과자', '감자깡', 1000, 17),
('과자', '다이제스트', 2500, 10),
('음료', '데미소다(포도맛)', 1000, 20),
('라면', '류현진라면', 99, 89),
('과자', '맛동산', 500, 50),
('음료', '맥콜', 900, 30),
('버거', '불고기버거', 1500, 0),
('라면', '불닭볶음면', 1200, 0),
('과자', '새우깡', 1500, 20),
('버거', '스파이시치킨버거', 2000, 5),
('라면', '신라면', 1200, 0),
('음료', '오렌지쥬스', 1200, 30),
('과자', '조청유과', 1300, 40),
('라면', '짜짜로니', 1200, 5),
('라면', '참깨라면', 1000, 2),
('음료', '칠성사이다', 900, 50),
('버거', '피자버거', 1500, 10),
('음료', '핫식스', 1000, 20);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
