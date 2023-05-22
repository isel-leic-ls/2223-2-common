FROM openjdk:17
WORKDIR /usr/app
COPY ./static-content ./static-content
COPY ./build/libs ./libs
CMD ["java", "-jar", "./libs/2223-2-common.jar"]
