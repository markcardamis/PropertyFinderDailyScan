package com.majoapps.propertyfinderdailyscan.data.entity;

import com.majoapps.propertyfinderdailyscan.data.enums.Frequency;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@Table(name="NOTIFICATIONS")
public class Notifications extends AuditModel {
    private static final long serialVersionUID = -4880020817874864117L;
    
    @ManyToOne(targetEntity = Account.class)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
    @Column(name="title")
    private String title;
    @Column(name="frequency")
    private Frequency frequency;
    @Column(name="property_zone")
    private String propertyZone;
    @Column(name="property_area_min")
    private Integer propertyAreaMin;
    @Column(name="property_area_max")
    private Integer propertyAreaMax;
    @Column(name="property_price_min")
    private Integer propertyPriceMin;
    @Column(name="property_price_max")
    private Integer propertyPriceMax;
    @Column(name="property_price_psm_min")
    private Integer propertyPricePSMMin;
    @Column(name="property_price_psm_max")
    private Integer propertyPricePSMMax;
    @Column(name="property_post_code")
    private String propertyPostCode;
    @Column(name="property_price_to_land_value_min")
    private BigDecimal propertyPriceToLandValueMin;
    @Column(name="property_price_to_land_value_max")
    private BigDecimal propertyPriceToLandValueMax;
    @Column(name="property_floor_space_ratio_min")
    private BigDecimal propertyFloorSpaceRatioMin;
    @Column(name="property_floor_space_ratio_max")
    private BigDecimal propertyFloorSpaceRatioMax;
}