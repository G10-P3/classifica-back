package br.com.g10.BEM.student.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentDetailsDTO {
    private String fullName;
    private int age;
    private String contact;
    private double average;
}
