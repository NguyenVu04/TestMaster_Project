package project.testmaster.backend.dto;

/**
 * Data Transfer Object for signup requests.
 */
public class SignupRequestDTO {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    /**
     * Returns the email of the signup request.
     *
     * @return the email of the signup request
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the signup request.
     *
     * @param email the new email of the signup request
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the password of the signup request.
     *
     * @return the password of the signup request
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the signup request.
     *
     * @param password the new password of the signup request
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the first name of the signup request.
     *
     * @return the first name of the signup request
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the signup request.
     *
     * @param firstName the new first name of the signup request
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the last name of the signup request.
     *
     * @return the last name of the signup request
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the signup request.
     *
     * @param lastName the new last name of the signup request
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the phone number of the signup request.
     *
     * @return the phone number of the signup request
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the signup request.
     *
     * @param phoneNumber the new phone number of the signup request
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}