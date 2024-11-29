package br.com.g10.BEM.result;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import br.com.g10.BEM.exam.ExamModel;
import br.com.g10.BEM.student.StudentModel;

@Data
@Entity
@Table(name = "result")
public class ResultModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column
    @Min(value = 0, message = "A pontuação de matemática não pode ser negativa")
    private int mathScore;
    
    @Column
    @Min(value = 0, message = "A pontuação de português não pode ser negativa")
    private int portugueseScore;

    @Column
    @Min(value = 0, message = "A pontuação total não pode ser negativa")
    private int totalScore;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "exam_id", nullable = false)
    private ExamModel exam;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private StudentModel student;
}
