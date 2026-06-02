package se.iths.sara.secureloginwithauthenticatorapp.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    @GetMapping("/profile")
    public String profile(HttpSession session) {

        Boolean verified = (Boolean) session.getAttribute("verified");

        if (verified == null || !verified) {
            return "redirect:/verify-code";
        }

        return "profile";
    }
}