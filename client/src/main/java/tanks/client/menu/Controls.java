package tanks.client.menu;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tanks.client.models.TankControls;

import java.awt.event.KeyListener;
import java.util.EventListener;

public class Controls {
    public Button back_button;

    public Stage showControls(Stage theStage, Title title, TankControls tankControls) {

        final double CANVAS_WIDTH = 1600;
        final double CANVAS_HEIGHT = 900;
        theStage.setResizable(false);

        final Image backButton = new Image("/gui/menus/icons/Back.png");
        final Image background = new Image("/gui/menus/CustomizeBackground.png");
        final ImageView flashScreen_node = new ImageView();
        flashScreen_node.setImage(background);

        final Button back_button = new Button();
        final ImageView back_button_node = new ImageView();
        back_button_node.setImage(backButton);
        back_button.setGraphic(back_button_node);
        back_button.setBackground(new Background(new BackgroundImage(new Image("/gui/menus/icons/PlayBackgroundLong.png"),
                null, null, null, null)));

        final HBox buttonContainer = new HBox(5);
        buttonContainer.setAlignment(Pos.TOP_CENTER);
        Insets buttonContainerPadding = new Insets(800, 0, 0, 1400);
        buttonContainer.setPadding(buttonContainerPadding);
        buttonContainer.getChildren().addAll(back_button);

        Text textUp = new Text("Move forwards");
        textUp.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, FontPosture.REGULAR, 15));
        textUp.setFill(Color.WHITE);

        Text textDown = new Text("Move back");
        textDown.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, FontPosture.REGULAR, 15));
        textDown.setFill(Color.WHITE);

        Text textLeft = new Text("Turn left");
        textLeft.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, FontPosture.REGULAR, 15));
        textLeft.setFill(Color.WHITE);

        Text textRight = new Text("Turn right");
        textRight.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, FontPosture.REGULAR, 15));
        textRight.setFill(Color.WHITE);

        Text textFire = new Text("Fire!");
        textFire.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, FontPosture.REGULAR, 15));
        textFire.setFill(Color.WHITE);

        Button forwards = new Button();
        forwards.setBackground(new Background(new BackgroundImage(new Image("/gui/menus/icons/PlayBackgroundLong.png"),
                null, null, null, null)));
        forwards.setTextFill(Color.WHITE);
        forwards.textProperty().bind(tankControls.getUp());
        forwards.setOnAction(event -> {
            EventHandler<KeyEvent> filter = new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    System.out.println(event.getText());
                    tankControls.getUp().setValue(event.getText());
                    event.consume();
                    forwards.removeEventFilter(KeyEvent.KEY_TYPED, this);
                }
            };

            forwards.addEventFilter(KeyEvent.KEY_TYPED, filter);
        });

        Button backwards = new Button();
        backwards.setBackground(new Background(new BackgroundImage(new Image("/gui/menus/icons/PlayBackgroundLong.png"),
                null, null, null, null)));
        backwards.setText("Key_DOWN");
        backwards.setTextFill(Color.WHITE);
        backwards.setOnAction(event -> {
            backwards.setText("Press any key");
        });

        Button left = new Button();
        left.setBackground(new Background(new BackgroundImage(new Image("/gui/menus/icons/PlayBackgroundLong.png"),
                null, null, null, null)));
        left.setText("Key_LEFT");
        left.setTextFill(Color.WHITE);
        left.setOnAction(event -> {
            left.setText("Press any key");
        });

        Button right = new Button();
        right.setBackground(new Background(new BackgroundImage(new Image("/gui/menus/icons/PlayBackgroundLong.png"),
                null, null, null, null)));
        right.setText("Key_LEFT");
        right.setTextFill(Color.WHITE);
        right.setOnAction(event -> {
            right.setText("Press any key");
        });

        Button fire = new Button();
        fire.setBackground(new Background(new BackgroundImage(new Image("/gui/menus/icons/PlayBackgroundLong.png"),
                null, null, null, null)));
        fire.setText("Mouse_LEFT");
        fire.setTextFill(Color.WHITE);
        fire.setOnAction(event -> {
            fire.setText("Press any key");
        });

        back_button.setOnAction(event -> {
            title.showTitle(theStage).show();
        });

        VBox buttons = new VBox(5);
        buttons.setAlignment(Pos.TOP_CENTER);
        Insets customizeContainerPadding = new Insets(200, 0, 0, 750);
        buttons.setPadding(customizeContainerPadding);
        buttons.getChildren().addAll(textUp, forwards, textDown, backwards, textLeft, left, textRight, right, textFire, fire);

        Group buttonGroup = new Group(flashScreen_node, buttonContainer, buttons);

        Scene theScene = new Scene(buttonGroup, CANVAS_WIDTH, CANVAS_HEIGHT, Color.BLACK);
        theStage.setScene(theScene);
        this.back_button = back_button;

        return theStage;
    }
}
