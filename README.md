---

# **Role-Based Access Control (RBAC) System**

## **Overview**

This project implements a **Role-Based Access Control (RBAC)** system using Spring Boot. It incorporates **Authentication**, **Authorization**, and role-based access to ensure secure and efficient user management.

### **Key Features**
- **Authentication:** 
  - Users can register, log in, and log out securely using JWT.
  - Passwords are securely hashed using BCrypt.
  
- **Authorization:**
  - Access to resources is determined based on user roles.
  - Different levels of access for roles like `Admin`, `Moderator`, and `User`.

- **RBAC Implementation:**
  - Admin can manage roles and edit user profiles.
  - Moderators can access user lists.
  - Users can view their own profile information.

---

## **Endpoints**

### **Authentication**
| Method | Endpoint            | Description                        |
|--------|---------------------|------------------------------------|
| POST   | `/auth/register`    | Register a new user and assign a role. |
| POST   | `/auth/login`       | Login and retrieve a JWT token.    |
| POST   | `/auth/logout`      | Logout and blacklist the token.    |

### **Admin**
| Method | Endpoint                | Description                                      |
|--------|-------------------------|--------------------------------------------------|
| GET    | `/admin/dashboard`      | View the Admin Dashboard (requires `ADMIN` role).|
| GET    | `/admin/all`            | View all users (requires `MODERATOR` role).      |
| POST   | `/admin/assign-role`    | Assign a role to a user.                        |
| PUT    | `/admin/edit-profile/{id}` | Edit user profile details.                     |

### **User**
| Method | Endpoint         | Description                                      |
|--------|------------------|--------------------------------------------------|
| GET    | `/user/profile`  | View the authenticated user's profile.          |

---

## **Authentication Flow**

1. **Registration (`/auth/register`):**
   - Users can register with a username, password, and role.
   - Passwords are securely hashed before storage.

2. **Login (`/auth/login`):**
   - On successful login, a JWT token is issued.
   - This token must be sent in the `Authorization` header for subsequent requests.

3. **Logout (`/auth/logout`):**
   - Blacklists the token to prevent further use.

---

## **Authorization Flow**

- **JWT Validation:**
  - Each request with a secured endpoint validates the token using the `JwtAuthenticationFilter`.

- **Role-Based Access:**
  - Admins can access all endpoints.
  - Moderators can view user lists but cannot edit profiles.
  - Users can only view their own profiles.

---

## **Models**

### **User**
- Fields:
  - `id`: Unique identifier.
  - `username`: Unique username.
  - `password`: Encrypted password.
  - `roles`: Set of roles assigned to the user.

### **Role**
- Fields:
  - `id`: Unique identifier.
  - `name`: Role name (`ADMIN`, `MODERATOR`, `USER`).

---

## **Technologies Used**
- **Backend:** Spring Boot
- **Security:** Spring Security, JWT
- **Database:** H2 (in-memory database for testing)
- **Password Hashing:** BCrypt

---

## **Setup and Installation**

1. **Clone the Repository**
   ```bash
   git clone https://github.com/your-repo-url
   cd your-repo-url
   ```

2. **Run the Application**
   - Ensure Java and Maven are installed.
   ```bash
   mvn spring-boot:run
   ```

3. **Access the Application**
   - The application runs on `http://localhost:8000`.

---

## **Testing the API**

Use a tool like **Postman** to test the endpoints.

### **Sample Workflow**
1. **Register a User**
   - Endpoint: `POST /auth/register?role={SpecifyRole}`
   - Body:
     ```json
     {
       "username": "testuser",
       "password": "password",
     }
     ```

2. **Login**
   - Endpoint: `POST /auth/login`
   - Body:
     ```json
     {
       "username": "testuser",
       "password": "password"
     }
     ```
   - Response:
     ```json
     {
       "message": "Login successful",
       "body": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJOaWsiLCJpYXQiOjE3MzI3MzUxMzksImV4cCI6MTczMjczODczOX0.oOjyeNX46yJyCmmCctB1XH2siny_EiL5Yx4KJLZPzzU"
     }
     ```

3. **Access Protected Routes**
   - Use the token in the `Authorization` header:
     ```
     Authorization: Bearer your-jwt-token
     ```

---

## **Screenshots**

Here’s the corrected code format for your **Screenshots** section in the `README.md` file:

---

## **Screenshots**

### **Postman Responses**
- **Get All Users (Only admin can access Need to pass jwt token of admin)**  
  ![image](https://github.com/user-attachments/assets/9c054a8d-8805-4503-9324-7d8a05720cb6)

- **Get User Profile (Every Role can access it, but one user cannot see another user's profile)**  
  ![image](https://github.com/user-attachments/assets/c994ae0d-2f19-4431-9b9d-0a39df9d8e94)

---

