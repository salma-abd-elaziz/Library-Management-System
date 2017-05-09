package windows.manager;

import driver.Engine;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Created by mirnafayez on 04/05/17.
 */
public class OrderController {

    OrdersUI ordersUI;

    protected OrderController(OrdersUI ordersUI) {
        this.ordersUI = ordersUI;

    }

    String selectUnconfirmedOrdersSQL = "SELECT BO.Seq_num ,B.ISBN, B.Title , BO.Order_count , B.Copies_num ,B.Min_copies FROM BOOK_ORDER AS BO ,BOOK AS B  WHERE B.ISBN=BO.ISBN AND Confirmed_by_manger=false";
    String selectConfirmedOrdersSQL = "SELECT BO.Seq_num,B.ISBN, B.Title , BO.Order_count , B.Copies_num ,B.Min_copies FROM BOOK_ORDER AS BO ,BOOK AS B  WHERE B.ISBN=BO.ISBN AND Confirmed_by_manger=true";

    //String orderBookQuery = "INSERT INTO BOOK_ORDER VALUES(book_ISBN,num_of_books,true)";
    String deleteOrdersSQL = "DELETE FROM BOOK_ORDER WHERE Seq_num = order_seq_num";
    String orderConfirmationSQL = "UPDATE BOOK_ORDER SET Confirmed_by_manger = true WHERE Seq_num = order_seq_num";


    /**/
        /* Action listener for "ADD BOOK " button */
    protected ActionListener getConfBtnListener() {
        return new confirmOrderBTn();
    }

    /* Action listener for "ADD BOOK " button */
    protected ActionListener getDeleteBtnListener() {
        return new deleteOrderBTn();
    }

    /* Action listener for "ADD BOOK " button */
    protected ActionListener getReceiveBtnListener() {
        return new receiveOrderBTn();
    }



    protected ResultSet getUnconfirmedOrders() throws SQLException {
        return driver.Engine.STATEMENT.executeQuery(selectUnconfirmedOrdersSQL);
    }

    protected ResultSet getConfirmedOrders() throws SQLException {
        return driver.Engine.STATEMENT.executeQuery(selectConfirmedOrdersSQL);
    }


    protected class confirmOrderBTn implements ActionListener {
        private String ISBN;
        private BookInfoForm jp;

        @Override
        public void actionPerformed(ActionEvent e){

            int x =ordersUI.unconfirmedOrdersTable.getSelectedRow();
            String seq_num=(String)ordersUI.unconfirmedOrdersTable.getValueAt(x,0);


            try {
                String s =orderConfirmationSQL.replace("order_seq_num", seq_num);
                Engine.STATEMENT.executeUpdate(s);

                DefaultTableModel d = (DefaultTableModel)ordersUI.unconfirmedOrdersTable.getModel();
                DefaultTableModel d2 = (DefaultTableModel)ordersUI.confirmedOrdersTable.getModel();
                d2.addRow(new Vector((Vector)d.getDataVector().get(x)));
                d.removeRow(ordersUI.unconfirmedOrdersTable.getSelectedRow());

            }catch (SQLException e2){
                JOptionPane.showMessageDialog(null, "Error ", "Error" + "", JOptionPane.ERROR_MESSAGE);
            }

        }


    }


    protected class deleteOrderBTn implements ActionListener {
        private String ISBN;
        private BookInfoForm jp;

        @Override
        public void actionPerformed(ActionEvent e) {

            int x =ordersUI.unconfirmedOrdersTable.getSelectedRow();
            String seq_num=(String)ordersUI.unconfirmedOrdersTable.getValueAt(x,0);


            try {
                String s =deleteOrdersSQL.replace("order_seq_num", seq_num);
                Engine.STATEMENT.executeUpdate(s);

                DefaultTableModel d = (DefaultTableModel)ordersUI.unconfirmedOrdersTable.getModel();
                d.removeRow(ordersUI.unconfirmedOrdersTable.getSelectedRow());

            }catch (SQLException e2){
                JOptionPane.showMessageDialog(null, "Error ", "Error" + "", JOptionPane.ERROR_MESSAGE);
            }
        }




    }


    protected class receiveOrderBTn implements ActionListener {
        private String ISBN;
        private BookInfoForm jp;

        @Override
        public void actionPerformed(ActionEvent e) {
            int x =ordersUI.confirmedOrdersTable.getSelectedRow();
            String seq_num=(String)ordersUI.confirmedOrdersTable.getValueAt(x,0);


            try {
                String s =deleteOrdersSQL.replace("order_seq_num", seq_num);
                Engine.STATEMENT.executeUpdate(s);

                DefaultTableModel d = (DefaultTableModel)ordersUI.confirmedOrdersTable.getModel();
                d.removeRow(ordersUI.confirmedOrdersTable.getSelectedRow());

            }catch (SQLException e2){
                JOptionPane.showMessageDialog(null, "Error ", "Error" + "", JOptionPane.ERROR_MESSAGE);
            }
        }
        }
}
