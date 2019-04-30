package tanks.client.models;

import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;

public class TankCustomizer {
    @Getter
    @Setter
    public String tankImage;
    @Getter
    @Setter
    public String tankTurretImage;
    @Getter
    @Setter
    public boolean customized = false;
}
