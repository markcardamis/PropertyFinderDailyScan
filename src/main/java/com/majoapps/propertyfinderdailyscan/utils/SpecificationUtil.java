package com.majoapps.propertyfinderdailyscan.utils;

import com.majoapps.propertyfinderdailyscan.business.domain.PropertyListingDTO;

public class SpecificationUtil {

    public static String createSpecificationString (PropertyListingDTO propertyInformation) {
        StringBuilder sb = new StringBuilder("propertyId>0");
        if (propertyInformation != null) {
            if (propertyInformation.getUnitNumber() != null && 
                propertyInformation.getUnitNumber().length() > 0) {
                sb.append(" AND unitNumber:");
                sb.append('\'');
                sb.append(propertyInformation.getUnitNumber().toUpperCase());
                sb.append('\'');
            }
            if (propertyInformation.getHouseNumber() != null && 
                propertyInformation.getHouseNumber().length() > 0) {
                sb.append(" AND houseNumber:");
                sb.append('\'');
                sb.append(propertyInformation.getHouseNumber().toUpperCase());
                sb.append('\'');
            }
            if (propertyInformation.getStreetName() != null && 
                propertyInformation.getStreetName().length() > 0) {
                sb.append(" AND streetName:");
                sb.append('\'');
                sb.append(roadString(propertyInformation.getStreetName().toUpperCase()));
                sb.append('\'');
            }
            if (propertyInformation.getSuburbName() != null && 
                propertyInformation.getSuburbName().length() > 0) {
                sb.append(" AND suburbName:");
                sb.append('\'');
                sb.append(propertyInformation.getSuburbName().toUpperCase());
                sb.append('\'');
            }
            if (propertyInformation.getPostCode() != null && 
                propertyInformation.getPostCode().length() > 0) {
                sb.append(" AND postCode:");
                sb.append('\'');
                sb.append(propertyInformation.getPostCode().toUpperCase());
                sb.append('\'');
            }
        }
        return sb.toString();
    }

    public static String createAddressString (PropertyListingDTO propertyInformation) {
        StringBuilder sb = new StringBuilder("");
        if (propertyInformation != null) {
            if (propertyInformation.getUnitNumber() != null && 
                propertyInformation.getUnitNumber().length() > 0) {
                sb.append(propertyInformation.getUnitNumber().toUpperCase());
                sb.append(" ");
            }
            if (propertyInformation.getHouseNumber() != null && 
                propertyInformation.getHouseNumber().length() > 0) {
                sb.append(propertyInformation.getHouseNumber().toUpperCase());
                sb.append(" ");
            }
            if (propertyInformation.getStreetName() != null && 
                propertyInformation.getStreetName().length() > 0) {
                sb.append(roadString(propertyInformation.getStreetName().toUpperCase()));
                sb.append(" ");
            }
            if (propertyInformation.getSuburbName() != null && 
                propertyInformation.getSuburbName().length() > 0) {
                sb.append(propertyInformation.getSuburbName().toUpperCase());
                sb.append(" ");
            }
            if (propertyInformation.getPostCode() != null && 
                propertyInformation.getPostCode().length() > 0) {
                sb.append(propertyInformation.getPostCode().toUpperCase());
            }
        }
        return sb.toString();
    }

    private static String roadString (String roadString) {
        String returnRoadString[] = roadString.split("\\ ");
        if (returnRoadString.length > 1) {
            String result = roadString.substring(0,roadString.lastIndexOf(" "));
            String tempString = returnRoadString[returnRoadString.length - 1];
            tempString = tempString.replaceFirst("ALLEY", "ALLY");
            tempString = tempString.replaceFirst("ARCADE", "ARC");
            tempString = tempString.replaceFirst("AVENUE", "AVE");
            tempString = tempString.replaceFirst("BOULEVARD", "BVD");
            tempString = tempString.replaceFirst("BYPASS", "BYPA");
            tempString = tempString.replaceFirst("CIRCUIT", "CCT");
            tempString = tempString.replaceFirst("CLOSE", "CL");
            tempString = tempString.replaceFirst("CORNER", "CRN");
            tempString = tempString.replaceFirst("COURT", "CT");
            tempString = tempString.replaceFirst("CRESCENT", "CRES");
            tempString = tempString.replaceFirst("CUL-DE-SAC", "CDS");
            tempString = tempString.replaceFirst("DRIVE", "DR");
            tempString = tempString.replaceFirst("ESPLANADE", "ESP");
            tempString = tempString.replaceFirst("GREEN", "GRN");
            tempString = tempString.replaceFirst("GROVE", "GR");
            tempString = tempString.replaceFirst("HIGHWAY", "HWY");
            tempString = tempString.replaceFirst("JUNCTION", "JNC");
            tempString = tempString.replaceFirst("LANE", "LANE");
            tempString = tempString.replaceFirst("LINK", "LINK");
            tempString = tempString.replaceFirst("MEWS", "MEWS");
            tempString = tempString.replaceFirst("PARADE", "PDE");
            tempString = tempString.replaceFirst("PLACE", "PL");
            tempString = tempString.replaceFirst("RIDGE", "RDGE");
            tempString = tempString.replaceFirst("ROAD", "RD");
            tempString = tempString.replaceFirst("SQUARE", "SQ");
            tempString = tempString.replaceFirst("STREET", "ST");
            tempString = tempString.replaceFirst("TERRACE", "TCE");
            roadString = result + " " + tempString;
        } 
        roadString = roadString.replaceAll("  ", " ");
        return roadString;
    }

}