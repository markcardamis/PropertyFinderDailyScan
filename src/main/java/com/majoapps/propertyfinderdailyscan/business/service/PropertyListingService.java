package com.majoapps.propertyfinderdailyscan.business.service;

import com.majoapps.propertyfinderdailyscan.business.domain.PropertyListingDTO;
import com.majoapps.propertyfinderdailyscan.data.entity.Notifications;
import com.majoapps.propertyfinderdailyscan.data.entity.PropertyListing;
import com.majoapps.propertyfinderdailyscan.data.repository.PropertyListingRepository;
import com.majoapps.propertyfinderdailyscan.exception.ResourceNotFoundException;
import com.majoapps.propertyfinderdailyscan.utils.ObjectMapperUtils;
import com.majoapps.propertyfinderdailyscan.utils.SpecificationUtil;
import com.sipios.springsearch.SpecificationsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.EntityNotFoundException;

@Slf4j
@Service
public class PropertyListingService {

    private final PropertyListingRepository propertyListingRepository;
    private final NotificationsService notificationsService;

    @Autowired
    public PropertyListingService(PropertyListingRepository propertyListingRepository,
                                  NotificationsService notificationsService) {
        this.propertyListingRepository = propertyListingRepository;
        this.notificationsService = notificationsService;
    }

    // return different amount of listings based on account priority
    public List<PropertyListingDTO> getPropertyListingBySearch(
            Specification<PropertyListing> searchSpec) {
        try {
            Pageable pageable = PageRequest.of(0, 20); // set page length
            List<PropertyListing> propertyListing = this.propertyListingRepository
                    .findAll(Specification.where(searchSpec), pageable).getContent();
            return ObjectMapperUtils.mapAll(propertyListing, PropertyListingDTO.class);
        } catch (Exception e) {
            log.error("Exception: ", e);
            throw new ResourceNotFoundException("Error retrieving results");
        }
    }

    public List<PropertyListingDTO> getPropertyListingsByNotificationsId(UUID id){
        Objects.requireNonNull(id);
        Notifications notifications = this.notificationsService.getNotificationsById(id);
        String token = SpecificationUtil.createSpecificationString(notifications);
        Specification<PropertyListing> specification = new SpecificationsBuilder<PropertyListing>()
                .withSearch(token).build();
        return (this.getPropertyListingBySearch(specification));
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
        List<PropertyListing> propertyListingResponse = propertyListingRepository
            .findByDomainListingId(propertyListing.getDomainListingId());
        if (propertyListingResponse.isEmpty()) { 
            return this.propertyListingRepository.save(propertyListing);
        } else {
            log.debug("Duplicate domainlistingId {} ", propertyListing.getDomainListingId());
            return propertyListingResponse.get(0);
        }
    }

    public PropertyListing findDomainListing(PropertyListing propertyListing) {
        List<PropertyListing> propertyListingResponse = propertyListingRepository
            .findByDomainListingId(propertyListing.getDomainListingId());
        if (propertyListingResponse.isEmpty()) { 
            return this.propertyListingRepository.save(propertyListing);
        } else {
            log.debug("Duplicate domainlistingId {} ", propertyListing.getDomainListingId());
            return propertyListingResponse.get(0);
        }
    }

    public PropertyListing updatePropertyListing(PropertyListing propertyListingNew) {
        List<PropertyListing> propertyListingResponse = propertyListingRepository
            .findByDomainListingId(propertyListingNew.getDomainListingId());
        PropertyListing propertyListingEntity = new PropertyListing();
        if (propertyListingResponse.isEmpty()) { 
            return (propertyListingRepository.save(propertyListingNew));
        } else {
            propertyListingEntity.setId(propertyListingResponse.get(0).getId());
            if(propertyListingNew.getTimeDate() != null) 
                propertyListingEntity.setTimeDate(propertyListingNew.getTimeDate());
            if(propertyListingNew.getDomainListingId() != null && propertyListingNew.getDomainListingId() != 0) 
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
            if(propertyListingNew.getArea() != null && propertyListingNew.getArea() != 0) 
                propertyListingEntity.setArea(propertyListingNew.getArea());
            if(propertyListingNew.getBathrooms() != null && propertyListingNew.getBathrooms() != 0) 
                propertyListingEntity.setBathrooms(propertyListingNew.getBathrooms());
            if(propertyListingNew.getBedrooms() != null && propertyListingNew.getBedrooms() != 0) 
                propertyListingEntity.setBedrooms(propertyListingNew.getBedrooms());
            if(propertyListingNew.getCarspaces() != null && propertyListingNew.getCarspaces() != 0) 
                propertyListingEntity.setCarspaces(propertyListingNew.getCarspaces());
            if(propertyListingNew.getLatitude() != null) 
                propertyListingEntity.setLatitude(propertyListingNew.getLatitude());
            if(propertyListingNew.getLongitude() != null) 
                propertyListingEntity.setLongitude(propertyListingNew.getLongitude());
            if(propertyListingNew.getSummaryDescription() != null) 
                propertyListingEntity.setSummaryDescription(propertyListingNew.getSummaryDescription());
            if(propertyListingNew.getPropertyId() != null) 
                propertyListingEntity.setPropertyId(propertyListingNew.getPropertyId());
            if(propertyListingNew.getPlanningPortalAddress() != null) 
                propertyListingEntity.setPlanningPortalAddress(propertyListingNew.getPlanningPortalAddress());
            if(propertyListingNew.getZone() != null) 
                propertyListingEntity.setZone(propertyListingNew.getZone());
            if(propertyListingNew.getFloorSpaceRatio() != null) 
                propertyListingEntity.setFloorSpaceRatio(propertyListingNew.getFloorSpaceRatio());
            if(propertyListingNew.getMinimumLotSize() != null) 
                propertyListingEntity.setMinimumLotSize(propertyListingNew.getMinimumLotSize());
            if(propertyListingNew.getLandValue() != null && propertyListingNew.getLandValue() != 0) 
                propertyListingEntity.setLandValue(propertyListingNew.getLandValue());
            if(propertyListingNew.getPricePSM() != null && propertyListingNew.getPricePSM() != 0) 
                propertyListingEntity.setPricePSM(propertyListingNew.getPricePSM());
            if(propertyListingNew.getPriceToLandValue() != null) 
                propertyListingEntity.setPriceToLandValue(propertyListingNew.getPriceToLandValue());
            if(propertyListingNew.getPropertyType() != null)
                propertyListingEntity.setPropertyType(propertyListingNew.getPropertyType());
            if(propertyListingNew.getStreetFrontage() != null)
                propertyListingEntity.setStreetFrontage(propertyListingNew.getStreetFrontage());
            return (propertyListingRepository.save(propertyListingNew));        
        }
    }

    public void deleteAll() {
        this.propertyListingRepository.deleteAll();
    }

}
