package lecture.no3.bankaccount;

public class Program {
    public static void main(String[] args) {
        Account account1 = new Account();
        account1.setData("Mr Nam",20000);
        account1.display();

        account1.deposit(50000);
        account1.display();
    }
}
