package tanks.client.networking;

import lombok.Getter;
import lombok.Setter;
import tanks.client.Main;
import tanks.client.models.TankBase;
import tanks.client.models.TankCustomizer;
import tanks.client.models.TankLocal;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;

public class TankManager {

    @Getter private Set<TankBase> tanks;
    @Getter private TankLocal tankLocal;

    public TankManager() {
        this.tanks = new HashSet<>();
    }

    public void addTank(TankBase tank) {
        this.tanks.add(tank);
    }

    public void addLocalTank(String id) {
        TankLocal tankLocal = new TankLocal();
        tankLocal.id = id;

        Main.scene.setOnMouseMoved(event -> tankLocal.setMousePosition(event.getSceneX(), event.getSceneY()));

        this.tankLocal = tankLocal;
        addTank(tankLocal);
    }

    public TankBase getTankById(String id) {
        Optional<TankBase> oTankBase = this.tanks.stream().filter(tankBase -> tankBase.getId().equals(id)).findFirst();
        return oTankBase.orElse(null);
    }

    public void flushManager() {
        this.tanks.clear();
    }

    /**
     * Updates a tank in manager based on dataString argument.
     *
     * dataString looks like this:
     * [ID] [x] [y] [hullAngle] [turretAngle] [shotCooldown]
     *
     * @param dataString
     */
    public void updateTank(String dataString) {
        String[] values = dataString.split(" ");

        String id = values[0];
        TankLocal tankBase = (TankLocal) getTankById(id);

        if (tankBase == null) {
            tankBase = new TankLocal();
            tankBase.id = id;
            addTank(tankBase);
        }

        int posX = Integer.parseInt(values[1]);
        int posY = Integer.parseInt(values[2]);
        double hullAngle = Double.parseDouble(values[3]);
        double turretAngle = Double.parseDouble(values[4]);
        double shotCooldown = Double.parseDouble(values[5]);

        tankBase.setPositionX(posX);
        tankBase.setPositionY(posY);
        tankBase.setHullRotation(hullAngle);
        tankBase.setTurretRotation(turretAngle);
    }

    /**
     * Queries tank data
     *
     * Returnable dataString looks like this:
     * [ID] [x] [y] [mouseX] [mouseY] [isUpPressed] [isDownPressed] [isLeftPressed] [isRightPressed]
     *
     * @return
     */
    public String getTankData() {
        if (tankLocal == null) return "";

        int posX = tankLocal.getPositionX();
        int posY = tankLocal.getPositionY();

        int mouseX = tankLocal.getMouseX();
        int mouseY = tankLocal.getMouseY();

        boolean isUpPressed = tankLocal.isUpPressed();
        boolean isDownPressed = tankLocal.isDownPressed();
        boolean isLeftPressed = tankLocal.isLeftPressed();
        boolean isRightPressed = tankLocal.isRightPressed();

        return String.format("%s %s %s %s %s %s %s %s %s",
                tankLocal.id, posX, posY, mouseX, mouseY,
                isUpPressed, isDownPressed, isLeftPressed, isRightPressed);
    }
}
