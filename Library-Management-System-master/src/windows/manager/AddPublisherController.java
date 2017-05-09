package windows.manager;

import driver.Engine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Created by mirnafayez on 06/05/17.
 */
public class AddPublisherController {
    /*Queries*/
    private String addPublisherSQL = " INSERT INTO PUBLISHER  VALUES (publisher_name, publisher_address , phone )";

    private AddPublisherUi addPublisherUi;

    protected AddPublisherController(AddPublisherUi addPublisherUi) {

        this.addPublisherUi = addPublisherUi;
    }

    /* Add action listener to " Add Publisher button" */
    protected ActionListener getAddPublisherBtnListener() {

        return new addPublisherBtnListener();
    }


    private class addPublisherBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String s = replce_string(addPublisherSQL, "publisher_name", addPublisherUi.publisherNameTF.getText(), true);
            s = replce_string(s, "publisher_address", addPublisherUi.publisherAddressTF.getText(), true);
            s = replce_string(s, "phone", addPublisherUi.publisherPhoneTF.getText(), true);
            System.out.println(s);

            try {
                Engine.STATEMENT.executeUpdate(s);
                JOptionPane.showMessageDialog(null, "Publisher is added Successfully ", "" + "", JOptionPane.INFORMATION_MESSAGE);
                addPublisherUi.dispose();
            } catch (SQLException sq_e) {
                System.out.println("ERROR HAPPENED" + sq_e.getMessage());
                JOptionPane.showMessageDialog(null, "Couldn't add the Publisher , check the data and re-add it \n " + sq_e.getMessage(), "Error: " + "", JOptionPane.INFORMATION_MESSAGE);

            }

        }
    }

    /* replace parts of the string */
    private String replce_string(String s, String rs, String rs_new, boolean is_string) {
        if (rs_new.isEmpty())
            return (s.replace(rs, "null"));
        if (is_string)
            return (s = s.replace(rs, "'" + rs_new + "'"));
        return s = s.replace(rs, rs_new);
    }
}

