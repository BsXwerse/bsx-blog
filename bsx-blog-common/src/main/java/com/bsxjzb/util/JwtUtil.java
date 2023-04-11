package com.bsxjzb.util;

import com.bsxjzb.exception.SystemException;
import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class JwtUtil {
    public static Long JWT_TTL = 24 * 60 * 60 * 1000L;
    public static String JWT_KEY = "madohomuhomu";
    public static SecretKey key;

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static SecretKey generalKey() {
        if (key != null) return key;
        byte[] bytes = Base64.getDecoder().decode(JWT_KEY);
        SecretKeySpec secretKeySpec = new SecretKeySpec(bytes, 0, bytes.length, "AES");
        key = secretKeySpec;
        return key;
    }

    public static JwtBuilder getJwtBuilder(String subject, Long ttl, String uuid) {
        SignatureAlgorithm hs256 = SignatureAlgorithm.HS256;
        SecretKey key = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        long expMillis = nowMillis + ttl;
        Date expDate = new Date(expMillis);
        return Jwts.builder()
                .setId(uuid)
                .setSubject(subject)
                .setIssuer("bsx-jzb")
                .setIssuedAt(now)
                .signWith(hs256, key)
                .setExpiration(expDate);
    }

    public static String createJwt(String subject) {
        return getJwtBuilder(subject, JWT_TTL, getUUID()).compact();
    }

    public static String createJwt(String subject, Long tll) {
        return getJwtBuilder(subject, tll, getUUID()).compact();
    }

    public static Claims parseJwt(String jwt) throws Exception{
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }
}
