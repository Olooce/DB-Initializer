package co.ke.coreNexus.dbInitializer.models;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DB-Initializer (co.ke.coreNexus)
 * Created by: oloo
 * On: 27/09/2024. 11:00
 * Description:
 *
 **/

public class DataModel {
    private final String schema;
    private final String tableName;
    private final List<TableDefinition> fields;

    @JsonCreator
    public DataModel(
            @JsonProperty("schema") String schema,
            @JsonProperty("tableName") String tableName,
            @JsonProperty("fields") List<TableDefinition> fields) {
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
