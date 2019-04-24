package station;

import java.util.Calendar;
import java.util.Date;

public class OneDayTicket extends Ticket {
    private final long TIME_OF_A_DAY = 86400000;
    private final long TIME_OF_A_MIN = 60000;
    private boolean valid;

    public OneDayTicket(Date issuedDate) {
        super(issuedDate, Integer.MAX_VALUE);
        valid = true;
    }

    @Override
    public void adjustValue(int value) {

    }

    @Override
    public boolean isValid() {
        Calendar calendar = Calendar.getInstance();
        if ((calendar.getTimeInMillis()-super.issuedDate.getTime()) >= TIME_OF_A_DAY)
            this.valid = false;
        return valid;
    }
}
