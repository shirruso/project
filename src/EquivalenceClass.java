
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

    public Pair<EquivalenceClass, EquivalenceClass> induceEC(int value, int attribute_index, Object[] mapValueToNumber) {
        EquivalenceClass ec1 = new EquivalenceClass();
        EquivalenceClass ec2 = new EquivalenceClass();
        for (Pair<Patient, Tuple> tuple : tuple_list) {
            Tuple tupleCopy = new Tuple(tuple.getValue());
            Pair pairCopy = new Pair<>(tuple.getKey(), tupleCopy);
            boolean condition = false;
            switch (attribute_index) {
                case 1://gender
                    condition = tuple.getKey().getGender().equals((String) mapValueToNumber[value]);
                case 2://age
                    condition = tuple.getKey().getAge() >= ((Pair<Integer, Integer>) mapValueToNumber[value]).getKey()
                            && tuple.getKey().getAge() <= ((Pair<Integer, Integer>) mapValueToNumber[value]).getValue();
                case 3://hypertension
                    condition = tuple.getKey().isHypertension() == (Boolean) mapValueToNumber[value];
                case 4: //heart disease
                    condition = tuple.getKey().isHeartDisease() == (Boolean) mapValueToNumber[value];
                case 5: //ever married
                    condition = tuple.getKey().isEverMarried() == (Boolean) mapValueToNumber[value];
                case 6: //work type
                    condition = tuple.getKey().getWorkType().equals((String) mapValueToNumber[value]);
                case 7: //residence type
                    condition = tuple.getKey().getResidenceType().equals((String) mapValueToNumber[value]);
                case 8: //avg glucose level
                    condition = tuple.getKey().getAvgGlucoseLevel() >= ((Pair<Integer, Integer>) mapValueToNumber[value]).getKey()
                            && tuple.getKey().getAvgGlucoseLevel() <= ((Pair<Integer, Integer>) mapValueToNumber[value]).getValue();
                case 9: //bmi
                    condition = tuple.getKey().getBmi() >= ((Pair<Integer, Integer>) mapValueToNumber[value]).getKey()
                            && tuple.getKey().getBmi() <= ((Pair<Integer, Integer>) mapValueToNumber[value]).getValue();
                case 10: //smoking status
                    condition = tuple.getKey().getSmokingStatus().equals((String) mapValueToNumber[value]);
                case 11: //stroke
                    condition = tuple.getKey().isStroke() == (Boolean) mapValueToNumber[value];
            }
            checkConditionAndUpdate(condition, tupleCopy, ec1, ec2, attribute_index, value, pairCopy);
        }
        return new Pair<>(ec1, ec2);
    }

    public void checkConditionAndUpdate(boolean condition, Tuple tuple, EquivalenceClass ec1, EquivalenceClass ec2, int attribute_index, int value, Pair pair) {
        if (condition) {
            tuple.setAttribute(attribute_index, value);
            ec1.addTuple(pair);
        } else {
            ec2.addTuple(pair);
        }
    }

    public int equivalenceClassSize() {
        return this.tuple_list.size();
    }
}
