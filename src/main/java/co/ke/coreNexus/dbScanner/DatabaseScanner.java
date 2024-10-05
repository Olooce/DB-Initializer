package co.ke.coreNexus.dbScanner;

import co.ke.coreNexus.dbInitializer.models.DataModel;
import co.ke.coreNexus.dbInitializer.models.TableDefinition;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DB-Initializer (co.ke.coreNexus.dbScanner)
 * Created by: oloo
 * On: 27/09/2024. 21:45
 * Description:Scans a connected database and generates DataModel objects.
 * These models can be exported and later used to initialize the database.
 **/

public class DatabaseScanner {

    private final String jdbcUrl;
    private final String username;
    private final String password;

    public DatabaseScanner(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    public List<DataModel> scanDatabase() throws SQLException {
        List<DataModel> dataModels = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});

            while (tables.next()) {
                String schema = tables.getString("TABLE_SCHEMA");
                String tableName = tables.getString("TABLE_NAME");

                // Get columns for the table
                List<TableDefinition> fields = getColumns(metaData, schema, tableName);

                // Add the data model
                dataModels.add(new DataModel(schema, tableName, fields));
            }
        }
        return dataModels;
    }

    private List<TableDefinition> getColumns(DatabaseMetaData metaData, String schema, String tableName) throws SQLException {
        List<TableDefinition> columns = new ArrayList<>();

        ResultSet columnsRS = metaData.getColumns(null, schema, tableName, "%");
        ResultSet primaryKeyRS = metaData.getPrimaryKeys(null, schema, tableName);
        ResultSet foreignKeyRS = metaData.getImportedKeys(null, schema, tableName);

        Map<String, Boolean> primaryKeyMap = new HashMap<>();
        while (primaryKeyRS.next()) {
            String pkColumnName = primaryKeyRS.getString("COLUMN_NAME");
            primaryKeyMap.put(pkColumnName, true);
        }

        Map<String, ForeignKeyInfo> foreignKeyMap = new HashMap<>();
        while (foreignKeyRS.next()) {
            String fkColumnName = foreignKeyRS.getString("FKCOLUMN_NAME");
            String referencedTable = foreignKeyRS.getString("PKTABLE_NAME");
            String referencedColumn = foreignKeyRS.getString("PKCOLUMN_NAME");
            foreignKeyMap.put(fkColumnName, new ForeignKeyInfo(referencedTable, referencedColumn));
        }

        while (columnsRS.next()) {
            String columnName = columnsRS.getString("COLUMN_NAME");
            String dataType = columnsRS.getString("TYPE_NAME");
            boolean isNullable = "YES".equals(columnsRS.getString("IS_NULLABLE"));
            boolean isPrimaryKey = primaryKeyMap.getOrDefault(columnName, false);

            ForeignKeyInfo foreignKeyInfo = foreignKeyMap.get(columnName);
            boolean isForeignKey = foreignKeyInfo != null;

            String referencedTable = isForeignKey ? foreignKeyInfo.referencedTable() : null;
            String referencedColumn = isForeignKey ? foreignKeyInfo.referencedColumn() : null;

            columns.add(new TableDefinition(columnName, dataType, isPrimaryKey, isForeignKey, referencedTable, referencedColumn, isNullable));
        }

        return columns;
    }

    private record ForeignKeyInfo(String referencedTable, String referencedColumn) {
    }
}