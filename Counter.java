import java.util.concurrent.atomic.*;

public class Counter {
    private AtomicInteger n = new AtomicInteger(0);
    
    public void increase() {
        n.incrementAndGet();
    }
    public int getCount() {
        return n.get();
    }
}