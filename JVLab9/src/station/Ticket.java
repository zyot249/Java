package station;

import java.util.Date;

public abstract class Ticket {
    protected Date issuedDate;
    protected int value;
    private Gate origin;

    public Ticket(Date issuedDate, int value) {
        this.issuedDate = issuedDate;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setOrigin(Gate gate) {
        this.origin = gate;
    }

    public Gate getOrigin() {
        return origin;
    }

    public void adjustValue(int amount) {
        this.value -= amount;
    }

    public abstract boolean isValid();
}
