--
CREATE TABLE IF NOT EXISTS public.tm_exercise
(
    id_exercise                 BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    result_expected             varchar(50) NOT NULL,
    level                       INT NOT NULL,
    category                    varchar(50) NOT NULL,
    type                        varchar(50) NOT NULL,
    PRIMARY KEY (id_exercise)
);

CREATE TABLE IF NOT EXISTS public.tm_image
(
    id_image                    BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    image_data                  TEXT NOT NULL, --TYPE?
    name                        varchar(50) NOT NULL, --UNIQUE?
    PRIMARY KEY (id_image)
);

CREATE TABLE IF NOT EXISTS public.tm_image_exercise
(
    id_image                    BIGINT NOT NULL,
    id_exercise                 BIGINT NOT NULL,
    PRIMARY KEY (id_image, id_exercise),
    FOREIGN KEY (id_image) REFERENCES tm_image (id_image),
    FOREIGN KEY (id_exercise) REFERENCES tm_exercise (id_exercise)
);

CREATE TABLE IF NOT EXISTS public.tm_phoneme
(
    id_phoneme                  BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    phoneme                     varchar(50) NOT NULL, --DEBERIA SER UNICO?
    PRIMARY KEY (id_phoneme)
);

CREATE TABLE IF NOT EXISTS public.tm_phoneme_articulation
(
    id_articulation             BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    id_phoneme                  BIGINT NOT NULL,
    url_video                   varchar(150) NOT NULL,
    PRIMARY KEY (id_articulation),
    FOREIGN KEY (id_phoneme) REFERENCES tm_phoneme (id_phoneme)
);

CREATE TABLE IF NOT EXISTS public.tm_exercise_phoneme
(
    id_phoneme                  BIGINT NOT NULL,
    id_exercise                 BIGINT NOT NULL,
    --level                       INT NOT NULL,
    PRIMARY KEY (id_phoneme, id_exercise),
    FOREIGN KEY (id_phoneme) REFERENCES tm_phoneme (id_phoneme),
    FOREIGN KEY (id_exercise) REFERENCES tm_exercise (id_exercise)
);

CREATE TABLE IF NOT EXISTS public.tm_role
(
    id_role                     BIGINT NOT NULL,
    name                        varchar(150) NOT NULL UNIQUE,
    PRIMARY KEY (id_role)
);

CREATE TABLE IF NOT EXISTS public.tm_professional
(
    id_professional             BIGINT NOT NULL,
    id_role                     BIGINT NOT NULL,
    first_name                  varchar(150) NOT NULL,
    last_name                   varchar(150) NOT NULL,
    age                         INT NOT NULL,
    PRIMARY KEY (id_professional),
    FOREIGN KEY (id_role) REFERENCES tm_role (id_role)
);

CREATE TABLE IF NOT EXISTS public.tm_patient
(
    id_patient                  BIGINT NOT NULL,
    id_professional             BIGINT,
    id_role                     BIGINT NOT NULL,
    first_name                  varchar(150) NOT NULL,
    last_name                   varchar(150) NOT NULL,
    age                         INT NOT NULL,
    PRIMARY KEY (id_patient),
    FOREIGN KEY (id_role) REFERENCES tm_role (id_role),
    FOREIGN KEY (id_professional) REFERENCES tm_professional (id_professional)
);

CREATE TABLE IF NOT EXISTS public.tm_task_group
(
    id_task_group               BIGINT NOT NULL,
    --id_phoneme                  BIGINT NOT NULL,
    id_patient                  BIGINT NOT NULL,
    status                      varchar(150) NOT NULL,
    --level                       INT NOT NULL,
    --category                    varchar(150) NOT NULL,
    PRIMARY KEY (id_task_group),
    --FOREIGN KEY (id_phoneme) REFERENCES tm_phoneme (id_phoneme),
    FOREIGN KEY (id_patient) REFERENCES tm_patient (id_patient)
);

CREATE TABLE IF NOT EXISTS public.tm_task_group_detail
(
    id_task                     BIGINT NOT NULL,
    id_task_group               BIGINT NOT NULL,
    id_exercise                 BIGINT NOT NULL,
    url_audio                   varchar(250) NOT NULL,--variable?
    result                      varchar(150) NOT NULL,
    PRIMARY KEY (id_task),
    FOREIGN KEY (id_task_group) REFERENCES tm_task_group (id_task_group),
    FOREIGN KEY (id_exercise) REFERENCES tm_exercise (id_exercise)
);

--GENERAR TABLAS PARA CATEGORY (SILABA, PALABRA, FRASE) y LEVEL (1,2,3)?

