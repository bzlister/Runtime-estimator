import org.apache.commons.lang3.time.StopWatch;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Estimator{

    private Algorithm func;
    private int MAX_N = 100;
    private int MIN_N = 0;
    private int NUM_PTS = 20;
    private int NUM_TRIALS = 1000;

    public Estimator(Algorithm func){
        if (func == null)
            throw new NullPointerException();
        this.func = func;
    }

    public Estimator(Algorithm func, int MIN_N, int MAX_N){
        this(func);
        this.MIN_N = MIN_N;
        this.MAX_N = MAX_N;
    }

    public Estimator(Algorithm func ,int MIN_N, int MAX_N, int NUM_PTS, int NUM_TRIALS){
        this(func, MIN_N, MAX_N);
        this.NUM_PTS = NUM_PTS;
        this.NUM_TRIALS = NUM_TRIALS;
    }

    public void compute(String path){
        System.out.print(String.format("Beginning computation of function %s...", func.getName()));
        EstimatorTimer timer = new EstimatorTimer();
        HashMap<Integer, TimeCountPair> times = new HashMap<>();
        times.put(0, new TimeCountPair());
        int[] points = new int[NUM_PTS];
        for (int a = 0; a < NUM_PTS; a++){
            points[a] = MIN_N + ((MAX_N-MIN_N)*a)/NUM_PTS;
        }
        for (int i = 0; i < NUM_TRIALS; i++){
            int n = points[i%NUM_PTS];
            try{
                timer.timeStart();
                func.run(n);
                long timeElapsed = timer.timeStop();
                times.putIfAbsent(n, new TimeCountPair());
                times.get(n).add(timeElapsed);
            } catch (IllegalArgumentException a){
                System.out.println(String.format("%l not a valid input for %s", n, (func.getName() == null ? "NO_NAME" : func.getName())));
            }
        }
        System.out.println("Done");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            for (int i = MIN_N; i < MAX_N; i++) {
                if (times.get(i) != null)
                    writer.write(((i == MIN_N ? "" : System.lineSeparator()) + i + ": " + times.get(i).value()));
            }
            writer.close();
        } catch (IOException e){
            System.out.println("IOException!");
        }
    }

    private class EstimatorTimer extends StopWatch {

        long start = 0;

        public EstimatorTimer(){
            super();
        }

        public void timeStart(){
            start = getNanoTime();
            this.start();
        }

        public long timeStop(){
            this.stop();
            long stop = getNanoTime();
            reset();
            return stop - start;
        }
    }

    private class TimeCountPair {
        private long meanTime = 0;
        private int numTrials = 0;

        public void add(long time){
            meanTime = (numTrials*meanTime + time)/(numTrials + 1);
            numTrials++;
        }

        public long value(){
            return meanTime;
        }
    }
}