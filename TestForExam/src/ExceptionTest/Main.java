package ExceptionTest;

public class Main {
    public static void main(String[] args) {
        Test test = new Test();
        try {
            test.printString("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
