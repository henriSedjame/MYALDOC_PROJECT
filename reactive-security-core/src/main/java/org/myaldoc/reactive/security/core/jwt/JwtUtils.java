package org.myaldoc.reactive.security.core.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.myaldoc.reactive.security.core.models.Role;
import org.myaldoc.reactive.security.core.models.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Project REACTIVE_SECURITY_JWT
 * @Author Henri Joel SEDJAME
 * @Date 31/12/2018
 * @Class purposes : .......
 */
@Component
public class JwtUtils implements Serializable {

    public static final String ROLES = "roles";

    //********************************************************************************************************************
    // ATTRIBUTS
    //********************************************************************************************************************

    private final JwtProperties jwtProperties;
    private SecretKey  key = Keys.secretKeyFor(SignatureAlgorithm.HS512);


    //********************************************************************************************************************
    // CONSTRUCTEUR
    //********************************************************************************************************************

    public JwtUtils(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    //********************************************************************************************************************
    // METHODES
    //********************************************************************************************************************

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(Base64.getEncoder().encodeToString(jwtProperties.getSecret().getBytes())).parseClaimsJws(token).getBody();
    }

    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(ROLES, user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList()));
        return doGenerateToken(claims, user.getUsername());
    }

    public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private String doGenerateToken(Map<String, Object> claims, String username) {
        Long expirationTimeLong = Long.parseLong(jwtProperties.getExpiration()); //in second

        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                //.signWith(key)
                .signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encodeToString(jwtProperties.getSecret().getBytes()))
                .compact();
    }

}
