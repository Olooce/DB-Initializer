package co.ke.coreNexus.dbInitializer.models;

/**
 * DB-Initializer (co.ke.coreNexus)
 * Created by: oloo
 * On: 27/09/2024. 10:58
 * Description:
 **/

public class TableDefinition {
    private String columnName;
    private String dataType;
    private boolean isPrimaryKey;
    private boolean isForeignKey;
    private String referencedTable;
    private String referencedColumn;
    private boolean isNullable;

    public TableDefinition(String columnName, String dataType, boolean isPrimaryKey,
                           boolean isForeignKey, String referencedTable, String referencedColumn, boolean isNullable) {
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