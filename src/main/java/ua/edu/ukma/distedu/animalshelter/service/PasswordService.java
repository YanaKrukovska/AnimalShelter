package ua.edu.ukma.distedu.animalshelter.service;

public interface PasswordService {

    String encodePassword(String password);

    //TODO: returns message with error that occurred while creating password
    String isValidPassword(String password);
    boolean comparePasswordAndConfirmationPassword(String password, String confirmationPassword);

}
