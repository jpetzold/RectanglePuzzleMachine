import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadedApp {

    public static void main(String[] args) {

        Instant start = Instant.now();

        int numberOfObjects = 12; // Number of experiments to execute
        int numberOfThreads = Runtime.getRuntime().availableProcessors();
        System.out.println("Will be executing "+ numberOfObjects + " experiments in " +numberOfThreads+ " threads." );
        int minValue = Integer.MAX_VALUE;

//        int[][] rectanglesDimensions = {
//                {70, 20}, {100, 80}, {20, 40}, {20, 40}, {20, 40}, {20, 40}, {20, 40}, {20, 40}, {20, 40}, {20, 40},
//                {20, 40}, {20, 40}, {20, 40}, {20, 40}, {20, 40}, {20, 40}, {20, 40}, {70, 20}, {100, 80}, {20, 40},
//                {20, 40}, {20, 40}, {20, 40}, {20, 40}, {20, 40}, {20, 40}, {20, 40}, {50, 40}, {20, 30}, {20, 20},
//                {20, 40}, {100, 60}, {20, 40}, {50, 40}, {20, 30}, {20, 20}, {20, 80}, {100, 60}, {20, 40}, {20, 40},
//                {10, 40}, {20, 10}, {15, 15}, {20, 20}, {15, 15}, {15, 15}, {15, 15}, {15, 15}, {15, 15}, {15, 15},
//                {15, 15}, {15, 15}, {15, 15}, {15, 15}, {20, 20}, {20, 20}, {20, 20}, {20, 20}, {20, 20}, {20, 20},
//                {70, 20}, {100, 80}, {20, 40}, {20, 40}, {20, 40}, {20, 40}, {20, 40}, {20, 40}, {20, 40}, {20, 40},
//                {20, 40}, {20, 40}, {20, 40}, {20, 40}, {20, 40}, {20, 40}, {20, 40}, {70, 20}, {100, 80}, {20, 40},
//                {20, 40}, {20, 40}, {20, 40}, {20, 40}, {20, 40}, {20, 40}, {20, 40}, {50, 40}, {20, 30}, {20, 20},
//                {20, 40}, {100, 60}, {20, 40}, {50, 40}, {20, 30}, {20, 20}, {20, 80}, {100, 60}, {20, 40}, {20, 40},
//                {10, 40}, {20, 10}, {15, 15}, {20, 20}, {15, 15}, {15, 15}, {15, 15}, {15, 15}, {15, 15}, {15, 15},
//                {15, 15}, {15, 15}, {15, 15}, {15, 15}, {20, 20}, {20, 20}, {20, 20}, {20, 20}, {20, 20}, {20, 20},
//                {20, 20}, {20, 20}, {20, 20}, {20, 20}
//        };

        int[][] rectanglesDimensions = {
                {70, 20}, {100, 80}, {20, 40}, {20, 40}, {20, 40}, {20, 40}, {20, 40}, {20, 40}, {20, 40}, {20, 40},
                {20, 40}, {20, 40}, {20, 40}, {20, 40}, {20, 40}, {20, 40}, {20, 40}, {70, 20}, {100, 80}, {20, 40},
                {20, 40}, {20, 40}, {20, 40}, {20, 40}, {20, 40}, {20, 40}, {20, 40}, {50, 40}, {20, 30}, {20, 20},
                {20, 40}, {100, 60}, {20, 40}, {50, 40}, {20, 30}, {20, 20}, {20, 80}, {100, 60}, {20, 40}, {20, 40},
                {10, 40}, {20, 10}, {15, 15}, {20, 20}, {15, 15}, {15, 15}, {15, 15}, {15, 15}, {15, 15}, {15, 15},
                {15, 15}, {15, 15}, {15, 15}, {15, 15}, {20, 20}, {20, 20}, {20, 20}, {20, 20}, {20, 20}, {20, 20},
                {20, 20}, {20, 20}, {20, 20}, {20, 20}
        };

        // Fixed thread pool for platform threads
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        List<Future<Experiment>> results = new ArrayList<>();


        try {

        // Create experiments
        for (int i = 0; i < numberOfObjects; i++) {
            Experiment task = new Experiment( i,rectanglesDimensions);
            results.add( executor.submit(task::performTask));
        }

        // Collect results and calculate the minimum value
            Experiment winner=new Experiment(999999999,rectanglesDimensions);
        for (Future<Experiment> future : results) {
            Experiment result = future.get(); // Waits for the task to complete
            minValue = Math.min(minValue, result.getScore());
            if(result.getScore()==minValue)
            {
                winner=result;
            }
        }

        winner.show(500,10);




        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }

        System.out.println("All experiments completed. Min val="+minValue);
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toSeconds();
        System.out.println("Execution time (s): "+timeElapsed);

        System.exit(0);
    }
}



