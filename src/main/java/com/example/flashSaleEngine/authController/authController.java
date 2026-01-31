package com.example.flashSaleEngine.authController;

import com.example.flashSaleEngine.dto.AuthRequest;
import com.example.flashSaleEngine.jwt.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class authController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest details) {

        System.out.println("Login called!!");

        String username = details.getUsername();
        String password = details.getPassword();

        boolean isAuthenticated = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password)).isAuthenticated();

        if(isAuthenticated){

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(jwtUtils.generateToken(details));

        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please check credentials!!");
    }
}
