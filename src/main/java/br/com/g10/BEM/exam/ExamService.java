package br.com.g10.BEM.exam;

import java.util.List;
import java.util.Optional;

import org.hibernate.validator.constraints.UUID;

import org.springframework.stereotype.Service;

import br.com.g10.BEM.exceptions.ExamAlreadyExistsException;
import br.com.g10.BEM.exceptions.ExamNotFoundException;

@Service
public class ExamService {
    
    private final IExamRepository examRepository;

    public ExamService(IExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    public ExamModel create(ExamModel examModel) {
        // Verifica se o simulado já existe
        final ExamModel existingExam = examRepository.findByName(examModel.getName());

        if (existingExam != null) {
            // Lança uma exceção se o simulado já existe
            throw new ExamAlreadyExistsException("Exam already exists");
        }

        // Cria e salva o novo simulado
        return examRepository.save(examModel);
    }

    public List<ExamModel> getAll() {
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
            if (examModel.getAnswerSheet() != null) {
                toUpdateExam.setAnswerSheet(examModel.getAnswerSheet());
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
