package br.com.g10.BEM.result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.g10.BEM.classes.ClassesModel;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/results")
public class ResultController {

    @Autowired
    private ResultService resultService;

    // Criar um novo resultado
    @PostMapping
    public ResponseEntity createResult(@RequestBody ResultModel result) {
        final boolean createdResult = resultService.createResult(result);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(createdResult);
    }

    // Listar todos os resultados
    @GetMapping
    public ResponseEntity<List<ResultModel>> getAllResults() {
        List<ResultModel> results = resultService.getAllResults();
        return ResponseEntity.ok(results);
    }

    // Buscar um resultado por ID
    @GetMapping("/{id}")
    public ResponseEntity<ResultModel> getResultById(@PathVariable UUID id) {
        ResultModel result = resultService.getResultById(id);
        return ResponseEntity.ok(result);
    }

    // Atualizar um resultado por ID
    @PutMapping("/{id}")
    public ResponseEntity<ResultModel> updateResult(
            @PathVariable UUID id,
            @RequestBody ResultModel resultDetails) {
        ResultModel updatedResult = resultService.updateResult(id, resultDetails);
        return ResponseEntity.ok(updatedResult);
    }

    // Deletar um resultado por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResult(@PathVariable UUID id) {
        resultService.deleteResult(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/summaries")
    public ResponseEntity<List<Map<String, Object>>> getExamSummaries() {
        List<Map<String, Object>> summaries = resultService.getExamSummaries();
        return ResponseEntity.ok(summaries);
    }


}
