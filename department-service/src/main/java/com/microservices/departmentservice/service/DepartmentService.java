package com.microservices.departmentservice.service;

import com.microservices.departmentservice.dto.DepartmentDTO;

public interface DepartmentService {

    DepartmentDTO saveDepartment(DepartmentDTO departmentDTO);
    DepartmentDTO getDepartmentByCode(String code);
}
