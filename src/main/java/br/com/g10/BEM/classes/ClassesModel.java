package br.com.g10.BEM.classes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.util.UUID;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;

// @Data - adiciona os métodos equals, hashCode, toString, getters e setters
// @Getter - adiciona os métodos getters em todos os atributos
// @Setter - adiciona os métodos setters em todos os atributos
@Data
@Entity
@Table(name = "classes")
public class ClassesModel {    
    
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @NotBlank(message = "A turma precisa ter um nome")
    @Size(min = 3, max = 50, message = "Nome da turma entre 3 a 50 caracteres")
    @Column(unique = true, nullable = false)
    private String className;

    @Column(nullable = false)
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
