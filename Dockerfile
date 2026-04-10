FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /build

COPY . .

RUN mvn -pl yuan-apps/yuan-admin -am -DskipTests clean package \
    && cp "$(find yuan-apps/yuan-admin/target -maxdepth 1 -type f -name '*.jar' ! -name '*original' | head -n 1)" /tmp/app.jar

FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=builder /tmp/app.jar /app/app.jar

EXPOSE 6011
ENV JAVA_OPTS=""

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
