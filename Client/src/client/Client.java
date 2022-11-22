package client;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import util.Message;
import util.Status;
import util.State;

public class Client {
    public static void main(String[] args) {
        try {
            System.out.println("Estabelecendo conexão...");
            Socket socket = new Socket("localhost", 5555);
            System.out.println("Conexão estabelecida");

            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

            /* 
            State state = State.CONNECT;
            String op = "";
            while (state != State.DISCONNECT) {

                switch(state){
                    case CONNECT:
                        switch(op){
                            case "login":
                                break;
                            case "register":
                                break;
                        }
                        break;
                    case AUTHENTICATED:
                        break;
                    case DISCONNECT:
                        break;
                    
                }*/
                System.out.println("Efetuando login");
                // Faz login
                Message msg = new Message("login");
                msg.setParam("user", "Pedro");
                msg.setParam("pass", "123");

                output.writeObject(msg);
                output.flush();

                System.out.println("Recebendo resposta");
                // Recebe resposta
                msg = (Message) input.readObject();

                if (msg.getStatus() == Status.OK) {
                    String reply = (String) msg.getParam("mensagem");
                    System.out.println("Resposta: " + reply);
                } else {
                    System.out.println("Erro: " + msg.getStatus());
                }

                // Manda operação Hello
                System.out.println("Operação Hello");
                msg = new Message("Hello");
                msg.setStatus(Status.REQUEST);
                msg.setParam("nome", "Pedro");
                msg.setParam("sobrenome", "Grigorio");

                System.out.println("Enviando mensagem...");
                output.writeObject(msg);
                output.flush();

                // System.out.println("Mensagem " + msg + "enviada.");

                // Recebe resposta
                msg = (Message) input.readObject();
                // System.out.println("Resposta: " + msg);

                if (msg.getStatus() == Status.OK) {
                    String reply = (String) msg.getParam("mensagem");
                    System.out.println("Resposta: " + reply);
                } else {
                    System.out.println("Erro: " + msg.getStatus());
                }

                msg = new Message("logout");
                output.writeObject(msg);
                output.flush();
            //}

            input.close();
            output.close();
            socket.close();
        } catch (IOException ex) {
            System.out.println("Erro no cliente: " + ex);
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException e) {
            System.out.println("Erro no cast: " + e.getMessage());
        }

    }
}
