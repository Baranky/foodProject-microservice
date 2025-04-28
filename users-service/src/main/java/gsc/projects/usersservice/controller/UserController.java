package gsc.projects.usersService.controller;


import gsc.projects.usersService.dto.UserDto;
import gsc.projects.usersService.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    private final UserService userServiceImp;

    public UserController(UserService userServiceImp) {
        this.userServiceImp = userServiceImp;
    }


    @GetMapping("test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("Hello World from users Service");

    }
    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserDto userDto){
        return new ResponseEntity<>(userServiceImp.createUser(userDto), HttpStatus.CREATED);
    }

    @GetMapping("/{userEmail}")
    public ResponseEntity<?> getUser(@PathVariable("userEmail") String userEmail){
        return ResponseEntity.ok(userServiceImp.getUserByEmail(userEmail));
    }

    @DeleteMapping("/{userEmail}")
    public ResponseEntity<?> delete(@PathVariable ("userEmail") String userEmail){
        userServiceImp.deleteUserByEmail(userEmail);
        return new ResponseEntity<>("User deleted successfully!", HttpStatus.OK);
    }
}
