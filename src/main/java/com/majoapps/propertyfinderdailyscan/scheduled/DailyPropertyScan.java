package com.majoapps.propertyfinderdailyscan.scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.majoapps.propertyfinderdailyscan.business.domain.DomainTokenAuthResponse;
import com.majoapps.propertyfinderdailyscan.business.domain.PropertyListingDTO;
import com.majoapps.propertyfinderdailyscan.business.domain.PropertySearchCommercialRequest;
import com.majoapps.propertyfinderdailyscan.business.domain.PropertySearchRequest;
import com.majoapps.propertyfinderdailyscan.business.domain.SearchLocations;
import com.majoapps.propertyfinderdailyscan.business.service.DomainAuthentication;
import com.majoapps.propertyfinderdailyscan.business.service.DomainListingService;
import com.majoapps.propertyfinderdailyscan.business.service.PropertyListingService;
import com.majoapps.propertyfinderdailyscan.data.entity.PropertyListing;
import com.majoapps.propertyfinderdailyscan.utils.DateHelper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DailyPropertyScan {

    private final PropertyListingService propertyListingService;
    private final DomainListingService domainListingService;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private static final DateHelper dateHelper = new DateHelper();
    private static final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public DailyPropertyScan(PropertyListingService propertyListingService, DomainListingService domainListingService) {
        this.propertyListingService = propertyListingService;
        this.domainListingService = domainListingService;
    }

    private String authToken = "";
    private PropertyListingDTO[] propertyListings = null;
    private PropertySearchRequest searchJson;
    private PropertySearchCommercialRequest searchJsonCommercial;
    private Integer domainKey = 0;
    private Integer domainSearchCount = 0;
    private String[] authKey = {
            System.getenv().get("DOMAIN_KEY_0"),
            System.getenv().get("DOMAIN_KEY_1"),
            System.getenv().get("DOMAIN_KEY_2"),
            System.getenv().get("DOMAIN_KEY_3"),
            System.getenv().get("DOMAIN_KEY_4"),
            System.getenv().get("DOMAIN_KEY_5")
    };

    @Scheduled(fixedRate = 3600000000L) // Run every hour  3600000L
    public void getListingsNSW() throws Exception {
        log.debug("Business Day Check {}", dateFormat.format(new Date()));
        if (!dateHelper.isBusinessDay()) {
            log.info("Schduled run of getListing {}", dateFormat.format(new Date()));
            domainKey = 2;
            getListingsResidentialNSW();
        }
    }

    private void getListingsResidentialNSW() throws Exception {
        Integer price;
        Integer priceStart = 100000;
        Integer priceIncrementAmount;
        Integer priceIncrementAmountSmall = 40000;
        Integer priceIncrementAmountSmallSydney = 100000;
        Integer priceIncrementAmountSmallRegional = 20000;
        Integer priceIncrementAmountMedium = 200000;
        Integer priceIncrementAmountLarge = 1000000;
        Integer priceStop = 5000000;
        Integer minLandSize = 400;
        String[] propertyTypes = new String[]{"DevelopmentSite", "House", "NewLand", "VacantLand"};

        getDomainAuth(domainKey);
        log.debug("Get Domain Key {} ", domainKey);

        PropertySearchRequest.Locations sydneyRegion = new PropertySearchRequest.Locations();
        sydneyRegion.state = "NSW";
        sydneyRegion.region = "Sydney Region";
        PropertySearchRequest.Locations illawarraSouthCoast = new PropertySearchRequest.Locations();
        illawarraSouthCoast.state = "NSW";
        illawarraSouthCoast.region = "Illawarra & South Coast";
        PropertySearchRequest.Locations hunterCentralNorthCoasts = new PropertySearchRequest.Locations();
        hunterCentralNorthCoasts.state = "NSW";
        hunterCentralNorthCoasts.region = "Hunter, Central & North Coasts";
        PropertySearchRequest.Locations regionalNSW = new PropertySearchRequest.Locations();
        regionalNSW.state = "NSW";
        regionalNSW.region = "Regional NSW";
        PropertySearchRequest.Locations[] locations = new PropertySearchRequest.Locations[]
                {sydneyRegion};

        for (int k = 0; k < locations.length; k++) {
            log.debug("Location {} ", locations[k].region);
            PropertySearchRequest propertySearchRequest = new PropertySearchRequest();
            price = priceStart;

            while (price <= priceStop) {
                if (price < 1000000 && locations[k].region.equals(regionalNSW.region)) {
                    priceIncrementAmount = priceIncrementAmountSmallRegional;
                } else if (price < 1000000 && locations[k].region.equals(sydneyRegion.region)) {
                    priceIncrementAmount = priceIncrementAmountSmallSydney;
                } else if (price < 1000000) {
                    priceIncrementAmount = priceIncrementAmountSmall;
                } else if (price < 2000000){
                    priceIncrementAmount = priceIncrementAmountMedium;
                } else {
                    priceIncrementAmount = priceIncrementAmountLarge;
                }
                propertySearchRequest.minPrice = price;
                propertySearchRequest.maxPrice = price + priceIncrementAmount;
                propertySearchRequest.minLandArea = minLandSize;
                propertySearchRequest.propertyTypes = propertyTypes;
                propertySearchRequest.locations = new PropertySearchRequest.Locations[]{locations[k]};
                searchJson = new SearchLocations().NSW(propertySearchRequest);
                propertySearchRequest.page = 1;
                if (domainSearchCount <= 450) {
                    propertyListings = getDomainListing();
                } else {
                    domainKey++;
                    if (domainKey >= authKey.length){
                        domainKey = 1;
                    }
                    log.debug("Domain Key {} ", domainKey);
                    getDomainAuth(domainKey);
                    domainSearchCount = 0;
                    propertyListings = getDomainListing();
                }

                if (propertyListings != null && propertyListings.length > 0){
                    log.debug("{} Pages 1 {}", price, propertyListings.length);
                    for (int l = 0; l < propertyListings.length; l++) {
                        PropertyListing propertyListing = modelMapper.map(propertyListings[l], PropertyListing.class);
                        System.out.println(propertyListing.toString());
                        //propertyListingService.savePropertyListing(propertyListing);
                    }
                    
                    
                }
                int i = 1;
                while (propertyListings != null && propertyListings.length >= 200 && i < 5) {
                    i++;
                    propertySearchRequest.page = i;
                    propertyListings = getDomainListing();
                    if (propertyListings != null && propertyListings.length > 0) {
                        log.debug("{} Pages {} {}", price, i, propertyListings.length);
                        //propertyListingService.savePropertyListing();
                    }
                }
                price += priceIncrementAmount;
            }
        }


    }

    private void getDomainAuth(Integer key) throws Exception {
        DomainAuthentication domainAuthentication = new DomainAuthentication();
        DomainTokenAuthResponse domainTokenAuthResponse = domainAuthentication.getAuthToken(authKey[key]);
        authToken = domainTokenAuthResponse.access_token;
    }

    private PropertyListingDTO[] getDomainListing() throws Exception {
        domainSearchCount++;
        return (domainListingService.getPropertyList(authToken, searchJson));
    }
   
}
