package br.com.g10.BEM.student.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentSearchDTO {
    private String name;
    private String className;
    private String shift;
    private int age;
    private double average;
}
