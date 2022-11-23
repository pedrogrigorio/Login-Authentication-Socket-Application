package screen;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import util.RoundJTextField;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.awt.event.MouseAdapter;

import util.Message;
import util.Status;

public class LoginScreen {

    JFrame frame = new JFrame();
    JButton loginButton = new JButton();
    JLabel registerLabel = new JLabel();
    JLabel status = new JLabel();
    JLabel title = new JLabel();
    RoundJTextField user = new RoundJTextField(20);
    RoundJTextField pass = new RoundJTextField(20);

    Message reply;
    Message msg = new Message("");
  

    public LoginScreen() throws IOException{//, ObjectOutputStream output, ObjectInputStream input) {

        msg.setStatus(Status.NULL);
        msg.setOperation("default");
        //this.socket = socket;
        //this.output = output;
        //this.input = input;

        // Login Button

        loginButton.setText("Entrar");
        loginButton.setBounds(225, 350, 150, 70);
        loginButton.setFont(new Font("Arial", Font.BOLD, 20));
        loginButton.setForeground(new Color(255, 255, 255));
        loginButton.setBackground(new Color(175, 12, 25));

        loginButton.addActionListener(e -> {
            try {
                login(e);
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        // Register Button

        registerLabel.setText("Cadastrar");
        registerLabel.setBounds(450, 350, 150, 70);
        registerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        registerLabel.setForeground(new Color(255, 255, 255));

        frame.add(loginButton);
        frame.add(registerLabel);

        registerLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                msg = new Message("login");
                frame.dispose();
                msg.setStatus(Status.CHANGESCREEN);
                //RegisterScreen register = new RegisterScreen(socket);
                //RegisterScreen register = new RegisterScreen(socket, output, input);        
            }
        });

        // User Field

        user.setText("Usuário");
        user.setBounds(200, 200, 400, 40);
        user.setFont(new Font("Arial", Font.PLAIN, 20));
        user.setForeground(Color.gray);
        user.setBackground(new Color(12, 12, 12));
        // user.setBorder(new LineBorder(Color.gray));
        frame.add(user);

        user.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                user.setText("");
                user.setForeground(Color.white);
                user.setBorder(new LineBorder(Color.gray));
            }
        });

        // Password Field

        pass.setText("Senha");
        pass.setBounds(200, 250, 400, 40);
        pass.setFont(new Font("Arial", Font.PLAIN, 20));
        pass.setBackground(new Color(12, 12, 12));
        // pass.setBorder(new LineBorder(Color.black));
        pass.setForeground(Color.gray);

        frame.add(pass);

        pass.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pass.setText("");
                pass.setForeground(Color.white);
            }
        });

        // Labels
        // Title Label

        title.setText("Entrar");
        title.setBounds(200, 100, 300, 70);
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setForeground(new Color(255, 255, 255));

        frame.add(title);

        // Status Label

        status.setText("Desconectado");
        status.setBounds(10, 550, 150, 70);
        status.setFont(new Font("Arial", Font.BOLD, 16));
        status.setForeground(new Color(175, 12, 25));

        frame.add(status);

        // Screen configs
        frame.setTitle("Entrar");
        frame.setSize(800, 640);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.getContentPane().setBackground(new Color(12, 12, 12));
    }

    private void login(ActionEvent actionEvent) throws IOException, ClassNotFoundException {

        String username = user.getText();
        String password = pass.getText();

        msg = new Message("login");
        msg.setParam("user", username);
        msg.setParam("pass", password);
        frame.dispose();
        msg.setStatus(Status.READY);
    }
    
    public Message getMessage(){
        return msg;
    }
}
