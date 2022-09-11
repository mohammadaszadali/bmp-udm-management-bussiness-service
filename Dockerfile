FROM adoptopenjdk/openjdk8:latest
ADD target/bmp-udm-management-bussiness-service-1.0-SNAPSHOT.jar bmp-udm-management-bussiness-service.jar
RUN sh -c 'touch /bmp-udm-management-bussiness-service.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/bmp-udm-management-bussiness-service.jar"]
