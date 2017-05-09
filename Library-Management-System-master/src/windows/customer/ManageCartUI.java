
package windows.customer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * Created by LENOVO on 5/1/2017.
 */



/**
 * Created by LENOVO on 5/1/2017.
 */
public class ManageCartUI extends JFrame {
   
    private JTable table;
    JLabel lblNewLabel;
    DefaultTableModel model;

    ManageCartController manageCartController;
    JList list;

    public ManageCartUI() throws SQLException{
        this.setBounds(100, 100, 759, 578);
        this.getContentPane().setLayout(null);

        lblNewLabel = new JLabel("");
        lblNewLabel.setBounds(221, 25, 146, 28);
        this.getContentPane().add(lblNewLabel);

        model = new DefaultTableModel();
        table = new JTable(model);

        JScrollPane js = new JScrollPane();
        js.setViewportView(table);
        js.setBounds(100, 108, 600, 400);

        model.addColumn("Product Key");
        model.addColumn("ISBN");
        model.addColumn("Price");
        model.addColumn("Title");
        model.addColumn("Publisher");
        model.addColumn("Production Year");
        model.addColumn("Category");
        model.addColumn("No. of Copies");

        table.setBounds(100, 108, 600, 375);
        this.getContentPane().add(js);
        js.setVisible(true);


        JLabel lblSearchResults = new JLabel("Cart Content");
        lblSearchResults.setBounds(380, 81, 106, 15);
        this.getContentPane().add(lblSearchResults);


        JButton btnRemove = new JButton("Remove");
        btnRemove.setBounds(465, 25, 117, 25);
        this.getContentPane().add(btnRemove);

        manageCartController = new ManageCartController(this);
        btnRemove.addActionListener(manageCartController.getRemoveBtnListener());

        this.setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }


    public String getProductKey(){
        return (String) table.getValueAt(table.getSelectedRow(), 0);
    }

    public DefaultTableModel getModel(){
        return model;
    }


    public void setSum(int sum) {
        lblNewLabel.setText("Total Price = " + String.valueOf(sum) + " $");
    }
}

