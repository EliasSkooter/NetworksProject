import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.String;

public class Userform extends JFrame{

    private JPanel mainpanel;
    private JButton quaryActiveCasesButton;
    private JButton viewListButton;
    private JComboBox comboBox1;
    private JButton addUserButton;
    private JButton submitUpdateButton;
    private JComboBox comboBox3;
    private JButton browsDocumentsButton;
    private JButton signOutButton;
    private JButton submitListUpdateButton;
    private JTextField textField1;
    private JTextField textField2;
    private JButton checkButton;
    private JLabel pcrl;
    private JLabel selectedfile;
    private String wew;
    public Userform(){
      pcrl.setVisible(false);
      browsDocumentsButton.setVisible(false);
        selectedfile.setVisible(false);
      setLocationRelativeTo(null);
      setTitle("User Form");
      setSize(631,419);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setContentPane(mainpanel);
      setResizable(false);
        browsDocumentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == browsDocumentsButton){
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
                if(comboBox1.) {
                    browsDocumentsButton.setVisible(true);
                    pcrl.setVisible(true);
                }
                else{
                    browsDocumentsButton.setVisible(false);
                    pcrl.setVisible(false);

                }
            }
        });


    }


    public static void  main(String[] args){
        JFrame frame = new Userform();
        frame.setVisible(true);


    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}