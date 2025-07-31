package gsc.projects.securityservice.service;





import gsc.projects.securityservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USERS-SERVICE")
public interface APIClient {

    @GetMapping("/user/{userEmail}")
    UserDto getUser(@PathVariable("userEmail") String userEmail);
}
