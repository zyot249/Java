public class Time {
    private int second;
    private int minute;
    private int hour;

    public void setSecond(int second) {
        if (second >= 0 && second <= 59)
            this.second = second;
        else throw new IllegalArgumentException("Invalid second!");
    }

    public void setMinute(int minute) {
        if (minute >= 0 && minute <= 59)
            this.minute = minute;
        else throw new IllegalArgumentException("Invalid minute!");
    }

    public void setHour(int hour) {
        if (hour >= 0 && hour <= 23)
            this.hour = hour;
        else throw new IllegalArgumentException("Invalid hour!");
    }

    public void setTime(int second, int minute, int hour) {
        this.setSecond(second);
        this.setMinute(minute);
        this.setHour(hour);
    }

    public Time(int second, int minute, int hour) {
        this.setTime(second, minute, hour);
    }

    public Time() {
        this.second = 0;
        this.minute = 0;
        this.hour = 0;
    }

    public int getSecond() {
        return second;
    }

    public int getMinute() {
        return minute;
    }

    public int getHour() {
        return hour;
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d:%02d", hour, minute, second);   // format hh:mm:ss
    }

    public Time nextSecond() {
        ++second;
        if (second == 60) {
            second = 0;
            ++minute;
            if (minute == 60) {
                ++hour;
                minute = 0;
                if (hour == 24)
                    hour = 0;
            }
        }
        return this;
    }
}
