package client;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Message;
import util.Status;


public class Client {
    public static void main(String[] args) {
        try {

            System.out.println("Estabelecendo conexão...");
            Socket socket = new Socket("localhost", 5555);
            System.out.println("Conexão estabelecida");

            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

            Message reply = new Message("reply");

            // Faz cadastro
            System.out.println("Efetuando cadastro");
            Message msg = new Message("register");
            msg.setParam("user", "Pedro");
            msg.setParam("pass", "123");

            // Envia register request
            System.out.println("Enviando...");
            output.writeObject(msg);
            output.flush();
            System.out.println("Enviado");

            // Recebe resposta
            // System.out.println("Resposta recebida");
            reply = (Message) input.readObject();
            System.out.println("Resposta recebida");

            if (reply.getStatus() == Status.OK) {
                System.out.println("Cadastrado com sucesso");
            } else {
                System.out.println("Falha no cadastro");
                System.out.println("Erro: " + reply.getStatus());
            }

            // Login
            System.out.println("Login");
            msg = new Message("login");
            msg.setParam("user", "Pedro");
            msg.setParam("pass", "123");
            // String op = keyboard.next();

            // Envia login request
            output.writeObject(msg);
            output.flush();

            // Recebe resposta
            reply = (Message) input.readObject();
            if (reply.getStatus() == Status.OK) {
                System.out.println("Logado");
            } else {
                System.out.println("Erro no login: " + reply.getStatus());
            }

            // Hello
            System.out.println("Operação Hello");
            msg = new Message("Hello");
            msg.setStatus(Status.REQUEST);
            msg.setParam("nome", "Pedro");
            msg.setParam("sobrenome", "Grigorio");

            // Envia mensagem
            System.out.println("Enviando mensagem...");
            output.writeObject(msg);
            output.flush();

            // Recebe resposta
            reply = (Message) input.readObject();

            if (reply.getStatus() == Status.OK) {
                String content = (String) reply.getParam("mensagem");
                System.out.println("Resposta: " + content);
            } else {
                System.out.println("Erro: " + reply.getStatus());
            }

            // Logout
            System.out.println("Logout");
            msg = new Message("logout");
            output.writeObject(msg);
            output.flush();
            System.out.println("Logout enviado");

            reply = (Message) input.readObject();
            System.out.println("Resposta: " + reply);
            System.out.println("Desconectado");

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
