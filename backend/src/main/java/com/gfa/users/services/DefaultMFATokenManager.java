package com.gfa.users.services;

import dev.samstevens.totp.code.CodeVerifier;
import dev.samstevens.totp.code.HashingAlgorithm;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import dev.samstevens.totp.util.Utils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("mfaTokenManager")
public class DefaultMFATokenManager implements MFATokenManager{

    @Resource
    private SecretGenerator secretGenerator;

    @Resource
    private QrGenerator qrGenerator;

    @Resource
    private CodeVerifier codeVerifier;

    //secret key will be generated during the registration process
    // if registration is successful we call this service it generates the secret
    // and we store this secret onto the user profile
    @Override
    public String generateSecretKey(){
        return secretGenerator.generate();
    }

    // second thing -> generating the QR code
    // part of the registration process -> successful registration => we will get QR code
    // the user scans it using the mobile and then it starts generating those one time password
    // the user will use external app for example Google Authenticator

    // the method needs the secret that was generated for a given user
    @Override
    public String getQRCode(String secret) throws QrGenerationException{
        QrData data = new QrData.Builder().label("MFA")
                .secret(secret)
                .issuer("Dinning app")
                .algorithm(HashingAlgorithm.SHA1)
                .digits(6)      // length of token -> one time password
                .period(30)     // for how long is token valid -> for example 30s
                .build();
        return Utils.getDataUriForImage(
                qrGenerator.generate(data),
                qrGenerator.getImageMimeType()
        );
    }

    // verifying the time based token
    // during the login process the user will that token (code) and also verification service
    // need additional information which is what is the secret that has been stored onto the customer profile
    // based onto those two information it basically verify whether the token that was provided by the customer is a valid or not valid
    
    @Override
    public boolean verifyTotp(String code, String secret){
        return codeVerifier.isValidCode(secret,code);
    }
}
