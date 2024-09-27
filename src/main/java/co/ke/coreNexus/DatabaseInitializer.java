package co.ke.coreNexus;

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
 **/

public class DatabaseInitializer {

    private String jdbcUrl;
    private String username;
    private String password;

    public DatabaseInitializer(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    public void initializeDatabase(List<DataModel> models) {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             Statement statement = connection.createStatement()) {

            for (DataModel model : models) {
                String createTableSQL = generateCreateTableSQL(model);
                statement.execute(createTableSQL);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String generateCreateTableSQL(DataModel model) {
        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS " + model.getTableName() + " (");
        List<TableDefinition> fields = model.getFields();

        for (TableDefinition field : fields) {
            sql.append(field.getColumnName())
                    .append(" ")
                    .append(field.getDataType());

            if (field.isPrimaryKey()) {
                sql.append(" PRIMARY KEY");
            }
            if (field.isForeignKey()) {
                sql.append(" REFERENCES ").append(field.getReferencedTable()).append("(").append(field.getReferencedColumn()).append(")");
            }
            if (!field.isNullable()) {
                sql.append(" NOT NULL");
            }
            sql.append(", ");
        }

        // Remove the last comma and space
        sql.setLength(sql.length() - 2);
        sql.append(");");
        return sql.toString();
    }
}