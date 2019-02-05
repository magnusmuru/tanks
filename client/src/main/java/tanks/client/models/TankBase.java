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

    public TankBase() {
         this.turretSprite = new ImageView(new Image("/gui/sprites/TankLocal.png"));
    }

    public void update(double time) {
        this.positionX += velocityX * time;
        this.positionY += velocityY * time;
    }

    public void setPosition(double posX, double posY) {
        this.positionX = posX;
        this.positionY = posY;
    }

    protected void setHullImage(String url) {
        this.hullImage = new Image(url);
        this.hullView = new ImageView(hullImage);
    }
}
