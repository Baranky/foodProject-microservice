package gsc.projects.usersService.dto;

import lombok.Builder;


@Builder
public record UserDto (
         String userName,
         int age,
         String userEmail,
         String userAddress
){
}
