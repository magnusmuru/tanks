package tanks.client.networking;

import lombok.Getter;
import lombok.Setter;
import tanks.client.Main;
import tanks.client.menu.Play;
import tanks.client.models.TankBase;
import tanks.client.models.TankCustomizer;
import tanks.client.models.TankLocal;
import tanks.client.models.TankRemote;

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

    public void addTankLocal(String id) {
        TankLocal tankLocal = new TankLocal();
        tankLocal.setId(id);

        Play.scene.setOnMouseMoved(event -> tankLocal.setMousePosition(event.getSceneX(), event.getSceneY()));
        Play.scene.setOnMouseClicked(event -> tankLocal.doShot());

        this.tankLocal = tankLocal;
        addTank(tankLocal);
    }

    public TankBase getTankById(String id) {
        Optional<TankBase> oTankBase = this.tanks.stream().filter(tankBase -> tankBase.getId().equals(id)).findFirst();
        return oTankBase.orElse(null);
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

        TankBase tankBase = getTankById(id);
        if (tankBase == null) {
            if (tankLocal == null) {
                addTankLocal(id);
            } else {
                TankRemote tankRemote = new TankRemote();
                tankRemote.setId(id);
                addTank(tankRemote);
            }

            tankBase = getTankById(id);
        }

        int posX = Integer.parseInt(values[1]);
        int posY = Integer.parseInt(values[2]);
        double hullAngle = Double.parseDouble(values[3]);
        double turretAngle = Double.parseDouble(values[4]);

        tankBase.setPositionX(posX);
        tankBase.setPositionY(posY);
        tankBase.setHullRotation(hullAngle);
        tankBase.getTurretRotation().set(turretAngle);
    }

    /**
     * Queries tank data
     *
     * Returnable dataString looks like this:
     * [ID] [x] [y] [mouseX] [mouseY] [isUpPressed] [isDownPressed] [isLeftPressed] [isRightPressed] [shouldShoot]
     *
     * @return
     */
    public String getTankData() {
        if (tankLocal == null) return "";

        return String.format("%s %s %s %s %s %s %s %s %s %s",
                tankLocal.getId(),
                tankLocal.getPositionX(), tankLocal.getPositionY(),
                tankLocal.getMouseX(), tankLocal.getMouseY(),
                tankLocal.getIsUpPressed().get(),
                tankLocal.getIsDownPressed().get(),
                tankLocal.getIsLeftPressed().get(),
                tankLocal.getIsRightPressed().get(),
                tankLocal.getShot());
    }
}
