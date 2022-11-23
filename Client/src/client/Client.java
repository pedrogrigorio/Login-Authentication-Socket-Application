package client;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import screen.Home;
import screen.LoginScreen;
import screen.RegisterScreen;
import util.Message;
import util.Status;
import util.State;

public class Client {
    public static void main(String[] args) throws ClassNotFoundException {
        try {

            System.out.println("Estabelecendo conexão...");
            Socket socket = new Socket("localhost", 5555);
            System.out.println("Conexão estabelecida");

            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

            State state = State.CONNECT;
            String op = "login";
            Message msg = new Message(op);
            Message reply;
            
            msg.setStatus(Status.NULL);
            LoginScreen login = new LoginScreen();
            RegisterScreen register = null;
            Home home = null;

            while (state != State.DISCONNECT) {
                switch (state) {
                    case CONNECT:
                        switch (op) {
                            case "login":
                                // Mensagem pronta para ser enviada
                                if (msg.getStatus() == Status.READY) {
                                    // Envia login
                                    System.out.println("Enviando login");
                                    String username = (String) msg.getParam("user");
                                    output.writeObject(msg);
                                    output.flush();

                                    // Recebe resposta
                                    reply = (Message) input.readObject();
                                    if (reply.getStatus() == Status.OK) {
                                        System.out.println("Logado");
                                        msg.setOperation("default");
                                        home = new Home(username);
                                        state = State.AUTHENTICATED;
                                    } 
                                    else {
                                        System.out.println("Erro no login: " + reply.getStatus() + " usuário ou senha inválidos.");
                                        msg = new Message("exit");
                                        output.writeObject(msg);
                                        output.flush();
                                        state = State.DISCONNECT;
                                    }
                                    //Reseta status para buscar próxima mensagem
                                    msg.setStatus(Status.NULL);

                                } // Mensagem de troca de tela
                                else if (msg.getStatus() == Status.CHANGESCREEN) {
                                    System.out.println("Trocando para a tela de cadastro");
                                    register = new RegisterScreen();
                                    msg.setStatus(Status.NULL);
                                    op = "register";
                                } 
                                else{
                                    msg = login.getMessage();
                                }
                                break;
                            case "register":
                                // Mensagem pronta para ser enviada
                                if (msg.getStatus() == Status.READY) {
                                    // Envia cadastro
                                    System.out.println("Enviando cadastro");
                                    output.writeObject(msg);
                                    output.flush();

                                    // Recebe resposta
                                    reply = (Message) input.readObject();

                                    if (reply.getStatus() == Status.OK) {
                                        System.out.println("Cadastrado com sucesso");
                                    } 
                                    else {
                                        System.out.println("Falha no cadastro");
                                        System.out.println("Erro: " + reply.getStatus());
                                    }

                                    //Reseta status para buscar próxima mensagem
                                    msg.setStatus(Status.NULL);

                                } // Mensagem de troca de tela
                                else if (msg.getStatus() == Status.CHANGESCREEN) {
                                    System.out.println("Trocando para a tela de Login");
                                    register = null;
                                    login = new LoginScreen();
                                    op = "login";
                                    msg.setStatus(Status.NULL);
                                }
                                else{
                                    msg = register.getMessage();
                                }
                                break;
                            default:
                                break;
                        }
                        break;
                    case AUTHENTICATED:
                        switch (op) {
                            case "Hello":
                                // Mensagem pronta para ser enviada
                                if (msg.getStatus() == Status.READY) {

                                    // Envia hello
                                    System.out.println("Enviando Hello");
                                    output.writeObject(msg);
                                    output.flush();

                                    // Recebe resposta
                                    reply = (Message) input.readObject();
                                    if (reply.getStatus() == Status.OK) {
                                        String content = (String) reply.getParam("mensagem");
                                        System.out.println("Resposta: " + content);
                                        home.reply(content);
                                    }

                                    // Reseta parâmetros para buscar nova mensagem
                                    msg.setStatus(Status.NULL);
                                    msg.setOperation("default");
                                    op = "default";
                                } 
                                else{
                                    msg = home.getMessage();
                                }
                                break;
                            case "logout":
                                // Envia logout
                                System.out.println("Enviando logout");
                                output.writeObject(msg);
                                output.flush();

                                // Recebe resposta
                                reply = (Message) input.readObject();
                                
                                // Volta para tela de login
                                System.out.println("Trocando para a tela de login");
                                home.close();
                                login = new LoginScreen();

                                msg.setStatus(Status.NULL);
                                state = State.CONNECT;
                                op = "login";
                                break;
                            default:
                                // Busca mensagem 
                                msg = home.getMessage();
                                op = msg.getOperation();
                                break;
                        }
                        break;
                    case DISCONNECT:
                        break;
                }
            }

            System.out.println("Fecha socket e I/O");
            input.close();
            output.close();
            socket.close();
        } catch (IOException ex) {
            System.out.println("Erro no cliente: " + ex);
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
