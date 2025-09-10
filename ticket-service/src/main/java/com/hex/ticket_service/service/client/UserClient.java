package com.hex.ticket_service.service.client;

import com.hex.ticket_service.service.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="user-service",url="http://localhost:8081/api/users")
public interface UserClient {


    @GetMapping("username/{username}")
    UserDto getUserByUsername(@PathVariable String username);

    @GetMapping("{id}")
    UserDto getUserById(@PathVariable Long id);

}
