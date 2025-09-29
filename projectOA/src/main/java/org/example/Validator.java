package org.example;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;


public class Validator {
//    TODO: add validation of checks
    public static boolean isValid(String value, Column column){
        String type = column.getType().toUpperCase();
        String columnName = column.getName().toLowerCase();
        switch (type){
            case "INT":
            case "INTEGER":
                return isInteger(value);
            case "FLOAT":
            case "DOUBLE":
            case "DECIMAL":
            case "NUMERIC":
                return isDouble(value);
            case "CHAR":
            case "VARCHAR":
            case "TEXT":
//                TODO: add more possible checks in future
                if (columnName.contains("email")) return isEmail(value);
                return isStringWithinLimits(value, column.getSize());
            case "BOOLEAN":
                value = value.toLowerCase();
                return isBoolean(value);
            case "DATE":
                return isDate(value);
        }
        return false;
    }
    public static boolean isEmail(String value){
        if (value == null || value.isEmpty()){
            return false;
        }
        try{
            InternetAddress emailAddress = new InternetAddress(value);
            emailAddress.validate();
        } catch (AddressException e) {
            return false;
        }


        return true;
    }


    public static boolean isInteger(String value){
        try{
            Integer.parseInt(value);
        }
        catch (NumberFormatException e){
            return false;
        }
        return true;


    }

    public static boolean isDouble(String value){
        try{
            Double.parseDouble(value);
        }
        catch (NumberFormatException e){
            return false;
        }
        return true;
    }

    public static boolean isStringWithinLimits(String value, int size){
        return value.length() <= size;
    }

    public static boolean isDate(String value){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        try{
            sdf.parse(value);
        } catch (ParseException e) {
           return false;

        }
        return true;

    }


    public static boolean isBoolean(String value){
        if (value.equals("true")){
            return true;
        }
        return false;
    }

}
