package com.rms.security.resourcehandler;

import com.rms.security.model.AuthResponse;
import com.rms.security.service.JwtUtils;
import com.rms.security.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@RestController
public class SecurityResource {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
     private MyUserDetailService userDetailService;

     private Supplier<BadCredentialsException> throwableSupplier=()->new BadCredentialsException("Bad Request");

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<AuthResponse> getAuthToken(@RequestHeader HttpHeaders headers) {
         Optional.ofNullable(headers).orElseThrow(throwableSupplier);
         Optional<String> userName=Optional.of(headers.get("username").stream().findFirst().get());
         Optional<String> password=Optional.of(headers.get("password").stream().findFirst().get());
         userName.orElseThrow(throwableSupplier);
         password.orElseThrow(throwableSupplier);
         try {
             authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName.get(),password.get(),new ArrayList<>()));
         }catch (BadCredentialsException e)
         {
             throw new UsernameNotFoundException("User Not Found",e);
         }
         UserDetails userDetails=userDetailService.loadUserByUsername(userName.get());

         AuthResponse authResponse=new AuthResponse();
         authResponse.setAuthToken(jwtUtils.generateToken(userDetails));

         return new ResponseEntity<>(authResponse , HttpStatus.OK);
    }
}
