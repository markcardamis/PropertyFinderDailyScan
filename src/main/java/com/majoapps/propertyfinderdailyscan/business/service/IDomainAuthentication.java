package com.majoapps.propertyfinderdailyscan.business.service;

import com.majoapps.propertyfinderdailyscan.business.domain.DomainTokenAuthResponse;

public interface IDomainAuthentication {
    DomainTokenAuthResponse getAuthToken(String authKey) throws Exception;
}
