package tanks.client.models;

public class TankLocal extends TankBase {
    boolean isRightPressed, isLeftPressed, isUpPressed, isDownPressed;
    double hullRotationFactor = 3.0;
    double speedFactor = 2.0;
    double mouseX, mouseY;

    /**
     * Initiate local tank. Set hull spite, get focus and add keyboard input listeners.
     */
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
    }

    /**
     * Set mouse coordinates to enable turret rotation.
     *
     * @param mouseX
     * @param mouseY
     */
    public void setMousePosition(double mouseX, double mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    /**
     * Calculate local tank's hull rotation and current position.
     *
     * @param time Milliseconds from last frame.
     */
    @Override
    public void update(double time) {
        super.update(time);

        if (isRightPressed) {
            if (hullRotation == -360) {
                hullRotation = 0;
            }
            hullRotation += hullRotationFactor;
        }

        if (isLeftPressed) {
            if (hullRotation == 360) {
                hullRotation = 0;
            }
            hullRotation -= hullRotationFactor;
        }

        if (isUpPressed) {
            positionX = hullView.getX() + speedFactor * (Math.cos(Math.toRadians(hullRotation)));
            positionY = hullView.getY() + speedFactor * (Math.cos(Math.toRadians(hullRotation)));
        }

        if (isDownPressed) {
            positionX = hullView.getX() - speedFactor * (Math.cos(Math.toRadians(hullRotation)));
            positionY = hullView.getY() - speedFactor * (Math.cos(Math.toRadians(hullRotation)));
        }
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
}
