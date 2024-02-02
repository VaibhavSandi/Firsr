package com.webproby.demo.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.webproby.demo.service.CustomUserDeatils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter{
	
	
	private Logger logger=LoggerFactory.getLogger(JWTAuthenticationFilter.class);
	
	@Autowired
	
	private CustomUserDeatils usd;
	
	
	@Autowired
	JWTHelper jwt;

	@Override
       
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		//Authorization=Beaarer qgyqtyeqwq;
		String requestheader=request.getHeader("Authorization");
		
		logger.info("Header : {}",requestheader);
		
		String username=null;
		String token=null;
		
		
		if(requestheader!=null && requestheader.startsWith("Bearer"))
		{
			token=requestheader.substring(7);
			
			try
			{
			username=jwt.getUsernameFromToken(token);
			}
			
			 catch (IllegalArgumentException e) {
	                logger.info("Illegal Argument while fetching the username !!");
	                e.printStackTrace();
	            } catch (ExpiredJwtException e) {
	                logger.info("Given jwt token is expired !!");
	                e.printStackTrace();
	            } catch (MalformedJwtException e) {
	                logger.info("Some changed has done in token !! Invalid Token");
	                e.printStackTrace();
	            } catch (Exception e) {
	                e.printStackTrace();

	        }
		}
			else
			{
				logger.info("Invaild token5");
			}
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
		{
			UserDetails userdetails=this.usd.loadUserByUsername(username);
			
			Boolean vaildateToken=this.jwt.validateToken(token, userdetails);
			
			if(vaildateToken)
			{
				// set Authentication,
				UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(userdetails,null, userdetails.getAuthorities());
				
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
			}
			else
				logger.info("vaildation failed!!");
		}
		
		
		filterChain.doFilter(request, response);
		
		
	}

}
	