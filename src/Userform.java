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
    private JComboBox comboBox1;
    private JButton addUserButton;
    private JButton submitUpdateButton;
    private JComboBox comboBox3;
    private JButton browseDocumentsButton;
    private JButton signOutButton;
    private JTextField TfaddUser;
    private JTextField TfCheckTrusted;
    private JButton checkButton;
    private JLabel pcrl;
    private JLabel selectedfile;
    private JLabel welcomeUserLabel;
    private JButton Status_btn;
    private JButton submitResultButton;
    //private JLabel numbertobeupdated;
    //private JLabel justlabel;
    private String wew;
    public Client c = new Client();
    private int id;
    public Userform(String user, int id) throws IOException {
        this.user = user;
        this.id = id;
        System.out.println(id);
      pcrl.setVisible(false);
      browseDocumentsButton.setVisible(false);
      selectedfile.setVisible(false);

      //numbertobeupdated.setVisible(false);
     // justlabel.setVisible(false);
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
        submitResultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    c.sendMessageToServer("pcr");
                    c.sendFileToServer(wew);
                    c.getOutputToServer().writeInt(id);
                } catch (IOException ex) {
                    ex.printStackTrace();
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

        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    c.sendMessageToServer("check_user_status");
                    c.sendMessageToServer(TfCheckTrusted.getText());
//                    c.sendMessageToServer(user);
                    c.getOutputToServer().writeInt(id);
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
                String persontoTrust = TfaddUser.getText();
                try {
                    c.sendMessageToServer("add_user_button");
                    c.sendMessageToServer(persontoTrust);
                    c.getOutputToServer().writeInt(id);

                    JOptionPane.showMessageDialog(null, "User has been added.");

                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });
        //user presses the buttob to send request for server to send him how many cases are present now
        queryActiveCasesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // c.sendFileToServer();
               // c.getInputFromServer();
                try {
                    c.sendMessageToServer("check_active_cases");
                    int count = c.getInputFromServer().readInt();
                    JOptionPane.showMessageDialog(null, "Total Number of Active Cases: "+count);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });
        Status_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String current_status = comboBox1.getSelectedItem().toString();
                try {
                    c.sendMessageToServer("current_status_condition");
                    c.sendMessageToServer(current_status);
                    c.getOutputToServer().writeInt(id);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                browseDocumentsButton.setVisible(true);
                pcrl.setVisible(true);
                selectedfile.setVisible(true);
            }
        });
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

}