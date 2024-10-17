package br.com.g10.BEM.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<StudentModel, String> {
    boolean existsByUserCpf(String userCpf);
}
