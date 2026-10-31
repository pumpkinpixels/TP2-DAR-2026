package activite3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class clientprocesstexte extends Thread {
    private Socket socket;
    private int numeroClient;

    public clientprocesstexte(Socket socket, int numeroClient) {
        this.socket = socket;
        this.numeroClient = numeroClient;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            out.println("Bienvenue ! Vous etes le client n°" + numeroClient);

            String ligne;
            while ((ligne = in.readLine()) != null) {
                System.out.println("Client n°" + numeroClient + " : " + ligne);
                out.println("Echo : " + ligne);
            }
            System.out.println("Client n°" + numeroClient + " déconnecté");
            socket.close();
        } catch (IOException e) {
            System.out.println("Client n°" + numeroClient + " : connexion terminée");
        }
    }
}
