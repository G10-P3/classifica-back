package br.com.g10.BEM.result;

import br.com.g10.BEM.classes.ClassesModel;
import br.com.g10.BEM.exam.ExamModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface ResultRepository extends JpaRepository<ResultModel, UUID> {
    List<ResultModel> findByExam(ExamModel exam);
}
