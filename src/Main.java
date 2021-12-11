import java.io.*;
import java.util.Scanner;

public class Main {
    // holds all the equivalence classes that are induced by the current anonymization
    private static List<EquivalenceClass> equivalence_classes_list = new ArrayList<>();
    //hold the current best anonymization
    private static List<Integer> best_anonymization = new ArrayList<>();
    // map each value to its attribute
    private static int[] mapValueToAttribute;
    // map each value of an attribute to a number
    private static Object[] mapValueToNumber;

    public static void main(String[] args) {
        String line = "";
        String splitBy = ",";
        try {
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader(".\\src\\healthcare-dataset-stroke-data.csv"));
            int counter = 100;
            br.readLine();
            EquivalenceClass most_general_ec = new EquivalenceClass();
            equivalence_classes_list.add(most_general_ec);
            //parse data set
            while ((line = br.readLine()) != null && counter > 0)   //returns a Boolean value
            {
                String[] attributes = line.split(splitBy);    // use comma as separator
                Patient patient = new Patient(attributes);
                Pair<Patient, Tuple> pair = new Pair<>(patient, new Tuple());
                most_general_ec.addTuple(pair);
                System.out.println(patient);
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
                , new Pair<>(0.8, 9), new Pair<>(1, 9), new Pair<>(10, 19), new Pair<>(20, 29), new Pair<>(30, 39)
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

    }

    public static List<Integer> pruneUselessValues(List<Integer> head, List<Integer> tail) {
        for (Integer value : tail) {

        }
        return null;
    }

}

