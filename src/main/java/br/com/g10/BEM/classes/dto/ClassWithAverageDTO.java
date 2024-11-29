package br.com.g10.BEM.classes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClassWithAverageDTO {
    private String id;
    private String className;
    private String description;
    private int studentCount;
    private double average;
    private String shift;
}
