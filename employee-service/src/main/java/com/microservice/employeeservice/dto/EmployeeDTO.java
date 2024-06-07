package com.microservice.employeeservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String departmentCode;
}
