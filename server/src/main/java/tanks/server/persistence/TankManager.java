package tanks.server.persistence;

import java.util.HashSet;

public class TankManager {

    private static int tankId = 0;

    private HashSet<Tank> tankSet;

    public TankManager() {
        this.tankSet = new HashSet<>();
    }

    public static Tank createTank() {
        return new Tank(tankId++);
    }

    public boolean addTank(Tank tank) {
        return this.tankSet.add(tank);
    }
}
