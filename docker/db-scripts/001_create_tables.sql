CREATE TABLE IF NOT EXISTS public.career
(
    id                      BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
    name                    varchar(255) NOT NULL,
    description             TEXT NOT NULL,
    PRIMARY KEY (id)
)