package controller;

import demo.workwear.model.WorkShoesTotal;
import demo.workwear.model.modelEnum.WorkShoesType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.client.RestTemplate;
import service.WorkShoesTotalService;
import view.input.InputValue;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class WorkShoesTotalController {

    private final RestTemplate restTemplate;
    private final WorkShoesTotalService workShoesTotalService;
    private final InputValue inputValue;

    private final String urlWorkShoesTotal = "http://localhost:8080/work_shoes_total";


   public List<WorkShoesTotal> findWorkShoesByTypeSortedNumber (){
       String url = urlWorkShoesTotal+"/work_shoes_total_type/{workShoesType}";
       WorkShoesType workShoesType = WorkShoesType.getType(inputValue.inputEnum("Тип",WorkShoesType.class));
       Object[] objects = restTemplate.getForEntity(url,Object[].class,workShoesType).getBody();
       assert objects != null;
        return  workShoesTotalService.parsedWorkShoesTotal(objects);
   }

    public List<WorkShoesTotal> findWorkShoesBySizeSortedNumber (){
       String url = urlWorkShoesTotal+"/work_shoes_total_size/{size}";
       try {
           Object[] objects = restTemplate.getForEntity(url, Object[].class, inputValue.inputInt("Размер")).getBody();
           assert objects != null;
           return workShoesTotalService.parsedWorkShoesTotal(objects);
       }catch (Exception e){
           e.printStackTrace();
           System.out.println("Неверно заданы параметры");
       }
       return new ArrayList<>();
    }

     public List<WorkShoesTotal> findAllWorkShoesSortedNumber(){
       String url =  urlWorkShoesTotal+"/work_shoes_total_all";
         Object[] objects = restTemplate.getForEntity(url,Object[].class).getBody();
         assert objects != null;
         return  workShoesTotalService.parsedWorkShoesTotal(objects);
     }
}
