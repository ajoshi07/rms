package com.rms.downstream;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.rms.exception.NoRecordException;
import com.rms.model.dto.Surcharge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Component
public class SurchargeCall {
    @Autowired
    RestTemplate restTemplate;

    @Value("${url}")
    String url;

    @HystrixCommand(fallbackMethod = "fallback")
   public Surcharge getSurchargeDetail()
   {
       try {
           return restTemplate.getForObject(url,Surcharge.class);
       }
       catch (Exception e)
       {
           System.out.println(e.getMessage());
           throw new NoRecordException();
       }

   }

    public Surcharge fallback() {
        System.out.println("DownStream down/timeout");
        return new Surcharge();
    }
}
