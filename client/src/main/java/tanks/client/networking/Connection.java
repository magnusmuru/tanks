package tanks.client.networking;

import tanks.client.Main;
import tanks.client.Utils;
import tanks.client.exceptions.InvalidHandshakeResponseException;
import tanks.client.menu.Play;
import tanks.client.models.TankBase;
import tanks.client.models.TankLocal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

public class Connection extends Thread {
    private Thread thread;
    private String threadName;

    private final String serverHandshakePattern = "^(?:server-handshake-id-)(\\d)$";

    private final String hostName = "localhost";
    private final int portNumber = 3000;

    boolean shouldContinue = true;

    public Connection(String threadName) {
        this.threadName = threadName;
    }

    public void start() {
        System.out.println("Starting " +  threadName );
        if (thread == null) {
            thread = new Thread (this, threadName);
            thread.start();
        }
    }

    @Override
    public void run() {
        Socket socket = null;
        DataInputStream dataIn;
        DataOutputStream dataOut;

        try {
            socket = new Socket(hostName, portNumber);
            dataIn = new DataInputStream(socket.getInputStream());
            dataOut = new DataOutputStream(socket.getOutputStream());

            dataOut.writeUTF("client-handshake");
            String handShakeResponse = dataIn.readUTF();

            Packet handShakePacket = Packet.parseMsgToPacket(handShakeResponse);

            // Response must be "server-handshake {id}"
            if (!handShakePacket.getCommand().equals("server-handshake"))
                throw new InvalidHandshakeResponseException(handShakeResponse);

            Play.tankManager.addLocalTank(handShakePacket.getPayload());

            handShakePacket = null;

            String serverMessage = "";

            Packet serverPacket = null;
            while (shouldContinue) {
                serverMessage = dataIn.readUTF();

                serverPacket = Packet.parseMsgToPacket(serverMessage);

                switch (serverPacket.getCommand()) {
                    case "update":
                        String[] tanksArray = serverPacket.getPayload().split("\\|");
                        for (String tank : tanksArray) {
                            Play.tankManager.updateTank(tank.trim());
                        }

                        dataOut.writeUTF("update completed");
                        break;
                    case "coords":
                        String id = serverPacket.getPayload();
                        TankBase tank = Play.tankManager.getTankLocal();

                        if (tank != null) {
                            dataOut.writeUTF(Play.tankManager.getTankData());
                        } else {
                            dataOut.writeUTF("puruks");
                        }
                        break;
                    case "stop":
                        shouldContinue = false;
                        dataOut.writeUTF("stopped");
                        break;
                    default:
                        System.out.println("Server says: " + serverMessage);
                        break;
                }
                dataOut.flush();
            }

            dataOut.flush();
            dataOut.close();
            socket.close();
        } catch (EOFException e ) {
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void end() {
        shouldContinue = false;
    }
}
