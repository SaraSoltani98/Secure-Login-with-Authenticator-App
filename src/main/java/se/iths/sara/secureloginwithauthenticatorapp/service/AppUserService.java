package se.iths.sara.secureloginwithauthenticatorapp.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.iths.sara.secureloginwithauthenticatorapp.model.AppUser;
import se.iths.sara.secureloginwithauthenticatorapp.repository.AppUserRepository;

import java.util.Optional;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final TwoFactorService twoFactorService;

    public AppUserService(AppUserRepository appUserRepository,
                          PasswordEncoder passwordEncoder,
                          TwoFactorService twoFactorService) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.twoFactorService = twoFactorService;
    }

    public Optional<AppUser> findByEmail(String email) {
        return appUserRepository.findByEmail(email);
    }

    public AppUser registerUser(AppUser user) {

        if (appUserRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.isTwoFactorEnabled()) {
            String secret = twoFactorService.generateSecret();
            user.setSecret(secret);
        }

        return appUserRepository.save(user);
    }

    public void deleteUser(AppUser user) {
        appUserRepository.delete(user);
    }

    public boolean verifyTwoFactorCode(AppUser user, int code) {
        return twoFactorService.isCodeValid(user.getSecret(), code);
    }
}