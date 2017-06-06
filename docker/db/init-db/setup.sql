SET default_storage_engine = INNODB;

CREATE TABLE example.tweet (
    id       INT            NOT NULL     AUTO_INCREMENT,
    message   VARCHAR(255) NOT NULL,
    modified_at INT NOT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE `foo` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `message` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `modified_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;