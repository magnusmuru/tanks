package tanks.client.models;

import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;

public class TankCustomizer {
    @Getter
    @Setter
    public Image tankImage;
    @Getter
    @Setter
    public Image tankTurretImage;
    @Getter
    @Setter
    public boolean customized = false;
}
