import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String line = "";
        String splitBy = ",";
        try {
            System.out.println("3");
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\shirr\\Documents\\Courses\\MiniProject\\K_anonymization\\src\\healthcare-dataset-stroke-data.csv"));
            int counter = 100;
            br.readLine();
            while ((line = br.readLine()) != null && counter > 0)   //returns a Boolean value
            {
                String[] attributes = line.split(splitBy);    // use comma as separator
                Patient patient = new Patient(attributes);
                System.out.println(patient);
                counter--;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

