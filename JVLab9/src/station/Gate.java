package station;

import java.util.Objects;

public class Gate {
    private String name;
    private int distance;

    public Gate(String name, int distance) {
        this.name = name;
        this.distance = distance;
    }

    public void enter(Ticket ticket) {
        if (ticket.isValid() && ticket.getOrigin() == null) {
            ticket.setOrigin(this);
            this.open();
        } else {
            this.close();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void exit(Ticket ticket) {
        // calculate fare
        Gate origin = ticket.getOrigin();
        if (origin == null) return;
        Line rail = new Line();
        int start, end, totalDistance;
        start = end = -1;
        for (int i = 0; i < rail.gates.length; i++) {
            if (rail.gates[i].equals(origin))
                start = i;
            if (rail.gates[i].equals(this)) {
                end = i;
                break;
            }
        }
        totalDistance = 0;
        if (start != -1 && end != -1) {
            for (int i = start; i < end; i++) {
                totalDistance += rail.gates[i].getDistance();
            }
        } else {
            System.out.println("You're cheating!");
            this.close();
        }

        // check valid
        if (ticket.getValue() >= Line.getFare(totalDistance)) {
            ticket.adjustValue(Line.getFare(totalDistance));
            this.open();
        } else this.close();
    }

    public void open() {
        System.out.println("Gate " + this.getName() + " is opening");
    }

    public void close() {
        System.out.println("Gate " + this.getName() + " is closing");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Gate)) return false;
        Gate gate = (Gate) o;
        return distance == gate.distance &&
                Objects.equals(name, gate.name);
    }

}
