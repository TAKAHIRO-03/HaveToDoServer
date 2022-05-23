FROM maven:3.8.5-amazoncorretto-17
COPY ./ "/app/GoodHabitsServer/"
#ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000","-jar","/app/GoodHabitsServer-0.0.1.jar"]
ENTRYPOINT ["mvn", "clean", "spring-boot:run", "-f", "/app/GoodHabitsServer/"]