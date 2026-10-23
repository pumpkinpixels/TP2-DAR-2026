package serveurpackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class serveur {
    public static void main(String[] args) {
        try {
            InetAddress adresseIP = InetAddress.getByName("127.0.0.1");
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(adresseIP, 1234));
            System.out.println("Je suis un serveur en attente la connexion d'un client");

            Socket socket = serverSocket.accept();
            System.out.println("un client est connecté");

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            while (true) {
                String a = in.readUTF();
                if (a.equals("q")) {
                    break;
                }
                String op = in.readUTF();
                String b = in.readUTF();

                double x = Double.parseDouble(a);
                double y = Double.parseDouble(b);
                String resultat;

                switch (op) {
                    case "+": resultat = String.valueOf(x + y); break;
                    case "-": resultat = String.valueOf(x - y); break;
                    case "*": resultat = String.valueOf(x * y); break;
                    case "/":
                        if (y == 0) resultat = "Erreur : division par zéro";
                        else resultat = String.valueOf(x / y);
                        break;
                    default: resultat = "Opérateur invalide";
                }

                out.writeUTF(resultat);
                out.flush();
            }

            socket.close();

            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
