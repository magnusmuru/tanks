package tanks.server.persistence;

import lombok.Getter;
import tanks.server.ServerMain;

import java.util.ArrayList;

public class ShotManager {
    @Getter private ArrayList<Shot> shots;
    private ArrayList<Shot> removeQueue;

    public ShotManager() {
        this.shots = new ArrayList<>();
        this.removeQueue = new ArrayList<>();
    }

    public void addShot(Shot shot) {
        shots.add(shot);
    }

    public void removeShot(Shot shot) {
        removeQueue.add(shot);
    }

    public void tick() {
        for (Shot shot : shots) {
            shot.update();
        }
        shots.removeAll(removeQueue);
        removeQueue.clear();
    }
}
