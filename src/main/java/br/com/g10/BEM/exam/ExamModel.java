package br.com.g10.BEM.exam;

import org.hibernate.validator.constraints.UUID;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.Date;
import java.util.List;

import br.com.g10.BEM.result.ResultModel;


@Data
@Entity
@Table(name = "exam")
public class ExamModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @NotBlank(message = "O simulado precisa ter um nome")
    @Size(min = 3, max = 50, message = "Nome do simulado entre 3 a 50 caracteres")
    @Column
    private String name;

    @Column
    @NotBlank(message = "O simulado precisa ter a quantidade de questões de matemática")
    private int mathQuestionsQuantity;

    @Column
    @NotBlank(message = "O simulado precisa ter a quantidade de questões de português")
    private int portugueseQuestionsQuantity;

    @Column
    @NotBlank(message = "O simulado precisa ter a quantidade de questões")
    private int questionsQuantity;

    // Data do simulado
    @Column
    @NotBlank(message = "O simulado precisa ter uma data")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column
    private String answerSheet;

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResultModel> results;
}
