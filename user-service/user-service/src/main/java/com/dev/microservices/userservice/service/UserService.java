package com.dev.microservices.userservice.service;

import com.dev.microservices.userservice.dto.UserRequest;
import com.dev.microservices.userservice.dto.UserResponse;
import com.dev.microservices.userservice.exception.DuplicateEmailException;
import com.dev.microservices.userservice.exception.UserNotFoundException;
import com.dev.microservices.userservice.model.User;
import com.dev.microservices.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse createUser(UserRequest userRequest){
        if(userRepository.existsByEmail(userRequest.getEmail())){
            throw new DuplicateEmailException("This email already exist:"+userRequest.getEmail());
        }
        User user = User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .build();
        User saved = userRepository.save(user);
        return mapToResponse(saved);
    }

    public UserResponse getUserById(Long userId){
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("This user not found with id: "+userId));
        return mapToResponse(user);
    }

    public List<UserResponse> getAllUsers(){
       return userRepository.findAll().stream().map(this::mapToResponse).toList();
    }


    private UserResponse mapToResponse(User saved) {
        return UserResponse.builder()
                .id(saved.getId())
                .name(saved.getName())
                .email(saved.getEmail())
                .build();
    }



}
