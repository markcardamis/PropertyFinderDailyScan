package com.majoapps.propertyfinderdailyscan.business.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.majoapps.propertyfinderdailyscan.business.domain.PropertyListingDTO;
import com.majoapps.propertyfinderdailyscan.business.domain.PropertyListingCommercialResponse;
import com.majoapps.propertyfinderdailyscan.business.domain.PropertyListingResponse;
import com.majoapps.propertyfinderdailyscan.business.domain.PropertySearchCommercialRequest;
import com.majoapps.propertyfinderdailyscan.business.domain.PropertySearchRequest;
import com.majoapps.propertyfinderdailyscan.utils.HttpMethod;
import com.majoapps.propertyfinderdailyscan.utils.IServiceHelper;
import com.majoapps.propertyfinderdailyscan.utils.PriceMethods;
import com.majoapps.propertyfinderdailyscan.utils.ServiceHelper;
import com.majoapps.propertyfinderdailyscan.utils.StringCheck;

import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Service
public class DomainListingService implements IDomainListingService
{
    private IServiceHelper mServiceHelper;// = new IServiceHelper();

    public DomainListingService() throws Exception {
        mServiceHelper = new ServiceHelper();
    }

    @Override
    public PropertyListingDTO[] getPropertyList(String authToken, PropertySearchRequest request) throws Exception{

        PropertyListingDTO[] propertyListings = null;

        SimpleDateFormat ISO_8601_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss'Z'", new Locale("AU"));
        String timeDate = ISO_8601_FORMAT.format(new Date());

        //Get Listing Property
        String urlListings = "https://api.domain.com.au/v1/listings/residential/_search";
        Gson gsonRequest = new GsonBuilder().disableHtmlEscaping().create();
        String requestData = gsonRequest.toJson(request);
        String responseJson = mServiceHelper.callHTTPService(urlListings, HttpMethod.POST, requestData, false, authToken);
        Gson gson = new Gson();
        PropertyListingResponse[] propertyListingResponse = gson.fromJson(responseJson, PropertyListingResponse[].class);
        if (propertyListingResponse.length > 0){
            propertyListings = new PropertyListingDTO[propertyListingResponse.length];
            for (int i = 0; i < propertyListingResponse.length; i++){
                propertyListings[i] = new PropertyListingDTO();
                if (propertyListingResponse[i].type.equals("PropertyListing")) {
                    propertyListings[i].timeDate = timeDate;
                    propertyListings[i].domainListingId = propertyListingResponse[i].listing.id;
                    propertyListings[i].address = StringCheck.isNotNullOrEmpty(propertyListingResponse[i].listing.propertyDetails.streetNumber, " ") +
                            StringCheck.isNotNullOrEmpty(propertyListingResponse[i].listing.propertyDetails.street, " ").toUpperCase(Locale.ENGLISH) +
                            StringCheck.isNotNullOrEmpty(propertyListingResponse[i].listing.propertyDetails.suburb, " ").toUpperCase(Locale.ENGLISH) +
                            propertyListingResponse[i].listing.propertyDetails.postcode;
                    propertyListings[i].unitNumber = StringCheck.isNotNullOrEmpty(propertyListingResponse[i].listing.propertyDetails.unitNumber , "");
                    propertyListings[i].houseNumber = StringCheck.isNotNullOrEmpty(propertyListingResponse[i].listing.propertyDetails.streetNumber, "");
                    propertyListings[i].streetName = StringCheck.isNotNullOrEmpty(propertyListingResponse[i].listing.propertyDetails.street, "");
                    propertyListings[i].suburbName = StringCheck.isNotNullOrEmpty(propertyListingResponse[i].listing.propertyDetails.suburb, "");
                    propertyListings[i].area = propertyListingResponse[i].listing.propertyDetails.landArea;
                    propertyListings[i].postCode = propertyListingResponse[i].listing.propertyDetails.postcode;
                    propertyListings[i].price = propertyListingResponse[i].listing.priceDetails.displayPrice;
                    propertyListings[i].listingURL = "https://www.domain.com.au/" +
                                propertyListingResponse[i].listing.listingSlug;
                    propertyListings[i].summaryDescription = Jsoup.parse(propertyListingResponse[i].listing.summaryDescription.toLowerCase()).text();
                    propertyListings[i].lat = propertyListingResponse[i].listing.propertyDetails.latitude;
                    propertyListings[i].lng = propertyListingResponse[i].listing.propertyDetails.longitude;
                    propertyListings[i].bathrooms = propertyListingResponse[i].listing.propertyDetails.bathrooms;
                    propertyListings[i].bedrooms = propertyListingResponse[i].listing.propertyDetails.bedrooms;
                    propertyListings[i].carspaces = propertyListingResponse[i].listing.propertyDetails.carspaces;
                } else {
                    propertyListings[i].domainListingId = 0;
                    propertyListings[i].address = "1 Sydney St SYDNEY 2000";
                    propertyListings[i].area = 1;
                    propertyListings[i].postCode = "2000";
                    propertyListings[i].price = "$2000000";
                    propertyListings[i].listingURL = "https://www.domain.com.au/";
                    propertyListings[i].summaryDescription = "Fake Address";
                }
            }
        }
        return propertyListings;
    }

    @Override
    public PropertyListingDTO[] getPropertyList(String authToken, PropertySearchCommercialRequest request) throws Exception{

        PropertyListingDTO[] propertyListings = null;

        SimpleDateFormat ISO_8601_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss'Z'", new Locale("AU"));
        String timeDate = ISO_8601_FORMAT.format(new Date());

        //Get Listing Property
        String urlListings = "https://api.domain.com.au/v1/listings/commercial/_search";
        Gson gsonRequest = new GsonBuilder().disableHtmlEscaping().create();
        String requestData = gsonRequest.toJson(request);
        String responseJson = mServiceHelper.callHTTPService(urlListings, HttpMethod.POST, requestData, false, authToken);
        Gson gson = new Gson();
        PropertyListingCommercialResponse[] propertyListingResponse = gson.fromJson(responseJson, PropertyListingCommercialResponse[].class);
        if (propertyListingResponse.length > 0){
            propertyListings = new PropertyListingDTO[propertyListingResponse.length];
            for (int i = 0; i < propertyListingResponse.length; i++){
                propertyListings[i] = new PropertyListingDTO();
                propertyListings[i].timeDate = timeDate;
                propertyListings[i].domainListingId = propertyListingResponse[i].id;
                propertyListings[i].address = StringCheck.isNotNullOrEmpty(propertyListingResponse[i].metadata.addressComponents.streetNumber, " ") +
                        StringCheck.isNotNullOrEmpty(propertyListingResponse[i].metadata.addressComponents.street, " ").toUpperCase(Locale.ENGLISH) +
                        StringCheck.isNotNullOrEmpty(propertyListingResponse[i].metadata.addressComponents.suburb, " ").toUpperCase(Locale.ENGLISH) +
                        propertyListingResponse[i].metadata.addressComponents.postcode;
                propertyListings[i].area = PriceMethods.convertStringToInteger(propertyListingResponse[i].propertyArea);
                propertyListings[i].postCode = propertyListingResponse[i].metadata.addressComponents.postcode;
                propertyListings[i].price = propertyListingResponse[i].price;
                propertyListings[i].listingURL = propertyListingResponse[i].ad.url;
                propertyListings[i].summaryDescription = propertyListingResponse[i].headline;
                propertyListings[i].lat = propertyListingResponse[i].geoLocation.latitude;
                propertyListings[i].lng = propertyListingResponse[i].geoLocation.longitude;
            }
        }
        return propertyListings;
    }

}
