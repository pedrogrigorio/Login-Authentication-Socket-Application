package screen;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import util.Message;
import util.Status;
import util.RoundJTextField;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.awt.event.MouseAdapter;

public class RegisterScreen {

    JFrame frame = new JFrame();
    JButton registerButton = new JButton();
    RoundJTextField user = new RoundJTextField(20);
    RoundJTextField pass = new RoundJTextField(20);
    JLabel title = new JLabel();
    JLabel status = new JLabel();
    JLabel back = new JLabel();

    //Socket socket;
    Message reply;
    Message msg = new Message("");

    public RegisterScreen() throws IOException{

        msg.setStatus(Status.NULL);

        // Register Button
        registerButton.setText("Cadastrar");
        registerButton.setBounds(200, 350, 400, 70);
        registerButton.setFont(new Font("Arial", Font.BOLD, 20));
        registerButton.setForeground(new Color(255, 255, 255));
        registerButton.setBackground(new Color(175, 12, 25));

        frame.add(registerButton);

        registerButton.addActionListener(e -> {
            try {
                register(e);
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        // Voltar Button

        back.setText("Voltar");
        back.setBounds(375, 450, 150, 70);
        back.setFont(new Font("Arial", Font.BOLD, 20));
        back.setForeground(new Color(255, 255, 255));

        frame.add(back);

        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                
                frame.dispose();
                msg = new Message("register");
                msg.setStatus(Status.CHANGESCREEN);
            }
        });

        // User Field

        user.setText("Usuário");
        user.setBounds(200, 200, 400, 40);
        user.setFont(new Font("Arial", Font.PLAIN, 20));
        user.setForeground(Color.gray);
        user.setBackground(new Color(12, 12, 12));
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
        title.setText("Cadastrar");
        title.setBounds(200, 100, 300, 70);
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setForeground(new Color(255, 255, 255));

        frame.add(title);

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

    public void register(ActionEvent e) throws IOException, ClassNotFoundException {

        String username = user.getText();
        String password = pass.getText();

        msg = new Message("register");
        msg.setParam("user", username);
        msg.setParam("pass", password);
        msg.setStatus(Status.READY);
    }

    public Message getMessage(){
        return msg;
    }
}
