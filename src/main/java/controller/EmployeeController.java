package controller;

import demo.workwear.model.Employee;
import demo.workwear.model.modelEnum.ProductionDivision;
import lombok.Data;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import service.EmployeeService;
import view.input.InputValue;
import view.output.Output;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class EmployeeController {


    private final RestTemplate restTemplate;
    private final String urlEmployee = "http://localhost:8080/employee";
    private final InputValue inputValue;
    private final EmployeeService employeeService;
    private final Output output;

    public EmployeeController(RestTemplate restTemplate, InputValue inputValue, EmployeeService employeeService, Output output) {
        this.restTemplate = restTemplate;
        this.inputValue = inputValue;
        this.employeeService = employeeService;
        this.output = output;
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
        try {
            String response = restTemplate.postForObject(url, request, String.class);
            System.out.println(response);
        }catch (Exception e){
            System.out.println("Неверно заданы параметры");
        }
    }

    public void deleteEmployee() {
        String url = urlEmployee + "/delete_employee/{id}";
        this.restTemplate.delete(url, inputValue.inputLong("id"));
    }

    public Employee findById() {
        String url = urlEmployee + "/{id}";
        Employee employee = restTemplate.getForObject(url, Employee.class, inputValue.inputLong("id сотрудника"));
        System.out.println(employee);
        return employee;
    }
    public Employee findById(Long id) {
        String url = urlEmployee + "/{id}";
        return restTemplate.getForObject(url, Employee.class, id);
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

    public List<Employee> findEmployeeByLastName() {
        String url = urlEmployee + "/last_name/{lastName}";
        try {
            ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(url, Object[].class, inputValue.input("Фамилия сотрудника"));
            Object[] objects = responseEntity.getBody();
            assert objects != null;
            return employeeService.parserJsonEmployee(objects);
        } catch (Exception e){
            System.out.println("Неверно заданы параметры");
        }
        return new ArrayList<>();
    }
    public Employee findEmployee() {

        List<Employee> employeeList = findEmployeeByLastName();
        output.outputList(employeeList);
        Long idEmployee = inputValue.inputLong("Введите id сотрудника для выдачи спецодежды ");
        return employeeList.stream().filter(employee -> employee.getId().equals(idEmployee)).findFirst().orElse(null);
    }
    public Long findEmployeeId () {
        List<Employee> employees = findEmployeeByLastName();
        if (employees.isEmpty()) {
            output.output("Нет сотрудника с такой фамилией");
            return -1L;
        }
        output.outputList(employees);
        output.output("Введите id сотрудника");
        return findById().getId();

    }
}
