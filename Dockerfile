FROM eclipse-temurin:21-jre@sha256:2f696f62306d28d32cce2ea6bbb2f2f5b8df736adcc019be61d0f90ce88f07d5
WORKDIR /app
COPY build/libs/*.jar app.jar
RUN adduser --system --group appuser
USER appuser
ENTRYPOINT ["java","-jar","/app/app.jar"]
