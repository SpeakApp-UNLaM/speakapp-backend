CREATE TABLE IF NOT EXISTS public.career
(
    id                      BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
    name                    varchar(255) NOT NULL,
    description             TEXT NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS public.pending_exercises
(
    id                      BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
    id_usr                    int NOT NULL,
    id_exercise               int NOT NULL,
    id_group_exercise         int NOT NULL,
    PRIMARY KEY (id)
    );
CREATE TABLE IF NOT EXISTS public.tm_exercise
(
    id                      BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
    word_exercise             varchar(10),
    id_group_exercise         int NOT NULL,
    url_image_related         varchar(255) NOT NULL,
    result_expected           varchar(255) NOT NULL,
    level                     int NOT NULL,
    PRIMARY KEY (id)
    );
CREATE TABLE IF NOT EXISTS public.tm_group_exercise
(
    id                      BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
    word_group_exercise       varchar(10),
    id_group_exercise         int NOT NULL,
    PRIMARY KEY (id)
    );

