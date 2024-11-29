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

    public boolean login(LoginDTO loginDTO) {
        try {
            final Optional<UserModel> optionalUser = userRepository.findByCpf(loginDTO.getCpf());

            if (optionalUser.isEmpty()) {
                return false;
            }

            final UserModel foundUser = optionalUser.get();

            if (!foundUser.getPassword().equals(loginDTO.getPassword())) {
                return false;
            }

            return true;
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}
