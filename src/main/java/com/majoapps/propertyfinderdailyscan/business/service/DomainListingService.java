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
import lombok.extern.slf4j.Slf4j;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Slf4j
@Service
public class DomainListingService implements IDomainListingService
{
    private IServiceHelper mServiceHelper;// = new IServiceHelper();
    private final SimpleDateFormat ISO_8601_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss'Z'", new Locale("AU"));
    private final String timeDate = ISO_8601_FORMAT.format(new Date());
    private final String imageDefaultUrl = "https://cdn.pixabay.com/photo/2013/07/12/14/47/house-148791_960_720.png";

    public DomainListingService() throws Exception {
        mServiceHelper = new ServiceHelper();
    }

    @Override
    public PropertyListingDTO[] getPropertyList(String authToken, PropertySearchRequest request) throws Exception{

        PropertyListingDTO[] propertyListings = null;

        //Get Listing Property
        String urlListings = "https://api.domain.com.au/v1/listings/residential/_search";
        Gson gsonRequest = new GsonBuilder().disableHtmlEscaping().create();
        String requestData = gsonRequest.toJson(request);
        String responseJson = mServiceHelper.callHTTPService(urlListings, HttpMethod.POST, requestData, false, authToken);
        Gson gson = new Gson();
        PropertyListingResponse[] propertyListingResponse = gson.fromJson(responseJson, PropertyListingResponse[].class);

        // Convert the Listing Property into the PropertyListingDTO
        if (propertyListingResponse.length > 0){
            propertyListings = new PropertyListingDTO[propertyListingResponse.length];
            for (int i = 0; i < propertyListingResponse.length; i++){
                propertyListings[i] = new PropertyListingDTO();
                try {
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
                        propertyListings[i].priceInt = PriceMethods.priceStringToInteger(propertyListingResponse[i].listing.priceDetails.displayPrice);
                        propertyListings[i].listingURL = "https://www.domain.com.au/" +
                                    propertyListingResponse[i].listing.listingSlug;
                        propertyListings[i].listingPhoto = ((propertyListingResponse[i].listing.media == null) || (propertyListingResponse[i].listing.media.isEmpty())
                                    ? imageDefaultUrl : propertyListingResponse[i].listing.media.get(0).url);
                        propertyListings[i].summaryDescription = Jsoup.parse(propertyListingResponse[i].listing.summaryDescription.toLowerCase()).text();
                        propertyListings[i].latitude = propertyListingResponse[i].listing.propertyDetails.latitude;
                        propertyListings[i].longitude = propertyListingResponse[i].listing.propertyDetails.longitude;
                        propertyListings[i].bathrooms = propertyListingResponse[i].listing.propertyDetails.bathrooms;
                        propertyListings[i].bedrooms = propertyListingResponse[i].listing.propertyDetails.bedrooms;
                        propertyListings[i].carspaces = propertyListingResponse[i].listing.propertyDetails.carspaces;
                    } else {
                        propertyListings[i].domainListingId = 0;
                    } 
                } catch (Exception e) {
                    log.error("DomainListingResidentialDTO : {} ", e.getLocalizedMessage());
                    propertyListings[i].domainListingId = 0;
                }
            }
        }
        return propertyListings;
    }

    @Override
    public PropertyListingDTO[] getPropertyList(String authToken, PropertySearchCommercialRequest request) throws Exception{

        PropertyListingDTO[] propertyListings = null;

        //Get Listing Property
        String urlListings = "https://api.domain.com.au/v1/listings/commercial/_search";
        Gson gsonRequest = new GsonBuilder().disableHtmlEscaping().create();
        String requestData = gsonRequest.toJson(request);
        String responseJson = mServiceHelper.callHTTPService(urlListings, HttpMethod.POST, requestData, false, authToken);
        Gson gson = new Gson();
        PropertyListingCommercialResponse[] propertyListingResponse = gson.fromJson(responseJson, PropertyListingCommercialResponse[].class);
        
        // Convert the Listing Property into the PropertyListingDTO
        if (propertyListingResponse.length > 0){
            propertyListings = new PropertyListingDTO[propertyListingResponse.length];
            for (int i = 0; i < propertyListingResponse.length; i++){
                propertyListings[i] = new PropertyListingDTO();
                try {
                    propertyListings[i].timeDate = timeDate;
                    propertyListings[i].domainListingId = propertyListingResponse[i].id;
                    propertyListings[i].address = StringCheck.isNotNullOrEmpty(propertyListingResponse[i].metadata.addressComponents.streetNumber, " ") +
                            StringCheck.isNotNullOrEmpty(propertyListingResponse[i].metadata.addressComponents.street, " ").toUpperCase(Locale.ENGLISH) +
                            StringCheck.isNotNullOrEmpty(propertyListingResponse[i].metadata.addressComponents.suburb, " ").toUpperCase(Locale.ENGLISH) +
                            propertyListingResponse[i].metadata.addressComponents.postcode;
                    propertyListings[i].unitNumber = StringCheck.isNotNullOrEmpty(propertyListingResponse[i].metadata.addressComponents.unitNumber, "");
                    propertyListings[i].houseNumber = StringCheck.isNotNullOrEmpty(propertyListingResponse[i].metadata.addressComponents.streetNumber, "");
                    propertyListings[i].streetName = StringCheck.isNotNullOrEmpty(propertyListingResponse[i].metadata.addressComponents.street, "");
                    propertyListings[i].suburbName = StringCheck.isNotNullOrEmpty(propertyListingResponse[i].metadata.addressComponents.suburb, "");
                    propertyListings[i].area = PriceMethods.convertStringToInteger(propertyListingResponse[i].propertyArea);
                    propertyListings[i].postCode = propertyListingResponse[i].metadata.addressComponents.postcode;
                    propertyListings[i].price = propertyListingResponse[i].price;
                    propertyListings[i].priceInt = PriceMethods.priceStringToInteger(propertyListingResponse[i].price);
                    propertyListings[i].listingURL = propertyListingResponse[i].ad.url;
                    propertyListings[i].listingPhoto = ((propertyListingResponse[i].media == null || (propertyListingResponse[i].media.isEmpty()))
                            ? imageDefaultUrl : propertyListingResponse[i].media.get(0).imageUrl);
                    propertyListings[i].summaryDescription = propertyListingResponse[i].headline;
                    propertyListings[i].latitude = propertyListingResponse[i].geoLocation.latitude;
                    propertyListings[i].longitude = propertyListingResponse[i].geoLocation.longitude;
                    propertyListings[i].bathrooms = 0.0;
                    propertyListings[i].bedrooms = 0.0;
                    propertyListings[i].carspaces = propertyListingResponse[i].carspaceCount;
                } catch (Exception e) {
                    log.error("DomainListingCommercialDTO : {} ", e.getLocalizedMessage());
                    propertyListings[i].domainListingId = 0;
                }
            }
        }
        return propertyListings;
    }

}
