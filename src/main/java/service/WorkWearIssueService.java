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

    public WorkWearIssued issuedWorkWear() {
        Employee employee = employeeController.findEmployee();
        if (employee == null) {
            output.output("Ошибка при выборе сотрудникв");
            return null;
        }
        List<WorkWear> workWearList = workWearController.findAllWorkWearByWorkWearType();
        if (workWearList.isEmpty()) return null;
        String size = inputValue.inputEnum("Введите требуемый размер одежды",WorkWearSize.class);
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



    public List<WorkWearIssued> parserWorkWearIssued (Object[] objects) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return Arrays.stream(objects)
                .map(x -> mapper.convertValue(x, WorkWearIssued.class))
                .collect(Collectors.toList());
    }

}
