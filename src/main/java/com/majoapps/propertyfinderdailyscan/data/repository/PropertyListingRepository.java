package com.majoapps.propertyfinderdailyscan.data.repository;

import com.majoapps.propertyfinderdailyscan.data.entity.PropertyListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyListingRepository extends JpaRepository<PropertyListing, Integer> {
}
