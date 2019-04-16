package tanks.server;

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
    private ServerSocket serverSocket;
    private TankManager tankManager;

    protected List<SingleConnection> singleConnections;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public ServerMain(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        //serverSocket.setSoTimeout(10000);

        singleConnections = new ArrayList<>();
        scheduler.scheduleAtFixedRate(this::serverTick, 0, 100, TimeUnit.MILLISECONDS);
    }

    public void run() {
        tankManager = new TankManager();

        while(true) {
            try {
                System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
                Socket client = serverSocket.accept();

                SingleConnection singleConnection = new SingleConnection(tankManager, client, this);
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
        for (SingleConnection singleConnection : singleConnections) {
            singleConnection.doTick();
        }

        for (SingleConnection singleConnection : singleConnections) {
            singleConnection.sendUpdate();
        }


        for (Tank tankS : tankManager.getTankSet()) {
            infoBuilder.append("|").append(tankS.getTankInfo());
        }

        infoBuilder = new StringBuilder();
        System.out.println("update " + infoBuilder.toString().replaceFirst("\\|", ""));
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
}