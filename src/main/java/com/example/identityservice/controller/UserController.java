package com.example.identityservice.controller;

import com.example.identityservice.dto.request.ApiResponse;
import com.example.identityservice.dto.request.UserCreationRequest;
import com.example.identityservice.dto.request.UserUpdateRequest;
import com.example.identityservice.dto.response.UserResponse;
import com.example.identityservice.entity.User;
import com.example.identityservice.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping()
    public ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request){
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createUser(request));
        return apiResponse;
    }

   /* @GetMapping()
    List<User> getUser(){
        return userService.getUsers();
    }*/

    //Giờ chúng ta không trả về Object nữa mà trả về UserResponse
    @GetMapping()
    List<User> getUser(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Username: {}", authentication.getName());
        log.info("Roles: {}", authentication.getAuthorities());
        authentication.getAuthorities().forEach(
                grantedAuthority -> log.info(grantedAuthority.getAuthority())
        );

        return userService.getUsers();
    }

    @GetMapping("/{userid}")
    UserResponse getUser(@PathVariable("userid") String id){
        return userService.getUserById(id);
    }

    /*@PutMapping("/{userid}")
    User updateUser(@PathVariable("userid") String userId, @RequestBody UserUpdateRequest request){
        return userService.updateUser(userId, request);
    }*/

    @PutMapping("/{userid}")
    UserResponse updateUser(@PathVariable("userid") String userId, @RequestBody UserUpdateRequest request){
        return userService.updateUser(userId, request);
    }


    @DeleteMapping("/{userid}")
    String deleteUser(@PathVariable("userid") String userId){
        userService.deleteUser(userId);
        return "User has been deleted";
    }
}
