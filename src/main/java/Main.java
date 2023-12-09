import controller.EmployeeController;
import controller.WorkShoesController;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.web.client.RestTemplate;
import service.EmployeeService;
import service.WorkShoesService;
import view.input.InputValue;

public class Main {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        InputValue inputValue = new InputValue();
        EmployeeController employeeController = new EmployeeController(restTemplate,inputValue,new EmployeeService(inputValue));
        employeeController.findAllEmployee().forEach(System.out::println);
       // employeeController.saveNewEmployee();
       // employeeController.deleteEmployee();
        //employeeController.findById();
       // employeeController.findAllEmployeeByProductionDivision().forEach(System.out::println);
       // employeeController.findAllEmployeeByLastName().forEach(System.out::println);
        WorkShoesService workShoesService = new WorkShoesService(inputValue);
        WorkShoesController workShoesController = new WorkShoesController(restTemplate,workShoesService,inputValue);
        workShoesController.saveNewWorkShoes();
    }
}
