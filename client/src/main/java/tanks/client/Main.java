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
        root = new Group();

        scene = new Scene(root, windowWidth, windowHeight);
        double CANVAS_WIDTH = 800;
        final double CANVAS_HEIGHT = 450;
        primaryStage.setResizable(false);

        final Image titleScreen = new Image("/gui/menus/Title.png"); //title screen image
        final Image playButton = new Image("/gui/menus/PlayTransparent.png"); //the play button image
        final Image customizeButton = new Image("/gui/menus/Customize.png");
        final Image helpButton = new Image("/gui/menus/Help.png");

        final ImageView flashScreen_node = new ImageView();
        flashScreen_node.setImage(titleScreen); //set the image of the title screen

        final Button play_button = new Button();
        final ImageView play_button_node = new ImageView();
        final Button customize_button = new Button();
        final ImageView customize_button_node = new ImageView();
        final Button help_button = new Button();
        final ImageView help_button_node = new ImageView();


        play_button_node.setImage(playButton); //set the image of the play button
        customize_button_node.setImage(customizeButton);
        help_button_node.setImage(helpButton);

        play_button.setGraphic(play_button_node);
        play_button.setBackground(new Background(new BackgroundImage(new Image("/gui/menus/PlayBackgroundLong.png"),
                null, null, null, null)));
        customize_button.setGraphic(customize_button_node);
        customize_button.setBackground(new Background(new BackgroundImage(new Image("/gui/menus/PlayBackgroundLong.png"),
                null, null, null, null)));
        help_button.setGraphic(help_button_node);
        help_button.setBackground(new Background(new BackgroundImage(new Image("/gui/menus/PlayBackgroundLong.png"),
                null, null, null, null)));

        /*
         * create the container of those buttons
         */
        final VBox buttonContainer = new VBox(5);
        buttonContainer.setAlignment(Pos.TOP_CENTER);
        Insets buttonContainerPadding = new Insets(250, 1, 1, 1);
        buttonContainer.setPadding(buttonContainerPadding);
        buttonContainer.getChildren().addAll(play_button, customize_button, help_button);

        primaryStage.setTitle("Jago Tanks!");
        primaryStage.getIcons().add(titleScreen); //stage icon


        StackPane root = new StackPane();

        root.getChildren().addAll(flashScreen_node, buttonContainer); //add the title screen and button container to the stackpane
        Scene theScene = new Scene(root, CANVAS_WIDTH, CANVAS_HEIGHT, Color.BLACK);
        primaryStage.setScene(theScene);
        primaryStage.show();

        play_button.setOnAction(e -> {
            connection = new Connection("network");
            tankManager = new TankManager();
            primaryStage.setTitle("The Game");
            primaryStage.setMaxHeight(windowHeight);
            primaryStage.setMinHeight(windowHeight);
            primaryStage.setMaxWidth(windowWidth);
            primaryStage.setMinWidth(windowWidth);
            primaryStage.setResizable(false);

            connection.start();

            Text frameRate = new Text(10, 50, "This is a test");

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

                    for (TankBase tank : tankManager.getTanks())
                        tank.render();

                    lastTime = now;
                }
            };
            animationTimer.start();

            root.getChildren().add(frameRate);

            primaryStage.setScene(scene);
            primaryStage.show();

        });
    }
}
