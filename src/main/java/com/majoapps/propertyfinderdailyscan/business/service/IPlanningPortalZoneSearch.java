package com.majoapps.propertyfinderdailyscan.business.service;

import com.majoapps.propertyfinderdailyscan.business.domain.PropertyListing;

public interface IPlanningPortalZoneSearch {
    PropertyListing getSinglePlanningZone(PropertyListing propertyListings) throws Exception;
}
