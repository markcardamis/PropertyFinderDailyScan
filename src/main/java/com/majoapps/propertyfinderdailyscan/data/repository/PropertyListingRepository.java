package com.majoapps.propertyfinderdailyscan.data.repository;

import com.majoapps.propertyfinderdailyscan.data.entity.PropertyListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PropertyListingRepository extends JpaRepository<PropertyListing, Integer> {
    List<PropertyListing> findByDomainListingId(Integer domainListingId);
}
