package windows.customer;

import driver.Engine;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import static driver.Engine.STATEMENT;
import static driver.Engine.LOGGED_IN_USER;

public class ManageCartController {
    private ResultSet rs;
    private String sql;
    private ManageCartUI ui;

    public ManageCartController(ManageCartUI ui) {
        String LOGGED_IN_USER = Engine.LOGGED_IN_USER;
        try {
            this.ui = ui;
            DefaultTableModel dm = (DefaultTableModel) ui.getModel();
            int rowCount = ui.getModel().getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
            }
            sql = "SELECT * FROM BOOK AS B, SHOPPING_CART AS SC WHERE User_name = " + "\"" + LOGGED_IN_USER + "\" AND B.ISBN = SC.ISBN AND Confirmed = 0";
            rs = STATEMENT.executeQuery(sql);
            while (rs.next()) {
                DefaultTableModel model = ui.getModel();
                String productKey = rs.getString("Seq_key");
                String ISBN = rs.getString("ISBN");
                String price = rs.getString("Price");
                String bookTitle = rs.getString("Title");
                String publisher = rs.getString("Publisher");
                String prod_year = rs.getString("Prod_year");
                String category = rs.getString("Category");
                String copies_num = rs.getString("Num_books");

                model.addRow(new Object[]{productKey, ISBN, bookTitle, price, publisher, prod_year, category, copies_num});
            }
            ui.setSum(getTotal(rs));
        } catch (Exception e) {
        }
    }


    public class removeBtnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String productKey = ui.getProductKey();
            sql = "DELETE FROM SHOPPING_CART WHERE SHOPPING_CART.Seq_key = " + productKey;
            try {
                DefaultTableModel dm = (DefaultTableModel) ui.getModel();
                int rowCount = ui.getModel().getRowCount();
                for (int i = rowCount - 1; i >= 0; i--) {
                    dm.removeRow(i);
                }
                STATEMENT.executeUpdate(sql);
                sql = "SELECT * FROM BOOK AS B, SHOPPING_CART AS SC WHERE User_name = " + "\"" + LOGGED_IN_USER + "\" and B.ISBN = SC.ISBN AND Confirmed = 0";

                rs = STATEMENT.executeQuery(sql);
                while (rs.next()) {
                    DefaultTableModel model = ui.getModel();
                    productKey = rs.getString("Seq_key");
                    String ISBN = rs.getString("ISBN");
                    String price = rs.getString("Price");
                    String bookTitle = rs.getString("Title");
                    String publisher = rs.getString("Publisher");
                    String prod_year = rs.getString("Prod_year");
                    String category = rs.getString("Category");
                    String copies_num = rs.getString("Num_books");
                    model.addRow(new Object[]{productKey, ISBN, bookTitle, price, publisher, prod_year, category, copies_num});
                }
                ui.setSum(getTotal(rs));
            } catch (SQLException e1) {
            }

        }
    }

    public ActionListener getRemoveBtnListener() {
        return new removeBtnListener();
    }


    private int getTotal(ResultSet rs) {
        int total = 0;
        try {
            rs.beforeFirst();
            while (rs.next()) {
                int price, numBooks;
                price = rs.getInt("Price");
                numBooks = rs.getInt("Num_books");
                total += (price * numBooks);
            }
        } catch (Exception e) {
        }
        return total;
    }

}
