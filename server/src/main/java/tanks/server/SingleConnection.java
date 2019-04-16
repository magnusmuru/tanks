package tanks.server;

import tanks.server.persistence.Tank;
import tanks.server.persistence.TankManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

public class SingleConnection {
    private Socket clientSocket;

    private DataInputStream dataIn;
    private DataOutputStream dataOut;

    private TankManager tankManager;
    private Tank tank;

    private int connectionTankId;
    private int count;

    private ServerMain serverInstance;

    private StringBuilder infoBuilder = new StringBuilder();

    public SingleConnection(TankManager tankManager, Socket clientSocket, ServerMain serverInstance) {
        this.tankManager = tankManager;
        this.clientSocket = clientSocket;
        this.serverInstance = serverInstance;

        this.count = 0;
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
        }

        return false;
    }

    public void stop() {
        try {
            dataOut.flush();

            clientSocket.close();
            serverInstance.singleConnections.remove(this);
            tankManager.removeTank(tank);

            System.out.println(String.format("Disconnected %s, tank id: %s", clientSocket.getInetAddress(), tank.getId()));
        } catch (IOException e) {
            System.out.println("SingleConnection : 76");
        }
    }

    public void doTick() {
        String in;
        try {
            dataOut.writeUTF("coords x");
            in = dataIn.readUTF();
            System.out.println(String.format("ID: %s, data: %s", tank.getId(), in));
            tank.updateVariables(in);
            tank.calculateTank();
        } catch (SocketException | EOFException se) {
            stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendUpdate() {
        String in;
        try {
            for (Tank tankS : tankManager.getTankSet()) {
                infoBuilder.append("|").append(tankS.getTankInfo());
            }

            String outStr = "update " + infoBuilder.toString().replaceFirst("\\|", "");
            dataOut.writeUTF(outStr);

            infoBuilder = new StringBuilder();

            in = dataIn.readUTF();
        } catch (SocketException | EOFException se) {
            stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
