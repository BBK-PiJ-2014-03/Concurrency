public class Increaser implements Runnable {
    private Counter c;
    
    private static Object obj = new Object();
    
    public Increaser(Counter counter) {
        this.c = counter;
    }
    
    public static void main(String args[]) {
        Counter counter = new Counter();
        
        for (int i = 0; i < 100; i++) {
            Increaser increaserTask = new Increaser(counter);
            Thread t = new Thread(increaserTask);
            t.start();
        }
        System.out.println("HERE");
        synchronized(obj) {
            obj.notifyAll();
        }
    }
    
    public void run() {
        synchronized(obj){
            try {
                System.out.println("Starting at " + c.getCount());
                
                for (int i = 0; i < 1000; i++) {
                    c.increase();
                }
                System.out.println("Stopping at " + c.getCount());
                obj.wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        obj.notifyAll();
        }
    }
}