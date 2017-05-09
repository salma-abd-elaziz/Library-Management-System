package windows.customer;

import javax.swing.*;

/**
 * Created by LENOVO on 5/1/2017.
 */
public class EditUI extends JFrame{

    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTextField textField_4;
    private JTextField textField_5;
    private JTextField textField_6;
    private JTextField textField_7;
    private JPasswordField passwordField_1;
    private JLabel label_1;
    private JLabel lblPassword;
    private JLabel lblFirstName;
    private JLabel lblLastName;
    private JLabel lblEmail;
    private JLabel lblPhone;
    private JLabel label_7;
    private JLabel lblAddress;
    private JLabel lblSecurityCode;


    EditController editController;
    public EditUI(){

        this.setBounds(100, 100, 600, 578);
        this.getContentPane().setLayout(null);

        editController = new EditController(this);


        JButton btnNewButton_1 = new JButton("Save");
        btnNewButton_1.setBounds(250, 350, 117, 25);
        this.getContentPane().add(btnNewButton_1);
        btnNewButton_1.addActionListener(editController.getSaveBtnListener());

        this.getContentPane().setLayout(null);
        this.getContentPane().add(btnNewButton_1);


        textField_1 = new JTextField();
        textField_1.setBounds(300, 50, 114, 19);
        this.getContentPane().add(textField_1);
        textField_1.setColumns(10);

        textField_2 = new JTextField();
        textField_2.setBounds(300, 100, 114, 19);
        this.getContentPane().add(textField_2);
        textField_2.setColumns(10);

        textField_3 = new JTextField();
        textField_3.setBounds(300, 129, 114, 19);
        this.getContentPane().add(textField_3);
        textField_3.setColumns(10);

        textField_4 = new JTextField();
        textField_4.setBounds(300, 160, 114, 19);
        this.getContentPane().add(textField_4);
        textField_4.setColumns(10);

        textField_5 = new JTextField();
        textField_5.setBounds(300, 191, 114, 19);
        this.getContentPane().add(textField_5);
        textField_5.setColumns(10);

        textField_6 = new JTextField();
        textField_6.setBounds(300, 222, 114, 19);
        this.getContentPane().add(textField_6);
        textField_6.setColumns(10);

        passwordField_1 = new JPasswordField();
        passwordField_1.setBounds(300, 69, 114, 19);
        this.getContentPane().add(passwordField_1);

        label_1 = new JLabel("User Name");
        label_1.setBounds(100, 52, 90, 15);
        this.getContentPane().add(label_1);

        lblPassword = new JLabel("Password");
        lblPassword.setBounds(100, 71, 90, 15);
        this.getContentPane().add(lblPassword);

        lblFirstName = new JLabel("First Name");
        lblFirstName.setBounds(100, 102, 90, 15);
        this.getContentPane().add(lblFirstName);

        lblLastName = new JLabel("Last Name");
        lblLastName.setBounds(100, 129, 90, 15);
        this.getContentPane().add(lblLastName);

        lblEmail = new JLabel("E-mail");
        lblEmail.setBounds(100, 160, 90, 15);
        this.getContentPane().add(lblEmail);

        lblPhone = new JLabel("Phone");
        lblPhone.setBounds(100, 191, 90, 15);
        this.getContentPane().add(lblPhone);

        label_7 = new JLabel("Shipping Address");
        label_7.setBounds(100, 222, 150, 15);
        this.getContentPane().add(label_7);


        this.setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public String[] getEditedData(){
        String[] editedData = new String[8];

        editedData[0] = textField_1.getText();
        editedData[1] = String.valueOf(passwordField_1.getPassword());
        editedData[2] = textField_2.getText();
        editedData[3] = textField_3.getText();
        editedData[4] = textField_4.getText();
        editedData[5] = textField_5.getText();
        editedData[6] = textField_6.getText();

        return editedData;
    }

}
