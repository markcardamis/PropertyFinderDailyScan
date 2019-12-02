package com.majoapps.propertyfinderdailyscan.business.service;

import com.majoapps.propertyfinderdailyscan.data.entity.PropertyInformation;
import com.majoapps.propertyfinderdailyscan.data.repository.PropertyInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;

@Service
public class PropertyInformationService {

    private final PropertyInformationRepository propertyInformationRepository;

    @Autowired
    public PropertyInformationService(PropertyInformationRepository propertyInformationRepository) {
        this.propertyInformationRepository = propertyInformationRepository;
    }

    public PropertyInformation getPropertyInformation(Integer id) {
        return this.propertyInformationRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(id.toString()));
    }

}
