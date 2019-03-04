package tanks.server.networking;

public class InvalidPacketException extends RuntimeException {
    public InvalidPacketException(String msg) {
        super(msg);
    }
}
