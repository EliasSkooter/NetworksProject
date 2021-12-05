import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.String;

public class Userform extends JFrame{

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
    public Userform(){
      pcrl.setVisible(false);
      browseDocumentsButton.setVisible(false);
      selectedfile.setVisible(false);
      setContentPane(mainpanel);
      setTitle("User Form");
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


    }


    public static void  main(String[] args){
        Userform frame = new Userform();


    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}