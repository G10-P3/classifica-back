package br.com.g10.BEM.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.g10.BEM.exceptions.ClassAlreadyExistsException;

import java.util.List;
import java.util.UUID;

@Service
public class ClassesService {
    
    @Autowired
    private ClassesRepository classesRepository;

    // Lendo todas as turmas
    public List<ClassesModel> listarTodas(){
        return classesRepository.findAll();
    }
    
    // Lendo turma por nome
    public ClassesModel buscarClassePorNome(String className){
        return classesRepository.findByClassName(className);
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
    
        // Busca se a turma já existe
        ClassesModel classe = classesRepository.findByClassName(classesModel.getClassName());
        if (classe != null) {
            throw new ClassAlreadyExistsException("Uma turma com o nome " + classesModel.getClassName() + " já existe.");
        }
    
        // Salva a nova turma
        return classesRepository.save(classesModel);
    }
    
    

    // Atualizando as Turmas
    // - Por id:
    public ClassesModel atualizarClassePorID(UUID id, ClassesModel classesModel){
        if(classesRepository.existsById(id)){
            classesModel.setId(id);
            return classesRepository.save(classesModel);

        }
        throw new RuntimeException("Uma turma com o nome " + classesModel.getClassName() + " já existe.");
    }

    // - Por nome da turma:
    public ClassesModel atualizarClassePorNome(String className, ClassesModel classesModel) {
        ClassesModel classe = classesRepository.findByClassName(className);
        
        if (classe != null) {

            classe.setClassName(classesModel.getClassName());
            classe.setDescription(classesModel.getDescription());

            return classesRepository.save(classe); 
        }
        
        throw new RuntimeException("Turma não encontrada com o nome: " + className);
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
