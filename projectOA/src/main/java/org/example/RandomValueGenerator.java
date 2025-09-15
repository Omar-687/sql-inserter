package org.example;
import java.sql.*;
import java.util.concurrent.ThreadLocalRandom;

// subclasses should be more specific
public class RandomValueGenerator {

//    public static int generateRandomValue(Column column){
//        String columnType = column.getType();
//        int maxSize = column.getSize();
//
//        switch (columnType.toUpperCase()){
//            case "VARCHAR":
//            case "TEXT":
//                return 5;
//        }
//
//
//    }
    public static int randomInt(int min, int max){
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
    public static double randomDouble(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    public static String randomString(int length){
        StringBuilder res = new StringBuilder();
        String allChars = allowedChars();
        int calculatedLength = randomInt(0, length);
        for (int i = 0; i < calculatedLength; i++){
            int idx = ThreadLocalRandom.current().nextInt(allChars.length());
            res.append(allChars.charAt(idx));

        }
        return res.toString();
    }
    public static String allowedChars(){
        StringBuilder sb = new StringBuilder();
        for (char c = 'a'; c <= 'z'; c++) sb.append(c);
        for (char c = 'A'; c <= 'Z'; c++) sb.append(c);
        for (char c = '0'; c <= '9'; c++) sb.append(c);

        return sb.toString();
    }
    public static Date randomDate(Date start, Date end){
        long startMillis = start.getTime();
        long endMillis = end.getTime();
        long randomMillis = ThreadLocalRandom.current().nextLong(startMillis, endMillis + 1);

        return new Date(randomMillis);
    }


}
