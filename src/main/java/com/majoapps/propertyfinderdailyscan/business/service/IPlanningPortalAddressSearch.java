package com.majoapps.propertyfinderdailyscan.business.service;

import com.majoapps.propertyfinderdailyscan.business.domain.PropertyListingDTO;
import java.util.List;

public interface IPlanningPortalAddressSearch {
    List<PropertyListingDTO> getFormattedAddressMultiThreaded(List<PropertyListingDTO> propertyListings) throws Exception;
}
