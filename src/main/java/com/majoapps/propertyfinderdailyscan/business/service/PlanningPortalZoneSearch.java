// package com.majoapps.propertyfinderdailyscan.business.service;

// import java.util.ArrayList;
// import java.util.Collections;
// import java.util.List;
// import java.util.concurrent.ExecutorService;
// import java.util.concurrent.Executors;

// import com.google.gson.Gson;
// import com.majoapps.propertyfinderdailyscan.business.domain.PlanningPortalZoneResponse;
// import com.majoapps.propertyfinderdailyscan.business.domain.PropertyListing;
// import com.majoapps.propertyfinderdailyscan.business.domain.PropertyListingDTO;
// import com.majoapps.propertyfinderdailyscan.data.entity.PropertyInformation;
// import com.majoapps.propertyfinderdailyscan.utils.HttpMethod;
// import com.majoapps.propertyfinderdailyscan.utils.IServiceHelper;
// import com.majoapps.propertyfinderdailyscan.utils.ServiceHelper;
// import com.majoapps.propertyfinderdailyscan.utils.UrlExtensionMethods;

// import lombok.extern.slf4j.Slf4j;

// @Slf4j
// public class PlanningPortalZoneSearch implements IPlanningPortalZoneSearch
// {

//     private PropertyInformationService propertyInformationService;

//     private IServiceHelper mServiceHelper;// = new IServiceHelper();
//     private List<PropertyListingDTO> propertyListingArrayList = Collections.synchronizedList(new ArrayList<>());
//     private Integer propertyListingLength = 0;
//     private long startTime;

//     public PlanningPortalZoneSearch() throws Exception {
//         mServiceHelper = new ServiceHelper();
//     }


//     @Override
//     public List<PropertyListingDTO> addPlanningZone(List<PropertyListingDTO> propertyListings) throws Exception {

//         for (PropertyListingDTO propertyListing : propertyListings) {
//             try {
//                 Integer propertyId = Integer.valueOf(propertyListing.planningPortalPropId);
//                 PropertyInformation propertyInformationResponse = propertyInformationService.getPropertyInformation(propertyId);
//                 if (propertyInformationResponse.getZoneCode() == null) {
//                     propertyListing.zone = propertyInformationResponse.getZoneCode();
//                 }
//                 if (propertyInformationResponse.getFloorSpaceRatio() == null) {
//                     propertyListing.fsr = propertyInformationResponse.getFloorSpaceRatio().floatValue();
//                 }
//                 if (propertyInformationResponse.getMinimumLotSize() == null) {
//                     propertyListing.minimumLotSize = propertyInformationResponse.getMinimumLotSize();
//                 }
//             } catch (Exception e) {
//                 log.error("Exception: " + e);
//             }
//         }
//         return propertyListings;
//     }
// }
