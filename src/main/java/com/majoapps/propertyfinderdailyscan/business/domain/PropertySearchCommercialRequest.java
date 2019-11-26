package com.majoapps.propertyfinderdailyscan.business.domain;

import com.google.gson.annotations.SerializedName;

public class PropertySearchCommercialRequest {
    public Integer advertiserId;
    public Integer page;
    public Integer pageSize;
    public String[] propertyTypes;
    public PriceSearch price;
    public LocationSearch[] locations;
    public String[] keywords;
    public GeoWindow geoWindow;

    public Integer landAreaMin;
    public Integer landAreaMax;
    public Integer buildingSizeMin;
    public Integer buildingSizeMax;

    public String searchMode;       // "forSale" "forLease" "sold" "leased"
    public String occupancy;
    public String sort;             // "default" "newestFirst" "cheapestTotalFirst" "cheapestPerSqmFirst" "mostExpensiveTotalFirst" "mostExpensivePerSqmFirst" "suburbAsc" "suburbDesc" "buildingSizeAsc" "buildingSizeDesc"
    public String saleType;         // "standardSale" "auction" "expressionOfInterest" "tender"
    public String propertyTitle;    // "freehold" "strata" "noBuilding"
    public ParkingSearch parking;
    public String[] exclusionTypes;
    public Integer annualReturn;    // Minimum annual return (in percents)

    public static class PriceSearch {
        @SerializedName("min") public Integer min;
        @SerializedName("max") public Integer max;
        @SerializedName("type") public String type;     // "totalAmount" "perSqm"
    }

    public static class LocationSearch {
        @SerializedName("state") public String state;
        @SerializedName("region") public String region;
        @SerializedName("area") public String area;
        @SerializedName("suburb") public String suburb;
        @SerializedName("street") public String street;
        @SerializedName("postCode") public String postCode;
    }

    public static class GeoWindow {
        @SerializedName("polygon") public GeoPoint[] polygon;
        @SerializedName("boundingBox") public GeoPoint[] boundingBox;
    }

    public static class ParkingSearch {
        @SerializedName("type") public String type;     // "onSite" "onStreet" "noParking"
        @SerializedName("carspaces") public Integer carspaces;
    }

    public static class GeoPoint {
        @SerializedName("lat") public Double lat;
        @SerializedName("long") public Double lng;
    }
}
