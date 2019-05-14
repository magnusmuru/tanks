package tanks.client.models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;


public class TankBase {
    @Getter @Setter private String id;
    @Getter @Setter protected int positionX, positionY;
    @Getter @Setter protected double hullRotation;
    @Getter private SimpleDoubleProperty turretRotation = new SimpleDoubleProperty(0);

    @Getter private Image hullImage;
    @Getter protected ImageView hullView;

    @Getter protected ImageView turretSprite;

    /**
     * Since turret has the same sprite for both local and remote tank, we'll set that here
     */
    public TankBase() {
         this.turretSprite = new ImageView(new Image("/gui/sprites/TankTurrets/Turret1.png"));
    }

    protected void bindTurret() {
        this.turretSprite.xProperty().bind(hullView.xProperty().add(20));
        this.turretSprite.yProperty().bind(hullView.yProperty().add(20));
        this.turretSprite.rotateProperty().bind(turretRotation);
    }

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
