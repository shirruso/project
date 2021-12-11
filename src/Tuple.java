/*
This class represent a record in the data set that has been generalized.
The first initialization represents the most generalized record
 */
public class Tuple {
    private Object[] record;

    /*
       Index   |  Attribute
         0         id
         1         gender
         2         age
         3         hypertension
         4         heart disease
         5         ever married
         6         work type
         7         residence type
         8         avg glucose level
         9         bmi
        10         smoking status
        11         stroke
     */
    public Tuple() {
        record = new Object[]{"*", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K"};
    }

    public Tuple(Tuple tuple){
        record = new Object[tuple.record.length];
        for(int i=0;i<tuple.record.length; i++){
            record[i] = tuple.record[i];
        }
    }
    public Object[] getRecord() {
        return record;
    }

    public void setAttribute(int index, int value) {
        this.record[index] = value;
    }

}
