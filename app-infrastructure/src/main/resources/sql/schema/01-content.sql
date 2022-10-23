CREATE TABLE IF NOT EXISTS `contents`
(
    `seq`               BIGINT        NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `uuid`              BINARY(16)    NOT NULL,
    `url`               VARCHAR(2000) NOT NULL,
    `description`       VARCHAR(200)  NOT NULL,
    `provider_user_seq` BIGINT        NOT NULL,
    `deleted`           BOOLEAN                DEFAULT FALSE,
    `created_at`        DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`        DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT `uk_contents_uuid` UNIQUE (`uuid`),
    CONSTRAINT `fk_provider_user_seq` FOREIGN KEY (`provider_user_seq`) REFERENCES `users`(`seq`)
);
