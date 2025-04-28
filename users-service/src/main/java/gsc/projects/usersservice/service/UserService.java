package gsc.projects.usersService.service;

import gsc.projects.usersService.dto.UserDto;
import gsc.projects.usersService.model.User;
import gsc.projects.usersService.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public UserDto createUser(UserDto userDto) {
        User existingUser = userRepository.findByUserEmail(userDto.userEmail());
        if(existingUser != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User email already exists");
        }
        existingUser = fromCreateDto(userDto);
        userRepository.save(existingUser);
        return toDto(existingUser);
    }


    public UserDto getUserByEmail(String userEmail) {
        User existingUser = userRepository.findByUserEmail(userEmail);
        if(existingUser == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User email not found");
        }
        return toDto(existingUser);
    }


    public void deleteUserByEmail(String userEmail) {
        User existingUser = userRepository.findByUserEmail(userEmail);
        if(existingUser == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User email not found");
        }
        userRepository.delete(existingUser);
    }


    public UserDto toDto(User user){
        return UserDto.builder()
                .userName(user.getUsername())
                .age(user.getAge())
                .userEmail(user.getUserEmail())
                .userAddress(user.getUserAddress())
                .build();
    }

    public User fromCreateDto(UserDto userDto){
        User user=new User();
        user.setUsername(userDto.userName());
        user.setAge(userDto.age());
        user.setUserEmail(userDto.userEmail());
        user.setUserAddress(userDto.userAddress());
        return user;
    }

}