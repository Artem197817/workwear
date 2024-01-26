package clientController;

import controller.EmployeeController;
import view.input.InputValue;

public record EmployeeClientController(InputValue inputValue, EmployeeController employeeController) {


    public void run() {

        boolean isJob = true;

        while (isJob) {

            int key = inputValue.inputInt("Введите команду");
            switch (key) {
                case 1 -> help();
                case 2 -> employeeController.saveNewEmployee();
                case 3 -> employeeController.findAllEmployee().forEach(System.out::println);
                case 4 -> employeeController.findAllEmployeeByProductionDivision().forEach(System.out::println);
                case 5 -> employeeController.findEmployeeByLastName().forEach(System.out::println);
                case 6 -> employeeController.deleteEmployee();
                case 0 -> isJob = false;
                default -> System.out.println("Неизвестная команда");
            }
        }
    }

    private void help() {

        System.out.println("1 - Помощь");
        System.out.println("2 - Добавить нового сотрудника");
        System.out.println("3 - Список всех сотрудников");
        System.out.println("4 - Список сотрудников производственного участка");
        System.out.println("5 - Поиск сотрудника по фамилии");
        System.out.println("6 - Удаление сотрудника");
        System.out.println("0 - Выход");
    }
}
