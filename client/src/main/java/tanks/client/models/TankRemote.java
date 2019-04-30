package tanks.client.models;

public class TankRemote extends TankBase {
    public TankRemote() {
        super();
        setHullImage("/gui/sprites/TankBases/TankEnemy.png");
    }

    @Override
    public void render() {
        hullView.setRotate(hullRotation);
        hullView.setX(positionX);
        hullView.setY(positionY);
    }

}
