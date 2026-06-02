package se.iths.sara.secureloginwithauthenticatorapp.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class QrCodeService {

    public String generateQrCodeUrl(String email, String secret) {
        return "otpauth://totp/SecureLoginApp:" + email +
                "?secret=" + secret +
                "&issuer=SecureLoginApp";
    }

    public String generateQrCodeImage(String qrCodeUrl) throws Exception {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        var bitMatrix = qrCodeWriter.encode(qrCodeUrl, BarcodeFormat.QR_CODE, 250, 250);

        var outputStream = new java.io.ByteArrayOutputStream();

        com.google.zxing.client.j2se.MatrixToImageWriter.writeToStream(
                bitMatrix,
                "PNG",
                outputStream
        );

        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }
}