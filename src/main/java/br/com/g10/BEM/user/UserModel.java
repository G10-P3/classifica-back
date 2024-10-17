package br.com.g10.BEM.user;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import br.com.g10.BEM.student.StudentModel;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class UserModel {

    @Id
    @Column(name = "cpf", unique = true, nullable = false)  // CPF como chave primária
    private String cpf;

    @Column(unique = true)
    private String username;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "profile_pic")
    private String profilePic;

    @OneToOne(mappedBy = "user")
    private StudentModel student;  // Relação com StudentModel
}
