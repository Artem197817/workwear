package clientController;

import controller.WorkWearController;
import controller.WorkWearIssuedController;
import controller.WorkWearTotalController;

import lombok.AllArgsConstructor;
import lombok.Data;
import view.input.InputValue;


@Data
@AllArgsConstructor
public class WorkWearClientController {


    private final InputValue inputValue;
    private final WorkWearController workWearController;
    private final WorkWearTotalController workWearTotalController;
    private final WorkWearIssuedController workWearIssuedController;

    public void run(){

        boolean is = true;
        while(is){

            int key = inputValue.inputInt("Введите команду");
            switch (key) {
                case 1 -> help();
                case 2 -> workWearController.saveAllNewWorkWear();
                case 3 -> workWearIssuedController.findAllWorkWearIssued().forEach(System.out::println);
                case 4 -> workWearIssuedController.saveWorkWearIssued();
                case 5 -> workWearIssuedController.findWorkWearIssuedByEmployee().forEach(System.out::println);
                case 6 -> workWearIssuedController.deleteWorkWearIssued();
                case 7 -> workWearTotalController.findAllWorkWearSortedNumber().forEach(System.out::println);
                case 8 -> workWearTotalController.findWorkWearBySizeSortedNumber().forEach(System.out::println);
                case 9 -> workWearTotalController.findWorkWearByTypeSortedNumber().forEach(System.out::println);
                case 0 -> is = false;
            }
        }
    }

    private void help() {

        System.out.println("1 -Помощь");
        System.out.println("2 - Пополнить склад новой спецодеждой");
        System.out.println("3 - Список всей выданной спецодежды");
        System.out.println("4 - Выдать спецодежду");
        System.out.println("5 - Список выданной сотруднику спецодежды");
        System.out.println("6 - Списать выданную сотруднику спецодежду");
        System.out.println("7 - Список всей спецодежды");
        System.out.println("8 - Список спецодежды по размеру");
        System.out.println("9 - Список спецодежды по типу");
        System.out.println("0 - Выход");
    }
}
