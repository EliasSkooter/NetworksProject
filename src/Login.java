import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Login extends JFrame{
    private JPanel LoginPanel;
    private JTextField tfUsername;
    private JLabel Password;
    private JPasswordField tfPassword;
    private JLabel Username;
    private JButton logInBtn;
    private JButton RegisterBtn;
    public Client c = new Client();
    public Login() throws IOException {
        setContentPane(LoginPanel);
        setTitle("Log in");
        setSize(400,200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        logInBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    c.sendMessageToServer(tfUsername.getText()+ " "+new String(tfPassword.getPassword()));
                   // dispose();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) throws IOException {
        Login userLogin = new Login();

    }
}
