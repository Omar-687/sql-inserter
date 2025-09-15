package org.example;

import java.sql.*;

public class DatabaseInspector {


    public static boolean checkTableExists(Connection conn, String tableName) throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        ResultSet rs = meta.getTables(null, null, tableName, new String[] {"TABLE"});

        boolean exists = rs.next();
        rs.close();

        return exists;
    }



    public static void listAllTables(String tableName) {


    }

}
