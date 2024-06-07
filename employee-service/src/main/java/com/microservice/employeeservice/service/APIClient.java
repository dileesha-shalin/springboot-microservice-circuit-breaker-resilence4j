package com.microservice.employeeservice.service;

import com.microservice.employeeservice.dto.DepartmentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:8080", value = "department-service")
public interface APIClient {

    @GetMapping("api/departments/{department-code}")
    DepartmentDTO getDepartment(@PathVariable(value = "department-code") String departmentCode);
}
