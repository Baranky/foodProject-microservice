package gsc.projects.securityservice.service;



import gsc.projects.securityservice.dto.UserDto;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:8084/api/user", name = "USERS-SERVICE")
public interface APIClient {

    @GetMapping("/{userEmail}")
    UserDto getUser(@PathVariable("userEmail") String userEmail);
}