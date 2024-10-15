//package br.com.g10.BEM.student;
//
//import br.com.g10.BEM.student.StudentModel;
//import br.com.g10.BEM.student.StudentRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import jakarta.persistence.EntityNotFoundException;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class StudentService {
//
//    @Autowired
//    private StudentRepository studentRepository;
//
//    public boolean existsStudentByCPF(String userCpf) {
//        return studentRepository.existsById(userCpf);
//    }
//
//
//    public StudentModel createStudent(StudentModel studentModel) {
//        if (studentModel == null) {
//            throw new IllegalArgumentException("O modelo de estudante não pode ser nulo.");
//        }
//        if (studentModel.getName() == null || studentModel.getName().trim().isEmpty()) {
//            throw new IllegalArgumentException("O nome do estudante não pode ser vazio.");
//        }
//
//        return studentRepository.save(studentModel);
//    }
//
//    public List<StudentModel> listAllStudents() {
//        return studentRepository.findAll();
//    }
//
//    public Optional<StudentModel> findStudentByCPF(String userCpf) {
//        return studentRepository.findById(userCpf);
//    }
//
//    public StudentModel updateStudentByCPF(String userCpf, StudentModel studentModel) {
//        if (studentRepository.existsById(userCpf)) {
//            StudentModel toUpdateStudent = studentRepository.findById(userCpf).get();
//
//            toUpdateStudent.setName(studentModel.getName());
//            toUpdateStudent.setLastName(studentModel.getLastName());
//            toUpdateStudent.setBirthDate(studentModel.getBirthDate());
//            toUpdateStudent.setGuardianName(studentModel.getGuardianName());
//            toUpdateStudent.setFinancialName(studentModel.getFinancialName());
//            toUpdateStudent.setPhone(studentModel.getPhone());
//
//            return studentRepository.save(toUpdateStudent);
//        } else {
//            throw new EntityNotFoundException("Um estudante com o CPF " + userCpf + " não existe.");
//        }
//    }
//
//    public void deleteStudentByCPF(String userCpf) {
//        studentRepository.deleteById(userCpf);
//    }
//}
