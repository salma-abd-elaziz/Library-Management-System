package windows.manager;

import driver.Engine;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by mirnafayez on 04/05/17.
 */
public class OrdersUI extends JFrame {

    protected Container ordersContent;
    private OrderController orderController;
    private JButton confOrder, deleteOrder, newOrder, receivedOrder;
    private JTextField isbn;

    protected JScrollPane confirmedOrdersSB;
    protected JScrollPane unConfirmedOrdersSB;


    protected JTable unconfirmedOrdersTable;
    protected JTable confirmedOrdersTable;

    JLabel orderLabel;
    JLabel orderLabel2;

    public OrdersUI() {
        super("BOOK ORDERS ");
        this.setVisible(true);
        ordersContent = this.getContentPane();
        this.setSize(Engine.WINDOW_WIDTH, Engine.WINDOW_HEIGHT);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ordersContent.setLayout(null);

        /* CONTROLLER*/
        orderController = new OrderController(this);


        orderLabel = new JLabel("Books to be ordered");
        orderLabel.setFont(new Font("Calibri", Font.BOLD, 15));
        orderLabel.setBounds(10, 10, 200, 20);

        orderLabel2 = new JLabel("Books Orders");
        orderLabel2.setFont(new Font("Calibri", Font.BOLD, 15));
        orderLabel2.setBounds(10, 260, 200, 20);


        /**/
        confOrder = new JButton("Confirm Order");
        confOrder.setVisible(true);
        confOrder.setBounds(30, 210, 200, 20);

        deleteOrder = new JButton("Delete Order");
        deleteOrder.setVisible(true);
        deleteOrder.setBounds(300, 210, 200, 20);

        newOrder = new JButton("New Order");
        //newOrder.setBounds(300,450,200,20);

        receivedOrder = new JButton("Order is received");
        receivedOrder.setBounds(300, 450, 200, 20);


        confOrder.addActionListener(orderController.getConfBtnListener());
        deleteOrder.addActionListener(orderController.getDeleteBtnListener());
        receivedOrder.addActionListener(orderController.getReceiveBtnListener());


        unconfirmedOrdersTable = new JTable();
        DefaultTableModel model1 = (DefaultTableModel) unconfirmedOrdersTable.getModel();
        model1.setColumnIdentifiers(new Object[]{"Order Num","Book ISBN", "Book Title", "Number of books to be ordered ", "Number of available books", "Mininum number of copies "});

        ResultSet rs1 = null;
        try {
            rs1 = orderController.getUnconfirmedOrders();
            while (rs1.next()) {
                model1.insertRow(unconfirmedOrdersTable.getRowCount(), new Object[]{rs1.getString("Seq_num"),rs1.getString("ISBN"), rs1.getString("Title"), rs1.getString("Order_count"), rs1.getString("Copies_num"), rs1.getString("Min_copies")});
            }
        } catch (SQLException e1) {
        }

        unConfirmedOrdersSB = new JScrollPane();
        unConfirmedOrdersSB.setViewportView(unconfirmedOrdersTable);
        unConfirmedOrdersSB.setBounds(10, 50, Engine.WINDOW_WIDTH - 100, 150);

        confirmedOrdersTable = new JTable();
        DefaultTableModel model2 = (DefaultTableModel) confirmedOrdersTable.getModel();

        model2.setColumnIdentifiers(new Object[]{"Order Num","Book ISBN", "Book Title", "Number of books to be ordered ", "Number of available books", "Mininum number of copies "});
        ResultSet rs2 = null;
        try {
            rs2 = orderController.getConfirmedOrders();
            while (rs2.next()) {
                model2.insertRow(confirmedOrdersTable.getRowCount(), new Object[]{rs2.getString("Seq_num"),rs2.getString("ISBN"), rs2.getString("Title"), rs2.getString("Order_count"), rs2.getString("Copies_num"), rs2.getString("Min_copies")});
            }
        } catch (SQLException e1) {
        }


        confirmedOrdersSB = new JScrollPane();
        confirmedOrdersSB.setViewportView(confirmedOrdersTable);
        confirmedOrdersSB.setBounds(10, 280, Engine.WINDOW_WIDTH - 100, 150);


        this.getContentPane().add(orderLabel);
        this.getContentPane().add(unConfirmedOrdersSB);


        this.getContentPane().add(orderLabel2);
        this.getContentPane().add(confirmedOrdersSB);

        this.getContentPane().add(confOrder);
        this.getContentPane().add(deleteOrder);
        this.getContentPane().add(receivedOrder);

    }
}
