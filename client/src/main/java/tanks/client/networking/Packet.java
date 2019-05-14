package tanks.client.networking;

import lombok.AllArgsConstructor;
import lombok.Data;
import tanks.client.exceptions.InvalidPacketException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@AllArgsConstructor
public class Packet {
    private String command;
    private String payload;

    private static final String commandPattern = "^((?:\\w+-\\w+)|(?:\\w+)(?: ))(.+)$";

    public static Packet parseMsgToPacket(String serverMessage) {
        Pattern pattern = Pattern.compile(commandPattern);

        Matcher matcher = pattern.matcher(serverMessage);

        if (!matcher.matches()) throw new InvalidPacketException(serverMessage);

        String cmd = matcher.group(1).trim();
        String payload = matcher.group(2).trim();

        return new Packet(cmd, payload);
    }
}
