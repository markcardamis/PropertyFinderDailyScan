package com.majoapps.propertyfinderdailyscan.business.service;

import java.util.List;
import com.majoapps.propertyfinderdailyscan.business.domain.PropertyListingDTO;

public interface IPlanningPortalZoneSearch {
    List<PropertyListingDTO> addPlanningZone(List<PropertyListingDTO> propertyListings) throws Exception;
}
