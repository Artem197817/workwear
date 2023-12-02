import controller.EmployeeController;
import org.springframework.web.client.RestTemplate;
import service.EmployeeService;

public class Main {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        EmployeeController employeeController = new EmployeeController(restTemplate,new EmployeeService());
        employeeController.findAllEmployee().forEach(System.out::println);
    }
}
