import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.String;

public class Userform extends JFrame{
    private String user;
    private JPanel mainpanel;
    private JButton queryActiveCasesButton;
    private JButton viewListButton;
    private JComboBox comboBox1;
    private JButton addUserButton;
    private JButton submitUpdateButton;
    private JComboBox comboBox3;
    private JButton browseDocumentsButton;
    private JButton signOutButton;
    private JTextField textField1;
    private JTextField textField2;
    private JButton checkButton;
    private JLabel pcrl;
    private JLabel selectedfile;
    private JLabel welcomeUserLabel;
    private String wew;
    public Client c = new Client();
    public Userform(String user) throws IOException {
        this.user = user;
      pcrl.setVisible(false);
      browseDocumentsButton.setVisible(false);
      selectedfile.setVisible(false);
      setContentPane(mainpanel);
      setTitle("User Form");
      welcomeUserLabel.setText("Welcome " + user);
      setSize(700,419);
      setLocationRelativeTo(null);
      setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//      setResizable(false);
      setVisible(true);
        browseDocumentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == browseDocumentsButton){
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setAcceptAllFileFilterUsed(false);
                    fileChooser.setDialogTitle("Select a PDF file");
                    FileNameExtensionFilter filter = new FileNameExtensionFilter(
                            ".PDF", "pdf");
                    fileChooser.addChoosableFileFilter(filter);
                    selectedfile.setVisible(false);
                    int response = fileChooser.showOpenDialog(null);// select file to open
                    if (response== JFileChooser.APPROVE_OPTION){
                        wew = fileChooser.getSelectedFile().getAbsolutePath();
                        //System.out.println(fileSize);
                        selectedfile.setText(fileChooser.getSelectedFile().getAbsolutePath());
                        selectedfile.setVisible(true);
                    }
                    else {
                        selectedfile.setText("the user cancelled the operation");
                        selectedfile.setVisible(true);
                    }

                }

            }
        });
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(((String)comboBox1.getSelectedItem()).equals("Contagious")) {
                    browseDocumentsButton.setVisible(true);
                    pcrl.setVisible(true);
                    selectedfile.setVisible(true);
                }
                else{
                    browseDocumentsButton.setVisible(false);
                    pcrl.setVisible(false);
                    selectedfile.setVisible(false);

                }
            }
        });


        signOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Login login = new Login();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                //cut client connection code

                dispose();

            }
        });
        queryActiveCasesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        comboBox3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    c.sendMessageToServer("check_user_status");
                    c.sendMessageToServer(textField2.getText());
                    c.sendMessageToServer(user);
                    boolean status_response = c.getInputFromServer().readBoolean();
                    if (status_response == true){
                        //user in the trusted list
                        String user_status = c.getInputFromServer().readUTF();
                        JOptionPane.showMessageDialog(null, "user found, status: "+user_status);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "user does not want to share his status");
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String txt = textField2.getText();
                try {
                    c.sendMessageToServer("add_user_button");
                    c.sendMessageToServer(txt);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

}