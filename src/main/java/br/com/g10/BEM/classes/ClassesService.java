package br.com.g10.BEM.classes;

import br.com.g10.BEM.classes.dto.ClassWithAverageDTO;
import br.com.g10.BEM.result.ResultModel;
import br.com.g10.BEM.student.StudentRepository;
import br.com.g10.BEM.student.dto.StudentDetailsDTO;
import br.com.g10.BEM.student.StudentModel;
import br.com.g10.BEM.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClassesService {
    
    @Autowired
    private ClassesRepository classesRepository;

    @Autowired
    private StudentRepository studentRepository;

    // Verificando se existe turma por ID
    public boolean existsClassById(UUID id) {
        return classesRepository.existsById(id);
    }

    // Criando turma
    public boolean createClass(ClassesModel classesModel) {
        List<StudentModel> persistedStudents = classesModel.getStudents().stream()
                .map(inputStudent -> {
                    Optional<StudentModel> studentOpt = studentRepository.findByUserCpf(inputStudent.getUserCpf());
                    if (studentOpt.isEmpty()) {
                        System.err.println("Estudante com CPF " + inputStudent.getUserCpf() + " não encontrado.");
                        throw new EntityNotFoundException("Estudante com CPF " + inputStudent.getUserCpf() + " não encontrado.");
                    }
                    StudentModel persistedStudent = studentOpt.get();
                    System.out.println("Estudante encontrado: " + persistedStudent.getName());
                    return persistedStudent;
                })
                .toList();
        classesModel.setStudents(persistedStudents);

        final ClassesModel response = classesRepository.save(classesModel);

        return response != null;
    }

    // Lendo todas as turmas
    public List<ClassesModel> getAllClasses() {
        return classesRepository.findAll();
    }

    // Lendo turma por id
    public Optional<ClassesModel> getClassById(UUID id) {
        return classesRepository.findById(id);
    }

    // Atualizando as Turmas
    public ClassesModel updateClassById(UUID id, ClassesModel classesModel) {
        if (classesRepository.existsById(id)) {
            ClassesModel toUpdateClass = classesRepository.findById(id).get();

            toUpdateClass.setClassName(classesModel.getClassName());
            toUpdateClass.setDescription(classesModel.getDescription());
            return classesRepository.save(toUpdateClass);
        } else {
            throw new EntityNotFoundException("Uma turma com o id " + id + " Não existe.");
        }
    }

    // Deletando turma por ID
    public void deleteClassById(UUID id) {
        classesRepository.deleteById(id);
    }


    public List<StudentDetailsDTO> getStudentsByClass(UUID classId) {
        ClassesModel classesModel = classesRepository.findById(classId).orElseThrow(() -> new EntityNotFoundException("Turma não encontrada com o ID " + classId));

        return classesModel.getStudents().stream().map(student -> {
            double average = student.getResults().stream().mapToInt(ResultModel::getTotalScore).average().orElse(0.0);
            int age = DateUtils.calculateAge(student.getBirthDate());

            return new StudentDetailsDTO(student.getName() + " " + student.getLastName(), age, student.getPhone(), average
            );
        }).toList();
    }



    public List<ClassWithAverageDTO> getAllClassesWithAverage() {
        List<ClassesModel> classList = classesRepository.findAll();

        return classList.stream().map(classModel -> {
            List<StudentModel> students = classModel.getStudents();
            List<ResultModel> results = students.stream()
                    .flatMap(student -> student.getResults().stream())
                    .toList();

            double average = results.stream()
                    .mapToInt(ResultModel::getTotalScore)
                    .average()
                    .orElse(0.0);

            return new ClassWithAverageDTO(
                    classModel.getId().toString(),
                    classModel.getClassName(),
                    classModel.getDescription(),
                    students.size(),
                    average,
                    classModel.getShift()
            );
        }).toList();
    }






}
