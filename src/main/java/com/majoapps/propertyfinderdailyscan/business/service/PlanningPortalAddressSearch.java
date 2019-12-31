package com.majoapps.propertyfinderdailyscan.business.service;

import com.majoapps.propertyfinderdailyscan.business.domain.PropertyListingDTO;
import com.majoapps.propertyfinderdailyscan.data.repository.PropertyInformationRepository;
import com.majoapps.propertyfinderdailyscan.utils.SpecificationUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PlanningPortalAddressSearch {

    private final PropertyInformationRepository propertyInformationRepository;
    private List<PropertyListingDTO> propertyListingArrayList = Collections.synchronizedList(new ArrayList<>());
    ExecutorService executor;

    @Autowired
    public PlanningPortalAddressSearch(PropertyInformationRepository propertyInformationRepository) {
        this.propertyInformationRepository = propertyInformationRepository;
    }

    public List<PropertyListingDTO> addPlanningPortalIdSingleThread(List<PropertyListingDTO> propertyListings) throws Exception {
        // Get Planning portal zone info
        if (propertyListings != null && propertyListings.size() > 0){
            for (PropertyListingDTO propertyListing : propertyListings) {
                if (propertyListing.getHouseNumber() != null && 
                    propertyListing.getHouseNumber().length() > 0 &&
                    propertyListing.getStreetName() != null && 
                    propertyListing.getStreetName().length() > 0 &&
                    propertyListing.getSuburbName() != null && 
                    propertyListing.getSuburbName().length() > 0 &&
                    propertyListing.getPostCode() != null &&
                    propertyListing.getPostCode().length() > 0) {


                    String addressString = SpecificationUtil.createAddressString(propertyListing);
                    if (!addressString.contains("lot")) {
                        log.debug("looking for address " + addressString);
                        List<String> returnAddressList = propertyInformationRepository.
                            findByAddress(addressString);

                        if (returnAddressList.size() == 0) {
                            propertyListing.setSuburbName("");
                            returnAddressList = propertyInformationRepository.findByAddress(
                                SpecificationUtil.createAddressString(propertyListing));
                        }

                        if (returnAddressList.size() == 0) {
                            log.debug("Cannot find address " + addressString);
                        } else if (returnAddressList.size() == 1) {
                            String returnAddreses[] = returnAddressList.get(0).split("\\,");
                            if (returnAddreses.length == 2) {
                                propertyListing.setPlanningPortalPropId(returnAddreses[0]);
                                propertyListing.setPlanningPortalAddress(returnAddreses[1]);
                                log.debug("found the address " + returnAddreses[1]);
                            } else {
                                log.info("SQL response for planning portal wrongly formatted " + 
                                    returnAddressList.get(0).toString());
                            }
                        } else {
                            log.debug("Found many address " + addressString);
                        }

                    } else {
                        log.debug("Address contains LOT " + addressString);
                    }
                        
                    propertyListingArrayList.add(propertyListing);
                }
            }
            log.info("Finished all planning portal address threads");
        }
        return propertyListingArrayList;
    }

    public List<PropertyListingDTO> addPlanningPortalId(List<PropertyListingDTO> propertyListings) throws Exception {
        executor = Executors.newFixedThreadPool(10);
        // Get Planning portal zone info
        if (propertyListings != null && propertyListings.size() > 0){
            for (PropertyListingDTO propertyListing : propertyListings) {
                if (propertyListing.getHouseNumber() != null && 
                    propertyListing.getHouseNumber().length() > 0 &&
                    propertyListing.getStreetName() != null && 
                    propertyListing.getStreetName().length() > 0 &&
                    propertyListing.getSuburbName() != null && 
                    propertyListing.getSuburbName().length() > 0 &&
                    propertyListing.getPostCode() != null &&
                    propertyListing.getPostCode().length() > 0) {

                    Runnable worker = new MyRunnable(propertyInformationRepository, propertyListing);
                    executor.execute(worker);
                    worker = null; // for gc

                }
            }
            log.info("Finished all planning portal address threads");
        }
        executor.shutdown();
        // Wait until all threads are finish
        while (!executor.isTerminated()) {
            Thread.yield(); 
        }
        return propertyListingArrayList;
    }


    private class MyRunnable implements Runnable {
        private PropertyInformationRepository propertyInformationRepository;
        private PropertyListingDTO propertyListing;
        MyRunnable(PropertyInformationRepository propertyInformationRepository, PropertyListingDTO propertyListing) {
            this.propertyInformationRepository = propertyInformationRepository;
            this.propertyListing = propertyListing;
        }

        @Override
        public void run() {
            try {
                String addressString = SpecificationUtil.createAddressString(propertyListing);
                    if (!addressString.contains("lot")) {
                        log.debug("looking for address " + addressString);
                        List<String> returnAddressList = propertyInformationRepository.
                            findByAddress(addressString);

                        if (returnAddressList.size() == 0) {
                            propertyListing.setSuburbName("");
                            returnAddressList = propertyInformationRepository.findByAddress(
                                SpecificationUtil.createAddressString(propertyListing));
                        }

                        if (returnAddressList.size() == 0) {
                            log.debug("Cannot find address " + addressString);
                        } else if (returnAddressList.size() == 1) {
                            String returnAddreses[] = returnAddressList.get(0).split("\\,");
                            if (returnAddreses.length == 2) {
                                propertyListing.setPlanningPortalPropId(returnAddreses[0]);
                                propertyListing.setPlanningPortalAddress(returnAddreses[1]);
                                log.debug("found the address " + returnAddreses[1]);
                            } else {
                                log.info("SQL response for planning portal wrongly formatted " + 
                                    returnAddressList.get(0).toString());
                            }
                        } else {
                            log.debug("Found many address " + addressString);
                        }

                    } else {
                        log.debug("Address contains LOT " + addressString);
                    }
                        
                    propertyListingArrayList.add(propertyListing);
            } catch (Exception e){
                log.debug(" runnable exception " + e.getLocalizedMessage());
            }
        }


    }

}
