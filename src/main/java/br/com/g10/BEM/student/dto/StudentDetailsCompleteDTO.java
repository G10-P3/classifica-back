package br.com.g10.BEM.student.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class StudentDetailsCompleteDTO {
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String className;
    private String shift;
    private String guardianName;
    private String cpf;
    private String phone;
    private String email;
    private double average;
    private String profilePic;
}
