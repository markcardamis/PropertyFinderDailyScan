package com.majoapps.propertyfinderdailyscan.utils;

public class StringCheck {
    public static String isNotNullOrEmpty(String str, String delimiter) {
        if(str != null && !str.isEmpty() && delimiter != null) {
            return ((str.endsWith(delimiter)) ? str : str + delimiter ); // don't double add delimiter
        }
        return "";
    }

    public static String firstNotNullWordSpaceDelimiter(String str) {
        if(isNotNullOrEmpty(str)) {
            String[] spaceSplitString = str.split("\\s+");
            return ((spaceSplitString.length > 0) ? spaceSplitString[0] : ""); // don't double add delimiter
        }
        return "";
    }

    public static boolean isNotNullOrEmpty(String str) {
        return(str != null && !str.isEmpty() && str.length() > 0);
    }

    public static String concatWhenNotNull (String originalString, String stringAdded, String delimiter) {
        if (isNotNullOrEmpty(delimiter))
            stringAdded += delimiter;
            return (isNotNullOrEmpty(originalString) ? originalString + stringAdded: stringAdded);
    }
}
