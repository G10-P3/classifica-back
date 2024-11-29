package br.com.g10.BEM.user;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserModel, String> {
    UserModel findByUsername(String username);

    Optional<UserModel> findByCpf(String cpf);
}

