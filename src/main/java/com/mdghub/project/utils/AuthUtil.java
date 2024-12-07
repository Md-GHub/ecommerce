package com.mdghub.project.utils;

import com.mdghub.project.model.Users;
import com.mdghub.project.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {

    @Autowired
    UserRepo userRepo;

    public String loggedInEmail(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users user = userRepo.findByUserName(auth.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        return user.getEmail();
    }
    public Long loggedInUserId(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users user = userRepo.findByUserName(auth.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        return user.getUserId();
    }
    public Users loggedInUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users user = userRepo.findByUserName(auth.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        return user;
    }


}
