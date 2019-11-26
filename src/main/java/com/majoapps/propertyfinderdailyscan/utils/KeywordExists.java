package com.majoapps.propertyfinderdailyscan.utils;

public class KeywordExists {

    public Boolean isKeywordPresent(String inputString, String[] keywords) {
        if (inputString != null && inputString.length() > 0 && keywords != null && keywords.length > 0) {
            for (String keyword : keywords) {
                if ((keyword != null) && (inputString.contains(keyword))) {
                    return true;
                }
            }
        }
        return false;
    }

    public String keywordPresent(String inputString, String[] keywords) {
        if (inputString != null && inputString.length() > 0 && keywords != null && keywords.length > 0) {
            for (String keyword : keywords) {
                if ((keyword != null) && (inputString.contains(keyword))) {
                    return keyword;
                }
            }
        }
        return "";
    }
}
