import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends JFrame{
    private JPanel panel1;
    private JTextField tfName;
    private JButton ImageBtn;
    private JLabel imageLabel;
    private JLabel nameLabel;
    private JPanel RegisterPanel;
    private JTextField tfEmail;
    private JLabel emailLabel;
    private JTextField tfUsername;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JPasswordField tfPassword;
    private JCheckBox vaccinationCheck;
    private JLabel vaccinationFormLabel;
    private JButton VaccinationFormBtn;
    private JLabel ImageSelectedLabel;
    private JLabel FileSelectedLabel;
    private JButton signUpButton;
    private JButton cancelButton;
    private JLabel confirmPasswordLabel;
    private JPasswordField tfConfirmPassword;
    private String image;
    private String vacCer;
    public Client c = new Client();

    public Register() throws IOException {
        FileSelectedLabel.setVisible(false);
        vaccinationFormLabel.setVisible(false);
        VaccinationFormBtn.setVisible(false);
        setContentPane(RegisterPanel);
        setTitle("Sign Up");
        setSize(600,350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        ImageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == ImageBtn){
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setAcceptAllFileFilterUsed(false);
                    fileChooser.setDialogTitle("Select a JPG, PNG, or JPEG file");
                    FileNameExtensionFilter filter = new FileNameExtensionFilter(
                            ".JPG, .PNG, .JPEG", "jpg", "png","jpeg");
                    fileChooser.addChoosableFileFilter(filter);

                    int response = fileChooser.showOpenDialog(null); // select file to open
                    if (response== JFileChooser.APPROVE_OPTION){
                        image = fileChooser.getSelectedFile().getAbsolutePath();
                        ImageSelectedLabel.setText(fileChooser.getSelectedFile().getAbsolutePath());
                    }
                    else {
                        ImageSelectedLabel.setText("the user cancelled the operation");
                    }

                }

            }
        });
        VaccinationFormBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == VaccinationFormBtn){
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setAcceptAllFileFilterUsed(false);
                    fileChooser.setDialogTitle("Select a PDF file");
                    FileNameExtensionFilter filter = new FileNameExtensionFilter(
                            ".PDF", "pdf");
                    fileChooser.addChoosableFileFilter(filter);

                    int response = fileChooser.showOpenDialog(null);// select file to open
                    if (response== JFileChooser.APPROVE_OPTION){
                        vacCer = fileChooser.getSelectedFile().getAbsolutePath();
                        //System.out.println(fileSize);
                        FileSelectedLabel.setText(fileChooser.getSelectedFile().getAbsolutePath());
                    }
                    else {
                        FileSelectedLabel.setText("the user cancelled the operation");
                    }

                }

            }
        });
        vaccinationCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(vaccinationCheck.isSelected() == true) {
                    vaccinationFormLabel.setVisible(true);
                    VaccinationFormBtn.setVisible(true);
                    FileSelectedLabel.setVisible(true);
                }
                else{
                    vaccinationFormLabel.setVisible(false);
                    VaccinationFormBtn.setVisible(false);
                    FileSelectedLabel.setVisible(false);
                }
            }
        });
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String regex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
//                    ^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$

                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(tfEmail.getText());
                    System.out.println(matcher.matches());

                    if (tfName.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null, "Please Fill In Your Name!");
                    }
                    else if(image == null){
                        JOptionPane.showMessageDialog(null, "Please Choose An Image!");
                    }
                    else if(tfEmail.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null, "Please Fill In Your Email!");
                    }
                    else if(tfUsername.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null, "Please Fill In a UserName!");
                    }
                    else if(new String(tfPassword.getPassword()).isEmpty()){
                            JOptionPane.showMessageDialog(null, "Please Fill In a Password!");
                    }
                    else if(vaccinationCheck.isSelected() && vacCer==null){
                        JOptionPane.showMessageDialog(null, "Please Choose Your Vaccination Certificate!");
                    }
                    else if(!tfName.getText().isEmpty() && !image.isEmpty() && !tfEmail.getText().isEmpty() && !tfUsername.getText().isEmpty() && !new String(tfPassword.getPassword()).isEmpty()) {
                        if(new String(tfPassword.getPassword()).equals(new String(tfConfirmPassword.getPassword()))) {
                            if(matcher.matches()) {
                                c.sendMessageToServer("reg");
                                c.sendMessageToServer(tfName.getText());
                                c.sendFileToServer(image);
                                c.sendMessageToServer(tfEmail.getText());
                                c.sendMessageToServer(tfUsername.getText());
                                c.sendMessageToServer(new String(tfPassword.getPassword()));
                                c.getOutputToServer().writeBoolean(vaccinationCheck.isSelected());
                                if (vaccinationCheck.isSelected() == true) {
                                    c.sendFileToServer(vacCer);
                                }
                                JOptionPane.showMessageDialog(null, "User Has Been Successfully Registered!");
//                                c.getSocket().close();
//                                Login li = new Login();
//                        li.setVisible(true);
//                                dispose();
                            }
                            else {
                                JOptionPane.showMessageDialog(null, "Please Enter a Valid Email");
                            }
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Password and Confirm Password Do Not Match!");
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    c.getSocket().close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                Login li = null;
                try {
                    li = new Login();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
//                li.setVisible(true);
                dispose();
            }
        });
    }
}
