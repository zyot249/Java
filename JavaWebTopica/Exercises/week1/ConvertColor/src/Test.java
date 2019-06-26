public class Test {
    public static void main(String[] args) {
        Convert convert = new Convert();
        try {
            convert.readBitmapImage("inputimage.bmp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
