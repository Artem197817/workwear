package service;


import com.fasterxml.jackson.databind.ObjectMapper;
import demo.workwear.model.WorkWear;

import demo.workwear.model.modelEnum.WorkWearHeight;
import demo.workwear.model.modelEnum.WorkWearSize;
import demo.workwear.model.modelEnum.WorkWearType;
import lombok.AllArgsConstructor;
import view.input.InputValue;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
public class WorkWearService {

    private final InputValue inputValue;


    public List<WorkWear> sortedWorkWearNotIssue(List<WorkWear> workWearList) {
        return workWearList.stream()
                .filter(x -> x.getWorkWearStatus() == WorkWear.NOT_ISSUE)
                .sorted(Comparator.comparing(WorkWear::getWorkWearType))
                .toList();
    }

    public List<WorkWear> parserWorkWear(Object[] objects) {
        ObjectMapper mapper = new ObjectMapper();
        return Arrays.stream(objects)
                .map(x -> mapper.convertValue(x, WorkWear.class))
                .collect(Collectors.toList());

    }

    public List<Map<String, String>> createMapNewWorkWear() {
        Map<String, String> mapValue = new HashMap<>();
        mapValue.put("modelWorkWear", inputValue.input("Модель"));
        mapValue.put("workWearType", inputValue.inputEnum("Тип", WorkWearType.class));
        mapValue.put("workWearSize", inputValue.inputEnum("Размер", WorkWearSize.class));
        mapValue.put("workWearHeight", inputValue.inputEnum("Рост", WorkWearHeight.class));
        int number = inputValue.inputInt("Количество");
        List<Map<String, String>> mapList = new ArrayList<>();
        for (int i = 0; i < number; i++) mapList.add(mapValue);

        return mapList;
    }
}

    
    

