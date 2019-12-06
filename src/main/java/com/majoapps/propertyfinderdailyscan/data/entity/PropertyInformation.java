package com.majoapps.propertyfinderdailyscan.data.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
@Table(name="property_information")
public class PropertyInformation {
    
    @Column(name = "district_code")
    private short districtCode;
    @Column(name="district_name")
    private String districtName;
    @Id
    @Column(name="property_id")
    private Integer propertyId;
    @Column(name="property_type")
    private String propertyType;
    @Column(name = "property_name")
    private String propertyName;
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
    @Column(name="zone_code")
    private String zoneCode;
    @Column(name="area")
    private BigDecimal area;
    @Column(name="area_type")
    private String areaType;
    @Temporal(TemporalType.DATE)
    @Column(name="base_date_1")
    private Date baseDate1;
    @Column(name="land_value_1")
    private Integer landValue1;
    @Column(name="authority_1")
    private String authority1;
    @Column(name="basis_1")
    private String basis1;
    @Temporal(TemporalType.DATE)
    @Column(name="base_date_2")
    private Date baseDate2;
    @Column(name="land_value_2")
    private Integer landValue2;
    @Column(name="authority_2")
    private String authority2;
    @Column(name="basis_2")
    private String basis2;
    @Temporal(TemporalType.DATE)
    @Column(name="base_date_3")
    private Date baseDate3;
    @Column(name="land_value_3")
    private Integer landValue3;
    @Column(name="authority_3")
    private String authority3;
    @Column(name="basis_3")
    private String basis3;
    @Temporal(TemporalType.DATE)
    @Column(name="base_date_4")
    private Date baseDate4;
    @Column(name="land_value_4")
    private Integer landValue4;
    @Column(name="authority_4")
    private String authority4;
    @Column(name="basis_4")
    private String basis4;
    @Temporal(TemporalType.DATE)
    @Column(name="base_date_5")
    private Date baseDate5;
    @Column(name="land_value_5")
    private Integer landValue5;
    @Column(name="authority_5")
    private String authority5;
    @Column(name="basis_5")
    private String basis5;
    @Column(name="floor_space_ratio")
    private BigDecimal floorSpaceRatio;
    @Column(name="minimum_lot_size")
    private String minimumLotSize;
    @Column(name="building_height")
    private BigDecimal buildingHeight;

}