public class ResponsiveAI implements Runnable {

    private final int TIME;
    private final int TASK;
    
    private static String finished = "Finished Tasks : ";
    
    public ResponsiveAI(int time, int task) {
        this.TIME = time;
        this.TASK = task;
    }

    public static void main(String[] args) {
        
        for (int i = 0; i < 11; i++) {
            System.out.print("Enter the duration in milliseconds of task " + i + ": ");
            String newTime = System.console().readLine();
            int newInt = Integer.parseInt(newTime);
            Runnable r = new ResponsiveAI(newInt, i);
            Thread t = new Thread(r);
            t.start();
            resetFinished();
        }
    }
    
    public static void resetFinished() {
        System.out.println(finished);
        finished = "Finished Tasks : ";
    }
    
    @Override
    public void run() {
        try {
            Thread.sleep(TIME);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        finished += TASK + ", ";
    }
}