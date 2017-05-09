package driver;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;

/**
 * Created by LENOVO on 4/30/2017.
 */
public class CustomerUI extends JFrame {


    JButton editInfoBtn, addBtn, searchBtn, manageCardBtn, checkoutBtn, logoutBtn;
    private JToolBar optionsBar;
    protected Connector connector;
    protected Container content;

    public CustomerUI(){

        super("");
        this.setVisible(true);
        content = this.getContentPane();
        this.setSize(900, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        content.setLayout(new BorderLayout());

        this.setLayout(new BorderLayout());
        JLabel background=new JLabel(new ImageIcon("resources/pics/background.jpg"));
        add(background);
        background.setLayout(new FlowLayout());


        /* initialize variables */
        connector = new Connector(this);

        editInfoBtn = new JButton("Edit Personal Info");
        addBtn = new JButton("Add Book To Cart");
        searchBtn = new JButton("Search Book");
        manageCardBtn = new JButton("Manage Shopping Card");
        checkoutBtn = new JButton("Check Out");
        logoutBtn = new JButton("Log Out");

        editInfoBtn.addActionListener(connector.getEditBtnListener());
        addBtn.addActionListener(connector.getAddBtnListener());
        manageCardBtn.addActionListener(connector.getManageBtnListener());
        checkoutBtn.addActionListener(connector.getCheckoutBtnListener());
        logoutBtn.addActionListener(connector.getLogoutBtnListener());

        optionsBar = new JToolBar();
        optionsBar.add(editInfoBtn);
        optionsBar.add(addBtn);
//        optionsBar.add(searchBtn);
        optionsBar.add(manageCardBtn);
        optionsBar.add(checkoutBtn);
        optionsBar.add(logoutBtn);

        content.add(optionsBar, BorderLayout.NORTH);
    }
}