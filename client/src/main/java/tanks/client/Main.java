package tanks.client;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tanks.client.menu.Play;
import tanks.client.menu.Title;

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

        //Media m = new Media(Paths.get("src/main/resources/gui/menus/Main.mp3").toUri().toString());
        //MediaPlayer player = new MediaPlayer(m);
        //player.setAutoPlay(true);
        //player.setVolume(0.05);
        //MediaView mediaView = new MediaView(player);

        root = new Group();
        //root.getChildren().addAll(mediaView);

        scene = new Scene(root, windowWidth, windowHeight);
        Title title = new Title();
        title.showTitle(primaryStage).show();

        primaryStage.setTitle("Jago Tanks!");
        primaryStage.setWidth(windowWidth);
        primaryStage.setHeight(windowHeight);
    }

    @Override
    public void stop() {
        Play.connection.end();
    }

    public static Main getInstance() {
        return instance;
    }
}
