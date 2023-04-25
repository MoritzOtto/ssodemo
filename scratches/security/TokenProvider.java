package de.biga.ssodemo.security;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;

@Service
public class TokenProvider {
    @Value("${tenantId}")
    private String tenantId;

    @Value("${clientId}")
    private String clientId;

    @Value("${audience}")
    private String audience;

    public boolean validateToken(String token) {
        if (token == null) {
            return false;
        }

        DecodedJWT jwt = JWT.decode(token);

        try {
            if (Instant.now().isAfter(jwt.getExpiresAtAsInstant())) {
                return false;
            }

            if (jwt.getAudience().stream().noneMatch(it -> it.equals(audience))) {
                return false;
            }

            JwkProvider provider = new UrlJwkProvider(new URL("https://login.microsoftonline.com/" + tenantId + "/discovery/keys?appid=" + clientId));
            Jwk jwk = provider.get(jwt.getKeyId());
            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);
            algorithm.verify(jwt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
