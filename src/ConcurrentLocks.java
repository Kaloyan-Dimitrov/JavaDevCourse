import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentLocks {
    public static void main(String[] args) {
        List<String> buffer = new ArrayList<>();
        ReentrantLock bufferLock = new ReentrantLock();
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        MyProducer producer = new MyProducer(buffer, ThreadColors.YELLOW, bufferLock);
        MyConsumer consumer1 = new MyConsumer(buffer, ThreadColors.PURPLE, bufferLock);
        MyConsumer consumer2 = new MyConsumer(buffer, ThreadColors.CYAN, bufferLock);

        executorService.execute(producer);
        executorService.execute(consumer1);
        executorService.execute(consumer2);

        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println(ThreadColors.RED + "I'm being printed for the callable class");
                return "This is the callable result";
            }
        });

        try {
            System.out.println(future.get());
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }

        executorService.shutdown();
    }
}

class MyProducer implements Runnable {
    public MyProducer(List<String> buffer, String color, ReentrantLock bufferLock) {
        this.buffer = buffer;
        this.color = color;
        this.bufferLock = bufferLock;
    }

    private final List<String> buffer;
    private final String color;
    private final ReentrantLock bufferLock;

    public void run() {
        Random random = new Random();
        String[] nums = {"1", "2", "3", "4", "5"};

        for (String num : nums) {
            try {
                System.out.println(color + "Adding..." + num);
                try {
                    bufferLock.lock();
                    buffer.add(num);
                } finally {
                    bufferLock.unlock();
                }

                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                System.out.println("Producer was interrupted");
            }
        }

        System.out.println(color + "Adding EOF and exiting...");
        try {
            bufferLock.lock();
            buffer.add("EOF");
        } finally {
            bufferLock.unlock();
        }
    }
}


class MyConsumer implements Runnable {
    public MyConsumer(List<String> buffer, String color, ReentrantLock bufferLock) {
        this.buffer = buffer;
        this.color = color;
        this.bufferLock = bufferLock;
    }

    private final List<String> buffer;
    private final String color;
    private final ReentrantLock bufferLock;

    public void run() {
        while (true) {
            try {
                bufferLock.lock();
                if (buffer.isEmpty()) {
                    continue;
                }
                if (buffer.get(0).equals("EOF")) {
                    System.out.println(color + "Exiting");
                    break;
                } else {
                    System.out.println(color + "Removed " + buffer.remove(0));
                }
            } finally {
                bufferLock.unlock();
            }
        }
    }
}