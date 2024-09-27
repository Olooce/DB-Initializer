
---

# Database Initializer

A Java package that dynamically creates database schemas and tables based on defined data models, ensuring the inclusion of constraints, indexes, and relationships.

## Features

- Automatically generates database schemas from Java data models.
- Supports primary and foreign key constraints.
- Creates indexes for optimized data retrieval.

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

1. Define your data models.
2. Instantiate the `DatabaseInitializer` class with your JDBC URL, username, and password.
3. Call the `initializeDatabase()` method to create the database schema.

Here's a working example:

```java
import co.ke.coreNexus.dbInitializer.DatabaseInitializer;
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

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request.

## License

This project is licensed under the MIT License.

---
