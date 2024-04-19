FROM amazoncorretto:21

LABEL "purpose"="Application"

COPY ./build/libs /app

ENTRYPOINT java -jar /app/app.jar