package com.majoapps.propertyfinderdailyscan.business.service;

import com.majoapps.propertyfinderdailyscan.business.domain.PropertyListingDTO;
import java.util.List;

public interface IPlanningPortalZoneSearch {
    List<PropertyListingDTO> addPlanningZone(List<PropertyListingDTO> propertyListings) throws Exception;
}
