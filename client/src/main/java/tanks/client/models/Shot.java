package tanks.client.models;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Shot extends Circle{
    public Shot(String dataString) {
        String[] dataSplit = dataString.split(" ");
        setCenterX(Double.valueOf(dataSplit[0]) + 20);
        setCenterY(Double.valueOf(dataSplit[1]) + 20);

        setFill(Paint.valueOf("ff2121"));
        setStroke(Paint.valueOf("#000000"));
        setRadius(3);
        setStrokeWidth(1);
    }
}
