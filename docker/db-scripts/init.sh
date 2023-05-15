
echo                                                                                  \
"SELECT 'CREATE DATABASE ${POSTGRES_DB_CUSTOM}'                                       \
WHERE NOT EXISTS (                                                                    \
                  SELECT                                                              \
                  FROM pg_database                                                    \
                  WHERE datname = '${POSTGRES_DB_CUSTOM}'                             \
                  )\gexec" | psql -U "${POSTGRES_USER}"
echo                                                                                  \
"CREATE TABLE IF NOT EXISTS public.career                                             \
(                                                                                     \
    id                      BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,             \
    name                    varchar(255) NOT NULL,                                    \
    description             TEXT NOT NULL,                                            \
    PRIMARY KEY (id),                                                                 \                                             \
    ON DELETE NO ACTION ON UPDATE NO ACTION                                           \
);" | psql -U "${POSTGRES_USER}" -d "${POSTGRES_DB_CUSTOM}"