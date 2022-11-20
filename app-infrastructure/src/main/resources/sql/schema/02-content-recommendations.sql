CREATE TABLE IF NOT EXISTS `contentrecommendations`
(
    `id`                    BIGINT        NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `recommender_user_id`   BINARY(16)    NOT NULL,
    `content_id`            BIGINT        NOT NULL,
    `created_at`            DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT `fk_recommender_user_id` FOREIGN KEY (`recommender_user_id`) REFERENCES `users`(`uuid`),
    CONSTRAINT `fk_recommended_content_id` FOREIGN KEY (`content_id`) REFERENCES `contents`(`id`)
);
