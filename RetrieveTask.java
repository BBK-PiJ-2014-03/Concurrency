import java.util.concurrent.*;
import java.util.ArrayDeque;
import java.util.Queue;

public class RetrieveTask implements Runnable {

    private Queue<Runnable> newQueue;
    private boolean running = true;

    public RetrieveTask() {
        this.newQueue = new ArrayDeque<Runnable>();
        this.running = true;
    }

    public void run() {
        while (true) {
            try {
                while (!newQueue.isEmpty()) {
                    Runnable nextTask = newQueue.poll();
                    nextTask.run();
                }
                synchronized(this) {
                    wait();
                }
            } catch (InterruptedException ex) {
            
            }
        }
    }
    
    public synchronized void addTask(Runnable r) {
        this.newQueue.offer(r);
        notifyAll();
    }
    
    public void shutdown() {
        this.running = false;
    }
}