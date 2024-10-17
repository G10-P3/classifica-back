package br.com.g10.BEM.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<StudentModel> getAllStudents() {
        return studentService.listAllStudents();
    }

    @PostMapping
    public ResponseEntity<StudentModel> createStudent(@RequestBody StudentModel studentModel) {
        StudentModel savedStudent = studentService.createStudent(studentModel);
        return ResponseEntity.ok(savedStudent);
    }

    @GetMapping("/{userCpf}")
    public ResponseEntity<StudentModel> getStudentByCPF(@PathVariable String userCpf) {
        StudentModel student = studentService.findStudentByCPF(userCpf);
        return ResponseEntity.ok(student);
    }

    @PutMapping("/{userCpf}")
    public ResponseEntity<StudentModel> updateStudent(@PathVariable String userCpf,
                                                      @RequestBody StudentModel studentModel) {
        StudentModel updatedStudent = studentService.updateStudentByCPF(userCpf, studentModel);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{userCpf}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String userCpf) {
        studentService.deleteStudentByCPF(userCpf);
        return ResponseEntity.noContent().build();
    }
}
