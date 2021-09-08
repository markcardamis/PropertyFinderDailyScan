package com.majoapps.propertyfinderdailyscan.business.domain;

import org.apache.commons.lang3.ArrayUtils;
import java.time.LocalDate;
import java.util.Optional;

public class SearchLocations {

    public PropertySearchRequest NSW(PropertySearchRequest propertySearchRequest) {
        PropertySearchRequest searchJson = new PropertySearchRequest();
        searchJson.listingType =  Optional.ofNullable(propertySearchRequest.listingType).orElse("Sale");
        searchJson.propertyTypes = Optional.ofNullable(propertySearchRequest.propertyTypes).orElse(new String[]{"DevelopmentSite","VacantLand"});
        searchJson.listingAttributes = Optional.ofNullable(propertySearchRequest.listingAttributes).orElse(new String[]{"NotUnderContract"});
        searchJson.minLandArea = Optional.ofNullable(propertySearchRequest.minLandArea).orElse(720);
        searchJson.minPrice = Optional.ofNullable(propertySearchRequest.minPrice).orElse(200000);
        searchJson.maxPrice = Optional.ofNullable(propertySearchRequest.maxPrice).orElse(250000);
        searchJson.page = Optional.ofNullable(propertySearchRequest.page).orElse(1);
        searchJson.pageSize = Optional.ofNullable(propertySearchRequest.pageSize).orElse(200);
        searchJson.dateUpdated = Optional.ofNullable(propertySearchRequest.dateUpdated).orElse(LocalDate.now().toString());

        PropertySearchRequest.Locations locations = new PropertySearchRequest.Locations();
        locations.state = "NSW"; // default value

        if (propertySearchRequest.locations == null) {
            searchJson.locations = new PropertySearchRequest.Locations[]{locations};
        } else {
            PropertySearchRequest.Locations[] locationsArray = {locations};
            for (int i = 0; i < propertySearchRequest.locations.length; i++){
                locations.state = Optional.ofNullable(propertySearchRequest.locations[i].state).orElse("NSW");
                locations.region = Optional.ofNullable(propertySearchRequest.locations[i].region).orElse("");
                locations.area = Optional.ofNullable(propertySearchRequest.locations[i].area).orElse("");
                locations.suburb = Optional.ofNullable(propertySearchRequest.locations[i].suburb).orElse("");
                locations.postCode = Optional.ofNullable(propertySearchRequest.locations[i].postCode).orElse("");
                locations.includeSurroundingSuburbs = Optional.ofNullable(propertySearchRequest.locations[i].includeSurroundingSuburbs).orElse(false);
                locationsArray = ArrayUtils.insert(0, locationsArray, locations);
            }
            searchJson.locations = locationsArray;
        }

        return searchJson;
    }



}
