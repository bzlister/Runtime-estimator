import java.util.ArrayList;
import java.util.HashMap;

public class Polynomial implements FunctionFamily {

    private HashMap<Integer, Long> values;

    public long evaluate(ArrayList<Integer> parameters, long x){
        int highestDegree = parameters.get(0)+1;
        long retval = 0;
        for (int i = 1; i < parameters.size(); i++){
            retval += (long)(Math.pow(x,highestDegree-i)*parameters.get(i));
        }
        return retval;
    }

    public void setValues(HashMap<Integer, Long> values){
        this.values = values;
    }

    public long SSE(ArrayList<Integer> paramaters){
        long sum = 0;
        for (Integer x : values.keySet()){
            long diff = evaluate(paramaters, x) - values.get(x);
            sum += diff*diff;
        }
        return sum;
    }

    public void tune(){

    }
}
