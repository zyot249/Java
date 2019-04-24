package station;

import java.util.Date;

public class PrepaidCard extends Ticket implements ICard {

    public PrepaidCard(Date issuedDate, int value) {
        super(issuedDate, value);
    }

    @Override
    public void add(int amount) {
        super.value += amount;
    }

    @Override
    public boolean deduct(int amount) {
        if (super.value < amount)
            return false;
        else {
            super.value -= amount;
            return true;
        }
    }

    @Override
    public void adjustValue(int amount) {
        deduct(amount);
    }

    @Override
    public boolean isValid() {
        return super.value != 0;
    }
}
