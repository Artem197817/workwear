package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.workwear.model.WorkShoes;
import demo.workwear.model.modelEnum.WorkShoesType;
import lombok.Data;
import view.input.InputValue;

import java.util.*;

@Data
public class WorkShoesService {

    private final InputValue inputValue;


    public Map<WorkShoesType, List<List<WorkShoes>>> parserSortedWorkShoes(String workShoes) {
        List<WorkShoes> workShoesList = parserWorkShoes(workShoes);
        List<WorkShoes> workShoesListSortedNotIssue = sortedWorkShoesNotIssue(workShoesList);
        Map<WorkShoesType, List<List<WorkShoes>>> workShoesTypeListMap = new HashMap<>();
        WorkShoesType workShoesType = workShoesListSortedNotIssue.get(0).getWorkShoesType();
        List<WorkShoes> workShoesTemp = new ArrayList<>();
        for (WorkShoes shoes : workShoesListSortedNotIssue) {
            if (shoes.getWorkShoesType().equals(workShoesType)) {
                workShoesTemp.add(shoes);
            } else {
                workShoesTypeListMap.put(workShoesType, sortedWorkShoesSize(workShoesTemp));
                workShoesTemp = new ArrayList<>();
                workShoesTemp.add(shoes);
                workShoesType = shoes.getWorkShoesType();
            }
            workShoesTypeListMap.put(workShoesType, sortedWorkShoesSize(workShoesTemp));

        }
        return workShoesTypeListMap;
    }
    public List<WorkShoes> sortedWorkShoesNotIssue(List<WorkShoes> workShoesList){
       return workShoesList.stream()
                .filter(x -> x.getWorkShoesStatus() == WorkShoes.NOT_ISSUE)
                .sorted(Comparator.comparing(WorkShoes::getWorkShoesType))
                .toList();
    }

    private List<List<WorkShoes>> sortedWorkShoesSize(List<WorkShoes> workShoesList) {
        List<WorkShoes> sortedSizeList = workShoesList.stream()
                .sorted(Comparator.comparing(WorkShoes::getWorkShoesSize))
                .toList();
        List<List<WorkShoes>> resultList = new ArrayList<>();
        List<WorkShoes> temp = new ArrayList<>();
        int workShoesSize = sortedSizeList.get(0).getWorkShoesSize();
        for (WorkShoes shoes : sortedSizeList) {
            if (shoes.getWorkShoesSize() == workShoesSize) {
                temp.add(shoes);
            } else {
                resultList.add(temp);
                temp = new ArrayList<>();
                temp.add(shoes);
                workShoesSize = shoes.getWorkShoesSize();
            }
        }
        resultList.add(temp);
        return resultList;
    }

    public List<Map<String, String>> createMapNewWorkShoes() {
        Map<String, String> mapValue = new HashMap<>();
        mapValue.put("modelWorkShoes", inputValue.input("Модель"));
        mapValue.put("workShoesSize", inputValue.input("Размер"));
        mapValue.put("workShoesType", inputValue.input("Тип"));
        int number = inputValue.inputInt("Количество");
        List<Map<String, String>> mapList = new ArrayList<>();
        for (int i = 0; i < number; i++) mapList.add(mapValue);

        return mapList;
    }

    public Map<String, String> createMapNewWorkShoesTemp() {
        Map<String, String> mapValue = new HashMap<>();
        mapValue.put("modelWorkShoes", inputValue.input("Модель"));
        mapValue.put("workShoesSize", inputValue.input("Размер"));
        mapValue.put("workShoesType", inputValue.input("Тип"));

        return mapValue;
    }

    public List<WorkShoes> parserWorkShoes(String workShoes) {
        ObjectMapper mapper = new ObjectMapper();
        String[] jsonArr = workShoes.substring(1, workShoes.length() - 1).replace("},{", "}---{").split("---");
        List<WorkShoes> workShoesList = new ArrayList<>();
        for (String str : jsonArr) {
            try {
                workShoesList.add(mapper.readValue(str, WorkShoes.class));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return workShoesList;
    }
}
