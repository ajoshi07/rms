package com.rms.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity(name = "RATE")
@Setter
@Getter
@NoArgsConstructor
public class Rate {
    @Id
    @GeneratedValue
    long id;
    String rateDesciption;
    @Column(nullable = false)
    Date reteEffectiveDate;
    @Column(nullable = false)
    Date rateExpirationDate;
    @Column(nullable = false)
    int Amount;
}
