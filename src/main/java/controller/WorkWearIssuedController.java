package controller;

import demo.workwear.model.WorkWearIssued;
import lombok.Data;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import service.WorkWearIssueService;
import view.input.InputValue;
import view.output.Output;

import java.util.List;


@Data
public class WorkWearIssuedController {

    private RestTemplate restTemplate;
    private WorkWearIssueService workWearIssuedService;
    private final InputValue inputValue;
    private  final Output output;
    private final String urlWorkWearIssued = "http://localhost:8080/work_wear_issued";

    public WorkWearIssuedController(RestTemplate restTemplate, WorkWearIssueService workWearIssuedService, InputValue inputValue, Output output) {
        this.restTemplate = restTemplate;
        this.workWearIssuedService = workWearIssuedService;
        this.inputValue = inputValue;
        this.output = output;
    }

    public void saveWorkWearIssued () {

        String url = urlWorkWearIssued + "/save_work_wear_issued";
        WorkWearIssued workWearIssued = workWearIssuedService.IssuedWorkWear();
        if (workWearIssued == null) return;
        HttpEntity <WorkWearIssued> request = new HttpEntity<>(workWearIssued);
        System.out.println(request);
        try {
            String response = restTemplate.postForObject(url, request, String.class);
            output.output(response);
        } catch (Exception e) {
            e.printStackTrace();
            output.output("Неверно заданы параметры");
        }
    }

    public List<WorkWearIssued> findAllWorkWearIssued(){
        String url =urlWorkWearIssued+ "/work_wear_issued_all";
        Object[] objects = restTemplate.getForEntity(url, Object[].class).getBody();
        assert objects != null;
        return workWearIssuedService.parserWorkWearIssued (objects);
    }

}
