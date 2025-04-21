package com.tonilr.ClassManager.Security;


import com.tonilr.ClassManager.Model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Base64;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

@Component
@RequiredArgsConstructor

public class JwtUtil {

	 @Value("${app.jwt.secret}")
	    private String secret;

	    @Value("${app.jwt.expiration}")
	    private long expiration;

	    public String extractUsername(String token) {
	        return extractClaim(token, Claims::getSubject);
	    }

	    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
	        final Claims claims = extractAllClaims(token);
	        return claimsResolver.apply(claims);
	    }

	    private Claims extractAllClaims(String token) {
	        return Jwts.parserBuilder()
	        	    .setSigningKey(getSigningKey())
	        	    .build()
	        	    .parseClaimsJws(token)
	        	    .getBody();
	    }
	    
	    private SecretKey getSigningKey() {
	        byte[] decodedKey = Base64.getDecoder().decode(secret);
	        return Keys.hmacShaKeyFor(decodedKey);	    }

	    public String generateToken(User user) {
	        return Jwts.builder()
	                .setSubject(user.getUsername())
	                .setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis() + expiration))
	                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
	                .compact();
	    }

	    public boolean isTokenValid(String token, User user) {
	        final String username = extractUsername(token);
	        return (username.equals(user.getUsername()) && !isTokenExpired(token));
	    }

	    private boolean isTokenExpired(String token) {
	        return extractExpiration(token).before(new Date());
	    }

	    private Date extractExpiration(String token) {
	        return extractClaim(token, Claims::getExpiration);
	    }
	
}
