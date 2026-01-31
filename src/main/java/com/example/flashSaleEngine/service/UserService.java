package com.example.flashSaleEngine.service;

import com.example.flashSaleEngine.dto.DtoMapper;
import com.example.flashSaleEngine.dto.UserResponseDto;
import com.example.flashSaleEngine.model.Role;
import com.example.flashSaleEngine.model.User;
import com.example.flashSaleEngine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public UserResponseDto getUser(String id){

            DtoMapper mapper = new DtoMapper();

            return mapper.userToUserResponse(userRepository.findById(id).orElseThrow());
    }

    public UserResponseDto addUser(User user){
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User saved = userRepository.save(user);

        DtoMapper mapper = new DtoMapper();
        return mapper.userToUserResponse(saved);
    }

    public boolean removeUser(String id){
        if (!userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }

        return false;
    }

    public UserResponseDto updateUser(String id, User user) {

        if(userRepository.existsById(id)){

            User preSaved = userRepository.findById(id).orElseThrow();

            if(user != null){
                if(user.getName() != null && !user.getName().isEmpty()){
                    preSaved.setName(user.getName());
                }
                if(user.getAddress() != null && !user.getAddress().isEmpty()){
                    preSaved.setAddress(user.getAddress());
                }
                if(user.getPhone() != null && !user.getPhone().isEmpty()){
                    preSaved.setPhone(user.getPhone());
                }
                if(user.getEmail() != null && !user.getEmail().isEmpty()){
                    preSaved.setEmail(user.getEmail());
                }
            }
            User saved = userRepository.save(preSaved);

            DtoMapper mapper = new DtoMapper();
            return mapper.userToUserResponse(saved);
        }

        return null;
    }
}
