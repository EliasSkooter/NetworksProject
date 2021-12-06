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
                    if(tfUsername.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null,"Please Enter Your Username!");
                    }
                    else if(new String(tfPassword.getPassword()).isEmpty()){
                        JOptionPane.showMessageDialog(null,"Please Enter Your Password!");
                    }
                    else {
                        c.sendMessageToServer("log");
                        c.sendMessageToServer(tfUsername.getText());
                        c.sendMessageToServer(new String(tfPassword.getPassword()));
                        boolean logInResponse = c.getInputFromServer().readBoolean();
                        if (logInResponse == true){
                            JOptionPane.showMessageDialog(null, "LogIn Successful \nWelcome "+tfUsername.getText());
                            Userform userform = new Userform(tfUsername.getText());
                            c.getSocket().close();
                            dispose();
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Authentication Failed");
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        RegisterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    c.getSocket().close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                Register re = null;
                try {
                    re = new Register();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
//                re.setVisible(true);
                dispose();
            }
        });
    }

    public static void main(String[] args) throws IOException {
        Login userLogin = new Login();

    }
}
