package Exception;

public class TestException {
    private static int divide(int a, int b) throws ArithmeticException {
        if (b == 0) {
            throw new ArithmeticException("b == 0");
        } else
            return a/b;
    }

    public static void main(String[] args) {
        try {
            System.out.println(divide(10, 0));
        }catch (ArithmeticException e){
            e.printStackTrace();
        }
    }
}
