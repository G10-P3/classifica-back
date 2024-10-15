package br.com.g10.BEM.user;

import br.com.g10.BEM.student.StudentModel;
import jakarta.persistence.*;
import lombok.Data;

// @Data - adiciona os métodos equals, hashCode, toString, getters e setters
// @Getter - adiciona os métodos getters em todos os atributos
// @Setter - adiciona os métodos setters em todos os atributos
@Data
@Entity
@Table(name = "users")
public class UserModel {

    @Id
    @Column(name = "cpf")
    private String cpf; // Usar CPF como chave primária conforme o diagrama

    @Column(unique = true, nullable = false)
    private String email; // Email deve ser único

    @Column(nullable = false)
    private String password;

    @Column(name = "profile_pic")
    private String profilePic;

    // Relacionamento 1-1 com o estudante
    @OneToOne(mappedBy = "user")
    private StudentModel student;
}
