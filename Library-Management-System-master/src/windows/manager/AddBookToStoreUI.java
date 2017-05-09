package windows.manager;

import driver.Connector;
import driver.Engine;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;

/**
 * Created by mirnafayez on 03/05/17.
 */
public class AddBookToStoreUI extends JFrame {

    protected BookInfoForm addBookPanel;
    private AddBookToStoreController controller;

    /*
    * Constructor
    * */
    public AddBookToStoreUI() {
        super("ADD A NEW BOOK FOR STORE");
        this.setVisible(true);
        this.setSize(Engine.WINDOW_WIDTH, Engine.WINDOW_HEIGHT);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getContentPane().setLayout(null);

        // Controller
        controller = new AddBookToStoreController(this);


        addBookPanel = new BookInfoForm();
        this.getContentPane().add(addBookPanel);

        addBookPanel.okBtn.addActionListener(controller.getokBtnListener());

    }

}
