package clientpackage;

import operation.operation;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class client {
    public static void main(String[] args) {
        try {
            System.out.println("Je suis un client pas encore connecté…");
            InetSocketAddress adresseServeur = new InetSocketAddress("127.0.0.1", 1234);
            Socket socket = new Socket();
            socket.connect(adresseServeur);
            System.out.println("je suis un client connecté");

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Scanner sc = new Scanner(System.in);

            Pattern modele = Pattern.compile(
                    "^\\s*(-?\\d+(?:\\.\\d+)?)\\s*([+\\-*/])\\s*(-?\\d+(?:\\.\\d+)?)\\s*$");

            while (true) {
                System.out.print("Entrez une opération (ex: 55 * 25) ou q pour quitter : ");
                String saisie = sc.nextLine();

                if (saisie.trim().equalsIgnoreCase("q")) {
                    out.writeObject(new operation(0, 'q', 0));
                    out.flush();
                    break;
                }

                Matcher m = modele.matcher(saisie);
                if (!m.matches()) {
                    System.out.println("Syntaxe invalide. Exemple : 55 * 25");
                    continue;
                }

                double a = Double.parseDouble(m.group(1));
                char op = m.group(2).charAt(0);
                double b = Double.parseDouble(m.group(3));

                operation operation = new operation(a, op, b);
                out.writeObject(operation);
                out.flush();

                String resultat = (String) in.readObject();
                System.out.println("Résultat : " + resultat);
            }

            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}