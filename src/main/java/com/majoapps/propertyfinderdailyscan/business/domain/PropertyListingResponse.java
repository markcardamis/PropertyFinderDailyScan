package com.majoapps.propertyfinderdailyscan.business.domain;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("unused")
public class PropertyListingResponse {
    public String type;
    public Listing listing;

    public static class Contact implements Serializable {
        public String name;
        private final static long serialVersionUID = -5022412230273243656L;
    }

    public static class InspectionSchedule implements Serializable {
        public Boolean byAppointment;
        public Boolean recurring;
        public List<Time> times = null;
        private final static long serialVersionUID = 2034955797176831288L;
    }

    public static class Listing implements Serializable {
        public String listingType;
        public Integer id;
        public Advertiser advertiser;
        public PriceDetails priceDetails;
        public List<Medium> media = null;
        public PropertyDetails propertyDetails;
        public String headline;
        public String summaryDescription;
        public Boolean hasFloorplan;
        public Boolean hasVideo;
        public List<String> labels = null;
        public InspectionSchedule inspectionSchedule;
        public String listingSlug;
        private final static long serialVersionUID = 77938258309101L;
    }

    public static class Advertiser {
        private String id;
        private String bannerUrl;
        private String preferredColourHex;
        private String name;
        private String logoUrl;
        private String type;
        private Contact[] contacts;
    }


    public static class Medium implements Serializable {
        public String category;
        public String url;
        private final static long serialVersionUID = 4086544935120502407L;
    }

    public static class PriceDetails implements Serializable {
        public String displayPrice;
        private final static long serialVersionUID = -9198532097364411366L;
    }

    public static class PropertyDetails implements Serializable {
        public String state;
        public List<String> features = null;
        public String propertyType;
        public List<String> allPropertyTypes = null;
        public Integer bathrooms;
        public Integer bedrooms;
        public Integer carspaces;
        public String unitNumber;
        public String streetNumber;
        public String street;
        public String area;
        public String region;
        public String suburb;
        public String postcode;
        public String displayableAddress;
        public Double latitude;
        public Double longitude;
        public Integer landArea;
        private final static long serialVersionUID = 6865077896891495080L;
    }

    public static class Time implements Serializable {
        public String openingTime;
        public String closingTime;
        private final static long serialVersionUID = -6178999153235331681L;
    }
}
