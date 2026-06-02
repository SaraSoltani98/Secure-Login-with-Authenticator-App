package se.iths.sara.secureloginwithauthenticatorapp.service;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import org.springframework.stereotype.Service;

@Service
public class TwoFactorService {

    private final GoogleAuthenticator googleAuthenticator =
            new GoogleAuthenticator();

    public String generateSecret() {

        GoogleAuthenticatorKey key =
                googleAuthenticator.createCredentials();

        return key.getKey();
    }

    public boolean isCodeValid(String secret, int code) {

        return googleAuthenticator.authorize(secret, code);
    }
}