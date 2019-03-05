package tanks.client.menu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Title {
    public Button play_button;
    public Button customize_button;
    public Button help_button;


    public Stage showTitle(Stage theStage) {

        final double CANVAS_WIDTH = 800;
        final double CANVAS_HEIGHT = 450;
        theStage.setResizable(false);

        final Image titleScreen = new Image("/gui/menus/Title.png"); //title screen image
        final Image playButton = new Image("/gui/menus/icons/PlayTransparent.png"); //the play button image
        final Image customizeButton = new Image("/gui/menus/icons/Customize.png");
        final Image helpButton = new Image("/gui/menus/icons/Help.png");

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
        play_button.setBackground(new Background(new BackgroundImage(new Image("/gui/menus/icons/PlayBackgroundLong.png"),
                null, null, null, null)));
        customize_button.setGraphic(customize_button_node);
        customize_button.setBackground(new Background(new BackgroundImage(new Image("/gui/menus/icons/PlayBackgroundLong.png"),
                null, null, null, null)));
        help_button.setGraphic(help_button_node);
        help_button.setBackground(new Background(new BackgroundImage(new Image("/gui/menus/icons/PlayBackgroundLong.png"),
                null, null, null, null)));

        /*
         * create the container of those buttons
         */
        final VBox buttonContainer = new VBox(5);
        buttonContainer.setAlignment(Pos.TOP_CENTER);
        Insets buttonContainerPadding = new Insets(250, 1, 1, 1);
        buttonContainer.setPadding(buttonContainerPadding);
        buttonContainer.getChildren().addAll(play_button, customize_button, help_button);

        theStage.setTitle("Jago Tanks!");
        theStage.getIcons().add(titleScreen); //stage icon

        StackPane root = new StackPane();

        root.getChildren().addAll(flashScreen_node, buttonContainer); //add the title screen and button container to the stackpane
        Scene theScene = new Scene(root, CANVAS_WIDTH, CANVAS_HEIGHT, Color.BLACK);
        theStage.setScene(theScene);
        this.play_button = play_button;
        this.customize_button = customize_button;
        this.help_button = help_button;

        return theStage;
    }
}