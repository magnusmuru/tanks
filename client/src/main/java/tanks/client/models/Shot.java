package tanks.client.models;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Shot extends Circle{
    public Shot(String dataString) {
        String[] dataSplit = dataString.split(" ");
        setCenterX(Integer.valueOf(dataSplit[0]));
        setCenterY(Integer.valueOf(dataSplit[1]));

        setFill(Paint.valueOf("ff2121"));
        setRadius(3);
        setStrokeWidth(1);
    }
}
