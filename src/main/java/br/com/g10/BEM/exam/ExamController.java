package br.com.g10.BEM.exam;

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
import org.hibernate.validator.constraints.UUID;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/exam")
public class ExamController {
    
    @Autowired
    private ExamService examService;

    // Criando Simulado
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody ExamModel request) {

        final ExamModel examModel = examService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(examModel);
    }

    // Lendo Todas os Simulados:
    @GetMapping("/")
    public ResponseEntity<List<ExamModel>> getAll() {
        final List<ExamModel> examList = examService.getAll();

        return ResponseEntity.ok(examList);
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Algum erro ocorreu ao atualizar a turma");
        }
    }

    // Deletando por ID    
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable UUID id) {
        try {
            examService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar o simulado: " + e.getMessage());
        }
    }
}
