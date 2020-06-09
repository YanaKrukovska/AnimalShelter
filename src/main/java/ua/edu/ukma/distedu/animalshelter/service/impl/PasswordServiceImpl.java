package ua.edu.ukma.distedu.animalshelter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.edu.ukma.distedu.animalshelter.service.PasswordService;

@Service
public class PasswordServiceImpl implements PasswordService {
    private final
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public PasswordServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public String encodePassword(String password) {
       return bCryptPasswordEncoder.encode(password);
    }

    @Override
    public String isValidPassword(String password) {

        if (password.length() < 2){
            return "Password is too short";
        }
        return "Password is valid";
    }

    @Override
    public boolean comparePasswordAndConfirmationPassword(String password, String confirmationPassword) {
        return password.equals(confirmationPassword);
    }
}
