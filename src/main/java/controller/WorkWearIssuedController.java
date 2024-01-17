package controller;

import demo.workwear.model.WorkWearIssued;
import lombok.Data;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import service.WorkWearIssueService;
import view.input.InputValue;
import view.output.Output;

import java.util.ArrayList;
import java.util.List;


@Data
public class WorkWearIssuedController {

    private RestTemplate restTemplate;
    private WorkWearIssueService workWearIssuedService;
    private final InputValue inputValue;
    private final Output output;
    private final EmployeeController employeeController;
    private final String urlWorkWearIssued = "http://localhost:8080/work_wear_issued";


    public WorkWearIssuedController(RestTemplate restTemplate, WorkWearIssueService workWearIssuedService,
                                    InputValue inputValue, Output output,EmployeeController employeeController) {
        this.restTemplate = restTemplate;
        this.workWearIssuedService = workWearIssuedService;
        this.inputValue = inputValue;
        this.output = output;
        this.employeeController = employeeController;

    }

    public void saveWorkWearIssued() {

        String url = urlWorkWearIssued + "/save_work_wear_issued";
        WorkWearIssued workWearIssued = workWearIssuedService.issuedWorkWear();
        if (workWearIssued == null) return;
        HttpEntity<WorkWearIssued> request = new HttpEntity<>(workWearIssued);
        try {
            String response = restTemplate.postForObject(url, request, String.class);
            output.output(response);
        } catch (Exception e) {
            e.printStackTrace();
            output.output("Неверно заданы параметры");
        }
    }

    public List<WorkWearIssued> findAllWorkWearIssued() {
        String url = urlWorkWearIssued + "/work_wear_issued_all";
        Object[] objects = restTemplate.getForEntity(url, Object[].class).getBody();
        assert objects != null;
        return workWearIssuedService.parserWorkWearIssued(objects);
    }

    public void deleteWorkWearIssued() {
        String url = urlWorkWearIssued + "/delete_work_wear_issued/{id}";
        this.restTemplate.delete(url, inputValue.inputLong("id удаляемой записи"));
    }

    public List<WorkWearIssued> findWorkWearIssuedByEmployee() {
        Long employeeId = employeeController.findEmployeeId();
        if (employeeId == -1L) return new ArrayList<>();
        String url = urlWorkWearIssued + "/work_wear_issued_by_employee_id/{id}";
        Object[] objects = restTemplate.getForEntity(url, Object[].class, employeeId).getBody();
        assert objects != null;
        return workWearIssuedService.parserWorkWearIssued(objects);
    }

}

