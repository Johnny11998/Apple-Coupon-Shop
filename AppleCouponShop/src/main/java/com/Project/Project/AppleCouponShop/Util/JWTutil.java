package com.Project.Project.AppleCouponShop.Util;

import com.Project.Project.AppleCouponShop.Exceptions.MajorException;
import com.Project.Project.AppleCouponShop.Exceptions.TokenExpiredException;
import com.Project.Project.AppleCouponShop.beans.UserDetails;
import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Data
public class JWTutil {
    //type of encryption - סוג של אלגורתים להצפנה
    private String signatureAlgorithm = SignatureAlgorithm.HS256.getJcaName();
    //our private key - מפתח ההצפנה שקיים רק אצלנו
    private String encodedSecretKey = "this+is+my+key+and+it+must+be+at+least+256+bits+long";
    //create our private key - יצירה של מפתח ההצפנה לשימוש ביצירה של הטוקנים שלנו
    private Key decodedSecrectKey = new SecretKeySpec(Base64.getDecoder().decode(encodedSecretKey), signatureAlgorithm);

    //generate key
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userType", userDetails.getUserType());
        //claims.put("userID", userDetails.getUserId());
        String myToken = createToken(claims, userDetails.getEmail());
        System.out.println("New token was created : " + myToken);
        return myToken;
        //return createToken(claims,userDetails.getEmail());
    }

    private String createToken(Map<String, Object> claims, String email) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(30, ChronoUnit.MINUTES)))
                .signWith(this.decodedSecrectKey)
                .compact();
    }

    public Claims extractAllClaims(String token) throws MajorException {
        try {
            JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(this.decodedSecrectKey).build();
            if (jwtParser == null) {
                throw new TokenExpiredException("Finally you came back! , please log in");
            }
            return jwtParser.parseClaimsJws(token).getBody();
        } catch (TokenExpiredException e) {
            e.getMessage();
        }
        return null;
    }

    public String extractEmail(String token) throws MajorException { // TokenExpiredException
        return extractAllClaims(token).getSubject();
    }

    private boolean isTokenExpired(String token) throws MajorException {
        try {
            extractAllClaims(token);
            return false;
        } catch (ExpiredJwtException | TokenExpiredException err) {
            token = null;
            err.getMessage();
            return true;
        }
    }
    public boolean validateToken(String token) throws MajorException {
        if (isTokenExpired(token)) {
            throw new TokenExpiredException("Finally you came back! , please log in");
        } return !isTokenExpired(token);
    }
    //    public static void main(String[] args){
//        UserDetails admin = new UserDetails("admin@admin.com", "12345","Admin");
//        JWTutil myutil = new JWTutil();
//        String myToken = myutil.generateToken(admin);
//        System.out.println("my new shiny token:\n"+myToken);
//    }
}

