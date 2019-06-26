package EnumTest;

public class Enum {
    public enum Day_of_week{
        SUNDAY(0), MONDAY(2), TUESDAY(3), WEDNESDAY(4), THUSDAY(5), FRIDAY(6), SATURDAY(7);
        private int value;
        Day_of_week(int value) {
            this.value = value;
        }
        static String getDate(Day_of_week date){
            return date.toString();
        }
        public int getValue(){
            return value;
        }
    }

    public static class Date{
        enum week{
            WEEK1, WEEK2;
        }
        public static final double PI = 3.14;
    }

    enum AbstractMethodInside{
        CONST1(5){
            @Override
            void print() {
                System.out.println(10);
            }
        };
        abstract void print();
        private int value;
        AbstractMethodInside(int value){
            this.value = value;
        }
    }

    public static void main(String[] args) {
//        AbstractMethodInside.CONST1.print();
//        System.out.println(AbstractMethodInside.CONST1.value);
//        System.out.println(AbstractMethodInside.values());
//        System.out.println(Day_of_week.valueOf("SUNDAY"));
//        System.out.println(Date.week.WEEK1);
//        Enum e = new Enum();
//        System.out.println(Day_of_week.FRIDAY);
        System.out.println(Day_of_week.getDate(Day_of_week.FRIDAY));
    }
}
