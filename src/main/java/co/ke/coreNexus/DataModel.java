package co.ke.coreNexus;

import java.util.List;

/**
 * DB-Initializer (co.ke.coreNexus)
 * Created by: oloo
 * On: 27/09/2024. 11:00
 * Description:
 **/

public class DataModel {
    private String tableName;
    private List<TableDefinition> fields;

    public DataModel(String tableName, List<TableDefinition> fields) {
        this.tableName = tableName;
        this.fields = fields;
    }

    public String getTableName() {
        return tableName;
    }

    public List<TableDefinition> getFields() {
        return fields;
    }
}
