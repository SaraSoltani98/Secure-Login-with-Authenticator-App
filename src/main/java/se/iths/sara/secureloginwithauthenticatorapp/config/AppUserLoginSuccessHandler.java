package se.iths.sara.secureloginwithauthenticatorapp.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import se.iths.sara.secureloginwithauthenticatorapp.model.AppUser;
import se.iths.sara.secureloginwithauthenticatorapp.service.AppUserService;

import java.io.IOException;

@Component
public class AppUserLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final AppUserService appUserService;

    public AppUserLoginSuccessHandler(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        String email = authentication.getName();

        AppUser user = appUserService.findByEmail(email).orElseThrow();

        if (user.isTwoFactorEnabled()) {

            HttpSession session = request.getSession();
            session.setAttribute("email", email);

            response.sendRedirect("/verify-code");

        } else {

            response.sendRedirect("/profile");
        }
    }
}