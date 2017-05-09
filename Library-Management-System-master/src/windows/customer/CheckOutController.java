package windows.customer;

import driver.Engine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by salma on 05/05/17.
 */
public class CheckOutController {

    private CheckOutUI ui;

    public CheckOutController(CheckOutUI ui) {
        this.ui = ui;
    }

    private class SaveBtnListener implements ActionListener {
        String sql;

        @Override
        public void actionPerformed(ActionEvent e) {
            String creditCardNo = ui.getCreditCardNo();
            String expiryDate = ui.getExpiryDate();

            if (isValidCard(creditCardNo) && isValidDate(expiryDate)) {
                try {
                    Engine.CONNECTION.setAutoCommit(false);

                    sql = "UPDATE SHOPPING_CART SET Confirmed = 1 WHERE Confirmed = 0 and User_name = " + "\"" + Engine.LOGGED_IN_USER + "\"";
                    Engine.STATEMENT.executeUpdate(sql);

                    Engine.CONNECTION.commit();
                    ui.setVisible(false);
                    ui.dispose();
                }catch (Exception e1){
                    JOptionPane.showMessageDialog(null, "Not enough quantity");
                    try {
                        Engine.CONNECTION.rollback();
                    } catch (SQLException e2) {
                    }
                }

                try {
                    Engine.CONNECTION.setAutoCommit(true);
                } catch (Exception e2) {
                }

            }else{
                JOptionPane.showMessageDialog(null, "Invalid Input");
            }

        }
    }


    private boolean isValidCard(String creditCardNo) {

        if (creditCardNo.length() != 16) {
            return false;
        }

        for (int i = 0; i < 16; i++) {
            if (!(creditCardNo.charAt(i) >= '0' && creditCardNo.charAt(i) <= '9')) {
                return false;
            }
        }

        return true;
    }

    private boolean isValidDate(String expiryDate) {

        String[] checkOutDate = expiryDate.split("-");

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String date = format1.format(cal.getTime());
        String[] todayDate = date.split("-");

        if(Integer.parseInt(checkOutDate[1]) >= 12 || Integer.parseInt(checkOutDate[2]) >= 31){
            return false;
        }
        if (Integer.parseInt(checkOutDate[0]) < Integer.parseInt(todayDate[0])) {
            return false;
        }

        if(Integer.parseInt(checkOutDate[0]) >= Integer.parseInt(todayDate[0])){
            return true;
        }

        return true;
    }

    public ActionListener getSaveBtnListener(){
        return new SaveBtnListener();
    }

}
