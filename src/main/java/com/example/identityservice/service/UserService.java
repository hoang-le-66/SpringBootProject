package com.example.identityservice.service;

import com.example.identityservice.dto.request.UserCreationRequest;
import com.example.identityservice.dto.request.UserUpdateRequest;
import com.example.identityservice.dto.response.UserResponse;
import com.example.identityservice.entity.User;
import com.example.identityservice.exception.AppException;
import com.example.identityservice.exception.ErrorCode;
import com.example.identityservice.mapper.UserMapper;
import com.example.identityservice.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;

    //Ở tầng Service, khi trả về dữ liệu cho tầng Controller thì người ta
    //Không trả về Entity mà phải trả về một cái DTO khác
    public User createUser(UserCreationRequest request) {
        if (userRepository.existsByUserName(request.getUserName()))
            throw new AppException(ErrorCode.USER_EXISTED);
        //Thằng request này là một instance của UserCreationRequest
        //Mà đã có annotation @Builder, nên là map thôi
        User user = userMapper.toUser(request);
        //interface PasswordEncoder gom 3 phuong thuc
        //1. encode: ma hoa
        //2. matches: kiem tra xem co khop nhau khong
        //3. upgradeEncoding
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        /*user.setUserName(request.getUserName());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDob(request.getDob());*/

        return userRepository.save(user);
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        /*User user = getUserById(userId);

        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDob(request.getDob());
*/
        User user = userRepository.findById(userId).get();
        userMapper.updateUser(user, request);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    /*public User getUserById(String id) {
        return userRepository.findById(id).get();
        *//*return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found user"));*//*
    }*/

    public UserResponse getUserById(String id) {
        return userMapper.toUserResponse(userRepository.findById(id).get());
        /*return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found user"));*/
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
