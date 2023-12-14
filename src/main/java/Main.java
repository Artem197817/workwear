import controller.EmployeeController;
import controller.WorkShoesController;
import controller.WorkWearController;
import org.springframework.web.client.RestTemplate;
import service.EmployeeService;
import service.WorkShoesService;
import service.WorkWearService;
import view.input.InputValue;
import view.output.Output;

public class Main {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        InputValue inputValue = new InputValue();
        Output output = new Output();
        EmployeeController employeeController = new EmployeeController(restTemplate,inputValue,new EmployeeService(inputValue));
        employeeController.findAllEmployee().forEach(System.out::println);
       // employeeController.saveNewEmployee();
       // employeeController.deleteEmployee();
        //employeeController.findById();
      // employeeController.findAllEmployeeByProductionDivision().forEach(System.out::println);
       // employeeController.findAllEmployeeByLastName().forEach(System.out::println);
        WorkShoesService workShoesService = new WorkShoesService(inputValue);
        WorkShoesController workShoesController = new WorkShoesController(restTemplate,workShoesService,inputValue, output);
        // workShoesController.saveNewWorkShoes();
        //workShoesController.findAllWorkShoes();
        //workShoesController.findAllWorkShoesNotSorted();
    //    workShoesController.deleteWorkShoes();
     //   workShoesController.findById();
       // workShoesController.findAllWorkShoesByWorkShoesSize();
        // workShoesController.findAllWorkShoesByWorkShoesType();
        WorkWearService workWearService = new WorkWearService(inputValue);
        WorkWearController workWearController = new WorkWearController(restTemplate,workWearService,inputValue,output);
       // workWearController.saveAllNewWorkWear();
        //workWearController.findAllWorkWearNotSorted();
       // workWearController.findAllWorkWearByWorkWearType();
    }
}
