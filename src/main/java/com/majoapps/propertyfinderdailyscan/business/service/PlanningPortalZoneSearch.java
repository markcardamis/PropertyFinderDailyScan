package com.majoapps.propertyfinderdailyscan.business.service;

import com.google.gson.Gson;
import com.majoapps.propertyfinderdailyscan.business.domain.PlanningPortalZoneResponse;
import com.majoapps.propertyfinderdailyscan.business.domain.PropertyListing;
import com.majoapps.propertyfinderdailyscan.utils.HttpMethod;
import com.majoapps.propertyfinderdailyscan.utils.IServiceHelper;
import com.majoapps.propertyfinderdailyscan.utils.UrlExtensionMethods;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PlanningPortalZoneSearch implements IPlanningPortalZoneSearch
{

    private final IServiceHelper serviceHelper;
    private final Gson gson = new Gson();
    
    @Autowired
    public PlanningPortalZoneSearch(IServiceHelper serviceHelper){
        this.serviceHelper = serviceHelper;
    }

    @Override
    public PropertyListing getSinglePlanningZone (PropertyListing propertyListings) throws Exception {

        // Get Planning portal zone info
        if (propertyListings != null){
            try {
                propertyListings.zone = "0"; // set default values as address is not always found
                propertyListings.lgaName = "0";
                propertyListings.fsr = 0f;
                propertyListings.maximumBuildingHeight = 0f;
                
                String address = "https://api.apps1.nsw.gov.au/planning/viewersf/V1/ePlanningApi/layerintersect";
                address = UrlExtensionMethods.appendParameter(address, "type", "property");
                address = UrlExtensionMethods.appendParameter(address, "id", propertyListings.planningPortalPropId);
                address = UrlExtensionMethods.appendParameter(address, "layers", "epi");
                String responseJson = serviceHelper.callHTTPService(address, HttpMethod.GET, "", false, "");
                address = null; // for gc

                PlanningPortalZoneResponse[] planningPortalZoneResponse = gson.fromJson(responseJson, PlanningPortalZoneResponse[].class);
                responseJson = null; // for gc
                
                for (int j = 0; j < planningPortalZoneResponse.length; j++) {
                    if (planningPortalZoneResponse[j].layerName.equals("Land Zoning")) {
                        propertyListings.zone = planningPortalZoneResponse[j].results[0].Zone;
                        propertyListings.lgaName =
                                planningPortalZoneResponse[j].results[0].LGA_Name;
                    } else if (planningPortalZoneResponse[j].layerName.equals("Floor Space Ratio (n:1)")){
                        propertyListings.fsr =
                                Float.valueOf(planningPortalZoneResponse[j].results[0].Floor_Space_Ratio);
                    } else if (planningPortalZoneResponse[j].layerName.equals("Minimum Lot Size")){
                        propertyListings.minimumLotSize =
                                planningPortalZoneResponse[j].results[0].title.replaceAll("\\D", "");
                    } else if (planningPortalZoneResponse[j].layerName.equals("Height of Building")){
                        propertyListings.maximumBuildingHeight = Float.valueOf(
                                planningPortalZoneResponse[j].results[0].Maximum_Building_Height);
                    }
                }

                planningPortalZoneResponse = null; // for gc
            } catch (Exception e) {
                log.error("Exception: " + e);
            }            
        }
        return propertyListings;
    }

}
