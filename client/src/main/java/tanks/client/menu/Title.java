package tanks.client.menu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import tanks.client.models.TankControls;
import tanks.client.models.TankCustomizer;

import java.nio.file.Paths;

public class Title {
    public Button play_button;
    public Button customize_button;
    public Button help_button;
    public Button control_button;


    public Stage showTitle(Stage theStage) {
        TankControls tankControls = new TankControls();
        TankCustomizer tankCustomizer = new TankCustomizer();

        final double CANVAS_WIDTH = 1600;
        final double CANVAS_HEIGHT = 900;

        final Image titleScreen = new Image("/gui/menus/Title.png"); //title screen image
        final Image playButton = new Image("/gui/menus/icons/PlayTransparent.png"); //the play button image
        final Image customizeButton = new Image("/gui/menus/icons/Customize.png");
        final Image helpButton = new Image("/gui/menus/icons/Help.png");
        final Image controlButton = new Image("/gui/menus/icons/Controls.png");
        final Image logo = new Image("/gui/menus/Logo.png");

        final ImageView flashScreen_node = new ImageView();
        flashScreen_node.setImage(titleScreen); //set the image of the title screen

        final Button play_button = new Button();
        final ImageView play_button_node = new ImageView();
        final Button customize_button = new Button();
        final ImageView customize_button_node = new ImageView();
        final Button help_button = new Button();
        final ImageView help_button_node = new ImageView();
        final Button control_button = new Button();
        final ImageView control_button_node = new ImageView();
        final ImageView logo_node = new ImageView(logo);


        play_button_node.setImage(playButton); //set the image of the play button
        customize_button_node.setImage(customizeButton);
        help_button_node.setImage(helpButton);
        control_button_node.setImage(controlButton);

        play_button.setGraphic(play_button_node);
        play_button.setBackground(new Background(new BackgroundImage(new Image("/gui/menus/icons/PlayBackgroundLong.png"),
                null, null, null, null)));
        customize_button.setGraphic(customize_button_node);
        customize_button.setBackground(new Background(new BackgroundImage(new Image("/gui/menus/icons/PlayBackgroundLong.png"),
                null, null, null, null)));
        help_button.setGraphic(help_button_node);
        help_button.setBackground(new Background(new BackgroundImage(new Image("/gui/menus/icons/PlayBackgroundLong.png"),
                null, null, null, null)));
        control_button.setGraphic(control_button_node);
        control_button.setBackground(new Background(new BackgroundImage(new Image("/gui/menus/icons/PlayBackgroundLong.png"),
                null, null, null, null)));

        /*
         * create the container of those buttons
         */
        final VBox buttonContainer = new VBox(5);
        buttonContainer.setAlignment(Pos.TOP_CENTER);
        Insets buttonContainerPadding = new Insets(225, 1, 1, 1);
        buttonContainer.setPadding(buttonContainerPadding);
        buttonContainer.getChildren().addAll(play_button, customize_button, control_button, help_button);

        VBox logoContainer = new VBox(0);
        logoContainer.setAlignment(Pos.TOP_CENTER);
        logoContainer.setPadding(new Insets(25, 1, 1, 1));
        logoContainer.getChildren().addAll(logo_node);

        theStage.setTitle("Jago Tanks!");
        theStage.getIcons().add(new Image("/gui/menus/Jago Tanks.png")); //stage icon

        StackPane root = new StackPane();


        root.getChildren().addAll(flashScreen_node, logoContainer, buttonContainer);
        Scene theScene = new Scene(root, CANVAS_WIDTH, CANVAS_HEIGHT, Color.BLACK);
        theStage.setScene(theScene);
        this.play_button = play_button;
        this.customize_button = customize_button;
        this.help_button = help_button;
        this.control_button = control_button;

        customize_button.setOnAction(e -> {
            Customize customizeMenu = new Customize();
            customizeMenu.showCustomize(theStage, this, tankCustomizer).show();
        });

        help_button.setOnAction(event -> {
            Help helpMenu = new Help();
            helpMenu.showHelp(theStage, this).show();
        });

        control_button.setOnAction(event -> {
            Controls controlMenu = new Controls();
            controlMenu.showControls(theStage, this, tankControls).show();
        });

        play_button.setOnAction(e -> {
            Play playMenu = new Play();
            playMenu.showPlay(theStage).show();
        });

        return theStage;
    }
}