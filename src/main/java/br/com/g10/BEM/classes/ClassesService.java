package br.com.g10.BEM.classes;

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

    // Verificando se existe turma por ID
    public boolean existsClassById(UUID id) {
        return classesRepository.existsById(id);
    }

    // Criando turma
    public ClassesModel createClass(ClassesModel classesModel) {
        // Validações
        if (classesModel == null) {
            throw new IllegalArgumentException("O modelo de classe não pode ser nulo.");
        }
        if (classesModel.getClassName() == null || classesModel.getClassName().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da turma não pode ser vazio.");
        }
        
        // Salva a nova turma
        return classesRepository.save(classesModel);
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

}
