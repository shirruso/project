

import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Main {
    // holds all the equivalence classes that are induced by the current anonymization
    //private static List<EquivalenceClass> equivalence_classes_list = new ArrayList<>();
    //hold the current best anonymization
    private static Head best_anonymization;
    // map each value to its attribute
    private static int[] mapValueToAttribute;
    // map each value of an attribute to a number
    private static Object[] mapValueToNumber;
    private static int k;
    private static int dataSetSize;
    private static List<Patient> dataSet;


    // do not forget to get k from input
    public static void main(String[] args) {
        dataSet = new ArrayList<>();
        EquivalenceClass most_general_ec = new EquivalenceClass();
        List<EquivalenceClass> equivalence_classes_list = new ArrayList<>();
        equivalence_classes_list.add(most_general_ec);
        best_anonymization = new Head(new ArrayList<>(), equivalence_classes_list);
        String line = "";
        String splitBy = ",";
        try {
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader(".\\src\\healthcare-dataset-stroke-data.csv"));
            int counter = 100;
            br.readLine();
            //parse data set
            while ((line = br.readLine()) != null && counter > 0)   //returns a Boolean value
            {
                String[] attributes = line.split(splitBy);    // use comma as separator
                /*create the first equivalence class that contains all the records in the dataset,
                 That is, the most generalized equivalence class*/
                Patient patient = new Patient(attributes);
                Pair<Patient, Tuple> pair = new Pair<>(patient, new Tuple());
                most_general_ec.addTuple(pair);
                dataSet.add(patient);
                // delete *******
                //System.out.println(patient);
                counter--;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
           Indexes    |  Attribute
             0 - 1        gender
             2 - 10       age
            11 - 12       hypertension
            13 - 14       heart disease
            15 - 16       ever married
            17 - 21       work type
            22 - 23       residence type
            24 - 30       avg glucose level
            31 - 37       bmi
            38 - 41       smoking status
            42 - 43       stroke

         */
        mapValueToNumber = new Object[]{"Male", "Female"
                , new Pair<>(0, 9), new Pair<>(1, 9), new Pair<>(10, 19), new Pair<>(20, 29), new Pair<>(30, 39)
                , new Pair<>(40, 49), new Pair<>(50, 59), new Pair<>(60, 69), new Pair<>(70, 82)
                , true, false
                , true, false
                , true, false
                , "children", "Govt_jov", "Never_worked", "Private", "Self-employed"
                , "Rural", "Urban"
                , new Pair<>(55, 85), new Pair<>(86, 116), new Pair<>(117, 147), new Pair<>(148, 178), new Pair<>(179, 209)
                , new Pair<>(210, 240), new Pair<>(241, 272)
                , new Pair<>(10, 20), new Pair<>(21, 31), new Pair<>(32, 42), new Pair<>(43, 53), new Pair<>(64, 74)
                , new Pair<>(75, 85), new Pair<>(86, 98)
                , "formerly smoked", "never smoked", "smokes", "Unknown"
                , true, false};
        // maps each value to its attribute
        mapValueToAttribute = new int[]
                {1, 1,
                        2, 2, 2, 2, 2, 2, 2, 2, 2,
                        3, 3,
                        4, 4,
                        5, 5,
                        6, 6, 6, 6, 6,
                        7, 7,
                        8, 8, 8, 8, 8, 8, 8,
                        9, 9, 9, 9, 9, 9, 9,
                        10, 10, 10, 10,
                        11, 11};

        k = 80;
        dataSetSize = 100;
        kOptimizeMain(k);
    }

    public static int kOptimizeMain(int k) {
        List<Integer> sigmaAll = IntStream.range(0, 44)
                .boxed()
                .collect(Collectors.toList());
        return KOptimize(k, best_anonymization, sigmaAll, Integer.MAX_VALUE);

    }

    public static int KOptimize(int k, Head head, List<Integer> tail, int bestCost) {
        tail = pruneUselessValues(head, tail);
        System.out.println("the new tail is: " + tail);
        int c_optional = computeCost(head);
        System.out.println("c optional is: " + c_optional);
        if (c_optional < bestCost) {
            best_anonymization = head;
            bestCost = c_optional;
        }
        tail = prune(head, tail, bestCost);
        System.out.println("The tail after prune:\n" + tail);
        return Integer.MAX_VALUE;
    }

    /*The purpose of the function is to prune all the tail values that induced equivalence classes that are smaller than k.
        these values are called "useless values". */
    public static List<Integer> pruneUselessValues(Head head, List<Integer> tail) {
        List<Integer> new_tail = new ArrayList();
        for (Integer value : tail) {
            List<Integer> valueList = new ArrayList<>();
            valueList.add(value);
            List<EquivalenceClass> new_equivalence_classes_list = updateEquivalenceClasses(head, valueList);
            if (!isUselessValue(new_equivalence_classes_list)) {
                new_tail.add(value);
            }
        }
        return new_tail;
    }
    /* create a new equivalence classes list resulting from adding "value" to the current anonymization */

    public static List<EquivalenceClass> updateEquivalenceClasses(Head head, List<Integer> values) {
        Head head_copy = new Head(new ArrayList<Integer>(head.getAnonymization()),head.getInducedEquivalenceClasses());
        List<EquivalenceClass> new_equivalence_classes_list = new ArrayList<>();
        for (Integer value : values) {
            new_equivalence_classes_list = new ArrayList<>();
            for (EquivalenceClass ec : head_copy.getInducedEquivalenceClasses()) {
                Pair<EquivalenceClass, EquivalenceClass> induced_ec = ec.induceEC(value, mapValueToAttribute[value], mapValueToNumber);
                if (induced_ec.getValue().size() > 0) {
                    new_equivalence_classes_list.add(induced_ec.getValue());
                }
                if (induced_ec.getKey().size() > 0) {
                    new_equivalence_classes_list.add(induced_ec.getKey());
                }
            }
            head_copy.addValue(value);
            List <EquivalenceClass> new_ec_copy = new ArrayList<>(new_equivalence_classes_list);
            head_copy.setEquivalenceClassList(new_ec_copy);
        }
        return new_equivalence_classes_list;
    }
    /*  If the new equivalence classes created by adding "value" to the head,  are all of size less than k,
       then "value" is called a useless value. Therefore, in this case the function return true, otherwise false.
    */

    public static boolean isUselessValue(List<EquivalenceClass> ec_list) {
        for (EquivalenceClass ec : ec_list) {
            if (ec.size() >= k)
                return false;
        }
        return true;
    }

    public static int computeCost(Head head) {
        int c = 0;
        int ecSize;
        for (EquivalenceClass ec : head.getInducedEquivalenceClasses()) {
            ecSize = ec.size();
            if (ecSize >= k) {
                c += ecSize * ecSize;
            } else {
                c += dataSetSize * ecSize;
            }
        }
        return c;
    }

    public static int computeLowerBound(Head head, List<Integer> tail) {
        int sum = 0;
        //list of all equivalence classes induced by allset A (union of head and tail)
        List<EquivalenceClass> ec_list = updateEquivalenceClasses(head, tail);
        for (Patient patient : dataSet) {
            for (EquivalenceClass ec_head : head.getInducedEquivalenceClasses()) {
                //find the equivalence class induced by head that contain patient
                if (ec_head.containsPatient(patient)) {
                    //check if this record is suppressed by H
                    if (ec_head.size() < k)
                        sum += dataSetSize;
                        // the record is not suppressed by H
                    else {
                        //find the equivalence class induced by the allset that contain patient
                        for (EquivalenceClass ec_allset : ec_list) {
                            if (ec_allset.containsPatient(patient))
                                sum += Math.max(ec_allset.size(), k);
                        }
                    }
                }
            }
        }
        //System.out.println(sum);
        return sum;
    }

    /*
    this function creates and returns a new tail set by removing values from T that can not
    lead to anonymization with cost lower than best_cost
     */
    public static List<Integer> prune(Head head, List<Integer> tail, int best_cost) {
        if (computeLowerBound(head, tail) >= best_cost)
            return new ArrayList<>();
        List<Integer> new_tail = new ArrayList<>(tail);
        System.out.println("tail : "+tail +"\n head: "+head.getAnonymization()+"\n");
        for (Integer value : tail) {
            List<Integer> value_list = new ArrayList<>();
            value_list.add(value);
            List<EquivalenceClass> new_ec_list = updateEquivalenceClasses(head, value_list);
            List<Integer> new_anonymization = new ArrayList<>(head.getAnonymization());
            new_anonymization.add(value);
            Head new_head = new Head(new_anonymization, new_ec_list);
            List<Integer> tail_without_value = new ArrayList<>(new_tail);
            tail_without_value.remove(value);
            if (prune(new_head, tail_without_value, best_cost).size() == 0)
                new_tail = tail_without_value;
        }
        if (!new_tail.equals(tail)){
            System.out.println("\nT_new not equal T , T_new: "+new_tail+"\ntail: "+tail);
            return prune(head, new_tail, best_cost);
        }
        return new_tail;
    }


}

