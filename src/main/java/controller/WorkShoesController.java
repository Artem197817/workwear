package controller;


import demo.workwear.model.WorkShoes;
import demo.workwear.model.modelEnum.WorkShoesType;
import lombok.Data;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import service.WorkShoesService;
import view.input.InputValue;
import view.output.Output;

import java.util.List;
import java.util.Map;

@Data
@RestController
public class WorkShoesController {

    private final RestTemplate restTemplate;
    private final WorkShoesService workShoesService;
    private final InputValue inputValue;
    private final Output output;

    private final String urlWorkShoes = "http://localhost:8080/work_shoes";


    public WorkShoesController(RestTemplate restTemplate, WorkShoesService workShoesService, InputValue inputValue, Output output) {
        this.restTemplate = restTemplate;
        this.workShoesService = workShoesService;
        this.inputValue = inputValue;
        this.output = output;
    }

    public List<WorkShoes> findAllWorkShoes() {
        ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(urlWorkShoes, Object[].class);
        Object[] objects = responseEntity.getBody();
        assert objects != null;
        return workShoesService.parserWorkShoes(objects);
    }

    public void saveNewWorkShoes() {
        String urlSave = urlWorkShoes + "/save_shoes";
        List<Map<String, String>> listMap = workShoesService.createMapNewWorkShoes();
        for (Map<String, String> map : listMap) {
            HttpEntity<Map<String, String>> request = new HttpEntity<>(map);
            String response = restTemplate.postForObject(urlSave, request, String.class);
            System.out.println(response);
        }
    }

    public void deleteWorkShoes() {
        String url = urlWorkShoes + "/delete_work_shoes/{id}";
        this.restTemplate.delete(url, inputValue.inputLong("id"));
    }

    public WorkShoes findById() {
        String url = urlWorkShoes + "/{id}";
        WorkShoes workShoes = restTemplate.getForObject(url, WorkShoes.class, inputValue.inputLong("id"));
        System.out.println(workShoes);
        return workShoes;
    }

    public List<WorkShoes> findAllWorkShoesByWorkShoesSize() {
        String url = urlWorkShoes + "/work_shoes_size/{workShoesSize}";
        Object[] objects = restTemplate.getForEntity(url, Object[].class, inputValue.inputInt("Размер")).getBody();
        assert objects != null;
        return workShoesService.parserWorkShoes(objects);
    }

    public List<WorkShoes> findAllWorkShoesByWorkShoesType() {
        String url = urlWorkShoes + "/work_shoes_type/{workShoesType}";
        WorkShoesType workShoesType = WorkShoesType.getType(inputValue.input("Тип обуви"));
        ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(url, Object[].class, workShoesType);
        Object[] objects = responseEntity.getBody();
        assert objects != null;
        return workShoesService.parserWorkShoes(objects);
    }
}

