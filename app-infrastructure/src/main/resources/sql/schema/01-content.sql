CREATE TABLE IF NOT EXISTS `contents`
(
    `id`               BIGINT        NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `url`               VARCHAR(2000) NOT NULL,
    `description`       VARCHAR(200)  NOT NULL,
    `provider_user_id` BIGINT        NOT NULL,
    `deleted`           BOOLEAN                DEFAULT FALSE,
    `created_at`        DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`        DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT `fk_provider_user_id` FOREIGN KEY (`provider_user_id`) REFERENCES `users`(`id`)
);
