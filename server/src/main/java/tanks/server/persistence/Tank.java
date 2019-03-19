package tanks.server.persistence;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;


public class Tank {
    @Getter public int id;

    @Getter @Setter protected int positionX, positionY, mouseX, mouseY;
    @Getter @Setter protected double hullRotation, turretRotation;
    @Getter boolean isRightPressed, isLeftPressed, isUpPressed, isDownPressed;

    final double hullRotationFactor = 0.5;
    final double speedFactor = 0.5;

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
    }

    public void calculateTank() {
        System.out.println(isUpPressed);
        if (isUpPressed) {
            positionX += 10; //speedFactor * Math.cos(Math.toRadians(hullRotation));
            positionY += 10; //speedFactor * Math.cos(Math.toRadians(hullRotation));
        } else if (isDownPressed) {
            positionX -= speedFactor * Math.cos(Math.toRadians(hullRotation));
            positionY -= speedFactor * Math.cos(Math.toRadians(hullRotation));
        }

        if (isLeftPressed) {
            hullRotation += hullRotationFactor;
            if (hullRotation > 360) hullRotation -= 360;
        } else if (isRightPressed) {
            hullRotation -= hullRotationFactor;
            if (hullRotation < 0) hullRotation += 360;
        }
        System.out.println("Calc: " + positionX + "|" + positionY);
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
}
