package project.testmaster.backend.utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtUtils {
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private Long expiration;

    public JwtUtils(String privateKeyPath, String publicKeyPath, Long expiration) throws Exception {
        this.privateKey = loadPrivateKeyFromPem(privateKeyPath);
        this.publicKey = loadPublicKeyFromPem(publicKeyPath);
        this.expiration = expiration;
    }

    private PrivateKey loadPrivateKeyFromPem(String filePath) throws Exception {
        String key = new String(Files.readAllBytes(Paths.get(filePath)));
        key = key.replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");
        byte[] keyBytes = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(spec);
    }

    private PublicKey loadPublicKeyFromPem(String filePath) throws Exception {
        String key = new String(Files.readAllBytes(Paths.get(filePath)));
        key = key.replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
        byte[] keyBytes = Base64.getDecoder().decode(key);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }

    public String generateToken(String username, String role) {
        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusSeconds(this.expiration)))
                .signWith(this.privateKey)
                .compact();
    }

    public Claims getClaimsFromToken(String token) {
        return (Claims) Jwts.parser()
                .verifyWith(this.publicKey)
                .build()
                .parse(token)
                .getPayload();
    }

    public boolean validateToken(String token) {
        return getClaimsFromToken(token).getExpiration().after(new Date());
    }
}
