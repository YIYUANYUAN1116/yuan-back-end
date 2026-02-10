FROM eclipse-temurin:17-jre
WORKDIR /app

COPY app.jar /app/app.jar

EXPOSE 6011
ENV JAVA_OPTS=""

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]