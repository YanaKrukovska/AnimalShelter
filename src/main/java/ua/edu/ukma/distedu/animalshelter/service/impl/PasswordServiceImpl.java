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
        return "valid";
    }

    @Override
    public boolean comparePasswordAndConfirmationPassword(String password, String confirmationPassword) {
        return password.equals(confirmationPassword);
    }

    @Override
    public boolean compareRawAndEncodedPassword(String raw, String encoded) {
        return bCryptPasswordEncoder.matches(raw, encoded);
    }
}
