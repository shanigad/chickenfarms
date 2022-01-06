DROP DATABASE IF EXISTS `escalation-management_db`;
CREATE DATABASE IF NOT EXISTS `customer_escalations_manager_db`;
CREATE TABLE IF NOT EXISTS `problem` (
                          `problem_id` bigint(50) NOT NULL AUTO_INCREMENT,
                          `name` varchar(50) DEFAULT NULL,
                          PRIMARY KEY (`problem_id`)
);

CREATE TABLE IF NOT EXISTS `root_cause` (
                                         `rc_id` bigint(50) NOT NULL AUTO_INCREMENT,
                                         `name` varchar(50) DEFAULT NULL,
                                         PRIMARY KEY (`rc_id`)
);
