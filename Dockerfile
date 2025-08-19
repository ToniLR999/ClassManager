FROM openjdk:17-jdk-slim

WORKDIR /app

# Copiar archivos de Maven del backend
COPY ClassManagerBackEndSpringBoot/pom.xml .
COPY ClassManagerBackEndSpringBoot/.mvn .mvn
COPY ClassManagerBackEndSpringBoot/mvnw .

# Dar permisos de ejecución
RUN chmod +x mvnw

# Descargar dependencias
RUN ./mvnw dependency:go-offline -B

# Copiar código fuente
COPY ClassManagerBackEndSpringBoot/src src

# Construir la aplicación
RUN ./mvnw clean package -DskipTests

# Exponer puerto
EXPOSE 8080

# Ejecutar con configuración de memoria MÍNIMA para Railway
CMD ["java", \
     "-Xmx256m", \
     "-Xms128m", \
     "-XX:+UseG1GC", \
     "-XX:+UseStringDeduplication", \
     "-XX:+UnlockExperimentalVMOptions", \
     "-XX:+UseCGroupMemoryLimitForHeap", \
     "-XX:MaxMetaspaceSize=128m", \
     "-XX:+DisableExplicitGC", \
     "-XX:+OptimizeStringConcat", \
     "-XX:+UseCompressedOops", \
     "-XX:+UseCompressedClassPointers", \
     "-jar", \
     "target/ClassManager-0.0.1-SNAPSHOT.jar"]
