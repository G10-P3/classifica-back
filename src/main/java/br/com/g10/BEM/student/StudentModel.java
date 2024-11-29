package br.com.g10.BEM.student;

import br.com.g10.BEM.classes.ClassesModel;
import br.com.g10.BEM.result.ResultModel;
import br.com.g10.BEM.user.UserModel;
import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "student")
public class StudentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_cpf", nullable = false)
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

    // Relação ManyToMany - um estudante pode pertencer a várias turmas
    @ManyToMany(mappedBy = "students")
    private List<ClassesModel> classes;

    // Correção: Remover @MapsId e usar apenas @JoinColumn
    @OneToOne
    @JoinColumn(name = "user_cpf", referencedColumnName = "cpf", nullable = false, insertable = false, updatable = false)
    private UserModel user;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResultModel> results;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentModel that = (StudentModel) o;
        return Objects.equals(userCpf, that.userCpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userCpf);
    }

}
