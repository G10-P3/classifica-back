package br.com.g10.BEM.classes;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


@RestController
@RequestMapping("/classes")
public class ClassesController {
    
    @Autowired
    private ClassesService classesService;

    @GetMapping("/")
    public ResponseEntity<List<ClassesModel>> listarTodas() {
        List<ClassesModel> classes = classesService.listarTodas();

        return ResponseEntity.ok(classes);
    }

    @GetMapping("/{className}")
    public ResponseEntity<ClassesModel> buscarPorNome(@PathVariable String className ) {
        ClassesModel classe = classesService.buscarClassePorNome(className);

        if(classe != null){
            
             return ResponseEntity.status(HttpStatus.CREATED).body(classe);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        
    }
    
    @PostMapping
    public ResponseEntity criarTurma(@Valid @RequestBody ClassesModel classesModel) {

        ClassesModel classe = classesService.criarClasse(classesModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(classe);
    }

    @PutMapping("/name/{className}")
    public ResponseEntity atualizarPorNome(@PathVariable  String className, @RequestBody ClassesModel classesModel) {
        
        ClassesModel classe = classesService.atualizarClassePorNome(className, classesModel);

        return ResponseEntity.ok(classe);
    }

    @DeleteMapping("/name/{className}")
    public ResponseEntity deletarPorNome(@PathVariable String className){
        classesService.deletarClassePorNome(className);
        return ResponseEntity.noContent().build();
    }
    
    
    

}
