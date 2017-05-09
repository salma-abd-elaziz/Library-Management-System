package windows.manager;

import driver.Engine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Created by mirnafayez on 03/05/17.
 */
public class AddBookToStoreController {


    private AddBookToStoreUI addBookToStore;

    protected AddBookToStoreController(AddBookToStoreUI addBookToStore) {

        this.addBookToStore = addBookToStore;
    }


    /* Action listener for "ADD BOOK " button */
    protected ActionListener getokBtnListener() {

        return new AddBookToStoreController.okBtnListener();
    }


    private class okBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String insertBookSQL = " INSERT INTO BOOK VALUES (ISBN,Title,publisher_name, Prod_year, Price,Category ,Copies_num, Min_copies)";
            String insetAuthorSQL = " INSERT INTO AUTHOR  VALUES (book_ISBN, author_name)";


            String s = replce_string(insertBookSQL, "ISBN", addBookToStore.addBookPanel.ISBN_v.getText(), false);
            s = replce_string(s, "Title", addBookToStore.addBookPanel.titleTF.getText(), true);
            s = replce_string(s, "Prod_year", addBookToStore.addBookPanel.prodYearTF.getText(), false);
            s = replce_string(s, "publisher_name", addBookToStore.addBookPanel.publisherTF.getText(), true);
            s = replce_string(s, "Price", addBookToStore.addBookPanel.priceTF.getText(), false);
            s = replce_string(s, "Category", addBookToStore.addBookPanel.categoryCB.getSelectedItem().toString(), true);
            s = replce_string(s, "Copies_num", addBookToStore.addBookPanel.copiesNumTF.getText(), false);
            s = replce_string(s, "Min_copies", addBookToStore.addBookPanel.minCopiesTF.getText(), false);

            System.out.println(s);
            try {
                Engine.STATEMENT.executeUpdate(s);
                boolean noAuthors = addBookToStore.addBookPanel.authorsTF.getText().isEmpty();
                String[] authors;
                if (!noAuthors) {
                    authors = addBookToStore.addBookPanel.authorsTF.getText().split(",");
                    String s2 = replce_string(insetAuthorSQL, "book_ISBN", addBookToStore.addBookPanel.ISBN_v.getText(), false);
                    for (int i = 0; i < authors.length; i++) {
                        String s3 = replce_string(s2, "author_name", authors[i], true);
                        System.out.println(s3);

                        Engine.STATEMENT.executeUpdate(s3);
                    }

                }
                JOptionPane.showMessageDialog(null, "Book is added Successfully ", "" + "", JOptionPane.INFORMATION_MESSAGE);
                addBookToStore.dispose();

            } catch (SQLException e1) {
                System.out.println("Exception happened");
                if (e1.getErrorCode() == 1054) { //------------> wrong type + FK
                    String l = e1.getMessage().replace("Unknown column ", "");
                    l = l.replace(" in 'field list'", "");
                    System.out.println("Wrong type" + l);
                } else if (e1.getErrorCode() == 1452) {
                    // fk
                    System.out.println(e1.getMessage());

                } else {
                    System.out.println(e1.getMessage());

                }
                JOptionPane.showMessageDialog(null, "Couldn't add the book , check the data and re-add it \n " + e1.getMessage(), "Error: " + "", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        private String replce_string(String s, String rs, String rs_new, boolean is_string) {
            if (rs_new.isEmpty())
                return (s.replace(rs, "null"));
            if (is_string)
                return (s = s.replace(rs, "'" + rs_new + "'"));
            return s = s.replace(rs, rs_new);
        }
    }
}






