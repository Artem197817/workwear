package controller;

import demo.workwear.model.WorkWear;
import demo.workwear.model.modelEnum.WorkWearSize;
import demo.workwear.model.modelEnum.WorkWearType;
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

    public Object[] findAllWorkWear() {
        Object[] objects = restTemplate.getForEntity(urlWorkWear, Object[].class).getBody();
        assert objects != null;
        output.outputMapWorkWear(workWearService.parserSortedWorkWear(objects));
        return objects;
    }

    public void saveAllNewWorkWear() {
        String url = urlWorkWear + "/save_all_wear";
        List<Map<String, String>> listMap = workWearService.createMapNewWorkWear();
        HttpEntity<List<Map<String, String>>> request = new HttpEntity<>(listMap);
        String response = restTemplate.postForObject(url, request, String.class);
        output.output(response);
    }

    public void deleteWorkWear() {
        String url = urlWorkWear + "/delete_work_wear/{id}";
        this.restTemplate.delete(url, inputValue.inputLong("id"));
    }

    public WorkWear findById() {
        String url = urlWorkWear + "/{id}";
        WorkWear workWear = restTemplate.getForObject(url, WorkWear.class, inputValue.inputLong("id"));
        System.out.println(workWear);
        return workWear;
    }

    public void findAllWorkWearNotSorted() {
        output.outputList(workWearService.parserWorkWear(findAllWorkWear()));
    }

    public List<WorkWear> findAllWorkWearByModelWorkWear() {
        String url = urlWorkWear + "/work_wear_model/{modelWorkWear}";
        Object[] objects = restTemplate.getForEntity(url, Object[].class, inputValue.input("Модель")).getBody();
        assert objects != null;
        List<WorkWear> workWearList = workWearService.parserWorkWear(objects);
        output.outputList(workWearService.sortedWorkWearNotIssue(workWearList));
        return workWearList;
    }

    public List<WorkWear> findAllWorkWearByWorkWearType() {
        String url = urlWorkWear + "/work_wear_type/{workWearType}";
        WorkWearType workWearType = WorkWearType.getType(inputValue.input("Тип"));
        Object[] objects = restTemplate.getForEntity(url, Object[].class, workWearType).getBody();
        assert objects != null;
        List<WorkWear> workWearList = workWearService.parserWorkWear(objects);
        output.outputList(workWearService.sortedWorkWearNotIssue(workWearList));
        return workWearList;
    }

    public List<WorkWear> findAllWorkWearByWorkWearSize() {
        String url = urlWorkWear + "/work_wear_size/{workWearSize}";
        WorkWearSize workWearSize = WorkWearSize.getType(inputValue.input("Рост"));
        Object[] objects = restTemplate.getForEntity(url, Object[].class, workWearSize).getBody();
        assert objects != null;
        List<WorkWear> workWearList = workWearService.parserWorkWear(objects);
        output.outputList(workWearService.sortedWorkWearNotIssue(workWearList));
        return workWearList;
    }
}
