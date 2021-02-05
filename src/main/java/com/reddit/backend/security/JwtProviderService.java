package com.reddit.backend.security;

import com.reddit.backend.exceptions.RedditCustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.sql.Date;
import java.time.Instant;

import static java.util.Date.from;

@Service
public class JwtProviderService {

    private KeyStore keyStore;

    @Value("${jwt.expire.time}")
    private long jwtExpirationTimeInMillis;

    //Secret pass for jks file
    private static final String SECRET = "secret";

    @PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream keyStoreStream = getClass().getResourceAsStream("/springblog.jks");
            keyStore.load(keyStoreStream, SECRET.toCharArray());
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
            throw new RedditCustomException("Error in JwtProviderService class while loading " + e);
        }
    }

    public String generateJWToken(Authentication authentication) {
        UserDetailsImpl loggingUser = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder().setSubject(loggingUser.getUsername())
                .setIssuedAt(from(Instant.now()))
                .signWith(getPrivateKey())
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationTimeInMillis)))
                .compact();
    }


    public String generateRefreshJWToken(String userName) {

        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(from(Instant.now()))
                .signWith(getPrivateKey())
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationTimeInMillis)))
                .compact();
    }

    public long getJwtExpirationTimeInMillis() {
        return jwtExpirationTimeInMillis;
    }

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("springblog", "secret".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new RedditCustomException("Exception occured while retrieving public key from keystore " + e);
        }
    }

    public boolean validateJwtToken(String JwtToken) {
        try {
            Jws<Claims> isValidated = Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(JwtToken);
        } catch (JwtException e) {
            System.err.print("error while parsing jwt token");
        }
        return true;

    }

    private PublicKey getPublicKey() {
        try {
            return keyStore.getCertificate("springblog").getPublicKey();
        } catch (KeyStoreException e) {
            throw new RedditCustomException("Error while getting Certificate in Public key method JWT provider class " + e);
        }
    }

    public String getUsernameFromJwt(String jwtToken) {
        Claims claims = Jwts.parser().setSigningKey(getPublicKey())
                .parseClaimsJws(jwtToken)
                .getBody();

        return claims.getSubject();
    }

}
