import controller.EmployeeController;
import org.springframework.web.client.RestTemplate;
import service.EmployeeService;
import view.input.InputValue;

public class Main {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        EmployeeController employeeController = new EmployeeController(restTemplate,new EmployeeService(new InputValue()));
        employeeController.findAllEmployee().forEach(System.out::println);
        //employeeController.saveNewEmployee();
    }
}
