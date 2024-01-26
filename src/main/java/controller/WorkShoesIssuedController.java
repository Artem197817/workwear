package controller;

import demo.workwear.model.WorkShoesIssued;
import lombok.Data;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import service.WorkShoesIssuedService;
import view.input.InputValue;
import view.output.Output;

import java.util.ArrayList;
import java.util.List;

@Data
public class WorkShoesIssuedController {

    private RestTemplate restTemplate;
    private WorkShoesIssuedService workShoesIssuedService;
    private final InputValue inputValue;
    private final Output output;
    private final EmployeeController employeeController;
    private final String urlWorkShoesIssued = "http://localhost:8080//work_shoes_issued";

    public WorkShoesIssuedController(RestTemplate restTemplate, WorkShoesIssuedService workShoesIssuedService
            , InputValue inputValue, Output output, EmployeeController employeeController) {
        this.restTemplate = restTemplate;
        this.workShoesIssuedService = workShoesIssuedService;
        this.inputValue = inputValue;
        this.output = output;
        this.employeeController = employeeController;
    }


    public List<WorkShoesIssued> findAllWorkShoesIssued() {
        String url = urlWorkShoesIssued + "/work_shoes_issued_all";
        try {
            Object[] objects = restTemplate.getForEntity(url, Object[].class).getBody();
        assert objects != null;
        return workShoesIssuedService.parserWorkShoesIssued(objects);
        }catch (Exception e){
            e.printStackTrace();
            output.output("Неверно заданы параметры");
        }
        return new ArrayList<>();
    }

    public void saveWorkShoesIssued() {
        String url = urlWorkShoesIssued + "/save_work_shoes_issued";
        WorkShoesIssued workShoesIssued = workShoesIssuedService.issuedWorkShoes();
        if (workShoesIssued == null) return;
        HttpEntity<WorkShoesIssued> request = new HttpEntity<>(workShoesIssued);
        try {
            System.out.println(request);
            String response = restTemplate.postForObject(url, request, String.class);
            output.output(response);
        } catch (Exception e) {
            e.printStackTrace();
            output.output("Неверно заданы параметры");
        }
    }

    public void deleteWorShoesIssued() {
        String url = urlWorkShoesIssued + "/delete_work_shoes_issued/{id}";
        this.restTemplate.delete(url, inputValue.inputLong("id удаляемой записи"));
    }

    public List<WorkShoesIssued> findWorkShoesIssuedByEmployee() {
        Long employeeId = employeeController.findEmployeeId();
        if (employeeId == -1L) return new ArrayList<>();
        String url = urlWorkShoesIssued + "/work_shoes_issued_by_id_employee/{id}";
        Object[] objects = restTemplate.getForEntity(url, Object[].class, employeeId).getBody();
        assert objects != null;
        return workShoesIssuedService.parserWorkShoesIssued(objects);
    }

}