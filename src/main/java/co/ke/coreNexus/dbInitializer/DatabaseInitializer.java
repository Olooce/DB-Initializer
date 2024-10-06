package co.ke.coreNexus.dbInitializer;

import co.ke.coreNexus.dbInitializer.models.DataModel;
import co.ke.coreNexus.dbInitializer.models.TableDefinition;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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

        for (TableDefinition field : fields) {
            sql.append("`").append(field.getColumnName()).append("` ")
                    .append(field.getDataType());

            if (field.isPrimaryKey()) {
                sql.append(" PRIMARY KEY");
            }
            if (!field.isNullable()) {
                sql.append(" NOT NULL");
            }
            sql.append(", ");
        }

        // Remove the last comma and space
        sql.setLength(sql.length() - 2);

        // Collect foreign key constraints to append them within the table definition
        List<String> foreignKeyConstraints = new ArrayList<>();
        for (TableDefinition field : fields) {
            if (field.isForeignKey()) {
                foreignKeyConstraints.add(String.format("FOREIGN KEY (`%s`) REFERENCES `%s` (`%s`)",
                        field.getColumnName(),
                        field.getReferencedTable(),
                        field.getReferencedColumn()));
            }
        }

        // Append foreign key constraints to the SQL
        if (!foreignKeyConstraints.isEmpty()) {
            sql.append(", ");
            sql.append(String.join(", ", foreignKeyConstraints));
        }

        sql.append(");");

        return sql.toString();
    }


}
