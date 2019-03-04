package tanks.client.exceptions;

public class InvalidPacketException extends RuntimeException {
    public InvalidPacketException(String msg) {
        super(msg);
    }
}
