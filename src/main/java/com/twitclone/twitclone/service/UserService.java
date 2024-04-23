package com.twitclone.twitclone.service;

import com.twitclone.twitclone.model.Role;
import com.twitclone.twitclone.model.User;
import com.twitclone.twitclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final MailSenderService mailSenderService;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, MailSenderService mailSenderService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.mailSenderService = mailSenderService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public boolean addUser(User user) {
        User userFromDb = userRepository.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        sendMessage(user);
        return true;

    }

    private void sendMessage(User user) {
        if (!user.getEmail().isEmpty()) {
            String message = String.format(
                    "Hello, %s! Welcome to Twitclone. Please, visit next link: http://localhost:8080/activate/%s ",
                    user.getUsername(),
                    user.getActivationCode()
            );

            mailSenderService.send(user.getEmail(), "Activation code", message);
        }
    }

    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);

        if (user == null) {
            return false;
        }

        user.setActivationCode(null);

        userRepository.save(user);

        return true;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void saveUser(User user, String username, Map<String, String> form) {
        user.setUsername(username);

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepository.save(user);
    }

    public void updateProfile(User user, String password, String email) {
        String userEmail = user.getEmail();

        boolean isEmailChanged = (email != null && !email.equals(userEmail))
                || (userEmail != null && !userEmail.equals(email));

        if (isEmailChanged) {
            user.setEmail(email);

            if (!email.isEmpty()) {
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }

        if (!password.isEmpty()) {
            user.setPassword(password);
        }

        userRepository.save(user);

        if (isEmailChanged) {
            sendMessage(user);
        }
    }
}
