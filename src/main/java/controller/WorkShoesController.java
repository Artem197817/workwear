package controller;

import demo.workwear.model.WorkShoes;
import demo.workwear.model.modelEnum.WorkShoesType;
import lombok.Data;
import org.springframework.web.client.RestTemplate;
import service.WorkShoesService;
import view.input.InputValue;

import java.util.List;
import java.util.Map;

@Data
public class WorkShoesController {

    private final RestTemplate restTemplate;
    private final WorkShoesService workShoesService;
    private final InputValue inputValue;

    private final String urlWorkShoes = "http://localhost:8080/work_shoes";


    public WorkShoesController(RestTemplate restTemplate, WorkShoesService workShoesService, InputValue inputValue) {
        this.restTemplate = restTemplate;
        this.workShoesService = workShoesService;
        this.inputValue = inputValue;
    }

    public Map<WorkShoesType, List<WorkShoes>> findAllWorkShoes() {
        Map<WorkShoesType, List<WorkShoes>> workShoesTypeListMap;
        String workShoes = restTemplate.getForObject(urlWorkShoes, String.class);
        assert workShoes != null;
               return WorkShoesService.parserSortedWorkShoes(workShoes);
    }
}
