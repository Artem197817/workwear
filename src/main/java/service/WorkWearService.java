package service;


import com.fasterxml.jackson.databind.ObjectMapper;
import demo.workwear.model.WorkWear;

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

    public List<WorkWear> parserWorkWear(Object[] object) {
        ObjectMapper mapper = new ObjectMapper();
        return Arrays.stream(object)
                .map(x->mapper.convertValue(object,WorkWear.class))
                .collect(Collectors.toList());

    }

    public List<Map<String, String>> createMapNewWorkWear() {
        Map<String, String> mapValue = new HashMap<>();
         mapValue.put("modelWorkWear", inputValue.input("Модель"));
        mapValue.put("workWearType", inputValue.input("Тип"));
        mapValue.put("workWearSize", inputValue.input("Размер"));
        mapValue.put("workWearHeight", inputValue.input("Рост"));
        int number = inputValue.inputInt("Количество");
        List<Map<String, String>> mapList = new ArrayList<>();
        for (int i = 0; i < number; i++) mapList.add(mapValue);

        return mapList;
    }
}

    
    

