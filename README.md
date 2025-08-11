# 🎓 ClassManager - Sistema de Gestión Académica

Una aplicación completa de gestión académica desarrollada con **Angular 17** y **Spring Boot 3**, que permite a profesores y administradores gestionar clases, estudiantes, asistencia y calificaciones de manera eficiente.

## 🌐 Demo en Vivo

**🔗 [Ver aplicación en Netlify](https://classmanager-tonilr.netlify.app/)**

## ✨ Características Principales

### 👨‍🏫 Gestión de Profesores
- 👤 Perfiles de profesores completos
- 🔐 Autenticación segura con JWT
- 📊 Dashboard personalizado
- ⚙️ Configuración de preferencias

### 🎯 Gestión de Clases
- 📚 Crear y administrar clases
- 📅 Programar horarios y fechas
- 👥 Asignar estudiantes a clases
- 📝 Descripciones y detalles de clase

### 👨‍🎓 Gestión de Estudiantes
- 👤 Registro completo de estudiantes
- 📋 Información personal y académica
- 🔍 Búsqueda y filtros avanzados
- 📊 Historial académico

### ✅ Control de Asistencia
- 📅 Registro diario de asistencia
- ⏰ Marcado de entrada/salida
- 📊 Estadísticas de asistencia
- 📈 Historial de asistencia por estudiante

### 📊 Sistema de Calificaciones
- 🎯 Asignación de calificaciones
- 📈 Seguimiento del progreso
- 📋 Reportes de rendimiento
- 📊 Promedios y estadísticas

### 🔐 Seguridad y Autenticación
- 🛡️ Autenticación JWT
- 🔒 Registro e inicio de sesión seguro
- 🚪 Protección de rutas
- 👤 Gestión de roles y permisos

## 🛠️ Tecnologías Utilizadas

### Frontend
- **Angular 17** - Framework principal
- **TypeScript** - Lenguaje de programación
- **Angular Material** - Componentes UI
- **RxJS** - Programación reactiva
- **Angular CDK** - Componentes de desarrollo

### Backend
- **Spring Boot 3.3.2** - Framework Java
- **Spring Security** - Autenticación y autorización
- **Spring Data JPA** - Persistencia de datos
- **PostgreSQL** - Base de datos
- **JWT** - Tokens de autenticación
- **Lombok** - Reducción de código boilerplate

### DevOps
- **Netlify** - Despliegue frontend
- **Railway** - Despliegue backend
- **GitHub** - Control de versiones
- **Docker** - Containerización

## 🚀 Instalación y Configuración

### Prerrequisitos
- Node.js 18 o superior
- Java 17 o superior
- PostgreSQL 13 o superior
- Maven 3.6 o superior

### Frontend (Angular)

```bash
# Clonar el repositorio
git clone https://github.com/tu-usuario/class-manager.git
cd class-manager/ClassManagerFrontEndAngular

# Instalar dependencias
npm install

# Configurar variables de entorno
# Crear archivo src/environments/environment.ts
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080'
};

# Ejecutar en modo desarrollo
npm start

# Construir para producción
npm run build
```

### Backend (Spring Boot)

```bash
# Navegar al directorio del backend
cd ../ClassManagerBackEndSpringBoot

# Configurar base de datos PostgreSQL
# Crear archivo src/main/resources/application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/classmanager
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password

# Ejecutar la aplicación
./mvnw spring-boot:run
```

## 📁 Estructura del Proyecto

```
ClassManager/
├── ClassManagerFrontEndAngular/     # Frontend Angular
│   ├── src/
│   │   ├── app/
│   │   │   ├── auth/               # Autenticación
│   │   │   ├── dashboard/          # Dashboard principal
│   │   │   │   ├── attendance/     # Control de asistencia
│   │   │   │   ├── classes/        # Gestión de clases
│   │   │   │   ├── grades/         # Sistema de calificaciones
│   │   │   │   ├── students/       # Gestión de estudiantes
│   │   │   │   └── profile/        # Perfil de usuario
│   │   │   ├── services/           # Servicios
│   │   │   └── shared/             # Componentes compartidos
│   │   └── environments/           # Configuraciones de entorno
│   └── package.json
├── ClassManagerBackEndSpringBoot/   # Backend Spring Boot
│   ├── src/main/java/
│   │   └── com/tonilr/ClassManager/
│   │       ├── Controller/         # Controladores REST
│   │       ├── Service/            # Lógica de negocio
│   │       ├── Repository/         # Acceso a datos
│   │       ├── Model/              # Entidades JPA
│   │       └── Security/           # Configuración de seguridad
│   └── pom.xml
└── README.md
```

## 🔧 Configuración de Base de Datos

```sql
-- Crear base de datos
CREATE DATABASE classmanager;

-- Las tablas se crearán automáticamente con JPA
```

## 🌍 Variables de Entorno

### Frontend (environment.ts)
```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080'
};
```

### Backend (application.properties)
```properties
# Base de datos
spring.datasource.url=jdbc:postgresql://localhost:5432/classmanager
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password

# JWT
app.jwtSecret=tu_jwt_secret
app.jwtExpirationInMs=86400000

# Servidor
server.port=8080
```

## 🧪 Testing

### Frontend
```bash
cd ClassManagerFrontEndAngular
npm test
```

### Backend
```bash
cd ClassManagerBackEndSpringBoot
./mvnw test
```

## 📦 Despliegue

### Frontend (Netlify)
1. Conectar repositorio GitHub a Netlify
2. Configurar build settings:
   - Build command: `npm run build`
   - Publish directory: `dist/class-manager`
3. Configurar variables de entorno en Netlify

### Backend (Railway)
1. Conectar repositorio GitHub a Railway
2. Configurar variables de entorno
3. Deploy automático en cada push

### Docker
```bash
# Construir imagen
docker build -t classmanager .

# Ejecutar contenedor
docker run -p 8080:8080 classmanager
```

## 🤝 Contribuir

1. Fork el proyecto
2. Crear una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir un Pull Request

## 📝 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## 👨‍💻 Autor

**Toni Lupiañez Roman**
- GitHub: [@ToniLRo](https://github.com/ToniLRo)
- LinkedIn: [Toni Lupiañez Roman](https://www.linkedin.com/in/toni-lupia%C3%B1ez-roman-4a8024202/)

## 🙏 Agradecimientos

- [Angular](https://angular.io/) - Framework frontend
- [Spring Boot](https://spring.io/projects/spring-boot) - Framework backend
- [Angular Material](https://material.angular.io/) - Componentes UI
- [Netlify](https://www.netlify.com/) - Hosting frontend
- [Railway](https://railway.app/) - Hosting backend

---

⭐ **¡Si te gusta este proyecto, dale una estrella en GitHub!**
