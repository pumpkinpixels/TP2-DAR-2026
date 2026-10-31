package activite3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class serveurtexte {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Serveur texte en attente de clients...");

            int numeroClient = 0;
            while (true) {
                Socket socket = serverSocket.accept();
                numeroClient++;
                System.out.println("Client n°" + numeroClient + " connecté, IP : "
                        + socket.getRemoteSocketAddress());
                new clientprocesstexte(socket, numeroClient).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}