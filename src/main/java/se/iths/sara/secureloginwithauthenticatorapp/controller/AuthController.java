package se.iths.sara.secureloginwithauthenticatorapp.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.iths.sara.secureloginwithauthenticatorapp.model.AppUser;
import se.iths.sara.secureloginwithauthenticatorapp.service.AppUserService;
import se.iths.sara.secureloginwithauthenticatorapp.service.QrCodeService;

@Controller
public class AuthController {

    private final AppUserService appUserService;
    private final QrCodeService qrCodeService;

    public AuthController(AppUserService appUserService,
                          QrCodeService qrCodeService) {
        this.appUserService = appUserService;
        this.qrCodeService = qrCodeService;
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new AppUser());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(AppUser user, Model model) throws Exception {

        AppUser savedUser = appUserService.registerUser(user);

        if (savedUser.isTwoFactorEnabled()) {
            String qrCodeUrl = qrCodeService.generateQrCodeUrl(
                    savedUser.getEmail(),
                    savedUser.getSecret()
            );

            String qrCodeImage = qrCodeService.generateQrCodeImage(qrCodeUrl);

            model.addAttribute("qrCodeImage", qrCodeImage);
            model.addAttribute("email", savedUser.getEmail());

            return "qr";
        }

        return "redirect:/login";
    }

    @GetMapping("/verify-code")
    public String verifyCodePage() {
        return "verify-code";
    }

    @PostMapping("/verify-code")
    public String verifyCode(@RequestParam int code,
                             HttpSession session,
                             Model model) {

        String email = (String) session.getAttribute("email");

        if (email == null) {
            return "redirect:/login";
        }

        AppUser user = appUserService.findByEmail(email).orElseThrow();

        boolean valid = appUserService.verifyTwoFactorCode(user, code);

        if (!valid) {
            Integer attempts = (Integer) session.getAttribute("attempts");

            if (attempts == null) {
                attempts = 0;
            }

            attempts++;
            session.setAttribute("attempts", attempts);

            if (attempts >= 3) {
                session.invalidate();
                return "redirect:/login?error";
            }

            model.addAttribute(
                    "error",
                    "Fel kod. Försök igen. Försök kvar: " + (3 - attempts)
            );

            return "verify-code";
        }

        session.setAttribute("verified", true);
        session.removeAttribute("attempts");

        return "redirect:/profile";
    }
}