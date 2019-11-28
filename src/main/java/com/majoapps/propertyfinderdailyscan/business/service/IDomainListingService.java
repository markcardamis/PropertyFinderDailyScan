package com.majoapps.propertyfinderdailyscan.business.service;

import com.majoapps.propertyfinderdailyscan.business.domain.PropertyListingDTO;
import com.majoapps.propertyfinderdailyscan.business.domain.PropertySearchCommercialRequest;
import com.majoapps.propertyfinderdailyscan.business.domain.PropertySearchRequest;

public interface IDomainListingService {
    PropertyListingDTO[] getPropertyList(String authKey, PropertySearchRequest request) throws Exception;
    PropertyListingDTO[] getPropertyList(String authKey, PropertySearchCommercialRequest request) throws Exception;
}
