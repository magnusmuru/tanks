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

public class Customize {
    public Button back_button;


    public Stage showTitle(Stage theStage) {

        final double CANVAS_WIDTH = 800;
        final double CANVAS_HEIGHT = 450;
        theStage.setResizable(false);

        final Image titleScreen = new Image("/gui/menus/CustomizeBackground.png"); //title screen image
        final Image backButton = new Image("/gui/menus/icons/Back.png"); //the play button image

        final ImageView flashScreen_node = new ImageView();
        flashScreen_node.setImage(titleScreen); //set the image of the title screen

        final Button back_button = new Button();
        final ImageView back_button_node = new ImageView();


        back_button_node.setImage(backButton); //set the image of the play button

        back_button.setGraphic(back_button_node);
        back_button.setBackground(new Background(new BackgroundImage(new Image("/gui/menus/icons/PlayBackgroundLong.png"),
                null, null, null, null)));
        /*
         * create the container of those buttons
         */
        final VBox buttonContainer = new VBox(5);
        buttonContainer.setAlignment(Pos.TOP_CENTER);
        Insets buttonContainerPadding = new Insets(400, 1, 1, 600);
        buttonContainer.setPadding(buttonContainerPadding);
        buttonContainer.getChildren().addAll(back_button);

        theStage.getIcons().add(titleScreen); //stage icon

        StackPane root = new StackPane();

        root.getChildren().addAll(flashScreen_node, buttonContainer); //add the title screen and button container to the stackpane
        Scene theScene = new Scene(root, CANVAS_WIDTH, CANVAS_HEIGHT, Color.BLACK);
        theStage.setScene(theScene);

        return theStage;
    }
}