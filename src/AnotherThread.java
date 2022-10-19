public class AnotherThread extends Thread {
    @Override
    public void run() {
        System.out.println(ThreadColors.RED + "Hello from another thread");
        try {
            Thread.sleep(3000);
        } catch(InterruptedException e) {
            System.out.println(ThreadColors.RED + "Another thread woke me up");
        }

        System.out.println(ThreadColors.RED + "Three seconds have elapsed and I am now awake");
    }
}
