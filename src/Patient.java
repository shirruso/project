public class Patient {
    private int id;
    private String gender;
    private int age;
    private boolean hypertension;
    private boolean heart_disease;
    private boolean ever_married;
    private String work_type;
    private String residence_type;
    private double avg_glucose_level;
    private double bmi;
    private String smoking_status;
    private boolean stroke;

    public Patient(String[] metadata) {
        this.id = Integer.parseInt(metadata[0]);
        this.gender = metadata[1];
        this.age = Integer.parseInt(metadata[2]);
        this.hypertension = metadata[3].equals("1");
        this.heart_disease = metadata[4].equals("1");
        this.ever_married = metadata[5].equals("1");
        this.work_type = metadata[6];
        this.residence_type = metadata[7];
        this.avg_glucose_level = Double.parseDouble(metadata[8]);
        this.bmi = Double.parseDouble(metadata[9]);
        this.smoking_status = metadata[10];
        this.stroke = metadata[11].equals("1");
    }

    public String toString() {
        return "Patient [id= " + this.id + ", gender= " + this.gender + ", age= " + this.age + ", hypertension= " + this.hypertension
                + ", heart disease= " + this.heart_disease + ", ever married= " + this.ever_married + ", work type= " + work_type
                + ", residence type= " + this.residence_type + ", avg glucose level= " + this.avg_glucose_level + ", bmi= " + this.bmi
                + ", smoking status= " + this.smoking_status + ", stroke= " + this.stroke + "]";
    }


}
