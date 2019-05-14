package tanks.server;

import tanks.server.persistence.Shot;
import tanks.server.persistence.ShotManager;
import tanks.server.persistence.Tank;
import tanks.server.persistence.TankManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class SingleConnection {
    private Socket clientSocket;

    private DataInputStream dataIn;
    private DataOutputStream dataOut;

    private ShotManager shotManager;
    private TankManager tankManager;
    private Tank tank;

    private ServerMain serverInstance;
    private StringBuilder infoBuilder;

    public SingleConnection(TankManager tankManager, ShotManager shotManager, Socket clientSocket, ServerMain serverInstance) {
        this.shotManager = shotManager;
        this.tankManager = tankManager;
        this.clientSocket = clientSocket;
        this.serverInstance = serverInstance;
    }

    public boolean tryHandShake() {
        try {
            dataIn = new DataInputStream(clientSocket.getInputStream());
            dataOut = new DataOutputStream(clientSocket.getOutputStream());

            String clientHandShake = dataIn.readUTF();

            if (!clientHandShake.equals("client-handshake")) {
                throw new RuntimeException("Invalid handshake message: " + clientHandShake);
            }

            tank = TankManager.createTank();

            if (!tankManager.addTank(tank)) {
                throw new RuntimeException("Unable to add tank to tankmanager. ID: " + tank.getId());
            }

            dataOut.writeUTF("server-handshake " + tank.getId());

            System.out.println(String.format("Connected %s, tank id: %s", clientSocket.getInetAddress(), tank.getId()));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void stop() {
        try {
            clientSocket.close();
            dataIn.close();
            dataOut.close();
            serverInstance.singleConnections.remove(this);
            tankManager.removeTank(tank);

            System.out.println(String.format("Disconnected %s, tank id: %s", clientSocket.getInetAddress(), tank.getId()));
        } catch (IOException e) {
            System.out.println("SingleConnection: 74");
        }
    }

    public void doTick() {
        String in;
        try {
            dataOut.writeUTF("coords x");
            in = dataIn.readUTF();

            if (in.equals("stopped")) {
                stop();
            } else {
                shotManager.tick();
                tank.updateVariables(in);
                tank.calculateTank();
            }
        } catch (SocketException | EOFException se) {
            stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendUpdate() {
        infoBuilder = new StringBuilder();

        try {
            for (Tank tankS : tankManager.getTankSet()) {
                infoBuilder.append("|").append(tankS.getTankInfo());
            }

            String outStr = "update " + infoBuilder.toString().replaceFirst("\\|", "");
            dataOut.writeUTF(outStr);

            String in = dataIn.readUTF();
        } catch (SocketException | EOFException se) {
            stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendShots() {
        infoBuilder = new StringBuilder();

        try {
            for (Shot shot : shotManager.getShots()) {
                infoBuilder.append("|").append(shot.getShotData());
            }

            String outStr = "shots " + infoBuilder.toString().replaceFirst("\\|", "");

            if (shotManager.getShots().size() > 0) {
                System.out.println(outStr);
                dataOut.writeUTF(outStr);
            }
        } catch (SocketException | EOFException se) {
            stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
