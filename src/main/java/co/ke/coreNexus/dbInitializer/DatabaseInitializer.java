package co.ke.coreNexus.dbInitializer;

import co.ke.coreNexus.dbInitializer.models.DataModel;
import co.ke.coreNexus.dbInitializer.models.TableDefinition;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * DB-Initializer (co.ke)
 * Created by: oloo
 * On: 27/09/2024. 10:49
 * Description:
 *
 **/

public class DatabaseInitializer {

    private final String jdbcUrl;
    private final String username;
    private final String password;

    public DatabaseInitializer(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    public void initializeDatabase(List<DataModel> models) {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             Statement statement = connection.createStatement()) {

            for (DataModel model : models) {
                // Create schema if it doesn't exist
                String schema = model.getSchema();
                if (schema != null && !schema.isEmpty()) {
                    String createSchemaSQL = generateCreateSchemaSQL(schema);
                    statement.execute(createSchemaSQL);
                }

                // Create table within the schema
                String createTableSQL = generateCreateTableSQL(model);
                statement.execute(createTableSQL);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String generateCreateSchemaSQL(String schema) {
        return "CREATE SCHEMA IF NOT EXISTS " + schema + ";";
    }

    private String generateCreateTableSQL(DataModel model) {
        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS ");

        // Include schema in the table creation if present
        if (model.getSchema() != null && !model.getSchema().isEmpty()) {
            sql.append("`").append(model.getSchema()).append("`.");
        }

        sql.append("`").append(model.getTableName()).append("` (");
        List<TableDefinition> fields = model.getFields();

        // Check if fields are present
        if (fields.isEmpty()) {
            sql.append(");"); // Just close the statement if no fields
            return sql.toString();
        }

        for (TableDefinition field : fields) {
            sql.append("`").append(field.getColumnName()).append("` ")
                    .append(field.getDataType());

            if (field.isPrimaryKey()) {
                sql.append(" PRIMARY KEY");
            }
            if (!field.isNullable()) {
                sql.append(" NOT NULL");
            }

            // Add a comma for all but the last field
            sql.append(", ");
        }

        // Remove the last comma and space
        sql.setLength(sql.length() - 2);
        sql.append(");");

        // Collect foreign key constraints after columns are defined
        for (TableDefinition field : fields) {
            if (field.isForeignKey()) {
                sql.append(", FOREIGN KEY (`")
                        .append(field.getColumnName())
                        .append("`) REFERENCES `")
                        .append(field.getReferencedTable())
                        .append("` (`")
                        .append(field.getReferencedColumn())
                        .append("`)");
            }
        }

        System.out.println("Generated SQL: " + sql);

        return sql.toString();
    }

}
