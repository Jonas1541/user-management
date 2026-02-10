# Estágio 1: Build (Compilação)
FROM gradle:8.10.0-jdk25 AS build
WORKDIR /app
COPY --chown=gradle:gradle . .
# Pula os testes no build para ser mais rápido na criação da imagem, 
# mas num pipeline real rodaríamos
RUN gradle bootJar --no-daemon -x test

# Estágio 2: Runtime (Imagem final leve)
FROM openjdk:25-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]