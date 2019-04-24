package station;

import java.util.Date;

public class OneWayTicket extends Ticket {
    private boolean valid;

    public OneWayTicket(Date issuedDate, int value) {
        super(issuedDate, value);
        this.valid = true;
    }

    @Override
    public void setOrigin(Gate gate) {
        super.setOrigin(gate);
        this.valid = false;
    }

    @Override
    public boolean isValid() {
        return valid;
    }
}
