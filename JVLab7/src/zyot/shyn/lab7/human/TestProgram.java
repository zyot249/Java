package zyot.shyn.lab7.human;

public class TestProgram {
    public static void main(String[] args) {
        Student p = new Student("Giap", "Gam cau","An cut",3,5000000.0);
        Person newP = (Person) p;
        System.out.println(newP.getAddress());
    }
}
