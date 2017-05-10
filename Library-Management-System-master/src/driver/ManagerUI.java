package driver;


import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

/**
 * Created by LENOVO on 4/30/2017.
 */
public class ManagerUI extends CustomerUI {


    JButton addBookBtn,modifyBtn, orderBtn, confOrderBtn, addPublisherBtn, viewReportBtn, addManagerBtn;
    protected JToolBar managerOptionsBar;

    /*
    * Construtor
    * */
    public ManagerUI() {
        super();
        managerOptionsBar=  new JToolBar();
        addBookBtn = new JButton("Add Book To Library");
        modifyBtn = new JButton("Modify Book");
        orderBtn = new JButton("Order Book");
        confOrderBtn = new JButton("Confirm Book Order");
        addPublisherBtn = new JButton("Add Publisher");
        viewReportBtn = new JButton("View Reports");
        addManagerBtn = new JButton("Add Manger");


        addBookBtn.addActionListener(super.connector.getaddBookBtnListener());
        modifyBtn.addActionListener(super.connector.getmodifyBtnListener());
        orderBtn.addActionListener(super.connector.getorderBtnListener());
        confOrderBtn.addActionListener(super.connector.getconfOrderBtnListener());
        viewReportBtn.addActionListener(super.connector.getviewReportBtnListener());
        addPublisherBtn.addActionListener(super.connector.getAddPublisherBtnListener());
        addManagerBtn.addActionListener(super.connector.getAddMangerBtrListener());

        managerOptionsBar.add(addBookBtn);
        managerOptionsBar.add(modifyBtn);
        managerOptionsBar.add(orderBtn);
        managerOptionsBar.add(confOrderBtn);
        managerOptionsBar.add(addPublisherBtn);
        managerOptionsBar.add(viewReportBtn);
        managerOptionsBar.add(addManagerBtn);
        super.content.add(managerOptionsBar, BorderLayout.AFTER_LAST_LINE);

    }

}