package serveurpackage;

import operation.operation;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class clientprocess extends Thread {
    private Socket socket;
    private int numeroClient;

    // Step 3: global counter shared by all threads
    private static int compteur = 0;

    private static synchronized int incrementerCompteur() {
        compteur++;
        return compteur;
    }

    public clientprocess(Socket socket, int numeroClient) {
        this.socket = socket;
        this.numeroClient = numeroClient;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // send the order number to the client
            out.writeObject(numeroClient);
            out.flush();

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

                int total = incrementerCompteur();
                System.out.println("Client n°" + numeroClient + " : " + x + " " + op + " " + y
                        + " | Total des opérations = " + total);

                out.writeObject(resultat);
                out.flush();
            }

            System.out.println("Client n°" + numeroClient + " déconnecté");
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Client n°" + numeroClient + " : connexion terminée");
        }
    }
}
