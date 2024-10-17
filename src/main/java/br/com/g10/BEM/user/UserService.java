package br.com.g10.BEM.user;
import org.springframework.stereotype.Service;
import br.com.g10.BEM.exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

@Service
public class UserService {

    private final IUserRepository userRepository;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel createUser(UserModel userModel) {
        final UserModel existingUser = userRepository.findByUsername(userModel.getUsername());

        if (existingUser != null) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        return userRepository.save(userModel);
    }


    public Optional<UserModel> findByCpf(String cpf) {
        return userRepository.findByCpf(cpf);
    }
}
