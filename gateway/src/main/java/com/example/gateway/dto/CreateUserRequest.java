package com.example.gateway.dto;



import com.example.gateway.enums.Role;
import lombok.Builder;


import java.util.Set;


@Builder
public record CreateUserRequest(
        String name,
        String username,
        String password,
        Set<Role> authorities
){
}
