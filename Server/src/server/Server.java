package server;

import java.io.*;
import java.net.*;

public class Server {
    
    private ServerSocket serverSocket;

    private void createSocket(int port) throws IOException{
        serverSocket = new ServerSocket(port);
    }

    private Socket waitContact() throws IOException{
        Socket conn = serverSocket.accept();
        return conn;
    }

    private void trataConexao(Socket socket) throws IOException{
        try{
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            
            Message msg = input.readObject();
            String op = msg.getOperation();

            if(op.equals("Hello")){
                String nome = (String) msg.getParam("nome");
                String sobrenome = (String) msg.getParam("sobrenome");

                Message reply = new Message("Hello reply");
                reply.setStatus(ok);
                reply.setParam("mensagem","Hello world, " + nome + " " + sobrenome);
            }

            output.writeObject(msg);
            System.out.println("Enviando resposta...");
            output.flush();

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
    }
}
