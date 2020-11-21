package com.rms.services.impl;


import com.rms.exception.NoRecordException;
import com.rms.exception.NoValidRateDataException;
import com.rms.model.Rate;
import com.rms.model.dto.RateDTO;
import com.rms.repos.RateRepository;
import com.rms.services.RateService;
import com.rms.transformer.RateTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service(value = "RateServiceImpl")
public class RateServiceImpl  implements RateService {

    @Autowired
    RateRepository rateRepository;

    @Autowired
    RateTransformer rateTransformer;

    public Rate getRateDetail(long id) {
        Optional<Rate> rate=rateRepository.findById(id);
        rate.orElseThrow(NoRecordException::new);
        return rate.get();

    }

    @Override
    public void addRate(Rate rate) {
        try {
            rateRepository.save(rate);
        }
        catch (DataAccessException e)
        {
            throw new NoValidRateDataException();
        }

    }

    @Override
    public void updateRate(Rate rate,RateDTO rateDTO) {
        try {
            rateTransformer.rateDataValidation(rateDTO);
            rate.setAmount(rateDTO.getAmount());
            rate.setRateDesciption(rateDTO.getRateDesciption());
            rate.setRateExpirationDate(rateDTO.getRateExpirationDate());
            rate.setReteEffectiveDate(rateDTO.getReteEffectiveDate());
            rateRepository.save(rate);
        }
        catch (DataAccessException ex)
        {
            throw new NoValidRateDataException();
        }
    }

    @Override
    public void deleteRate(long id) {
        try {
            rateRepository.deleteById(id);
        }
        catch (DataAccessException exception)
        {
            throw  new NoRecordException();
        }

    }
}
