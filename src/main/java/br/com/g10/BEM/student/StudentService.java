package br.com.g10.BEM.student;

import br.com.g10.BEM.student.dto.StudentDetailsCompleteDTO;
import br.com.g10.BEM.student.dto.StudentDetailsDTO;
import br.com.g10.BEM.student.dto.StudentSearchDTO;
import br.com.g10.BEM.user.UserModel;
import br.com.g10.BEM.user.UserService;
import br.com.g10.BEM.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.g10.BEM.result.ResultModel;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserService userService;


    @Transactional
    public boolean createStudent(StudentModel studentModel) {
        System.out.println(studentModel);
        if (studentModel == null) {
            throw new IllegalArgumentException("O modelo de estudante não pode ser nulo.");
        }

        if (studentModel.getName() == null || studentModel.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do estudante não pode ser vazio.");
        }

        // Buscar o usuário pelo CPF
        final UserModel existingUser = userService.findByCpf(studentModel.getUserCpf())
                .orElseThrow(() -> new EntityNotFoundException("Usuário com CPF " + studentModel.getUserCpf() + " não encontrado"));

        // Configurar relação entre Student e User
        studentModel.setUser(existingUser);
        studentModel.setUserCpf(existingUser.getCpf());

        final StudentModel savedStudent = studentRepository.save(studentModel);

        // Persistir no banco
        return savedStudent != null;
    }
    // Lendo todos os estudantes
    public List<StudentModel> listAllStudents() {
        return studentRepository.findAll();
    }

    // Lendo estudante por CPF
    public StudentModel findStudentByCPF(String userCpf) {
        return studentRepository.findByUserCpf(userCpf)
                .orElseThrow(() -> new EntityNotFoundException("Estudante com CPF " + userCpf + " não encontrado."));
    }

    // Atualizando estudante pelo CPF
    public StudentModel updateStudentByCPF(String userCpf, StudentModel studentModel) {
        if (!studentRepository.existsById(userCpf)) {
            throw new EntityNotFoundException("Estudante com CPF " + userCpf + " não existe.");
        }

        StudentModel toUpdateStudent = findStudentByCPF(userCpf);

        toUpdateStudent.setName(studentModel.getName());
        toUpdateStudent.setLastName(studentModel.getLastName());
        toUpdateStudent.setBirthDate(studentModel.getBirthDate());
        toUpdateStudent.setGuardianName(studentModel.getGuardianName());
        toUpdateStudent.setFinancialName(studentModel.getFinancialName());
        toUpdateStudent.setPhone(studentModel.getPhone());
        toUpdateStudent.setClasses(studentModel.getClasses());

        return studentRepository.save(toUpdateStudent);
    }

    // Deletando estudante pelo CPF
    public void deleteStudentByCPF(String userCpf) {
        if (!studentRepository.existsById(userCpf)) {
            throw new EntityNotFoundException("Estudante com CPF " + userCpf + " não encontrado.");
        }
        studentRepository.deleteById(userCpf);
    }

    public List<StudentSearchDTO> searchStudentsByName(String name) {
        List<StudentModel> students = studentRepository.findByNameContainingIgnoreCase(name);

        return students.stream().map(student -> {
            String className = student.getClasses().isEmpty() ? "Sem turma" : student.getClasses().get(0).getClassName();
            String shift = student.getClasses().isEmpty() ? "N/A" : student.getClasses().get(0).getShift();
            double average = student.getResults().stream().mapToInt(ResultModel::getTotalScore).average().orElse(0.0);
            int age = DateUtils.calculateAge(student.getBirthDate());

            return new StudentSearchDTO(student.getName(), className, shift, age, average);
        }).toList();
    }

    public StudentDetailsCompleteDTO getStudentCompleteDetails(String cpf) {
        // Corrige o método para buscar pelo CPF
        StudentModel student = studentRepository.findByUserCpf(cpf)
                .orElseThrow(() -> new EntityNotFoundException("Estudante com CPF " + cpf + " não encontrado."));

        // Calcula a média das notas do estudante
        double average = student.getResults().stream()
                .mapToInt(ResultModel::getTotalScore)
                .average()
                .orElse(0.0);

        // Retorna os detalhes completos do estudante
        return new StudentDetailsCompleteDTO(
                student.getName(),
                student.getLastName(),
                student.getBirthDate(),
                student.getClasses().isEmpty() ? null : student.getClasses().get(0).getClassName(),
                student.getClasses().isEmpty() ? null : student.getClasses().get(0).getShift(),
                student.getGuardianName(),
                student.getUserCpf(),
                student.getPhone(),
                student.getUser().getEmail(),
                average,
                student.getUser().getProfilePic()
        );
    }

    public List<StudentDetailsDTO> getStudentDetails() {
        List<StudentModel> students = studentRepository.findAll();

        return students.stream()
                .map(student -> {
                    double average = student.getResults().stream()
                            .mapToDouble(ResultModel::getTotalScore)
                            .average()
                            .orElse(0.0);

                    String className = student.getClasses().isEmpty()
                            ? "Sem turma"
                            : student.getClasses().get(0).getClassName();

                    return new StudentDetailsDTO(
                            student.getName() + " " + student.getLastName(),
                            className,
                            student.getBirthDate() != null
                                    ? DateUtils.calculateAge(student.getBirthDate())
                                    : 0,
                            average
                    );
                })
                .toList();
    }








}


