public class Main {
    public static void main(String[] args) {
        System.out.println(ThreadColors.PURPLE + "Hello from the main thread");
        Thread anotherThread = new AnotherThread();
        new Thread() {
            @Override
            public void run() {
                System.out.println(ThreadColors.GREEN + "new thread");
            }
        }.start();
        anotherThread.start();

        Thread myRunnableThread = new Thread(new MyRunnable() {
            @Override
            public void run() {
                System.out.println(ThreadColors.YELLOW + "Hello from my runnable thread");
            }
        });
        myRunnableThread.start();

        System.out.println(ThreadColors.PURPLE + "Hello again from the main thread");
    }
}
