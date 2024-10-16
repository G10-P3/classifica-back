package br.com.g10.BEM.classes;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ClassesRepository extends JpaRepository<ClassesModel, UUID> {
    ClassesModel findByClassName(String className);
}
