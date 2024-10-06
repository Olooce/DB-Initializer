package co.ke.coreNexus.dbInitializer.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DB-Initializer (co.ke.coreNexus)
 * Created by: oloo
 * On: 27/09/2024. 10:58
 * Description:
 **/

public class TableDefinition {
    private final String columnName;
    private final String dataType;
    private final boolean isPrimaryKey;
    private final boolean isForeignKey;
    private final String referencedTable;
    private final String referencedColumn;
    private final boolean isNullable;

    @JsonCreator
    public TableDefinition(
            @JsonProperty("columnName") String columnName,
            @JsonProperty("dataType") String dataType,
            @JsonProperty("primaryKey") boolean isPrimaryKey,
            @JsonProperty("foreignKey") boolean isForeignKey,
            @JsonProperty("referencedTable") String referencedTable,
            @JsonProperty("referencedColumn") String referencedColumn,
            @JsonProperty("nullable") boolean isNullable) {
        this.columnName = columnName;
        this.dataType = dataType;
        this.isPrimaryKey = isPrimaryKey;
        this.isForeignKey = isForeignKey;
        this.referencedTable = referencedTable;
        this.referencedColumn = referencedColumn;
        this.isNullable = isNullable;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getDataType() {
        return dataType;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public boolean isForeignKey() {
        return isForeignKey;
    }

    public String getReferencedTable() {
        return referencedTable;
    }

    public String getReferencedColumn() {
        return referencedColumn;
    }

    public boolean isNullable() {
        return isNullable;
    }
}