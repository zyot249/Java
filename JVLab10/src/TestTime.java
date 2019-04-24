public class TestTime {
    public static void main(String[] args) {
        Time t1 = new Time(1, 2, 3);
        System.out.println(t1);
        Time t2 = new Time(60,59,12);
        try {
            Time t3 = new Time(60,59,12);
            System.out.println("This line will be skipped if exception occurs");
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }

        System.out.println("Continue after exception!");

    }
}
