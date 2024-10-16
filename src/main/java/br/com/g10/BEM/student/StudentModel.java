package br.com.g10.BEM.student;

import br.com.g10.BEM.classes.ClassesModel;
import br.com.g10.BEM.user.UserModel;
import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import java.util.Date;

@Data
@Entity
@Table(name = "student")
public class StudentModel {

    @Id
    @Column(name = "user_cpf")
    private String userCpf;

    @NotBlank(message = "O nome do aluno é obrigatório")
    private String name;

    @NotBlank(message = "O sobrenome do aluno é obrigatório")
    private String lastName;

    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @NotBlank(message = "O nome do responsável é obrigatório")
    private String guardianName;

    private String financialName;

    private String phone;

    // Relação opcional-- um estudante pode pertencer a uma turma ou nao
    @ManyToOne
    @JoinColumn(name = "class_id", nullable = true) // É true porque um estudante pode nao pertencer a umma turma
    private ClassesModel classEntity;

    @OneToOne
    @JoinColumn(name = "cpf", nullable = false)
    private UserModel user;
}
