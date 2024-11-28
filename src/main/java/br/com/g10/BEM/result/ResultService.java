package br.com.g10.BEM.result;

import br.com.g10.BEM.classes.ClassesModel;
import br.com.g10.BEM.exam.ExamModel;
import br.com.g10.BEM.exam.ExamRepository;
import br.com.g10.BEM.student.StudentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ResultService {

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private ExamRepository examRepository;


    public ResultModel createResult(ResultModel result) {
        return resultRepository.save(result);
    }

    public List<ResultModel> getAllResults() {
        return resultRepository.findAll();
    }

    public ResultModel getResultById(UUID id) {
        return resultRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Resultado não encontrado com ID " + id));
    }

    public ResultModel updateResult(UUID id, ResultModel resultDetails) {
        ResultModel result = resultRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Resultado não encontrado com ID " + id));

        result.setMathScore(resultDetails.getMathScore());
        result.setPortugueseScore(resultDetails.getPortugueseScore());
        result.setTotalScore(resultDetails.getTotalScore());
        result.setExam(resultDetails.getExam());
        result.setStudent(resultDetails.getStudent());

        return resultRepository.save(result);
    }

    public void deleteResult(UUID id) {
        if (!resultRepository.existsById(id)) {
            throw new EntityNotFoundException("Resultado não encontrado com ID " + id);
        }
        resultRepository.deleteById(id);
    }

    public List<Map<String, Object>> getExamSummaries() {
        List<ExamModel> exams = examRepository.findAllByOrderByCreatedAtDesc();

        return exams.stream().flatMap(exam ->
                exam.getClasses().stream().map(classes -> {
                    List<StudentModel> students = classes.getStudents();
                    List<ResultModel> examResults = resultRepository.findByExam(exam);
                    List<ResultModel> classResults = examResults.stream().filter(result -> students.contains(result.getStudent())).toList();

                    double averageScore = classResults.stream().collect(Collectors.groupingBy(ResultModel::getStudent)).values().stream().mapToDouble(results -> results.stream()
                                    .mapToInt(ResultModel::getTotalScore)
                                    .average().orElse(0.0))
                            .average().orElse(0.0);

                    Map<String, Object> summary = new HashMap<>();
                    summary.put("simulado", exam.getName());
                    summary.put("turma", classes.getClassName());
                    summary.put("criado", exam.getCreatedAt());
                    summary.put("aplicacao", exam.getDate());
                    summary.put("media", String.format("%.1f", averageScore));

                    return summary;
                })
        ).collect(Collectors.toList());
    }
}
