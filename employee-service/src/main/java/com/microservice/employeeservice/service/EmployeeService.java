package com.microservice.employeeservice.service;

import com.microservice.employeeservice.dto.ApiResponseDTO;
import com.microservice.employeeservice.dto.EmployeeDTO;

public interface EmployeeService {
    EmployeeDTO saveEmployee(EmployeeDTO employeeDTO);

    ApiResponseDTO getEmployeeById(Long id);
}
