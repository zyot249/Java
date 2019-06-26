package ExceptionTest;

public class Test {

    public void printString(String str) throws Exception{
        class Test1{
            int hello;
        }

        if (str.isEmpty()) throw new Exception("Empty String");
        else System.out.println(str);
    }
}
