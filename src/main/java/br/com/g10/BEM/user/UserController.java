package br.com.g10.BEM.user;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping("/")
    public void createUser(@RequestBody UserModel userModel) {
        // Create a new user
        System.out.println(userModel.getUsername());
    }
    
}
