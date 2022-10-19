public class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println(ThreadColors.YELLOW + "Hello from MyRunnable's implementation of Runnable");
    }
}
