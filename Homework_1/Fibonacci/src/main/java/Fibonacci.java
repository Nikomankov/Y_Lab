import java.util.ArrayList;
import java.util.List;

public class Fibonacci {
    private final List<Integer> list;

    public Fibonacci(){
        list = new ArrayList<>(50);
    }

    public List<Integer> getList() {
        return List.copyOf(list);
    }

    public int getNumberByPosition(int n){
        if(list.size() < n){
            countingNumbers(n);
        }
        return list.get(n);
    }

    private void countingNumbers(int n){
        for(int i = list.size(); i <= n; i++){
            if(i < 2){
                list.add(i == 0 ? 0 : 1);
            } else {
                list.add(list.get(i-1) + list.get(i-2));
            }
        }
    }

}
