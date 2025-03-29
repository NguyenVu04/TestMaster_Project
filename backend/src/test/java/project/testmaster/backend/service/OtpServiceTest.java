package project.testmaster.backend.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import project.testmaster.backend.service.OtpService;

class OtpServiceTest {

    @Test
    void testGenerateOtpLength() {
        String otp = OtpService.generateOtp();
        assertEquals(6, otp.length(), "OTP length should be 6 characters");
    }

    @RepeatedTest(5) // Run multiple times to check randomness
    void testGenerateOtpCharacters() {
        String otp = OtpService.generateOtp();
        assertTrue(otp.matches("[0-9A-Za-z]{6}"), "OTP should contain only alphanumeric characters");
    }

    @Test
    void testGenerateOtpUniqueness() {
        String otp1 = OtpService.generateOtp();
        String otp2 = OtpService.generateOtp();
        assertNotEquals(otp1, otp2, "Two consecutive OTPs should not be the same");
    }
}