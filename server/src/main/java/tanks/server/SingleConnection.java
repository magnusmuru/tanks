package tanks.server;

import tanks.server.persistence.Tank;
import tanks.server.persistence.TankManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SingleConnection {
    private Socket clientSocket;

    private DataInputStream dataIn;
    private DataOutputStream dataOut;

    private TankManager tankManager;
    private Tank tank;

    private int connectionTankId;
    private int count;

    public SingleConnection(TankManager tankManager, Socket clientSocket) {
        this.tankManager = tankManager;
        this.clientSocket = clientSocket;

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doTick() {
        String in;
        try {
            dataOut.writeUTF("coords - 2");
            in = dataIn.readUTF();
            System.out.println("coords: " + in);

            System.out.println();
            tank.updateVariables(in);
            tank.calculateTank();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendUpdate() {
        String in;
        try {
            String tankInfo = tank.getTankInfo();
            System.out.println("tankinfo: " + tankInfo);
            dataOut.writeUTF("update - " + tankInfo);
            in = dataIn.readUTF();
            System.out.println(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
