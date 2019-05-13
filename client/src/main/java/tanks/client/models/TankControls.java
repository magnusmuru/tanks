package tanks.client.models;


import com.sun.glass.events.KeyEvent;
import com.sun.glass.events.MouseEvent;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;


public class TankControls {
    @Getter
    public StringProperty up = new SimpleStringProperty("UP");
    //@Getter
    //@Setter
    //public String right = KeyEvent.getTypeString(KeyEvent.VK_RIGHT);
    //@Getter
    //@Setter
    // public String down = KeyEvent.getTypeString(KeyEvent.VK_DOWN);
    //@Getter
    //@Setter
    //public String left = KeyEvent.getTypeString(KeyEvent.VK_LEFT);
    //@Getter
    // @Setter
    //public String fire = KeyEvent.getTypeString(MouseEvent.BUTTON_LEFT);
}
