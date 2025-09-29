package org.example;

import java.sql.DatabaseMetaData;

public class Column {
    private String name;
    private String type;
    private int size;
    private int nullable;
    private boolean isNullable;
    private boolean isPrimaryKey;
    private boolean isAutoIncrement;


    public Column(String name, String type, int size,
                  int nullable, boolean isPrimaryKey, boolean isAutoIncrement){
        this.name = name;
        this.type = type;
        this.size = size;
        this.nullable = nullable;
        this.isPrimaryKey = isPrimaryKey;
        this.isAutoIncrement = isAutoIncrement;

        if (this.nullable == DatabaseMetaData.columnNullable){
            this.isNullable = true;
        }
        else if (this.nullable == DatabaseMetaData.columnNoNulls) {
            this.isNullable = false;
        }
        else{
            this.isNullable = false;
        }

    }



    @Override
    public String toString() {
        return "Column{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", size=" + size +
                ", isNullable=" + isNullable +
                '}';
    }


    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public boolean isPrimaryKey(){
        return isPrimaryKey;
    }
    public boolean isAutoIncrement(){
        return isAutoIncrement;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setPrimaryKey(boolean primaryKey) {
        isPrimaryKey = primaryKey;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        isAutoIncrement = autoIncrement;
    }

}
