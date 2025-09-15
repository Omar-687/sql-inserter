package org.example;
import java.util.Locale;
import java.util.Scanner;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class RowInserter {
    private static String databaseName = "row_adder_project";
    private static  String url = "jdbc:mysql://localhost:3306/" + databaseName;
    private static  String user = "root";
    private static  String pass = "SiSo451s923/!1232£4$";

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args){
        try (Connection conn = DriverManager.getConnection(url, user, pass)){
            insertRows(conn);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void setUser(String user) {
        RowInserter.user = user;
    }

    public static void setPass(String pass) {
        RowInserter.pass = pass;
    }

    public static void setDatabaseName(String databaseName) {
        RowInserter.databaseName = databaseName;
        RowInserter.url = "jdbc:mysql://localhost:3306/" + databaseName;
    }

    public static String getDatabaseName() {
        return databaseName;
    }



    public static void randomiseInsertion(PreparedStatement sqlStatement,Connection conn, String tableName, int rowAddNum, int columnCount) throws SQLException {
        for (int i = 0; i < rowAddNum; i++){
            for (int j = 0; j < columnCount; j++){
                String columnValue = "";
//                TODO
            }

        }




//        for (int i = 0; i < rowAddNum; i++){
//
//        }

    }
    public static String buildSQLInsert(String tableName, int columnCount, List<Column> columnsList){
        StringBuilder sqlCommand = new StringBuilder("INSERT INTO " + tableName + " (");
        for (int i = 0; i < columnCount; i++){
            Column column = columnsList.get(i);
            sqlCommand.append(column.getName());
            if (i + 1 == columnCount){
                sqlCommand.append(")");
            }
            else{
                sqlCommand.append(", ");
            }


        }
        sqlCommand.append(" VALUES (");
        for (int i = 0; i < columnCount; i ++){
            sqlCommand.append("?");
            if (i + 1 == columnCount){
                sqlCommand.append(")");
            }
            else{
                sqlCommand.append(", ");
            }
        }
        return sqlCommand.toString();
    }
// insert 1 or more rows into the table manually
    public static void insertRows(Connection conn) throws SQLException {

        System.out.println("Enter the table name: ");
        String tableName = scanner.nextLine();
        if (DatabaseInspector.checkTableExists(conn, tableName) == false){
            throw new Error("Table doesn't exist");
        }

//        DatabaseMetaData dbMeta = conn.getMetaData();
//        String databaseNameFromConnection = conn.getCatalog();
//        ResultSet columns = dbMeta.getColumns(databaseNameFromConnection, null, tableName,null);
//
//
//        List<Column> columnsList = new ArrayList<>();
//        int columnCount = 0;
//        System.out.println("Detailed " + columns+ " " + columns);
//
//        while (columns.next()){
//            String columnName = columns.getString("COLUMN_NAME");
//            String typeName = columns.getString("TYPE_NAME");
//            int columnSize = Integer.parseInt(columns.getString("COLUMN_SIZE"));
//            int nullable = columns.getInt("NULLABLE");
//
//            System.out.println("Column: " + columnName + ", Type: " + typeName);
//            Column column = new Column(columnName, typeName, columnSize, nullable);
//            columnsList.add(column);
//            columnCount += 1;
//
//
//        }

        List<Column> columnsList = DatabaseUtils.getTableColumns(conn, tableName);
        int columnCount = columnsList.size();

        System.out.println("How many rows to add?");
        int rowAddNum = Integer.parseInt(scanner.nextLine());



        PreparedStatement pstmt = conn.prepareStatement(buildSQLInsert(tableName, columnCount, columnsList));
        System.out.println("Randomise row insertion?");
        String randomise = scanner.nextLine();
        if ("YES".equals(randomise.toUpperCase())){
            randomiseInsertion(pstmt, conn, tableName, rowAddNum, columnCount);
            return;
        }
//
        for (int i = 0; i < rowAddNum; i++){
            System.out.println("Please enter values of columns of " + (i + 1) + " added row.");
            System.out.println();
            for (int j = 0; j < columnCount; j++){
                Column column = columnsList.get(j);
                System.out.println("Column " + column.getName() + " is defined as " + column.getType() + " with max size " + column.getSize());
                System.out.println("Please enter the value of " + column.getName() + " column:");
                String columnValue = scanner.nextLine();
                Boolean isValidValue = Validator.isValid(columnValue, column);
                while (true){
                    if (isValidValue){
                        break;
                    }
                    System.out.println("Invalid value. Please try again.");
                    columnValue = scanner.nextLine();

                    isValidValue = Validator.isValid(columnValue, column);


                }

                pstmt.setString(j + 1, columnValue);
            }
            pstmt.addBatch();



        }

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

    private static Connection connectToDatabase() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }



}
