package windows.manager;

import driver.Engine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Created by salma on 09/05/17.
 */
public class AddManagerUI extends JFrame {
    AddManagerController controller;
    JTextField userNameTF;

    public AddManagerUI() {
        super("ADD Manger");
        this.setVisible(true);
        this.setSize(Engine.WINDOW_WIDTH, Engine.WINDOW_HEIGHT);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setBounds(100, 100, 558, 350);
        this.getContentPane().setLayout(null);
        controller = new AddManagerController(this);
        JLabel UserNameLB = new JLabel("User name :");
        UserNameLB.setBounds(30 , 30 , 120 ,30);

         userNameTF = new JTextField();
        userNameTF.setBounds( 200,30,120,30);

        JButton addMangerBTN = new JButton("ADD MANGER");
        addMangerBTN.setBounds( 200, 90 , 120,30);


        this.getContentPane().add(UserNameLB);
        this.getContentPane().add(userNameTF);
        this.getContentPane().add(addMangerBTN);

        addMangerBTN.addActionListener(controller.getAddManagerBtnListener());

    }

}
