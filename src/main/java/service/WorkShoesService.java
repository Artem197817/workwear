package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.workwear.model.WorkShoes;
import demo.workwear.model.modelEnum.WorkShoesType;
import lombok.Data;
import view.input.InputValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class WorkShoesService {

    private final InputValue inputValue;


    public static Map<WorkShoesType, List<WorkShoes>> parserSortedWorkShoes(String workShoes) {
        ObjectMapper mapper = new ObjectMapper();
        String [] jsonArr = workShoes.substring(1,workShoes.length()-1).replace("},{","}---{").split("---");
        List<WorkShoes> workShoesList = new ArrayList<>();



        return null;
    }
}
