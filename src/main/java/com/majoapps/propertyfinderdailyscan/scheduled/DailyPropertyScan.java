package com.majoapps.propertyfinderdailyscan.scheduled;

import com.majoapps.propertyfinderdailyscan.business.domain.PropertyListingDTO;
import com.majoapps.propertyfinderdailyscan.business.domain.PropertySearchCommercialRequest;
import com.majoapps.propertyfinderdailyscan.business.domain.PropertySearchRequest;
import com.majoapps.propertyfinderdailyscan.business.domain.SearchLocations;
import com.majoapps.propertyfinderdailyscan.business.service.IDomainAuthentication;
import com.majoapps.propertyfinderdailyscan.business.service.IDomainListingService;
import com.majoapps.propertyfinderdailyscan.business.service.PlanningPortalAddressSearch;
import com.majoapps.propertyfinderdailyscan.business.service.PropertyInformationService;
import com.majoapps.propertyfinderdailyscan.business.service.PropertyListingService;
import com.majoapps.propertyfinderdailyscan.data.entity.PropertyInformation;
import com.majoapps.propertyfinderdailyscan.data.entity.PropertyListing;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@ToString
@Slf4j
@Component
public class DailyPropertyScan {
    private final static int PAGE_SIZE = 200;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private static final ModelMapper modelMapper = new ModelMapper();
    private List<PropertyListingDTO> propertyListingList = new ArrayList<>();

    private final PropertyListingService propertyListingService;
    private final PropertyInformationService propertyInformationService;
    private final IDomainListingService domainListingService;
    private final IDomainAuthentication domainAuthentication;
    private final PlanningPortalAddressSearch planningPortalAddressSearch;

    @Autowired
    public DailyPropertyScan(PropertyListingService propertyListingService, 
            PropertyInformationService propertyInformationService,
            IDomainListingService domainListingService, 
            IDomainAuthentication domainAuthentication, 
            PlanningPortalAddressSearch planningPortalAddressSearch) {
        this.propertyListingService = propertyListingService;
        this.propertyInformationService = propertyInformationService;
        this.domainListingService = domainListingService;
        this.domainAuthentication = domainAuthentication;
        this.planningPortalAddressSearch = planningPortalAddressSearch;
    }

    private final String[] authKey = {
        System.getenv().get("DOMAIN_KEY_0"),
        System.getenv().get("DOMAIN_KEY_1"),
        System.getenv().get("DOMAIN_KEY_2"),
        System.getenv().get("DOMAIN_KEY_3"),
        System.getenv().get("DOMAIN_KEY_4"),
        System.getenv().get("DOMAIN_KEY_5")
    };
    private String authTokenString = "";
    
    private Integer domainKey = 0;
    private Integer domainSearchCount = 0;
    

    @Async
    public void getListingsNSW() throws Exception {
        log.debug("Business Day Check {}", dateFormat.format(new Date()));
        propertyListingService.deleteAll();
        log.info("Schduled run of getListing {}", dateFormat.format(new Date()));
        if (System.getenv().get("DOMAIN_KEY") != null) {
            domainKey = Integer.valueOf(System.getenv().get("DOMAIN_KEY"));
            log.info("DOMAIN KEY {} ", domainKey);
        } else {
            domainKey = 3;
            log.info("DOMAIN KEY DEFAULT {} ", domainKey);
        }
        getListingsResidentialNSW();
        getListingsCommercialNSW();
        System.out.println("Complete");
    }

    private void getListingsResidentialNSW() throws Exception {
        Integer price;
        Integer priceStart = 100000;
        Integer priceIncrementAmount;
        Integer priceIncrementAmountSmall = 40000;
        Integer priceIncrementAmountSmallSydney = 100000;
        Integer priceIncrementAmountSmallRegional = 20000;
        Integer priceIncrementAmountMedium = 200000;
        Integer priceIncrementAmountLarge = 2000000;
        Integer priceStop = 20000000;
        Integer minLandSize = 100;
        String[] propertyTypes = new String[]{
            "AcreageSemiRural", 
            "DevelopmentSite", 
            "Farm",
            "House", 
            "NewLand",
            "Rural",
            "VacantLand"
        };

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
                {sydneyRegion, illawarraSouthCoast, hunterCentralNorthCoasts, regionalNSW};

        for (PropertySearchRequest.Locations location : locations) {
            log.info("Location {} ", location.region);
            PropertySearchRequest propertySearchRequest = new PropertySearchRequest();
            price = priceStart;

            while (price <= priceStop) {
                if (price < 1000000 && location.region.equals(regionalNSW.region)) {
                    priceIncrementAmount = priceIncrementAmountSmallRegional;
                } else if (price < 1000000 && location.region.equals(sydneyRegion.region)) {
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
                propertySearchRequest.locations = new PropertySearchRequest.Locations[]{location};
                PropertySearchRequest searchJson = new SearchLocations().NSW(propertySearchRequest);
                
                for (int i = 1; i < 6; i++) { // run through paginated results max 5 pages
                    searchJson.page = i;
                    String domainAuthString = getDomainAuth(domainAuthentication, authTokenString, domainKey, domainSearchCount);
                    PropertyListingDTO[] propertyListings = getDomainListing(domainListingService, domainAuthString, searchJson);
                    if (propertyListings != null && propertyListings.length > 0) { // check if there are results to add
                        propertyListingList.clear();
                        propertyListingList = addPlanningPortalAddress(planningPortalAddressSearch, Arrays.asList(propertyListings));
                        propertyListingList = addAdditionalPropertyFields(propertyInformationService, propertyListingList);
                        saveDatabasePoint(propertyListingService, propertyListingList);
                        log.info("{} Pages {} {}", price, i, propertyListings.length);
                    }
                    if (propertyListings == null || propertyListings.length < PAGE_SIZE) {
                        break; // exit the loop if there are less than the max results or no results
                    }
                }
                price += priceIncrementAmount;
            }
        }
    }

    public void getListingsCommercialNSW() throws Exception {
        PropertySearchCommercialRequest searchJsonCommercial = new PropertySearchCommercialRequest();
        searchJsonCommercial.searchMode = "forSale";
        PropertySearchCommercialRequest.PriceSearch priceSearch = new PropertySearchCommercialRequest.PriceSearch();
        priceSearch.min = 100000;
        priceSearch.max = 20000000;
        priceSearch.type = "totalAmount";
        searchJsonCommercial.price = priceSearch;
        searchJsonCommercial.propertyTypes = new String[]{
            "acreageSemiRural",
            "blockOfUnits",
            "developmentLand",
            "developmentSite",
            "house",
            "newLand",
            "propertyRealEstate",
            "rural",
            "vacantLand"
        };
        searchJsonCommercial.landAreaMin = 100;
        searchJsonCommercial.pageSize = PAGE_SIZE;

        PropertySearchCommercialRequest.LocationSearch sydneyRegion = new PropertySearchCommercialRequest.LocationSearch();
        sydneyRegion.state = "NSW";
        sydneyRegion.region = "Sydney Region";
        PropertySearchCommercialRequest.LocationSearch regionalNSW = new PropertySearchCommercialRequest.LocationSearch();
        regionalNSW.state = "NSW";
        regionalNSW.region = "Regional NSW";
        PropertySearchCommercialRequest.LocationSearch illawarraSouthCoast = new PropertySearchCommercialRequest.LocationSearch();
        illawarraSouthCoast.state = "NSW";
        illawarraSouthCoast.region = "Illawarra & South Coast";
        PropertySearchCommercialRequest.LocationSearch hunterCentralNorthCoasts = new PropertySearchCommercialRequest.LocationSearch();
        hunterCentralNorthCoasts.state = "NSW";
        hunterCentralNorthCoasts.region = "Hunter, Central & North Coasts";
        PropertySearchCommercialRequest.LocationSearch[] locations = new PropertySearchCommercialRequest.LocationSearch[]
                {sydneyRegion, illawarraSouthCoast, hunterCentralNorthCoasts, regionalNSW};

        for (PropertySearchCommercialRequest.LocationSearch location : locations) {
            log.debug("Location {} ", location.region);
            searchJsonCommercial.locations = new PropertySearchCommercialRequest.LocationSearch[]{location};

            for (int i = 1; i < 6; i++) { // run through paginated results max 5 pages
                searchJsonCommercial.page = i;
                String domainAuthString = getDomainAuth(domainAuthentication, authTokenString, domainKey, domainSearchCount);
                PropertyListingDTO[] propertyListings = getDomainListing(domainListingService, domainAuthString, searchJsonCommercial);

                if (propertyListings != null && propertyListings.length > 0) { // check if there are results to add
                    propertyListingList.clear();
                    propertyListingList = addPlanningPortalAddress(planningPortalAddressSearch, Arrays.asList(propertyListings));
                    propertyListingList = addAdditionalPropertyFields(propertyInformationService, propertyListingList);
                    saveDatabasePoint(propertyListingService, propertyListingList);
                    log.debug("Pages {} {}", i, propertyListings.length);
                }
                if (propertyListings == null || propertyListings.length < PAGE_SIZE) {
                    break; // exit the loop if there are less than the max results or no results
                }
            }
        }

        if (propertyListingList != null) {
            System.out.println("Commercial property listings complete " + propertyListingList.size());
        }
    }


    private String getDomainAuth(IDomainAuthentication domainAuthentication, String currentAuthString, 
            Integer key, Integer apiRequestCount) throws Exception {
        if (apiRequestCount == 0) {
            return domainAuthentication.getAuthToken(authKey[key]).access_token;
        } else if (apiRequestCount > 450) { //max 500 requests per api key
            key++;
            if (key >= authKey.length) {
                key = 0;
            }
            domainSearchCount = 0;
            System.out.println("Domain Key Change" + key);
            return domainAuthentication.getAuthToken(authKey[key]).access_token;
        } else {
            return currentAuthString;
        }
    }

    private PropertyListingDTO[] getDomainListing(IDomainListingService domainListingService, String authToken,
            PropertySearchRequest searchJson) throws Exception {
        domainSearchCount++;
        authTokenString = authToken;
        return (domainListingService.getPropertyList(authToken, searchJson));
    }

    private PropertyListingDTO[] getDomainListing(IDomainListingService domainListingService, String authToken,
            PropertySearchCommercialRequest searchJsonCommercial) throws Exception {
        domainSearchCount++;
        return(domainListingService.getPropertyList(authToken, searchJsonCommercial));
    }

    private List<PropertyListingDTO> addPlanningPortalAddress(PlanningPortalAddressSearch planningPortalAddressSearch, List<PropertyListingDTO> propertyListings) throws Exception {
        return(planningPortalAddressSearch.addPlanningPortalId(propertyListings));
    }

    private List<PropertyListingDTO> addAdditionalPropertyFields(PropertyInformationService propertyInformationService, List<PropertyListingDTO> propertyListings) throws Exception {
        for (PropertyListingDTO propertyListingDTO : propertyListings) {
            if (propertyListingDTO.getPropertyId() != null) {
                try {
                    PropertyInformation propertyInformation = propertyInformationService.
                        getPropertyInformation(propertyListingDTO.getPropertyId());
                    propertyListingDTO.setFloorSpaceRatio(propertyInformation.getFloorSpaceRatio()); 
                    propertyListingDTO.setLandValue(propertyInformation.getLandValue1());
                    propertyListingDTO.setZone(propertyInformation.getZoneCode());
                    propertyListingDTO.setMinimumLotSize(propertyInformation.getMinimumLotSize());
                    
                    // calculated fields for the PropertyListingDTO object
                    if (propertyListingDTO.getPriceInt() != null && propertyListingDTO.getPriceInt() != 0 && 
                            propertyListingDTO.getArea() != null && propertyListingDTO.getArea() != 0){
                        propertyListingDTO.setPricePSM(propertyListingDTO.getPriceInt()/propertyListingDTO.getArea());
                    }

                    if (propertyListingDTO.getPriceInt() != null && propertyListingDTO.getPriceInt() != 0 && 
                            propertyListingDTO.getLandValue() != null && propertyListingDTO.getLandValue() != 0){
                        propertyListingDTO.setPriceToLandValue(BigDecimal.valueOf(propertyListingDTO.getPriceInt()).
                            divide(BigDecimal.valueOf(propertyListingDTO.getLandValue()), 2, RoundingMode.CEILING));
                    }
                } catch (Exception e){
                    log.error("Can't find property {} ", e.getLocalizedMessage());
                }
            }
        }
        return (propertyListings);
    }



    private void saveDatabasePoint(PropertyListingService propertyListingService, List<PropertyListingDTO>  propertyListings) throws Exception {
        for (PropertyListingDTO propertyListingDTO : propertyListings) {
            PropertyListing propertyListing = modelMapper.map(propertyListingDTO, PropertyListing.class);
            if (propertyListing != null && propertyListing.getDomainListingId() != 0) {
                propertyListingService.savePropertyListing(propertyListing);
            } else {
                assert propertyListing != null;
                log.debug("Cannot save NULL LISTING {}", propertyListing.toString());
            }
        }
    }

        // private PropertyListing[] filterProperties(PropertyListing[] pListings) {
    //     FilterProperties filterProperties = new FilterProperties();
    //     return (filterProperties.filterProperties(pListings));
    // }
   
}
