package br.com.g10.BEM.exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.g10.BEM.classes.ClassesModel;
import br.com.g10.BEM.classes.ClassesRepository;
import br.com.g10.BEM.exceptions.ExamAlreadyExistsException;
import br.com.g10.BEM.exceptions.ExamNotFoundException;

@Service
public class ExamService {
    
    private final ExamRepository examRepository;

    @Autowired
    private ClassesRepository classesRepository;


    public ExamService(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    public boolean create(ExamModel examModel) {
        System.out.println(examModel.getName());

        // Verifica se a lista de classes é nula e a inicializa
        if (examModel.getClasses() == null) {
            examModel.setClasses(new ArrayList<>());
        }

        // Verifica se o simulado já existe
        final ExamModel existingExam = examRepository.findByName(examModel.getName());

        if (existingExam != null) {
            throw new ExamAlreadyExistsException("Exam already exists");
        }

        // Buscar as classes no banco de dados
        List<ClassesModel> persistedClasses = examModel.getClasses().stream()
                .map(classModel -> {
                    System.out.println("Procurando classe com ID: " + classModel.getId());
                    return classesRepository.findById(classModel.getId())
                            .orElseThrow(() -> new ExamNotFoundException("Classe com ID " + classModel.getId() + " não encontrada."));
                })
                .toList();

        // Associar as classes encontradas ao simulado
        examModel.setClasses(persistedClasses);

        // Salvar o simulado
        final ExamModel savedExam = examRepository.save(examModel);
        System.out.println(savedExam.getId());

        return savedExam != null;
    }


    public List<ExamModel> getAll() {
        final List<ExamModel> exams = examRepository.findAll();

        // Fazer com que o retorno não tenha referência circular
        exams.forEach(exam -> {
            exam.getResults().forEach(result -> {
                result.setExam(null);
                result.setStudent(null);
            });
            exam.getClasses().forEach(classesModel -> {
                classesModel.setStudents(null);
            });
        });

        return examRepository.findAll();
    }

    public Optional<ExamModel> getById(UUID id) {
        return examRepository.findById(id);
    }

    public ExamModel update(UUID id, ExamModel examModel) {
        if (examRepository.existsById(id)) {
            final ExamModel toUpdateExam = examRepository.findById(id)
                    .orElseThrow(() -> new ExamNotFoundException("Exam not found"));

            if (examModel.getName() != null) {
                toUpdateExam.setName(examModel.getName());
            }
            if (examModel.getMathQuestionsQuantity() != 0) {
                toUpdateExam.setMathQuestionsQuantity(examModel.getMathQuestionsQuantity());
            }
            if (examModel.getPortugueseQuestionsQuantity() != 0) {
                toUpdateExam.setPortugueseQuestionsQuantity(examModel.getPortugueseQuestionsQuantity());
            }
            if (examModel.getQuestionsQuantity() != 0) {
                toUpdateExam.setQuestionsQuantity(examModel.getQuestionsQuantity());
            }
            if (examModel.getDate() != null) {
                toUpdateExam.setDate(examModel.getDate());
            }

            return examRepository.save(toUpdateExam);
        } else {
            throw new ExamNotFoundException("Exam not found");
        }
    }

    public void delete(UUID id) {
        // Verifica se o simulado existe
        if (!examRepository.existsById(id)) {
            // Lança uma exceção se o simulado não existe
            throw new ExamNotFoundException("Exam not found");
        }
        
        examRepository.deleteById(id);
    }
}
