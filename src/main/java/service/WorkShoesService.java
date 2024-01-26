package service;


import com.fasterxml.jackson.databind.ObjectMapper;
import demo.workwear.model.WorkShoes;
import demo.workwear.model.modelEnum.WorkShoesType;
import lombok.AllArgsConstructor;
import view.input.InputValue;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
public class WorkShoesService {

    private final InputValue inputValue;


    public List<WorkShoes> sortedWorkShoesNotIssue(List<WorkShoes> workShoesList){
       return workShoesList.stream()
                .filter(x -> x.getWorkShoesStatus() == WorkShoes.NOT_ISSUE)
                .sorted(Comparator.comparing(WorkShoes::getWorkShoesType))
                .toList();
    }

    public List<Map<String, String>> createMapNewWorkShoes() {
        Map<String, String> mapValue = new HashMap<>();
        mapValue.put("modelWorkShoes", inputValue.input("Модель"));
        mapValue.put("workShoesSize", inputValue.input("Размер"));
        mapValue.put("workShoesType", inputValue.inputEnum("Тип",WorkShoesType.class));
        int number = inputValue.inputInt("Количество");
        List<Map<String, String>> mapList = new ArrayList<>();
        for (int i = 0; i < number; i++) mapList.add(mapValue);

        return mapList;
    }


    public List<WorkShoes> parserWorkShoes(Object[] objects) {
        ObjectMapper mapper = new ObjectMapper();
        return Arrays.stream(objects)
                .map(object ->mapper.convertValue(object, WorkShoes.class))
                .collect(Collectors.toList());
    }
}
