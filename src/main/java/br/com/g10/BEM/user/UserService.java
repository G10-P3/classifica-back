package br.com.g10.BEM.user;

import org.springframework.stereotype.Service;

import br.com.g10.BEM.exceptions.UserAlreadyExistsException;

@Service
public class UserService {

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel createUser(UserModel userModel) {
        // Verifica se o username já existe
        final UserModel existingUser = userRepository.findByUsername(userModel.getUsername());

        if (existingUser != null) {
            // Lança uma exceção se o usuário já existe
            throw new UserAlreadyExistsException("User already exists");
        }

        // Cria e salva o novo usuário
        return userRepository.save(userModel);
    }
}
