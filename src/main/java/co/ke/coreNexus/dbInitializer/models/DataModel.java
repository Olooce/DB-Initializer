package co.ke.coreNexus.dbInitializer.models;

import java.util.List;

/**
 * DB-Initializer (co.ke.coreNexus)
 * Created by: oloo
 * On: 27/09/2024. 11:00
 * Description:
 * Now includes schema.
 **/

public class DataModel {
    private final String schema;
    private final String tableName;
    private final List<TableDefinition> fields;

    public DataModel(String schema, String tableName, List<TableDefinition> fields) {
        this.schema = schema;
        this.tableName = tableName;
        this.fields = fields;
    }

    public String getSchema() {
        return schema;
    }

    public String getTableName() {
        return tableName;
    }

    public List<TableDefinition> getFields() {
        return fields;
    }
}
