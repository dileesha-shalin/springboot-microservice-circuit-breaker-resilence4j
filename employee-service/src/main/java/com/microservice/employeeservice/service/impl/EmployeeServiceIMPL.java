package com.microservice.employeeservice.service.impl;

import com.microservice.employeeservice.dto.ApiResponseDTO;
import com.microservice.employeeservice.dto.DepartmentDTO;
import com.microservice.employeeservice.dto.EmployeeDTO;
import com.microservice.employeeservice.entity.Employee;
import com.microservice.employeeservice.repository.EmployeeRepository;
import com.microservice.employeeservice.service.APIClient;
import com.microservice.employeeservice.service.EmployeeService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;



@Service
@AllArgsConstructor
public class EmployeeServiceIMPL implements EmployeeService {

private static final Logger LOGGER= LoggerFactory.getLogger(EmployeeServiceIMPL.class);


    private EmployeeRepository employeeRepository;
//    private RestTemplate restTemplate;


private WebClient webClient;

   private APIClient apiClient;


    @Override
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee=new Employee(
                employeeDTO.getId(),
                employeeDTO.getFirstname(),
                employeeDTO.getLastname(),
                employeeDTO.getEmail(),
                employeeDTO.getDepartmentCode()
        );
        Employee savedEmployee=employeeRepository.save(employee);

        EmployeeDTO savedEmployeeDTO= new EmployeeDTO(
                savedEmployee.getId(),
                savedEmployee.getFirstname(),
                savedEmployee.getLastname(),
                savedEmployee.getEmail(),
                savedEmployee.getDepartmentCode()
        );
        return savedEmployeeDTO;
    }


    @CircuitBreaker(name="${spring.application.name}",fallbackMethod = "getDefaultDepartment")
    @Retry(name="${spring.application.name}",fallbackMethod = "getDefaultDepartment")
    @Override
    public ApiResponseDTO getEmployeeById(Long id) {
        LOGGER.info("inside getEmployeeById method");
        Employee employee= employeeRepository.findById(id).get();

        //communication with rest template
       /* ResponseEntity<DepartmentDTO> responseEntity =restTemplate.
                getForEntity("http://localhost:8080/api/departments/" + employee.getDepartmentCode(),
                        DepartmentDTO.class);


        DepartmentDTO departmentDTO=responseEntity.getBody();*/


        // using web client
        DepartmentDTO departmentDTO= webClient.get()
                .uri("http://localhost:8080/api/departments/" + employee.getDepartmentCode())
                .retrieve()
                .bodyToMono(DepartmentDTO.class)
                .block();


       // DepartmentDTO departmentDTO=apiClient.getDepartment(employee.getDepartmentCode());


        EmployeeDTO employeeDTO= new EmployeeDTO(
                employee.getId(),
                employee.getFirstname(),
                employee.getLastname(),
                employee.getEmail(),
                employee.getDepartmentCode()
        );

        ApiResponseDTO apiResponseDTO = new ApiResponseDTO();
        apiResponseDTO.setEmployeeDTO(employeeDTO);
        apiResponseDTO.setDepartmentDTO(departmentDTO);
        return apiResponseDTO;
    }

    public ApiResponseDTO getDefaultDepartment(Long id, Exception exception) {
        LOGGER.info("inside getDefaultDepartment method");
        Employee employee= employeeRepository.findById(id).get();

        DepartmentDTO departmentDTO= new DepartmentDTO();

        departmentDTO.setDepartmentName("Default Department");
        departmentDTO.setDepartmentCode("D001");
        departmentDTO.setDepartmentDescription("Development Department");

        EmployeeDTO employeeDTO= new EmployeeDTO(
                employee.getId(),
                employee.getFirstname(),
                employee.getLastname(),
                employee.getEmail(),
                employee.getDepartmentCode()
        );

        ApiResponseDTO apiResponseDTO = new ApiResponseDTO();
        apiResponseDTO.setEmployeeDTO(employeeDTO);
        apiResponseDTO.setDepartmentDTO(departmentDTO);
        return apiResponseDTO;
    }
}
