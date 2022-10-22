public class ThreadStarvation {
    public static Object lock = new Object();
    public static void main(String[] args) {
        Thread t1 = new Thread(new Worker(ThreadColors.PURPLE), "Priority 10");
        Thread t2 = new Thread(new Worker(ThreadColors.RED), "Priority 8");
        Thread t3 = new Thread(new Worker(ThreadColors.RED_BRIGHT), "Priority 6");
        Thread t4 = new Thread(new Worker(ThreadColors.YELLOW), "Priority 4");
        Thread t5 = new Thread(new Worker(ThreadColors.GREEN), "Priority 2");

        t1.setPriority(10);
        t2.setPriority(8);
        t3.setPriority(6);
        t4.setPriority(4);
        t5.setPriority(2);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }

    private static class Worker implements Runnable {
        private int runCount = 1;
        private String threadColor;

        public Worker(String threadColor) {
            this.threadColor = threadColor;
        }

        @Override
        public void run() {
            for(int i = 0; i < 5; i ++) {
                synchronized (lock) {
                    System.out.format(threadColor + "%s: runCount = %d\n", Thread.currentThread().getName(), runCount++);
                }
            }
        }
    }
}
