import controller.*;
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
        //  workShoesController.findAllWorkShoesByWorkShoesSize().forEach(System.out::println);
       // workShoesController.findAllWorkShoesByWorkShoesType().forEach(System.out::println);
        WorkWearService workWearService = new WorkWearService(inputValue);
        WorkWearController workWearController = new WorkWearController(restTemplate,workWearService,inputValue,output);
       // workWearController.saveAllNewWorkWear();
        //workWearController.findAllWorkWearNotSorted();
       // workWearController.findAllWorkWearByWorkWearType();
        WorkWearTotalService workWearTotalService = new WorkWearTotalService();
        WorkWearTotalController workWearTotalController = new WorkWearTotalController(restTemplate,workWearTotalService,inputValue);
       // workWearTotalController.findAllWorkWearSortedNumber().forEach(System.out::println);
        //workWearTotalController.findWorkWearByTypeSortedNumber().forEach(System.out::println);
       // workWearTotalController.findWorkWearBySizeSortedNumber().forEach(System.out::println);
        WorkShoesTotalController workShoesTotalController = new WorkShoesTotalController(restTemplate,new WorkShoesTotalService(),inputValue);
        //workShoesTotalController.findWorkShoesByTypeSortedNumber().forEach(System.out::println);
        workShoesTotalController.findAllWorkShoesSortedNumber().forEach(System.out::println);
        workShoesTotalController.findWorkShoesBySizeSortedNumber().forEach(System.out::println);
    }
}
