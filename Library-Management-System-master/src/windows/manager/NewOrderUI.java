package windows.manager;

import driver.Engine;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by mirnafayez on 05/05/17.
 */
public class NewOrderUI extends JFrame {

    NewOrderController newOrderController;

    protected Container newOrderContent;

    JTable table;
    protected JPanel panel;
    protected JTextField ISBN_num;
    protected JTextField copies_numTF;

    public NewOrderUI() {
        /* this */
        super("new Order");
        this.setVisible(true);
        newOrderContent = this.getContentPane();
        this.setSize(Engine.WINDOW_WIDTH, Engine.WINDOW_HEIGHT);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        /*Panel */
        panel = new JPanel();
        panel.setPreferredSize((Dimension) new Dimension(Engine.WINDOW_WIDTH, Engine.WINDOW_HEIGHT));
        panel.setLayout(null);

        /* Scroll Pane*/
        JScrollPane scrollPane = new JScrollPane();
        panel.add(scrollPane);



        /* Table*/
        table = new JTable();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setColumnIdentifiers(new Object[]{"ISBN", "TITLE", "Publisher"});


        ResultSet rs = null;
        try {
            rs = Engine.STATEMENT.executeQuery("SELECT * FROM BOOK ");
            while (rs.next()) {
                model.insertRow(table.getRowCount(), new Object[]{rs.getString("ISBN"), rs.getString("Title"), rs.getString("Publisher")});
            }
        } catch (SQLException e1) {
            // msg box ;
            JOptionPane.showMessageDialog(null, "Book store has no books  ", "InfoBox: " + "No Books Message", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        }


        table.setEnabled(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // add table to Scrollpane

        scrollPane.setViewportView(table);
        scrollPane.setBounds(5, 5, Engine.WINDOW_WIDTH - 20, Engine.WINDOW_HEIGHT - 100);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setBackground(Color.WHITE);

        /**/
        JButton addNewOrder = new JButton("New Order");
        addNewOrder.setBounds(300, 520, 186, 25);
        panel.add(addNewOrder);

        /**/
        ISBN_num = new JTextField();
        ISBN_num.setBounds(20, 520, 80, 25);
        panel.add(ISBN_num);

        copies_numTF = new JTextField();
        copies_numTF.setBounds(120, 520, 80, 25);
        panel.add(copies_numTF);


        this.getContentPane().add(panel, BorderLayout.NORTH);

        newOrderController = new NewOrderController(this);
        addNewOrder.addActionListener(newOrderController.getNewOrderBtnListener());
    }
}
