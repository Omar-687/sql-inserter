package org.example;
import java.util.Locale;
import java.util.Scanner;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class RowInserter {
//    TODO: get rid of these hardcoded credentials
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



    public static void randomiseInsertion(PreparedStatement pstmt,
                                          Connection conn, String tableName,
                                          int rowAddNum, List<Column> columnList) throws SQLException {
        for (int i = 0; i < rowAddNum; i++){
            for (int j = 0; j < columnList.size(); j++){
                Column column = columnList.get(j);
                if (column.isAutoIncrement()){
                    continue;
                }
                Object randomColumnValue = RandomValueGenerator.generateRandomValue(column);
//              TODO:  deal with null value later
                Boolean isValidValue = Validator.isValid(randomColumnValue.toString(), column);
                if (!isValidValue){
                    throw new IllegalArgumentException();
                }

                pstmt.setString(j + 1, randomColumnValue.toString());
            }

            pstmt.addBatch();

        }
        DatabaseUtils.executeBatchWithTransaction(conn, pstmt);



    }
    public static String buildSQLInsert(String tableName, int columnCount, List<Column> columnsList){
        StringBuilder sqlCommand = new StringBuilder("INSERT INTO " + tableName + " (");
        for (int i = 0; i < columnCount; i++){
            Column column = columnsList.get(i);

            sqlCommand.append(column.getName());
            if (i + 1 == columnCount){
                sqlCommand.append(")");
            }
            if (i + 1 != columnCount){
                sqlCommand.append(", ");
            }


        }
        sqlCommand.append(" VALUES (");
        for (int i = 0; i < columnCount; i ++){
            Column column = columnsList.get(i);

            sqlCommand.append("?");
            if (i + 1 == columnCount){
                sqlCommand.append(")");
            }
            if (i + 1 != columnCount){
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

        List<Column> columnsList = DatabaseUtils.getTableColumns(conn, tableName, true);
        int columnCount = columnsList.size();

        System.out.println("How many rows to add?");
        int rowAddNum = Integer.parseInt(scanner.nextLine());



        PreparedStatement pstmt = conn.prepareStatement(buildSQLInsert(tableName, columnCount, columnsList));
        System.out.println("Randomise row insertion?");
        String randomise = scanner.nextLine();
        if ("YES".equals(randomise.toUpperCase())){
            randomiseInsertion(pstmt, conn, tableName, rowAddNum, columnsList);
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
       System.out.println(pstmt);
       DatabaseUtils.executeBatchWithTransaction(conn, pstmt);



    }

    private static Connection connectToDatabase() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }



}
