package project.testmaster.backend.service;

import java.security.SecureRandom;

import org.springframework.stereotype.Service;

/**
 * Service class for handling operations related to OTP (One-Time Password) generation.
 *
 * This service provides functionality to generate secure and randomized OTPs
 * using alphanumeric characters.
 *
 * The primary use case for this service is to provide temporary and unique
 * OTPs that can be used for authentication or verification purposes in
 * applications.
 */
@Service
public class OtpService {
    private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int OTP_LENGTH = 6;
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Generates a random alphanumeric One-Time Password (OTP) of a predefined length.
     * The OTP is created using characters from a predefined set of alphanumeric characters
     * and is intended for secure and temporary usage in authentication or verification processes.
     *
     * @return A randomly generated OTP as a String.
     */
    public static String generateOtp() {
        StringBuilder otp = new StringBuilder(OTP_LENGTH);
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return otp.toString();
    }
}
