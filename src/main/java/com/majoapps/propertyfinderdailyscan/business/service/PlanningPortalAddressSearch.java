package com.majoapps.propertyfinderdailyscan.business.service;

import com.majoapps.propertyfinderdailyscan.business.domain.PropertyListingDTO;
import com.majoapps.propertyfinderdailyscan.data.repository.PropertyInformationRepository;
import com.majoapps.propertyfinderdailyscan.utils.SpecificationUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PlanningPortalAddressSearch {

    private final PropertyInformationRepository propertyInformationRepository;
    private List<PropertyListingDTO> propertyListingArrayList = Collections.synchronizedList(new ArrayList<>());

    @Autowired
    public PlanningPortalAddressSearch(PropertyInformationRepository propertyInformationRepository) {
        this.propertyInformationRepository = propertyInformationRepository;
    }

    public List<PropertyListingDTO> addPlanningPortalId(List<PropertyListingDTO> propertyListings) throws Exception{

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
                    System.out.println("looking for address " + addressString);


                    List<String> returnAddressList = propertyInformationRepository.findByAddress(
                        SpecificationUtil.createAddressString(propertyListing));
                    
                    if (returnAddressList.size() == 0) {
                        propertyListing.setSuburbName("");
                        addressString = SpecificationUtil.createAddressString(propertyListing);
                        System.out.println("looking for address1 " + addressString);
                        returnAddressList = propertyInformationRepository.findByAddress(
                            SpecificationUtil.createAddressString(propertyListing));
                    }

                    if (returnAddressList.size() == 0) {
                        System.out.println("Cannot find address " + addressString);
                    } else if (returnAddressList.size() == 1){
                        String returnAddreses[] = returnAddressList.get(0).split("\\,");
                        if (returnAddreses.length == 2) {
                            propertyListing.setPlanningPortalPropId(returnAddreses[0]);
                            propertyListing.setPlanningPortalAddress(returnAddreses[1]);
                            System.out.println("found the address " + returnAddreses[1]);
                        } else {
                            log.debug("SQL response for planning portal not properly formatted " + returnAddressList.get(0).toString());
                        }
                    } else {
                        System.out.println("Found many address " + addressString);
                    }
                    propertyListingArrayList.add(propertyListing);


                    // for (String returnAddress : returnAddressList) {
                    //     String returnAddreses[] = returnAddress.split("\\,");
                    //     if (returnAddreses.length == 2) {
                    //         propertyListing.setPlanningPortalPropId(returnAddreses[0]);
                    //         propertyListing.setPlanningPortalAddress(returnAddreses[1]);
                    //         System.out.println("found the address " + returnAddreses[1]);
                    //     } else {
                    //         log.debug("SQL response for planning portal not properly formatted " + returnAddress.toString());
                    //     }
                    //     propertyListingArrayList.add(propertyListing);
                    // }

                }
            }
            log.info("Finished all planning portal address threads");
        }
        return propertyListingArrayList;
    }

}
