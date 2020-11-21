package com.rms;

import com.rms.exception.NoRecordException;
import com.rms.exception.NoValidRateDataException;
import com.rms.model.Rate;
import com.rms.model.dto.RateDTO;
import com.rms.repos.RateRepository;
import com.rms.services.impl.RateServiceImpl;
import com.rms.transformer.RateTransformer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;

import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class RateServiceImplTest {

    @InjectMocks
    RateServiceImpl rateService=new RateServiceImpl();

    @Mock
    RateRepository rateRepository;

    @Mock
    RateTransformer rateTransformer;

    @Test
    public void getRateDetailTest() {
        Long id=1L;
        Rate rate= Mockito.mock(Rate.class);
        Optional<Rate> optionalRate=Optional.ofNullable(rate);
        when(rateRepository.findById(id)).thenReturn(optionalRate);
        rateService.getRateDetail(id);

    }

    @Test(expected = NoRecordException.class)
    public void getRateDetailTestException() {
        Long id=1L;
        Rate rate= Mockito.mock(Rate.class);
        Optional<Rate> optionalRate=Optional.ofNullable(rate);
        when(rateRepository.findById(id)).thenThrow(new NoRecordException());
        rateService.getRateDetail(id);

    }

    @Test
    public void addRateTest() {
        Rate rate=Mockito.mock(Rate.class);
        when(rateRepository.save(rate)).thenReturn(rate);
        rateService.addRate(rate);

    }

    @Test(expected = Exception.class)
    public void addRateExceptionTest() {
        Rate rate=Mockito.mock(Rate.class);
        when(rateRepository.save(rate)).thenThrow(new Exception());
        rateService.addRate(rate);

    }

    @Test
    public void updateRateTest() {
        Rate rate=Mockito.mock(Rate.class);
        RateDTO rateDto=Mockito.mock(RateDTO.class);
        doNothing().when(rateTransformer).rateDataValidation(rateDto);
        when(rateRepository.save(rate)).thenReturn(rate);
        rateService.updateRate(rate,rateDto);
    }

    @Test(expected = NoValidRateDataException.class)
    public void updateRateExceptionTest() {
        Rate rate=Mockito.mock(Rate.class);
        RateDTO rateDto=Mockito.mock(RateDTO.class);
        doThrow(new NoValidRateDataException()).when(rateTransformer).rateDataValidation(rateDto);
        when(rateRepository.save(rate)).thenReturn(rate);
        rateService.updateRate(rate,rateDto);
    }


    @Test(expected = NoRecordException.class)
    public void deleteRateTest() {
        Long id=1l;
        doThrow(new NoRecordException()).when(rateRepository).deleteById(id);
        rateService.deleteRate(id);
    }
}
