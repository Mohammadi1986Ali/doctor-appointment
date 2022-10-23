package com.blubank.doctorappointment.web.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.function.Function;

import static java.util.stream.Collectors.joining;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    private final Environment env;
    private final Logger logger;

    public String generateAccessToken(UserDetails userDetails) throws JOSEException {
        var now = Instant.now();
        var ttl = env.getProperty("token.ttl", Integer.class);
        var authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(joining(" "));

        JWSSigner signer = new MACSigner(env.getProperty("token.secret"));

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .issuer("blubank.com")
                .issueTime(Date.from(now))
                .subject(userDetails.getUsername())
                .claim("roles", authorities)
                .expirationTime(Date.from(now.plusSeconds(ttl)))
                .build();

        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
        signedJWT.sign(signer);
        return signedJWT.serialize();
    }

    public boolean validate(String token) {
        try {
            SignedJWT jwt = SignedJWT.parse(token);
            MACVerifier verifier = new MACVerifier(env.getProperty("token.secret"));
            return jwt.verify(verifier);
        } catch (ParseException | JOSEException e) {
            logger.warn("JWT token could not validated", e.getMessage());
            return false;
        }
    }

    public String getUsername(String token) throws ParseException {
        return getClaim(token, JWTClaimsSet::getSubject);
    }

    public Date getExpirationDate(String token) throws ParseException {
        return getClaim(token, JWTClaimsSet::getExpirationTime);
    }

    public <T> T getClaim(String token, Function<JWTClaimsSet, T> claimsResolver) throws ParseException {
        final JWTClaimsSet claims = parseToken(token);
        return claimsResolver.apply(claims);
    }

    private JWTClaimsSet parseToken(String token) throws ParseException {
        return SignedJWT.parse(token).getJWTClaimsSet();
    }
}
