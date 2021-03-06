package ua.edu.ukma.distedu.animalshelter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.edu.ukma.distedu.animalshelter.persistence.model.Role;
import ua.edu.ukma.distedu.animalshelter.persistence.model.User;
import ua.edu.ukma.distedu.animalshelter.persistence.repository.UserRepository;
import ua.edu.ukma.distedu.animalshelter.service.PasswordService;
import ua.edu.ukma.distedu.animalshelter.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordService passwordService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(User newUser) {
        return userRepository.save(newUser);
    }

    public boolean saveUser(User user) {
        User checkUsername = userRepository.findUserByUsername(user.getUsername());
        User checkEmail = userRepository.findUserByEmail(user.getEmail());

        if (checkUsername != null) {
            return false;
        }

        if (checkEmail != null) {
            return false;
        }

        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(passwordService.encodePassword(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Override
    public User findUserById(long id) {
        Optional<User> userFromDb = userRepository.findById(id);
        return userFromDb.orElse(new User());
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

}
