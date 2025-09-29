# Row Adder

## 🛠️ Batch SQL Inserter (Java) – Finished Project

This is a Java project designed to perform **batch insertions into a SQL database** using JDBC. It allows users to insert multiple rows—either **manually** or using **randomized values**—via a command-line interface.

The project started as a learning exercise and now functions as a complete tool for interacting with a database through Java. It serves as a strong demonstration of backend Java development, database interaction, and batch processing techniques.

---

## ✅ Status

**Completed.**  
The core functionality is fully implemented. Additional features or improvements may be added in the future.

---

## 🎯 Features

- ✅ Batch insertion of rows into any SQL table
- ✅ Randomized data generation using [Java Faker](https://github.com/DiUS/java-faker)
- ✅ Manual row input via command line
- ✅ Automatic SQL generation from table metadata
- ✅ Basic validation and error handling
- ✅ Command-line interaction (JDBC-powered)
- ✅ Supports auto-increment skipping
- ✅ Configurable database access (via setters)

---

## 🔧 Technologies Used

- Java SE 11+
- JDBC
- MySQL
- Gradle
- Java Faker
- **IDE: IntelliJ IDEA Community Edition**
---

## 📂 Project Structure

- `src/main/java/org/example` – Core logic (inserter, validator, random generator, etc.)
- `testing/` – Screenshots and logs of insertion inputs and resulting outputs  
  - `manual/` – Manual insertion examples  
  - `random/` – Randomized insertion examples

---

## 🚀 How to Run

1. Ensure you have Java 11+ and MySQL installed.
2. Clone the repository.
3. Configure database credentials using the provided setter methods.
4. Build the project (through bash):
   ./gradlew clean build
  
5. Run main method in RowInserter class.