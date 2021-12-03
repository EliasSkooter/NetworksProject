import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

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
                    System.out.println(vacCer);

                    c.sendFileToServer(vacCer);

//                      c.sendImageToServer(image);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    public static void main(String[] args) throws IOException {
        Register userRegister = new Register();

    }
}
