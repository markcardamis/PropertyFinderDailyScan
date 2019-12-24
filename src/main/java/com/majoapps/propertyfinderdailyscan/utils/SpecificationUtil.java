package com.majoapps.propertyfinderdailyscan.utils;

import com.majoapps.propertyfinderdailyscan.business.domain.PropertyListingDTO;

import org.apache.commons.lang3.StringUtils;

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
            // if (propertyInformation.getSuburbName() != null && 
            //     propertyInformation.getSuburbName().length() > 0) {
            //     sb.append(" AND suburbName:");
            //     sb.append('\'');
            //     sb.append(propertyInformation.getSuburbName().toUpperCase());
            //     sb.append('\'');
            // }
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

    private static String roadString (String roadString) {
        roadString = roadString.replaceAll("ALLEY", "ALLY");
        roadString = roadString.replaceAll("ARCADE", "ARC");
        roadString = roadString.replaceAll("AVENUE", "AVE");
        roadString = roadString.replaceAll("BOULEVARD", "BVD");
        roadString = roadString.replaceAll("BYPASS", "BYPA");
        roadString = roadString.replaceAll("CIRCUIT", "CCT");
        roadString = roadString.replaceAll("CLOSE", "CL");
        roadString = roadString.replaceAll("CORNER", "CRN");
        roadString = roadString.replaceAll("COURT", "CT");
        roadString = roadString.replaceAll("CRESCENT", "CRES");
        roadString = roadString.replaceAll("CUL-DE-SAC", "CDS");
        roadString = roadString.replaceAll("DRIVE", "DR");
        roadString = roadString.replaceAll("ESPLANADE", "ESP");
        roadString = roadString.replaceAll("GREEN", "GRN");
        roadString = roadString.replaceAll("GROVE", "GR");
        roadString = roadString.replaceAll("HIGHWAY", "HWY");
        roadString = roadString.replaceAll("JUNCTION", "JNC");
        roadString = roadString.replaceAll("LANE", "LANE");
        roadString = roadString.replaceAll("LINK", "LINK");
        roadString = roadString.replaceAll("MEWS", "MEWS");
        roadString = roadString.replaceAll("PARADE", "PDE");
        roadString = roadString.replaceAll("PLACE", "PL");
        roadString = roadString.replaceAll("RIDGE", "RDGE");
        roadString = roadString.replaceAll("ROAD", "RD");
        roadString = roadString.replaceAll("SQUARE", "SQ");
        roadString = roadString.replaceAll("STREET", "ST");
        roadString = roadString.replaceAll("TERRACE", "TCE");
        roadString = roadString.replaceAll("  ", " ");
        return roadString;
    }

}