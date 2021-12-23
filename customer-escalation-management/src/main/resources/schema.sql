CREATE TABLE IF NOT EXISTS `problem` (
                          `id` int NOT NULL,
                          `name` varchar(50) DEFAULT NULL,
                          PRIMARY KEY (`id`)
);
CREATE TABLE IF NOT EXISTS `root_cause` (
                                         `rc_id` int NOT NULL,
                                         `name` varchar(50) DEFAULT NULL,
                                         PRIMARY KEY (`rc_id`)
);
