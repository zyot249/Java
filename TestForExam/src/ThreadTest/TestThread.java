package ThreadTest;

public class TestThread {


    public static void main(String[] args) {
        final Object lock1 = new Object();
        final Object lock2 = new Object();
        Thread t1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("ThreadTest 1: Holding lock 1...");

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("ThreadTest 1: Waiting for lock 2...");

                synchronized (lock2) {
                    System.out.println("ThreadTest 1: Holding lock 1 & 2...");
                }
            }
        });
        Thread t2 = new Thread(() -> {
            synchronized (lock2) {
                System.out.println("ThreadTest 2: Holding lock 2...");

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("ThreadTest 2: Waiting for lock 1...");

                synchronized (lock1) {
                    System.out.println("ThreadTest 2: Holding lock 1 & 2...");
                }
            }
        });
        t1.start();
        t2.start();
    }
}
