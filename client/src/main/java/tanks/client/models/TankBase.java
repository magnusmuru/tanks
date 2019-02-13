package tanks.client.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;


public class TankBase {
    @Getter protected double positionX;
    @Getter protected double positionY;
    @Getter @Setter protected double velocityX;
    @Getter @Setter protected double velocityY;

    @Getter @Setter protected double hullRotation;
    @Getter @Setter protected double turretRotation;

    @Getter private Image hullImage;
    @Getter protected ImageView hullView;

    protected ImageView turretSprite;

    /**
     * Since turret has the same sprite are for both local and remote tank, we'll set that here
     */
    public TankBase() {
         this.turretSprite = new ImageView(new Image("/gui/sprites/TankLocal.png"));
    }

    /**
     * Updates tank's coordinates on the playing field.
     *
     * @param time Milliseconds from last frame.
     */
    public void update(double time) {
    }

    /**
     * Render tank on screen.
     */
    public void render() {}

    protected void setHullImage(String url) {
        this.hullImage = new Image(url);
        this.hullView = new ImageView(hullImage);
    }
}
