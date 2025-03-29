package project.testmaster.backend.utils;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

import org.springframework.core.io.ClassPathResource;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * Utility class for handling JSON Web Tokens (JWT).
 *
 * The `JwtUtils` class provides methods for creating, parsing, and validating
 * JWT tokens. It utilizes RSA private and public keys for signing and verifying
 * tokens. This class supports loading RSA keys from PEM files and ensures JWT
 * expiration handling.
 */
public class JwtUtils {
    private final PrivateKey privateKey;
    private final PublicKey publicKey;
    private final Long expiration;

    /**
     * Constructs a new JwtUtils instance.
     *
     * This constructor initializes the utility class by loading the provided RSA private
     * and public keys from PEM files and sets the token expiration duration. It throws an
     * exception if the keys cannot be loaded properly.
     *
     * @param privateKeyPath the file path to the PEM-encoded RSA private key
     * @param publicKeyPath the file path to the PEM-encoded RSA public key
     * @param expiration the expiration time in seconds for the JWT tokens
     * @throws Exception if an error occurs while loading the RSA keys
     */
    public JwtUtils(String privateKeyPath, String publicKeyPath, Long expiration) throws Exception {
        this.privateKey = loadPrivateKeyFromPem(privateKeyPath);
        this.publicKey = loadPublicKeyFromPem(publicKeyPath);
        this.expiration = expiration;
    }

    /**
     * Loads a RSA private key from a PEM file.
     *
     * The method reads the contents of the specified file, removes PEM formatting
     * headers and footers, decodes the base64-encoded content, and generates a
     * {@code PrivateKey} object using the "RSA" algorithm.
     *
     * @param filePath the path of the PEM file containing the RSA private key
     * @return the {@code PrivateKey} object generated from the PEM file
     * @throws Exception if an error occurs while reading the file, decoding the key,
     *                   or creating the {@code PrivateKey} object
     */
    private PrivateKey loadPrivateKeyFromPem(String filePath) throws Exception {
        InputStream inputStream = new ClassPathResource(filePath).getInputStream();
        String key = new String(inputStream.readAllBytes());

        key = key.replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");
        byte[] keyBytes = Base64.getDecoder().decode(key);

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(spec);
    }

    /**
     * Loads a public key from a PEM file located at the specified file path.
     * The method reads the PEM file, decodes the base64-encoded content, and generates
     * an RSA public key object.
     *
     * @param filePath the path to the PEM file containing the public key
     * @return the RSA public key loaded from the specified PEM file
     * @throws Exception if an error occurs during the loading process, such as
     *                   issues with file access, decoding, or key generation
     */
    private PublicKey loadPublicKeyFromPem(String filePath) throws Exception {
        InputStream inputStream = new ClassPathResource(filePath).getInputStream();
        String key = new String(inputStream.readAllBytes
        ());
        key = key.replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
        byte[] keyBytes = Base64.getDecoder().decode(key);

        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }

    /**
     * Generates a JSON Web Token (JWT) for a given username and role.
     *
     * The token includes the username as the subject, a custom claim for the role, an issuance date,
     * and an expiration date defined by the given configuration. The token is signed using the
     * configured private RSA key.
     *
     * @param username the username to include as the subject of the token
     * @param role the role to include as a custom claim in the token
     * @return the generated JWT as a String
     */
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusSeconds(this.expiration)))
                .signWith(this.privateKey)
                .compact();
    }

    /**
     * Extracts claims from a JSON Web Token (JWT).
     *
     * This method parses the provided JWT token using the configured public key
     * to validate its signature and extract the payload containing the claims.
     *
     * @param token the JWT token from which claims need to be extracted
     * @return the {@code Claims} object representing the parsed payload of the token
     */
    public Claims getClaimsFromToken(String token) {
        return (Claims) Jwts.parser()
                .verifyWith(this.publicKey)
                .build()
                .parse(token)
                .getPayload();
    }

    /**
     * Validates the provided JSON Web Token (JWT) by checking its expiration date.
     * A token is considered valid if the expiration time is after the current time.
     *
     * @param token the JWT token to be validated
     * @return true if the token is valid and not expired; false otherwise
     */
    public boolean validateToken(String token) {
        return getClaimsFromToken(token).getExpiration().after(new Date());
    }
}
