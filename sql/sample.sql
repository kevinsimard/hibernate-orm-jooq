CREATE DATABASE IF NOT EXISTS `example`; USE `example`;

CREATE TABLE IF NOT EXISTS `user`(
    `id` BIGINT AUTO_INCREMENT,
    `username` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    PRIMARY KEY(`id`)
);

INSERT INTO `user` VALUES(
    null, 'foobar', 'foo@bar.com'
);
