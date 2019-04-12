import java.util.ArrayList;

public class EstimationExample {
    public static void main(String[] args){

        Algorithm log = new Algorithm() {
            @Override
            public String getName() {
                return "Log";
            }

            @Override
            public void run(int n) throws IllegalArgumentException {
                while (n != 0){
                    try {
                        Thread.sleep(0, 1);//this is sometimes necessary for very 'fast' algorithms
                    } catch (InterruptedException e){
                        System.out.println("InterruptException!");
                    }
                    n/=2;
                }
            }
        };

        Algorithm exp2 = new Algorithm() {
            @Override
            public String getName() {
                return "Exp2";
            }

            @Override
            public void run(int n) throws IllegalArgumentException {
                ArrayList<Integer> queue = new ArrayList();
                queue.add(n);
                while (!queue.isEmpty()){
                    int task = queue.remove(0);
                    if (task > 1){
                        queue.add(task-1);
                        queue.add(task-2);
                    }
                }
            }
        };

        new Estimator(log).compute("logpoints.txt");
        new Estimator(exp2, 0, 15).compute("exp2points.txt");//MAX_N lowered to 15 to allow the method to finish in reasonable time

    }
}