import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class User extends JFrame{
    private JPanel panel1;
    private JLabel pickATrustedUserLabel;
    private JLabel numOfActiveCasesLabel;
    private JPanel userPanel;
    private JLabel addUserLabel;
    private JLabel statusLabel;
    private JLabel uploadPRCLabel;
    private JLabel locationLabel;
    private JButton logOutButton;
    private JButton QueryActive;
    private JLabel valueActive;
    private JComboBox<String> listUsersTrusted;
    private JComboBox<String> ListUsersToTrust;
    private JButton submitToTrust;
    private JComboBox<String> covidStatus;
    private JButton SubmitStatus;
    private JButton pcrUploadButton;
    private JLabel pcrUploadLabel;
    private JComboBox<String> dropdownLocations;
    private JButton submitLocation;
    private JLabel UserNameLabel;
    private JLabel detailsLabel;
    private JLabel detailsSubLabel;
    private String pcr;

    public User() {
        setContentPane(userPanel);
        setTitle("User");
        setSize(600,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        pcrUploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == pcrUploadButton) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setAcceptAllFileFilterUsed(false);
                    fileChooser.setDialogTitle("Select a PDF file");
                    FileNameExtensionFilter filter = new FileNameExtensionFilter(
                            ".PDF", "pdf");
                    fileChooser.addChoosableFileFilter(filter);

                    int response = fileChooser.showOpenDialog(null);// select file to open
                    if (response == JFileChooser.APPROVE_OPTION) {
                        pcr = fileChooser.getSelectedFile().getAbsolutePath();
                        //System.out.println(fileSize);
                        pcrUploadLabel.setText(fileChooser.getSelectedFile().getAbsolutePath());
                    } else {
                        pcrUploadLabel.setText("the user cancelled the operation");
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        User user = new User();
    }
}
