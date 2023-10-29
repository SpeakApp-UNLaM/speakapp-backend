ALTER TABLE tm_task_group_detail
ALTER COLUMN url_audio TYPE varchar(255);
ALTER TABLE tm_task_group_detail
ALTER COLUMN result TYPE varchar(255);

ALTER TABLE tm_task_group_detail
ALTER COLUMN url_audio DROP NOT NULL;
ALTER TABLE tm_task_group_detail
ALTER COLUMN result DROP NOT NULL;

CREATE TABLE IF NOT EXISTS public.tm_task_item_detail
(
    id_task_item_detail         BIGINT NOT NULL,
    id_task                     BIGINT NOT NULL,--id_task_item
    id_image_selected           BIGINT NOT NULL,
    name_selected               varchar(255),
    PRIMARY KEY (id_task_item_detail),
    FOREIGN KEY (id_task) REFERENCES tm_task_group_detail (id_task),
    FOREIGN KEY (id_image_selected) REFERENCES tm_image (id_image)
)