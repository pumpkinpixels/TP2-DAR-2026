package serveurpackage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
public class serveur {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Je suis un serveur en attente la connexion d'un client");

            int numeroClient = 0;
            while (true) {
                Socket socket = serverSocket.accept();
                numeroClient++;
                System.out.println("Client n°" + numeroClient + " connecté, IP : "
                        + socket.getRemoteSocketAddress());
                new clientprocess(socket, numeroClient).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}