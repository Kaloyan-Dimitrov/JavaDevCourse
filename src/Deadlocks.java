public class Deadlocks {
    public static Object lock1 = new Object();
    public static Object lock2 = new Object();

    public static void main(String[] args) {
        new Thread1(ThreadColors.YELLOW).start();
        new Thread2(ThreadColors.GREEN).start();
    }

    private static class Thread1 extends Thread {
        public String color;
        Thread1(String color) {
            super();
            this.color = color;
        }
        public void run() {
            synchronized (lock1) {
                System.out.println(color + "Thread 1: Has lock1");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {

                }
                System.out.println(color + "Thread 1: Waiting for lock2");
                synchronized (lock2) {
                    System.out.println(color + "Thread 1: Has lock1 and lock2");
                }
                System.out.println(color + "Thread 1: Released lock2");
            }
            System.out.println(color + "Thread 1: Released lock1. Exiting...");
        }
    }

    private static class Thread2 extends Thread {
        public String color;
        Thread2(String color) {
            super();
            this.color = color;
        }
        public void run() {
            synchronized(lock2) {
                System.out.println(color + "Thread 2: Has lock2");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {

                }
                System.out.println(color + "Thread 2: Waiting for lock1");
                synchronized(lock1) {
                    System.out.println(color + "Thread 2: Has lock1 and lock2");
                }
                System.out.println(color + "Thread 2: Released lock1");
            }
            System.out.println(color + "Thread 2: Released lock2. Exiting...");
        }
    }
}