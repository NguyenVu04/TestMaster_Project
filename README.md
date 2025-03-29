# TestMaster 📝

## 🎯 Project Title
**Online Examination Application**

---

## 🌟 Vision
TestMaster is designed to support teachers in conducting simple online assessments for their classes. Key features include:

- 🧑‍🎓 **Students** can access tests using a passcode.
- 📊 **Test results** are stored in the system per student and per test.
- 🧑‍🏫 **Teachers** can view results for the entire class, create tests, and manage assessments seamlessly.

---

## 🛠️ Recommended Technical Stack
TestMaster is built using the following technologies:

| Component    | Technology |
|-------------|------------|
| Backend     | **Java** (Spring Boot) |
| Database    | **PostgreSQL** |
| Frontend    | **Next.js** |
| Authentication | **Auth.js** |

---

## 🚀 Getting Started
### 📋 Prerequisites
Ensure you have the following installed on your machine:
- ✅ Java Development Kit (**JDK**)
- ✅ PostgreSQL
- ✅ Node.js
- ✅ npm (Node Package Manager)

### 🛠️ Installation Guide
#### 1️⃣ Clone the repository
```bash
git clone https://github.com/yourusername/testmaster.git
cd testmaster
```

#### 2️⃣ Backend Setup
```bash
cd backend
./mvnw install
```
**Configure the database connection** in `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/testmaster
spring.datasource.username=yourusername
spring.datasource.password=yourpassword
```

#### 3️⃣ Frontend Setup
```bash
cd ../frontend
npm install
```
Configure authentication settings in `auth.config.js`.

---

## ▶️ Running the Application
**Start the backend server:**
```bash
cd backend
./mvnw spring-boot:run
```

**Start the frontend server:**
```bash
cd ../frontend
npm run dev
```

🔗 Open your browser and navigate to **`http://localhost:3000`** to access TestMaster.

---

## ☁️ Deployment to Google Cloud App Engine (Backend)
### 🌐 Prerequisites
- Google Cloud SDK installed and initialized
- Google Cloud project created
- App Engine API enabled in your project

### 🚀 Deployment Steps
#### 1️⃣ Build the JAR file
```bash
cd backend
./mvnw clean package
```
This generates a JAR file in the `target` directory (e.g., `your-application-0.0.1-SNAPSHOT.jar`).

To test your JAR file locally, run:
```bash
java -jar target/your-app-0.0.1-SNAPSHOT.jar
```

#### 2️⃣ Deploy to App Engine
```bash
gcloud app deploy target/your-application-0.0.1-SNAPSHOT.jar
```

#### 3️⃣ Review your deployed application
```bash
gcloud app browse
```

🎉 Your application is now live on Google Cloud App Engine!

---

### 💡 Contributing
We welcome contributions! Feel free to open issues and submit pull requests.

### 📜 License
This project is licensed under the **MIT License**.

---

🚀 Happy Coding! 🎯

