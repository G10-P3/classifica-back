// package br.com.g10.BEM.exam;

// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import jakarta.persistence.EntityNotFoundException;
// import jakarta.validation.Valid;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.http.HttpStatus;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;

// import java.util.List;
// import java.util.Optional;
// import java.util.UUID;

// @SuppressWarnings("rawtypes")
// @RestController
// @RequestMapping("/exam")
// public class ExamController {
    
//     @Autowired
//     private ClassesService classesService;

//     // Criando Classes(turma):
//     @PostMapping
//     public ResponseEntity createClass(@Valid @RequestBody ClassesModel classesModel) {

//         final ClassesModel classModel = classesService.createClass(classesModel);

//         return ResponseEntity.status(HttpStatus.CREATED).body(classModel);
//     }

//     // Lendo Todas as Classes:
//     @GetMapping("/")
//     public ResponseEntity<List<ClassesModel>> getAllClasses() {
//         final List<ClassesModel> classList = classesService.getAllClasses();

//         return ResponseEntity.ok(classList);
//     }

//     // Lendo Classes específicas Por ID:
//     @GetMapping("/{id}")
//     public ResponseEntity<ClassesModel> getClassById(@PathVariable UUID id) {
//         try {
//             Optional<ClassesModel> classModel = classesService.getClassById(id);

//             if (classModel.isPresent()) {
//                 return ResponseEntity.ok(classModel.get());
//             } else {
//                 return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//             }
//         } catch (Exception error) {
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//         }
//     }

//     // Atualizando Classes por ID
//     @PutMapping("/update/{id}")
//     public ResponseEntity updateClassById(@PathVariable UUID id, @RequestBody @Valid ClassesModel classesModel) {
//         try {
//             ClassesModel updatedClass = classesService.updateClassById(id, classesModel);
//             return ResponseEntity.ok(updatedClass);

//         } catch (EntityNotFoundException error) {
//             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error.getMessage());
//         } catch (Exception error) {
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER
// _ERROR).body("Algum erro ocorreu ao atualizar a turma");
//         }
//     }

//     // Deletando por ID    
//     @DeleteMapping("/delete/{id}")
//     public ResponseEntity deleteClassById(@PathVariable UUID id) {
//         if (!classesService.existsClassById(id)) {
//             return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//         }

//         classesService.deleteClassById(id);
//         return ResponseEntity.noContent().build();
//     }
// }
