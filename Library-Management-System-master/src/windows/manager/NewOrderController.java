package windows.manager;

import driver.Engine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Created by mirnafayez on 05/05/17.
 */
public class NewOrderController {

    private String newOrderSQL = "INSERT INTO BOOK_ORDER (ISBN,Order_count,Confirmed_by_manger)VALUES( book_ISBN,num_order,TRUE)";
    private NewOrderUI newOrderUI;

    protected NewOrderController(NewOrderUI NewOrderUI) {
        this.newOrderUI = NewOrderUI;
    }


    protected ActionListener getNewOrderBtnListener() {

        return new newOrderBtnListener();
    }


    private class newOrderBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (newOrderUI.ISBN_num.getText().isEmpty() || newOrderUI.copies_numTF.getText().isEmpty()) {
                // show comp
                return;
            }
            String s = newOrderSQL.replace("book_ISBN", newOrderUI.ISBN_num.getText());
            s = s.replace("num_order", newOrderUI.copies_numTF.getText());
            try {
                Engine.STATEMENT.executeUpdate(s);
                JOptionPane.showMessageDialog(null, "Order is done Successfully", "InfoBox: " + "", JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException e3) {
                JOptionPane.showMessageDialog(null, "Order couldn't be done ", "ERROR: " + "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
