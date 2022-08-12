FROM maven:3.8.5-amazoncorretto-17
COPY ./ "/app/GoodHabitsServer/"
ENTRYPOINT ["mvn", "clean", "spring-boot:run", "-f", "/app/GoodHabitsServer/"]