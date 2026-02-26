FROM eclipse-temurin:25-jdk-alpine AS base

WORKDIR /build

RUN apk add --no-cache maven
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

FROM base AS extract

RUN java -Djarmode=tools -jar /build/target/*.jar extract --launcher --layers --destination server/build/extracted

FROM eclipse-temurin:25-jre-alpine AS runtime

WORKDIR /app

COPY --from=extract /build/server/build/extracted/dependencies/ ./
COPY --from=extract /build/server/build/extracted/spring-boot-loader/ ./
COPY --from=extract /build/server/build/extracted/snapshot-dependencies/ ./
COPY --from=extract /build/server/build/extracted/application/ ./

ENV SPRING_PROFILES_DEFAULT=container

HEALTHCHECK --interval=1m --timeout=3s CMD wget --spider http://localhost:8080 || exit 1

ENTRYPOINT [ \
    "java", \
    "-Xms512M", "-Xmx2048M", \
    "-XX:+UseParallelGC", \
    "-XX:MinHeapFreeRatio=5", "-XX:MaxHeapFreeRatio=10", \
    "-XX:GCTimeRatio=4", "-XX:AdaptiveSizePolicyWeight=90", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-Djava.io.tmpdir=/tmp/", \
    "org.springframework.boot.loader.launch.JarLauncher", \
    "--spring.config.additional-location=optional:file:/configuration/" \
]