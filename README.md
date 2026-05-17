# Secure Login with Authenticator App

## Beskrivning

Detta projekt är en säker inloggningsapplikation byggd med Spring Boot.

Applikationen använder:

- Spring Security
- Tvåfaktorsautentisering (2FA)
- Microsoft/Google Authenticator
- QR-kod
- H2 databas
- Thymeleaf

## Funktioner

- Registrera användare
- Krypterade lösenord
- Login med Spring Security
- QR-kod genereras vid aktivering av 2FA
- TOTP-verifiering
- Blockering efter tre felaktiga kodförsök
- Profilsida efter godkänd verifiering

## Teknologier

- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- Thymeleaf
- Google Authenticator API
- ZXing

## Starta projektet

Starta applikationen via IntelliJ:

1. Öppna projektet
2. Klicka på den gröna Run-knappen
3. Öppna:

http://localhost:8080