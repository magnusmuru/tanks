package tanks.client.exceptions;

public class InvalidHandshakeResponseException extends RuntimeException {
    public InvalidHandshakeResponseException(String msg) {
        super(msg);
    }
}
