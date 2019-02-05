package tanks.client.models;

import javafx.animation.AnimationTimer;

public class TankLocal extends TankBase {
    boolean isRightPressed, isLeftPressed, isUpPressed, isDownPressed;
    double hullRotationFactor = 2.0;
    double speedFactor = 2.0;

    public TankLocal() {
        super();
        setHullImage("/gui/sprites/TankLocal.png");

        getHullView().setFocusTraversable(true);

        getHullView().setOnKeyPressed(event -> {
            switch(event.getCode()) {
                case UP:
                    isUpPressed = true;
                    break;
                case DOWN:
                    isDownPressed = true;
                    break;
                case LEFT:
                    isLeftPressed = true;
                    break;
                case RIGHT:
                    isRightPressed = true;
                    break;
            }
        });

        getHullView().setOnKeyReleased(event -> {
            switch(event.getCode()) {
                case UP:
                    isUpPressed = false;
                    break;
                case DOWN:
                    isDownPressed = false;
                    break;
                case LEFT:
                    isLeftPressed = false;
                    break;
                case RIGHT:
                    isRightPressed = false;
                    break;
            }
        });

        AnimationTimer timer = new AnimationTimer(){
            @Override
            public void handle(long now) {
                if (isRightPressed) {
                    if (hullRotation == -360) {
                        hullRotation = 0;
                    }
                    hullView.setRotate(hullRotation += hullRotationFactor);

                }

                if (isLeftPressed) {
                    if (hullRotation == 360) {
                        hullRotation = 0;
                    }
                    hullView.setRotate(hullRotation -= hullRotationFactor);}

                if (isUpPressed) {
                    hullView.setX(hullView.getX() + speedFactor * (Math.cos(Math.toRadians(hullRotation))));
                    hullView.setY(hullView.getY() + speedFactor * (Math.sin(Math.toRadians(hullRotation))));
                }

                if (isDownPressed) {
                    hullView.setX(hullView.getX() - speedFactor * (Math.cos(Math.toRadians(hullRotation))));
                    hullView.setY(hullView.getY() - speedFactor * (Math.sin(Math.toRadians(hullRotation))));
                }
            }
        };
        timer.start();
    }

    public void setVelocity(double x, double y) {
        this.velocityX = x;
        this.velocityY = y;
    }

    public void addVelocity(double x, double y) {
        this.velocityX += x;
        this.velocityY += y;
    }
}
