package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.workwear.model.Employee;
import lombok.Data;
import view.input.InputValue;

import java.util.*;

@Data
public class EmployeeService {

    private final InputValue inputValue;

        public List<Employee> parserJsonEmployee (String jsons){
            ObjectMapper mapper = new ObjectMapper();
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

        public Map<String,String> createMapNewEmployee(){
            Map<String,String> mapValue =new HashMap<>();
            mapValue.put("firstName",inputValue.input("Имя"));
            mapValue.put("lastName",inputValue.input("Фамилия"));
            mapValue.put("patronymic",inputValue.input("Отчество"));
            mapValue.put("productionDivision",inputValue.input("Участок"));
            mapValue.put("company",inputValue.input("Компания"));
            mapValue.put("specialization",inputValue.input("Проффессия"));
            return  mapValue;
        }
    }

