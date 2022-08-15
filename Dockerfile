FROM maven:3.8.5-amazoncorretto-17
ENTRYPOINT ["mvn", "clean", "compile", "spring-boot:run", "-f", "/app/HaveTodoServer"]