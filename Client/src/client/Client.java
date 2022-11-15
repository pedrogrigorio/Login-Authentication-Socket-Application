package client;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import util.Message;
import util.Status;

public class Client {
    public static void main(String[] args) {
        try{
            System.out.println("Estabelecendo conexão...");
            Socket socket = new Socket("localhost", 5555);
            System.out.println("Conexão estabelecida");

            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

            Message msg = new Message("Hello");
            msg.setStatus(Status.REQUEST);
            msg.setParam("nome", "Pedro");
            msg.setParam("sobrenome", "Grigorio");

            System.out.println("Enviando mensagem...");
            output.writeObject(msg);
            output.flush();

            System.out.println("Mensagem " + msg + "enviada.");

            msg = (Message) input.readObject();
            System.out.println("Resposta: " + msg);
            
            if(msg.getStatus() == Status.OK){
                String reply = (String) msg.getParam("mensagem");
                System.out.println("Resposta: " + reply);
            }
            else{
                System.out.println("Erro: " + msg.getStatus());
            }

            input.close();
            output.close();
            socket.close();
        }
        catch(IOException ex) {
            System.out.println("Erro no cliente: " + ex);
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (ClassNotFoundException e) {
            System.out.println("Erro no cast: " + e.getMessage());
        }
        

    }
}
