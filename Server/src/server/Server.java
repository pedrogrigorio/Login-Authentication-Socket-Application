package server;

import java.io.*;
import java.net.*;

import util.Message;
import util.Status;
import util.State;

import java.util.HashMap;
import java.util.Map;

public class Server {
    
    private ServerSocket serverSocket;

    Map<String, Object> bd = new HashMap<>();

    private void createSocket(int port) throws IOException{
        serverSocket = new ServerSocket(port);
    }

    private Socket waitContact() throws IOException{
        Socket conn = serverSocket.accept();
        return conn;
    }

    private void trataConexao(Socket socket) throws IOException, ClassNotFoundException{
        try{
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            
            
            State state = State.CONNECT;
            while(state != State.DISCONNECT){
                System.out.println("Recebendo mensagem e verificando operacao...");
                Message msg = (Message) input.readObject();
                String op = msg.getOperation();
                Message reply = new Message(op + "reply");
            
                switch(state){
                    case CONNECT:
                        switch(op){
                            case "login":
                                try {
                                    System.out.println("Verificando credenciais");
                                    String user = (String) msg.getParam("user");
                                    String pass = (String) msg.getParam("pass");
                                    if(bd.containsKey(user) && bd.get(user) == pass){
                                        System.out.println("Verificados");
                                        reply.setStatus(Status.OK);
                                        state = State.AUTHENTICATED;
                                    }
                                    else{
                                        System.out.println("Login Recusado");
                                        reply.setStatus(Status.ERROR);
                                        state = State.DISCONNECT;
                                    }
                                } catch (Exception e) {
                                    reply.setStatus(Status.ERROR);
                                    reply.setParam("msg", "Usuário ou senha inválidos.");
                                }
                                break;
                            case "register":
                                System.out.println("Verificando dados");
                                String user = (String) msg.getParam("user");
                                String pass = (String) msg.getParam("pass");
                                System.out.println(user + pass);
                                if(!bd.containsKey(user)){
                                    System.out.println("Cadastrando");
                                    reply.setStatus(Status.OK);
                                    System.out.println("OK setado");
                                    bd.put(user, pass);
                                    System.out.println("Cadastrado");
                                }
                                else{
                                    reply.setStatus(Status.ERROR);
                                    state = State.DISCONNECT;
                                } 
                                break;
                            default:
                                System.out.println("teste1");
                                reply.setStatus(Status.ERROR);
                                reply.setParam("msg", "Mensagem não autorizada");
                                break;
                        }
                        break;
                    case AUTHENTICATED:
                        switch(op){
                            case "Hello":
                                System.out.println("Realizando operação Hello");
                                String nome = (String) msg.getParam("nome");
                                String sobrenome = (String) msg.getParam("sobrenome");
                                System.out.println("Mensagem recebida");
                            
                                reply = new Message("Helloreply");
                            
                                if(nome == null || sobrenome == null)
                                    reply.setStatus(Status.ERROR);
                                else{
                                    reply.setStatus(Status.OK);
                                    reply.setParam("mensagem", "Hello world, " + nome + " " + sobrenome);
                                    System.out.println("Tudo ok");
                                }
                                break;
                            case "logout":
                                System.out.println("Realizando logout");
                                reply.setStatus(Status.OK);
                                state = State.DISCONNECT;
                                break;
                            default:
                                reply.setStatus(Status.ERROR);
                                reply.setParam("msg", "Mensagem não autorizada");
                                break;
                        }
                        break;
                    case DISCONNECT:
                        System.out.println("Cliente desconectado");
                        break;
                }

                System.out.println("Enviando resposta...");
                output.writeObject(reply);
                output.flush();
            }

            input.close();
            output.close();
        }
        catch(IOException e){
            System.out.println("Problema no tratamento da conexão com o cliente: " + socket.getInetAddress());
            System.out.println("Erro: " + e.getMessage());
        }
        finally{
            closeSocket(socket);
        }
    }

    private void closeSocket(Socket socket) throws IOException{
        socket.close();
    }

    public static void main(String[] args){
        try{
            Server conn = new Server();
            conn.createSocket(5555);

            while(true){
                System.out.println("Aguardando conexão...");
                Socket socket = conn.waitContact();
                System.out.println("Cliente conectado.");
                conn.trataConexao(socket);
                System.out.println("Cliente finalizado.");
            }
        }
        catch(IOException e){
            System.out.println("Erro no servidor: " + e.getMessage());
        }
        catch(ClassNotFoundException e){
            System.out.println("Erro no cast: " + e.getMessage());
        }
    }
}
