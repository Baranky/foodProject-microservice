package gsc.projects.usersservice.controller;


import gsc.projects.usersservice.dto.UserDto;
import gsc.projects.usersservice.service.UserServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserServiceImp userServiceImp;

    public UserController(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
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
