package com.eworld.configuration;

import com.eworld.configuration.security.UserContext;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtServiceProvider {
    private  final String JWT_SECRET = System.getProperty("JWT_SECRET");

    private  final long JWT_EXPIRATION = 86400L;

    public String generateToken(UserContext userContext){
        Date now = new Date();
        Date expirtyDate = new Date(now.getTime() + JWT_EXPIRATION);
        String token = Jwts.builder()
                .setSubject(userContext.getUsername())
                .setIssuedAt(now)
                .setExpiration(expirtyDate)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
        return token;

    }

    public String getUserNameFromJwt(String token){
        Claims claims = Jwts.parser()
                            .setSigningKey(JWT_SECRET)
                            .parseClaimsJws(token)
                            .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String authToken){
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        }
        catch (MalformedJwtException ex){
            log.error("Invalid JWT token");
        }
        catch (ExpiredJwtException ex){
            log.error("Expired JWT token");
        }
        catch (UnsupportedJwtException ex){
            log.error("Unsupport jwt token");
        }
        catch (IllegalArgumentException ex){
            log.error("JWT claims String is empty");
        }
        return false;
    }


}
