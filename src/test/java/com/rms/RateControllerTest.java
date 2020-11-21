package com.rms;

import com.rms.controller.RateController;
import com.rms.downstream.SurchargeCall;
import com.rms.exception.NoRecordException;
import com.rms.exception.NoValidRateDataException;
import com.rms.model.Rate;
import com.rms.model.dto.RateDTO;
import com.rms.model.dto.Surcharge;
import com.rms.services.RateService;
import com.rms.services.impl.RateServiceImpl;
import com.rms.transformer.RateTransformer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.Silent.class)
public class RateControllerTest {

    @InjectMocks
    RateController controllerTest=new RateController();

    @Mock
    SurchargeCall surchargeCall;

    @Mock
    RateTransformer rateTransformer;

    @Mock
    RateService rateService;

    @Test
    public void getRateFromIDTest()
    {
        Long id=1L;
        Rate rate= Mockito.mock(Rate.class);
        Surcharge surcharge= Mockito.mock(Surcharge.class);
        RateDTO rateDTO=Mockito.mock(RateDTO.class);
        when(rateService.getRateDetail(id)).thenReturn(rate);
        when(surchargeCall.getSurchargeDetail()).thenReturn(surcharge);
        when(rateTransformer.transformRateToRateDTO(surcharge,rate)).thenReturn(rateDTO);
        controllerTest.getRateFromID(id);
    }

    @Test
    public void addRateTest()
    {
        Rate rate= Mockito.mock(Rate.class);
        RateDTO rateDTO=Mockito.mock(RateDTO.class);
        when(rateTransformer.convertToRateDAO(rateDTO)).thenReturn(rate);
        doNothing().when(rateService).addRate(rate);
        controllerTest.addRate(rateDTO);
    }

    @Test
    public void updateRateTest()
    {
        Long id=1L;
        Rate rate= Mockito.mock(Rate.class);
        RateDTO rateDTO=Mockito.mock(RateDTO.class);
        when(rateService.getRateDetail(id)).thenReturn(rate);
        doNothing().when(rateService).updateRate(rate,rateDTO);
        controllerTest.updateRate(rateDTO);
    }

    @Test
    public void updateRateTestWithException()
    {
        Long id=1L;
        Rate rate= Mockito.mock(Rate.class);
        RateServiceImpl rateService=Mockito.mock(RateServiceImpl.class);
        RateDTO rateDTO=new RateDTO();
        rateDTO.setAmount(-1);
        when(rateService.getRateDetail(id)).thenThrow(new NoRecordException());
        doNothing().when(rateService).updateRate(rate,rateDTO);
        controllerTest.updateRate(rateDTO);
    }

    @Test
    public void deleteRateTest()
    {
        Long id=1L;
        doNothing().when(rateService).deleteRate(id);
        controllerTest.deleteRate(id);
    }

}
