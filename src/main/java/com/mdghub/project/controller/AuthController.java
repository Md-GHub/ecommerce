package com.mdghub.project.controller;

import com.mdghub.project.model.AppRole;
import com.mdghub.project.model.Roles;
import com.mdghub.project.model.Users;
import com.mdghub.project.repository.RoleRepo;
import com.mdghub.project.repository.UserRepo;
import com.mdghub.project.security.jwt.JwtUtils;
import com.mdghub.project.security.request.LoginRequest;
import com.mdghub.project.security.response.LoginResponse;
import com.mdghub.project.security.request.SignupRequest;
import com.mdghub.project.security.response.MessageResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepo userRepository;

    @Autowired
    JwtUtils jwtUtil;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    RoleRepo roleRepository;
    @PostMapping("/signin") //login
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(  loginRequest.getUsername(),
                                                                            loginRequest.getPassword()));
        } catch (AuthenticationException exception) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }


        //after verified

        SecurityContextHolder.getContext().setAuthentication(authentication); // authentication object holder

        UserDetails userDetails = (UserDetails) authentication.getPrincipal(); // Userobject , username as principle

        String jwtToken = jwtUtil.generateTokenFromUsername(userDetails); // generating jwt token

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        LoginResponse response = new LoginResponse(jwtToken, roles ,userDetails.getUsername());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        //validation of user
        if (userRepository.existsByUserName((signUpRequest.getUsername()))){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail((signUpRequest.getEmail()))){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        Users user = new Users(signUpRequest.getPhone(), signUpRequest.getEmail(),
                signUpRequest.getFirstName(),signUpRequest.getLastName(),encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getUsername()
                );

        Set<String> strRoles = signUpRequest.getRole();
        Set<Roles> roles = new HashSet<>();

        if (strRoles == null) {
            Roles userRole = roleRepository.findByRoleName(AppRole.USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Roles adminRole = roleRepository.findByRoleName(AppRole.ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "seller":
                        Roles modRole = roleRepository.findByRoleName(AppRole.SELLER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                        break;
                    default:
                        Roles userRole = roleRepository.findByRoleName(AppRole.USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully! now you can login"));
    }
}
