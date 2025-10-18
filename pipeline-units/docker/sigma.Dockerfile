FROM gradle:8.14.3-jdk21-noble AS build

COPY sigma /opt/app/sigma
COPY build-logic /opt/app/build-logic
COPY config /opt/app/config
COPY gradle /opt/app/gradle
COPY build.gradle.kts /opt/app/build.gradle.kts
COPY gradle.properties /opt/app/gradle.properties
COPY gradlew /opt/app/gradlew
COPY settings.gradle.kts /opt/app/settings.gradle.kts

WORKDIR /opt/app

RUN gradle build --parallel :sigma:build :sigma:distTar

FROM eclipse-temurin:21.0.7_6-jre-noble AS app

COPY --from=build /opt/app/sigma/build/distributions/sigma.tar /opt/app/sigma.tar

WORKDIR /opt/app
RUN tar -xvf sigma.tar && rm sigma.tar

ENTRYPOINT ["/bin/bash", "-c", "/opt/app/sigma/bin/sigma"]
