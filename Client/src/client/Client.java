package client;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    public static void main(String[] args) {
        try{
            System.out.println("Estabelecendo conexão...");
            Socket socket = new Socket("localhost", 5555);
            System.out.println("Conexão estabelecida");

            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

            System.out.println("Enviando mensagem...");
            String msg = "HELLO";
            output.writeUTF(msg);
            output.flush();

            System.out.println("Mensagem " + msg + " enviada.");

            msg = input.readUTF();
            System.out.println("Resposta: " + msg);

            input.close();
            output.close();
            socket.close();
        }
        catch(IOException ex) {
            System.out.println("Erro no cliente: " + ex);
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }
}
