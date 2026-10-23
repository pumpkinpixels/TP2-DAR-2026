package clientpackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            Scanner sc = new Scanner(System.in);


            Pattern modele = Pattern.compile(
                    "^\\s*(-?\\d+(?:\\.\\d+)?)\\s*([+\\-*/])\\s*(-?\\d+(?:\\.\\d+)?)\\s*$");

            while (true) {
                System.out.print("Entrez une opération (ex: 55 * 25) ou q pour quitter : ");
                String saisie = sc.nextLine();

                if (saisie.trim().equalsIgnoreCase("q")) {
                    out.writeUTF("q");
                    out.flush();
                    break;
                }

                Matcher m = modele.matcher(saisie);
                if (!m.matches()) {
                    System.out.println("Syntaxe invalide. Exemple : 55 * 25");
                    continue;
                }

                out.writeUTF(m.group(1));
                out.writeUTF(m.group(2));
                out.writeUTF(m.group(3));
                out.flush();

                String resultat = in.readUTF();
                System.out.println("Résultat : " + resultat);
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}






