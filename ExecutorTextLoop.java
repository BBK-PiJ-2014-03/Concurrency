import java.util.concurrent.*;

public class ExecutorTextLoop implements Runnable {
    
    public static final int COUNT = 10;
    
    private final String name;
    
    private static final int NUM_THREADS = 2;
    
    public ExecutorTextLoop(String name) {
        this.name = name;
    }
    
    @Override
    public void run() {
        long threadID = Thread.currentThread().getId()%NUM_THREADS+1;
        for (int i = 0; i < COUNT; i++) {
            System.out.println("Loop: Thread " + threadID + ", iteration:" + i + ".");
        }
    }
    
    public static void main(String args[]) {
        
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        
        if (args.length < 1 || (!args[0].equals("0") && !args[0].equals("1"))) {
            System.out.println("USAGE: java TextLoop <mode>");
            System.out.println(" mode 0: without threads");
            System.out.println(" mode 1: with threads");
        } 
        else if (args[0].equals("0")) {
            for (int i = 0; i < 10; i++) {
                Runnable r = new ExecutorTextLoop("Thread " + i);
                r.run();
            }
        } 
        else {
            for (int i = 0; i < 10; i++) {
                Runnable r = new ExecutorTextLoop("Thread " + i);
                executor.execute(r);
            }
        }
        System.out.println(executor.isShutdown());
        executor.shutdown();
        System.out.println(executor.isShutdown());
    }
}