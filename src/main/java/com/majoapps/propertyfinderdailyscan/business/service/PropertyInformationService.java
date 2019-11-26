package com.majoapps.propertyfinderdailyscan.business.service;

import com.majoapps.propertyfinderdailyscan.data.entity.PropertyInformation;
import com.majoapps.propertyfinderdailyscan.data.repository.PropertyInformationRepository;
import com.majoapps.propertyfinderdailyscan.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

@Service
public class PropertyInformationService {

    private final PropertyInformationRepository propertyInformationRepository;

    @Autowired
    public PropertyInformationService(PropertyInformationRepository propertyInformationRepository) {
        this.propertyInformationRepository = propertyInformationRepository;
    }

    public List<PropertyInformation> getAllProperties() {
        List<PropertyInformation> properties = new ArrayList<>();
        Iterable<PropertyInformation> results = this.propertyInformationRepository.findAll();
        results.forEach(properties::add);
        return properties;
    }

    public PropertyInformation getPropertyInformation(Integer id) {
        return this.propertyInformationRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(id.toString()));
    }

    public ResponseEntity<PropertyInformation> updatePropertyInformation(Integer id, PropertyInformation newAccount){
        return propertyInformationRepository.findById(id).map(propertyInformation -> {
            propertyInformation.setDistrictCode(newAccount.getDistrictCode());
            propertyInformation.setDistrictName(newAccount.getDistrictName());
            propertyInformation.setPropertyType(newAccount.getPropertyType());
            propertyInformation.setPropertyName(newAccount.getPropertyName());
            propertyInformation.setUnitNumber(newAccount.getUnitNumber());
            propertyInformation.setStreetName(newAccount.getStreetName());
            propertyInformation.setSuburbName(newAccount.getSuburbName());
            propertyInformation.setPostCode(newAccount.getPostCode());
            propertyInformation.setZoneCode(newAccount.getZoneCode());
            propertyInformation.setArea(newAccount.getArea());
            propertyInformation.setAreaType(newAccount.getAreaType());
            propertyInformation.setBaseDate1(newAccount.getBaseDate1());
            propertyInformation.setLandValue1(newAccount.getLandValue1());
            propertyInformation.setAuthority1(newAccount.getAuthority1());
            propertyInformation.setBasis1(newAccount.getBasis1());
            propertyInformation.setBaseDate2(newAccount.getBaseDate2());
            propertyInformation.setLandValue2(newAccount.getLandValue2());
            propertyInformation.setAuthority2(newAccount.getAuthority2());
            propertyInformation.setBasis2(newAccount.getBasis2());
            propertyInformation.setBaseDate3(newAccount.getBaseDate3());
            propertyInformation.setLandValue3(newAccount.getLandValue3());
            propertyInformation.setAuthority3(newAccount.getAuthority3());
            propertyInformation.setBasis3(newAccount.getBasis3());
            propertyInformation.setBaseDate4(newAccount.getBaseDate4());
            propertyInformation.setLandValue4(newAccount.getLandValue4());
            propertyInformation.setAuthority4(newAccount.getAuthority4());
            propertyInformation.setBasis4(newAccount.getBasis4());
            propertyInformation.setBaseDate5(newAccount.getBaseDate5());
            propertyInformation.setLandValue5(newAccount.getLandValue5());
            propertyInformation.setAuthority5(newAccount.getAuthority5());
            propertyInformation.setBasis5(newAccount.getBasis5());
            propertyInformation.setFloorSpaceRatio(newAccount.getFloorSpaceRatio());
            propertyInformation.setMinimumLotSize(newAccount.getMinimumLotSize());
            propertyInformationRepository.save(propertyInformation);
            return ResponseEntity.ok(propertyInformation);
        }).orElseThrow(() -> new ResourceNotFoundException("Account [ID="+id+"] can't be found"));
    }

    public ResponseEntity<PropertyInformation> partialUpdatePropertyInformation(Integer id, PropertyInformation newAccount){
        return propertyInformationRepository.findById(id).map(propertyInformation -> {
            if(newAccount.getDistrictCode() != 0) 
                propertyInformation.setDistrictCode(newAccount.getDistrictCode());
            if(newAccount.getDistrictName() != null) 
                propertyInformation.setDistrictName(newAccount.getDistrictName());
            if(newAccount.getPropertyType() != null) 
                propertyInformation.setPropertyType(newAccount.getPropertyType());
            if(newAccount.getPropertyName() != null) 
                propertyInformation.setPropertyName(newAccount.getPropertyName());
            if(newAccount.getUnitNumber() != null) 
                propertyInformation.setUnitNumber(newAccount.getUnitNumber());
            if(newAccount.getStreetName() != null) 
                propertyInformation.setStreetName(newAccount.getStreetName());
            if(newAccount.getSuburbName() != null) 
                propertyInformation.setSuburbName(newAccount.getSuburbName());
            if(newAccount.getPostCode() != null) 
                propertyInformation.setPostCode(newAccount.getPostCode());
            if(newAccount.getZoneCode() != null) 
                propertyInformation.setZoneCode(newAccount.getZoneCode());
            if(newAccount.getArea() != null) 
                propertyInformation.setArea(newAccount.getArea());
            if(newAccount.getAreaType() != null) 
                propertyInformation.setAreaType(newAccount.getAreaType());
            if(newAccount.getBaseDate1() != null) 
                propertyInformation.setBaseDate1(newAccount.getBaseDate1());
            if(newAccount.getLandValue1() != null) 
                propertyInformation.setLandValue1(newAccount.getLandValue1());
            if(newAccount.getAuthority1() != null) 
                propertyInformation.setAuthority1(newAccount.getAuthority1());
            if(newAccount.getBasis1() != null) 
                propertyInformation.setBasis1(newAccount.getBasis1());
            if(newAccount.getBaseDate2() != null) 
                propertyInformation.setBaseDate2(newAccount.getBaseDate2());
            if(newAccount.getLandValue2() != null) 
                propertyInformation.setLandValue2(newAccount.getLandValue2());
            if(newAccount.getAuthority2() != null) 
                propertyInformation.setAuthority2(newAccount.getAuthority2());
            if(newAccount.getBasis2() != null) 
                propertyInformation.setBasis2(newAccount.getBasis2());
            if(newAccount.getBaseDate3() != null) 
                propertyInformation.setBaseDate3(newAccount.getBaseDate3());
            if(newAccount.getLandValue3() != null) 
                propertyInformation.setLandValue3(newAccount.getLandValue3());
            if(newAccount.getAuthority3() != null) 
                propertyInformation.setAuthority3(newAccount.getAuthority3());
            if(newAccount.getBasis3() != null) 
                propertyInformation.setBasis3(newAccount.getBasis3());
            if(newAccount.getBaseDate4() != null) 
                propertyInformation.setBaseDate4(newAccount.getBaseDate4());
            if(newAccount.getLandValue4() != null) 
                propertyInformation.setLandValue4(newAccount.getLandValue4());
            if(newAccount.getAuthority4() != null) 
                propertyInformation.setAuthority4(newAccount.getAuthority4());
            if(newAccount.getBasis4() != null) 
                propertyInformation.setBasis4(newAccount.getBasis4());
            if(newAccount.getBaseDate5() != null) 
                propertyInformation.setBaseDate5(newAccount.getBaseDate5());
            if(newAccount.getLandValue5() != null) 
                propertyInformation.setLandValue5(newAccount.getLandValue5());
            if(newAccount.getAuthority5() != null) 
                propertyInformation.setAuthority5(newAccount.getAuthority5());
            if(newAccount.getBasis5() != null) 
                propertyInformation.setBasis5(newAccount.getBasis5());
            if(newAccount.getFloorSpaceRatio() != null) 
                propertyInformation.setFloorSpaceRatio(newAccount.getFloorSpaceRatio());
            if(newAccount.getMinimumLotSize() != null) 
                propertyInformation.setMinimumLotSize(newAccount.getMinimumLotSize());
            propertyInformationRepository.save(propertyInformation);
            return ResponseEntity.ok(propertyInformation);
        }).orElseThrow(() -> new ResourceNotFoundException("Account [ID="+id+"] can't be found"));
    }

}
