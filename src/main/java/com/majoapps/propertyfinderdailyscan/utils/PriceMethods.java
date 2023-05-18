package com.majoapps.propertyfinderdailyscan.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class PriceMethods
{
    private static int indexOfRegEx(String strSource, String strRegExPattern) {
        int idx = -1; //default value

        Pattern p =  Pattern.compile(strRegExPattern); //compile pattern from string
        Matcher m = p.matcher(strSource); //create a matcher object

        if(m.find()) {
            idx = m.start(); //get the start index using start method of the Matcher class
        }
        return idx;
    }

    private static final String errorNumber = "0";

    public static Integer priceStringToInteger(String text) throws Exception {
        try {
            String originalText = text;
            if (text != null && text.length() > 0 && text.matches(".*\\d.*")) {
                text = text.toLowerCase();
                int firstIndex = text.indexOf('$');
                if (firstIndex < 0) {
                    int firstIndex1 = indexOfRegEx(text,"\\d\\d\\d\\d\\d\\d");
                    if (firstIndex1 < 0) {
                        int firstIndex2 = indexOfRegEx(text,"(\\d+,)?\\d\\d\\d,\\d\\d\\d");
                        if (firstIndex2 < 0) {
                            int firstIndex3 = indexOfRegEx(text,"\\d\\d\\dk");
                            if (firstIndex3 < 0) {
                                int firstIndex4 = indexOfRegEx(text,"\\d\\.(\\d+)( )?m");
                                if (firstIndex4 < 0) {
                                    log.debug("Regex pattern cannot parse \"{}\"", originalText);
                                } else {
                                    firstIndex = firstIndex4;
                                }
                            } else {
                                firstIndex = firstIndex3;
                            }
                        } else {
                            firstIndex = firstIndex2;
                        }
                    } else {
                        firstIndex = firstIndex1;
                    }
                } else {
                    firstIndex++;
                }
                if (firstIndex >= 0) { // found a $
//                    text = text.substring(firstIndex + 1);
                    text = text.substring(firstIndex);

                    int secondIndex = text.indexOf('-');
                    int thirdIndex = text.indexOf("to");
                    int fourthIndex = text.indexOf('$');
                    if (secondIndex > 0) {
                        text = text.substring(0, secondIndex);
                    } else if (thirdIndex > 0) {
                        text = text.substring(0, thirdIndex);
                    } else if (fourthIndex > 0) {
                        text = text.substring(0, fourthIndex);
                    }
                    if (text.contains("mil") || text.contains("million")) {
                        text = text.replaceAll("[^0-9.]", "");
                        text = removeMultipleDecimals(text);
                        return ((int) (Double.parseDouble(text) * 1000000));
                    }

                    text = text.replaceAll("[,|\\s]", "");

                    if (text.length() < 6) {
                        if (text.contains("m")) {
                            text = text.replaceAll("[^0-9.]", "");
                            text = removeMultipleDecimals(text);
                            return ((int) (Double.parseDouble(text) * 1000000));
                        } else if (text.contains("k")) {
                            text = text.replaceAll("[^0-9.]", "");
                            text = removeMultipleDecimals(text);
                            return ((int) (Double.parseDouble(text) * 1000));
                        } else {
                            text = text.replaceAll("[^0-9.]", "");
                            text = removeMultipleDecimals(text);
                            return ((int) (Double.parseDouble(text) * 1000000));
                        }
                    } else {
                        text = text.replaceAll("[^0-9.]", "");
                    }
                } else {
                    text = errorNumber;
                }
            } else {
                text = errorNumber;
            }

            text = removeMultipleDecimals(text);
        } catch (Exception e){
            log.error("Exception string parse \"{}\" : \"{}\" ", text, e.toString());
            return (int) Double.parseDouble(errorNumber);
        }
        return ((int) Double.parseDouble(text));
    }


    private static String removeMultipleDecimals(String text) {
        if (text.matches(".*\\..*\\..*")) {
            int firstIndex = text.indexOf('.');
            int secondIndex = text.indexOf('.', firstIndex + 1);
            if (secondIndex > 0) {
                text = text.substring(0, secondIndex);
            }
        }
        if (text != null && text.length() > 0) {
            return text;
        } else {
            return errorNumber;
        }
    }

    public static Integer convertStringToInteger(String text) {
        if (text != null) {
            text = text.replaceAll("[^0-9]", "");
            return Integer.parseInt(text);
        }
        log.debug("Convert String To Integer null passed in {}", text);
        return 1;
    }

}


