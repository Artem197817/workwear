package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import controller.EmployeeController;
import controller.WorkShoesController;
import demo.workwear.model.*;

import lombok.AllArgsConstructor;
import view.input.InputValue;
import view.output.Output;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class WorkShoesIssuedService {

    private final EmployeeController employeeController;
    private final InputValue inputValue;
    private final Output output;
    private final WorkShoesController workShoesController;
    private final WorkShoesService workShoesService;


    public List<WorkShoesIssued> parserWorkShoesIssued(Object[] objects) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return Arrays.stream(objects)
                .map(x -> mapper.convertValue(x, WorkShoesIssued.class))
                .collect(Collectors.toList());
    }


    public WorkShoesIssued issuedWorkShoes() {
        Employee employee = employeeController.findEmployee();
        if (employee == null) {
            output.output("Ошибка при выборе сотрудникв");
            return null;
        }
        List<WorkShoes> workShoesList = workShoesController.findAllWorkShoesByWorkShoesType();
        if (workShoesList.isEmpty()) return null;
        int size = inputValue.inputInt("Введите требуемый размер обуви");
        List<WorkShoes> workShoesListSortedBySize = workShoesService.sortedWorkShoesNotIssue(workShoesList).stream()
                .filter(workShoes -> size == workShoes.getWorkShoesSize())
                .toList();
        output.outputList(workShoesListSortedBySize);
        Long idWorkShoes = inputValue.inputLong("Введите id выдаваемой обуви ");
        WorkShoes workShoes = workShoesListSortedBySize.stream()
                .filter(workShoes1 -> workShoes1.getId().equals(idWorkShoes))
                .findFirst().orElse(null);
        if (workShoes == null) {
            output.output("Ошибка при определении спецобуви");
            return null;
        }
        Integer monthPeriod = inputValue.inputInt("Введите срок выдачи спецобуви в месяцах");
        return new WorkShoesIssued(employee.getId(), idWorkShoes, monthPeriod);
    }

}


