package org.example;
import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

public class DatabaseUtils {

    public static void executeBatchWithTransaction(Connection conn, PreparedStatement pstmt) throws SQLException {
        conn.setAutoCommit(false);
        try{
            int[] res = pstmt.executeBatch();
            conn.commit();
            System.out.println(res.length + " rows inserted.");
        } catch (SQLException e) {
            conn.rollback();
            System.out.println("Insertion failed. Transaction rolled back.");
            e.printStackTrace();
        } finally{
            conn.setAutoCommit(true);
            pstmt.close();
        }
    }

    public static List<Column> getTableColumns(Connection conn, String tableName, boolean columnsWithoutAutoInc) throws SQLException {
        DatabaseMetaData dbMeta = conn.getMetaData();
        String databaseNameFromConnection = conn.getCatalog();
        ResultSet columns = dbMeta.getColumns(databaseNameFromConnection, null, tableName,null);
        List<Column> columnsList = new ArrayList<>();
        System.out.println("Detailed " + columns+ " " + columns);


        ResultSet primaryKeys = dbMeta.getPrimaryKeys(databaseNameFromConnection, null, tableName);
        Set<String> pkColumns = new HashSet<>();
        while (primaryKeys.next()) {
            String pkColumnName = primaryKeys.getString("COLUMN_NAME");
            pkColumns.add(pkColumnName);
        }





        while (columns.next()){
            String columnName = columns.getString("COLUMN_NAME");
            String typeName = columns.getString("TYPE_NAME");
            int columnSize = Integer.parseInt(columns.getString("COLUMN_SIZE"));
            int nullable = columns.getInt("NULLABLE");
            String isAutoIncrementStr = columns.getString("IS_AUTOINCREMENT");
            boolean isAutoIncrement = "YES".equalsIgnoreCase(isAutoIncrementStr);

            boolean isPrimaryKey = pkColumns.contains(columnName);
//            System.out.println("Column: " + columnName + ", Type: " + typeName);
            if (isAutoIncrement && columnsWithoutAutoInc){
                continue;
            }
            Column column = new Column(columnName, typeName, columnSize, nullable, isPrimaryKey, isAutoIncrement);
            columnsList.add(column);


        }
        return columnsList;
    }
}
