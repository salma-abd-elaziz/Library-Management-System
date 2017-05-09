package driver;

import windows.customer.ManageCartController;

import javax.swing.*;
import java.awt.*;

/**
 * Created by salma on 01/05/17.
 */
public class LogInUI extends JFrame{

    private JTextField textField;
    private JPasswordField passwordField;
    private LogInController logInController;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTextField textField_4;
    private JTextField textField_5;
    private JTextField textField_6;
    private JPasswordField textField_7;
    private JPasswordField passwordField_1;
    private JLabel label_1;
    private JLabel lblPassword;
    private JLabel lblFirstName;
    private JLabel lblLastName;
    private JLabel lblEmail;
    private JLabel lblPhone;
    private JLabel lblAddress;
    private JLabel lblSecurityCode;

    public LogInUI(){
        this.setBounds(100, 100, 450, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 768, 498);

        logInController = new LogInController(this);
        JButton btnNewButton = new JButton("Sign in");
        btnNewButton.setBounds(73, 189, 117, 25);
        btnNewButton.addActionListener(logInController.getSignInBtnListener());
        this.getContentPane().setLayout(null);
        this.getContentPane().add(btnNewButton);

        textField = new JTextField();
        textField.setBounds(279, 62, 114, 19);
        this.getContentPane().add(textField);
        textField.setColumns(10);

        JButton btnNewButton_1 = new JButton("Sign up");
        btnNewButton_1.setBounds(494, 421, 117, 25);
        this.getContentPane().add(btnNewButton_1);
        btnNewButton_1.addActionListener(logInController.getSignUpBtnListener());

        passwordField = new JPasswordField();
        passwordField.setBounds(279, 110, 114, 19);
        this.getContentPane().add(passwordField);

        JLabel label = new JLabel("User Name");
        label.setBounds(98, 64, 90, 15);
        this.getContentPane().add(label);

        JLabel lblNewLabel = new JLabel("Password");
        lblNewLabel.setBounds(98, 112, 90, 15);
        this.getContentPane().add(lblNewLabel);

        textField_1 = new JTextField();
        textField_1.setBounds(608, 38, 114, 19);
        this.getContentPane().add(textField_1);
        textField_1.setColumns(10);

        textField_2 = new JTextField();
        textField_2.setBounds(608, 100, 114, 19);
        this.getContentPane().add(textField_2);
        textField_2.setColumns(10);

        textField_3 = new JTextField();
        textField_3.setBounds(608, 129, 114, 19);
        this.getContentPane().add(textField_3);
        textField_3.setColumns(10);

        textField_4 = new JTextField();
        textField_4.setBounds(608, 160, 114, 19);
        this.getContentPane().add(textField_4);
        textField_4.setColumns(10);

        textField_5 = new JTextField();
        textField_5.setBounds(608, 191, 114, 19);
        this.getContentPane().add(textField_5);
        textField_5.setColumns(10);

        textField_6 = new JTextField();
        textField_6.setBounds(608, 222, 114, 19);
        this.getContentPane().add(textField_6);
        textField_6.setColumns(10);

        textField_7 = new JPasswordField();
        textField_7.setText("-");
        textField_7.setBounds(608, 253, 114, 19);
        this.getContentPane().add(textField_7);
        textField_7.setColumns(10);

        passwordField_1 = new JPasswordField();
        passwordField_1.setBounds(608, 69, 114, 19);
        this.getContentPane().add(passwordField_1);

        label_1 = new JLabel("User Name");
        label_1.setBounds(429, 52, 90, 15);
        this.getContentPane().add(label_1);

        lblPassword = new JLabel("Password");
        lblPassword.setBounds(429, 71, 90, 15);
        this.getContentPane().add(lblPassword);

        lblFirstName = new JLabel("First Name");
        lblFirstName.setBounds(429, 102, 90, 15);
        this.getContentPane().add(lblFirstName);

        lblLastName = new JLabel("Last Name");
        lblLastName.setBounds(429, 129, 90, 15);
        this.getContentPane().add(lblLastName);

        lblEmail = new JLabel("E-mail");
        lblEmail.setBounds(429, 160, 90, 15);
        this.getContentPane().add(lblEmail);

        lblPhone = new JLabel("Phone");
        lblPhone.setBounds(429, 191, 90, 15);
        this.getContentPane().add(lblPhone);

        lblAddress = new JLabel("Address");
        lblAddress.setBounds(429, 220, 90, 15);
        this.getContentPane().add(lblAddress);

        lblSecurityCode = new JLabel("Mgr Security Code");
        lblSecurityCode.setBounds(429, 250, 136, 15);
        this.getContentPane().add(lblSecurityCode);

        this.setVisible(true);

    }
    
    public String[] getSignInData(){
        String[] signInData = new String[2];
        signInData[0] = textField.getText();
        signInData[1] = String.valueOf(passwordField.getPassword());
        return signInData;        
    }
    
    public String[] getSignUpData(){
        String[] signUpData = new String[8];
        signUpData[0] = textField_1.getText();
        signUpData[1] = String.valueOf(passwordField_1.getPassword());
        signUpData[2] = textField_2.getText();
        signUpData[3] = textField_3.getText();
        signUpData[4] = textField_4.getText();
        signUpData[5] = textField_5.getText();
        signUpData[6] = textField_6.getText();
        signUpData[7] = textField_7.getText();
        return signUpData;
    }

}
