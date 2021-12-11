
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/*
This class holds all the tuples that induced by a specific anonymization
 */
public class EquivalenceClass {
    private List<Pair<Patient, Tuple>> tuple_list;

    public EquivalenceClass() {
        tuple_list = new ArrayList<>();
    }

    public void addTuple(Pair<Patient, Tuple> tuple) {
        this.tuple_list.add(tuple);
    }

    public Pair<EquivalenceClass,EquivalenceClass> induceEC(int value, int attribute_index, Object[] mapValueToNumber){
        EquivalenceClass ec1 =new EquivalenceClass();
        EquivalenceClass ec2 =new EquivalenceClass();
        for(Pair<Patient,Tuple> tuple:tuple_list){
            Tuple tupleCopy = new Tuple(tuple.getValue());
            Pair pairCopy = new Pair<>(tuple.getKey(),tupleCopy);
            switch (attribute_index){
                case 1:
                    boolean isEqual = tuple.getKey().getGender().equals((String)mapValueToNumber[value]);
                    checkConditionAndUpdate(isEqual,tupleCopy,ec1,ec2,attribute_index,value,pairCopy);
                case 2:
                    boolean isInRange = tuple.getKey().getAge() >= ((Pair<Integer,Integer>)mapValueToNumber[value]).getKey()
                                         && tuple.getKey().getAge() <= ((Pair<Integer,Integer>)mapValueToNumber[value]).getValue();
                    checkConditionAndUpdate(isInRange,tupleCopy,ec1,ec2,attribute_index,value,pairCopy);
            }

        }


        return null;
    }
    public void checkConditionAndUpdate(boolean condition, Tuple tuple, EquivalenceClass ec1, EquivalenceClass ec2,int attribute_index, int value, Pair pair){
        if(condition){
            tuple.setAttribute(attribute_index,value);
            ec1.addTuple(pair);
        }
        else{
            ec2.addTuple(pair);
        }
    }

    public int equivalenceClassSize(){
        return this.tuple_list.size();
    }
}
