public class Countdown {
    private int i;
    public void doCountdown() {
        String color;

        switch(Thread.currentThread().getName()) {
            case "Thread 1":
                color = ThreadColors.CYAN;
                break;
            case "Thread 2":
                color = ThreadColors.PURPLE;
                break;
            default:
                color = ThreadColors.GREEN;
        }

        for(i = 10; i > 0; i --) {
            System.out.println(color + Thread.currentThread().getName() + ": i = " + i);
        }
    }
}












