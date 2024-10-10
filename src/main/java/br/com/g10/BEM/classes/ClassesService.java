package br.com.g10.BEM.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.g10.BEM.exceptions.ClassAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class ClassesService {
    
    @Autowired
    private ClassesRepository classesRepository;

    // Verificando se existe turma por ID
    public boolean existeClassePorID(UUID id){
        return classesRepository.existsById(id);
    }
    
    
    // Criando turma
    public ClassesModel criarClasse(ClassesModel classesModel) {
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
    public List<ClassesModel> listarTodas(){
        return classesRepository.findAll();
    }
    
    // Lendo turma por id
    public Optional<ClassesModel> buscarClassePorID(UUID id){
        return classesRepository.findById(id);
        
    }


    // Atualizando as Turmas
    // - Por id:
    public ClassesModel atualizarClassePorID(UUID id, ClassesModel classesModel){
        if(classesRepository.existsById(id)){
            classesModel.setId(id);
            return classesRepository.save(classesModel);

        }
        throw new EntityNotFoundException("Uma turma com o id " + classesModel.getId() + " Não existe.");
    }
    

    // Deletando turma
    // - Deletando por Id:
    public void deletarClassePorId(UUID id){
        classesRepository.deleteById(id);
    }

    // - Deletando por Nome:
    public void deletarClassePorNome(String name){
        ClassesModel classe = classesRepository.findByClassName(name);

        if(classe != null){
            classesRepository.delete(classe);
        }else{
            throw new RuntimeException("Turma de nome: " + name + " não foi encontrada!" );

        }
    }
    
}
