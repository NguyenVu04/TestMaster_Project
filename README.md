# TestMaster 📝

## Project Title
**Online Examination Application**

## Vision 🌟
TestMaster is designed to support teachers in conducting simple online assessments for their classes. Key features include:

- 🧑‍🎓 Students can access tests using a passcode.
- 📊 Test results are stored in the system per student and per test.
- 🧑‍🏫 Teachers can view results for the entire class, create tests, and more.

## Recommended Technical Stack 🛠️
To build TestMaster, we recommend using the following technologies:

- **Java**: For the core backend logic.
- **PostgreSQL**: For database management and storage of test results.
- **Spring Boot**: For creating the backend RESTful API.
- **Next.js**: For the frontend application.
- **Auth.js**: For handling authentication and authorization.

## Getting Started 🚀
To get started with TestMaster, follow the steps below:

### Prerequisites 📋
Ensure you have the following installed on your machine:
- Java Development Kit (JDK)
- PostgreSQL
- Node.js
- npm (Node Package Manager)

### Installation 🛠️
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/testmaster.git
   cd testmaster
   ```

2. Set up the backend:
   - Navigate to the backend directory:
     ```bash
     cd backend
     ```
   - Install dependencies and build the project:
     ```bash
     ./mvnw install
     ```
   - Configure the database connection in `application.properties` file:
     ```properties
     spring.datasource.url=jdbc:postgresql://localhost:5432/testmaster
     spring.datasource.username=yourusername
     spring.datasource.password=yourpassword
     ```

3. Set up the frontend:
   - Navigate to the frontend directory:
     ```bash
     cd ../frontend
     ```
   - Install dependencies:
     ```bash
     npm install
     ```
   - Configure authentication settings in `auth.config.js` file.

### Running the Application ▶️
1. Start the backend server:
   ```bash
   cd backend
   ./mvnw spring-boot:run
   ```

2. Start the frontend server:
   ```bash
   cd ../frontend
   npm run dev
   ```

3. Open your browser and navigate to `http://localhost:3000` to access TestMaster.
