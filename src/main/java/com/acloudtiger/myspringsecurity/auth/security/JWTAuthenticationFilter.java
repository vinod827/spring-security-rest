package com.acloudtiger.myspringsecurity.auth.security;

import com.acloudtiger.myspringsecurity.entity.ApplicationUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import com.acloudtiger.myspringsecurity.auth.security.constants.SecurityConstant;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    private AuthenticationManager authenticationManager;


    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        logger.info("Entering attemptAuthentication@JWTAuthenticationFilter");

        try{
            ApplicationUser credential = new ObjectMapper()
                    .readValue(request.getInputStream(), ApplicationUser.class);

            logger.info("Validating Credential:: {}, {}",credential.getUsername(), credential.getPassword());

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(credential.getUsername(),
                            credential.getPassword(),
                            new ArrayList<>()));

        }catch(IOException io){
            throw new RuntimeException(io);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
            throws IOException, ServletException {

        logger.info("Entering successfulAuthentication@JWTAuthenticationFilter");
        String jwtToken = Jwts.builder()
                .setSubject(((User) authResult.getPrincipal()).getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstant.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstant.SECRET)
                .compact();

        logger.info("JWT Token:: {}", jwtToken);
        response.addHeader(SecurityConstant.HEADER_STRING, SecurityConstant.TOKEN_PREFIX + jwtToken);

    }
}
