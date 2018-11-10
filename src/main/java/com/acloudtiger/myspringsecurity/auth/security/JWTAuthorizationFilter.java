package com.acloudtiger.myspringsecurity.auth.security;

import com.acloudtiger.myspringsecurity.auth.security.constants.SecurityConstant;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    private static Logger logger = LoggerFactory.getLogger(JWTAuthorizationFilter.class);

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }


    /**
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        logger.info("Entering doFilterInternal@JWTAuthorizationFilter");
        String header = request.getHeader(SecurityConstant.HEADER_STRING);
        logger.info("header:: {}", header);

        if(null == header || !header.startsWith(SecurityConstant.TOKEN_PREFIX)){
            logger.info("chaining");
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        logger.info("Authentication:: {}", authentication);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);

    }


    /**
     *
     * @param request
     * @return
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request){
        logger.info("Entering getAuthenticationToken@JWTAuthorizationFilter");
        String token = request.getHeader(SecurityConstant.HEADER_STRING);
        logger.info("token::{}", token);
        if(null != token){
            //Parsing JWT Token
            String user = Jwts.parser()
                    .setSigningKey(SecurityConstant.SECRET)
                    .parseClaimsJws(token.replace(SecurityConstant.TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();

            logger.info("user:: {}",user);
            if(null != user){
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
            return null;
        }
            return null;
    }
}
