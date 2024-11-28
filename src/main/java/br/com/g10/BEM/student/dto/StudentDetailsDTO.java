package br.com.g10.BEM.student.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentDetailsDTO {
    private String name;
    private int age;
    private String phone;
    private double average;
}
