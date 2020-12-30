import java.util.concurrent.atomic.*;

public class Lucky {
    static AtomicInteger x = new AtomicInteger(0);
    static AtomicInteger count = new AtomicInteger(0);
    private static final Object lock = new Object();

    static class LuckyThread extends Thread {
        @Override
        public void run() {
            while (true) {
                synchronized (lock) {
                    int i = x.incrementAndGet();
                    if (i >= 999999)
                        break;
                    if ((i % 10) + (i / 10) % 10 + (i / 100) % 10 == (i / 1000)
                            % 10 + (i / 10000) % 10 + (i / 100000) % 10) {
                        System.out.println(i);
                        count.incrementAndGet();
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new LuckyThread();
        Thread t2 = new LuckyThread();
        Thread t3 = new LuckyThread();
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        System.out.println("Total: " + count);
    }
}
