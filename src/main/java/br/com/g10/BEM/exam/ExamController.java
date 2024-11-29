package br.com.g10.BEM.exam;

import br.com.g10.BEM.classes.ClassesModel;
import br.com.g10.BEM.classes.ClassesRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/exam")
public class ExamController {
    
    @Autowired
    private ExamService examService;

    @Autowired
    private ClassesRepository classesRepository;

    // Criando Simulado
    @PostMapping
    public ResponseEntity<br.com.g10.BEM.exam.dto.ExamDTO> create(@RequestBody br.com.g10.BEM.exam.dto.ExamDTO request) {
        if (request.getClasses() == null || request.getClasses().isEmpty()) {
            return ResponseEntity.badRequest().body(null); // Ou envie uma mensagem explicativa
        }

        ExamModel examModel = new ExamModel();
        examModel.setName(request.getName());
        examModel.setMathQuestionsQuantity(request.getMathQuestionsQuantity());
        examModel.setPortugueseQuestionsQuantity(request.getPortugueseQuestionsQuantity());
        examModel.setQuestionsQuantity(request.getQuestionsQuantity());
        examModel.setDate(request.getDate());
        examModel.setObservations(request.getObservations());

        // Lógica para buscar e associar as classes ao simulado
        List<ClassesModel> classes = request.getClasses().stream()
                .map(classId -> classesRepository.findById(classId)
                        .orElseThrow(() -> new EntityNotFoundException("Classe com ID " + classId + " não encontrada")))
                .toList();
        examModel.setClasses(classes);

        boolean created = examService.create(examModel);

        if (created) {
            return ResponseEntity.status(201).body(request);
        }
        return ResponseEntity.status(500).build();
    }


    // Lendo Simulado específica Por ID:
    @GetMapping("/{id}")
    public ResponseEntity<ExamModel> getById(@PathVariable UUID id) {
        try {
            final Optional<ExamModel> examModel = examService.getById(id);

            if (examModel.isPresent()) {
                return ResponseEntity.ok(examModel.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Atualizando Simulado por ID
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable UUID id, @RequestBody @Valid ExamModel examModel) {
        try {
            final ExamModel updatedExam = examService.update(id, examModel);
            return ResponseEntity.ok(updatedExam);

        } catch (EntityNotFoundException error) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error.getMessage());
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Algum erro ocorreu ao atualizar a turma");
        }
    }

    // Deletando por ID    
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable UUID id) {
        try {
            examService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao deletar o simulado: " + e.getMessage());
        }
    }
    // Lendo Todos os Simulados
    @GetMapping
    public ResponseEntity<List<ExamModel>> getAllExams() {
        try {
            List<ExamModel> exams = examService.getAll(); // Certifique-se de que examService.getAll() esteja implementado.
            return ResponseEntity.ok(exams);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
