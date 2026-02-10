# Estágio 1: Build (Compilação)
FROM eclipse-temurin:25-jdk AS build
WORKDIR /app
COPY . .
# Pula os testes no build para ser mais rápido na criação da imagem, 
# mas num pipeline real rodaríamos
RUN ./gradlew bootJar --no-daemon -x test

# Estágio 2: Runtime (Imagem final leve)
FROM eclipse-temurin:25-jdk
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]