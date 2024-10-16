package br.com.g10.BEM.classes;

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
@RequestMapping("/classes")
public class ClassesController {

    @Autowired
    private ClassesService classesService;

    // Criando Classes(turma):
    @PostMapping
    public ResponseEntity criarTurma(@Valid @RequestBody ClassesModel classesModel) {
        final ClassesModel classe = classesService.criarClasse(classesModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(classe);
    }

    // Lendo Todas as Classes:
    @GetMapping("/")
    public ResponseEntity<List<ClassesModel>> listarTodas() {
        final List<ClassesModel> classes = classesService.listarTodas();
        return ResponseEntity.ok(classes);
    }

    // Lendo Classes específicas Por ID:
    @GetMapping("/{id}")
    public ResponseEntity<ClassesModel> buscarPorID(@PathVariable UUID id) {
        try {
            final Optional<ClassesModel> classe = classesService.buscarClassePorID(id);

            if (classe.isPresent()) {
                return ResponseEntity.ok(classe.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception erro) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Atualizando Classes por ID
    @PutMapping("/atualizar/{id}")
    public ResponseEntity atualizarPorID(@PathVariable UUID id, @RequestBody @Valid ClassesModel classesModel) {
        try {
            final ClassesModel classe = classesService.atualizarClassePorID(id, classesModel);
            return ResponseEntity.ok(classe);
        } catch (EntityNotFoundException erro) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro.getMessage());
        } catch (Exception erro) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Algum erro ao atualizar a turma");
        }
    }

    // Deletando por ID
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity deletarPorID(@PathVariable UUID id) {
        if (!classesService.existeClassePorID(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        classesService.deletarClassePorId(id);
        return ResponseEntity.noContent().build();
    }
}
