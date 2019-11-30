package com.majoapps.propertyfinderdailyscan.business.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PropertyListingDTO {
    public String timeDate;
    public Integer domainListingId;
    public String listingURL;
    public String address;
    public String unitNumber;
    public String houseNumber;
    public String streetName;
    public String suburbName;
    public String postCode;
    public Integer area;
    public String price;
    public String planningPortalPropId;
    public String planningPortalAddress;
    public String summaryDescription;
    public Double lat;
    public Double lng;
    public Double bathrooms;
    public Double bedrooms;
    public Integer carspaces;
}
