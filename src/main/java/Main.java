import clientController.ClientController;
import clientController.EmployeeClientController;
import clientController.WorkShoesClientController;
import clientController.WorkWearClientController;
import controller.*;
import demo.workwear.model.WorkShoesIssued;
import demo.workwear.model.WorkShoesTotal;
import org.springframework.web.client.RestTemplate;
import service.*;
import view.input.InputValue;
import view.output.Output;

public class Main {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        InputValue inputValue = new InputValue();
        Output output = new Output();
        EmployeeService employeeService = new EmployeeService(inputValue);
        EmployeeController employeeController = new EmployeeController(restTemplate, inputValue, employeeService, output);
        WorkShoesService workShoesService = new WorkShoesService(inputValue);
        WorkShoesController workShoesController = new WorkShoesController(restTemplate, workShoesService, inputValue, output);
        WorkWearService workWearService = new WorkWearService(inputValue);
        WorkWearController workWearController = new WorkWearController(restTemplate, workWearService, inputValue, output);
        WorkWearTotalService workWearTotalService = new WorkWearTotalService();
        WorkWearTotalController workWearTotalController = new WorkWearTotalController(restTemplate, workWearTotalService, inputValue);
        WorkShoesTotalController workShoesTotalController = new WorkShoesTotalController(restTemplate, new WorkShoesTotalService(), inputValue);
        WorkWearIssueService workWearIssueService = new WorkWearIssueService(employeeController, inputValue, output, workWearController, workWearService);
        WorkWearIssuedController workWearIssuedController = new WorkWearIssuedController(restTemplate, workWearIssueService,
                inputValue, output, employeeController);
        WorkShoesIssuedService workShoesIssuedService = new WorkShoesIssuedService(employeeController, inputValue, output
                , workShoesController, workShoesService);
        WorkShoesIssuedController workShoesIssuedController = new WorkShoesIssuedController(restTemplate, workShoesIssuedService
                , inputValue, output, employeeController);
        EmployeeClientController employeeClientController = new EmployeeClientController(inputValue, employeeController);
        WorkShoesClientController workShoesClientController = new WorkShoesClientController(inputValue, workShoesIssuedController
                , workShoesController, workShoesTotalController);
        WorkWearClientController workWearClientController = new WorkWearClientController(inputValue, workWearController
                , workWearTotalController, workWearIssuedController);
        ClientController clientController = new ClientController(inputValue, employeeClientController, workShoesClientController
                , workWearClientController);
        clientController.run();

    }
}
