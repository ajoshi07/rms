package com.rms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class AppConfig {

   @Bean
   public RestTemplate getTemplate(RestTemplateBuilder builder) throws NoSuchAlgorithmException, KeyManagementException {

       TrustManager[] trustAllCerts = new TrustManager[] {
               new X509TrustManager() {
                   public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                       return new X509Certificate[0];
                   }
                   public void checkClientTrusted(
                           java.security.cert.X509Certificate[] certs, String authType) {
                   }
                   public void checkServerTrusted(
                           java.security.cert.X509Certificate[] certs, String authType) {
                   }
               }
       };
       SSLContext sslContext = SSLContext.getInstance("SSL");
       sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
       CloseableHttpClient httpClient = HttpClients.custom()
               .setSSLContext(sslContext)
               .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
               .build();
       HttpComponentsClientHttpRequestFactory customRequestFactory = new HttpComponentsClientHttpRequestFactory();
       customRequestFactory.setHttpClient(httpClient);
       RestTemplate restTemplate=builder.requestFactory(() -> customRequestFactory).build();
       List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
       MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
       converter.setSupportedMediaTypes(Collections.singletonList(MediaType.TEXT_PLAIN));
       messageConverters.add(converter);
       restTemplate.setMessageConverters(messageConverters);
       return restTemplate;


   }


}
