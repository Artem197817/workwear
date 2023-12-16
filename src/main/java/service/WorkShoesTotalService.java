package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.workwear.model.WorkShoesTotal;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WorkShoesTotalService {

    public List<WorkShoesTotal> parsedWorkShoesTotal (Object[] objects){
        ObjectMapper mapper = new ObjectMapper();
        return Arrays.stream(objects)
                .map(o -> mapper.convertValue(o,WorkShoesTotal.class))
                .collect(Collectors.toList());
    }
}
