package server;

import java.io.*;
import java.net.*;

public class Server {
    
    private ServerSocket serverSocket;

    private void criarServerSocket(int port) throws IOException{
        serverSocket = new ServerSocket(port);
    }

    private Socket esperaConexao() throws IOException{
        Socket con = serverSocket.accept();
        return con;
    }

    private void trataConexao(Socket socket) throws IOException{
        try{
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());

            String msg = input.readUTF();
            System.out.println("Mensagem recebida...");
            output.writeUTF("Hello World");

            input.close();
            output.close();
        }
        catch(IOException e){
            //tratamento
        }
        finally{
            fechaSocket(socket);
        }
    }

    private void fechaSocket(Socket s) throws IOException{
        s.close();
    }

    public static void main(String[] args){
        try{
            Server server = new Server();
            System.out.println("Aguardando conexão...");
            server.criarServerSocket(5555);
            Socket socket = server.esperaConexao();
            System.out.println("Cliente conectado.");
            server.trataConexao(socket);
            System.out.println("Cliente finalizado.");
        }
        catch(IOException e){
            //tratamento
        }
    }
}
