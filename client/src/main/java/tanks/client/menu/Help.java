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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Help {
    public Button back_button;

    public Stage showHelp(Stage theStage, Title title) {

        final double CANVAS_WIDTH = 1600;
        final double CANVAS_HEIGHT = 900;


        final Image backButton = new Image("/gui/menus/icons/Back.png");
        final Image titleScreen = new Image("/gui/menus/CustomizeBackground.png");
        final ImageView flashScreen_node = new ImageView();
        flashScreen_node.setImage(titleScreen); //set the image of the title screen

        final Button back_button = new Button();
        final ImageView back_button_node = new ImageView();
        back_button_node.setImage(backButton);
        back_button.setGraphic(back_button_node);
        back_button.setBackground(new Background(new BackgroundImage(new Image("/gui/menus/icons/PlayBackgroundLong.png"),
                null, null, null, null)));

        Text text = new Text();
        text.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, FontPosture.REGULAR, 15));
        text.setTextAlignment(TextAlignment.CENTER);
        text.setText("You are a Jago Tank driver. Your job is to destroy all the other tanks to win! \n \n Customize your tank to your liking, so you spread even more fear to your enemy. \n" +
                "Change the controls to best fit your destructive rampage! \n \n To play, start the server using the server .jar file \n \n After that just hit play and wage war among The Dune!");
        text.setFill(Color.WHITE);
        Group group = new Group(text);


        final HBox buttonContainer = new HBox(5);
        buttonContainer.setAlignment(Pos.TOP_CENTER);
        Insets buttonContainerPadding = new Insets(790, 0, 0, 1300);
        buttonContainer.setPadding(buttonContainerPadding);
        buttonContainer.getChildren().addAll(back_button);


        StackPane root = new StackPane();
        root.getChildren().addAll(flashScreen_node, buttonContainer, group);
        Scene theScene = new Scene(root, CANVAS_WIDTH, CANVAS_HEIGHT, Color.BLACK);
        theStage.setScene(theScene);
        this.back_button = back_button;

        back_button.setOnAction(event -> {
            title.showTitle(theStage).show();
        });

        return theStage;
    }
}
