package com.hex.user_service.controller;

import com.hex.user_service.dto.AuthDtos;
import com.hex.user_service.dto.UserDto;
import com.hex.user_service.exception.UserNotFoundException;
import com.hex.user_service.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@CrossOrigin
public class UserController {
    private final UserRepository userRepository;


    @GetMapping("/username/{username}")
    @PreAuthorize("hasAnyRole('AGENT','ADMIN')")
    //@PreAuthorize("hasAnyRole('AGENT','ADMIN','username==authentication.name')")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_AGENT') or #username==authentication.name")
    public UserDto getUserByUsername(@PathVariable String username, Authentication auth)
    {
        var user=userRepository.findByUsername(username).orElseThrow(()->new UserNotFoundException("User Not Found!"));
        return new UserDto(user.getId(),user.getUsername(),user.getEmail(),user.getRole().name());
    }


    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('AGENT','ADMIN')")

    //@PreAuthorize("hasAnyRole('AGENT','ADMIN',#id.toString()==authentication.principal")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_AGENT') or #id.toString()==authentication.principal")
    public UserDto getUserById(@PathVariable Long id,Authentication auth)
    {
        var user=userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User Not Found!"));
        log.info("Id value in AuthController: {}",id);
        return new UserDto(user.getId(),user.getUsername(),user.getEmail(),user.getRole().name());
    }

}
