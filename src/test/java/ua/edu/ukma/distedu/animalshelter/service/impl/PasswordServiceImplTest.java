package ua.edu.ukma.distedu.animalshelter.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;
import ua.edu.ukma.distedu.animalshelter.service.PasswordService;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PasswordServiceImpl.class, BCryptPasswordEncoder.class})
public class PasswordServiceImplTest {

    @Autowired
    private PasswordService passwordService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    void encodePassword() {
        String adminPasswordHash = passwordService.encodePassword("1");
        System.out.println(adminPasswordHash);
        Assertions.assertNotNull(adminPasswordHash);
    }
}