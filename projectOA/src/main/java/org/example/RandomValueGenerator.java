package org.example;
import java.sql.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import com.github.javafaker.Faker;
// subclasses should be more specific
public class RandomValueGenerator {
    private static final Random random = new Random();
    private static Faker faker = new Faker();
    public static Object generateRandomValue(Column column){
        String columnType = column.getType();
        String columName = column.getName().toLowerCase();
        int maxSize = column.getSize();

        switch (columnType.toUpperCase()){
            case "INT":
            case "INTEGER":
                return randomInt(Integer.MIN_VALUE, Integer.MAX_VALUE);

            case "FLOAT":
            case "DOUBLE":
            case "DECIMAL":
            case "NUMERIC":
                return randomDouble(Double.MIN_VALUE, Double.MIN_VALUE);
            case "CHAR":
            case "VARCHAR":
            case "TEXT":
                if (columName.contains("email")) return faker.internet().emailAddress();
                if (columName.contains("first_name")) return faker.name().firstName();
                if (columName.contains("last_name")) return faker.name().lastName();
                if (columName.contains("name")) return faker.name().fullName();
                if (columName.contains("company")) return faker.company().name();
                if (columName.contains("country")) return faker.country().name();


                return randomString(column.getSize());
            case "BOOLEAN":
                return randomBoolean();
            case "DATE":
                Date minDate = new Date(Long.MIN_VALUE);
                Date maxDate = new Date(Long.MAX_VALUE);
                return randomDate(minDate, maxDate);
            }

        return null;
    }
    public static int randomInt(int min, int max){
        return ThreadLocalRandom.current().nextInt(min, max);
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
    public static Boolean randomBoolean(){
        return random.nextBoolean();
    }

    public static Date randomDate(Date start, Date end){
        long startMillis = start.getTime();
        long endMillis = end.getTime();
        long randomMillis = ThreadLocalRandom.current().nextLong(startMillis, endMillis + 1);

        return new Date(randomMillis);
    }


}
