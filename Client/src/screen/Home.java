package screen;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import util.Message;
import util.Status;
import util.RoundJTextField;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class Home {

    JFrame frame = new JFrame();
    JButton hello = new JButton();
    JButton logout = new JButton();
    RoundJTextField user = new RoundJTextField(20);
    RoundJTextField pass = new RoundJTextField(20);
    JLabel title = new JLabel();
    JLabel serverReply = new JLabel();
    JLabel status = new JLabel();
    JLabel back = new JLabel();

    Message msg = new Message("");
    String username;

    public Home(String username) throws IOException{//, ObjectOutputStream output, ObjectInputStream input, String username) {

        msg.setStatus(Status.NULL);
        this.username = username;
        
        // Logout Button
        hello.setText("Hello");
        hello.setBounds(200, 250, 400, 70);
        hello.setFont(new Font("Arial", Font.BOLD, 20));
        hello.setForeground(new Color(255, 255, 255));
        hello.setBackground(new Color(175, 12, 25));

        frame.add(hello);

        hello.addActionListener(e -> {
            try {
                hello(e);
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        // Logout Button
        logout.setText("Sair");
        logout.setBounds(200, 350, 400, 70);
        logout.setFont(new Font("Arial", Font.BOLD, 20));
        logout.setForeground(new Color(255, 255, 255));
        logout.setBackground(new Color(175, 12, 25));

        frame.add(logout);

        logout.addActionListener(e -> {
            try {
                logout(e);
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        // Labels

        // Title Label
        title.setText("Home");
        title.setBounds(348, 100, 300, 40);
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setForeground(new Color(255, 255, 255));

        frame.add(title);

        // Resposta Label
        serverReply.setText("Resposta:");
        serverReply.setBounds(270, 190, 300, 70);
        serverReply.setFont(new Font("Arial", Font.BOLD, 20));
        serverReply.setForeground(new Color(255, 255, 255));

        frame.add(serverReply);

        // Status Label

        status.setText("Conectado");
        status.setBounds(10, 550, 150, 70);
        status.setFont(new Font("Arial", Font.BOLD, 16));
        status.setForeground(Color.green);

        frame.add(status);

        // Screen configs
        frame.setTitle("Cadastrar");
        frame.setSize(800, 640);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.getContentPane().setBackground(new Color(12, 12, 12));

    }

    public void hello(ActionEvent e) throws IOException, ClassNotFoundException {

        msg = new Message("Hello");
        msg.setParam("nome", username);
        msg.setStatus(Status.READY);
    }

    public void logout(ActionEvent e) throws IOException, ClassNotFoundException {

        msg = new Message("logout");
        msg.setStatus(Status.READY);
    }

    public Message getMessage(){
        return msg;
    }

    public void reply(String reply){
        serverReply.setText("Resposta: " + reply);
    }

    public void close(){
        frame.dispose();
    }
}
