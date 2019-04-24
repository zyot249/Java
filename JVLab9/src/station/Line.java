package station;

public class Line {
    public Gate[] gates;

    public Line() {
        gates = new Gate[4];
        gates[0] = new Gate("Station A", 17);
        gates[1] = new Gate("Station B", 5);
        gates[2] = new Gate("Station C", 11);
        gates[3] = new Gate("Station D", 0);
    }

    public static int getFare(int distance) {
        if (distance <= 4) return 120;
        else {
            int bonus = distance - 4;
            if (bonus % 2 == 0) {
                return 120 + bonus * 15;
            } else
                return 120 + ((bonus / 2) + 1) * 30;
        }
    }
}
