# speakapp-backend
Este repo contiene el backend del proyecto speak-app

## Technologies
1) Instalación IntelliJ IDEA ultimate for students: [link](https://www.youtube.com/watch?v=cnEJxLxOcds)
2) Manegador de dependencias: maven 3.7.x
3) Lenguaje: java 11
4) Frameworks: Spring-Boot 2.7.x, Hibernate 5.6.x
5) Base De datos: postgresql 15.x
6) Docker

## Run
Se debe ejecutar el siguiente comando en la raiz del proyecto (hacer cd ./speakapp-backend)

* crear la imagen docker de speak_app (solo es necesario cuando se agregan nuevas libs) \
```docker-compose -f docker/docker-compose.yml --project-directory ./docker build --no-cache```

* levantar la bbdd y la app \
```docker-compose -f docker/docker-compose.yml --project-directory ./docker up -d```

* levantar solo la bbdd \
```docker-compose -f docker/docker-compose.yml --project-directory ./docker up postgres_speak -d```

## Logs
Para mirar los de un container de docker

*  Mirar logs del postgres\
  ```docker logs -f -t postgres_speak```

* Mirar logs del backend (app de java)\
  ```docker logs -f -t speak_app```


## Stop
Parar/showdown de los container de docker

* Parar cualquier service del docker-compose\
```docker-compose -f docker/docker-compose.yml --project-directory ./docker down```

## SWAGGER

* [link swagger](http://localhost:9292/speak-app/swagger-ui/index.html)


## Dump Postgres

*  Dump de la bbdd ***speak_db***
```
docker exec -i postgres_speak /usr/bin/pg_dump -U <username> speak_db > 003_dump_speak_app_data.sql
```

*  Dump de la bbdd ***speak_db*** solo la tabla ***tm_image***
```
docker exec -i postgres_speak /usr/bin/pg_dump -U <username> -d speak_db -t tm_image > 003_dump_table_image.sql
```

*  Dump de la bbdd ***speak_db*** sin la tabla ***tm_image***
```
docker exec -i postgres_speak /usr/bin/pg_dump -U <username> speak_db --exclude-table '*tm_image' > 004_dump_database_speak_db_without_image.sql
```
