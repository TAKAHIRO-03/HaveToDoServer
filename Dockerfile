FROM maven:3.8.7-amazoncorretto-17
ENTRYPOINT ["mvn", "clean", "compile", "spring-boot:run", "-f", "/app/HaveTodoServer"]