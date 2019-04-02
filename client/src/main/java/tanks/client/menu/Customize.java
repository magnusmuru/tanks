package tanks.client.menu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import lombok.Getter;
import lombok.Setter;
import tanks.client.menu.Title;

public class Customize {
    public Button back_button;
    public Button save_button;
    @Getter @Setter public Image turret_image;
    @Getter @Setter public Image base_image;

    public Stage showTitle(Stage theStage, Title title) {

        final double CANVAS_WIDTH = 800;
        final double CANVAS_HEIGHT = 450;
        theStage.setResizable(false);

        final Image titleScreen = new Image("/gui/menus/CustomizeBackground.png"); //title screen image
        final Image backButton = new Image("/gui/menus/icons/Back.png");
        final Image saveButton = new Image("/gui/menus/icons/Save.png");

        final ImageView flashScreen_node = new ImageView();
        flashScreen_node.setImage(titleScreen); //set the image of the title screen

        final Button back_button = new Button();
        final ImageView back_button_node = new ImageView();
        back_button_node.setImage(backButton); //set the image of the play button
        back_button.setGraphic(back_button_node);
        back_button.setBackground(new Background(new BackgroundImage(new Image("/gui/menus/icons/PlayBackgroundLong.png"),
                null, null, null, null)));

        final Button save_button = new Button();
        final ImageView save_button_node = new ImageView();
        save_button_node.setImage(saveButton); //set the image of the play button
        save_button.setGraphic(save_button_node);
        save_button.setBackground(new Background(new BackgroundImage(new Image("/gui/menus/icons/PlayBackgroundLong.png"),
                null, null, null, null)));

        /*
         * create the container of those buttons
         */
        final HBox buttonContainer = new HBox(5);
        buttonContainer.setAlignment(Pos.TOP_CENTER);
        Insets buttonContainerPadding = new Insets(400, 1, 1, 500);
        buttonContainer.setPadding(buttonContainerPadding);
        buttonContainer.getChildren().addAll(save_button, back_button);


        StackPane root = new StackPane();

        root.getChildren().addAll(flashScreen_node, buttonContainer); //add the title screen and button container to the stackpane
        Scene theScene = new Scene(root, CANVAS_WIDTH, CANVAS_HEIGHT, Color.BLACK);
        theStage.setScene(theScene);
        this.save_button = save_button;
        this.back_button = back_button;

        back_button.setOnAction(event -> {
            title.showTitle(theStage).show();
        });
        save_button.setOnAction(event -> {
        });

        return theStage;
    }
}