package com.example.identityservice.service;

import com.example.identityservice.dto.request.AuthenticationRequest;
import com.example.identityservice.exception.AppException;
import com.example.identityservice.exception.ErrorCode;
import com.example.identityservice.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
   /* UserRepository userRepository;
    @NonFinal
    //Danh dau la @NonFinal de khong inject vao constructor
    protected static final String SIGNER_KEY =
            "77hZte9+zS+bDjqomBuNfOu8QB+uOvBcb1YmFbOIBh6DyVRa5RmvJZI1qUzlQDZ1";
    //nho sua lai la Authenticate
    public boolean authenticate(AuthenticationRequest request) {
        var user = userRepository.findByUserName(
                        request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        return passwordEncoder.matches(request.getPassword(), user.getPassword());
        *//*boolean isAuthenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!isAuthenticated)
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        var token = generateToken(request.getUsername());

        return AuthenticationResponse.builder().token(token).authenticated(true).build();*//*
    }

    private String generateToken(String username) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        //claims la gi???
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("hoang_le")
                .issueTime(new Date())
                .expirationTime(
                        new Date(Instant
                                .now()
                                .plus(1, ChronoUnit.HOURS)
                                .toEpochMilli()
                        ))
                .claim("customClaim", "Custom")
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        //Dang ky xong token
        JWSObject jswObject = new JWSObject(header, payload);

        try {
            jswObject.sign(new MACSigner(SIGNER_KEY));
            return jswObject.serialize();
        } catch (JOSEException e) {
            System.err.println("Cannot create token");
            throw new RuntimeException(e);
        }
    }*/
   UserRepository userRepository;

    public boolean authenticate(AuthenticationRequest request){
        var user = userRepository.findByUserName(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        return passwordEncoder.matches(request.getPassword(), user.getPassword());
    }
}
