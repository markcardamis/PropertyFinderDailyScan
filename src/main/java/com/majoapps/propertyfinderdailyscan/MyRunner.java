package com.majoapps.propertyfinderdailyscan;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.majoapps.propertyfinderdailyscan.business.domain.PropertyListing;
import com.majoapps.propertyfinderdailyscan.business.service.IPlanningPortalZoneSearch;
import com.majoapps.propertyfinderdailyscan.business.service.PlanningPortalZoneSearch;
import com.majoapps.propertyfinderdailyscan.business.service.PropertyInformationService;
import com.majoapps.propertyfinderdailyscan.data.entity.PropertyInformation;
import com.majoapps.propertyfinderdailyscan.utils.IServiceHelper;
import com.majoapps.propertyfinderdailyscan.utils.ServiceHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyRunner implements CommandLineRunner {

    ExecutorService executor;

    @Autowired
    PropertyInformationService propertyInformationService;

    @Override
    public void run(String... args) throws Exception {
        // run service on startup to fetch data.
        getAllProperties();

    }

    public void getAllProperties() throws Exception {
        executor = Executors.newFixedThreadPool(20);
        IServiceHelper serviceHelper = new ServiceHelper();
        PropertyInformation propertyInformation1 = null;
        IPlanningPortalZoneSearch planningPortalZoneSearch = new PlanningPortalZoneSearch(serviceHelper);
        PropertyInformation propertyInformation = new PropertyInformation();

        Integer startIdInteger = propertyInformationService.getPropertyInformation(1).getLandValue1();
        System.out.println("start int getenv " + startIdInteger);
        Integer end_id = 0;

        if (startIdInteger == null) {
            startIdInteger = 0;
            System.out.println("setting start_id default" + startIdInteger);
        } else {

        }
        for (int i = startIdInteger; i < startIdInteger + 1000001; i++) {
            try {
                propertyInformation1 = propertyInformationService.getPropertyInformation(i);
                end_id = i;
                if (propertyInformation1.getBuildingHeight() == null) {
                    propertyInformation.setPropertyId(i);
                    Runnable worker = new MyRunnable(planningPortalZoneSearch, propertyInformation);
                    executor.execute(worker);
                    Thread.sleep(150L); // run at just under 7 RPS
                    worker = null; // for gc
                }
                if(end_id % 1000==0) {
                    propertyInformation.setPropertyId(1);
                    propertyInformation.setLandValue1(end_id);
                    propertyInformationService.partialUpdatePropertyInformation(1, propertyInformation);
                    System.out.println("update setenv " + end_id);
                }

            
            } catch (Exception e) {
                //System.out.println(" for loop exception " + e.getLocalizedMessage());
            } 
        }
        executor.shutdown();
            // Wait until all threads are finish
            while (!executor.isTerminated()) {
                Thread.yield();
            }
        propertyInformation.setPropertyId(1);
        propertyInformation.setLandValue1(end_id);
        propertyInformationService.partialUpdatePropertyInformation(1, propertyInformation);
        System.out.println("end int setenv " + end_id);
    }

    private class MyRunnable implements Runnable {
        private IPlanningPortalZoneSearch planningPortalZoneSearch;
        private PropertyInformation propertyInformationNew;

        MyRunnable(IPlanningPortalZoneSearch planningPortalZoneSearch, PropertyInformation propertyInformationNew) {
            this.planningPortalZoneSearch = planningPortalZoneSearch;
            this.propertyInformationNew = propertyInformationNew;
        }

        @Override
        public void run() {
            try {
                PropertyListing propertyListing = new PropertyListing();
                propertyListing.planningPortalPropId = propertyInformationNew.getPropertyId().toString();
                PropertyListing propertyListing1 = planningPortalZoneSearch.getSinglePlanningZone(propertyListing);
                propertyListing = null; // for gc
                planningPortalZoneSearch = null; // for gc 
                propertyInformationNew.setFloorSpaceRatio(new BigDecimal(
                    Float.toString(propertyListing1.fsr)));
                propertyInformationNew.setMinimumLotSize(propertyListing1.minimumLotSize);
                propertyInformationNew.setBuildingHeight(new BigDecimal(
                    Float.toString(propertyListing1.maximumBuildingHeight)));
                propertyInformationService.partialUpdatePropertyInformation(Integer.valueOf
                    (propertyListing1.planningPortalPropId), propertyInformationNew);
                System.out.println(Integer.valueOf(propertyListing1.planningPortalPropId) + " fsr " + 
                    propertyInformationNew.getFloorSpaceRatio() + " lot size " + 
                    propertyInformationNew.getMinimumLotSize() + " maximum height " + 
                    propertyInformationNew.getBuildingHeight());
                propertyInformationNew = null; // for gc
            } catch (Exception e){
                System.out.println(" runnable exception " + e.getLocalizedMessage());
            }
        }


    }
}
