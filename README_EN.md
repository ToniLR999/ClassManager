# 🎓 ClassManager - Academic Management System

A complete academic management application developed with **Angular 17** and **Spring Boot 3**, that allows teachers and administrators to efficiently manage classes, students, attendance, and grades.

## 🌐 Live Demo

**🔗 [View application on Netlify](https://classmanager-tonilr.netlify.app/)**

## ✨ Main Features

### 👨‍🏫 Teacher Management
- 👤 Complete teacher profiles
- 🔐 Secure JWT authentication
- 📊 Personalized dashboard
- ⚙️ Preference configuration

### 🎯 Class Management
- 📚 Create and manage classes
- 📅 Schedule timetables and dates
- 👥 Assign students to classes
- 📝 Class descriptions and details

### 👨‍🎓 Student Management
- 👤 Complete student registration
- 📋 Personal and academic information
- 🔍 Advanced search and filters
- 📊 Academic history

### ✅ Attendance Control
- 📅 Daily attendance recording
- ⏰ Entry/exit marking
- 📊 Attendance statistics
- 📈 Attendance history per student

### 📊 Grading System
- 🎯 Grade assignment
- 📈 Progress tracking
- 📋 Performance reports
- 📊 Averages and statistics

### 🔐 Security and Authentication
- 🛡️ JWT authentication
- 🔒 Secure registration and login
- 🚪 Route protection
- 👤 Role and permission management

## 🛠️ Technologies Used

### Frontend
- **Angular 17** - Main framework
- **TypeScript** - Programming language
- **Angular Material** - UI components
- **RxJS** - Reactive programming
- **Angular CDK** - Development components

### Backend
- **Spring Boot 3.3.2** - Java framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Data persistence
- **PostgreSQL** - Database
- **JWT** - Authentication tokens

### DevOps
- **Netlify** - Frontend deployment
- **Railway** - Backend deployment
- **GitHub** - Version control

## 🚀 Installation and Setup

### Prerequisites
- Node.js 18 or higher
- Java 17 or higher
- PostgreSQL 13 or higher
- Maven 3.6 or higher

### Frontend (Angular)

```bash
# Clone the repository
git clone https://github.com/your-username/class-manager.git
cd class-manager/ClassManagerFrontEndAngular

# Install dependencies
npm install

# Configure environment variables
# Create file src/environments/environment.ts
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080'
};

# Run in development mode
npm start

# Build for production
npm run build
```

### Backend (Spring Boot)

```bash
# Navigate to backend directory
cd ../ClassManagerBackEndSpringBoot

# Configure PostgreSQL database
# Create file src/main/resources/application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/classmanager
spring.datasource.username=your_username
spring.datasource.password=your_password

# Run the application
./mvnw spring-boot:run
```

## 📁 Project Structure

```
ClassManager/
├── ClassManagerFrontEndAngular/     # Angular Frontend
│   ├── src/
│   │   ├── app/
│   │   │   ├── auth/               # Authentication
│   │   │   ├── dashboard/          # Main dashboard
│   │   │   │   ├── attendance/     # Attendance control
│   │   │   │   ├── classes/        # Class management
│   │   │   │   ├── grades/         # Grading system
│   │   │   │   ├── students/       # Student management
│   │   │   │   └── profile/        # User profile
│   │   │   ├── services/           # Services
│   │   │   └── shared/             # Shared components
│   │   └── environments/           # Environment configurations
│   └── package.json
├── ClassManagerBackEndSpringBoot/   # Spring Boot Backend
│   ├── src/main/java/
│   │   └── com/tonilr/ClassManager/
│   │       ├── Controller/         # REST controllers
│   │       ├── Service/            # Business logic
│   │       ├── Repository/         # Data access
│   │       ├── Model/              # JPA entities
│   │       └── Security/           # Security configuration
│   └── pom.xml
└── README.md
```

## 🔧 Database Configuration

```sql
-- Create database
CREATE DATABASE classmanager;

-- Tables will be created automatically with JPA
```

## 🌍 Environment Variables

### Frontend (environment.ts)
```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080'
};
```

### Backend (application.properties)
```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/classmanager
spring.datasource.username=your_username
spring.datasource.password=your_password

# JWT
app.jwtSecret=your_jwt_secret
app.jwtExpirationInMs=86400000

# Server
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

## 📦 Deployment

### Frontend (Netlify)
1. Connect GitHub repository to Netlify
2. Configure build settings:
   - Build command: `npm run build`
   - Publish directory: `dist/class-manager`
3. Configure environment variables in Netlify

### Backend (Railway)
1. Connect GitHub repository to Railway
2. Configure environment variables
3. Automatic deployment on each push

### Docker
```bash
# Build image
docker build -t classmanager .

# Run container
docker run -p 8080:8080 classmanager
```

## 🤝 Contributing

1. Fork the project
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is under the MIT License. See the `LICENSE` file for more details.

## 👨‍💻 Author

**Toni Lupiañez Roman**
- GitHub: [@ToniLRo](https://github.com/ToniLRo)
- LinkedIn: [Toni Lupiañez Roman](https://www.linkedin.com/in/toni-lupia%C3%B1ez-roman-4a8024202/)

## 🙏 Acknowledgments

- [Angular](https://angular.io/) - Frontend framework
- [Spring Boot](https://spring.io/projects/spring-boot) - Backend framework
- [Angular Material](https://material.angular.io/) - UI components
- [Netlify](https://www.netlify.com/) - Frontend hosting
- [Railway](https://railway.app/) - Backend hosting

---

⭐ **If you like this project, give it a star on GitHub!**
