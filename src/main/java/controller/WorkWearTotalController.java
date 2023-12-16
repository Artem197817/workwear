package controller;

import demo.workwear.model.WorkWearTotal;
import demo.workwear.model.modelEnum.WorkWearSize;
import demo.workwear.model.modelEnum.WorkWearType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.client.RestTemplate;
import service.WorkWearTotalService;
import view.input.InputValue;

import java.util.List;

@Data
@AllArgsConstructor
public class WorkWearTotalController {

    private final RestTemplate restTemplate;
    private final WorkWearTotalService workWearServiceTotal;
    private final InputValue inputValue;


    private final String urlWorkWearTotal = "http://localhost:8080/work_wear_total";

    public List<WorkWearTotal> findWorkWearByTypeSortedNumber() {
        String url = urlWorkWearTotal + "/work_wear_total_type/{workWearType}";
        WorkWearType workWearType = WorkWearType.getType(inputValue.input("Тип"));
        Object[] objects = restTemplate.getForEntity(url, Object[].class, workWearType).getBody();
        assert objects != null;
        return workWearServiceTotal.parserWorkWearTotal(objects);
    }

    public List<WorkWearTotal> findWorkWearBySizeSortedNumber() {
        String url = urlWorkWearTotal + "/work_wear_total_size/{workWearSize}";
        WorkWearSize workWearSize = WorkWearSize.getType(inputValue.input("Размер"));
        Object[] objects = restTemplate.getForEntity(url, Object[].class, workWearSize).getBody();
        assert objects != null;
        return workWearServiceTotal.parserWorkWearTotal(objects);
    }

    public List<WorkWearTotal> findAllWorkWearSortedNumber() {
        String url = urlWorkWearTotal + "/work_wear_total_all";
        Object[] objects = restTemplate.getForEntity(url, Object[].class).getBody();
        assert objects != null;
        return workWearServiceTotal.parserWorkWearTotal(objects);

    }
}
