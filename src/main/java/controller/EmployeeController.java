package controller;

import demo.workwear.model.Employee;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import service.EmployeeService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class EmployeeController {


    private final RestTemplate restTemplate;
    private final String urlEmployee = "http://localhost:8080/employee";

    private final EmployeeService employeeService;

    public EmployeeController(RestTemplate restTemplate, EmployeeService employeeService) {
        this.restTemplate = restTemplate;
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
        String response = restTemplate.postForObject(url,request,String.class);
        System.out.println(response);
    }
}
