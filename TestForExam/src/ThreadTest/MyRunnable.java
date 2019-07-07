package ThreadTest;

public class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("hello thread!");
    }

    public static void main(String[] args) {
        new Thread(new MyRunnable()).start();
    }
}
