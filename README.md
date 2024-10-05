
_---

# Database Initializer

A Java package that dynamically creates database schemas and tables based on defined data models, ensuring the inclusion
of constraints, indexes, and relationships.

## Motivation

I came up with this project when I realized people were cloning into some of my projects that required a database but 
did not have a way of recreating the exact schema I was using. Hopefully, this can become a standard way to export my 
schemas or even allow applications to automatically initialize the required schema if it doesn't exist.

The goal is to enable recreating databases without necessarily recreating the data in them.

To see an example complete implementation, you can visit this [GitHub repository](https://github.com/Olooce/FedhaYouthGroupSystem-SCO200_Project/blob/master/src/main/java/ac/ku/oloo/configs/DB_Config.java).

## Features

- Automatically generates database schemas from Java data models.
- Supports primary and foreign key constraints.
- Creates indexes for optimized data retrieval.
- Scans existing databases to generate data models automatically.
- Exports and loads data models in JSON format.

## Requirements

- Java 8 or higher
- A compatible database (e.g., MySQL, PostgreSQL, etc.)

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/Olooce/DB-Initializer.git
   ```
2. Add the package to your project’s dependencies.

## Usage

### 1. Define and Initialize Database

Define your data models, instantiate the `DatabaseInitializer` class, and call the `initializeDatabase()` method to create the database schema.

Here's a working example:

```java
import co.ke.coreNexus.dbInitializer.models.DataModel;
import co.ke.coreNexus.dbInitializer.models.TableDefinition;

import java.util.ArrayList;
import java.util.List;

public class Main {
   public static void main(String[] args) {
      String jdbcUrl = "jdbc:mysql://localhost:3306/your_database"; // Change this to your DB URL
      String username = "your_username"; // Your DB username
      String password = "your_password"; // Your DB password

      DatabaseInitializer dbInitializer = new DatabaseInitializer(jdbcUrl, username, password);

      // Define your data models 
      List<DataModel> models = new ArrayList<>();

      // Example for a Users table in the 'app_schema'
      List<TableDefinition> userFields = new ArrayList<>();
      userFields.add(new TableDefinition("userId", "BIGINT", true, false, null, null, false));
      userFields.add(new TableDefinition("username", "VARCHAR(50)", false, false, null, null, false));
      userFields.add(new TableDefinition("passwordHash", "VARCHAR(255)", false, false, null, null, false));
      models.add(new DataModel("app_schema", "Users", userFields));

      // Example for a Members table in the 'app_schema'
      List<TableDefinition> memberFields = new ArrayList<>();
      memberFields.add(new TableDefinition("memberId", "BIGINT", true, false, null, null, false));
      memberFields.add(new TableDefinition("userId", "BIGINT", false, true, "Users", "userId", false));
      memberFields.add(new TableDefinition("memberName", "VARCHAR(100)", false, false, null, null, false));
      models.add(new DataModel("app_schema", "Members", memberFields));

      // Initialize the database with schema creation
      dbInitializer.initializeDatabase(models);
   }
}
```

### 2. Scan an Existing Database

The `DatabaseScanner` class allows you to scan an existing database and generate `DataModel` objects. These models can 
be exported for reuse.

```java
import co.ke.coreNexus.dbScanner.DatabaseScanner;
import co.ke.coreNexus.dbInitializer.models.DataModel;

import java.sql.SQLException;
import java.util.List;

public class ScanDatabase {
   public static void main(String[] args) {
      String jdbcUrl = "jdbc:mysql://localhost:3306/your_database"; // Change this to your DB URL
      String username = "your_username"; // Your DB username
      String password = "your_password"; // Your DB password

      DatabaseScanner dbScanner = new DatabaseScanner(jdbcUrl, username, password);

      try {
         // Scan the database and get the data models
         List<DataModel> dataModels = dbScanner.scanDatabase();
         // You can now use these data models to initialize another database or export them
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }
}
```

### 3. Export Database Models to a File

The `DatabaseExporter` class allows you to export the scanned or defined data models to a JSON file for reuse or versioning.

```java
import co.ke.coreNexus.dbScanner.DatabaseExporter;
import co.ke.coreNexus.dbInitializer.models.DataModel;

import java.io.IOException;
import java.util.List;

public class ExportDatabaseModels {
   public static void main(String[] args) {
      // Assume dataModels is a list of DataModel objects obtained from the scanner or defined manually
      List<DataModel> dataModels = ...; 
      String filePath = "path/to/export/dataModels.json";

      try {
         DatabaseExporter.exportDataModelsToFile(dataModels, filePath);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
```

### 4. Load Data Models from a File

The `ModelLoader` class allows you to load data models from a JSON file for initializing a database.

```java
import co.ke.coreNexus.dbInitializer.ModelLoader;
import co.ke.coreNexus.dbInitializer.models.DataModel;

import java.io.IOException;
import java.util.List;

public class LoadDataModels {
   public static void main(String[] args) {
      String filePath = "path/to/export/dataModels.json";

      try {
         // Load data models from the JSON file
         List<DataModel> dataModels = ModelLoader.loadDataModelsFromFile(filePath);
         // Use these models to initialize a database
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
```

## Explanation of Usage and Importance

### Usage Flow

1. **Define Your Data Models**: These models represent the structure of your database, specifying schemas, tables, and constraints. The `DataModel` and `TableDefinition` classes are used to define this structure in Java.
2. **Database Initialization**: The `DatabaseInitializer` class is used to create the database schema based on these models. You can dynamically create schemas, tables, columns, and constraints without writing manual SQL.
3. **Scan Existing Database**: If you already have a database, use the `DatabaseScanner` to scan its structure and generate corresponding Java data models.
4. **Export and Load Models**: Export the data models to a JSON file for reuse or versioning (`DatabaseExporter`). Load them later to recreate or initialize a similar database (`ModelLoader`).

### Importance

1. **Schema Synchronization**: Instead of manually writing SQL to create tables and constraints, you can define them programmatically. This ensures that your Java application has an exact representation of the database.
2. **Automation**: Automates database creation, making it easier for developers to set up environments, especially in CI/CD workflows.
3. **Maintenance**: It reduces the complexity of maintaining schema changes by defining them centrally in Java objects and exporting the schema for later reuse or versioning.
4. **Flexibility**: You can dynamically create or modify the database structure. It helps when updating schemas or setting up fresh environments without relying on SQL scripts.

The package is especially useful for projects involving rapid iterations, where database schema changes need to be propagated consistently across different environments with minimal manual intervention.

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request.

## License

This project is licensed under the MIT License.

---_ 
