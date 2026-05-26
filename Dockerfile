# build

FROM maven:3.8.8-amazoncorretto-21-al2023 as build

WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests

# run

FROM amazoncorretto:21.0.5
WORKDIR /app

COPY --from=build ./build/target/*.jar ./libraryapi.jar

EXPOSE 8080
EXPOSE 9090

ENV DS_URL=''
ENV DS_USERNAME=''
ENV DS_PASSWORD=''
ENV GOOGLE_CLIENT_ID='client_id'
ENV GOOGLE_CLIENT_SECRET='client_secret'

ENV spring_profiles_active='production'
ENV TZ='America/Sao_Paulo'

ENTRYPOINT java -jar libraryapi.jar