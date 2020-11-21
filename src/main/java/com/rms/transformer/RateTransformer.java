package com.rms.transformer;

import com.rms.exception.NoValidRateDataException;
import com.rms.model.Rate;
import com.rms.model.dto.RateDTO;
import com.rms.model.dto.Surcharge;
import org.springframework.stereotype.Component;

@Component
public class RateTransformer {

    public Rate convertToRateDAO(RateDTO rateDTO) {
        Rate rate=new Rate();
        rateDataValidation(rateDTO);
        rate.setAmount(rateDTO.getAmount());
        rate.setRateDesciption(rateDTO.getRateDesciption());
        rate.setRateExpirationDate(rateDTO.getRateExpirationDate());
        rate.setReteEffectiveDate(rateDTO.getReteEffectiveDate());
        return rate;
    }

    public RateDTO transformRateToRateDTO(Surcharge surcharge, Rate rate) {
        RateDTO rateDTO=new RateDTO();
        rateDTO.setId(rate.getId());
        rateDTO.setAmount(rate.getAmount());
        rateDTO.setRateDesciption(rate.getRateDesciption());
        rateDTO.setRateExpirationDate(rate.getRateExpirationDate());
        rateDTO.setReteEffectiveDate(rate.getReteEffectiveDate());
        rateDTO.setSurcharge(surcharge);
        return rateDTO;

    }

    public void rateDataValidation(RateDTO rateDTO)
    {
        if(rateDTO.getAmount()<0 || rateDTO.getRateExpirationDate()==null||rateDTO.getReteEffectiveDate()==null)
            throw new NoValidRateDataException();
    }
}
