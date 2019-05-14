package tanks.client.networking;

import tanks.client.exceptions.InvalidHandshakeResponseException;
import tanks.client.menu.Play;
import tanks.client.models.Shot;
import tanks.client.models.TankBase;
import tanks.client.models.TankLocal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class Connection extends Thread {
    private Thread thread;
    private String threadName;

    private final String serverHandshakePattern = "^(?:server-handshake-id-)(\\d)$";

    private final String hostName = "192.168.51.194";
    private final int portNumber = 3000;

    boolean shouldContinue = true;

    private Socket socket = null;
    private DataInputStream dataIn;
    private DataOutputStream dataOut;

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

    public void sendShot() {
        try {
            TankLocal local = Play.tankManager.getTankLocal();
            dataOut.writeUTF(String.format("shot %s %s %s", local.getId(), local.getMouseX(), local.getMouseY()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
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

            Play.tankManager.addTankLocal(handShakePacket.getPayload());

            handShakePacket = null;

            String serverMessage;
            Packet serverPacket;
            while (shouldContinue) {
                serverMessage = dataIn.readUTF();

                serverPacket = Packet.parseMsgToPacket(serverMessage);

                switch (serverPacket.getCommand()) {
                    case "update":
                        String[] tanksArray = serverPacket.getPayload().split("\\|");
                        for (String tank : tanksArray) {
                            Play.tankManager.updateTank(tank.trim());
                            Play.tankManager.getTankLocal().tickShotCooldown();
                        }

                        dataOut.writeUTF("update completed");
                        break;
                    case "shots":
                        String[] shotsArray = serverPacket.getPayload().split("\\|");

                        Play.shotArrayList.clear();
                        for (String shotString : shotsArray) {
                            Play.shotArrayList.add(new Shot(shotString));
                        }

                        break;
                    case "coords":
                        TankBase tank = Play.tankManager.getTankLocal();

                        if (tank != null) {
                            dataOut.writeUTF(Play.tankManager.getTankData());
                        } else {
                            dataOut.writeUTF("puruks");
                        }
                        break;
                    case "stop":
                        end();
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
        try {
            dataOut.writeUTF("stopped");
            shouldContinue = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
