package org.example;

import java.sql.DatabaseMetaData;

public class Column {
    private String name;
    private String type;
    private int size;
    private int nullable;
    private boolean isNullable;
    public Column(String name, String type, int size, int nullable){
        this.name = name;
        this.type = type;
        this.size = size;
        this.nullable = nullable;

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





    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getSize() {
        return size;
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
}
