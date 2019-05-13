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
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tanks.client.menu.Customize;
import tanks.client.models.TankBase;
import tanks.client.models.TankLocal;
import tanks.client.networking.Connection;
import tanks.client.networking.TankManager;
import tanks.client.menu.Title;
import javafx.scene.media.Media;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Main extends Application {
    private static Main instance;

    private static int windowWidth = 1600;
    private static int windowHeight = 900;

    public static Group root;
    public static Scene scene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        instance = this;

        Media m = new Media(Paths.get("src/main/resources/gui/menus/Main.mp3").toUri().toString());
        MediaPlayer player = new MediaPlayer(m);
        player.setAutoPlay(true);
        player.setVolume(0.05);
        MediaView mediaView = new MediaView(player);

        root = new Group();
        root.getChildren().addAll(mediaView);

        scene = new Scene(root, windowWidth, windowHeight);
        Title title = new Title();
        title.showTitle(primaryStage).show();

        primaryStage.setTitle("Jago Tanks!");
        primaryStage.setWidth(windowWidth);
        primaryStage.setHeight(windowHeight);
    }

    @Override
    public void stop(){
        connection.end();
    }

    public static Main getInstance() {
        return instance;
    }
}
