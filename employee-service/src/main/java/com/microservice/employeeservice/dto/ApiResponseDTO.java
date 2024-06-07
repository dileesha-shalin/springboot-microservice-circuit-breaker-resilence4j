package com.microservice.employeeservice.dto;

import com.microservice.employeeservice.dto.EmployeeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApiResponseDTO {

    private EmployeeDTO employeeDTO;
    private DepartmentDTO departmentDTO;
}
