package com.rewards.backend.app.security.token;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.rewards.backend.exception.CustomException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtHelper {
    //requirement :
    public static final long JWT_TOKEN_VALIDITY = 24 * 60 *60;

    
    //    public static final long JWT_TOKEN_VALIDITY =  60;
    private static final String SECRET = "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

//    public static Claims extractClaims(String jwtToken) throws SignatureException {
    	public static Claims extractClaims(String jwtToken) throws SecurityException {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(jwtToken)
                    .getBody();
        } catch (SecurityException e) {
            // Handle the exception, e.g., log and return null or rethrow
        	e.printStackTrace();
            throw new CustomException(e.getMessage());
        }
    }

    public String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
    
    //retrieve expiration date from jwt token2
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieveing any information from token we will need the secret key
//    public Claims getAllClaimsFromToken(String token) {
//        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
//    }
    
    public Claims getAllClaimsFromToken(String token) {
        JwtParser parser = Jwts.parserBuilder().setSigningKey(SECRET).build();
        return parser.parseClaimsJws(token).getBody();
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }
    
    //generate for customer
    public String generateTokenForCustomer(String email) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, email);
    }

    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   compaction of the JWT to a URL-safe string
    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject)
        		.setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
        //The method signWith(SignatureAlgorithm, String) from the type JwtBuilder is deprecated
    }
    
//    private String doGenerateToken(Map<String, Object> claims, String subject) {
//        SecretKey secretKey = createSecretKey(SECRET);
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(subject)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
//                .signWith(secretKey)
//                .compact();
//    }
    
    public String generateTokenWithClaims(String subject, Map<String, Object> customClaims) {
        return Jwts.builder()
                .setClaims(customClaims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        //The method signWith(SignatureAlgorithm, String) from the type JwtBuilder is deprecated
    }
   

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
//        return true;
        
			if(username.equals(userDetails.getUsername()))
        	{
        	return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        		
        	}
			else
	        	return false;
		
        
    }
    
    public String generateTokenWithClaimsWithoutSub(Map<String, Object> customClaims, String subject) {
        return Jwts.builder()
                .setClaims(customClaims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }


//	public String generateTokenWithClaimsWithoutSub(Map<String, Object> customClaims,String subject) {
//		// TODO Auto-generated method stub
//		return Jwts.builder()
//                .setClaims(customClaims)
//                .setSubject(subject)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
//                .signWith(SignatureAlgorithm.HS512, SECRET)
//                .compact();
//	}
    

}