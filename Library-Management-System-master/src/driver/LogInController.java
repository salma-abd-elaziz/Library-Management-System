package driver;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static driver.Engine.STATEMENT;

/**
 * Created by salma on 03/05/17.
 */

public class LogInController {
    LogInUI ui;

    public LogInController(LogInUI ui) {
        this.ui = ui;
    }

    public class SignInBtnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ResultSet rs = null;
            String sql = null;
            sql = "SELECT * FROM STORE_USER WHERE User_name = \"" + ui.getSignInData()[0] + "\" and Password = \"" + ui.getSignInData()[1] + "\"";
            try {
                rs = STATEMENT.executeQuery(sql);
                rs.next();
                Engine.IS_MANAGER = rs.getInt("Is_manager");

                if (Engine.IS_MANAGER == 1) {
                    new ManagerUI();
                    ui.setVisible(false); //you can't see me!
                    ui.dispose();
                } else {
                    new CustomerUI();

                    ui.setVisible(false); //you can't see me!
                    ui.dispose();
                }
                Engine.LOGGED_IN_USER = ui.getSignInData()[0];
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, "Wrong username or password!");
            }
        }
    }


    public class SignUpBtnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String sql = null;
            int isManager = 0;
            isManager = ui.getSignUpData()[7].equals(Engine.MgrSecurityCode) ? 1 : 0;

            sql = "INSERT INTO STORE_USER VALUES ( \"" + ui.getSignUpData()[0] + "\" , " +
                    "\"" + ui.getSignUpData()[1] + "\" , " +
                    "\"" + ui.getSignUpData()[2] + "\" , " +
                    "\"" + ui.getSignUpData()[3] + "\" , " +
                    "\"" + ui.getSignUpData()[4] + "\" , " +
                    "\"" + ui.getSignUpData()[5] + "\" , " +
                    "\"" + ui.getSignUpData()[6] + "\" , " +
                    String.valueOf(isManager) +
                    " )";

            try {
                STATEMENT.executeUpdate(sql);
                JOptionPane.showMessageDialog(null, "Signed up successfully!");
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

    public ActionListener getSignUpBtnListener() {
        return new SignUpBtnListener();
    }

    public ActionListener getSignInBtnListener() {
        return new SignInBtnListener();
    }

}
