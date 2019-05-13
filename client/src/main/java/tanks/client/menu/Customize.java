package tanks.client.menu;

import javafx.animation.KeyFrame;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
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
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import tanks.client.models.TankCustomizer;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Customize {
    public Button back_button;
    public Button save_button;
    public Button base_button;
    public Button corner_button;
    public Button corner_button2;
    @Getter
    @Setter
    public Image turret_image;
    @Getter
    @Setter
    public Image base_image;
    public int baseNumber = 2;

    public File absFile = new File("src/main/resources/gui/sprites/TankBases");
    private List<File> listOfBaseFiles = Arrays.asList(absFile.listFiles());
    public File turretFolder = new File("src/main/resources/gui/sprites/TankTurrets");
    private List<File> listOfTurretFiles = Arrays.asList(turretFolder.listFiles());


    public Stage showCustomize(Stage theStage, Title title, TankCustomizer tankCustomizer) {

        final double CANVAS_WIDTH = 1600;
        final double CANVAS_HEIGHT = 900;

        final Image titleScreen = new Image("/gui/menus/CustomizeBackground.png");
        final Image backButton = new Image("/gui/menus/icons/Back.png");
        final Image saveButton = new Image("/gui/menus/icons/Save.png");
        final Image savedButton = new Image("/gui/menus/icons/Saved.png");
        final Image cornerButton = new Image("/gui/menus/icons/Corner.png");
        final Image cornerButton2 = new Image("/gui/menus/icons/Corner2.png");


        final ImageView flashScreen_node = new ImageView();
        flashScreen_node.setImage(titleScreen); //set the image of the title screen

        Button back_button = new Button();
        ImageView back_button_node = new ImageView();
        back_button_node.setImage(backButton);
        back_button.setGraphic(back_button_node);
        back_button.setBackground(new Background(new BackgroundImage(new Image("/gui/menus/icons/PlayBackgroundLong.png"),
                null, null, null, null)));

        Button save_button = new Button();
        final ImageView save_button_node = new ImageView();
        save_button_node.setImage(saveButton);
        save_button.setGraphic(save_button_node);
        save_button.setBackground(new Background(new BackgroundImage(new Image("/gui/menus/icons/PlayBackgroundLong.png"),
                null, null, null, null)));

        ImageView base_button_node = new ImageView();
        base_button_node.setImage(new Image(listOfBaseFiles.get(baseNumber % listOfBaseFiles.size()).toURI().toString()));

        ImageView base_turret_node = new ImageView();
        base_turret_node.setImage(new Image(listOfTurretFiles.get(baseNumber % listOfTurretFiles.size()).toURI().toString()));

        final Button corner_button = new Button();
        final ImageView corner_button_node = new ImageView();
        corner_button_node.setImage(cornerButton);
        corner_button.setGraphic(corner_button_node);
        corner_button.setBackground(new Background(new BackgroundImage(new Image("/gui/menus/icons/transparent.png"),
                BackgroundRepeat.ROUND, null, null, BackgroundSize.DEFAULT)));

        final Button corner_button2 = new Button();
        final ImageView corner_button_node2 = new ImageView();
        corner_button_node2.setImage(cornerButton2);
        corner_button2.setGraphic(corner_button_node2);
        corner_button2.setBackground(new Background(new BackgroundImage(new Image("/gui/menus/icons/transparent.png"),
                null, null, null, null)));

        final Button corner_button3 = new Button();
        final ImageView corner_button_node3 = new ImageView();
        corner_button_node3.setImage(cornerButton);
        corner_button3.setGraphic(corner_button_node3);
        corner_button3.setBackground(new Background(new BackgroundImage(new Image("/gui/menus/icons/transparent.png"),
                BackgroundRepeat.ROUND, null, null, BackgroundSize.DEFAULT)));

        final Button corner_button4 = new Button();
        final ImageView corner_button_node4 = new ImageView();
        corner_button_node4.setImage(cornerButton2);
        corner_button4.setGraphic(corner_button_node4);
        corner_button4.setBackground(new Background(new BackgroundImage(new Image("/gui/menus/icons/transparent.png"),
                null, null, null, null)));

        HBox tankBaseContainer = new HBox(20);
        tankBaseContainer.setAlignment(Pos.TOP_CENTER);
        tankBaseContainer.getChildren().addAll(corner_button2, base_button_node, corner_button);

        HBox tankTurretContainer = new HBox(20);
        tankTurretContainer.setAlignment(Pos.TOP_CENTER);
        tankTurretContainer.getChildren().addAll(corner_button4, base_turret_node, corner_button3);

        Text textBase = new Text("Select a Tank base!");
        textBase.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, FontPosture.REGULAR, 15));
        textBase.setFill(Color.WHITE);

        Text textTurret = new Text("Select a Tank turret!");
        textTurret.setFont(Font.font("Trebuchet MS", FontWeight.BOLD, FontPosture.REGULAR, 15));
        textTurret.setFill(Color.WHITE);

        VBox customizeContainer = new VBox(20);
        customizeContainer.setAlignment(Pos.TOP_CENTER);
        Insets customizeContainerPadding = new Insets(200, 0, 0, 650);
        customizeContainer.setPadding(customizeContainerPadding);
        customizeContainer.getChildren().addAll(textBase, tankBaseContainer, textTurret, tankTurretContainer);

        HBox buttonContainer = new HBox(5);
        buttonContainer.setAlignment(Pos.TOP_CENTER);
        Insets buttonContainerPadding = new Insets(750, 0, 0, 1175);
        buttonContainer.setPadding(buttonContainerPadding);
        buttonContainer.getChildren().addAll(save_button, back_button);

        Group buttonGroup = new Group(buttonContainer, customizeContainer);


        StackPane root = new StackPane();
        root.getChildren().addAll(flashScreen_node, buttonGroup);
        Scene theScene = new Scene(root, CANVAS_WIDTH, CANVAS_HEIGHT);
        theStage.setScene(theScene);
        this.save_button = save_button;
        this.back_button = back_button;
        this.corner_button = corner_button;
        this.corner_button2 = corner_button2;

        back_button.setOnAction(event -> {
            title.showTitle(theStage).show();
        });

        corner_button.setOnAction(actionEvent -> {
            baseNumber++;
            base_button_node.setImage(new Image(listOfBaseFiles.get(baseNumber % listOfBaseFiles.size()).toURI().toString()));
        });

        corner_button2.setOnAction(actionEvent -> {
            baseNumber--;
            base_button_node.setImage(new Image(listOfBaseFiles.get(baseNumber % listOfBaseFiles.size()).toURI().toString()));
        });

        save_button.setOnAction(event -> {
            tankCustomizer.setTankImage(listOfBaseFiles.get(baseNumber % listOfBaseFiles.size()).toURI().toString());
            tankCustomizer.setTankTurretImage(listOfTurretFiles.get(baseNumber % listOfTurretFiles.size()).toURI().toString());
            tankCustomizer.setCustomized(true);
            save_button_node.setImage(savedButton);

            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            save_button_node.setImage(saveButton);
                        }
                    },
                    3000
            );
        });

        return theStage;
    }
}