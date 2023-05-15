# speakapp-backend
Este repo contiene el backend del proyecto speak-app

## Technologies

1) Manegador de dependencias: maven 3.7.x
2) Lenguaje: java 11
3) Frameworks: Spring-Boot, Hibernate

## run app
Previamente installar docker
Se debe ejecutar el siguiente comando en la raiz del proyecto (hacer cd ./speakapp-backend)
1) docker-compose -f docker/docker-compose.yml --project-directory ./docker up


## Test Example

Crear los careers:

curl --location 'http://localhost:9292/speak-app/careers' \
--header 'Content-Type: application/json' \
--data '{
"name": "martin",
"description": "description"
}'

Obtener los careers:

curl --location 'http://localhost:9292/speak-app/careers'

Obtener los careers por nombre martin:

curl --location 'http://localhost:9292/speak-app/careers/martin'