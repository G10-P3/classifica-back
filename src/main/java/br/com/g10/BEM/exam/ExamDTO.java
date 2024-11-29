package br.com.g10.BEM.exam.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ExamDTO {
    private UUID id;
    private String name;
    private int mathQuestionsQuantity;
    private int portugueseQuestionsQuantity;
    private int questionsQuantity;
    private Date date;
    private String observations;
    private List<UUID> classes; // IDs das classes associadas ao simulado
}
