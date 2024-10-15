//package br.com.g10.BEM.student;
//
//import br.com.g10.BEM.student.StudentModel;
//import br.com.g10.BEM.student.StudentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/students")
//public class StudentController {
//
//    @Autowired
//    private StudentService studentService;
//
//    @GetMapping
//    public List<StudentModel> getAllStudents() {
//        return studentService.listAllStudents();
//    }
//
//    @PostMapping
//    public ResponseEntity<StudentModel> createStudent(@RequestBody StudentModel studentModel) {
//        StudentModel savedStudent = studentService.createStudent(studentModel);
//        return ResponseEntity.ok(savedStudent);
//    }
//
//    @GetMapping("/{userCpf}")
//    public ResponseEntity<StudentModel> getStudentByCPF(@PathVariable String userCpf) {
//        Optional<StudentModel> student = studentService.findStudentByCPF(userCpf);
//        return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @PutMapping("/{userCpf}")
//    public ResponseEntity<StudentModel> updateStudent(@PathVariable String userCpf,
//                                                      @RequestBody StudentModel studentModel) {
//        StudentModel updatedStudent = studentService.updateStudentByCPF(userCpf, studentModel);
//        return ResponseEntity.ok(updatedStudent);
//    }
//
//    @DeleteMapping("/{userCpf}")
//    public ResponseEntity<Void> deleteStudent(@PathVariable String userCpf) {
//        studentService.deleteStudentByCPF(userCpf);
//        return ResponseEntity.noContent().build();
//    }
//}
