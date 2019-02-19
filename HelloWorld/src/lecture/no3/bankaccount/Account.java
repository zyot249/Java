package lecture.no3.bankaccount;

public class Account {
    private double balance;
    private String name;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    void display() {
        System.out.println("Account name: " + getName());
        System.out.println("Balance :" + getBalance());
    }

    void setData(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    void deposit(double pMoney) {
        this.balance += pMoney;
    }

    boolean withdraw(double pMoney) {
        if (pMoney < 0) return false;
        double temp;
        temp = getBalance() - pMoney;
        if (temp < 0) {
            return false;
        } else {
            setBalance(temp);
            return true;
        }
    }
}
