package windows.customer;

import driver.Engine;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static driver.Engine.LOGGED_IN_USER;
import static driver.Engine.STATEMENT;

/**
 * Created by LENOVO on 5/1/2017.
 */

public class EditController {

    EditUI ui;
    String sql;

    public EditController(EditUI ui){
        this.ui = ui;
    }

    public ActionListener getSaveBtnListener() {
        return new SaveBtnListener();
    }

    private class SaveBtnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String sql = null;

            String[] editedData = ui.getEditedData();
            String[] column = new String[8];
            column[0] = "User_name";
            column[1] = "Password";
            column[2] = "First_name";
            column[3] = "Last_name";
            column[4] = "Email";
            column[5] = "Phone";
            column[6] = "Shipping_address";
            column[7] = "Is_manager";


            sql = "UPDATE STORE_USER SET " +
                    column[0] + " = \"" + editedData[0] + "\"" + " , " +
                    column[1] + " = \"" + editedData[1] + "\"" + " , " +
                    column[2] + " = \"" + editedData[2] + "\"" + " , " +
                    column[3] + " = \"" + editedData[3] + "\"" + " , " +
                    column[4] + " = \"" + editedData[4] + "\"" + " , " +
                    column[5] + " = \"" + editedData[5] + "\"" + " , " +
                    column[6] + " = \"" + editedData[6] + "\"" +
                    " WHERE User_name = " + "\"" + Engine.LOGGED_IN_USER + "\"";

            try {
                STATEMENT.executeUpdate(sql);
                LOGGED_IN_USER = editedData[0];
                JOptionPane.showMessageDialog(null, "Data updated successfully!");
            } catch (Exception e1) {

                ResultSet checkRS = null;

                String errMsg = e1.getMessage();
                String[] tokens = errMsg.split(" ");
                List<String> tokensList = Arrays.asList(tokens);
                Collections.reverse(new ArrayList(tokensList));

                String invalidField = null;
                for (String token : tokens) {
                    if (token.charAt(0) == '\'') {
                        invalidField = token;
                    }
                }
                if (errMsg.equals("invalid Email")) {
                    invalidField = "Email";
                } else if (invalidField.equals("'PRIMARY'")) {
                    invalidField = "User name";
                } else if (invalidField.equals("'Email_UNIQUE'")) {
                    invalidField = "Email";
                } else if (invalidField.equals("'Phone_UNIQUE'")) {
                    invalidField = "Phone";
                }

                JOptionPane.showMessageDialog(null, "Value of " + invalidField + " is invalid");
            }


        }
    }
}
