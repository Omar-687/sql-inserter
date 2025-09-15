package org.example;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class DatabaseUtils {
    public static List<Column> getTableColumns(Connection conn, String tableName) throws SQLException {
        DatabaseMetaData dbMeta = conn.getMetaData();
        String databaseNameFromConnection = conn.getCatalog();
        ResultSet columns = dbMeta.getColumns(databaseNameFromConnection, null, tableName,null);


        List<Column> columnsList = new ArrayList<>();
        int columnCount = 0;
        System.out.println("Detailed " + columns+ " " + columns);

        while (columns.next()){
            String columnName = columns.getString("COLUMN_NAME");
            String typeName = columns.getString("TYPE_NAME");
            int columnSize = Integer.parseInt(columns.getString("COLUMN_SIZE"));
            int nullable = columns.getInt("NULLABLE");

            System.out.println("Column: " + columnName + ", Type: " + typeName);
            Column column = new Column(columnName, typeName, columnSize, nullable);
            columnsList.add(column);
            columnCount += 1;


        }
        return columnsList;
    }
}
