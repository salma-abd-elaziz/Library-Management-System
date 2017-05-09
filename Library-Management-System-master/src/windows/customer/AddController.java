package windows.customer;

import driver.CustomerUI;
import driver.Engine;
import windows.manager.BookInfoForm;
import windows.manager.ModifyBookController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.sun.xml.internal.fastinfoset.alphabet.BuiltInRestrictedAlphabets.table;

/**
 * Created by LENOVO on 5/1/2017.
 */

public class AddController {
    private AddUI ui;
    private ResultSet rs;
    private JTable table;
    private String name;
    private String searchValue;

    private String ISBN;


    private String updateBookSQL = "UPDATE BOOK set col = val WHERE ISBN = book_ISBN ";
    String deleteBookAuthorsSQL = "DELETE FROM AUTHOR WHERE ISBN = book_ISBN";
    String insetAuthorSQL = " INSERT INTO AUTHOR  VALUES (book_ISBN, author_name)";

    public AddController(AddUI ui) {
        this.ui = ui;
    }

    /*Adding Action  Listner to Modify book */
    protected ActionListener getStartModifyingBtnListener() {

        return new startModifyingBtnListener();
    }

    /*Adding Action  Listner to Update book Information */
    protected ActionListener getUpdateBookBtnListener() {

        return new updateBookBtnListener();
    }


    private class startModifyingBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            table = ui.getTable();
            ui.getContentPane().removeAll();


            ui.modifying(table.getValueAt(table.getSelectedRow(), 3).toString());
            ui.repaint();
        }
    }


    protected class updateBookBtnListener implements ActionListener {
        private windows.manager.BookInfoForm jp = ui.modifyingBookPanel;


        @Override
        public void actionPerformed(ActionEvent e) {

            String s1 = replce_string(updateBookSQL, "book_ISBN", ui.getTable().getValueAt(ui.getTable().getSelectedRow(), 3).toString(), false);
            /*author*/
            String new_ISBN = jp.ISBN_v.getText();
            String s2 = replce_string(deleteBookAuthorsSQL, "book_ISBN", new_ISBN, false);


            try {
                // START TRANSACTION
                Engine.CONNECTION.setAutoCommit(false);

                String temp;

                temp = s1.replace("col", "Title");
                temp = replce_string(temp, "val", jp.titleTF.getText(), true);
                Engine.STATEMENT.executeUpdate(temp);

                temp = s1.replace("col", "Publisher");
                temp = replce_string(temp, "val", jp.publisherTF.getText(), true);
                Engine.STATEMENT.executeUpdate(temp);


                temp = s1.replace("col", "Price");
                temp = replce_string(temp, "val", jp.priceTF.getText(), false);
                Engine.STATEMENT.executeUpdate(temp);


                temp = s1.replace("col", "Prod_year");
                temp = replce_string(temp, "val", jp.prodYearTF.getText(), false);
                Engine.STATEMENT.executeUpdate(temp);


                temp = s1.replace("col", "Category");
                temp = replce_string(temp, "val", jp.categoryCB.getSelectedItem().toString(), true);
                Engine.STATEMENT.executeUpdate(temp);


                temp = s1.replace("col", "Copies_num");
                temp = replce_string(temp, "val", jp.copiesNumTF.getText(), false);
                Engine.STATEMENT.executeUpdate(temp);


                temp = s1.replace("col", "Min_copies");
                temp = replce_string(temp, "val", jp.minCopiesTF.getText(), false);
                Engine.STATEMENT.executeUpdate(temp);

                temp = s1.replace("col", "ISBN");
                temp = replce_string(temp, "val", jp.ISBN_v.getText(), false);
                Engine.STATEMENT.executeUpdate(temp);


                /*delete all authors for this isbn */
                Engine.STATEMENT.executeUpdate(s2);

                boolean noAuthors = jp.authorsTF.getText().isEmpty();
                String[] authors;
                if (!noAuthors) {

                    //insert new author
                    authors = jp.authorsTF.getText().split(",");
                    String s3 = replce_string(insetAuthorSQL, "book_ISBN", new_ISBN, false);

                    s3 = replce_string(s3, "author_name", authors[0], true);
                    Engine.STATEMENT.executeUpdate(s3);


                    for (int i = 1; i < authors.length; i++) {
                        s3 = s3.replace(authors[i - 1], authors[i]);

                        Engine.STATEMENT.executeUpdate(s3);
                    }

                }

                // If there is no error. COMMIT
                Engine.CONNECTION.commit();
                JOptionPane.showMessageDialog(null, "Book is modified Successfully ", "" + "", JOptionPane.INFORMATION_MESSAGE);


            } catch (SQLException se) {
                JOptionPane.showMessageDialog(null, "Error in modifying book ", "Error" + "", JOptionPane.ERROR_MESSAGE);

                // If there is any error.
                try {
                    Engine.CONNECTION.rollback();
                } catch (SQLException p) {

                }
            }

            // END TRANSACTION
            try {

                Engine.CONNECTION.setAutoCommit(true);
            } catch (SQLException p) {

            }
        }

    }

    private String replce_string(String s, String rs, String rs_new, boolean is_string) {
        if (rs_new.isEmpty())
            return (s.replace(rs, "null"));
        if (is_string)
            return (s = s.replace(rs, "'" + rs_new + "'"));
        return s = s.replace(rs, rs_new);
    }

    private class AddBtnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                table = ui.getTable();
                String ISBN = null;
                int selectedRow;
                selectedRow = table.getSelectedRow();


                if (selectedRow != -1) {
                    String number = ui.getNumberOfCopies();
                    if (number == null)
                        return;

                    int numberOfCopies = Integer.parseInt(number);

                    if (numberOfCopies < 1)
                        return;

                    String bookName = (String) table.getValueAt(selectedRow, 0);

                    rs.beforeFirst();
                    while (rs.next()) {
                        String name = rs.getString("Title");
                        if (name.equals(bookName)) {
                            ISBN = rs.getString("ISBN");
                        }
                    }
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, 1);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

                    String date = format1.format(cal.getTime());


                    String sql = "INSERT INTO SHOPPING_CART (User_name , ISBN , Num_books , Con_date , Confirmed) VALUES " +
                            "( \"" + Engine.LOGGED_IN_USER + "\", " +
                            " \"" + ISBN + "\"," +
                            " \"" + numberOfCopies + "\" ," +
                            " \'" + date + "\' ," +
                            "0 )";

                    Engine.STATEMENT.executeUpdate(sql);
                    sql = "SELECT * FROM BOOK WHERE " + name + " = \"" + searchValue + "\"";
                    rs = Engine.STATEMENT.executeQuery(sql);
                }
            } catch (SQLException e1) {
            }
        }
    }


    private class SearchBtnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            name = ui.getChoice();
            searchValue = ui.getSearchValue();

            if (name != null && !searchValue.equals("")) {
                String sql;
                if (!name.equals("Author"))
                    sql = "SELECT * FROM BOOK WHERE " + name + " = \"" + searchValue + "\"";
                else
                    sql = "SELECT * FROM BOOK , AUTHOR WHERE BOOK.ISBN = AUTHOR.ISBN";

                DefaultTableModel dm = (DefaultTableModel) ui.getModel();
                int rowCount = ui.getModel().getRowCount();
                for (int i = rowCount - 1; i >= 0; i--) {
                    dm.removeRow(i);
                }

                try {
                    rs = Engine.STATEMENT.executeQuery(sql);
                    while (rs.next()) {
                        DefaultTableModel model = ui.getModel();
                        String ISBN = rs.getString("ISBN");
                        String book = rs.getString("Title");
                        String price = rs.getString("Price");
                        String No_of_copies = rs.getString("Copies_num");
                        model.addRow(new Object[]{book, price, No_of_copies, ISBN});
                    }

                } catch (Exception e1) {
                }
            }
        }
    }


    public ActionListener getSearchBtnListener() {
        return new SearchBtnListener();
    }


    public ActionListener getAddBtnListener() {
        return new AddBtnListener();
    }

}
