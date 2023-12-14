package controller;

import demo.workwear.model.Employee;
import demo.workwear.model.modelEnum.ProductionDivision;
import lombok.Data;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import service.EmployeeService;
import view.input.InputValue;

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
        ResponseEntity<Object[]> responseEntity =
                restTemplate.getForEntity(urlEmployee, Object[].class);
        Object[] objects = responseEntity.getBody();
        assert objects != null;
       return employeeService.parserJsonEmployee(objects);

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
        ProductionDivision productionDivision = ProductionDivision.getType(inputValue.input("Участок"));
        ResponseEntity<Object[]> responseEntity =
                restTemplate.getForEntity(url, Object[].class,productionDivision);
        Object[] objects = responseEntity.getBody();
        assert objects != null;
        return employeeService.parserJsonEmployee(objects);

    }

    public List<Employee> findAllEmployeeByLastName() {
        String url = urlEmployee + "/last_name/{lastName}";
        ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(url,Object[].class,inputValue.input("Фамилия"));
        Object[] objects = responseEntity.getBody();
        assert objects != null;
      return employeeService.parserJsonEmployee(objects);
    }
}
