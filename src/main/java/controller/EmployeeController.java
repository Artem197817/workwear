package controller;

import demo.workwear.model.Employee;
import demo.workwear.model.modelEnum.ProductionDivision;
import lombok.Data;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import service.EmployeeService;
import view.input.InputValue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Data
public class EmployeeController {


    private final RestTemplate restTemplate;
    private final String urlEmployee = "http://localhost:8080/employee";
    private final InputValue inputValue;

    private final EmployeeService employeeService;

    public EmployeeController(RestTemplate restTemplate, InputValue inputValue, EmployeeService employeeService) {
        this.restTemplate = restTemplate;
        this.inputValue = inputValue;
        this.employeeService = employeeService;
    }

    public List<Employee> findAllEmployee() {
        List<Employee> employees;
        String employee = restTemplate.getForObject(urlEmployee, String.class);
        assert employee != null;
        employees = employeeService.parserJsonEmployee(employee);

        return employees;
    }

    public void saveNewEmployee() {
        String url = urlEmployee + "/save_employee";
        HttpEntity<Map<String, String>> request = new HttpEntity<>(employeeService.createMapNewEmployee());
        String response = restTemplate.postForObject(url, request, String.class);
        System.out.println(response);
    }

    public void deleteEmployee() {
        String url = urlEmployee + "/delete_employee/{id}";
        this.restTemplate.delete(url, inputValue.inputLong("id"));
    }

    public Employee findById() {
        String url = urlEmployee + "/{id}";
        Employee employee = restTemplate.getForObject(url, Employee.class, inputValue.inputLong("id"));
        System.out.println(employee);
        return employee;
    }

    public Employee updateEmployee() {
        return null;
    }

    public List<Employee> findAllEmployeeByProductionDivision() {
        String url = urlEmployee + "/pd/{productionDivision}";
        List<Employee> employees;
        ProductionDivision productionDivision = ProductionDivision.getType(inputValue.input("Участок"));
        String employee = restTemplate.getForObject(url, String.class, productionDivision);
        assert employee != null;
        employees = employeeService.parserJsonEmployee(employee);

        return employees;
    }

    public List<Employee> findAllEmployeeByLastName() {
        String url = urlEmployee + "/last_name/{lastName}";
        List<Employee> employees;
        String employee = restTemplate.getForObject(url, String.class, inputValue.input("Фамилия"));
        assert employee != null;
        employees = employeeService.parserJsonEmployee(employee);

        return employees;
    }
}
