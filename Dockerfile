FROM openjdk:27-ea-jdk
COPY target/flashSaleEngine.jar flashSaleEngine.jar

ENTRYPOINT ["java", "-jar", "/flashSaleEngine.jar"]