package com.rms.services;


import com.rms.model.Rate;
import com.rms.model.dto.RateDTO;


public interface RateService {

    public Rate getRateDetail(long id);

    public void addRate(Rate rate);

    public void updateRate(Rate rate, RateDTO rateDTO);

    public void deleteRate(long id);

}
