package server;

import java.io.*;
import java.net.*;

import util.Message;
import util.Status;

public class Server {
    
    private ServerSocket serverSocket;

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
            
            System.out.println("Recebendo mensagem e verificando operacao...");
            Message msg = (Message) input.readObject();
            String op = msg.getOperation();
            Message reply = null;

            if(op.equals("Hello")){
                String nome = (String) msg.getParam("nome");
                String sobrenome = (String) msg.getParam("sobrenome");
                System.out.println("Mensagem recebida");

                reply = new Message("Helloreply");

                if(nome == null || sobrenome == null)
                    reply.setStatus(Status.ERROR);
                else{
                    reply.setStatus(Status.OK);
                    reply.setParam("mensagem","Hello world, " + nome + " " + sobrenome);
                    System.out.println("Tudo ok");
                }
            }

            output.writeObject(reply);
            output.flush();
            System.out.println("Enviando resposta...");

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
