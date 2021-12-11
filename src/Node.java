import java.util.ArrayList;
import java.util.List;

public class Node {
    // each number in these lists represent a value of an attribute
    private List<Integer> head;
    private List<Integer> tail;

    public Node() {
        head = new ArrayList<>();
        tail = new ArrayList<>();
    }

    public void moveValueFromTailToHead(int value) {
        head.add(value);
        tail.remove(value);
    }

    public void removeValueFromTail(int value) {
        tail.remove(value);
    }
}
