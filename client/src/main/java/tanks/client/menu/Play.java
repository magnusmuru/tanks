package tanks.client.menu;

import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tanks.client.models.TankBase;
import tanks.client.networking.Connection;
import tanks.client.networking.TankManager;

import java.awt.*;

public class Play {
    private static Play instance;

    public Play() {
        instance = this;
    }

    public static Connection connection;
    public static TankManager tankManager;
    public Group root, shots;
    public static Scene scene;

    final double CANVAS_WIDTH = 1600;
    final double CANVAS_HEIGHT = 900;

    public Stage showPlay(Stage theStage) {
        connection = new Connection("network");
        tankManager = new TankManager();
        root = new Group();
        shots = new Group();
        Group tanks = new Group();

        final Image playScreen = new Image("/gui/menus/Play Stage.png");

        final ImageView flashScreen_node = new ImageView();
        flashScreen_node.setImage(playScreen);

        Rectangle r1 = new Rectangle(584, 425, 296, 128);
        Rectangle r2 = new Rectangle(800, 285, 133, 247);
        Rectangle r3 = new Rectangle(1029, 432, 329, 176);
        Rectangle r4 = new Rectangle(800, 556, 131, 295);
        r1.setBounds(r1);
        r2.setBounds(r2);
        r3.setBounds(r3);
        r4.setBounds(r4);

        connection.start();

        Text frameRate = new Text(10, 50, "This is a text");

        AnimationTimer animationTimer = new AnimationTimer() {
            long lastTime;

            @Override
            public void start() {
                super.start();
                this.lastTime = System.nanoTime();
            }

            @Override
            public void handle(long now) {
                double deltaNanos = now - lastTime;
                double deltaMillis = deltaNanos / 1000000;

                frameRate.setText(String.format("%1$.2f", 1000 / deltaMillis) + " fps");

                for (TankBase tank : tankManager.getTanks()) {
                    Arc cooldownArc = tankManager.getTankLocal().getCoolDownArc();
                    if (!tanks.getChildren().contains(cooldownArc))
                        tanks.getChildren().add(cooldownArc);

                    if (!tanks.getChildren().contains(tank.getHullView()))
                        tanks.getChildren().add(tank.getHullView());

                    if (!tanks.getChildren().contains(tank.getTurretSprite()))
                        tanks.getChildren().add(tank.getTurretSprite());

                    tank.render();
                }

                lastTime = now;
            }
        };
        animationTimer.start();

        root.getChildren().addAll(flashScreen_node, tanks);
        scene = new Scene(root, CANVAS_WIDTH, CANVAS_HEIGHT, Color.BLACK);

        theStage.setScene(scene);

        return theStage;
    }

    public static Play getInstance() {
        return instance;
    }
}
