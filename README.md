# DocFinder: Disease Identification and Doctor Recommendation System

<div align="center">

![Java](https://img.shields.io/badge/Java-11+-orange?style=flat-square&logo=java)
![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue?style=flat-square&logo=mysql)
![Maven](https://img.shields.io/badge/Maven-3.6+-red?style=flat-square&logo=apache-maven)
![License](https://img.shields.io/badge/License-MIT-green?style=flat-square)
![Status](https://img.shields.io/badge/Status-Active-brightgreen?style=flat-square)

**A comprehensive Java application demonstrating Object-Oriented Programming (OOP) and Database Management Systems (DBMS) concepts**

[Features](#-features) • [Installation](#-installation) • [Usage](#-usage) • [Architecture](#-architecture) • [Documentation](#-documentation)

</div>

---

## 📋 Table of Contents

- [Project Overview](#project-overview)
- [Features](#-features)
- [Technology Stack](#-technology-stack)
- [Project Structure](#-project-structure)
- [Installation](#-installation)
- [Database Setup](#-database-setup)
- [Usage](#-usage)
- [Architecture](#-architecture)
- [OOP Concepts Demonstrated](#-oop-concepts-demonstrated)
- [DBMS Concepts Demonstrated](#-dbms-concepts-demonstrated)
- [Testing](#-testing)
- [Documentation](#-documentation)
- [Team Contribution](#-team-contribution)
- [Future Enhancements](#-future-enhancements)
- [License](#-license)

---

## Project Overview

**DocFinder** is an educational mini-project that demonstrates how to build a complete Java application using Object-Oriented Programming and Database Management Systems principles.

### Problem Statement

Many patients experience symptoms but are uncertain about:
- Which type of doctor to consult
- What immediate first-aid steps they can take
- The availability and specialization of nearby doctors
- How to safely share health information

### Solution

DocFinder provides a **simple, secure, and user-friendly application** that:
1. Allows users to input symptoms safely
2. Identifies likely disease categories
3. Provides initial first-aid guidance
4. Recommends appropriate specialized doctors
5. Displays doctor information (specialization, clinic hours, contact)
6. Protects user privacy throughout

### Project Scope

- **Course**: CMIS 2113 (Object-Oriented Programming)
- **University**: Wayamba University of Sri Lanka
- **Team**: Team Axion (4 members)
- **Status**: ✅ Active Development (Interim Phase)
- **Educational Purpose**: Demonstrate OOP and DBMS concepts for second-year university students

---

## ✨ Features

### Core Functionality

#### 1. User Account Management
- ✅ Secure user registration with validation
- ✅ Login authentication with password hashing
- ✅ Profile management and updates
- ✅ Personal information protection

#### 2. Symptom Input & Disease Identification
- ✅ Multi-symptom input interface
- ✅ Symptom validation from database
- ✅ Intelligent disease matching algorithm
- ✅ Display of matched disease with description
- ✅ Severity level indication (Mild/Moderate/Severe)

#### 3. First-Aid Guidance
- ✅ Step-by-step first-aid instructions
- ✅ Duration for each step
- ✅ Safety precautions and warnings
- ✅ Emergency warning signs

#### 4. Doctor Recommendation
- ✅ Automatic doctor recommendation based on disease
- ✅ Display of doctor specialization
- ✅ Clinic address and operating hours
- ✅ Direct contact information

#### 5. Doctor Search
- ✅ Search doctors by specialization
- ✅ Filter by availability status
- ✅ Sort by preference

#### 6. Search History
- ✅ Maintain user search history
- ✅ Track disease identification and doctor recommendations
- ✅ Historical data retrieval

#### 7. Security & Privacy
- ✅ Password hashing (SHA-256)
- ✅ SQL injection prevention (prepared statements)
- ✅ No medicine prescription (advisory only)
- ✅ User data encryption in transit

---

## 🛠 Technology Stack

| Component | Technology | Version |
|-----------|-----------|---------|
| **Language** | Java | 11+ |
| **DBMS** | MySQL | 8.0+ |
| **Build Tool** | Maven | 3.6+ |
| **Testing** | JUnit 4 | 4.13+ |
| **Version Control** | Git | Latest |
| **IDE** | IntelliJ IDEA / Eclipse | Latest |

### Dependencies

```xml
<!-- MySQL JDBC Driver -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
</dependency>

<!-- JUnit Testing -->
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.13.2</version>
    <scope>test</scope>
</dependency>
```

---

## 📂 Project Structure

```
docfinder-project/
│
├── src/main/java/com/docfinder/
│   │
│   ├── model/                          # Entity classes
│   │   ├── Person.java                 # Abstract base class
│   │   ├── User.java                   # User class (extends Person)
│   │   ├── Doctor.java                 # Abstract doctor class
│   │   ├── GeneralPractitioner.java    # Doctor subclass
│   │   ├── Cardiologist.java           # Doctor subclass
│   │   ├── Dermatologist.java          # Doctor subclass
│   │   ├── Disease.java                # Abstract disease class
│   │   ├── CommonDisease.java          # Disease subclass
│   │   ├── RareDiseases.java           # Disease subclass
│   │   └── FirstAidStep.java           # First-aid steps class
│   │
│   ├── dao/                            # Data Access Objects
│   │   ├── DatabaseConnection.java     # JDBC connection manager
│   │   ├── UserDAO.java                # User CRUD operations
│   │   ├── DoctorDAO.java              # Doctor CRUD operations
│   │   ├── DiseaseDAO.java             # Disease CRUD operations
│   │   └── SearchHistoryDAO.java       # Search history tracking
│   │
│   ├── service/                        # Business Logic Layer
│   │   ├── SymptomChecker.java         # Disease matching algorithm
│   │   ├── RecommendationSystem.java   # Doctor recommendations
│   │   ├── UserService.java            # User management service
│   │   └── SearchHistoryService.java   # Search history service
│   │
│   ├── exception/                      # Custom Exceptions
│   │   ├── InvalidSymptomException.java
│   │   ├── DiseaseNotFoundException.java
│   │   ├── DoctorNotAvailableException.java
│   │   ├── InvalidLoginException.java
│   │   └── DatabaseConnectionException.java
│   │
│   ├── ui/                             # User Interface
│   │   └── ConsoleUI.java              # Console-based UI
│   │
│   ├── interface/                      # Contracts
│   │   ├── ISearchable.java            # Search functionality
│   │   └── IRecommendable.java         # Recommendation functionality
│   │
│   ├── util/                           # Utility Classes
│   │   ├── InputValidator.java         # Input validation
│   │   └── PasswordHasher.java         # Password hashing
│   │
│   └── DiseaseIdentificationApp.java   # Main application class
│
├── src/test/java/com/docfinder/test/   # Unit Tests
│   ├── UserDAOTest.java
│   ├── DoctorDAOTest.java
│   ├── SymptomCheckerTest.java
│   ├── RecommendationSystemTest.java
│   └── UserServiceTest.java
│
├── src/main/resources/
│   ├── db.properties                   # Database configuration
│   └── log4j.properties                # Logging configuration
│
├── docs/                               # Documentation
│   ├── DATABASE_SETUP.md               # Database setup guide
│   ├── API.md                          # API documentation
│   ├── ARCHITECTURE.md                 # Architecture guide
│   ├── CONTRIBUTING.md                 # Contribution guidelines
│   └── diagrams/                       # UML diagrams
│       ├── ClassDiagram.png
│       ├── UseCaseDiagram.png
│       └── ERDiagram.png
│
├── pom.xml                             # Maven configuration
├── README.md                           # This file
├── .gitignore                          # Git ignore file
└── .git/                               # Version control

```

---

## 🚀 Installation

### Prerequisites

Before you start, ensure you have the following installed:

```bash
# Check Java version (11 or higher)
java -version
# Output: openjdk 11.0.x or higher

# Check MySQL version (8.0 or higher)
mysql --version
# Output: mysql Ver 8.0.x

# Check Maven version (3.6 or higher)
mvn --version
# Output: Apache Maven 3.6.x
```

**Download/Install:**
- [Java JDK 11+](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [MySQL Server 8.0+](https://www.mysql.com/downloads/mysql/)
- [Apache Maven 3.6+](https://maven.apache.org/download.cgi)

### Step 1: Clone the Repository

```bash
# Clone from GitHub
git clone https://github.com/teamaxion/docfinder.git

# Navigate to project directory
cd docfinder
```

### Step 2: Install Dependencies

```bash
# Using Maven (automatically downloads dependencies from pom.xml)
mvn clean install
```

### Step 3: Database Setup

```bash
# Option A: Using MySQL command line
mysql -u root -p

# Then run the database setup script (see Database Setup section below)

# Option B: Using MySQL Workbench
# 1. Open MySQL Workbench
# 2. Create new SQL script
# 3. Paste contents from docs/DATABASE_SETUP.md
# 4. Execute script
```

### Step 4: Configure Database Connection

Edit `src/main/resources/db.properties`:

```properties
# Database Configuration
db.url=jdbc:mysql://localhost:3306/docfinder_db
db.username=root
db.password=YOUR_MYSQL_PASSWORD
db.driver=com.mysql.cj.jdbc.Driver
```

### Step 5: Build the Project

```bash
# Compile source code
mvn clean compile

# Run all tests
mvn test

# Package as executable JAR
mvn package
```

### Step 6: Run the Application

```bash
# Option A: Run from IDE
# 1. Open project in IntelliJ IDEA or Eclipse
# 2. Run DiseaseIdentificationApp.java

# Option B: Run from command line
mvn exec:java -Dexec.mainClass="com.docfinder.DiseaseIdentificationApp"

# Option C: Run packaged JAR
java -jar target/docfinder-1.0.0.jar
```

---

## 🗄️ Database Setup

### Quick Setup (One Command)

```bash
# Create database and tables
mysql -u root -p docfinder_db < docs/DATABASE_SETUP.sql
```

### Manual Setup

```sql
-- Create Database
CREATE DATABASE docfinder_db;
USE docfinder_db;

-- Create Tables (see docs/DATABASE_SETUP.md for full SQL)
CREATE TABLE User (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    -- ... (see documentation for complete schema)
);

-- Create other tables (Doctor, Disease, Symptom, etc.)
-- ... (see docs/DATABASE_SETUP.md)

-- Insert Sample Data
-- ... (see docs/DATABASE_SETUP.md)
```

### Database Structure

- **9 Normalized Tables** (3NF normalization)
- **Primary Keys**: Unique identifiers for each record
- **Foreign Keys**: Relationships between tables
- **Junction Tables**: Many-to-many relationships (DiseaseSymptom, DiseaseDoctor)

See `docs/DATABASE_SETUP.md` for detailed schema and sample data.

---

## 📖 Usage

### Starting the Application

```bash
mvn exec:java -Dexec.mainClass="com.docfinder.DiseaseIdentificationApp"
```

### User Workflow

```
1. APPLICATION STARTS
   └─> Main Menu displayed

2. REGISTER OR LOGIN
   └─> Register: Enter username, email, password, age, gender, contact
   └─> Login: Enter username and password

3. USER MENU
   ├─> Option 1: Input Symptoms
   │   ├─> Select symptoms from list
   │   ├─> System analyzes and identifies disease
   │   ├─> Displays disease description and severity
   │   ├─> Shows first-aid steps
   │   └─> Recommends appropriate doctors
   │
   ├─> Option 2: Search Doctors by Specialization
   │   ├─> Enter specialization (e.g., "Cardiologist")
   │   ├─> System searches database
   │   └─> Displays matching doctors with contact info
   │
   ├─> Option 3: View Profile
   │   └─> Displays user registration information
   │
   ├─> Option 4: View Search History
   │   ├─> Shows past searches
   │   ├─> Diseases identified
   │   └─> Doctors recommended
   │
   └─> Option 5: Logout
       └─> Return to main menu

4. EXIT
   └─> Application terminates
```

### Example Interaction

```
=== DocFinder - Disease Identification System ===

1. Register New User
2. Login
3. Exit

Enter choice: 2

Enter username: rajkumar
Enter password: ••••••••

✓ Login successful! Welcome, Raj Kumar

=== User Menu ===
1. Input Symptoms
2. Search Doctors
3. View Profile
4. View Search History
5. Logout

Enter choice: 1

Available Symptoms:
1. Fever
2. Cough
3. Headache
4. Sore Throat
5. Shortness of Breath

Select symptoms (comma-separated): 1,2,4

Analyzing symptoms...

========== DISEASE IDENTIFIED ==========
Disease: Influenza
Description: Contagious respiratory infection
Severity: Moderate

========== FIRST AID STEPS ==========
1. Rest for 24-48 hours
   Duration: Until fever subsides
   Precautions: Avoid strenuous activities

2. Stay hydrated
   Duration: Throughout illness
   Precautions: Drink 2-3 liters daily

========== RECOMMENDED DOCTORS ==========
Dr. Anil Silva
Specialization: Cardiologist
Clinic: Heart Center, Colombo
Hours: 9 AM - 5 PM
Contact: 0701234567
```

---

## 🏗️ Architecture

### Layered Architecture

```
┌──────────────────────────────────────────────────────┐
│           PRESENTATION LAYER                          │
│  (ConsoleUI - User interaction, menus, display)       │
└─────────────────────────┬──────────────────────────┘
                          │
┌─────────────────────────▼──────────────────────────┐
│           BUSINESS LOGIC LAYER                      │
│  (SymptomChecker, RecommendationSystem, Services)   │
└─────────────────────────┬──────────────────────────┘
                          │
┌─────────────────────────▼──────────────────────────┐
│            DATA ACCESS LAYER (DAO)                  │
│  (UserDAO, DoctorDAO, DiseaseDAO, JDBC)            │
└─────────────────────────┬──────────────────────────┘
                          │
┌─────────────────────────▼──────────────────────────┐
│              DATABASE LAYER                         │
│  (MySQL - 9 normalized tables)                      │
└──────────────────────────────────────────────────┘
```

### Data Flow

```
User Input (Console)
        ↓
ConsoleUI (Display & Input)
        ↓
DiseaseIdentificationApp (Controller/Router)
        ↓
Service Layer (Business Logic)
    ├─> SymptomChecker (Disease matching)
    ├─> RecommendationSystem (Doctor search)
    └─> UserService (Authentication)
        ↓
DAO Layer (Database Access)
    ├─> UserDAO (User operations)
    ├─> DoctorDAO (Doctor operations)
    ├─> DiseaseDAO (Disease operations)
    └─> DatabaseConnection (JDBC)
        ↓
MySQL Database (Data Storage)
```

### Class Responsibilities

| Class | Responsibility |
|-------|-----------------|
| **Person (abstract)** | Base class with common attributes |
| **User** | User profile, authentication, symptom input |
| **Doctor (abstract)** | Base for specialized doctors |
| **Disease (abstract)** | Disease properties, symptom matching |
| **SymptomChecker** | Analyze symptoms, identify disease |
| **RecommendationSystem** | Find and recommend appropriate doctors |
| **UserDAO** | User CRUD operations |
| **DoctorDAO** | Doctor CRUD operations |
| **DiseaseDAO** | Disease and symptom operations |
| **ConsoleUI** | Display screens, get user input |
| **DiseaseIdentificationApp** | Main controller, coordinate operations |

---

## 📌 OOP Concepts Demonstrated

### 1. **Encapsulation** 🔒
Hiding internal details and providing controlled access:

```java
public class User extends Person {
    private String email;           // Private - hidden
    private String passwordHash;    // Private - protected
    
    // Public getter - controlled access
    public String getEmail() {
        return email;
    }
    
    // Public setter - with validation
    public void setEmail(String email) {
        if (email.contains("@")) {
            this.email = email;
        }
    }
}
```

**Benefit**: Protects sensitive data, validates input, prevents misuse

### 2. **Inheritance** 🧬
Reusing code through hierarchies:

```java
// Parent class
public abstract class Doctor {
    private String name;
    protected String specialization;
    
    public abstract String getSpecializationDetails();
}

// Child class - Cardiologist
public class Cardiologist extends Doctor {
    @Override
    public String getSpecializationDetails() {
        return "Cardiologist - Heart & Cardiovascular Diseases";
    }
    
    public void performHeartScreening() {
        // Cardiologist-specific method
    }
}
```

**Benefit**: Code reuse, hierarchy, specialization

### 3. **Polymorphism** 🦄
Same interface, different behaviors:

```java
public abstract class Disease {
    public abstract List<FirstAidStep> getFirstAidSteps();
}

public class CommonDisease extends Disease {
    @Override
    public List<FirstAidStep> getFirstAidSteps() {
        // Simple first-aid for common diseases
    }
}

public class RareDiseases extends Disease {
    @Override
    public List<FirstAidStep> getFirstAidSteps() {
        // Detailed first-aid for rare diseases
    }
}

// Usage - same method, different behavior
List<Disease> diseases = new ArrayList<>();
diseases.add(new CommonDisease());
diseases.add(new RareDiseases());

for (Disease disease : diseases) {
    List<FirstAidStep> steps = disease.getFirstAidSteps();
    // Different implementation based on disease type
}
```

**Benefit**: Flexible code, easy extension

### 4. **Abstraction** 📦
Showing only what's necessary, hiding complexity:

```java
// Abstract class - incomplete, defines contract
public abstract class Disease {
    protected String diseaseName;
    
    // Method must be implemented by subclasses
    public abstract double matchSymptoms(List<String> symptoms);
}

// Interface - defines what can be done
public interface ISearchable {
    Object search(String criteria);
}

// Usage - doesn't need to know implementation details
Disease disease = new CommonDisease();
double matchScore = disease.matchSymptoms(userSymptoms);
// User doesn't care HOW it matches, only WHAT is returned
```

**Benefit**: Simple interface, hidden complexity, flexibility

### 5. **Collections** 📚
Managing multiple objects:

```java
// ArrayList - dynamic array
List<Disease> diseases = new ArrayList<>();
diseases.add(new CommonDisease());
diseases.add(new RareDiseases());

// HashMap - key-value pairs
Map<String, Doctor> doctorsBySpecialization = new HashMap<>();
doctorsBySpecialization.put("Cardiologist", new Cardiologist());

// Iteration
for (Disease disease : diseases) {
    System.out.println(disease.getDiseaseName());
}
```

**Benefit**: Easy data management, iteration, searching

### 6. **Exception Handling** ⚠️
Graceful error management:

```java
public class SymptomChecker {
    public Disease analyzeSymptoms(List<String> symptoms) 
            throws InvalidSymptomException, DiseaseNotFoundException {
        
        if (symptoms == null || symptoms.isEmpty()) {
            throw new InvalidSymptomException("Symptoms cannot be empty");
        }
        
        Disease matched = findMatchingDisease(symptoms);
        
        if (matched == null) {
            throw new DiseaseNotFoundException("No disease matched");
        }
        
        return matched;
    }
}

// Usage with exception handling
try {
    Disease disease = checker.analyzeSymptoms(symptoms);
} catch (InvalidSymptomException e) {
    System.out.println("Invalid input: " + e.getMessage());
} catch (DiseaseNotFoundException e) {
    System.out.println("No disease found: " + e.getMessage());
}
```

**Benefit**: Robust error handling, graceful degradation

---

## 🗄️ DBMS Concepts Demonstrated

### 1. **Normalization** (3NF)
Eliminating data redundancy:

```
Before Normalization (Bad):
Disease Table:
disease_id | disease_name | symptom1 | symptom2 | symptom3
1          | Flu          | Fever    | Cough    | Headache
(Repeating columns waste space)

After Normalization (Good):
Disease Table: disease_id, disease_name
Symptom Table: symptom_id, symptom_name
DiseaseSymptom: disease_id, symptom_id (Junction table)
```

**Benefit**: No redundancy, easier updates, less storage

### 2. **Relationships** 🔗
Connecting data across tables:

```sql
-- One-to-Many: One Doctor has one Specialization
-- Many Doctors share same Specialization
SELECT d.doctor_name, s.specialization_name
FROM Doctor d
INNER JOIN Specialization s ON d.specialization_id = s.specialization_id;

-- Many-to-Many: One Disease has many Symptoms
-- One Symptom affects many Diseases
SELECT d.disease_name, s.symptom_name
FROM Disease d
INNER JOIN DiseaseSymptom ds ON d.disease_id = ds.disease_id
INNER JOIN Symptom s ON ds.symptom_id = s.symptom_id;
```

**Benefit**: Data organization, consistency, query flexibility

### 3. **SQL Queries (CRUD)**
Create, Read, Update, Delete operations:

```java
// CREATE - Add new user
String insertSQL = "INSERT INTO User (username, email, password_hash, age) " +
                   "VALUES (?, ?, ?, ?)";
pstmt = conn.prepareStatement(insertSQL);
pstmt.setString(1, user.getUsername());
pstmt.executeUpdate();

// READ - Get user by ID
String selectSQL = "SELECT * FROM User WHERE user_id = ?";
pstmt = conn.prepareStatement(selectSQL);
pstmt.setInt(1, userID);
ResultSet rs = pstmt.executeQuery();

// UPDATE - Modify user email
String updateSQL = "UPDATE User SET email = ? WHERE user_id = ?";
pstmt = conn.prepareStatement(updateSQL);
pstmt.setString(1, newEmail);
pstmt.executeUpdate();

// DELETE - Remove user
String deleteSQL = "DELETE FROM User WHERE user_id = ?";
pstmt = conn.prepareStatement(deleteSQL);
pstmt.setInt(1, userID);
pstmt.executeUpdate();
```

**Benefit**: Complete data manipulation, data persistence

### 4. **Data Integrity** 🔐
Ensuring data consistency:

```sql
-- Primary Keys - Unique identifiers
CREATE TABLE User (
    user_id INT PRIMARY KEY AUTO_INCREMENT,  -- Unique ID
    username VARCHAR(50) UNIQUE NOT NULL,    -- No duplicates
    ...
);

-- Foreign Keys - Relationship constraints
CREATE TABLE Doctor (
    doctor_id INT PRIMARY KEY AUTO_INCREMENT,
    specialization_id INT NOT NULL,
    FOREIGN KEY (specialization_id) 
        REFERENCES Specialization(specialization_id)  -- Must exist
);

-- Constraints - Validate data
CREATE TABLE User (
    age INT NOT NULL CHECK (age >= 1 AND age <= 120),  -- Valid range
    email VARCHAR(100) UNIQUE NOT NULL,                 -- No duplicates
    ...
);
```

**Benefit**: Prevents invalid data, maintains consistency

### 5. **Indexes** ⚡
Improving query performance:

```sql
-- Create indexes on frequently queried columns
CREATE INDEX idx_username ON User(username);
CREATE INDEX idx_disease_name ON Disease(disease_name);
CREATE INDEX idx_specialization ON Doctor(specialization_id);

-- Indexes speed up searches
SELECT * FROM User WHERE username = 'rajkumar';  -- Fast with index
```

**Benefit**: Faster queries, better performance

### 6. **Transactions** (Optional)
Ensuring data consistency across operations:

```java
try {
    conn.setAutoCommit(false);  // Start transaction
    
    userDAO.registerUser(user);
    searchHistoryDAO.saveSearch(user, disease, doctors);
    
    conn.commit();  // Save all changes
} catch (SQLException e) {
    conn.rollback();  // Undo all changes if error
}
```

**Benefit**: All-or-nothing operations, consistency

---

## 🧪 Testing

### Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=UserDAOTest

# Run with detailed output
mvn test -X
```

### Test Coverage

- **UserDAO Tests**: Registration, login, profile update
- **DoctorDAO Tests**: Search by specialization, retrieval
- **SymptomChecker Tests**: Disease matching, algorithm accuracy
- **RecommendationSystem Tests**: Doctor filtering, sorting
- **UserService Tests**: Authentication, search history

### Example Test

```java
@Test
public void testUserRegistration() {
    User user = new User("testuser", "test@email.com", "hashedPassword", 25, "Male", "0701234567");
    boolean registered = userDAO.registerUser(user);
    assertTrue("User should be registered", registered);
}
```

### Test Results

```
Tests run: 25, Failures: 0, Errors: 0

✓ testUserRegistration - PASSED
✓ testUserLogin - PASSED
✓ testSymptomMatching - PASSED
✓ testDoctorRecommendation - PASSED
...

BUILD SUCCESS
```

---

## 📚 Documentation

### Key Documents

| Document | Purpose | Location |
|----------|---------|----------|
| **README.md** | Project overview (this file) | Root directory |
| **DATABASE_SETUP.md** | Database setup instructions | `docs/` |
| **API.md** | Class and method documentation | `docs/` |
| **ARCHITECTURE.md** | System design and architecture | `docs/` |
| **CONTRIBUTING.md** | Contribution guidelines | `docs/` |

### UML Diagrams

Located in `docs/diagrams/`:
- `ClassDiagram.png` - OOP structure
- `UseCaseDiagram.png` - User interactions
- `ERDiagram.png` - Database structure

### Code Documentation

```java
/**
 * Analyzes user symptoms and identifies matching disease.
 * 
 * @param symptoms List of symptoms entered by user
 * @return Disease object representing the most likely disease
 * @throws InvalidSymptomException if symptoms list is empty
 * @throws DiseaseNotFoundException if no disease matches symptoms
 * 
 * Example:
 * List<String> symptoms = Arrays.asList("Fever", "Cough");
 * Disease disease = symptomChecker.analyzeSymptoms(symptoms);
 */
public Disease analyzeSymptoms(List<String> symptoms) 
    throws InvalidSymptomException, DiseaseNotFoundException {
    // Implementation
}
```

---

## 👥 Team Contribution

### Team Axion Members

| Name | ID | Role | Contribution |
|------|----|----|-------------|
| **A.M.R.S.B. Attanayake** | 232017 | Lead Developer | User classes, authentication, DAO layer |
| **K.A.A.N. Sanjana** | 232232 | Database Designer | Database schema, normalization, SQL queries |
| **S.M.S.P. Samarakoon** | 232126 | UI Developer | Console interface, user menus, display |
| **R.A.L. Wishwajith** | 232168 | QA & Documentation | Testing, documentation, UML diagrams |

### Contribution Statistics

```
Total Commits: 50+
Lines of Code: 5000+
Test Coverage: 75%+
Documentation: Complete

Commits per member:
- A.M.R.S.B. Attanayake: 15+ commits
- K.A.A.N. Sanjana: 12+ commits
- S.M.S.P. Samarakoon: 14+ commits
- R.A.L. Wishwajith: 10+ commits
```

### How to View Contributions

```bash
# View commit history
git log --oneline --author="Member Name"

# View commits per author
git shortlog -sn

# View detailed commit graph
git log --graph --all --oneline
```

---

## 🔄 Git Workflow

### Cloning Repository

```bash
git clone https://github.com/teamaxion/docfinder.git
cd docfinder
```

### Creating Feature Branch

```bash
# Create new branch for feature
git checkout -b feature/user-authentication

# Make changes, then commit
git add .
git commit -m "feature: Add user authentication with password hashing"

# Push to remote
git push origin feature/user-authentication
```

### Creating Pull Request

1. Push branch to GitHub
2. Go to GitHub repository
3. Click "New Pull Request"
4. Select feature branch
5. Add description
6. Request review
7. Merge after approval

### Merging Main

```bash
# Switch to main
git checkout main

# Pull latest
git pull origin main

# Merge feature
git merge feature/user-authentication

# Push merged code
git push origin main
```

---

## 🚀 Future Enhancements

### Phase 2 Features (Optional)

- [ ] **User Ratings**: Allow patients to rate doctors
- [ ] **Appointment Booking**: Schedule appointments (mock implementation)
- [ ] **Appointment History**: Track past appointments
- [ ] **Doctor Availability Calendar**: Real-time availability
- [ ] **SMS/Email Notifications**: Send appointment reminders
- [ ] **Mobile App**: Android/iOS version

### Phase 3 Features (Future)

- [ ] **Real Doctor Database**: Integration with actual hospitals
- [ ] **Medicine Information**: Display related medicines (advisory)
- [ ] **Telemedicine**: Video consultation with doctors
- [ ] **AI Integration**: Machine learning for disease prediction
- [ ] **Cloud Deployment**: Deploy on AWS/Azure
- [ ] **Multi-language Support**: Support multiple languages

### Code Improvements

- [ ] Improve disease matching algorithm
- [ ] Add more comprehensive logging
- [ ] Implement caching for performance
- [ ] Add REST API endpoints
- [ ] Implement role-based access control
- [ ] Add data encryption at rest

---

## 📋 Requirements Met

### Academic Requirements

- ✅ **OOP Concepts**: Encapsulation, Inheritance, Polymorphism, Abstraction
- ✅ **DBMS Concepts**: Normalization (3NF), Relationships, SQL CRUD
- ✅ **Code Quality**: Clean code, proper naming, comments
- ✅ **Testing**: Unit tests with JUnit
- ✅ **Documentation**: Comprehensive README, API docs, UML diagrams
- ✅ **Version Control**: Git repository with meaningful commits
- ✅ **Team Collaboration**: Equal contributions from all members

### Project Scope

- ✅ User registration and authentication
- ✅ Symptom input and disease identification
- ✅ First-aid guidance
- ✅ Doctor recommendation
- ✅ Search functionality
- ✅ Personal data protection
- ✅ Console-based interface

---

## ⚠️ Important Notes

### Security Disclaimer

This is an **educational project** for learning purposes. For production use:
- Use professional password hashing libraries
- Implement SSL/TLS encryption
- Add comprehensive input validation
- Use parameterized queries (already implemented)
- Implement rate limiting
- Add audit logging

### Privacy Notice

The application stores user information in a local MySQL database. Users' data is:
- Protected with password hashing
- Accessed only through prepared statements
- Never shared or transmitted externally
- Can be deleted on user request

---

## 📞 Support & Contact

### Getting Help

- **Course Lecturer**: Contact your instructor for academic guidance
- **GitHub Issues**: Report bugs and request features
- **Documentation**: Check `docs/` folder for detailed guides
- **Learning Guides**: See `Beginners_Learning_Guide.md` for concept explanations

### Troubleshooting

**Issue**: MySQL connection fails
```
Solution: 
1. Ensure MySQL is running
2. Check db.properties has correct credentials
3. Verify database docfinder_db exists
```

**Issue**: Tests fail with "table doesn't exist"
```
Solution:
1. Run DATABASE_SETUP.sql
2. Verify all tables created: SHOW TABLES;
3. Check sample data inserted
```

**Issue**: Compilation errors
```
Solution:
1. Run: mvn clean compile
2. Check all dependencies installed: mvn dependency:resolve
3. Ensure Java 11+ installed
```

---

## 📜 License

This project is licensed under the **MIT License** - see the LICENSE file for details.

```
MIT License

Copyright (c) 2025 Team Axion - Wayamba University

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions...
```

---

## 🎓 Educational Value

This project demonstrates:

### For Students
- How to structure a Java application
- OOP principles in practice
- Database design and normalization
- Java-MySQL integration via JDBC
- Git version control workflow
- Professional coding standards

### For Instructors
- Assessment of OOP understanding
- DBMS knowledge evaluation
- Code quality evaluation
- Team collaboration assessment
- Problem-solving skills

---

## 🙏 Acknowledgments

- **Wayamba University of Sri Lanka** - Academic institution
- **CMIS 2113 Lecturer** - Course instructor and guidance
- **Team Axion Members** - Collaborative development
- **Open Source Community** - Java, MySQL, Maven communities

---

## 📝 Changelog

### Version 1.0.0 (Current)
- Initial project setup
- Core OOP and DBMS implementation
- User authentication and profile management
- Disease identification system
- Doctor recommendation system
- Database design (3NF normalization)
- JUnit testing framework
- Comprehensive documentation

---

<div align="center">

### ⭐ If this project helped you, please consider giving it a star! ⭐

**Made with ❤️ by Team Axion**

[⬆ Back to Top](#docfinder-online-disease-identification-and-doctor-recommendation-system)

</div>
