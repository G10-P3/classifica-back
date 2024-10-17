package br.com.g10.BEM.exam;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface IExamRepository extends JpaRepository<ExamModel, UUID> {
    ExamModel findByName(String name);
}
