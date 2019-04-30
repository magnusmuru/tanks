package tanks.client.models;

import lombok.Getter;

public class TankLocal extends TankBase {
    @Getter boolean isRightPressed, isLeftPressed, isUpPressed, isDownPressed;
    @Getter int mouseX, mouseY;

    /**
     * Initiate local tank. Set hull spite, get focus and add keyboard input listeners.
     */
    public TankLocal() {
        super();
        setHullImage("/gui/sprites/TankBases/Tank1.png");
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
}
