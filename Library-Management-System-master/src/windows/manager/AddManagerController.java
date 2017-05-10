package windows.manager;

import driver.Engine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by salma on 09/05/17.
 */
public class AddManagerController {

    AddManagerUI ui;
    public AddManagerController (AddManagerUI ui) {
        this.ui = ui;
    }

    /* Action listener for "ADD BOOK " button */
    protected ActionListener getAddManagerBtnListener() {
        return new addMangerBtnListener();
    }


    private class addMangerBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String insertBookSQL = "UPDATE STORE_USER SET Is_manager = 1 WHERE User_name = \"actual_user_name\"";

            insertBookSQL=insertBookSQL.replace("actual_user_name",ui.userNameTF.getText());

            ui.setVisible(false);
            ui.dispose();
            try{
                Engine.STATEMENT.executeUpdate(insertBookSQL);
            }catch(Exception EX){
            }
        }
    }


}
