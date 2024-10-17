package br.com.g10.BEM.exam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.hibernate.validator.constraints.UUID;

public interface IExamRepository extends JpaRepository<ExamModel, UUID> {
    ExamModel findByName(String name);
}
