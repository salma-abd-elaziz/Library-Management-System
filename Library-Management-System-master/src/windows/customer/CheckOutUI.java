package windows.customer;

import javax.swing.*;

/**
 * Created by salma on 05/05/17.
 */
public class CheckOutUI extends JFrame {

    private JTextField textField;
    private JTextField textField_1;
    private JButton btnOk;
    private CheckOutController checkOutController;

    public CheckOutUI() {
        this.setBounds(100, 100, 558, 350);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getContentPane().setLayout(null);

        checkOutController = new CheckOutController(this);

        JLabel lblCridetCard = new JLabel("Credit Card Number : ");
        lblCridetCard.setBounds(59, 55, 159, 21);
        this.getContentPane().add(lblCridetCard);

        textField = new JTextField();
        textField.setBounds(220, 56, 179, 19);
        this.getContentPane().add(textField);
        textField.setColumns(10);

        JLabel lblNewLabel = new JLabel("Check Out Inf  :");
        lblNewLabel.setBounds(30, 12, 109, 31);
        this.getContentPane().add(lblNewLabel);

        JLabel lblExpiryDate = new JLabel("Expiry Date : ");
        lblExpiryDate.setBounds(59, 105, 143, 15);
        this.getContentPane().add(lblExpiryDate);

        textField_1 = new JTextField();
        textField_1.setBounds(220, 103, 179, 19);
        this.getContentPane().add(textField_1);
        textField_1.setColumns(10);

        btnOk = new JButton("OK");
        btnOk.setBounds(200, 171, 117, 25);
        btnOk.addActionListener(checkOutController.getSaveBtnListener());
        this.getContentPane().add(btnOk);
        this.setVisible(true);
    }


    public String getCreditCardNo() {
        return textField.getText();
    }

    public String getExpiryDate() {
        return textField_1.getText();
    }
}
