package tanks.server.persistence;

import lombok.Getter;
import lombok.Setter;
import tanks.server.ServerMain;

public class Shot {
    @Getter @Setter private double positionX;
    @Getter @Setter private double positionY;
    @Getter @Setter private double rotation;
    private static final int speedFactor = 10;

    public Shot(double positionX, double positionY, double rotation) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.rotation = rotation;
    }

    public void update() {
        if (positionX > 0 && positionX < 1600 && positionY > 0 && positionY < 900) {
            positionX += speedFactor * Math.cos(Math.toRadians(rotation));
            positionY += speedFactor * Math.sin(Math.toRadians(rotation));
            getShot(this, ServerMain.getInstance().getTankManager());
        } else {
            ServerMain.getInstance().getShotManager().removeShot(this);
        }
    }

    public void getShot(Shot shot, TankManager tankManager) {
        for (Tank tank : tankManager.getTankSet()) {
            if (tank.getPositionX() - 20 > shot.getPositionX() && tank.getPositionX() + 20 < shot.getPositionX() &&
                    tank.getPositionY() - 20 > shot.getPositionY() && tank.getPositionY() + 20 < shot.getPositionY()) {
                tank.killSpawnTank(tank);
                ServerMain.getInstance().getShotManager().removeShot(this);
            }
        }
    }

    public String getShotData() {
        return String.format("%s %s", positionX, positionY);
    }
}
