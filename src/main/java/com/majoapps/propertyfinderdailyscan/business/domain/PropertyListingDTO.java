package com.majoapps.propertyfinderdailyscan.business.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PropertyListingDTO {
    public String timeDate;
    public Integer domainListingId;
    public String price;
    public Integer priceInt;
    public String listingURL;
    public String listingPhoto;
    public String address;
    public String unitNumber;
    public String houseNumber;
    public String streetName;
    public String suburbName;
    public String postCode;
    public Integer area;
    public String zone;
    public BigDecimal floorSpaceRatio;
    public Integer landValue;
    public Double bathrooms;
    public Double bedrooms;
    public Integer carspaces;
    public Double latitude;
    public Double longitude;
    public String summaryDescription;
    public String planningPortalPropId;
    public String planningPortalAddress;
    public Integer pricePSM;
    public BigDecimal priceToLandValue;
}
