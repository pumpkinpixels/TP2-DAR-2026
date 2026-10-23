package serveurpackage;

import operation.operation;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            while (true) {
                operation operation = (operation) in.readObject();

                char op = operation.getOperateur();
                if (op == 'q') {
                    break;
                }
                double x = operation.getOperande1();
                double y = operation.getOperande2();
                String resultat;

                switch (op) {
                    case '+': resultat = String.valueOf(x + y); break;
                    case '-': resultat = String.valueOf(x - y); break;
                    case '*': resultat = String.valueOf(x * y); break;
                    case '/':
                        if (y == 0) resultat = "Erreur : division par zéro";
                        else resultat = String.valueOf(x / y);
                        break;
                    default: resultat = "Opérateur invalide";
                }

                out.writeObject(resultat);
                out.flush();
            }

            socket.close();
            serverSocket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}