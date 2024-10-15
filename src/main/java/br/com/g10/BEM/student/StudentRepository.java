package br.com.g10.BEM.student;

import br.com.g10.BEM.student.StudentModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentModel, String> {
}
