package view.output;

import demo.workwear.model.WorkShoes;
import demo.workwear.model.modelEnum.WorkShoesType;

import java.util.List;
import java.util.Map;

public class Output {

    public void outputMapWorkShoes (Map<WorkShoesType , List<List<WorkShoes>>> workShoesTypeListMap){

        for (Map.Entry<WorkShoesType , List<List<WorkShoes>>> map: workShoesTypeListMap.entrySet()){
            System.out.println(map.getKey().getValue());
            List<List<WorkShoes>> list1 = map.getValue();
                for (List<WorkShoes> list: map.getValue()){
                    System.out.println(list.get(0).getWorkShoesSize() + " - " + list.size() + " шт");
                }
        }
    }
    public <E> void outputList (List<E> list){
        list.forEach(System.out::println);
    }
}
