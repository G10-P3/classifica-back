package br.com.g10.BEM.student.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentDetailsDTO {
    private String fullName;
    private String className;
    private int age;
    private double average;
}
