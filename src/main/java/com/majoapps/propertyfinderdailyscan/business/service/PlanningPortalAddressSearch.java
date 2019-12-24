package com.majoapps.propertyfinderdailyscan.business.service;

import com.google.gson.Gson;
import com.majoapps.propertyfinderdailyscan.business.domain.PlanningPortalAddressResponse;
import com.majoapps.propertyfinderdailyscan.business.domain.PropertyListingDTO;
import com.majoapps.propertyfinderdailyscan.data.entity.PropertyInformation;
import com.majoapps.propertyfinderdailyscan.data.repository.PropertyInformationRepository;
import com.majoapps.propertyfinderdailyscan.utils.HttpMethod;
import com.majoapps.propertyfinderdailyscan.utils.IServiceHelper;
import com.majoapps.propertyfinderdailyscan.utils.ServiceHelper;
import com.majoapps.propertyfinderdailyscan.utils.SpecificationUtil;
import com.majoapps.propertyfinderdailyscan.utils.StringCheck;
import com.majoapps.propertyfinderdailyscan.utils.UrlExtensionMethods;
import com.sipios.springsearch.SpecificationsBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PlanningPortalAddressSearch implements IPlanningPortalAddressSearch
{
    private IServiceHelper mServiceHelper;// = new IServiceHelper();
    private List<PropertyListingDTO> propertyListingArrayList = Collections.synchronizedList(new ArrayList<>());
    private Integer propertyListingLength = 0;
    private long startTime;

    private final PropertyInformationRepository propertyInformationRepository;

    @Autowired
    public PlanningPortalAddressSearch(PropertyInformationRepository propertyInformationRepository) {
        mServiceHelper = new ServiceHelper();
        this.propertyInformationRepository = propertyInformationRepository;
    }

    // public PlanningPortalAddressSearch() throws Exception {
    //     mServiceHelper = new ServiceHelper();
    //     this.propertyInformationRepository = null;
    // }


    @Override
    public List<PropertyListingDTO> getFormattedAddressMultiThreaded(List<PropertyListingDTO> propertyListings) throws Exception{
        ExecutorService executor = Executors.newFixedThreadPool(10);
        startTime = System.currentTimeMillis();

        // Get Planning portal zone info
        if (propertyListings != null && propertyListings.size() > 0){
            propertyListingLength = propertyListings.size();

            for (PropertyListingDTO propertyListing : propertyListings) {
                Runnable worker = new MyRunnable(propertyListing);
                //Runnable worker = new DatabaseRunnable(propertyListing);
                executor.execute(worker);
            }
            executor.shutdown();
            // Wait until all threads are finish
            while (!executor.isTerminated()) {
                //Thread.yield();
            }
            log.info("Finished all planning portal address threads");
        }
        return propertyListingArrayList;
    }

    private class DatabaseRunnable implements Runnable {
        private final PropertyListingDTO propertyListing;

        DatabaseRunnable(PropertyListingDTO propertyListing) {
            this.propertyListing = propertyListing;
        }

        @Override
        public void run() {
            try {
                if (propertyListing.getHouseNumber() != null && 
                    propertyListing.getHouseNumber().length() > 0 &&
                    propertyListing.getStreetName() != null && 
                    propertyListing.getStreetName().length() > 0 &&
                    propertyListing.getSuburbName() != null && 
                    propertyListing.getSuburbName().length() > 0 &&
                    propertyListing.getPostCode() != null &&
                    propertyListing.getPostCode().length() > 0) {

                    String token = SpecificationUtil.createSpecificationString(propertyListing);        
                    Specification<PropertyInformation> specification = new SpecificationsBuilder<PropertyInformation>()
                        .withSearch(token).build();
                    Pageable pageable = PageRequest.of(0, 1, Sort.by("propertyId"));
                    List<PropertyInformation> propertyInformationList = propertyInformationRepository.findAll(
                        Specification.where(specification), pageable).getContent();
                    if (propertyInformationList != null && propertyInformationList.size() > 0) { 
                        propertyListing.planningPortalPropId = Integer.toString(propertyInformationList.get(0).getPropertyId());
                        String address = "";
                        if (StringCheck.isNotNullOrEmpty(propertyInformationList.get(0).getUnitNumber())) {
                            address += propertyInformationList.get(0).getUnitNumber() + " ";
                        }
                        if (StringCheck.isNotNullOrEmpty(propertyInformationList.get(0).getHouseNumber())) {
                            address += propertyInformationList.get(0).getHouseNumber() + " ";
                        }
                        if (StringCheck.isNotNullOrEmpty(propertyInformationList.get(0).getStreetName())) {
                            address += propertyInformationList.get(0).getStreetName() + " ";
                        }
                        if (StringCheck.isNotNullOrEmpty(propertyInformationList.get(0).getSuburbName())) {
                            address += propertyInformationList.get(0).getSuburbName() + " ";
                        }
                        if (StringCheck.isNotNullOrEmpty(propertyInformationList.get(0).getPostCode())) {
                            address += propertyInformationList.get(0).getPostCode();
                        }
                        propertyListing.planningPortalAddress = address;
                        propertyListingArrayList.add(propertyListing);
                        log.debug("PlanningPortalAddress " + propertyListingArrayList.size() + "/" + propertyListingLength); 
                    }
                }

            } catch (Exception e) {
                log.error("Exception: {} " + e.getLocalizedMessage());
            }

        }
    }

    private class MyRunnable implements Runnable {
        private final PropertyListingDTO propertyListing;

        MyRunnable(PropertyListingDTO propertyListing) {
            this.propertyListing = propertyListing;
        }

        @Override
        public void run() {
            try {
                if (propertyListing.getHouseNumber() != null && 
                    propertyListing.getHouseNumber().length() > 0 &&
                    propertyListing.getStreetName() != null && 
                    propertyListing.getStreetName().length() > 0 &&
                    propertyListing.getSuburbName() != null && 
                    propertyListing.getSuburbName().length() > 0 &&
                    propertyListing.getPostCode() != null &&
                    propertyListing.getPostCode().length() > 0) {

                    long sTime = System.currentTimeMillis();
                    String address = "https://api.apps1.nsw.gov.au/planning/viewersf/V1/ePlanningApi/address";
                    address = UrlExtensionMethods.appendParameter(address, "a", propertyListing.address);
                    String responseJson = mServiceHelper.callHTTPService(address,
                            HttpMethod.GET, "", false, "");
                    Gson gson = new Gson();
                    PlanningPortalAddressResponse[] planningPortalAddressResponses = gson.fromJson(responseJson, PlanningPortalAddressResponse[].class);
                    propertyListing.planningPortalPropId = planningPortalAddressResponses[0].propId;
                    propertyListing.planningPortalAddress = planningPortalAddressResponses[0].address;
                    propertyListingArrayList.add(propertyListing);
                    log.debug("PlanningPortalAddress " + propertyListingArrayList.size() + "/" + propertyListingLength);
                    
                    long endTime = System.currentTimeMillis() - startTime;
                    long eTime = System.currentTimeMillis() - sTime;
                    log.debug("RPS " + propertyListingArrayList.size()/(endTime/1000f));
                    if (eTime > 2000) {
                        eTime = 2000;
                    }
                    Thread.sleep(2000-eTime);
                }

            } catch (Exception e) {
                log.error("Exception: {} " + e);
            }

        }
    }

}
