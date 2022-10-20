public class CountdownThread extends Thread {
    private Countdown threadCountdown;

    public CountdownThread(Countdown countdown) {
        this.threadCountdown = countdown;
    }

    public void run() {
        this.threadCountdown.doCountdown();
    }
}
