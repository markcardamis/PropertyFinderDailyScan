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

    public void deleteAll() {
        this.propertyListingRepository.deleteAll();
    }

}
