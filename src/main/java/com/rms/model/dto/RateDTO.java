package com.rms.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@Setter
@Getter
@NoArgsConstructor
public class RateDTO {
    long id;
    String rateDesciption;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    Date reteEffectiveDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    Date rateExpirationDate;
    int Amount;
    Surcharge surcharge;
}
