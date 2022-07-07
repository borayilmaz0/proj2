package com.proj2.proj2.security;

import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {
    private String APP_SECRET = "secret";
    private long EXPIRES_IN = 86400;

    public String genereteJwtToken(Authentication authentication)
    {
        JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();
        Date expireDate = new Date(new Date().getTime() + (EXPIRES_IN * 1000));
        return Jwts.builder()
            .setSubject(Long.toString(userDetails.getId()))
            .setIssuedAt(new Date())
            .setExpiration(expireDate)
            .signWith(SignatureAlgorithm.HS512, APP_SECRET)
            .compact();
    }

    public Long getUserIdFromJwt(String token)
    {
        Claims claims = Jwts.parser()
            .setSigningKey(APP_SECRET)
            .parseClaimsJws(token)
            .getBody();
        
        return Long.parseLong(claims.getSubject());
    }

    public boolean isTokenValid(String token)
    {
        try {
            Jwts.parser()
            .setSigningKey(APP_SECRET)
            .parseClaimsJws(token);
            return !isTokenExpired(token);

        } catch(SignatureException e) {
            System.out.println("SIGNATURE EXCEPTION");
            return false;
        } catch (MalformedJwtException e) {
            System.out.println("JWT IS MALFORMED");
            return false;
        } catch(ExpiredJwtException e) {
            System.out.println("JWT IS EXPIRED");
            return false;
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT IS UNSUPORTED");
            return false;
        } catch(IllegalArgumentException e) {
            System.out.println("ILLEGAL ARGUMENT");
            return false;
        } catch(Exception e) {
            System.out.println("UNKNOWN ERROR");
            return false;
        }
    }

    public boolean isTokenExpired(String token)
    {
        Date expDate = Jwts.parser()
        .setSigningKey(APP_SECRET)
        .parseClaimsJws(token)
        .getBody()
        .getExpiration();
        return expDate.before(new Date());
    }
}
