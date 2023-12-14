package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.workwear.model.Employee;
import lombok.Data;
import view.input.InputValue;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class EmployeeService {

    private final InputValue inputValue;

        public List<Employee> parserJsonEmployee (Object[] objects){
            ObjectMapper mapper = new ObjectMapper();
            return Arrays.stream(objects)
                    .map(object ->mapper.convertValue(object, Employee.class))
                    .collect(Collectors.toList());
        }

        public Map<String,String> createMapNewEmployee(){
            Map<String,String> mapValue =new HashMap<>();
            mapValue.put("firstName",inputValue.input("Имя"));
            mapValue.put("lastName",inputValue.input("Фамилия"));
            mapValue.put("patronymic",inputValue.input("Отчество"));
            mapValue.put("productionDivision",inputValue.input("Участок"));
            mapValue.put("company",inputValue.input("Компания"));
            mapValue.put("specialization",inputValue.input("Профессия"));
            return  mapValue;
        }
    }

