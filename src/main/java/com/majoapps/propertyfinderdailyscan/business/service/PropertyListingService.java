package com.majoapps.propertyfinderdailyscan.business.service;

import com.majoapps.propertyfinderdailyscan.data.entity.PropertyListing;
import com.majoapps.propertyfinderdailyscan.data.repository.PropertyListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityNotFoundException;

@Service
public class PropertyListingService {

    private final PropertyListingRepository propertyListingRepository;

    @Autowired
    public PropertyListingService(PropertyListingRepository propertyListingRepository) {
        this.propertyListingRepository = propertyListingRepository;
    }

    public List<PropertyListing> getAllListings() {
        List<PropertyListing> properties = new ArrayList<>();
        Iterable<PropertyListing> results = this.propertyListingRepository.findAll();
        results.forEach(properties::add);
        return properties;
    }

    public PropertyListing getPropertyListing(Integer id) {
        return this.propertyListingRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(id.toString()));
    }

    public PropertyListing savePropertyListing(PropertyListing propertyListing) {
        return this.propertyListingRepository.save(propertyListing);
    }

    public PropertyListing updatePropertyListing(PropertyListing propertyListingNew) {
        List<PropertyListing> propertyListingResponse = propertyListingRepository
            .findByDomainListingId(propertyListingNew.getDomainListingId());
        PropertyListing propertyListingEntity = new PropertyListing();
        if (propertyListingResponse.isEmpty()) { 
            return (propertyListingRepository.save(propertyListingNew));
        } else {
            propertyListingEntity.setId(propertyListingNew.getId());
            if(propertyListingNew.getTimeDate() != null) 
                propertyListingEntity.setTimeDate(propertyListingNew.getTimeDate());
            if(propertyListingNew.getDomainListingId() != 0) 
                propertyListingEntity.setDomainListingId(propertyListingNew.getDomainListingId());
            if(propertyListingNew.getPrice() != null) 
                propertyListingEntity.setPrice(propertyListingNew.getPrice());
            if(propertyListingNew.getListingURL() != null) 
                propertyListingEntity.setListingURL(propertyListingNew.getListingURL());
            if(propertyListingNew.getListingPhoto() != null) 
                propertyListingEntity.setListingPhoto(propertyListingNew.getListingPhoto());
            if(propertyListingNew.getAddress() != null) 
                propertyListingEntity.setAddress(propertyListingNew.getAddress());
            if(propertyListingNew.getUnitNumber() != null) 
                propertyListingEntity.setUnitNumber(propertyListingNew.getUnitNumber());
            if(propertyListingNew.getHouseNumber() != null) 
                propertyListingEntity.setHouseNumber(propertyListingNew.getHouseNumber());
            if(propertyListingNew.getStreetName() != null) 
                propertyListingEntity.setStreetName(propertyListingNew.getStreetName());
            if(propertyListingNew.getSuburbName() != null) 
                propertyListingEntity.setSuburbName(propertyListingNew.getSuburbName());
            if(propertyListingNew.getPostCode() != null) 
                propertyListingEntity.setPostCode(propertyListingNew.getPostCode());
            if(propertyListingNew.getArea() != 0) 
                propertyListingEntity.setArea(propertyListingNew.getArea());
            if(propertyListingNew.getBathrooms() != 0) 
                propertyListingEntity.setBathrooms(propertyListingNew.getBathrooms());
            if(propertyListingNew.getBedrooms() != 0) 
                propertyListingEntity.setBedrooms(propertyListingNew.getBedrooms());
            if(propertyListingNew.getCarspaces() != 0) 
                propertyListingEntity.setCarspaces(propertyListingNew.getCarspaces());
            if(propertyListingNew.getLatitude() != null) 
                propertyListingEntity.setLatitude(propertyListingNew.getLatitude());
            if(propertyListingNew.getLongitude() != null) 
                propertyListingEntity.setLongitude(propertyListingNew.getLongitude());
            if(propertyListingNew.getSummaryDescription() != null) 
                propertyListingEntity.setSummaryDescription(propertyListingNew.getSummaryDescription());
            if(propertyListingNew.getPlanningPortalPropId() != null) 
                propertyListingEntity.setPlanningPortalPropId(propertyListingNew.getPlanningPortalPropId());
            if(propertyListingNew.getPlanningPortalAddress() != null) 
                propertyListingEntity.setPlanningPortalAddress(propertyListingNew.getPlanningPortalAddress());

            return (propertyListingRepository.save(propertyListingNew));        
        }
    }

    public void deleteAll() {
        this.propertyListingRepository.deleteAll();
    }

}
