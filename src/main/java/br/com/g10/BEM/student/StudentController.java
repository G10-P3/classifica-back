package br.com.g10.BEM.student;

import br.com.g10.BEM.student.dto.StudentDetailsCompleteDTO;
import br.com.g10.BEM.student.dto.StudentDetailsDTO;
import br.com.g10.BEM.student.dto.StudentSearchDTO;
import br.com.g10.BEM.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.g10.BEM.result.ResultModel;

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
    public ResponseEntity createStudent(@RequestBody StudentModel studentModel) {
        boolean savedStudent = studentService.createStudent(studentModel);
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

    @GetMapping("/search")
    public ResponseEntity<List<StudentSearchDTO>> searchStudentsByName(@RequestParam String name) {
        List<StudentSearchDTO> students = studentService.searchStudentsByName(name);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/details/{cpf}")
    public ResponseEntity<StudentDetailsCompleteDTO> getStudentCompleteDetails(@PathVariable String cpf) {
        StudentDetailsCompleteDTO studentDetails = studentService.getStudentCompleteDetails(cpf);
        return ResponseEntity.ok(studentDetails);
    }

    @GetMapping("/details")
    public List<StudentDetailsDTO> getStudentDetails() {
        try {
            return studentService.getStudentDetails();
        } catch (Exception e) {
            System.err.println("Erro ao buscar detalhes dos estudantes: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar detalhes dos estudantes", e);
        }
    }





}
