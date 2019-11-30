package com.majoapps.propertyfinderdailyscan.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.Data;

@Entity
@Data
@Table(name="LISTING")
public class PropertyListing {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name="time_date")
    private String timeDate;
    @Column(name="domain_listing_id")
    private Integer domainListingId;
    @Column(name="price")
    private String price;
    @Column(name="listing_url")
    private String listingURL;
    @Column(name="listing_photo")
    private String listingPhoto;
    @Column(name="address")
    private String address;
    @Column(name="unit_number")
    private String unitNumber;
    @Column(name="house_number")
    private String houseNumber;
    @Column(name="street_name")
    private String streetName;
    @Column(name = "suburb_name")
    private String suburbName;
    @Column(name="post_code")
    private String postCode;
    @Column(name="area")
    private Integer area;
    @Column(name="bathrooms")
    private Double bathrooms;
    @Column(name="bedrooms")
    private Double bedrooms;
    @Column(name="carspaces")
    private Integer carspaces;
    @Column(name="latitude")
    private Double latitude;
    @Column(name="longitude")
    private Double longitude;
    @Column(name="summary_description")
    private String summaryDescription;
    @Column(name="planning_portal_id")
    private String planningPortalPropId;
    @Column(name="planning_portal_address")
    private String planningPortalAddress;

    @Column(name="deleted")
    private boolean deleted;
}