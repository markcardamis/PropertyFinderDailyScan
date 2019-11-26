package com.majoapps.propertyfinderdailyscan.data.repository;

import com.majoapps.propertyfinderdailyscan.data.entity.PropertyInformation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyInformationRepository extends CrudRepository<PropertyInformation, Integer> {
}
