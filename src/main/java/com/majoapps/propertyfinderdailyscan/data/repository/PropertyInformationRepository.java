package com.majoapps.propertyfinderdailyscan.data.repository;

import java.util.List;

import com.majoapps.propertyfinderdailyscan.data.entity.PropertyInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyInformationRepository extends JpaRepository<PropertyInformation, Integer> {

        @Query(value = "SELECT concat_ws(',', property_id, concat_ws(' ', unit_number, house_number, street_name, suburb_name, post_code)) FROM property_information WHERE  to_tsvector('simple', f_concat_ws(' ', unit_number, house_number, street_name, suburb_name, post_code)) @@ plainto_tsquery('simple', ?1) LIMIT 1", nativeQuery = true)
        List<String> findByAddress(String address);

}
