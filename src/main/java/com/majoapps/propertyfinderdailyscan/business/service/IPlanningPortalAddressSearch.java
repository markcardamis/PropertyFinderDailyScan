package com.majoapps.propertyfinderdailyscan.business.service;

import com.majoapps.propertyfinderdailyscan.business.domain.PropertyListingDTO;

public interface IPlanningPortalAddressSearch {
    PropertyListingDTO[] getFormattedAddress(PropertyListingDTO[] propertyListings) throws Exception;
    PropertyListingDTO[] getFormattedAddressMultiThreaded(PropertyListingDTO[] propertyListings) throws Exception;
}
