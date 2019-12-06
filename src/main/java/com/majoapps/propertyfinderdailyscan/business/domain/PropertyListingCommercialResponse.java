package com.majoapps.propertyfinderdailyscan.business.domain;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class PropertyListingCommercialResponse implements Serializable {

    @SerializedName("ad")
    public Ad ad;
    @SerializedName("price")
    public String price;
    @SerializedName("advertiser")
    public Advertiser advertiser;
    @SerializedName("geoLocation")
    public GeoLocation geoLocation;
    @SerializedName("propertyArea")
    public String propertyArea;
    @SerializedName("propertyType")
    public String propertyType;
    @SerializedName("address")
    public String address;
    @SerializedName("headline")
    public String headline;
    @SerializedName("hasVideo")
    public Boolean hasVideo;
    @SerializedName("media")
    public List<Medium> media = null;
    @SerializedName("id")
    public Integer id;
    @SerializedName("metadata")
    public Metadata metadata;
    @SerializedName("carspaceCount")
    public Integer carspaceCount;
    private static final long serialVersionUID = 8206906958076690619L;

    public static class Ad implements Serializable {
        @SerializedName("adType")
        public String adType;
        @SerializedName("url")
        public String url;
        private final static long serialVersionUID = -4314013000550650471L;
    }

    public static class AddressComponents implements Serializable {
        @SerializedName("area")
        public String area;
        @SerializedName("postcode")
        public String postcode;
        @SerializedName("region")
        public String region;
        @SerializedName("stateShort")
        public String stateShort;
        @SerializedName("street")
        public String street;
        @SerializedName("streetNumber")
        public String streetNumber;
        @SerializedName("suburb")
        public String suburb;
        @SerializedName("unitNumber")
        public String unitNumber;
        private final static long serialVersionUID = 6641252051390069380L;
    }

    public static class Advertiser implements Serializable {
        @SerializedName("address")
        public String address;
        @SerializedName("id")
        public Integer id;
        @SerializedName("name")
        public String name;
        @SerializedName("preferredColorHex")
        public String preferredColorHex;
        @SerializedName("images")
        public Images images;
        @SerializedName("contacts")
        public List<Contact> contacts = null;
        @SerializedName("isConjunctional")
        public Boolean isConjunctional;
        private final static long serialVersionUID = -3586637282094391250L;
    }

    public static class Contact implements Serializable {
        @SerializedName("id")
        public Integer id;
        @SerializedName("imageUrl")
        public String imageUrl;
        @SerializedName("displayFullName")
        public String displayFullName;
        @SerializedName("phoneNumbers")
        public List<PhoneNumber> phoneNumbers = null;
        private final static long serialVersionUID = 7485400801136187038L;
    }

    public static class GeoLocation implements Serializable {
        @SerializedName("latitude")
        public Double latitude;
        @SerializedName("longitude")
        public Double longitude;
        private final static long serialVersionUID = 2578205340423136821L;
    }

    public static class Images implements Serializable {
        @SerializedName("agencyBannerImageUrl")
        public String agencyBannerImageUrl;
        @SerializedName("logoUrl")
        public String logoUrl;
        private final static long serialVersionUID = 1851881450988601290L;
    }

    public class Medium implements Serializable {
        @SerializedName("imageUrl")
        public String imageUrl;
        @SerializedName("mediaType")
        public String mediaType;
        @SerializedName("type")
        public String type;
        private final static long serialVersionUID = -1926809761908860833L;
    }

    public static class Metadata implements Serializable {
        @SerializedName("addressComponents")
        public AddressComponents addressComponents;
        private final static long serialVersionUID = -1525504254975636719L;
    }

    public static class PhoneNumber implements Serializable {
        @SerializedName("displayLabel")
        public String displayLabel;
        @SerializedName("type")
        public String type;
        @SerializedName("number")
        public String number;
        private final static long serialVersionUID = 2420252397499197523L;
    }
}


