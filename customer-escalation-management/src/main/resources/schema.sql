CREATE TABLE IF NOT EXISTS `problem` (
                          `problem_id` bigint(50) NOT NULL,
                          `name` varchar(50) DEFAULT NULL,
                          PRIMARY KEY (`problem_id`)
);
CREATE TABLE IF NOT EXISTS `root_cause` (
                                         `rc_id` bigint(50) NOT NULL,
                                         `name` varchar(50) DEFAULT NULL,
                                         PRIMARY KEY (`rc_id`)
);
