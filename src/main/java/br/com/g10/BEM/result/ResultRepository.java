package br.com.g10.BEM.result;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ResultRepository extends JpaRepository<ResultModel, UUID> {
    // Aqui você pode adicionar métodos personalizados, se necessário
}
