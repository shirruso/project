import java.util.ArrayList;
import java.util.List;

public class Head {
    private List<Integer> anonymization;
    private List<EquivalenceClass> inducedEquivalenceClasses;

    public Head(List<Integer> anonymization, List<EquivalenceClass> inducedEquivalenceClasses){
        this.anonymization = anonymization;
        this.inducedEquivalenceClasses = inducedEquivalenceClasses;
    }


    public List<EquivalenceClass> getInducedEquivalenceClasses() {
        return inducedEquivalenceClasses;
    }

    public List<Integer> getAnonymization() {
        return anonymization;
    }
}
