package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.workwear.model.WorkWear;
import demo.workwear.model.modelEnum.WorkWearSize;
import demo.workwear.model.modelEnum.WorkWearType;
import lombok.AllArgsConstructor;
import view.input.InputValue;

import java.util.*;

@AllArgsConstructor
public class WorkWearService {

   private final InputValue inputValue;
    public Map<WorkWearType, List<List<WorkWear>>> parserSortedWorkWear(String workWear) {
        List<WorkWear> workWearList = parserWorkWear(workWear);
        List<WorkWear> workWearListSortedNotIssue = sortedWorkWearNotIssue(workWearList);
        Map<WorkWearType, List<List<WorkWear>>> workWearTypeListMap = new HashMap<>();
        WorkWearType workWearType = workWearListSortedNotIssue.get(0).getWorkWearType();
        List<WorkWear> workWearTemp = new ArrayList<>();
        for (WorkWear wear : workWearListSortedNotIssue) {
            if (wear.getWorkWearType().equals(workWearType)) {
                workWearTemp.add(wear);
            } else {
                workWearTypeListMap.put(workWearType, sortedWorkWearSize(workWearTemp));
                workWearTemp = new ArrayList<>();
                workWearTemp.add(wear);
                workWearType = wear.getWorkWearType();
            }
            workWearTypeListMap.put(workWearType, sortedWorkWearSize(workWearTemp));

        }
        return workWearTypeListMap;
    }

    private List<List<WorkWear>> sortedWorkWearSize(List<WorkWear> workWearList) {
        List<WorkWear> sortedSizeList = workWearList.stream()
                .sorted(Comparator.comparing(WorkWear::getWorkWearSize))
                .toList();
        List<List<WorkWear>> resultList = new ArrayList<>();
        List<WorkWear> temp = new ArrayList<>();
        WorkWearSize workWearSize = sortedSizeList.get(0).getWorkWearSize();
        for (WorkWear wear : sortedSizeList) {
            if (wear.getWorkWearSize().equals(workWearSize)) {
                temp.add(wear);
            } else {
                resultList.add(temp);
                temp = new ArrayList<>();
                temp.add(wear);
                workWearSize = wear.getWorkWearSize();
            }
        }
        resultList.add(temp);
        return resultList;
    }

    public List<WorkWear> sortedWorkWearNotIssue(List<WorkWear> workWearList) {
        return workWearList.stream()
                .filter(x -> x.getWorkWearStatus() == WorkWear.NOT_ISSUE)
                .sorted(Comparator.comparing(WorkWear::getWorkWearType))
                .toList();
    }

    public List<WorkWear> parserWorkWear(String workWear) {
        ObjectMapper mapper = new ObjectMapper();
        String[] jsonArr = workWear.substring(1, workWear.length() - 1).replace("},{", "}---{").split("---");
        List<WorkWear> workWearList = new ArrayList<>();
        for (String str : jsonArr) {
            try {
                workWearList.add(mapper.readValue(str, WorkWear.class));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return workWearList;
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

    
    

