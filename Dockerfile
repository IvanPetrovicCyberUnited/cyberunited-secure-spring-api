FROM eclipse-temurin:21-jre@sha256:2f696f62306d28d32cce2ea6bbb2f2f5b8df736adcc019be61d0f90ce88f07d5
RUN adduser --uid 1000 --disabled-password --gecos '' appuser
WORKDIR /app
COPY build/libs/*.jar app.jar
USER appuser
ENTRYPOINT ["java","-jar","/app/app.jar"]
