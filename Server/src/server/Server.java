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
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

            String msg = input.readUTF();
            System.out.println("Mensagem recebida: " + msg);

            System.out.println("Enviando resposta...");
            output.writeUTF("Hello World");
            output.flush();

            input.close();
            output.close();
        }
        catch(IOException e){
            System.out.println("Problema no tratamento da conexão com o cliente: " + socket.getInetAddress());
            System.out.println("Erro: " + e.getMessage());
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
            server.criarServerSocket(5555);
            
            while(true){
                System.out.println("Aguardando conexão...");
                Socket socket = server.esperaConexao();
                System.out.println("Cliente conectado.");
                server.trataConexao(socket);
                System.out.println("Cliente finalizado.");
            }
        }
        catch(IOException e){
            //tratamento
            System.out.println("Erro no servidor: " + e.getMessage());
        }
    }
}
