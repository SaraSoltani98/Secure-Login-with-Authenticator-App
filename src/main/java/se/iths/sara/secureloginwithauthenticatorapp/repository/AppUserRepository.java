package se.iths.sara.secureloginwithauthenticatorapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.sara.secureloginwithauthenticatorapp.model.AppUser;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);
}