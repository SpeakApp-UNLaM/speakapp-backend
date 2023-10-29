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


## Test Example

Crear los careers:
```
curl --location 'http://localhost:9292/speak-app/careers' \
--header 'Content-Type: application/json' \
--data '{
"name": "martin",
"description": "description"
}'
```
Obtener los careers:
```
curl --location 'http://localhost:9292/speak-app/careers'
```
Obtener los careers por nombre martin:
```
curl --location 'http://localhost:9292/speak-app/careers/martin'
```
Obtener el reconocimiento de vos:
```
curl --location 'http://localhost:9292/speak-app/speech-recognition/transcription' \
--form 'file=@"/C:/Users/guidobarra/Downloads/carla.wav"'
```