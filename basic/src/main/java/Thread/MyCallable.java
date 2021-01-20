package Thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class MyCallable implements Callable{

    public void bark(){
        System.out.println("wang!wang!wang!");
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Callable c1 = new MyCallable();
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Future f1 = pool.submit(c1);
        System.out.println(f1.get().toString());
    }
    @Override
    public Object call() throws Exception {
        for (int i = 0; i < 5; i++) {
            bark();
            Thread.sleep(1000);
        }
        return "miao~miao~";
    }
}
