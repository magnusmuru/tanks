package tanks.client;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tanks.client.models.TankBase;
import tanks.client.models.TankLocal;
import tanks.client.networking.Connection;
import tanks.client.networking.TankManager;
import tanks.client.menu.Title;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Main extends Application {
    private static Main instance;

    private static int windowWidth = 800;
    private static int windowHeight = 450;

    private static Connection connection;
    public static TankManager tankManager;

    public static Group root;
    public static Scene scene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        instance = this;
        root = new Group();

        scene = new Scene(root, windowWidth, windowHeight);
        Title title = new Title();
        title.showTitle(primaryStage).show();

        primaryStage.setTitle("The Game");
        primaryStage.setWidth(windowWidth);
        primaryStage.setHeight(windowHeight);

        tankManager = new TankManager();

        title.play_button.setOnAction(e -> {
            connection = new Connection("network");
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

            root.getChildren().add(frameRate);

            primaryStage.setScene(scene);
            primaryStage.show();

        });
    }

    public static Main getInstance() {
        return instance;
    }
}
