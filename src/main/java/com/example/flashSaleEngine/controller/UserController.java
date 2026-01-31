package com.example.flashSaleEngine.controller;

import com.example.flashSaleEngine.dto.UserResponseDto;
import com.example.flashSaleEngine.model.User;
import com.example.flashSaleEngine.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(
            description = "Register",
            summary = "New user can register"
    )
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody User user){
        UserResponseDto savedUser = userService.addUser(user);
        if(savedUser != null){

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedUser);
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }

    @Operation(
            description = "Get user",
            summary = "Get user by there id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable String id){
        UserResponseDto user = userService.getUser(id);

        if(user != null){
            return ResponseEntity.ok(user);
        }

        return ResponseEntity.notFound().build();
    }
    @Operation(
            description = "Delete user",
            summary = "Delete user by there id"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id){
        boolean deleted = userService.removeUser(id);

        if(deleted){
            return ResponseEntity
                    .noContent()
                    .build();
        }

        return ResponseEntity
                .notFound()
                .build();
    }

    @Operation(
            description = "Update user",
            summary = "Update user by there id"
    )
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable String id , @RequestBody User user){
        UserResponseDto updated = userService.updateUser(id ,user);

        if(updated != null) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(updated);
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }
}
