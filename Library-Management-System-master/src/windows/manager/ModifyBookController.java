package windows.manager;

import driver.Engine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ModifyBookController {
    String updateBookSQL = "UPDATE BOOK set col = val WHERE ISBN = book_ISBN ";
    String deleteBookAuthorsSQL = "DELETE FROM AUTHOR WHERE ISBN = book_ISBN";
    String insetAuthorSQL = " INSERT INTO AUTHOR  VALUES (book_ISBN, author_name)";

    private ModifyBookUI modifyBookUI;
    /*
     * Constructor
     */

    protected ModifyBookController(ModifyBookUI modifyBookUI) {
        this.modifyBookUI = modifyBookUI;
    }

    /*Adding Action  Listner to Modify book */
    protected ActionListener getStartModifyingBtnListener() {

        return new startModifyingBtnListener();
    }

    /*Adding Action  Listner to Update book Information */
    protected ActionListener getUpdateBookBtnListener() {

        return new updateBookBtnListener();
    }

    /*start modifying with the given ISBN*/
    private class startModifyingBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (modifyBookUI.ISBN_num.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "No ISBN entered", "Error" + "", JOptionPane.ERROR_MESSAGE);
                return;
            }
            modifyBookUI.getContentPane().removeAll();
            modifyBookUI.modifying(modifyBookUI.ISBN_num.getText());
            modifyBookUI.repaint();


        }
    }

    /*update book*/
    protected class updateBookBtnListener implements ActionListener {
        private String ISBN;
        private BookInfoForm jp;


        @Override
        public void actionPerformed(ActionEvent e) {

            String s1 = replce_string(updateBookSQL, "book_ISBN", ISBN, false);
            /*author*/
            String new_ISBN = jp.ISBN_v.getText();//------------------------------------menna
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

                    /*insert new author*/
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

        private String replce_string(String s, String rs, String rs_new, boolean is_string) {
            if (rs_new.isEmpty())
                return (s.replace(rs, "null"));
            if (is_string)
                return (s = s.replace(rs, "'" + rs_new + "'"));
            return s = s.replace(rs, rs_new);
        }

        public void setISBN(String ISBN, BookInfoForm jp) {
            this.ISBN = ISBN;
            this.jp = jp;
        }
    }

}