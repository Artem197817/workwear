package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.workwear.model.Employee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmployeeService {

        public List<Employee> parserJsonEmployee (String jsons){
            ObjectMapper mapper = new ObjectMapper();
          //  mapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);

           // mapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING,true);
            String [] jsonArr = jsons.substring(1,jsons.length()-1).replace("},{","}---{").split("---");
            Arrays.stream(jsonArr).forEach(System.out::println);
            List<Employee> employees = new ArrayList<>();
            for (String str: jsonArr) {
                try {
                    employees.add(mapper.readValue(str,Employee.class));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
            return employees;
        }
    }

