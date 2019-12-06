package com.majoapps.propertyfinderdailyscan.data.entity;

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
    @Column(name="planning_zone")
    private String planningZone;
    @Column(name="property_area_min")
    private int propertyAreaMin;
    @Column(name="property_area_max")
    private int propertyAreaMax;
    @Column(name="property_price_min")
    private int propertyPriceMin;
    @Column(name="property_price_max")
    private int propertyPriceMax;
    @Column(name="property_psm_min")
    private int propertyPSMMin;
    @Column(name="property_psm_max")
    private int propertyPSMMax;
    @Column(name="property_post_code")
    private String propertyPostCode;
    @Column(name="property_price_to_land_value_min")
    private BigDecimal propertyPriceToLandValueMin;
    @Column(name="property_price_to_land_value_max")
    private BigDecimal propertyPriceToLandValueMax;
}

