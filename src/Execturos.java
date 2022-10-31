import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

public class Execturos {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = new ForkJoinPool();


        executorService.shutdown();
//        executorService.awaitTermination(1, TimeUnit.SECONDS);
    }
}
