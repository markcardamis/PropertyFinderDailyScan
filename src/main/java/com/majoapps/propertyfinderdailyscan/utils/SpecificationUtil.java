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
        String roadStringTemp = roadString;

        //look for unusual multi-string abbreviations first
        roadStringTemp = roadStringTemp.replaceFirst("SERVICE WAY", "SWY");
        roadStringTemp = roadStringTemp.replaceFirst("FIRE TRACK", "FTRK");
        roadStringTemp = roadStringTemp.replaceFirst("CUL DE SAC", "CDS");

        //split the string into parts uses spaces
        String[] returnRoadString = roadStringTemp.split("\\s+");

        if (returnRoadString.length > 1) {
            //only check the last word in the array for the abbreviation
            String tempString = returnRoadString[returnRoadString.length - 1];
            tempString = tempString.replaceFirst("ACCESS", "ACCS");
            tempString = tempString.replaceFirst("ALLEY", "ALLY");
            tempString = tempString.replaceFirst("AMBLE", "AMBL");
            tempString = tempString.replaceFirst("APPROACH", "APP");
            tempString = tempString.replaceFirst("ARCADE", "ARC");
            tempString = tempString.replaceFirst("ARTERY", "ARTL");
            tempString = tempString.replaceFirst("AVENUE", "AVE");
            tempString = tempString.replaceFirst("BANAN", "BA");
            tempString = tempString.replaceFirst("BANK", "BANK");
            tempString = tempString.replaceFirst("BAY", "BAY");
            tempString = tempString.replaceFirst("BEACH", "BCH");
            tempString = tempString.replaceFirst("BROADWAY", "BDWY");
            tempString = tempString.replaceFirst("BEND", "BEND");
            tempString = tempString.replaceFirst("BOWL", "BOWL");
            tempString = tempString.replaceFirst("BRAE", "BRAE");
            tempString = tempString.replaceFirst("BRACE", "BRCE");
            tempString = tempString.replaceFirst("BREAK", "BRK");
            tempString = tempString.replaceFirst("BROW", "BROW");
            tempString = tempString.replaceFirst("BUSWAY", "BSWY");
            tempString = tempString.replaceFirst("BOULEVARD", "BVD");
            tempString = tempString.replaceFirst("BOARDWALK", "BWLK");
            tempString = tempString.replaceFirst("BYPASS", "BYPA");
            tempString = tempString.replaceFirst("CAUSEWAY", "CAUS");
            tempString = tempString.replaceFirst("CIRCUIT", "CCT");
            tempString = tempString.replaceFirst("CUL-DE-SAC", "CDS");
            tempString = tempString.replaceFirst("CHASE", "CH");
            tempString = tempString.replaceFirst("CIRCLE", "CIR");
            tempString = tempString.replaceFirst("CLOSE", "CL");
            tempString = tempString.replaceFirst("CLUSTER", "CLR");
            tempString = tempString.replaceFirst("COMMON", "CMMN");
            tempString = tempString.replaceFirst("COMMONS", "CMMNS");
            tempString = tempString.replaceFirst("CONCORD", "CNCD");
            tempString = tempString.replaceFirst("CORNER", "CNR");
            tempString = tempString.replaceFirst("CONNECTION", "CNTN");
            tempString = tempString.replaceFirst("CENTREWAY", "CNWY");
            tempString = tempString.replaceFirst("CONCOURSE", "CON");
            tempString = tempString.replaceFirst("COVE", "COVE");
            tempString = tempString.replaceFirst("COPSE", "CPS");
            tempString = tempString.replaceFirst("CIRCUS", "CRCS");
            tempString = tempString.replaceFirst("CRESCENT", "CRES");
            tempString = tempString.replaceFirst("CRIEF", "CRF");
            tempString = tempString.replaceFirst("COURSE", "CRSE");
            tempString = tempString.replaceFirst("CROSSING", "CRSG");
            tempString = tempString.replaceFirst("CROSS", "CRSS");
            tempString = tempString.replaceFirst("CREST", "CRST");
            tempString = tempString.replaceFirst("CORSO", "CSO");
            tempString = tempString.replaceFirst("COURT", "CT");
            tempString = tempString.replaceFirst("CENTRE", "CTR");
            tempString = tempString.replaceFirst("CUTTING", "CTTG");
            tempString = tempString.replaceFirst("COURTYARD", "CTYD");
            tempString = tempString.replaceFirst("CRUISEWAY", "CUWY");
            tempString = tempString.replaceFirst("DALE", "DALE");
            tempString = tempString.replaceFirst("DASH", "DASH");
            tempString = tempString.replaceFirst("DELL", "DELL");
            tempString = tempString.replaceFirst("DENE", "DENE");
            tempString = tempString.replaceFirst("DEVIATION", "DEVN");
            tempString = tempString.replaceFirst("DIP", "DIP");
            tempString = tempString.replaceFirst("DIVIDE", "DIV");
            tempString = tempString.replaceFirst("DOCK", "DOCK");
            tempString = tempString.replaceFirst("DOMAIN", "DOM");
            tempString = tempString.replaceFirst("DOWN", "DOWN");
            tempString = tempString.replaceFirst("DRIVE", "DR");
            tempString = tempString.replaceFirst("DRIVEWAY", "DRWY");
            tempString = tempString.replaceFirst("DISTRIBUTOR", "DSTR");
            tempString = tempString.replaceFirst("EDGE", "EDGE");
            tempString = tempString.replaceFirst("ELBOW", "ELB");
            tempString = tempString.replaceFirst("END", "END");
            tempString = tempString.replaceFirst("ENTRANCE", "ENT");
            tempString = tempString.replaceFirst("EASEMENT", "ESMT");
            tempString = tempString.replaceFirst("ESPLANADE", "ESP");
            tempString = tempString.replaceFirst("ESTATE", "EST");
            tempString = tempString.replaceFirst("EXPRESSWAY", "EXP");
            tempString = tempString.replaceFirst("FAIRWAY", "FAWY");
            tempString = tempString.replaceFirst("FIRETRAIL", "FITR");
            tempString = tempString.replaceFirst("FLAT", "FLAT");
            tempString = tempString.replaceFirst("FIRELINE", "FLNE");
            tempString = tempString.replaceFirst("FOLLOW", "FOLW");
            tempString = tempString.replaceFirst("FORD", "FORD");
            tempString = tempString.replaceFirst("FORK", "FORK");
            tempString = tempString.replaceFirst("FRONTAGE", "FRTG");
            tempString = tempString.replaceFirst("FORESHORE", "FSHR");
            tempString = tempString.replaceFirst("FREEWAY", "FWY");
            tempString = tempString.replaceFirst("GAP", "GAP");
            tempString = tempString.replaceFirst("GARDEN", "GDN");
            tempString = tempString.replaceFirst("GARDENS", "GDNS");
            tempString = tempString.replaceFirst("GLADE", "GLD");
            tempString = tempString.replaceFirst("GLEN", "GLEN");
            tempString = tempString.replaceFirst("GULLY", "GLY");
            tempString = tempString.replaceFirst("GROVE", "GR");
            tempString = tempString.replaceFirst("GRANGE", "GRA");
            tempString = tempString.replaceFirst("GREEN", "GRN");
            tempString = tempString.replaceFirst("GATE", "GTE");
            tempString = tempString.replaceFirst("GATEWAY", "GWY");
            tempString = tempString.replaceFirst("HILL", "HILL");
            tempString = tempString.replaceFirst("HOLLOW", "HLLW");
            tempString = tempString.replaceFirst("HARBOUR", "HRBR");
            tempString = tempString.replaceFirst("HEATH", "HTH");
            tempString = tempString.replaceFirst("HEIGHTS", "HTS");
            tempString = tempString.replaceFirst("HUB", "HUB");
            tempString = tempString.replaceFirst("HAVEN", "HVN");
            tempString = tempString.replaceFirst("HIGHWAY", "HWY");
            tempString = tempString.replaceFirst("ISLAND", "ID");
            tempString = tempString.replaceFirst("JUNCTION", "JNC");
            tempString = tempString.replaceFirst("KEY", "KEY");
            tempString = tempString.replaceFirst("KEYS", "KEYS");
            tempString = tempString.replaceFirst("LANE", "LANE");
            tempString = tempString.replaceFirst("LANDING", "LDG");
            tempString = tempString.replaceFirst("LINE", "LINE");
            tempString = tempString.replaceFirst("LINK", "LINK");
            tempString = tempString.replaceFirst("LOOKOUT", "LKT");
            tempString = tempString.replaceFirst("LANEWAY", "LNWY");
            tempString = tempString.replaceFirst("LOOP", "LOOP");
            tempString = tempString.replaceFirst("LYNNE", "LYNN");
            tempString = tempString.replaceFirst("MALL", "MALL");
            tempString = tempString.replaceFirst("MANOR", "MANR");
            tempString = tempString.replaceFirst("MEAD", "MEAD");
            tempString = tempString.replaceFirst("MEWS", "MEWS");
            tempString = tempString.replaceFirst("MEANDER", "MNDR");
            tempString = tempString.replaceFirst("MOTORWAY", "MWY");
            tempString = tempString.replaceFirst("NOOK", "NOOK");
            tempString = tempString.replaceFirst("OUTLOOK", "OTLK");
            tempString = tempString.replaceFirst("OUTLET", "OTLT");
            tempString = tempString.replaceFirst("PARK", "PARK");
            tempString = tempString.replaceFirst("PASS", "PASS");
            tempString = tempString.replaceFirst("PATH", "PATH");
            tempString = tempString.replaceFirst("PARADE", "PDE");
            tempString = tempString.replaceFirst("PATHWAY", "PHWY");
            tempString = tempString.replaceFirst("POCKET", "PKT");
            tempString = tempString.replaceFirst("PARKWAY", "PKWY");
            tempString = tempString.replaceFirst("PLACE", "PL");
            tempString = tempString.replaceFirst("PLAZA", "PLZA");
            tempString = tempString.replaceFirst("POINT", "PNT");
            tempString = tempString.replaceFirst("PORT", "PORT");
            tempString = tempString.replaceFirst("PRECINCT", "PREC");
            tempString = tempString.replaceFirst("PROMENADE", "PROM");
            tempString = tempString.replaceFirst("PURSUIT", "PRST");
            tempString = tempString.replaceFirst("PASSAGE", "PSGE");
            tempString = tempString.replaceFirst("QUADRANT", "QDRT");
            tempString = tempString.replaceFirst("QUAY", "QY");
            tempString = tempString.replaceFirst("QUAYS", "QYS");
            tempString = tempString.replaceFirst("RAMP", "RAMP");
            tempString = tempString.replaceFirst("REACH", "RCH");
            tempString = tempString.replaceFirst("ROAD", "RD");
            tempString = tempString.replaceFirst("RIDGE", "RDGE");
            tempString = tempString.replaceFirst("ROADS", "RDS");
            tempString = tempString.replaceFirst("ROADWAY", "RDWY");
            tempString = tempString.replaceFirst("RESERVE", "RES");
            tempString = tempString.replaceFirst("REST", "REST");
            tempString = tempString.replaceFirst("RIDE", "RIDE");
            tempString = tempString.replaceFirst("RISE", "RISE");
            tempString = tempString.replaceFirst("RAMBLE", "RMBL");
            tempString = tempString.replaceFirst("ROUND", "RND");
            tempString = tempString.replaceFirst("ROW", "ROW");
            tempString = tempString.replaceFirst("RISING", "RSNG");
            tempString = tempString.replaceFirst("ROUTE", "RTE");
            tempString = tempString.replaceFirst("RETURN", "RTN");
            tempString = tempString.replaceFirst("RETREAT", "RTT");
            tempString = tempString.replaceFirst("RUN", "RUN");
            tempString = tempString.replaceFirst("RIVER", "RVR");
            tempString = tempString.replaceFirst("SUBWAY", "SBWY");
            tempString = tempString.replaceFirst("SKYLINE", "SKLN");
            tempString = tempString.replaceFirst("SLOPE", "SLPE");
            tempString = tempString.replaceFirst("SPUR", "SPUR");
            tempString = tempString.replaceFirst("SQUARE", "SQ");
            tempString = tempString.replaceFirst("STREET", "ST");
            tempString = tempString.replaceFirst("STRAIT", "STAI");
            tempString = tempString.replaceFirst("STEPS", "STPS");
            tempString = tempString.replaceFirst("STRIP", "STRP");
            tempString = tempString.replaceFirst("STRAIGHT", "STRT");
            tempString = tempString.replaceFirst("TARN", "TARN");
            tempString = tempString.replaceFirst("TERRACE", "TCE");
            tempString = tempString.replaceFirst("THROUGHWAY", "THRU");
            tempString = tempString.replaceFirst("TRUNKWAY", "TKWY");
            tempString = tempString.replaceFirst("TOP", "TOP");
            tempString = tempString.replaceFirst("TOR", "TOR");
            tempString = tempString.replaceFirst("TRACK", "TRK");
            tempString = tempString.replaceFirst("TRAIL", "TRL");
            tempString = tempString.replaceFirst("TURN", "TURN");
            tempString = tempString.replaceFirst("TWIST", "TWIST");
            tempString = tempString.replaceFirst("VALE", "VALE");
            tempString = tempString.replaceFirst("VIEW", "VIEW");
            tempString = tempString.replaceFirst("VILLA", "VLLA");
            tempString = tempString.replaceFirst("VALLEY", "VLLY");
            tempString = tempString.replaceFirst("VISTA", "VSTA");
            tempString = tempString.replaceFirst("VIEWS", "VWS");
            tempString = tempString.replaceFirst("WALK", "WALK");
            tempString = tempString.replaceFirst("WAY", "WAY");
            tempString = tempString.replaceFirst("WOODS", "WDS");
            tempString = tempString.replaceFirst("WHARF", "WHRF");
            tempString = tempString.replaceFirst("WALKWAY", "WKWY");
            tempString = tempString.replaceFirst("WATERS", "WTRS");
            tempString = tempString.replaceFirst("WATERWAY", "WTWY");
            tempString = tempString.replaceFirst("WYND", "WYND");
            returnRoadString[returnRoadString.length - 1] = tempString;
        }
        return String.join(" ", returnRoadString);
    }

}