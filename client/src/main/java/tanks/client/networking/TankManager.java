package tanks.client.networking;

import lombok.Getter;
import lombok.Setter;
import tanks.client.Main;
import tanks.client.models.TankBase;
import tanks.client.models.TankLocal;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class TankManager {

    @Getter private Set<TankBase> tanks;
    @Getter private TankLocal tankLocal;

    public TankManager() {
        this.tanks = new HashSet<>();
    }

    public void addTank(TankBase tank) {
        this.tanks.add(tank);
        Main.root.getChildren().add(tank.getHullView());
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
     * @param id
     * @return
     */
    public String getTankData(String id) {
        TankLocal tankBase = (TankLocal) getTankById(id);

        if (tankBase == null) return "Not found";

        int posX = tankBase.getPositionX();
        int posY = tankBase.getPositionY();

        int mouseX = tankBase.getMouseX();
        int mouseY = tankBase.getMouseY();

        boolean isUpPressed = tankBase.isUpPressed();
        boolean isDownPressed = tankBase.isDownPressed();
        boolean isLeftPressed = tankBase.isLeftPressed();
        boolean isRightPressed = tankBase.isRightPressed();


        return String.format("%s %s %s %s %s %s %s %s %s",
                id,posX, posY, mouseX, mouseY,
                isUpPressed, isDownPressed, isLeftPressed, isRightPressed);
    }
}
