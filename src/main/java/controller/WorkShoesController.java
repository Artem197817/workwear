package controller;

import demo.workwear.model.Employee;
import demo.workwear.model.WorkShoes;
import demo.workwear.model.modelEnum.WorkShoesType;
import lombok.Data;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import service.WorkShoesService;
import view.input.InputValue;
import view.output.Output;

import java.util.List;
import java.util.Map;

@Data
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

    public String findAllWorkShoes() {
        String workShoes = restTemplate.getForObject(urlWorkShoes, String.class);
        assert workShoes != null;
        output.outputMapWorkShoes(workShoesService.parserSortedWorkShoes(workShoes));
        return workShoes;
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
    public void findAllWorkShoesNotSorted (){
       output.outputList( workShoesService.parserWorkShoes(findAllWorkShoes()));
    }
   public List<WorkShoes> findAllWorkShoesByWorkShoesSize (){
        String url = urlWorkShoes + "/work_shoes_size/{workShoesSize}";
       String workShoes = restTemplate.getForObject(url, String.class,inputValue.inputInt("Размер"));
       assert workShoes != null;
       List<WorkShoes> workShoesList = workShoesService.parserWorkShoes(workShoes);
       output.outputList(workShoesService.sortedWorkShoesNotIssue(workShoesList));
       return workShoesList;
    }

    public List<WorkShoes> findAllWorkShoesByWorkShoesType (){
        String url = urlWorkShoes + "/work_shoes_type/{workShoesType}";
        WorkShoesType workShoesType = WorkShoesType.getType(inputValue.input("Тип обуви"));
        String workShoes = restTemplate.getForObject(url, String.class,workShoesType);
        assert workShoes != null;
        List<WorkShoes> workShoesList = workShoesService.parserWorkShoes(workShoes);
        output.outputList(workShoesService.sortedWorkShoesNotIssue(workShoesList));
        return workShoesList;
    }
}

