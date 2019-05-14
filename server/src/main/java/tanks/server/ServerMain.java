package tanks.server;

import lombok.Getter;
import tanks.server.persistence.ShotManager;
import tanks.server.persistence.Tank;
import tanks.server.persistence.TankManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerMain extends Thread {
    private static ServerMain instance;
    private ServerSocket serverSocket;
    @Getter private TankManager tankManager;
    @Getter private ShotManager shotManager;

    protected List<SingleConnection> singleConnections;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);

    public ServerMain(int port) throws IOException {
        instance = this;
        serverSocket = new ServerSocket(port);
        //serverSocket.setSoTimeout(10000);

        singleConnections = new ArrayList<>();
        scheduler.scheduleAtFixedRate(this::serverTick, 0, 30, TimeUnit.MILLISECONDS);
    }

    public void run() {
        tankManager = new TankManager();
        shotManager = new ShotManager();

        while(true) {
            try {
                System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
                Socket client = serverSocket.accept();

                SingleConnection singleConnection = new SingleConnection(tankManager, shotManager, client, this);
                boolean hsSuccess = singleConnection.tryHandShake();

                if (hsSuccess) singleConnections.add(singleConnection);

            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    StringBuilder infoBuilder;
    private void serverTick() {
        infoBuilder = new StringBuilder();

        for (SingleConnection singleConnection : singleConnections) {
            singleConnection.doTick();
        }

        for (SingleConnection singleConnection : singleConnections) {
            singleConnection.sendUpdate();
            singleConnection.sendShots();
        }
    }

    public static void main(String [] args) {
        int port = 3000;
        try {
            Thread t = new ServerMain(port);
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ServerMain getInstance() {
        return instance;
    }
}