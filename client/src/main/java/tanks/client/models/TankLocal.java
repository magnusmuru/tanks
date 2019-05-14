package tanks.client.models;

import javafx.beans.property.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import lombok.Getter;
import lombok.Setter;
import tanks.client.Main;
import tanks.client.menu.Play;

public class TankLocal extends TankBase {
    @Getter BooleanProperty isRightPressed = new SimpleBooleanProperty(false);
    @Getter BooleanProperty isLeftPressed = new SimpleBooleanProperty(false);
    @Getter BooleanProperty isUpPressed = new SimpleBooleanProperty(false);
    @Getter BooleanProperty isDownPressed = new SimpleBooleanProperty(false);
    @Getter int mouseX, mouseY;

    private static final double initialCooldownTicks = 320.0;
    private IntegerProperty shotCooldownTicks = new SimpleIntegerProperty(0);
    @Getter BooleanProperty canShoot = new SimpleBooleanProperty();

    @Getter @Setter boolean shouldShoot = false;

    @Getter private Arc coolDownArc;

    /**
     * Initiate local tank. Set hull sprite, get focus and add keyboard input listeners.
     */
    public TankLocal() {
        super();

        setHullImage("/gui/sprites/TankBases/Tank1.png");
        getHullView().setFocusTraversable(true);
        super.bindTurret();

        canShoot.bind(shotCooldownTicks.isEqualTo(0));

        getHullView().setOnKeyPressed(event -> {
            switch(event.getCode()) {
                case UP: isUpPressed.setValue(true); break;
                case DOWN: isDownPressed.setValue(true); break;
                case LEFT: isLeftPressed.setValue(true); break;
                case RIGHT: isRightPressed.setValue(true); break;
            }
        });

        getHullView().setOnKeyReleased(event -> {
            switch(event.getCode()) {
                case UP: isUpPressed.setValue(false); break;
                case DOWN: isDownPressed.setValue(false); break;
                case LEFT: isLeftPressed.setValue(false); break;
                case RIGHT: isRightPressed.setValue(false); break;
            }
        });

        coolDownArc = new Arc();
        coolDownArc.setType(ArcType.ROUND);;
        coolDownArc.setFill(Paint.valueOf("#abababbf"));
        coolDownArc.setStrokeWidth(0);
        coolDownArc.setRadiusX(40);
        coolDownArc.setRadiusY(40);
        coolDownArc.setStartAngle(90);

        //current / initial * 360
        coolDownArc.lengthProperty().bind(shotCooldownTicks.divide(initialCooldownTicks).multiply(360));
        coolDownArc.centerXProperty().bind(getHullView().xProperty().add(20));
        coolDownArc.centerYProperty().bind(getHullView().yProperty().add(20));
    }

    public void doShot() {
        if (canShoot.get()) {
            shouldShoot = true;
        }
    }

    public boolean getShot() {
        if (shouldShoot) {
            shouldShoot = false;
            shotCooldownTicks.setValue(initialCooldownTicks);
            return true;
        }
        return false;
    }

    /**
     * Set mouse coordinates to enable turret rotation.
     *
     * @param mouseX
     * @param mouseY
     */
    public void setMousePosition(double mouseX, double mouseY) {
        this.mouseX = (int) mouseX;
        this.mouseY = (int) mouseY;
    }

    /**
     * Render local tank
     */
    @Override
    public void render() {
        hullView.setRotate(hullRotation);
        hullView.setX(positionX);
        hullView.setY(positionY);
    }

    public void tickShotCooldown() {
        if (shotCooldownTicks.greaterThan(0).get()) {
            shotCooldownTicks.setValue(shotCooldownTicks.get() - 1);
        }
    }
}
