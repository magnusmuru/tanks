package tanks.client.models;

import lombok.Getter;
import lombok.Setter;

public class TankCustomizer {
    @Getter @Setter private String tankImage;
    @Getter @Setter private String tankTurretImage;

    public TankCustomizer(String tankShell, String tankTurret) {
        this.tankImage = tankShell;
        this.tankTurretImage = tankTurret;
    }
}
