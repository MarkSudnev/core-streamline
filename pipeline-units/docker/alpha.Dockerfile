FROM gradle:8.14.3-jdk21-noble AS build

COPY alpha /opt/app/alpha
COPY build-logic /opt/app/build-logic
COPY config /opt/app/config
COPY gradle /opt/app/gradle
COPY shared /opt/app/shared
COPY build.gradle.kts /opt/app/build.gradle.kts
COPY gradle.properties /opt/app/gradle.properties
COPY gradlew /opt/app/gradlew
COPY settings.gradle.kts /opt/app/settings.gradle.kts

WORKDIR /opt/app

RUN gradle build --parallel :alpha:build :alpha:distTar

FROM eclipse-temurin:21.0.7_6-jre-noble AS app

COPY --from=build /opt/app/alpha/build/distributions/alpha.tar /opt/app/alpha.tar

WORKDIR /opt/app
RUN tar -xvf alpha.tar && rm alpha.tar

ENTRYPOINT ["/bin/bash", "-c", "/opt/app/alpha/bin/alpha"]
