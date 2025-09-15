package org.example;
import java.text.SimpleDateFormat;
import java.text.ParseException;
public class Validator {
    public static boolean isValid(String value, Column column){
        String type = column.getType().toUpperCase();
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
                return isStringWithinLimits(value, column.getSize());
            case "BOOLEAN":
                value = value.toLowerCase();
                return isBoolean(value);
            case "DATE":
                return isDate(value);
        }
        return false;
    }

    public static boolean isInteger(String value){
        try{
            Integer.parseInt(value);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }


    }

    public static boolean isDouble(String value){
        try{
            Double.parseDouble(value);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
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

        return false;
    }


    public static boolean isBoolean(String value){
        if (value.equals("true")){
            return true;
        }
        return false;
    }

}
