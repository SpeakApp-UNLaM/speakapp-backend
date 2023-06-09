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

* levantar la bbdd y la app \
```docker-compose -f docker/docker-compose.yml --project-directory ./docker up```

* levantar solo la bbdd \
```docker-compose -f docker/docker-compose.yml --project-directory ./docker up postgres_speak```

## Stop
* Parar cualquier service del docker-compose1\
```docker-compose -f docker/docker-compose.yml --project-directory ./docker down```



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

![CHEESE!](./doc/images/postman-request-speech-recognition.png)