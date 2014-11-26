import java.util.concurrent.Executor;

public class ExecutorImpl implements Executor {
    
    private RetrieveTask manager;
    
    public ExecutorImpl() {
        manager = new RetrieveTask();
        Thread t = new Thread(manager);
        t.start();
    }
    
    public void execute(Runnable r) {
        manager.addTask(r);
    }
    
    public void shutdown() {
        this.manager.shutdown();
    }
}