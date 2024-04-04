package com.example.identityservice.configuration;

import com.example.identityservice.entity.User;
import com.example.identityservice.enums.Role;
import com.example.identityservice.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

//Tao mot user ADMIN
//ADMIN chi duoc tao mot lan duy nhat de install application
@Configuration
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;

    //ApplicationRunner se luon duoc khoi chay khi ma chung ta chay
    //Add user ADMIN
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            //Do application co the start nhieu lan
            //Check xem co ADMIN chua
            if (userRepository.findByUserName("admin").isEmpty()){
                var roles = new HashSet<String>();
                //parse to String
                roles.add(Role.ADMIN.name());
                User user = User.builder()
                        .userName("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles(roles)
                        .build();

                userRepository.save(user);
                log.warn("admin user has been created with default password: admin, please change it");
            }
        };
    }

}
