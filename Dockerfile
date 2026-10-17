# =========================================================================
# STAGE 1: Build Environment (Swapped to Ubuntu 'noble')
# =========================================================================
FROM eclipse-temurin:26-jdk-noble AS builder
WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw

RUN ./mvnw dependency:go-offline -B
COPY src ./src
RUN ./mvnw clean package -DskipTests

# =========================================================================
# STAGE 2: Lightweight Runtime Environment
# =========================================================================
FROM eclipse-temurin:26-jre-alpine AS runner
WORKDIR /app

RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.h2.console.enabled=false", "--logging.level.org.apache.coyote.http11.Http11Processor=ERROR"]