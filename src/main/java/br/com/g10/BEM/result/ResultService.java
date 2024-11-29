package br.com.g10.BEM.result;

import br.com.g10.BEM.classes.ClassesModel;
import br.com.g10.BEM.exam.ExamModel;
import br.com.g10.BEM.exam.ExamRepository;
import br.com.g10.BEM.exam.ExamService;
import br.com.g10.BEM.student.StudentModel;
import br.com.g10.BEM.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;
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

    @Autowired
    private StudentRepository studentRepository;

    // Service de simulado
    @Autowired
    private ExamService examService;

    public boolean createResult(ResultModel result) {
        StudentModel student = studentRepository.findByUserCpf(result.getStudent().getUserCpf())
                .orElseThrow(() -> new EntityNotFoundException("Estudante com CPF " + result.getStudent().getUserCpf() + " não encontrado."));

        ExamModel exam = examRepository.findById(result.getExam().getId())
                .orElseThrow(() -> new EntityNotFoundException("Simulado com ID " + result.getExam().getId() + " não encontrado."));
        result.setStudent(student);
        result.setExam(exam);

        final ResultModel savedResult = resultRepository.save(result);

        return savedResult != null;
    }

    public List<ResultModel> getAllResults() {
        List<ResultModel> results = resultRepository.findAll();

        // fazer com que o retorno não tenha referencia circular
        results.forEach(result -> {
            result.getStudent().setResults(null);
            result.getStudent().setClasses(null);
            result.getStudent().setUser(null);
        });

        return results;
    }

    public List<ResultModel> getAllResultsByExam(UUID examId) {

        // Buscar o simulado
        final ExamModel exam = examRepository.findById(examId)
                .orElseThrow(() -> new EntityNotFoundException("Simulado com ID " + examId + " não encontrado."));

        // Buscar os resultados associados ao simulado
        final List<ResultModel> results = resultRepository.findByExam(exam);

        // Remover relações circulares dos resultados
        results.forEach(result -> {
            if (result.getStudent() != null) {
                result.getStudent().setResults(null);

                if (result.getStudent().getClasses() != null) {
                    result.getStudent().getClasses().forEach(classes -> classes.setStudents(null));
                }
                result.getStudent().setUser(null);
            } else {
                System.out.println("Estudante é nulo para o resultado com ID: " + result.getId());
            }
        });

        return results;
    }

    public ResultModel getResultById(UUID id) {
        return resultRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Resultado não encontrado com ID " + id));
    }

    public ResultModel updateResult(UUID id, ResultModel resultDetails) {
        final ResultModel result = resultRepository.findById(id)
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

    public List<ResultDTO> getExamSummaries() {

        // Pegar todos os simulados
        final List<ExamModel> exams = examService.getAll();

        // DTOs que serão retornados
        final List<ResultDTO> examSummaries = new ArrayList<>();

        // Iterar sobre cada simulado
        exams.forEach(exam -> {
            // Pegar todos os resultados do simulado
            final List<ResultModel> results = this.getAllResultsByExam(exam.getId());

            if (results.isEmpty()) {
                System.out.println("Nenhum resultado encontrado para o simulado: " + exam.getName());
            }

            // Log detalhado dos resultados
            results.forEach(result -> {
                if (result.getStudent() == null) {
                    System.out.println("Estudante está nulo para o resultado ID: " + result.getId());
                } else {
                    System.out.println("Estudante encontrado: " + result.getStudent().getName());
                    System.out.println("Turmas do estudante: " + result.getStudent().getClasses());
                }
            });

            // Agrupar os resultados por turma
            Map<String, List<ResultModel>> resultsByClass = results.stream()
                    .collect(Collectors.groupingBy(result -> {
                        ClassesModel studentClass = null;
                        if (result.getStudent() != null) {
                            studentClass = result.getStudent()
                                    .getClasses()
                                    .stream()
                                    .findFirst()
                                    .orElse(null);
                        }
                        return (studentClass != null) ? studentClass.getClassName() : "Sem turma";
                    }));

            // Processar cada turma e calcular média
            resultsByClass.forEach((className, classResults) -> {

                double average = classResults.stream()
                        .mapToDouble(result -> {
                            if (result == null) {
                                System.out.println("Resultado está nulo ao calcular a média.");
                                return 0.0;
                            }
                            return result.getTotalScore();
                        })
                        .average()
                        .orElse(0.0);

                // Criar DTO para o resumo do simulado
                ResultDTO examSummary = new ResultDTO();
                examSummary.setId(exam.getId());
                examSummary.setExamName(exam.getName());
                examSummary.setCreationDate(exam.getCreatedAt());
                examSummary.setApplication(exam.getDate());
                examSummary.setAverage(average);
                examSummary.setClassName(className);

                // Adicionar DTO à lista de resumos
                examSummaries.add(examSummary);
            });
        });

        return examSummaries;
    }


}
