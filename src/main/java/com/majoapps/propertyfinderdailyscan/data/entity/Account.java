package com.majoapps.propertyfinderdailyscan.data.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@Table(name="ACCOUNT")
public class Account extends AuditModel{
    private static final long serialVersionUID = -2785806030291416426L;
    
    @Column(name = "user_id")
    private String userId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name="last_login")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;

}
