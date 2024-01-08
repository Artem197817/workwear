package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import controller.EmployeeController;
import controller.WorkWearController;
import demo.workwear.model.Employee;
import demo.workwear.model.WorkWear;
import demo.workwear.model.WorkWearIssued;
import demo.workwear.model.modelEnum.WorkWearSize;
import lombok.AllArgsConstructor;
import view.input.InputValue;
import view.output.Output;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class WorkWearIssueService {

    private final EmployeeController employeeController;
    private final InputValue inputValue;
    private final Output output;
    private final WorkWearController workWearController;
    private final WorkWearService workWearService;

    public WorkWearIssued IssuedWorkWear() {
        Employee employee = findEmployee();
        if (employee == null) {
            output.output("Ошибка при выборе сотрудникв");
            return null;
        }
        List<WorkWear> workWearList = workWearController.findAllWorkWearByWorkWearType();
        if (workWearList.isEmpty()) return null;
        String size = inputValue.input("Введите требуемый размер одежды");
        WorkWearSize workWearSize = WorkWearSize.getType(size);
        List<WorkWear> workWearListSortedBySize = workWearService.sortedWorkWearNotIssue(workWearList).stream()
                .filter(workWear -> workWear.getWorkWearSize().equals(workWearSize))
                .toList();
        output.outputList(workWearListSortedBySize);
        Long idWorkWear = inputValue.inputLong("Введите id выдаваемой одежды ");
        WorkWear workWear = workWearListSortedBySize.stream()
                .filter(workWear1 -> workWear1.getId().equals(idWorkWear))
                .findFirst().orElse(null);
        if (workWear == null) {
            output.output("Ошибка при определении спецодежды");
            return null;
        }
        Integer wearPeriod = inputValue.inputInt("Введите срок выдачи спецодежды в месяцах");
        return new WorkWearIssued(employee.getId(), idWorkWear,wearPeriod);
    }

    private Employee findEmployee() {

        List<Employee> employeeList = employeeController.findEmployeeByLastName();
        output.outputList(employeeList);
        Long idEmployee = inputValue.inputLong("Введите id сотрудника для выдачи спецодежды ");
        return employeeList.stream().filter(employee -> employee.getId().equals(idEmployee)).findFirst().orElse(null);
    }

    public List<WorkWearIssued> parserWorkWearIssued (Object[] objects) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return Arrays.stream(objects)
                .map(x -> mapper.convertValue(x, WorkWearIssued.class))
                .collect(Collectors.toList());
    }

    public Long findEmployeeId () {
        List<Employee> employees = employeeController.findEmployeeByLastName();
        if (employees.isEmpty()) {
            output.output("Нет сотрудника с такой фамилией");
            return -1L;
        }
        output.outputList(employees);
        output.output("Введите id сотрудника");
       return employeeController.findById().getId();

    }
}
