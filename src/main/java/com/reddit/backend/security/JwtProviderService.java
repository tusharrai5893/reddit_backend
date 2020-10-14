package com.reddit.backend.security;

import com.reddit.backend.exceptions.RedditCustomException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.time.Instant;

import static java.util.Date.from;

@Service
public class JwtProviderService {

    private KeyStore keyStore;

    @PostConstruct
    public void init(){
        try {
            keyStore= KeyStore.getInstance("JKS");
            InputStream keyStoreStream = getClass().getResourceAsStream("/springblog.jks");
            keyStore.load(keyStoreStream,"secret".toCharArray());
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
            throw new RedditCustomException("Error in JwtProviderService class while loading "+ e);
        }
    }

    public String generateJWToken(Authentication authentication){
        UserDetailsImpl loggingUser = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder().setSubject(loggingUser.getUsername())
                .setIssuedAt(from(Instant.now()))
                .signWith(getPrivateKey())
                .compact();
    }

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("springblog","secret".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new RedditCustomException("Exception occured while retrieving public key from keystore "+ e);
        }
    }

}
