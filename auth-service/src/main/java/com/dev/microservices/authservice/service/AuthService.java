package com.dev.microservices.authservice.service;

import com.dev.microservices.authservice.client.UserServiceClient;
import com.dev.microservices.authservice.dto.LoginRequest;
import com.dev.microservices.authservice.dto.LoginResponse;
import com.dev.microservices.authservice.dto.RegisterRequest;
import com.dev.microservices.authservice.dto.RegisterResponse;
import com.dev.microservices.authservice.dto.external.CreateUserRequest;
import com.dev.microservices.authservice.dto.external.UserResponseDTO;
import com.dev.microservices.authservice.exception.InvalidCredentialsException;
import com.dev.microservices.authservice.exception.UserProfileCreationException;
import com.dev.microservices.authservice.exception.DuplicateEmailException;
import com.dev.microservices.authservice.model.AuthUser;
import com.dev.microservices.authservice.repository.AuthUserRepository;
import com.dev.microservices.authservice.response.ApiResponse;
import com.dev.microservices.authservice.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthUserRepository authUserRepository;
    private final JwtUtil jwtUtil;
    private final UserServiceClient userServiceClient;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(AuthUserRepository authUserRepository, JwtUtil jwtUtil, UserServiceClient userServiceClient) {
        this.authUserRepository = authUserRepository;
        this.jwtUtil = jwtUtil;
        this.userServiceClient = userServiceClient;
    }

    public RegisterResponse register(RegisterRequest request){
        if(authUserRepository.existsByEmail(request.getEmail())){
            throw new DuplicateEmailException("Email already registered: "+request.getEmail());
        }

        // register the auth user into db
        AuthUser authUser = AuthUser.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        AuthUser saved = authUserRepository.save(authUser);

        // now create profile for this user in user-service
        ApiResponse<UserResponseDTO> created = userServiceClient.createUser(CreateUserRequest.builder()
                    .name(request.getName())
                    .email(request.getEmail()).build());

        if (created == null || !created.isSuccess() || created.getData() == null) {
            throw new UserProfileCreationException("User profile creation failed in user service");
        }

        return mapToResponse(saved);

    }

    public LoginResponse login(LoginRequest request) {

        AuthUser user = authUserRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getEmail());

        return LoginResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .build();
    }

    public RegisterResponse mapToResponse(AuthUser authUser){
        return RegisterResponse.builder()
                .userId(authUser.getId())
                .name(authUser.getName())
                .email(authUser.getEmail())
                .build();
    }
}
