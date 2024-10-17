package br.com.g10.BEM.exam;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import br.com.g10.BEM.result.ResultModel;

@Data
@Entity
@Table(name = "exam")
public class ExamModel {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(updatable = false, nullable = false)
    private UUID id;

    @NotBlank(message = "O simulado precisa ter um nome")
    @Size(min = 3, max = 50, message = "Nome do simulado deve ter entre 3 a 50 caracteres")
    @Column(nullable = false)
    private String name;

    @Min(value = 1, message = "A quantidade de questões de matemática deve ser no mínimo 1")
    @Column(nullable = false)
    private int mathQuestionsQuantity;

    @Min(value = 1, message = "A quantidade de questões de português deve ser no mínimo 1")
    @Column(nullable = false)
    private int portugueseQuestionsQuantity;

    @Min(value = 1, message = "A quantidade total de questões deve ser no mínimo 1")
    @Column(nullable = false)
    private int questionsQuantity;

    @NotNull(message = "O simulado precisa ter uma data")
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date date;

    @Column
    private String answerSheet;

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResultModel> results;
}

