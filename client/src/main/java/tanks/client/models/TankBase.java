package tanks.client.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;


public class TankBase {
    @Getter @Setter public String id;

    @Getter @Setter protected int positionX;
    @Getter @Setter protected int positionY;

    @Getter @Setter protected double hullRotation;
    @Getter @Setter protected double turretRotation;

    @Getter private Image hullImage;
    @Getter protected ImageView hullView;

    protected ImageView turretSprite;

    /**
     * Since turret has the same sprite for both local and remote tank, we'll set that here
     */
    public TankBase() {
         this.turretSprite = new ImageView(new Image("/gui/sprites/TankBases/Tank1.png"));
    }

    /**
     * Updates tank's coordinates on the playing field.
     *
     * @param time Milliseconds from last frame.
     */
    //public abstract void update(double time);

    /**
     * Render tank on screen.
     */
    public void render() {}

    protected void setHullImage(String url) {
        this.hullImage = new Image(url);
        this.hullView = new ImageView(hullImage);
    }

    protected void remove() {

    }
}
