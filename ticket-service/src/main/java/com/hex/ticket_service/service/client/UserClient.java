package com.hex.ticket_service.service.client;

import com.hex.ticket_service.service.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="user-service",url="http://localhost:8081")
public interface UserClient {


    @GetMapping("/api/users/username/{username}")
    UserDto getUserByUsername(@PathVariable String username);

    @GetMapping("/api/users/{id}")
    UserDto getUserById(@PathVariable Long id);

}
