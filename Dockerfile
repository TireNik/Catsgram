FROM amazoncorretto:21
COPY target/*.jar app.jar
ENV CATSGRAM_IMAGE_DIRECTORY = "images"
ENTRYPOINT ["java","-jar","/app.jar"]

