package tanks.client.utils;

import lombok.Getter;
import lombok.Setter;

public class LongValue {
    @Getter @Setter private long value;

    /**
     * A helper class to store time in Main.AnimationTimer.handle()
     *
     * @param newValue
     */
    public LongValue(long newValue) {
        value = newValue;
    }
}