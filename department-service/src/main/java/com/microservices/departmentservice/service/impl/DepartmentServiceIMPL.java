package com.microservices.departmentservice.service.impl;

import com.microservices.departmentservice.dto.DepartmentDTO;
import com.microservices.departmentservice.entity.Department;
import com.microservices.departmentservice.repository.DepartmentRepository;
import com.microservices.departmentservice.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DepartmentServiceIMPL implements DepartmentService {

    private DepartmentRepository departmentRepository;
    @Override
    public DepartmentDTO saveDepartment(DepartmentDTO departmentDTO) {
        Department department=new Department(
                departmentDTO.getId(),
                departmentDTO.getDepartmentName(),
                departmentDTO.getDepartmentDescription(),
                departmentDTO.getDepartmentCode()
        );

        Department savedDepartment = departmentRepository.save(department);

        DepartmentDTO savedDepartmentDTO= new DepartmentDTO(
                savedDepartment.getId(),
                savedDepartment.getDepartmentName(),
                savedDepartment.getDepartmentDescription(),
                savedDepartment.getDepartmentCode()
        );
        return savedDepartmentDTO;
    }

    @Override
    public DepartmentDTO getDepartmentByCode(String code) {
        Department department=departmentRepository.findByDepartmentCode(code);
        DepartmentDTO departmentDTO=new DepartmentDTO(
                department.getId(),
                department.getDepartmentName(),
                department.getDepartmentDescription(),
                department.getDepartmentCode()
        );
        return departmentDTO;
    }
}
