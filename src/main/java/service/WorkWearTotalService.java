package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.workwear.model.WorkWearTotal;

import java.util.Arrays;
import java.util.List;


public class WorkWearTotalService {

    public List<WorkWearTotal> parserWorkWearTotal(Object[] objects) {
        ObjectMapper mapper = new ObjectMapper();
        return Arrays.stream(objects)
                .map(o -> mapper.convertValue(o, WorkWearTotal.class))
                .toList();
    }
}