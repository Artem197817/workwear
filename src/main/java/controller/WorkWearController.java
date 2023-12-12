package controller;

import demo.workwear.model.WorkWear;
import lombok.Data;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import service.WorkWearService;
import view.input.InputValue;
import view.output.Output;

import java.util.List;
import java.util.Map;

@Data
public class WorkWearController {

    private final RestTemplate restTemplate;
    private final WorkWearService workWearService;
    private final InputValue inputValue;
    private final Output output;

    private final String urlWorkWear = "http://localhost:8080/work_wear";

    public String findAllWorkWear() {
        String workWear = restTemplate.getForObject(urlWorkWear, String.class);
        assert workWear != null;
        output.outputMapWorkWear(workWearService.parserSortedWorkWear(workWear));
        return workWear;
    }
    public void saveAllNewWorkWear(){
        String url = urlWorkWear+"/save_all_wear";
        List<Map<String, String>> listMap = workWearService.createMapNewWorkWear();
        HttpEntity <List<Map<String, String>>> request = new HttpEntity<>(listMap);
        String response = restTemplate.postForObject(url, request, String.class);
        output.output(response);
    }
    public void deleteWorkWear(){
        String url = urlWorkWear+"/delete_work_wear/{id}";
        this.restTemplate.delete(url, inputValue.inputLong("id"));
    }
    public WorkWear findById(){
        String url = urlWorkWear+"/{id}";
        WorkWear workWear = restTemplate.getForObject(url, WorkWear.class,inputValue.inputLong("id"));
        System.out.println(workWear);
        return workWear;
    }
    public void findAllWorkWearNotSorted (){
        output.outputList(workWearService.parserWorkWear(findAllWorkWear()));
    }
    public List<WorkWear> findAllWorkWearByModelWorkWear (){
        String url = urlWorkWear+"/work_wear_model/{modelWorkWear}";
        String workWear = restTemplate.getForObject(url, String.class,inputValue.input("Модель"));
        assert workWear != null;
        List<WorkWear> workWearList = workWearService.parserWorkWear(workWear);
        output.outputList(workWearService.sortedWorkWearNotIssue(workWearList));
        return workWearList;
    }
    
}
/**

 *
 *     public List<WorkShoes> findAllWorkShoesByWorkShoesType (){
 *         String url = urlWorkShoes + "/work_shoes_type/{workShoesType}";
 *         WorkShoesType workShoesType = WorkShoesType.getType(inputValue.input("Тип обуви"));
 *         String workShoes = restTemplate.getForObject(url, String.class,workShoesType);
 *         assert workShoes != null;
 *         List<WorkShoes> workShoesList = workShoesService.parserWorkShoes(workShoes);
 *         output.outputList(workShoesService.sortedWorkShoesNotIssue(workShoesList));
 *         return workShoesList;
 *     }
 */