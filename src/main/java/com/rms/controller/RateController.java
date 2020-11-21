package com.rms.controller;


import com.rms.downstream.SurchargeCall;
import com.rms.exception.NoValidRateDataException;
import com.rms.model.Rate;
import com.rms.model.dto.RateDTO;
import com.rms.model.dto.Surcharge;
import com.rms.services.RateService;
import com.rms.transformer.RateTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rms")
public class RateController {

    Logger logger = LoggerFactory.getLogger(RateController.class);

    @Autowired
    RateService rateService;

    @Autowired
    SurchargeCall surchargeCall;

    @Autowired
    RateTransformer rateTransformer;

    @GetMapping(value = "/getRate/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RateDTO getRateFromID(@PathVariable long id)
    {
        logger.info("getRateFromId called with id "+id);
        Rate rate=rateService.getRateDetail(id);
        Surcharge surcharge=surchargeCall.getSurchargeDetail();
        RateDTO rateDTO=rateTransformer.transformRateToRateDTO(surcharge,rate);
        return rateDTO;
    }

    @PostMapping(value = "/addRate")
    @ResponseStatus(HttpStatus.CREATED)
    public void addRate(@RequestBody RateDTO rateDTO)
    {
         Rate rate=rateTransformer.convertToRateDAO(rateDTO);
         rateService.addRate(rate);
    }

    @PutMapping(value = "/updateRate")
    @ResponseStatus(HttpStatus.OK)
    public void updateRate(@RequestBody RateDTO rateDTO)
    {
        try {
            Rate rate=rateService.getRateDetail(rateDTO.getId());
            rateService.updateRate(rate,rateDTO);
        }
        catch (Exception ex)
        {
            throw  new NoValidRateDataException();
        }
    }

    @DeleteMapping(value = "/deleteRate/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteRate(@PathVariable long id)
    {
        rateService.deleteRate(id);
    }


}
