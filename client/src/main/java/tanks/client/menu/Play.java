package tanks.client.menu;

import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tanks.client.models.TankBase;
import tanks.client.networking.Connection;
import tanks.client.networking.TankManager;

import java.awt.*;

public class Play {

    private static Connection connection;
    public static TankManager tankManager;
    public static Group root;
    public static Scene scene;

    public Stage showPlay(Stage theStage) {
        connection = new Connection("network");
        tankManager = new TankManager();
        root = new Group();
        final double CANVAS_WIDTH = 1600;
        final double CANVAS_HEIGHT = 900;

        final Image playScreen = new Image("/gui/menus/Play Stage.png");

        final ImageView flashScreen_node = new ImageView();
        flashScreen_node.setImage(playScreen);

        Rectangle r1 = new Rectangle(296, 128);
        Rectangle r2 = new Rectangle(133, 247);
        Rectangle r3 = new Rectangle(329, 176);
        Rectangle r4 = new Rectangle(131, 295);
        r1.setLocation(584,425);
        r2.setLocation(800,285);
        r3.setLocation(1029,432);
        r4.setLocation(800,556);
        r1.setBounds(r1);
        r2.setBounds(r2);
        r3.setBounds(r3);
        r4.setBounds(r4);
        r1.setBounds(584,425,296,128);


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
                    if (!root.getChildren().contains(tank.getHullView()))
                        root.getChildren().add(tank.getHullView());

                    tank.render();
                }

                lastTime = now;
            }
        };
        animationTimer.start();

        root.getChildren().add(flashScreen_node);
        scene = new Scene(root, CANVAS_WIDTH, CANVAS_HEIGHT, Color.BLACK);

        theStage.setScene(scene);

        return theStage;
    }
}
