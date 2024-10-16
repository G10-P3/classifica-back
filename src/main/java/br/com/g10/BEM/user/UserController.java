package br.com.g10.BEM.user;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository userRepository;

    
    @PostMapping("/")
    public ResponseEntity createUser(@RequestBody UserModel userModel) {

        final var user = this.userRepository.findByUsername(userModel.getUsername());

        if (user != null) {
            // Retornar mensagem de erro + status code 400
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
        }

        // Create a new user
        final var userCreated = this.userRepository.save(userModel);

        // Return the user created
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
    
}
