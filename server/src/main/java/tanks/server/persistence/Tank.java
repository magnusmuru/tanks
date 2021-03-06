package tanks.server.persistence;

import lombok.Getter;
import lombok.Setter;
import tanks.server.ServerMain;

import java.util.Objects;

public class Tank {
    private static final int WIDTH = 1600;
    private static final int HEIGHT = 860;

    @Getter
    public int id;

    @Getter
    @Setter
    protected int positionX, positionY, mouseX, mouseY;
    @Getter
    @Setter
    protected double hullRotation, turretRotation;
    @Getter
    boolean isRightPressed, isLeftPressed, isUpPressed, isDownPressed;
    @Getter
    boolean doShot;

    final double hullRotationFactor = 5;
    final double speedFactor = 4;

    public Tank(int id) {
        this.id = id;

        this.turretRotation = 0;
        this.hullRotation = 0;
    }

    /**
     * Updates tank in server TankManager
     *
     * @param data: [ID] [x] [y] [mouseX] [mouseY] [isUpPressed] [isDownPressed] [isLeftPressed] [isRightPressed]
     */
    public void updateVariables(String data) {
        String[] dataParts = data.split(" ");

        id = Integer.parseInt(dataParts[0]);
        positionX = Integer.parseInt(dataParts[1]);
        positionY = Integer.parseInt(dataParts[2]);

        mouseX = Integer.parseInt(dataParts[3]);
        mouseY = Integer.parseInt(dataParts[4]);

        isUpPressed = Boolean.valueOf(dataParts[5]);
        isDownPressed = Boolean.valueOf(dataParts[6]);
        isLeftPressed = Boolean.valueOf(dataParts[7]);
        isRightPressed = Boolean.valueOf(dataParts[8]);
        doShot = Boolean.valueOf(dataParts[9]);

    }

    public void calculateTank() {
        if (positionY < 0) positionY = 0;
        else if (HEIGHT < positionY) positionY = HEIGHT - 50;

        if (positionX < 0) positionX = 0;
        else if (WIDTH < positionX) positionX = WIDTH;

        if (isRightPressed) {
            hullRotation += hullRotationFactor;
            if (hullRotation > 360) hullRotation -= 360;
        } else if (isLeftPressed) {
            hullRotation -= hullRotationFactor;
            if (hullRotation < 0) hullRotation += 360;
        }

        if (isUpPressed) {
            positionX += speedFactor * Math.cos(Math.toRadians(hullRotation));
            positionY += speedFactor * Math.sin(Math.toRadians(hullRotation));
        } else if (isDownPressed) {
            positionX -= speedFactor * Math.cos(Math.toRadians(hullRotation));
            positionY -= speedFactor * Math.sin(Math.toRadians(hullRotation));
        }

        double angle = Math.atan2(mouseY - positionY, mouseX - positionX);
        turretRotation = Math.toDegrees(angle);

        if (doShot) {
            ServerMain.getInstance().getShotManager().addShot(new Shot(positionX, positionY, turretRotation));
        }
    }

    /**
     * [ID] [x] [y] [hullAngle] [turretAngle] [shotCooldown]
     *
     * @return
     */
    public String getTankInfo() {
        return String.format("%s %s %s %s %s %s",
                id, positionX, positionY, hullRotation, turretRotation, 0);
    }

    public void killSpawnTank(Tank tank) {
        tank.setPositionX((int) (Math.random() * WIDTH));
        tank.setPositionY((int) (Math.random() * HEIGHT));
    }

    @Override
    public String toString() {
        return getTankInfo();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tank tank = (Tank) o;
        return id == tank.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
