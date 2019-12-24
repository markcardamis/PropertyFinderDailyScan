package com.majoapps.propertyfinderdailyscan.data.repository;

import java.util.List;

import com.majoapps.propertyfinderdailyscan.data.entity.PropertyInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyInformationRepository extends JpaRepository<PropertyInformation, Integer>,
        JpaSpecificationExecutor<PropertyInformation> {
}
